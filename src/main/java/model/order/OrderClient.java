package model.order;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import model.pojo.Order;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static model.api.Endpoints.*;

/**
 * @author  smirnov sergey
 * @since   08.04.2023
 */
public class OrderClient {

    // спецификация запроса
    private RequestSpecification reqSpec() {
        return given().log().all()
            .contentType(ContentType.JSON)
            .baseUri(BASE_URI.getEndpoint());
    }

    // создание заказа
    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return reqSpec().body(order)
            .post(CREATE_ORDER.getEndpoint()).then();
    }

    // получение списка заказов параметры запроса: int courierId, String nearestStationJSON, int limit, int page
    @Step("Получение списка заказов")
    public ValidatableResponse orders(Map<String, String> queryParam) {
        return reqSpec()
            .queryParam("courierId", queryParam.getOrDefault("courierId", ""))
            .queryParam("nearestStation", queryParam.getOrDefault("nearestStation", ""))
            .queryParam("limit", queryParam.getOrDefault("limit", ""))
            .queryParam("page", queryParam.getOrDefault("page", ""))
            .get(GET_ORDER_LIST.getEndpoint())
            .then();
    }

}