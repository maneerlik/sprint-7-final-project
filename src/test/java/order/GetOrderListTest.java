package order;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.order.OrderClient;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * @author  smirnov sergey
 * @since   08.04.2023
 */
public class GetOrderListTest {

    private OrderClient client;

    @Before
    public void setUp() {
        client = new OrderClient();
    }

    @Epic(value = "Заказ")
    @Feature(value = "Получение списка заказов")
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Базовый тест для эндпоинта: /api/v1/orders - получение списка заказов")
    @Severity(SeverityLevel.CRITICAL)
    public void getOrderListTest() {
        ValidatableResponse response = client.orders(Map.of("limit", "5"));
        response.assertThat().body("orders", notNullValue());
    }

}