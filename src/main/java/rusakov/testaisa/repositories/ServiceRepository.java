package rusakov.testaisa.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import rusakov.testaisa.models.Service;

import javax.transaction.Transactional;

public interface ServiceRepository extends CrudRepository<Service, Integer>
{
    Service getServiceById(Integer id);
    boolean existsById(Integer id);
    @Transactional
    @Modifying
    void deleteById(Integer id);
}
