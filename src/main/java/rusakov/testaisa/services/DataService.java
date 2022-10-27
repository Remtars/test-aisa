package rusakov.testaisa.services;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rusakov.testaisa.controllers.DataController;
import rusakov.testaisa.models.Client;
import rusakov.testaisa.models.Operation;
import rusakov.testaisa.models.Order;
import rusakov.testaisa.objects.OrderObject;
import rusakov.testaisa.objects.OrderResponse;
import rusakov.testaisa.objects.ServiceObject;
import rusakov.testaisa.repositories.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DataService
{
    private static final Logger log = LoggerFactory.getLogger(DataService.class);

    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final ServiceRepository serviceRepository;
    private final OperationRepository operationRepository;

    @Autowired
    public DataService(ClientRepository clientRepository, OrderRepository orderRepository,
                       ServiceRepository serviceRepository, OperationRepository operationRepository)
    {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.serviceRepository = serviceRepository;
        this.operationRepository = operationRepository;
    }

    public ArrayList<Client> getClients()
    {
        return new ArrayList<Client>(this.clientRepository.findAll());
    }

    public OrderResponse addOrder(OrderObject orderObject, Boolean findBestTime)
    {
        if (orderObject.getServices().isEmpty())
        {
            return new OrderResponse("Ошибка записи: Список услуг пустой");
        }
        if (!clientRepository.existsClientByName(orderObject.getUserName()))
        {
            return new OrderResponse("Ошибка записи: Клиента с таким именем не существует");
        }

        try
        {
            OrderResponse response = new OrderResponse();
            Order order = new Order();
            ArrayList<Operation> operations = new ArrayList<>();

            int totalPrice = 0;
            int totalTime = 0;

            for (Integer id : orderObject.getServices())
            {
                if (!serviceRepository.existsById(id))
                {
                    return new OrderResponse("Ошибка: такой услуги не существует");
                }
                rusakov.testaisa.models.Service service = serviceRepository.getServiceById(id);
                totalPrice += service.getPrice();
                totalTime += service.getTimeMinutes();

                Operation operation = new Operation();
                operation.setOrder(order);
                operation.setService(service);
                operations.add(operation);
            }
            order.setFinished(false);
            order.setUserName(orderObject.getUserName());
            order.setPrice(totalPrice);

            response.setPrice(totalPrice);
            response.setTimeWait(totalTime);

            Timestamp currentTime = new Timestamp(new Date().getTime());

            if (orderObject.getTime() != null)
            {
                if (currentTime.after(Timestamp.valueOf(orderObject.getTime())))
                {
                    return new OrderResponse("Ошибка записи: Невозможно записаться в прошлое");
                }
            }

            order.setTimeIssued(currentTime);
            response.setTime(currentTime);

            Timestamp timeStart;
            if (findBestTime || orderObject.getTime() == null)
            {
                timeStart = getAvailableTime(currentTime, totalTime);
            }
            else
            {
                timeStart = Timestamp.valueOf(orderObject.getTime());
            }

            order.setTimeStart(timeStart);
            order.setTimeFinish(new Timestamp(DateUtils.addMinutes(timeStart, totalTime).getTime()));

            int id = orderRepository.save(order).getId();
            response.setId(id);
            response.setMessage("Успешно записано");

            operationRepository.saveAll(operations);

            return response;
        }
        catch (Exception e)
        {
            return new OrderResponse("Ошибка записи");
        }
    }

    public Timestamp getAvailableTime(Timestamp currentTime, Integer lengthMinutes)
    {
        List<Timestamp> starts = orderRepository.getAllTimeStart();
        List<Timestamp> finishes = orderRepository.getAllTimeFinish();

        // Если заказов нет
        if (starts.isEmpty())
        {
            return currentTime;
        }

        // Можно ли разместить заказ до первого
        if (starts.get(0).after(DateUtils.addMinutes(currentTime, lengthMinutes)))
        {
            return currentTime;
        }
        // Если нельзя разместить до первого, и заказ всего один
        if (finishes.size() == 1)
        {
            if (finishes.get(0).after(currentTime))
            {
                return finishes.get(0);
            } else
            {
                return currentTime;
            }
        }
        // Поиск свободного места для записи
        for (int i=1; i<starts.size(); i++)
        {
            if (DateUtils.addMinutes(finishes.get(i-1), lengthMinutes).before(starts.get(i))
                && finishes.get(i-1).after(currentTime))
            {
                return finishes.get(i-1);
            }
        }

        if (finishes.get(finishes.size()-1).after(currentTime))
        {
            return finishes.get(finishes.size()-1);
        } else
        {
            return currentTime;
        }
    }

    public String getTimeWait(Integer id)
    {
        Timestamp currentTime = new Timestamp(new Date().getTime());
        Timestamp orderTime = orderRepository.getTimeStartUsingId(id);

        long differenceInTime = orderTime.getTime() - currentTime.getTime();
        long differenceInSeconds = TimeUnit.MILLISECONDS.toSeconds(differenceInTime) % 60;
        long differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(differenceInTime) % 60;
        long differenceInHours = TimeUnit.MILLISECONDS.toHours(differenceInTime) % 24;
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInTime) % 365;
        long differenceInYears = TimeUnit.MILLISECONDS.toDays(differenceInTime) / 365L;

        StringBuilder sb = new StringBuilder();
        if (differenceInYears > 0)
        {
            sb.append(differenceInYears);
            sb.append(" лет ");
        }
        if (differenceInDays > 0)
        {
            sb.append(differenceInDays);
            sb.append(" дней ");
        }
        if (differenceInHours > 0)
        {
            sb.append(differenceInHours);
            sb.append(" часов ");
        }
        if (differenceInMinutes > 0)
        {
            sb.append(differenceInMinutes);
            sb.append(" минут ");
        }
        if (differenceInSeconds > 0)
        {
            sb.append(differenceInSeconds);
            sb.append(" секунд");
        }

        return sb.toString();
    }

    public String deleteServiceById(Integer id)
    {
        try
        {
            serviceRepository.deleteById(id);
        }
        catch (Exception e)
        {
            log.error("Ошибка удаления услуги: " + e.getMessage());
            return String.format("Ошибка удаления услуги %d", id);
        }
        return String.format("Услуга %d успешно удалена", id);
    }

    public String addService(ServiceObject serviceObject)
    {
        rusakov.testaisa.models.Service service = new rusakov.testaisa.models.Service();
        if (serviceObject.checkNotNull())
        {
            service.setServiceName(serviceObject.getName());
            service.setPrice(serviceObject.getPrice());
            service.setTimeMinutes(serviceObject.getTime());

            serviceRepository.save(service);
            return "Услуга успешно добавлена";
        }
        else return "Ошибка добавления услуги: параметры не должны быть пустыми";
    }
}

