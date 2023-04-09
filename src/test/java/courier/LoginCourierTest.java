package courier;

import com.google.gson.GsonBuilder;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.courier.CourierClient;
import model.pojo.Courier;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Logger;

import static model.courier.RandomCourierGenerator.randomCourier;
import static model.pojo.CourierCreds.credsFrom;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

/**
 * @author  smirnov sergey
 * @since   08.04.2023
 */
public class LoginCourierTest {

    private Courier courier;
    private CourierClient client; // утилитарный объект для работы с рестами курьера

    @Before
    public void setUp() {
        courier = randomCourier();
        client = new CourierClient();
        Logger log = Logger.getLogger(LoginCourierTest.class.getName());
        log.info(new GsonBuilder().setPrettyPrinting().create().toJson(courier));
    }

    @Epic(value = "Курьер")
    @Feature(value = "Авторизация курьера")
    @Test
    @DisplayName("Авторизация существующего курьера")
    @Description("Базовый тест для эндпоинта: /api/v1/courier/login - авторизация существующего курьера")
    @Severity(SeverityLevel.CRITICAL)
    public void loginCourierTest() {
        client.create(courier);
        ValidatableResponse response = loginCourier(courier);
        checkLoginRespOKStatusCode(response);
        checkIdRespBody(response);
        deleteCourier(response);
    }

    @Epic(value = "Курьер")
    @Feature(value = "Авторизация курьера")
    @Test
    @DisplayName("Авторизация несуществующего курьера")
    @Description("Базовый тест для эндпоинта: /api/v1/courier/login - авторизация несуществующего курьера")
    @Severity(SeverityLevel.CRITICAL)
    public void loginVoidCourierTest() {
        ValidatableResponse response = loginCourier(courier);
        checkLoginRespNOTFStatusCode(response);
        checkNOTFRespBody(response);
    }

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(Courier courier) {
        return client.login(credsFrom(courier));
    }

    @Step("Статус код ответа: 200. Курьер авторизован")
    public void checkLoginRespOKStatusCode(ValidatableResponse response) {
        assertEquals("Статус код неверный", HttpStatus.SC_OK, response.extract().statusCode());
    }

    @Step("Статус код ответа: 404. Курьер не существует")
    public void checkLoginRespNOTFStatusCode(ValidatableResponse response) {
        assertEquals("Статус код неверный", HttpStatus.SC_NOT_FOUND, response.extract().statusCode());
    }

    @Step("Проверка ответа. Курьер авторизован")
    public void checkIdRespBody(ValidatableResponse response) {
        response.assertThat().body("id", notNullValue());
    }

    @Step("Проверка ответа. Учетная запись не найдена")
    public void checkNOTFRespBody(ValidatableResponse response) {
        assertThat("Ответ не корректный",
                "{\"code\":404,\"message\":\"Учетная запись не найдена\"}", is(response.extract().asString()));
    }

    @Step
    public void deleteCourier(ValidatableResponse response) {
        client.delete(response.extract().path("id").toString());
    }

}