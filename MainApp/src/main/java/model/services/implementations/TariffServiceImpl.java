package model.services.implementations;

import exceptions.BusinessLogicException;
import model.dto.*;
import model.entities.Addition;
import model.entities.Package;
import model.entities.Tariff;
import model.log.ModelLog;
import model.repositories.AdditionRepository;
import model.repositories.TariffRepository;
import model.services.TariffService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import utils.Converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TariffServiceImpl implements TariffService {
    private TariffRepository tariffRepository;
    private AdditionRepository additionRepository;
    private ModelMapper modelMapper;
    private Converter converter;
    private static Logger logger = LoggerFactory.getLogger(TariffServiceImpl.class);

    @Autowired
    public TariffServiceImpl(TariffRepository tariffRepository, AdditionRepository additionRepository,
                             ModelMapper modelMapper, Converter converter) {
        this.tariffRepository = tariffRepository;
        this.additionRepository = additionRepository;
        this.modelMapper = modelMapper;
        this.converter = converter;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void create(TariffDto tariff) throws BusinessLogicException {
        logger.debug("create() : {}", tariff);
        if (tariff == null) {
            throw new BusinessLogicException("Tariff is null!");
        }
        tariff.setValid(true);
        tariffRepository.create(modelMapper.map(tariff, Tariff.class));
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public List<TariffDto> getAll() throws BusinessLogicException {
        logger.debug("getAll()");
        List<Tariff> tariffs = tariffRepository.getAllValid();
        if (tariffs == null) {
            throw new BusinessLogicException("No valid tariffs!");
        }
        for (Tariff tariff : tariffs) {
            Set<Package> packages = new HashSet<>();
            tariff.getPackages().forEach(
                    pack -> {
                        if (pack.getAddition().getValid()) {
                            packages.add(pack);
                        }
                    }
            );
            tariff.setPackages(packages);
        }
        return tariffs.stream().map(tariff -> modelMapper.map(tariff, TariffDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public TariffDto getById(Integer id) {
        logger.debug("getById() : {}", id);
        Tariff tariff = tariffRepository.getByProperty("id", id);
        return tariff == null ? null : modelMapper.map(tariff, TariffDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void saveOrUpdate(TariffFormDto tariffForm) throws BusinessLogicException {
        logger.debug("saveOrUpdate() : {}", tariffForm);
        if (tariffForm == null) {
            throw new BusinessLogicException("TariffForm is null!");
        }
        TariffDto tariff = converter.convertToTariffDto(tariffForm);
        try {
            if (getById(tariff.getId()) == null) {
                tariffRepository.create(modelMapper.map(tariff, Tariff.class));
            } else {
                tariffRepository.update(modelMapper.map(tariff, Tariff.class));
            }
        } catch (Exception e) {
            throw new BusinessLogicException("Saving tariff failed!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void deleteById(int id) throws BusinessLogicException {
        logger.debug("deleteById() : {}", id);
        Tariff tariff = tariffRepository.getByProperty("id", id);
        if (tariff == null) {
            throw new BusinessLogicException("No tariffs with this id!");
        }
        tariff.setValid(false);
        try {
            tariffRepository.update(tariff);
        } catch (Exception e) {
            throw new BusinessLogicException("Deleting tariff failed!");
        }
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public List<AdditionDto> getAllAdditions() throws BusinessLogicException {
        logger.debug("getAllAdditions()");
        List<Addition> additions = additionRepository.getAllValid();
        if (additions == null) {
            throw new BusinessLogicException("No valid additions!");
        }
        return additions.stream()
                .map(add -> modelMapper.map(add, AdditionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public List<TariffOptionsDto> getTariffsWithOptions() throws BusinessLogicException {
        logger.debug("getTariffsWithOptions()");
        List<Tariff> tariffs = tariffRepository.getAllValid();
        if (tariffs == null) {
            throw new BusinessLogicException("No valid tariffs!");
        }
        List<TariffOptionsDto> tods = new ArrayList<>();
        Set<AdditionDto> additions = new HashSet<>(additionRepository.getAllValid()).stream()
                .map(addition -> modelMapper.map(addition, AdditionDto.class))
                .collect(Collectors.toSet());
        for (Tariff tariff : tariffs) {
            Set<AdditionDto> availableAdditions = new HashSet<>(additions);
            TariffOptionsDto tod = new TariffOptionsDto();
            tod.setId(tariff.getId());
            tod.setName(tariff.getName());
            tod.setSms(tariff.getSms());
            tod.setMinutes(tariff.getMinutes());
            tod.setInternet(tariff.getInternet());
            tod.setDescription(tariff.getDescription());
            tod.setPrice(tariff.getPrice());

            Set<AdditionDto> incompatibleAdditions = tariff.getPackages().stream()
                    .map(Package::getAddition)
                    .map(add -> modelMapper.map(add, AdditionDto.class))
                    .collect(Collectors.toSet());

            availableAdditions.removeAll(incompatibleAdditions);
            tod.setAdditions(availableAdditions);
            tods.add(tod);
        }
        return tods;
    }

    @Override
    @Transactional(readOnly = true)
    @ModelLog
    public Set<AdditionDto> getRemainAdditions(TariffDto tariff) throws BusinessLogicException {
        logger.debug("getRemainAdditions() : {}", tariff);
        if (tariff == null) {
            throw new BusinessLogicException("Tariff is null!");
        }
        List<Addition> additions = additionRepository.getAllValid();
        if (additions == null) {
            throw new BusinessLogicException("No valid additions!");
        }
        Set<AdditionDto> allAdditions = additions.stream()
                .map(add -> modelMapper.map(add, AdditionDto.class))
                .collect(Collectors.toSet());
        Set<AdditionDto> incompatibleAdditions = new HashSet<>();
        tariff.getPackages().forEach(pack -> incompatibleAdditions.add(pack.getAddition()));
        allAdditions.removeAll(incompatibleAdditions);
        return allAdditions;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ModelLog
    public void removeIncompatibleOption(Integer tId, Integer optId) throws BusinessLogicException {
        Tariff tariff = tariffRepository.getByProperty("id", tId);
        if (tariff == null) {
            throw new BusinessLogicException("No tariffs with this id!");
        }
        Set<Package> packageToRemove = new HashSet<>();
        for (Package pack : tariff.getPackages()) {
            if (pack.getAddition().getId() == optId) {
                packageToRemove.add(pack);
            }
        }
        Set<Package> tariffPackages = tariff.getPackages();
        tariffPackages.removeAll(packageToRemove);
        tariff.setPackages(tariffPackages);
        try {
            tariffRepository.update(tariff);
        } catch (Exception e) {
            throw new BusinessLogicException("Removing options from tariff failed!");
        }
    }
}
