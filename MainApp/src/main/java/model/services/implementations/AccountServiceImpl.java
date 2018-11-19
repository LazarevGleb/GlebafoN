package model.services.implementations;

import exceptions.BusinessLogicException;
import model.dto.ContractDto;
import model.dto.UserDto;
import model.entities.Contract;
import model.log.ModelLog;
import model.repositories.ContractRepository;
import model.services.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import utils.AttributeName;

import java.util.HashSet;
import java.util.Set;

@Component
public class AccountServiceImpl implements AccountService, UserDetailsService {
    private ContractRepository contractRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setContractRepository(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void update(UserDto user) throws BusinessLogicException {
        if (user.getConfirmPassword() == null) {
            throw new BusinessLogicException("Password is not confirmed!");
        }
        Contract contract = contractRepository.getByProperty(AttributeName.NUMBER, user.getNumber());
        if (contract == null) {
            throw new BusinessLogicException("No contract found!");
        }
        contract.setPassword(user.getConfirmPassword());
        contractRepository.update(contract);
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public ContractDto getContractByNumber(String number) {
        Contract contract = contractRepository.getByProperty(AttributeName.NUMBER, number);
        return contract == null ? null : modelMapper.map(contract, ContractDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String number) {
        String username;
        String password;
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (number.equals(AttributeName.MANAGER)) {
            username = "manager";
            password = bCryptPasswordEncoder.encode("manager");
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        } else {
            Contract contract = contractRepository.getByProperty("number", number);
            if (contract == null) return null;
            username = contract.getNumber();
            password = bCryptPasswordEncoder.encode(contract.getPassword());
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new User(username, password, grantedAuthorities);
    }
}
