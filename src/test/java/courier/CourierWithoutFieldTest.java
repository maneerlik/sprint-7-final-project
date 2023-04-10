package courier;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.courier.CourierClient;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static steps.BaseSteps.checkRespBodyMessage;
import static steps.BaseSteps.checkRespStatusCode;

/**
 * @author  smirnov sergey
 * @since   05.04.2023
 */
@RunWith(Parameterized.class)
public class CourierWithoutFieldTest {

    private final String reqJSON;
    private final String missingField;
    private final CourierClient client;

    /**
     * конструктор
     *
     * @param missingField   наименование отсутствующего поля.
     */
    public CourierWithoutFieldTest(String reqJSON, String missingField) {
        this.reqJSON = reqJSON;
        this.missingField = missingField;
        client = new CourierClient();
    }

    @Parameterized.Parameters(name = "{index}: without {1}")
    public static Object[][] data() {
        return new Object[][] {
            { "{\"password\":\"some_password\",\"firstName\":\"CourierWithoutLogin\"}", "login" },
            { "{\"login\":\"some_login\",\"firstName\":\"CourierWithoutPassword\"}", "password" }
        };
    }

    @Epic(value = "Курьер")
    @Feature(value = "Создание курьера")
    @Test
    @DisplayName("Создание курьера с отсутствующим обязательным полем")
    @Description("Попытка создать курьера с отсутствующим полем: login/password")
    @Severity(SeverityLevel.CRITICAL)
    public void сreateCourierWithoutFieldTest() {
        ValidatableResponse response = client.create(reqJSON, missingField);
        checkRespStatusCode(response, HttpStatus.SC_BAD_REQUEST);
        checkRespBodyMessage(response, "Недостаточно данных для создания учетной записи");
    }

    @Epic(value = "Курьер")
    @Feature(value = "Создание курьера")
    @Test
    @DisplayName("Авторизация курьера с отсутствующим обязательным полем")
    @Description("Попытка авторизоваться курьером с отсутствующим полем: login/password")
    @Severity(SeverityLevel.CRITICAL)
    public void loginCourierWithoutFieldTest() {
        ValidatableResponse response = client.login(reqJSON, missingField);
        checkRespStatusCode(response, HttpStatus.SC_BAD_REQUEST);
        checkRespBodyMessage(response, "Недостаточно данных для входа");
    }

}