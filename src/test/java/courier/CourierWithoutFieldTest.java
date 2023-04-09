package courier;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;

import static io.restassured.RestAssured.given;
import static model.api.Endpoints.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author  smirnov sergey
 * @since   05.04.2023
 */
@RunWith(Parameterized.class)
public class CourierWithoutFieldTest {

    private final File incorrectCourierJSON;
    private final String missingField;

    /**
     * конструктор
     *
     * @param incorrectCourierJSON   JSON файл запроса на создание курьера.
     */
    public CourierWithoutFieldTest(File incorrectCourierJSON, String missingField) {
        this.incorrectCourierJSON = incorrectCourierJSON;
        this.missingField = missingField;
        RestAssured.baseURI = BASE_URI.getEndpoint();
    }

    @Parameterized.Parameters(name = "{index}: without {1}")
    public static Object[][] data() {
        return new Object[][] {
            { new File("src/test/resources/courierWithoutLogin.json"), "login" },
            { new File("src/test/resources/courierWithoutPassword.json"), "password" }
        };
    }

    @Epic(value = "Курьер")
    @Feature(value = "Создание курьера")
    @Test
    @DisplayName("Создание курьера с отсутствующим обязательным полем")
    @Description("Попытка создать курьера с отсутствующим полем: login/password")
    @Severity(SeverityLevel.CRITICAL)
    public void сreateCourierWithoutFieldTest() {
        ValidatableResponse response = createCourierWithoutField(missingField);
        checkRespStatusCode(response);
        checkErrCreateRespBody(response);
    }

    @Epic(value = "Курьер")
    @Feature(value = "Создание курьера")
    @Test
    @DisplayName("Авторизация курьера с отсутствующим обязательным полем")
    @Description("Попытка авторизоваться курьером с отсутствующим полем: login/password")
    @Severity(SeverityLevel.CRITICAL)
    public void loginCourierWithoutFieldTest() {
        ValidatableResponse response = loginCourierWithoutField(missingField);
        checkRespStatusCode(response);
        checkErrLoginRespBody(response);
    }

    @Step("Создать курьера без поля {fieldName} не удалось")
    public ValidatableResponse createCourierWithoutField(String fieldName) {
         return given().header("Content-type", "application/json")
                .body(incorrectCourierJSON)
                .post(CREATE_COURIER.getEndpoint())
                .then()
                .log()
                .status()
                .log()
                .body();
    }

    @Step("Авторизоваться курьером без поля {fieldName} не удалось")
    public ValidatableResponse loginCourierWithoutField(String fieldName) {
        return given().header("Content-type", "application/json")
                .body(incorrectCourierJSON)
                .post(LOGIN_COURIER.getEndpoint())
                .then()
                .log()
                .status()
                .log()
                .body();
    }

    @Step("Статус код ответа: 400")
    public void checkRespStatusCode(ValidatableResponse response) {
        assertEquals("Статус код неверный", HttpStatus.SC_BAD_REQUEST, response.extract().statusCode());
    }

    @Step("Недостаточно данных для создания учетной записи. Курьер не создан")
    public void checkErrCreateRespBody(ValidatableResponse response) {
        assertThat("Ответ не корректный",
                "{\"code\":400,\"message\":\"Недостаточно данных для создания учетной записи\"}",
                is(response.extract().asString()));
    }

    @Step("Недостаточно данных для входа. Авторизация не выполнена")
    public void checkErrLoginRespBody(ValidatableResponse response) {
        assertThat("Ответ не корректный",
                "{\"code\":400,\"message\":\"Недостаточно данных для входа\"}",
                is(response.extract().asString()));
    }

}