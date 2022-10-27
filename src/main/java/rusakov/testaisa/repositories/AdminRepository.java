package rusakov.testaisa.repositories;

import org.springframework.data.repository.CrudRepository;
import rusakov.testaisa.models.Admin;

public interface AdminRepository extends CrudRepository<Admin, Integer>
{
    boolean existsByNameAndPassword(String name, String password);
}
