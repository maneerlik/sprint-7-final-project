package model.courier;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import model.pojo.Courier;

import java.util.Locale;

/**
 * @author  smirnov sergey
 * @since   04.04.2023
 */
public class RandomCourierGenerator {

    /**
     * приватный конструктор служебного класса
     */
    private RandomCourierGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static Courier randomCourier() {
        Locale locale = new Locale("en-GB");
        Faker faker = new Faker(locale);
        FakeValuesService fakeValuesService = new FakeValuesService(locale, new RandomService());

        return new Courier().setLogin(faker.harryPotter().character())
            .setPassword(fakeValuesService.regexify("[0-9a-zA-Z]{8,}")) // генерит значение по регулярке
            .setFirstName(faker.name().firstName());
    }

}