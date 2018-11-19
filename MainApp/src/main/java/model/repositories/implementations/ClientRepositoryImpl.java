package model.repositories.implementations;

import model.entities.Client;
import model.repositories.AbstractRepository;
import model.repositories.ClientRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class ClientRepositoryImpl extends AbstractRepository<Client> implements ClientRepository {

    public ClientRepositoryImpl() {
        super(Client.class);
    }
}
