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

import static model.courier.RandomCourierGenerator.randomCourier;
import static model.pojo.CourierCreds.credsFrom;
import static steps.BaseSteps.*;

/**
 * @author  smirnov sergey
 * @since   08.04.2023
 */
public class LoginCourierTest {

    private Courier courier;
    private CourierClient client; // утилитарный объект для работы с рестами курьера
    private String courierId;

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
    @DisplayName("Авторизация несуществующего курьера")
    @Description("Базовый тест для эндпоинта: /api/v1/courier/login - авторизация несуществующего курьера")
    @Severity(SeverityLevel.CRITICAL)
    public void loginVoidCourierTest() {
        ValidatableResponse response = client.login(credsFrom(courier));
        checkRespStatusCode(response, HttpStatus.SC_NOT_FOUND);
        checkRespBodyMessage(response, "Учетная запись не найдена");
    }

    @Epic(value = "Курьер")
    @Feature(value = "Авторизация курьера")
    @Test
    @DisplayName("Авторизация существующего курьера")
    @Description("Базовый тест для эндпоинта: /api/v1/courier/login - авторизация существующего курьера")
    @Severity(SeverityLevel.CRITICAL)
    public void loginCourierTest() {
        client.create(courier); // создаем курьера
        ValidatableResponse response = client.login(credsFrom(courier));
        checkRespStatusCode(response, HttpStatus.SC_OK);
        checkRespBodyElementIsNotNull(response, "id");
        courierId = response.extract().path("id").toString();
    }

    @After
    public void deleteCourier() {
        if(courierId != null) client.delete(courierId);
    }

}