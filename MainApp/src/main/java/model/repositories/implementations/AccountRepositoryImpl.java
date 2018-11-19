package model.repositories.implementations;

import model.entities.Account;
import model.repositories.AbstractRepository;
import model.repositories.AccountRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryImpl extends AbstractRepository<Account> implements AccountRepository {
    public AccountRepositoryImpl() {
        super(Account.class);
    }
}
