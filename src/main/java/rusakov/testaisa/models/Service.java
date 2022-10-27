package rusakov.testaisa.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "services")
public class Service
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "service")
    private String serviceName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "time_minutes")
    private Integer timeMinutes;

    @OneToMany(mappedBy = "service")
    List<Operation> operations;

    public Service() {}

    //region Getters & Setters

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String service)
    {
        this.serviceName = service;
    }

    public Integer getPrice()
    {
        return price;
    }

    public void setPrice(Integer price)
    {
        this.price = price;
    }

    public Integer getTimeMinutes()
    {
        return timeMinutes;
    }

    public void setTimeMinutes(Integer timeMinutes)
    {
        this.timeMinutes = timeMinutes;
    }

    //endregion
}
