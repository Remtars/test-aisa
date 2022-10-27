package rusakov.testaisa.objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public class OrderObject
{
    @Schema(description = "Имя пользователя", type = "String")
    String userName;
    @Schema(description = "Список id услуг", type = "Integer[]")
    List<Integer> services;
    @Schema(description = "Время, на которое записан пользователь", format = "dd.MM.yyyy HH:mm")
    @JsonDeserialize(using=LocalDateTimeDeserializer.class)
    LocalDateTime time;

    public OrderObject() {}

    public OrderObject(String userName, List<Integer> services, LocalDateTime time)
    {
        this.userName = userName;
        this.services = services;
        this.time = time;
    }

    //region Getters & Setters

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public List<Integer> getServices()
    {
        return services;
    }

    public void setServices(List<Integer> services)
    {
        this.services = services;
    }

    public LocalDateTime getTime()
    {
        return time;
    }

    public void setTime(LocalDateTime time)
    {
        this.time = time;
    }

    //endregion


    @Override
    public String toString()
    {
        return "OrderObject{" +
                "userName='" + userName + '\'' +
                ", services=" + services +
                ", time=" + time +
                '}';
    }
}
