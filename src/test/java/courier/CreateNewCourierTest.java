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
import static steps.BaseSteps.checkRespStatusCode;
import static steps.BaseSteps.checkRespTrue;

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
        checkRespStatusCode(response, HttpStatus.SC_CREATED);
        checkRespTrue(response, true);
    }

    @After
    public void deleteCourier() {
        ValidatableResponse response = client.login(credsFrom(courier));
        client.delete(response.extract().path("id").toString());
    }

}