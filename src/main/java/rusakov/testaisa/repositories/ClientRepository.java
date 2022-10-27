package rusakov.testaisa.repositories;

import org.springframework.data.repository.CrudRepository;
import rusakov.testaisa.models.Client;

import java.util.ArrayList;

public interface ClientRepository extends CrudRepository<Client, Integer>
{
    boolean existsByNameAndPassword(String name, String password);
    ArrayList<Client> findAll();
    Boolean existsClientByName(String name);
}
