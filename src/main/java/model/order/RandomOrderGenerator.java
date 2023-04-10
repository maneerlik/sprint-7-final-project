package model.order;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import model.pojo.Order;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author  smirnov sergey
 * @since   08.04.2023
 */
public class RandomOrderGenerator {

    /**
     * приватный конструктор служебного класса
     */
    private RandomOrderGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static Order randomOrder() {
        Locale locale = new Locale("en-GB");
        Faker faker = new Faker(locale);
        FakeValuesService fakeValuesService = new FakeValuesService(locale, new RandomService());

        // Заполняет список случайным количеством возможных цветов: от 0 до 2
        List<String> color = new ArrayList<>();
        String[] colors = new String[]{"BLACK", "GREY"};
        int count = faker.random().nextInt(3)-1; // генерит число от -1 до 1
        for( ; count > -1; count--) {
            color.add(colors[count]);
        }

        return new Order()
                .setFirstName(faker.name().firstName())
                .setLastName(faker.name().lastName())
                .setAddress(faker.address().fullAddress())
                .setMetroStation(String.valueOf(faker.random().nextInt(201)))
                .setPhone(fakeValuesService.regexify("(\\+7 (800|900|921) \\d{3} \\d{2} \\d{2})"))
                .setRentTime(faker.number().randomDigitNotZero())
                .setDeliveryDate(new SimpleDateFormat("yyyy-MM-dd")
                        .format(faker.date().future(10, TimeUnit.DAYS)))
                .setComment(faker.rickAndMorty().character())
                .setColor(color);
    }

}