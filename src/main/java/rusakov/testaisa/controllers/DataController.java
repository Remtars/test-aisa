package rusakov.testaisa.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rusakov.testaisa.models.Client;
import rusakov.testaisa.objects.OrderObject;
import rusakov.testaisa.objects.OrderResponse;
import rusakov.testaisa.objects.ServiceObject;
import rusakov.testaisa.services.DataService;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/data")
public class DataController
{
    private static final Logger log = LoggerFactory.getLogger(DataController.class);
    private static final String EXAMPLE_ENROLL = "{\n" +
            "  \"userName\": \"test\",\n" +
            "  \"services\": [\n" +
            "    1, 2, 3\n" +
            "  ],\n" +
            "  \"time\": \"31.12.2022 23:01\"\n" +
            "}";
    private static final String EXAMPLE_ENROLL_FREE = "{\n" +
            "  \"userName\": \"test\",\n" +
            "  \"services\": [\n" +
            "    1, 2, 3\n" +
            "  ],\n" +
            "  \"time\": null \n" +
            "}";
    private static final String EXAMPLE_ADD = "{\n" +
            "  \"name\": \"Чистка салона\",\n" +
            "  \"price\": 300,\n" +
            "  \"time\": 25\n" +
            "}";

    private DataService dataService;

    @Autowired
    public DataController(DataService dataService)
    {
        this.dataService = dataService;
    }

    @Operation(description = "Запись на автомойку с выбранными услугами")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class)))
    @PostMapping("/enroll")
    public ResponseEntity<OrderResponse> enroll(
            @Parameter(description = "Объект, описывающий запрос клиента на запись")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Запись с тремя услугами", value = EXAMPLE_ENROLL)}
                    )
            )
            @RequestBody OrderObject orderObject)
    {
        log.info(String.format("/enroll, получен объект: %s", orderObject.toString()));

        OrderResponse response = dataService.addOrder(orderObject, false);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(description = "Запись на автомойку с выбранными услугами на ближайшее свободное время")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class)))
    @PostMapping("/enrollFree")
    public ResponseEntity<OrderResponse> enrollFree(
            @Parameter(description = "Объект, описывающий запрос клиента на запись")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Запись с тремя услугами на свободное время", value = EXAMPLE_ENROLL_FREE)}
                    )
            )
            @RequestBody OrderObject orderObjectFree)
    {
        log.info(String.format("/enrollFree, получен объект: %s", orderObjectFree.toString()));

        OrderResponse response = dataService.addOrder(orderObjectFree, true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(description = "Запрос времени ожидания очереди")
    @GetMapping("/getTimeWait")
    public ResponseEntity<String> getTimeWait(
            @Parameter(description = "Integer, id заказа клиента")
            @RequestParam Integer id)
    {
        String timeWait = dataService.getTimeWait(id);

        return new ResponseEntity<>(timeWait,HttpStatus.OK);
    }

    @Operation(description = "Получить список клиентов")
    @GetMapping("/clients")
    public ArrayList<Client> getClients()
    {
        return dataService.getClients();
    }

    @Operation(description = "Удаление услуги")
    @PostMapping("/deleteService")
    public ResponseEntity<String> deleteService(
            @Parameter(description = "Integer, id удаляемой услуги")
            @RequestParam Integer id)
    {
        String response = dataService.deleteServiceById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(description = "Добавление услуги")
    @PostMapping("/addService")
    public ResponseEntity<String> addService(
            @Parameter(description = "Объект, описывающий запрос на добавление услуги")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Добавление новой услуги", value = EXAMPLE_ADD)}
                    )
            )
            @RequestBody ServiceObject serviceObject)
    {
        String response = dataService.addService(serviceObject);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
