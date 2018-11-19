package model.services;

import exceptions.BusinessLogicException;
import model.dto.AdditionDto;
import model.dto.TariffDto;
import model.dto.TariffFormDto;
import model.dto.TariffOptionsDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface TariffService extends model.services.Service {
    /**
     * Creates new tariff
     *
     * @param tariff specified tariff
     */
    void create(TariffDto tariff) throws BusinessLogicException;

    /**
     * Returns all tariffs
     *
     * @return list of tariffs
     */
    List<TariffDto> getAll() throws BusinessLogicException;

    /**
     * Returns tariff according to its id
     *
     * @param id specified id
     * @return TariffDto instance
     */
    TariffDto getById(Integer id);

    /**
     * Creates or updates tariff
     *
     * @param tariff specified tariff
     */
    void saveOrUpdate(TariffFormDto tariff) throws BusinessLogicException;

    /**
     * Deletes tariff according to its id
     *
     * @param id specified id
     */
    void deleteById(int id) throws BusinessLogicException;

    /**
     * Returns all additions
     *
     * @return list of additions
     */
    List<AdditionDto> getAllAdditions() throws BusinessLogicException;

    /**
     * Returns all tariffs with its' options
     *
     * @return list of tariffs
     */
    List<TariffOptionsDto> getTariffsWithOptions() throws BusinessLogicException;

    /**
     * Returns all remaining additions which are not incompatible for tariff
     *
     * @param tariff specified tariff
     * @return set AdditionDto
     */
    Set<AdditionDto> getRemainAdditions(TariffDto tariff) throws BusinessLogicException;

    /**
     * Removes options from tariff's list of incompatible options
     *
     * @param tId   specified tariff
     * @param optId specified option
     */
    void removeIncompatibleOption(Integer tId, Integer optId) throws BusinessLogicException;
}
