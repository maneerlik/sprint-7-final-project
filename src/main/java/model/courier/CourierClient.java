package model.courier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import model.pojo.Courier;
import model.pojo.CourierCreds;

import java.io.File;

import static io.restassured.RestAssured.given;
import static model.api.Endpoints.*;

/**
 * @author  smirnov sergey
 * @since   05.04.2023
 */
public class CourierClient {

    // спецификация запроса
    private RequestSpecification reqSpec() {
        return given().log().all()
            .contentType(ContentType.JSON)
            .baseUri(BASE_URI.getEndpoint());
    }

    // создание курьера
    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return reqSpec().body(courier)
            .post(CREATE_COURIER.getEndpoint()).then();
    }

    // создание курьера без обязательного поля
    @Step("Попытка создать курьера без поля {missingField}")
    public ValidatableResponse create(String jsonReq, String missingField) {
        return reqSpec().body(jsonReq)
                .post(CREATE_COURIER.getEndpoint()).then();
    }

    // авторизация курьера
    @Step("Авторизация курьера")
    public ValidatableResponse login(CourierCreds creds) {
        return reqSpec().body(creds)
            .post(LOGIN_COURIER.getEndpoint()).then();
    }

    // авторизация курьера без обязательного поля
    @Step("Попытка авторизоваться курьером без поля {missingField}")
    public ValidatableResponse login(String jsonReq, String missingField) {
        return reqSpec().body(jsonReq)
                .post(LOGIN_COURIER.getEndpoint()).then();
    }

    // удаление курьера
    @Step("Удаление курьера")
    public void delete(String courierId) {
        reqSpec().delete(DELETE_COURIER.getEndpoint() + courierId).then();
    }

}