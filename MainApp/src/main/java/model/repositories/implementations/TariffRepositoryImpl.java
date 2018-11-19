package model.repositories.implementations;

import model.entities.Tariff;
import model.repositories.AbstractRepository;
import model.repositories.TariffRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class TariffRepositoryImpl extends AbstractRepository<Tariff> implements TariffRepository {
    public TariffRepositoryImpl() {
        super(Tariff.class);
    }
}
