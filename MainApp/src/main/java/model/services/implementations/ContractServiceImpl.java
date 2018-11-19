package model.services.implementations;

import exceptions.BusinessLogicException;
import model.dto.*;
import model.entities.Addition;
import model.entities.Addition.Parameter;
import model.entities.Client;
import model.entities.Contract;
import model.entities.Tariff;
import model.log.ModelLog;
import model.repositories.AdditionRepository;
import model.repositories.ClientRepository;
import model.repositories.ContractRepository;
import model.repositories.TariffRepository;
import model.services.ContractService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import utils.AttributeName;
import utils.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ContractServiceImpl implements ContractService {
    private ClientRepository clientRepository;
    private ContractRepository contractRepository;
    private TariffRepository tariffRepository;
    private AdditionRepository additionRepository;
    private ModelMapper modelMapper;
    private Converter converter;

    @Autowired
    public ContractServiceImpl(ClientRepository clientRepository, ContractRepository contractRepository,
                               TariffRepository tariffRepository, AdditionRepository additionRepository,
                               ModelMapper modelMapper, Converter converter) {
        this.clientRepository = clientRepository;
        this.contractRepository = contractRepository;
        this.tariffRepository = tariffRepository;
        this.additionRepository = additionRepository;
        this.modelMapper = modelMapper;
        this.converter = converter;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void create(ContractDto contractDto) throws BusinessLogicException {
        if (contractDto == null) {
            throw new BusinessLogicException("Parameter is null");
        }
        Contract contract = new Contract();
        contract.setNumber(contractDto.getNumber());
        contract.setClient(modelMapper.map(contractDto.getClient(), Client.class));
        contract.setTariff(modelMapper.map(contractDto.getTariff(), Tariff.class));
        List<Addition> additions = contractDto.getAdditions().stream()
                .map(add -> modelMapper.map(add, Addition.class))
                .collect(Collectors.toList());
        contract.setAdditions(additions);
        contract.setBalance(contractDto.getBalance());
        contract.setValid(true);
        contractRepository.create(contract);
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public List<ContractDto> getAll() throws BusinessLogicException {
        List<Contract> contracts = contractRepository.getAllValid();
        if (contracts == null) {
            throw new BusinessLogicException("No valid contracts");
        }
        return contracts.stream()
                .map(cnt -> modelMapper.map(cnt, ContractDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public ContractDto getByNumber(String number) throws BusinessLogicException {
        Contract contract = contractRepository.getByProperty(AttributeName.NUMBER, number);
        if (contract == null) {
            throw new BusinessLogicException("No contract with this number");
        }
        return modelMapper.map(contract, ContractDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public ContractDto getById(int id) throws BusinessLogicException {
        Contract contract = contractRepository.getByProperty("id", id);
        if (contract == null) {
            throw new BusinessLogicException("No contract with this id!");
        }
        return modelMapper.map(contract, ContractDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public List<TariffDto> getTariffList() throws BusinessLogicException {
        List<Tariff> tariffs = tariffRepository.getAllValid();
        if (tariffs == null) {
            throw new BusinessLogicException("No valid tariffs!");
        }
        return tariffs.stream()
                .map(t -> modelMapper.map(t, TariffDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public TariffDto getTariffById(int id) throws BusinessLogicException {
        Tariff tariff = tariffRepository.getByProperty("id", id);
        if (tariff == null) {
            throw new BusinessLogicException("No tariffs with this id!");
        }
        return modelMapper.map(tariff, TariffDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void updateContract(ContractDto contractDto) throws BusinessLogicException {
        if (contractDto == null) {
            throw new BusinessLogicException("Wrong argument");
        }
        try {
            contractDto.setAdditions(converter.checkOwnAdditions(contractDto, contractDto.getAdditions()));
            Contract contract = modelMapper.map(contractDto, Contract.class);
            contractRepository.update(contract);
        } catch (Exception e) {
            throw new BusinessLogicException("Update of contract with additions failed!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void update(ContractDto contractDto) throws BusinessLogicException {
        if (contractDto == null) {
            throw new BusinessLogicException("Contract is null!");
        }
        try {
            Contract contract = modelMapper.map(contractDto, Contract.class);
            contractRepository.update(contract);
        } catch (Exception e) {
            throw new BusinessLogicException("Update of contract failed");
        }
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public Set<String> getNumberList() throws BusinessLogicException {
        try {
            return converter.getNumbers();
        } catch (Exception e) {
            throw new BusinessLogicException("No numbers generated!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void inverseBlockStatus(ContractDto contractDto) throws BusinessLogicException {
        if (contractDto == null) {
            throw new BusinessLogicException("Contract is null");
        }
        try {
            if (contractDto.getBlock() == Contract.BLOCK.UNBLOCKED) {
                contractDto.setBlock(Contract.BLOCK.MANAGER_BLOCKED);
            } else {
                contractDto.setBlock(Contract.BLOCK.UNBLOCKED);
            }
            Contract contract = modelMapper.map(contractDto, Contract.class);
            contractRepository.update(contract);
        } catch (Exception e) {
            throw new BusinessLogicException("Inverse of block status failed");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void inverseClientBlockStatus(ContractDto contractDto) throws BusinessLogicException {
        if (contractDto == null) {
            throw new BusinessLogicException("ContractDto is null");
        }
        try {
            if (contractDto.getBlock() == Contract.BLOCK.UNBLOCKED) {
                contractDto.setBlock(Contract.BLOCK.CLIENT_BLOCKED);
            } else if (contractDto.getBlock() == Contract.BLOCK.CLIENT_BLOCKED) {
                contractDto.setBlock(Contract.BLOCK.UNBLOCKED);
            }
            Contract contract = modelMapper.map(contractDto, Contract.class);
            contractRepository.update(contract);
        } catch (Exception e) {
            throw new BusinessLogicException("Inverse of client's block status failed");
        }
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public List<Integer> getAvailableOptionsIds(ContractDto contractDto) throws BusinessLogicException {
        if (contractDto == null) {
            throw new BusinessLogicException("ContractDto is null!");
        }
        TariffDto tariffDto = contractDto.getTariff();
        if (tariffDto == null)
            return new ArrayList<>();
        List<AdditionDto> contractAdditions = contractDto.getAdditions();
        List<Addition> additions = additionRepository.getAllValid();
        List<Integer> ids = new ArrayList<>();
        for (Addition ad : additions) {
            Addition.Parameter parameter = ad.getParameter();
            if (parameter == Parameter.SMS && tariffDto.getSms() == 0 ||
                    parameter == Parameter.MINUTES && tariffDto.getMinutes() == 0 ||
                    parameter == Parameter.INTERNET && tariffDto.getInternet() == 0 ||
                    contractAdditions.contains(modelMapper.map(ad, AdditionDto.class))) {
                break;
            } else {
                ids.add(ad.getId());
            }
        }
        return ids;
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public List<AdditionDto> getAvailableOptions(ContractDto contractDto) throws BusinessLogicException {
        if (contractDto == null) {
            throw new BusinessLogicException("Argument is null");
        }
        List<Addition> additions = additionRepository.getAllValid();
        return converter.checkAdditions(contractDto, additions);
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public AdditionDto getAdditionById(int aId) throws BusinessLogicException {
        Addition addition = additionRepository.getByProperty("id", aId);
        if (addition == null) {
            throw new BusinessLogicException("No additions with this id!");
        }
        return modelMapper.map(addition, AdditionDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public List<AdditionDto> getAdditionListByIdList(List<Integer> optionIds) throws BusinessLogicException {
        List<Addition> additions = new ArrayList<>();
        for (Integer id : optionIds) {
            Addition addition = additionRepository.getByProperty("id", id);
            if (addition == null) {
                throw new BusinessLogicException("No additions with this id");
            }
            additions.add(addition);
        }
        return additions.stream()
                .map(addition -> modelMapper.map(addition, AdditionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void submitBasket(ContractDto contractDto, Basket basket) throws BusinessLogicException {
        if (contractDto == null) {
            throw new BusinessLogicException("ContractDto null");
        }
        if (basket == null) {
            throw new BusinessLogicException("Basket is null");
        }
        TariffDto tariffDto = basket.getChosenTariff();
        if (tariffDto != null) {
            contractDto.setTariff(tariffDto);
            List<AdditionDto> additions = contractDto.getAdditions();
            if (!additions.isEmpty()) {
                List<AdditionDto> result = converter.checkOwnAdditions(contractDto, additions);
                contractDto.setAdditions(result);
            }
        }

        List<AdditionDto> deletedAdditions = new ArrayList<>(basket.getDeletedAdditions());
        if (!deletedAdditions.isEmpty())
            contractDto.getAdditions().removeAll(deletedAdditions);

        List<AdditionDto> chosenAdditions = new ArrayList<>(basket.getChosenAdditions());
        if (!chosenAdditions.isEmpty()) {
            List<Addition> additions = chosenAdditions.stream()
                    .map(add -> modelMapper.map(add, Addition.class))
                    .collect(Collectors.toList());
            List<AdditionDto> result = converter.checkAdditions(contractDto, additions);
            if (result != null) {
                contractDto.getAdditions().addAll(result);
            }
        }

        contractDto.setBalance(contractDto.getBalance().subtract(basket.getTotalSum()));
        try {
            contractRepository.update(modelMapper.map(contractDto, Contract.class));
        } catch (Exception e) {
            throw new BusinessLogicException("Submitting of basket failed!");
        }

        basket.clear();
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public ClientDto getClientById(int id) throws BusinessLogicException {
        Client client = clientRepository.getByProperty("id", id);
        if (client == null) {
            throw new BusinessLogicException("No clients with this id!");
        }
        return modelMapper.map(client, ClientDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public List<AdditionDto> getAllAdditions() throws BusinessLogicException {
        List<Addition> additions = additionRepository.getAllValid();
        if (additions == null) {
            throw new BusinessLogicException("No valid additions!");
        }
        return additions.stream().map(addition -> modelMapper.map(addition, AdditionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void deleteById(int id) throws BusinessLogicException {
        Contract contract = contractRepository.getByProperty("id", id);
        if (contract == null) {
            throw new BusinessLogicException("No contracts with this id!");
        }
        contract.setValid(false);
        try {
            contractRepository.update(contract);
        } catch (Exception e) {
            throw new BusinessLogicException("Deleting of contract failed!");
        }
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public ContractDto getByPassword(String password) throws BusinessLogicException {
        Contract contract = contractRepository.getByProperty("password", password);
        if (contract == null) {
            throw new BusinessLogicException("No contracts with this password!");
        }
        return modelMapper.map(contract, ContractDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void addClientContract(int id, ContractFormDto contractFormDto) throws BusinessLogicException {
        Client client = clientRepository.getByProperty("id", id);
        if (client == null) {
            throw new BusinessLogicException("No client with this id!");
        }
        if (contractFormDto == null) {
            throw new BusinessLogicException("Second parameter is null");
        }
        ContractDto contractDto = converter.convertToContractDtoFromForm(contractFormDto, client);
        contractRepository.create(modelMapper.map(contractDto, Contract.class));
    }
}