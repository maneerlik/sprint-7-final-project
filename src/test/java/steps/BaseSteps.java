package steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

/**
 * Реализация универсальных шагов
 *
 * @author  smirnov sergey
 * @since   10.04.2023
 */
public class BaseSteps {

    @Step("Статус код ответа: {status}")
    public static void checkRespStatusCode(ValidatableResponse response, int status) {
        assertEquals("Статус код неверный", status, response.extract().statusCode());
    }

    @Step("Проверка ответа. Элемент {element} не пустой")
    public static void checkRespBodyElementIsNotNull(ValidatableResponse response, String element) {
        response.assertThat().body(element, notNullValue());
    }

    @Step("Проверка ответа. {message}")
    public static void checkRespBodyMessage(ValidatableResponse response, String message) {
        response.assertThat().body("message", equalTo(message));
    }

    @Step("Проверка ответа. {message}")
    public static void checkRespTrue(ValidatableResponse response, Boolean message) {
        response.assertThat().body("ok", equalTo(message));
    }

}
