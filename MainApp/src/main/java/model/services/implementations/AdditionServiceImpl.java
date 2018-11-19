package model.services.implementations;

import exceptions.BusinessLogicException;
import model.dto.*;
import model.entities.Addition;
import model.entities.Package;
import model.entities.Tariff;
import model.log.ModelLog;
import model.repositories.AdditionRepository;
import model.repositories.TariffRepository;
import model.services.AdditionService;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AdditionServiceImpl implements AdditionService {
    private AdditionRepository additionRepository;
    private ModelMapper modelMapper;
    private TariffRepository tariffRepository;

    private static Logger logger = LoggerFactory.getLogger(AdditionServiceImpl.class);

    @Autowired
    public AdditionServiceImpl(AdditionRepository additionRepository, ModelMapper modelMapper,
                               TariffRepository tariffRepository) {
        this.additionRepository = additionRepository;
        this.modelMapper = modelMapper;
        this.tariffRepository = tariffRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void create(AdditionDto option) throws BusinessLogicException {
        logger.debug("create() : {}", option);
        if (option == null) {
            throw new BusinessLogicException("Option is null");
        }
        option.setValid(true);
        additionRepository.create(modelMapper.map(option, Addition.class));
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public List<AdditionDto> getAll() throws BusinessLogicException {
        logger.debug("getAll()");
        List<Addition> options = additionRepository.getAllValid();
        if (options == null) {
            throw new BusinessLogicException("No options found");
        }
        for (Addition addition : options) {
            Set<Package> packages = new HashSet<>();
            addition.getPackages().forEach(
                    pack -> {
                        if (pack.getTariff().getValid()) {
                            packages.add(pack);
                        }
                    }
            );
            addition.setPackages(packages);
        }
        return options.stream().map(opt -> modelMapper.map(opt, AdditionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void deleteById(int id) throws BusinessLogicException {
        logger.debug("deleteById() : {}", id);
        Addition addition = additionRepository.getByProperty("id", id);
        if (addition == null) {
            throw new BusinessLogicException("No addition with specified id!");
        }
        addition.setValid(false);
        additionRepository.update(addition);
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public AdditionDto getById(Integer id) {
        logger.debug("getById() : {}", id);
        Addition addition = additionRepository.getByProperty("id", id);
        return addition == null ? null : modelMapper.map(addition, AdditionDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public Set<TariffDto> getRemainTariffs(AdditionDto addition) throws BusinessLogicException {
        logger.debug("getRemainAdditions() : {}", addition);
        if (addition == null) {
            throw new BusinessLogicException("Option is null!");
        }
        List<Tariff> tariffDao = tariffRepository.getAllValid();
        Set<TariffDto> tariffs = tariffDao.stream()
                .map(tar -> modelMapper.map(tar, TariffDto.class))
                .collect(Collectors.toSet());
        Set<TariffDto> incompatibleTariffs = new HashSet<>();
        addition.getPackages().forEach(pack -> incompatibleTariffs.add(pack.getTariff()));
        tariffs.removeAll(incompatibleTariffs);
        return tariffs;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public Integer removeIncompatibleTariff(String request) throws BusinessLogicException {
        logger.debug("removeIncompatibleTariff() : {}", request);
        String[] parts = request.split("#");
        if (parts[0].charAt(0) == '\"') {
            parts[0] = parts[0].substring(1);
        }
        int id = Integer.parseInt(parts[0]);
        Addition addition = additionRepository.getByProperty("id", id);
        if (addition == null) {
            throw new BusinessLogicException("No addition with id!");
        }
        String[] fullTariff = parts[1].split("\\(");
        String tariffName = fullTariff[0];
        Set<Package> packageToRemove = new HashSet<>();
        for (Package pack : addition.getPackages()) {
            if (pack.getTariff().getName().equals(tariffName)) {
                packageToRemove.add(pack);
            }
        }
        Set<Package> additionPackages = addition.getPackages();
        additionPackages.removeAll(packageToRemove);
        addition.setPackages(additionPackages);
        additionRepository.update(addition);
        return id;
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public Set<AddStatus> getAddStatusSet(Integer id) throws BusinessLogicException {
        logger.debug("getAddStatusSet : {}", id);
        List<Addition> additions = additionRepository.getAllValid();
        if (id != 0) {
            Addition addition = additionRepository.getByProperty("id", id);
            if (addition == null) {
                throw new BusinessLogicException("Wrong id!");
            }
            additions.remove(addition);
        }
        Set<AddStatus> addStatusSet = new HashSet<>(additions.size());
        additions.forEach(add -> {
            AddStatus addStatus = new AddStatus();
            addStatus.setName(add.getName());
            addStatus.setStatus(AddStatus.Status.NONE);
            addStatus.setId(add.getId());
            addStatusSet.add(addStatus);
        });
        return addStatusSet;
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public Set<AddStatus> checkRelations(String relations) throws BusinessLogicException {
        logger.debug("checkRelations() : {}", relations);
        int id = Character.getNumericValue(relations.charAt(relations.length() - 2));
        Set<AddStatus> addStatuses = parseRelationString(relations.substring(0, relations.length() - 2));
        if (addStatuses == null) {
            throw new BusinessLogicException("Failed parsing");
        }
        Set<Addition> mandatories = new HashSet<>();
        Set<Addition> incompatibles = new HashSet<>();
        for (AddStatus as : addStatuses) {
            if (as.getStatus() == AddStatus.Status.MANDATORY) {
                mandatories.add(additionRepository.getByProperty("id", as.getId()));
            } else if (as.getStatus() == AddStatus.Status.INCOMPATIBLE) {
                incompatibles.add(additionRepository.getByProperty("id", as.getId()));
            }
        }
        addStatuses = firstCheck(addStatuses, mandatories);
        if (addStatuses.isEmpty()) {
            return new HashSet<>();
        }

        AddStatus mainAS = new AddStatus();
        if (id != 0) {
            mainAS.setId(additionRepository.getByProperty("id", id).getId());
            Addition mainAdd = additionRepository.getByProperty("id", id);
            addStatuses = secondCheck(addStatuses, incompatibles, mainAdd);
            addStatuses = thirdCheck(addStatuses, mandatories, mainAdd);
            if (addStatuses.isEmpty()) {
                return new HashSet<>();
            }

        } else {
            mainAS.setId(additionRepository.getAllValid().stream()
                    .map(Addition::getId)
                    .reduce(Integer.MIN_VALUE, Integer::max) + 1);
        }
        mainAS.setName("mainAS");
        mainAS.setStatus(AddStatus.Status.NONE);
        addStatuses.add(mainAS);
        return addStatuses;
    }

    private Set<AddStatus> firstCheck(Set<AddStatus> addStatuses, Set<Addition> mandatories) {
        if (mandatories.size() > 1) {
            for (Addition man : mandatories) {
                for (Addition add : mandatories) {
                    if (man.getIncompatibleOptions().contains(add)) {
                        return new HashSet<>();
                    }
                }
            }
        }
        return addStatuses;
    }

    private Set<AddStatus> secondCheck(Set<AddStatus> addStatuses, Set<Addition> incompatibles,
                                       Addition mainAdd) {
        if (!incompatibles.isEmpty()) {
            for (Addition inc : incompatibles) {
                if (inc.getMandatoryOptions().contains(mainAdd)) {
                    return new HashSet<>();
                }
            }
        }
        return addStatuses;
    }

    private Set<AddStatus> thirdCheck(Set<AddStatus> addStatuses, Set<Addition> mandatories,
                                      Addition mainAdd) {
        if (addStatuses.isEmpty() || mandatories.isEmpty()) {
            return addStatuses;
        }
        for (Addition man : mandatories) {
            if (man.getMandatoryOptions().contains(mainAdd)) {
                return new HashSet<>();
            }
        }
        return addStatuses;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public Set<AddStatus> submitRelations(Set<AddStatus> addStatuses) throws BusinessLogicException {
        logger.debug("submitRelations() : {}", addStatuses);
        if (addStatuses == null) {
            throw new BusinessLogicException("Set is null");
        }
        Addition mainAdd = new Addition();
        for (AddStatus as : addStatuses) {
            if (as.getName().equals("mainAS")) {
                mainAdd = additionRepository.getByProperty("id", as.getId());
                addStatuses.remove(as);
                break;
            }
        }
        Addition finalMainAdd = mainAdd;
        addStatuses.forEach(as -> {
            if (as.getStatus() == AddStatus.Status.MANDATORY) {
                finalMainAdd.getMandatoryOptions().add(
                        additionRepository.getByProperty("id", as.getId()));
            } else if (as.getStatus() == AddStatus.Status.INCOMPATIBLE) {
                finalMainAdd.getIncompatibleOptions().add(
                        additionRepository.getByProperty("id", as.getId()));
            }
        });
        mainAdd.setMandatoryOptions(finalMainAdd.getMandatoryOptions());
        mainAdd.setIncompatibleOptions(finalMainAdd.getIncompatibleOptions());
        additionRepository.update(mainAdd);

        return addStatuses;
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public Set<TariffDto> getRemainedTariffs(List<String> packStr) throws BusinessLogicException {
        if (packStr == null) {
            throw new BusinessLogicException("List is null");
        }
        Set<TariffDto> incomTariffs = parsePackStr(packStr);
        Set<TariffDto> allTariffs = tariffRepository.getAllValid().stream()
                .map(tar -> modelMapper.map(tar, TariffDto.class))
                .collect(Collectors.toSet());
        allTariffs.removeAll(incomTariffs);
        return allTariffs;
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public AdditionWithTariffsDto getAddWithTariffsById(Integer id) {
        logger.debug("getAddWithTariffsById() : {}", id);
        AdditionWithTariffsDto ad = new AdditionWithTariffsDto();
        List<Tariff> tariffs = tariffRepository.getAllValid();
        Set<TariffDto> allTariffs = tariffs.stream()
                .map(tar -> modelMapper.map(tar, TariffDto.class))
                .collect(Collectors.toSet());
        if (id == 0) {
            ad.setRemainedTariffs(allTariffs);
        } else {
            AdditionDto addition = modelMapper.map(additionRepository
                    .getByProperty("id", id), AdditionDto.class);
            ad.setId(id);
            ad.setName(addition.getName());
            ad.setParameter(addition.getParameter());
            ad.setValue(addition.getValue());
            ad.setValid(addition.getValid());
            ad.setPrice(addition.getPrice());
            ad.setAdditionActivationCost(addition.getAdditionActivationCost());
            ad.setPackages(addition.getPackages());

            Set<TariffDto> incomTariffs = ad.getPackages().stream()
                    .map(PackageDto::getTariff)
                    .collect(Collectors.toSet());
            allTariffs.removeAll(incomTariffs);
            ad.setRemainedTariffs(allTariffs);
        }
        return ad;
    }

    private Set<TariffDto> parsePackStr(List<String> packStr) {
        Set<TariffDto> tariffDtos = new HashSet<>();
        for (String str : packStr) {
            String nameTariff = str.substring(1, str.indexOf('('));
            Tariff tariff = tariffRepository.getByProperty("name", nameTariff);
            TariffDto tariffDto = modelMapper.map(tariff, TariffDto.class);
            tariffDtos.add(tariffDto);
        }
        return tariffDtos;
    }

    private Set<AddStatus> parseRelationString(String str) {
        str = str.substring(1);
        Set<AddStatus> addStatuses = new HashSet<>();
        String[] parts = str.split(";");
        for (String part : parts) {
            AddStatus addStatus = new AddStatus();
            addStatus.setId(Integer.parseInt(part.split("#")[0]));
            addStatus.setName(additionRepository.getByProperty("id",
                    addStatus.getId()).getName());
            addStatus.setStatus(AddStatus.Status.valueOf(part.split("#")[1]));
            addStatuses.add(addStatus);
        }
        return addStatuses;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void saveOrUpdate(AdditionDto additionDto) {
        logger.debug("saveOrUpdate() : {}", additionDto);
        try {
            if (getById(additionDto.getId()) == null) {
                additionRepository.create(modelMapper.map(additionDto, Addition.class));
            } else {
                additionRepository.update(modelMapper.map(additionDto, Addition.class));
            }
        } catch (ConstraintViolationException e) {
            logger.info("saveOrUpdate(\'{}\') threw ConstraintViolationException", additionDto);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public AdditionDto parseRequest(String request) {
        String[] parts = request.split("\",\"[a-z]");
        AdditionDto additionDto = parseAddFields(parts);

        Set<PackageDto> packageDtosCh = populatePackages(parsePackageChosen(parts[6]), additionDto);
        additionDto.setPackages(packageDtosCh);

        Set<PackageDto> packageDtosPr = populatePackages(parsePackagePrevious(parts[6]), additionDto);
        additionDto.getPackages().addAll(packageDtosPr);

        additionDto.setMandatoryOptions(parseMandatoryOptions(parts[7]));

        Set<AdditionDto> incompatibleOptions = parseIncompatibleOptions(parts[8]);
        additionDto.setIncompatibleOptions(incompatibleOptions);
        for (AdditionDto ad : incompatibleOptions) {
            ad.getIncompatibleOptions().add(additionDto);
        }

        additionDto.setValid(true);
        return additionDto;
    }

    private Set<AdditionDto> parseIncompatibleOptions(String part) {
        String incompatibles = part.substring(part.indexOf(':') + 2, part.indexOf('}'));
        Set<AdditionDto> incompatibleOptions = new HashSet<>();
        if (!incompatibles.equals("") && !incompatibles.equals(",") && !incompatibles.equals("\"")) {
            String[] incomIndexes = incompatibles.split(";");

            for (String idx : incomIndexes) {
                if (!idx.equals("") && !idx.equals(",") && !idx.equals("\"")) {
                    if (idx.contains(";")) {
                        idx = idx.substring(0, idx.indexOf(';'));
                    }
                    incompatibleOptions.add(
                            modelMapper.map(additionRepository.getByProperty(
                                    "id", Integer.parseInt(idx)), AdditionDto.class));
                }
            }
        }
        return incompatibleOptions;
    }

    private Set<Addition> parseMandatoryOptions(String part) {
        String mandatories = part.substring(part.indexOf(':') + 2);
        Set<Addition> mandatoryOptions = new HashSet<>();
        if (!mandatories.equals("") && !mandatories.equals(",") && !mandatories.equals("\"")) {
            String[] mandIndexes = mandatories.split(";");
            for (String idx : mandIndexes) {
                if (!idx.equals("") && !idx.equals(",") && !idx.equals("\"")) {
                    if (idx.contains(";")) {
                        idx = idx.substring(0, idx.indexOf(';'));
                    }
                    mandatoryOptions.add(additionRepository.getByProperty("id", Integer.parseInt(idx)));
                }
            }
        }
        return mandatoryOptions;
    }

    private String[] parsePackagePrevious(String part) {
        String[] tariffsPr;
        if (part.contains("  ")) {
            String tariffsPrevious = part.substring(part.indexOf("  ") + 1);
            tariffsPrevious = tariffsPrevious.trim();
            tariffsPr = tariffsPrevious.split("X");
        } else {
            tariffsPr = ",".split(",");
        }
        return tariffsPr;
    }

    private String[] parsePackageChosen(String part) {
        String[] tariffsCh;
        if (part.contains(",\\")) {
            String tariffChosen = part.substring(part.indexOf(':') + 2, part.indexOf(",\\"));
            tariffsCh = tariffChosen.split("\\),");
        } else {
            tariffsCh = part.substring(part.indexOf(':') + 2).split("\\),");
        }
        return tariffsCh;
    }

    private AdditionDto parseAddFields(String[] parts) {
        AdditionDto additionDto = new AdditionDto();
        for (int i = 0; i < 6; i++) {
            parts[i] = parts[i].substring(parts[i].indexOf(':') + 2);
        }
        additionDto.setId(Integer.parseInt(parts[0]));
        additionDto.setName(parts[1]);
        additionDto.setParameter(Addition.Parameter.valueOf(parts[2]));
        additionDto.setValue(Integer.parseInt(parts[3]));
        additionDto.setPrice(BigDecimal.valueOf(Double.parseDouble(parts[4])));
        additionDto.setAdditionActivationCost(BigDecimal.valueOf(Double.parseDouble(parts[5])));
        return additionDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeIncompatibleOption(AdditionDto additionDto, AdditionDto relAdd) throws BusinessLogicException {
        if (additionDto == null) {
            throw new BusinessLogicException("First argument is null!");
        }
        //Due to wel-known bug of Hibernate I have to do this f*cking check
        // instead of simple Set#remove
        if (!additionDto.getIncompatibleOptions().contains(relAdd)) {
            relAdd.getIncompatibleOptions().forEach(add -> {
                if (add.getName().equals(additionDto.getName())) {
                    Set<AdditionDto> incompatibles = new HashSet<>();
                    relAdd.getIncompatibleOptions().forEach(addition -> {
                        if (!addition.getName().equals(additionDto.getName())) {
                            incompatibles.add(addition);
                        }
                    });
                    relAdd.setIncompatibleOptions(incompatibles);
                    additionRepository.update(modelMapper.map(relAdd, Addition.class));
                }
            });
        }
    }

    private Set<PackageDto> populatePackages(String[] tariffs, AdditionDto additionDto) {
        Set<PackageDto> packageDtos = new HashSet<>();
        for (String tariff : tariffs) {
            if (!tariff.equals("") && !tariff.equals(",")) {
                String tariffName = tariff.substring(0, tariff.indexOf('('));
                TariffDto tariffDto = modelMapper.map(tariffRepository.getByProperty(
                        "name", tariffName), TariffDto.class);
                PackageDto packageDto = new PackageDto();
                packageDto.setTariff(tariffDto);
                packageDto.setAddition(additionDto);
                packageDtos.add(packageDto);
            }
        }
        return packageDtos;
    }
}
