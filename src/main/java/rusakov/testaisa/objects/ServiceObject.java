package rusakov.testaisa.objects;

import io.swagger.v3.oas.annotations.media.Schema;

public class ServiceObject
{
    @Schema(description = "Название новой услуги", type = "String")
    String name;
    @Schema(description = "Стоимость новой услуги", type = "Integer")
    Integer price;
    @Schema(description = "Продолжительность новой услуги в минутах", type = "Integer")
    Integer time;

    public ServiceObject()
    {
    }

    public ServiceObject(String name, Integer price, Integer time)
    {
        this.name = name;
        this.price = price;
        this.time = time;
    }

    public boolean checkNotNull()
    {
        return this.name != null && this.price != null && this.time != null;
    }

    //region Getters & Setters
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getPrice()
    {
        return price;
    }

    public void setPrice(Integer price)
    {
        this.price = price;
    }

    public Integer getTime()
    {
        return time;
    }

    public void setTime(Integer time)
    {
        this.time = time;
    }
    //endregion
}
