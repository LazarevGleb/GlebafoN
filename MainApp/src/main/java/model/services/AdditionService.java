package model.services;

import exceptions.BusinessLogicException;
import model.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface AdditionService extends model.services.Service {
    /**
     * Creates new addition
     *
     * @param option specified add
     */
    void create(AdditionDto option) throws BusinessLogicException;

    /**
     * Returns all additions
     *
     * @return list of additions
     */
    List<AdditionDto> getAll() throws BusinessLogicException;

    /**
     * Deletes according to id
     *
     * @param id specified id
     */
    void deleteById(int id) throws BusinessLogicException;

    /**
     * Creates or updates addition
     *
     * @param additionDto specified add
     */
    void saveOrUpdate(AdditionDto additionDto) throws BusinessLogicException;

    /**
     * Returns addition according to its id
     *
     * @param id specified id
     * @return AdditionDto instance
     */
    AdditionDto getById(Integer id);

    /**
     * Returns all remaining tariffs which are not incompatible for addition
     *
     * @param addition specified addition
     * @return set TariffDto
     */
    Set<TariffDto> getRemainTariffs(AdditionDto addition) throws BusinessLogicException;

    /**
     * Removes tariff from addition's list of incompatible tariffs
     *
     * @param tariff
     * @return option
     * @throws BusinessLogicException
     */
    Integer removeIncompatibleTariff(String tariff) throws BusinessLogicException;

    /**
     * Returns all additions beside the one
     *
     * @param id specified addition
     * @return set AddStatus
     */
    Set<AddStatus> getAddStatusSet(Integer id) throws BusinessLogicException;

    /**
     * Checks whether chosen relations are available
     *
     * @param relations chosen relations
     * @return Set names+relation
     */
    Set<AddStatus> checkRelations(String relations) throws BusinessLogicException;

    /**
     * Submits checked options relations
     *
     * @param addStatuses relations
     * @return set of option relations
     */
    Set<AddStatus> submitRelations(Set<AddStatus> addStatuses) throws BusinessLogicException;

    /**
     * Returns all remained tariffs according to string
     *
     * @param packStr specified string
     * @return set TariffDto
     */
    Set<TariffDto> getRemainedTariffs(List<String> packStr) throws BusinessLogicException;

    /**
     * Returns addition with tariffs according to id
     *
     * @param id specified id
     * @return dto
     */
    AdditionWithTariffsDto getAddWithTariffsById(Integer id) throws BusinessLogicException;

    /**
     * Parses request
     *
     * @param req specified request
     * @return dto
     */
    AdditionDto parseRequest(String req) throws BusinessLogicException;

    /**
     * If additionDto is not incompatible for ad then make it bidirectional
     *
     * @param additionDto specified additionDto
     * @param ad          specidied ad
     */
    void removeIncompatibleOption(AdditionDto additionDto, AdditionDto ad) throws BusinessLogicException;
}
