package rusakov.testaisa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import rusakov.testaisa.models.Order;

import java.sql.Timestamp;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer>
{
    @Query("SELECT o.timeStart FROM Order o")
    List<Timestamp> getAllTimeStart();

    @Query("SELECT o.timeFinish FROM Order o")
    List<Timestamp> getAllTimeFinish();

    @Query("SELECT o.timeStart FROM Order o WHERE o.id = ?1")
    Timestamp getTimeStartUsingId(Integer id);
}
