package rusakov.testaisa.repositories;

import org.springframework.data.repository.CrudRepository;
import rusakov.testaisa.models.Operation;

public interface OperationRepository extends CrudRepository<Operation, Integer>
{
}
