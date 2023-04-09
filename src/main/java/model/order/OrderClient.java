package model.order;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.pojo.Order;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static model.api.Endpoints.*;

/**
 * @author  smirnov sergey
 * @since   08.04.2023
 */
public class OrderClient {

    public OrderClient() {
        RestAssured.baseURI = BASE_URI.getEndpoint();
    }

    // создание заказа
    public ValidatableResponse create(Order order) {
        return given().header("Content-type", "application/json")
                .body(order)
                .post(CREATE_ORDER.getEndpoint())
                .then()
                .log()
                .status()
                .log()
                .body();
    }

    // получение списка заказов int courierId, String nearestStationJSON, int limit, int page
    public ValidatableResponse orders(Map<String, String> queryParam) {
        return given().header("Content-type", "application/json")
                .queryParam("courierId", queryParam.getOrDefault("courierId", ""))
                .queryParam("nearestStation", queryParam.getOrDefault("nearestStation", ""))
                .queryParam("limit", queryParam.getOrDefault("limit", ""))
                .queryParam("page", queryParam.getOrDefault("page", ""))
                .get(GET_ORDER_LIST.getEndpoint())
                .then()
                .log()
                .status()
                .log()
                .body();
    }

}