package model.repositories.implementations;

import model.entities.Contract;
import model.repositories.AbstractRepository;
import model.repositories.ContractRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class ContractRepositoryImpl extends AbstractRepository<Contract> implements ContractRepository {

    public ContractRepositoryImpl() {
        super(Contract.class);
    }
}
