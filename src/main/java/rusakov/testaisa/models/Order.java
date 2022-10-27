package rusakov.testaisa.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "time_issued")
    private Timestamp timeIssued;

    @Column(name = "time_start")
    private Timestamp timeStart;

    @Column(name = "time_finish")
    private Timestamp timeFinish;

    @Column(name = "is_finished")
    private Boolean finished;

    @Column(name = "price")
    private Integer price;

    @OneToMany(mappedBy = "order")
    List<Operation> operations;

    public Order() {}

    //region Getters & Setters

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public Timestamp getTimeIssued()
    {
        return timeIssued;
    }

    public void setTimeIssued(Timestamp timeIssued)
    {
        this.timeIssued = timeIssued;
    }

    public Timestamp getTimeStart()
    {
        return timeStart;
    }

    public void setTimeStart(Timestamp timeStart)
    {
        this.timeStart = timeStart;
    }

    public Timestamp getTimeFinish()
    {
        return timeFinish;
    }

    public void setTimeFinish(Timestamp timeFinish)
    {
        this.timeFinish = timeFinish;
    }

    public Boolean getFinished()
    {
        return finished;
    }

    public void setFinished(Boolean finished)
    {
        this.finished = finished;
    }

    public Integer getPrice()
    {
        return price;
    }

    public void setPrice(Integer price)
    {
        this.price = price;
    }

    //endregion
}
