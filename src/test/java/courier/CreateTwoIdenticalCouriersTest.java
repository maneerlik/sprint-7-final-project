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
 * @since   05.04.2023
 */
public class CreateTwoIdenticalCouriersTest {

    private Courier courier;
    private CourierClient client; // утилитарный объект для работы с рестами курьера

    @Before
    public void setUp() {
        courier = randomCourier();
        client = new CourierClient();
        Logger log = Logger.getLogger(CreateTwoIdenticalCouriersTest.class.getName());
        log.info(new GsonBuilder().setPrettyPrinting().create().toJson(courier));
    }

    @Epic(value = "Курьер")
    @Feature(value = "Создание курьера")
    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Попытка создать двух курьеров с одинаковыми учетными данными")
    @Severity(SeverityLevel.CRITICAL)
    public void createTwoIdenticalCouriersTest() {
        ValidatableResponse createFirstCourierResp = createCourier(courier);
        checkCreateRespStatusCode(createFirstCourierResp);
        ValidatableResponse createSecondCourierResp = createCourier(courier);
        checkErrRespStatusCode(createSecondCourierResp);
        checkResponseBody(createSecondCourierResp);
    }

    @Step("Cоздание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return client.create(courier);
    }

    @Step("Статус код ответа: 201. Курьер создан")
    public void checkCreateRespStatusCode(ValidatableResponse response) {
        assertEquals("Статус код неверный", HttpStatus.SC_CREATED, response.extract().statusCode());
    }

    @Step("Статус код ответа: 409. Логин уже используется")
    public void checkErrRespStatusCode(ValidatableResponse response) {
        assertEquals("Статус код неверный", HttpStatus.SC_CONFLICT, response.extract().statusCode());
    }

    @Step("Проверка ответа. Логин уже используется")
    public void checkResponseBody(ValidatableResponse response) {
        assertThat("Ответ не корректный",
                "{\"code\":409,\"message\":\"Этот логин уже используется. Попробуйте другой.\"}",
                is(response.extract().asString()));
    }

    @After
    public void deleteCourier() {
        ValidatableResponse loginCourierResp = client.login(credsFrom(courier));
        client.delete(loginCourierResp.extract().path("id").toString());
    }

}