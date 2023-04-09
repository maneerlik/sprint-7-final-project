package courier;

import com.google.gson.GsonBuilder;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.courier.CourierClient;
import model.pojo.Courier;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Logger;

import static model.pojo.CourierCreds.credsFrom;
import static model.courier.RandomCourierGenerator.randomCourier;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author  smirnov sergey
 * @since   03.04.2023
 */
public class CreateNewCourierTest {

    private Courier courier;
    private CourierClient client; // утилитарный объект для работы с рестами курьера

    @Before
    public void setUp() {
        courier = randomCourier();
        client = new CourierClient();
        Logger log = Logger.getLogger(CreateNewCourierTest.class.getName());
        log.info(new GsonBuilder().setPrettyPrinting().create().toJson(courier));
    }

    @Epic(value = "Курьер")
    @Feature(value = "Создание курьера")
    @Test
    @DisplayName("Создание нового курьера")
    @Description("Базовый тест для эндпоинта: /v1/courier - создание курьера")
    @Severity(SeverityLevel.CRITICAL)
    public void сreateNewCourierTest() {
        ValidatableResponse response = client.create(courier);
        checkRespStatusCode(response);
        checkCreateRespBody(response);
    }

    @Step("Статус код ответа: 201. Курьер создан")
    public void checkRespStatusCode(ValidatableResponse response) {
        assertEquals("Статус код неверный", HttpStatus.SC_CREATED, response.extract().statusCode());
    }

    @Step("Проверка ответа. Курьер создан")
    public void checkCreateRespBody(ValidatableResponse response) {
        assertThat("Ответ не корректный", "{\"ok\":true}", is(response.extract().asString()));
    }

    @After
    public void deleteCourier() {
        ValidatableResponse loginCourierResp = client.login(credsFrom(courier));
        client.delete(loginCourierResp.extract().path("id").toString());
    }

}