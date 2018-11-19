package model.services;

import exceptions.BusinessLogicException;

public interface SecurityService {
    /**
     * Auto login after registration
     *
     * @param email    email to login
     * @param password password to login
     */
    void autoLogin(String email, String password) throws BusinessLogicException;
}
