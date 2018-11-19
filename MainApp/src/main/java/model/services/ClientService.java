package model.services;

import exceptions.BusinessLogicException;
import model.dto.AdditionDto;
import model.dto.BigClientDto;
import model.dto.ClientDto;
import model.dto.TariffDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ClientService extends model.services.Service {
    /**
     * Returns client according to its id
     *
     * @param id specified id
     * @return ClientDto instance
     */
    ClientDto getById(Integer id) throws BusinessLogicException;

    /**
     * Creates new clientDto
     *
     * @param client specified client
     */
    void create(ClientDto client) throws BusinessLogicException;

    /**
     * Returns all clients
     *
     * @return list of ClientDto
     */
    List<ClientDto> getAll() throws BusinessLogicException;

    /**
     * Deletes client according to its id
     *
     * @param id specified id
     */
    void deleteById(Integer id) throws BusinessLogicException;

    /**
     * Updates client
     *
     * @param dto specified client
     */
    void update(ClientDto dto) throws BusinessLogicException;

    /**
     * Retrieves a list of all existed tariffs
     *
     * @return list of TariffDto
     */
    List<TariffDto> getTariffList() throws BusinessLogicException;

    /**
     * Retrieves a list of all existed options
     *
     * @return list of AdditionDto
     */
    List<AdditionDto> getAdditionList() throws BusinessLogicException;

    /**
     * Build ClientDto instance from BigClient that contains a lot of waste data
     *
     * @param bigClient instance of BigClient that contains client, contract number,
     *                  tariff and options info
     */
    void createFromBigClient(BigClientDto bigClient) throws BusinessLogicException;

    /**
     * Returns generated set of numbers
     *
     * @return set of numbers
     */
    Set<String> getNumberList() throws BusinessLogicException;
}
