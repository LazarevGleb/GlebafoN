package model.services.implementations;

import exceptions.BusinessLogicException;
import model.dto.*;
import model.entities.Addition;
import model.entities.Client;
import model.entities.Contract;
import model.entities.Tariff;
import model.log.ModelLog;
import model.repositories.AdditionRepository;
import model.repositories.ClientRepository;
import model.repositories.ContractRepository;
import model.repositories.TariffRepository;
import model.services.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import utils.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private ContractRepository contractRepository;
    private TariffRepository tariffRepository;
    private AdditionRepository additionRepository;
    private ModelMapper modelMapper;
    private Converter converter;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ContractRepository contractRepository,
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
    @Transactional(readOnly = true)
    @ModelLog
    public ClientDto getById(Integer id) throws BusinessLogicException {
        Client client = clientRepository.getByProperty("id", id);
        if (client == null) {
            throw new BusinessLogicException("No client with this id!");
        }
        return modelMapper.map(client, ClientDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void create(ClientDto clientDto) throws BusinessLogicException {
        if (clientDto==null){
            throw new BusinessLogicException("Client is null");
        }
        clientDto.setValid(true);
        try {
            Client client = modelMapper.map(clientDto, Client.class);
            clientRepository.create(client);
        } catch (Exception e) {
            throw new BusinessLogicException("Failed creation");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @ModelLog
    public List<ClientDto> getAll() throws BusinessLogicException {
        List<Client> clients = clientRepository.getAllValid();
        if (clients == null) {
            throw new BusinessLogicException("No valid clients!");
        }
        for (Client client : clients) {
            List<Contract> contracts = new ArrayList<>();
            client.getContracts().forEach(
                    contract -> {
                        if (contract.getValid()) {
                            contracts.add(contract);
                        }
                    }
            );
            client.setContracts(contracts);
        }

        return clients.stream()
                .map(client -> modelMapper.map(client, ClientDto.class))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void deleteById(Integer id) throws BusinessLogicException {
        ClientDto clientDto = getById(id);
        List<ContractDto> contractDtos = clientDto.getContracts();
        for (ContractDto cnt : contractDtos) {
            clientDto.getContracts().remove(cnt);
            cnt.setValid(false);
            Contract contract = modelMapper.map(cnt, Contract.class);
            contractRepository.update(contract);
            clientDto.getContracts().add(cnt);
        }
        Client client = modelMapper.map(clientDto, Client.class);
        client.setValid(false);
        clientRepository.update(client);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void update(ClientDto dto) throws BusinessLogicException {
        if (dto == null) {
            throw new BusinessLogicException("Parameter is null!");
        }
        clientRepository.update(modelMapper.map(dto, Client.class));
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public List<TariffDto> getTariffList() throws BusinessLogicException {
        List<Tariff> tariffs = tariffRepository.getAllValid();
        if (tariffs == null) {
            throw new BusinessLogicException("No vaild tariffs!");
        }
        return tariffs.stream()
                .map(t -> modelMapper.map(t, TariffDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public List<AdditionDto> getAdditionList() throws BusinessLogicException {
        List<Addition> additions = additionRepository.getAllValid();
        if (additions == null) {
            throw new BusinessLogicException("No valid options!");
        }
        return additions.stream()
                .map(ad -> modelMapper.map(ad, AdditionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void createFromBigClient(BigClientDto bigClient) throws BusinessLogicException {
        try {
            ClientDto clientDto = converter.convertToClientDto(bigClient);
            ContractDto contractDto = converter.convertToContractDto(bigClient, clientDto);
            clientDto.getContracts().add(contractDto);
            Client client = modelMapper.map(clientDto, Client.class);
            clientRepository.create(client);
        } catch (Exception e) {
            throw new BusinessLogicException("Client creation failed!");
        }
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public Set<String> getNumberList() throws BusinessLogicException {
        try {
            return converter.getNumbers();
        } catch (Exception e) {
            throw new BusinessLogicException("No numbers generated");
        }
    }
}
