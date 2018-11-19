package utils;

import model.dto.*;
import model.entities.Addition;
import model.entities.Client;
import model.entities.Contract;
import model.entities.Tariff;
import model.repositories.AdditionRepository;
import model.repositories.ContractRepository;
import model.repositories.TariffRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ConverterImpl implements Converter {
    private ModelMapper modelMapper;
    private TariffRepository tariffRepository;
    private AdditionRepository additionRepository;
    private ContractRepository contractRepository;

    @Autowired
    public ConverterImpl(ModelMapper modelMapper, TariffRepository tariffRepository,
                         AdditionRepository additionRepository, ContractRepository contractRepository) {
        this.modelMapper = modelMapper;
        this.additionRepository = additionRepository;
        this.tariffRepository = tariffRepository;
        this.contractRepository = contractRepository;
    }

    private String convertDate(String date) {
        //0123456789
        //yyyy-mm-dd
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        String day = date.substring(8);
        return day + "." + month + "." + year;
    }

    @Override
    public ClientDto convertToClientDto(BigClientDto bcd) {
        ClientDto client = new ClientDto();
        client.setName(bcd.getName());
        client.setSurname(bcd.getSurname());
        client.setBirthday(convertDate(bcd.getBirthday()));
        client.setPassportSeries(bcd.getPassportSeries());
        client.setPassportNumber(bcd.getPassportNumber());
        client.setPassportIssuedBy(bcd.getPassportIssuedBy());
        client.setPassportIssueDate(convertDate(bcd.getPassportIssueDate()));
        client.setPassportDivisionCode(bcd.getPassportDivisionCode());
        client.setAddress(bcd.getAddress());
        client.setEmail(bcd.getEmail());
        client.setValid(true);

        return client;
    }

    @Override
    @Transactional(readOnly = true)
    public ContractDto convertToContractDto(BigClientDto bcd, ClientDto clientDto) {
        ContractDto contract = getContractWithTariffOptions(bcd.getTariffId(), bcd.getAdditionIds());

        contract.setNumber(bcd.getNumber());
        contract.setClient(clientDto);
        contract.setBlock(Contract.BLOCK.UNBLOCKED);
        contract.setValid(true);
        contract.setBalance(bcd.getBalance());
        return contract;
    }

    private ContractDto getContractWithTariffOptions(Integer tId, List<Integer> aIds) {
        ContractDto contractDto = new ContractDto();
        contractDto.setTariff(getTariffDtoById(tId));
        contractDto.setAdditions(getListAdditionDtoById(aIds));
        return contractDto;
    }

    private TariffDto getTariffDtoById(Integer id) {
        Tariff tariff = tariffRepository.getByProperty("id", id);
        return tariff == null ? null : modelMapper.map(tariff, TariffDto.class);
    }

    private List<AdditionDto> getListAdditionDtoById(List<Integer> additionIds) {
        List<AdditionDto> additions = new ArrayList<>();
        for (Integer id : additionIds) {
            additions.add(modelMapper.map(
                    additionRepository.getByProperty("id", id), AdditionDto.class));
        }
        return additions;
    }

    @Override
    public AdditionDto updateAdditionDto(AdditionFormDto additionForm) {
        AdditionDto additionDto = modelMapper.map(
                additionRepository.getByProperty("id", additionForm.getId()),
                AdditionDto.class);
        return populateAdditionDto(additionDto, additionForm);
    }

    @Override
    public Set<String> getNumbers() {
        Set<String> numbers = new HashSet<>();
        Set<String> usedNumbers = contractRepository.getAll().stream()
                .map(Contract::getNumber)
                .collect(Collectors.toSet());

        for (int it = 0; it < 5; it++) {
            numbers.add(genNumber().toString());
        }
        numbers.removeAll(usedNumbers);
        return numbers;
    }

    private StringBuilder genNumber() {
        Random random = new Random();

        int part2 = random.nextInt(899) + 100;
        int part3 = random.nextInt(8999) + 1000;

        return new StringBuilder().append("+7-925-").append(part2).append("-").append(part3);
    }

    private Addition compareAdditionValues(Addition addition, List<AdditionDto> contractAdditions,
                                           Addition.Parameter parameter) {
        int value = addition.getValue();// parameter of checking option
        //iterate over all this contract's additions       (1)
        for (AdditionDto cAd : contractAdditions) {
            //if parameter of checking option is different
            if (parameter != cAd.getParameter()
                    && (contractAdditions.indexOf(cAd) == contractAdditions.size() - 1)
                    || value > cAd.getValue()) {
                //if parameters are different but it's the end of contract's
                // addition list then add current checking addition
                // or parameters are the same and the value of current checking options is greater than
                // value of option in list then add it
                return addition;
            }
        }
        return null;
    }

    private Addition checkCurrentAddition(Addition addition, List<AdditionDto> contractAdditions,
                                          TariffDto tariffDto) {
        AdditionDto adDto = modelMapper.map(addition, AdditionDto.class);
        //Check whether contract has already this addition and is it incompatible for the tariff
        if (!contractAdditions.contains(adDto) &&
                tariffDto.getPackages().stream().noneMatch(p -> p.getAddition().equals(adDto)))
            /*!tariffDto.getIncompatibleAdditions().contains(adDto))*/ {
            //get the parameter of current addition in DB
            Addition.Parameter parameter = addition.getParameter();
            //if this parameter in contract's tariff is 0 then iterate next addition
            if (parameter == Addition.Parameter.SMS && tariffDto.getSms() != 0 ||
                    parameter == Addition.Parameter.MINUTES && tariffDto.getMinutes() != 0 ||
                    parameter == Addition.Parameter.INTERNET && tariffDto.getInternet() != 0) {
                //if there are no additions on contract then add current
                if (contractAdditions.isEmpty()) {
                    return addition;
                } else {
                    return compareAdditionValues(addition, contractAdditions, parameter);
                }
            }
        }
        return null;
    }

    @Override
    public List<AdditionDto> checkAdditions(ContractDto contractDto, List<Addition> additions) {
        TariffDto tariffDto = contractDto.getTariff();
        if (tariffDto == null)
            return new ArrayList<>();
        //current chosen options of the contract
        List<AdditionDto> contractAdditions = contractDto.getAdditions();//current contract's additions
        List<Addition> availableAdditions = new ArrayList<>();//result additions
        //iterate over all additions in DB
        for (Addition ad : additions) {
            availableAdditions.add(checkCurrentAddition(ad, contractAdditions, tariffDto));
        }
        return availableAdditions.isEmpty() ? null :
                availableAdditions.stream()
                        .filter(Objects::nonNull)
                        .map(addition -> modelMapper.map(addition, AdditionDto.class))
                        .collect(Collectors.toList());
    }

    @Override
    public List<AdditionDto> checkOwnAdditions(ContractDto contractDto, List<AdditionDto> ownAdditions) {
        TariffDto tariffDto = contractDto.getTariff();
        List<AdditionDto> availableAdditions = new ArrayList<>();
        for (AdditionDto ad : ownAdditions) {
            if (tariffDto.getPackages().stream().
                    noneMatch(packageDto -> packageDto.getAddition().equals(ad))) {
                Addition.Parameter parameter = ad.getParameter();
                if (!(parameter == Addition.Parameter.SMS && tariffDto.getSms() == 0 ||
                        parameter == Addition.Parameter.MINUTES && tariffDto.getMinutes() == 0 ||
                        parameter == Addition.Parameter.INTERNET && tariffDto.getInternet() == 0)) {
                    availableAdditions.add(ad);
                }
            }
        }
        return availableAdditions;
    }

    @Override
    public TariffDto convertToTariffDto(TariffFormDto tariffForm) {
        TariffDto tariff = new TariffDto();

        tariff.setId(tariffForm.getId());
        tariff.setName(tariffForm.getName());
        tariff.setPrice(tariffForm.getPrice());
        tariff.setDescription(tariffForm.getDescription());
        tariff.setSms(tariffForm.getSms());
        tariff.setMinutes(tariffForm.getMinutes());
        tariff.setInternet(tariffForm.getInternet());

        List<AdditionDto> additions = getListAdditionDtoById(tariffForm.getAddIds());
        Set<PackageDto> packages = new HashSet<>();
        for (AdditionDto ad : additions) {
            PackageDto packageDto = new PackageDto();
            packageDto.setTariff(tariff);
            packageDto.setAddition(ad);
            packages.add(packageDto);
        }
        tariff.setPackages(packages);
        tariff.setValid(true);

        return tariff;
    }

    @Override
    public TariffFormDto convertToTariffFormDto(TariffDto tariff) {
        TariffFormDto tariffForm = new TariffFormDto();

        tariffForm.setId(tariff.getId());
        tariffForm.setName(tariff.getName());
        tariffForm.setPrice(tariff.getPrice());
        tariffForm.setDescription(tariff.getDescription());
        tariffForm.setSms(tariff.getSms());
        tariffForm.setMinutes(tariff.getMinutes());
        tariffForm.setInternet(tariff.getInternet());

        List<Integer> addIds = tariff.getPackages().stream()
                .map(PackageDto::getAddition)
                .map(AdditionDto::getId)
                .collect(Collectors.toList());
        tariffForm.setAddIds(addIds);

        return tariffForm;
    }

    private AdditionDto populateAdditionDto(AdditionDto addition, AdditionFormDto additionForm) {
        addition.setId(additionForm.getId());
        addition.setName(additionForm.getName());
        addition.setParameter(additionForm.getParameter());
        addition.setValue(additionForm.getValue());
        addition.setPrice(additionForm.getPrice());
        addition.setAdditionActivationCost(additionForm.getAdditionActivationCost());
        addition.setValid(true);

        for (Integer id : additionForm.getTariffIds()) {
            PackageDto packageDto = new PackageDto();
            packageDto.setTariff(getTariffDtoById(id));
            packageDto.setAddition(addition);
            addition.getPackages().add(packageDto);
        }

        Set<Addition> mandatories = additionForm.getAddStatuses().stream()
                .filter(st -> st.getStatus() == AddStatus.Status.MANDATORY)
                .map(st-> additionRepository.getByProperty("id", st.getId()))
                .collect(Collectors.toSet());
        addition.setMandatoryOptions(mandatories);

        Set<AdditionDto> incompatibles = additionForm.getAddStatuses().stream()
                .filter(st -> st.getStatus() == AddStatus.Status.INCOMPATIBLE)
                .map(st-> additionRepository.getByProperty("id", st.getId()))
                .map(add -> modelMapper.map(add, AdditionDto.class))
                .collect(Collectors.toSet());
        addition.setIncompatibleOptions(incompatibles);
        for (AdditionDto incOpt : incompatibles) {
            incOpt.getIncompatibleOptions()
                    .add(addition);
        }
        return addition;
    }

    @Override
    public AdditionDto convertToAdditionDto(AdditionFormDto additionForm) {
        AdditionDto additionDto = new AdditionDto();
        return populateAdditionDto(additionDto, additionForm);
    }

    @Override
    public AdditionFormDto convertToAdditionFormDto(AdditionDto addition) {
        AdditionFormDto afd = new AdditionFormDto();

        afd.setId(addition.getId());
        afd.setName(addition.getName());
        afd.setParameter(addition.getParameter());
        afd.setValue(addition.getValue());
        afd.setPrice(addition.getPrice());
        afd.setAdditionActivationCost(addition.getAdditionActivationCost());

        return afd;
    }

    @Override
    public ContractDto convertToContractDtoFromForm(ContractFormDto contractFormDto, Client client) {
        ContractDto contractDto = new ContractDto();

        contractDto.setId(contractFormDto.getId());
        contractDto.setBlock(Contract.BLOCK.UNBLOCKED);
        contractDto.setClient(modelMapper.map(client, ClientDto.class));
        contractDto.setNumber(contractFormDto.getNumber());
        contractDto.setTariff(getTariffDtoById(contractFormDto.getTariffId()));
        contractDto.setAdditions(getListAdditionDtoById(contractFormDto.getAdditionIds()));
        contractDto.setAdditions(checkOwnAdditions(contractDto, contractDto.getAdditions()));
        contractDto.setBalance(contractFormDto.getBalance());
        contractDto.setValid(true);

        return contractDto;
    }
}
