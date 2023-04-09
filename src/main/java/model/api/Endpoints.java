package model.api;

/**
 * @author  smirnov sergey
 * @since   03.04.2023
 */
public enum Endpoints {

    BASE_URI("http://qa-scooter.praktikum-services.ru/"),
    CREATE_COURIER("/api/v1/courier"),
    LOGIN_COURIER("/api/v1/courier/login"),
    DELETE_COURIER("/api/v1/courier"),
    CREATE_ORDER("/api/v1/orders"),
    GET_ORDER_LIST("/api/v1/orders");

    private String endpoint;

    Endpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

}