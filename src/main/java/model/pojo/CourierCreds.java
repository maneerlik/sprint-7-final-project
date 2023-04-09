package model.pojo;

/**
 * @author  smirnov sergey
 * @since   03.04.2023
 */
public class CourierCreds {

    private final String login;
    private final String password;

    public CourierCreds(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCreds credsFrom(Courier courier) {
        return new CourierCreds(courier.getLogin(), courier.getPassword());
    }

}