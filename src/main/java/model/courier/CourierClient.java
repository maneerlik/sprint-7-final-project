package model.courier;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.pojo.Courier;
import model.pojo.CourierCreds;

import static io.restassured.RestAssured.given;
import static model.api.Endpoints.*;

/**
 * @author  smirnov sergey
 * @since   05.04.2023
 */
public class CourierClient {

    public CourierClient() {
        RestAssured.baseURI = BASE_URI.getEndpoint();
    }

    // создание курьера
    public ValidatableResponse create(Courier courier) {
        return given().header("Content-type", "application/json")
            .body(courier)
            .post(CREATE_COURIER.getEndpoint())
            .then()
            .log()
            .status()
            .log()
            .body();
    }

    // аутентификация курьера
    public ValidatableResponse login(CourierCreds creds) {
        return given().header("Content-type", "application/json")
            .body(creds)
            .post(LOGIN_COURIER.getEndpoint())
            .then()
            .log()
            .status()
            .log()
            .body();
    }

    // удаление курьера
    public void delete(String courierId) {
        given().header("Content-type", "application/json")
            .delete(DELETE_COURIER.getEndpoint() + courierId)
            .then()
            .log()
            .status()
            .log()
            .body();
    }

}