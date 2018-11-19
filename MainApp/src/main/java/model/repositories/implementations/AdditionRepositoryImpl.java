package model.repositories.implementations;

import model.entities.Addition;
import model.repositories.AbstractRepository;
import model.repositories.AdditionRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class AdditionRepositoryImpl extends AbstractRepository<Addition> implements AdditionRepository {
    public AdditionRepositoryImpl() {
        super(Addition.class);
    }
}
