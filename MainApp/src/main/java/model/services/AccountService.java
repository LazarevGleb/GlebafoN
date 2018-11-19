package model.services;

import exceptions.BusinessLogicException;
import model.dto.ContractDto;
import model.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface AccountService extends model.services.Service {

    /**
     * Updates specified user
     *
     * @param user
     */
    void update(UserDto user) throws BusinessLogicException;

    /**
     * Returns contract according to its number
     *
     * @param number specified number
     * @return ContractDto instance
     */
    ContractDto getContractByNumber(String number) throws BusinessLogicException;
}
