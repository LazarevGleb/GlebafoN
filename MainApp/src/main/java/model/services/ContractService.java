package model.services;

import exceptions.BusinessLogicException;
import model.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ContractService extends model.services.Service {
    /**
     * Creates new contract
     *
     * @param contract specified contract
     */
    void create(ContractDto contract) throws BusinessLogicException;

    /**
     * Returns all contracts
     *
     * @return list of contractDto
     */
    List<ContractDto> getAll() throws BusinessLogicException;

    /**
     * Returns contract according to its number
     *
     * @param number specified number
     * @return ContractDto instance
     */
    ContractDto getByNumber(String number) throws BusinessLogicException;

    /**
     * Retrieves contract according to its identifier
     *
     * @param id identifier of contract
     * @return instance of ContractDto
     */
    ContractDto getById(int id) throws BusinessLogicException;

    /**
     * Retrieves a list of all existed tariffs
     *
     * @return list of TariffDto
     */
    List<TariffDto> getTariffList() throws BusinessLogicException;

    /**
     * Retrieves tariff according to its identifier
     *
     * @param id identifier of tariff
     * @return tariff
     */
    TariffDto getTariffById(int id) throws BusinessLogicException;

    /**
     * Updates specified contract
     *
     * @param contract new contract
     */
    void updateContract(ContractDto contract) throws BusinessLogicException;

    /**
     * Inverses status of the supplied contract by manager.
     * If it's blocked, changes status to unblocked.
     * If it's unblocked, changes status ti blocked by manager.
     *
     * @param contract supplied contract
     */
    void inverseBlockStatus(ContractDto contract) throws BusinessLogicException;

    /**
     * Inverses status of the supplied contract by client.
     * If it's blocked by client, changes status to unblocked.
     * If it's unblocked, changes status ti blocked by client.
     *
     * @param contract supplied contract
     */
    void inverseClientBlockStatus(ContractDto contract) throws BusinessLogicException;

    /**
     * Retrieves list of identifiers of all available options for this contract
     *
     * @param contractDto specified contract
     * @return list of identifiers
     */
    List<Integer> getAvailableOptionsIds(ContractDto contractDto) throws BusinessLogicException;

    /**
     * Retrieves list of all available options for this contract
     *
     * @param contractDto specified contract
     * @return list of available additions
     */
    List<AdditionDto> getAvailableOptions(ContractDto contractDto) throws BusinessLogicException;

    /**
     * Retrieves addition with specified identifier
     *
     * @param aId identifier
     * @return addition
     */
    AdditionDto getAdditionById(int aId) throws BusinessLogicException;

    /**
     * Retrieves list of options according to list of identifiers
     *
     * @param optionIds identifiers
     * @return list of chosen options
     */
    List<AdditionDto> getAdditionListByIdList(List<Integer> optionIds) throws BusinessLogicException;

    /**
     * Transfers data from client's basket to DB and then clears the basket
     *
     * @param contractDto contract of client
     * @param basket      client's basket
     */
    void submitBasket(ContractDto contractDto, Basket basket) throws BusinessLogicException;

    /**
     * Returns client according to its id
     *
     * @param id specified id
     * @return ClientDto instance
     */
    ClientDto getClientById(int id) throws BusinessLogicException;

    /**
     * Returns all additions
     *
     * @return list of additions
     */
    List<AdditionDto> getAllAdditions() throws BusinessLogicException;

    /**
     * Deletes contract according to its id
     *
     * @param id specified id
     */
    void deleteById(int id) throws BusinessLogicException;

    /**
     * Returns contract according to its password
     *
     * @param password specified password
     * @return ContractDto instance
     */
    ContractDto getByPassword(String password) throws BusinessLogicException;

    /**
     * Creates new contract for client
     *
     * @param id              specified client
     * @param contractFormDto specified contract
     */
    void addClientContract(int id, ContractFormDto contractFormDto) throws BusinessLogicException;

    /**
     * Updates specified contract
     *
     * @param contractDto specified contract
     */
    void update(ContractDto contractDto) throws BusinessLogicException;

    /**
     * Return set of generated numbers
     *
     * @return set of random numbers
     */
    Set<String> getNumberList() throws BusinessLogicException;
}