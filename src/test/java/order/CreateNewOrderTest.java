package order;

import com.google.gson.GsonBuilder;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.order.OrderClient;
import model.pojo.Order;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Logger;

import static model.order.RandomOrderGenerator.randomOrder;
import static steps.BaseSteps.checkRespBodyElementIsNotNull;
import static steps.BaseSteps.checkRespStatusCode;

/**
 * @author  smirnov sergey
 * @since   08.04.2023
 */
public class CreateNewOrderTest {

    private Order order;
    private OrderClient client;

    @Before
    public void setUp() {
        order = randomOrder();
        client = new OrderClient();
        Logger log = Logger.getLogger(CreateNewOrderTest.class.getName());
        log.info(new GsonBuilder().setPrettyPrinting().create().toJson(order));
    }

    @Epic(value = "Заказ")
    @Feature(value = "Создание заказа")
    @Test
    @DisplayName("Создание заказа")
    @Description("Базовый тест для эндпоинта: /api/v1/orders - создание заказа")
    @Severity(SeverityLevel.CRITICAL)
    public void createOrderTest() {
        ValidatableResponse response = client.create(order);
        checkRespStatusCode(response, HttpStatus.SC_CREATED);
        checkRespBodyElementIsNotNull(response, "track");
    }

}