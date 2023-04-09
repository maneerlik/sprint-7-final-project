<!-- PROJECT LOGO -->
<a name="readme-top"></a>
<div align="center">
   <h3 align="center">Test Scooter API</h3>

   ![REST Assured](rest-assured-logo.png)

   <p align="center">
    Учебный проект по автоматизации тестирования API сервиса аренды самоката. Практика использования библиотек <b>REST Assured</b>, <b>Gson</b> и фреймворка <b>Allure-Framework</b>.
    <br/>

   </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-the-project">About The Project</a></li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#tests">Tests</a></li>
    <li><a href="#tech-stack">Tech Stack</a></li>
  </ol>
</details>

### About The Project

Необходимо написать автотесты для API учебного сервиса «Яндекс.Самокат» (qa-scooter.praktikum-services.ru/docs/). Его разработали специально для студентов.

<b>Требуется проверить:</b>
1. Создание курьера
* курьера можно создать;
* нельзя создать двух одинаковых курьеров;
* чтобы создать курьера, нужно передать в запросе все обязательные поля;
* запрос возвращает правильный код ответа;
* успешный запрос возвращает <b>ok: true</b>;
* если одного из полей нет, запрос возвращает ошибку;
* если создать пользователя с логином, который уже есть, возвращается ошибка.

2. Логин курьера
* курьер может авторизоваться;
* для авторизации нужно передать все обязательные поля;
* система вернёт ошибку, если неправильно указать логин или пароль;
* если какого-то поля нет, запрос возвращает ошибку;
* успешный запрос возвращает <b>id</b>.

3. Создание заказа
* можно указать один из цветов — BLACK или GREY;
* можно указать оба цвета;
* можно совсем не указывать цвет;
* тело ответа содержит <b>track</b>.

4. Список заказов
* в теле ответа возвращается список заказов.
   
5. Отчёт <b>Allure</b>

### Usage

Для формирования отчета необходимо выполнить команды: `mvn clean test` и `mvn allure:serve`. 

### Tests
* `CourierWithoutFieldTest` - создание курьера без обязательных полей;
* `CreateNewCourierTest` - создание курьера;
* `CreateTwoIdenticalCouriersTest` - создание двух одинаковых курьеров;
* `LoginCourierTest` - авторизация курьера;
* `CreateNewOrderTest` - создание заказа;
* `GetOrderListTest` - получение списка заказов.

### Tech Stack

[![Java11][java]][javadoc-url]
[![JUnit4][junit]][junit-url]
[![Allure][Allure]][Allure-url]
[![Gson][Gson]][Gson-url]
[![REST Assured][REST_Assured]][rest-assured-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[javadoc-url]: https://docs.oracle.com/en/java/javase/11/docs/api/index.html
[java]: https://img.shields.io/badge/Java_11-FF2D20?style=for-the-badge&logo=oracle&logoColor=white
[junit-url]: https://junit.org/junit4/
[junit]: https://img.shields.io/badge/JUnit_4-20232A?style=for-the-badge
[Allure-url]: https://docs.qameta.io/allure/
[Allure]: https://img.shields.io/badge/Allure-a5d37e?style=for-the-badge
[rest-assured-url]: https://rest-assured.io/
[REST_Assured]: https://img.shields.io/badge/rest_assured-00bb76?style=for-the-badge
[Gson-url]: https://github.com/google/gson
[Gson]: https://img.shields.io/badge/Gson-4e6f58?style=for-the-badge&logo=google&logoColor=white
