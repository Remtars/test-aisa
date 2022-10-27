package rusakov.testaisa.objects;

import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OrderResponse
{
    @Schema(description = "ID добавленного заказа", type = "Integer")
    private int id;
    @Schema(description = "Время добавления заказа", type = "Timestamp")
    private Timestamp time;
    @Schema(description = "Сколько будет длиться оказание услуг, в минутах", type = "Integer")
    private int timeWait;
    @Schema(description = "Стоимость оказания услуг", type = "Integer")
    private int price;
    @Schema(description = "Сообщение о результате выполнения запроса", type = "String")
    private String message;

    public OrderResponse() {}
    public OrderResponse(String message)
    {
        this.message = message;
    }

    //region Getters & Setters

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Timestamp getTime()
    {
        return time;
    }

    public void setTime(Timestamp time)
    {
        this.time = time;
    }

    public int getTimeWait()
    {
        return timeWait;
    }

    public void setTimeWait(int timeWait)
    {
        this.timeWait = timeWait;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    //endregion
}
