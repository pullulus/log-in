package ru.netology.web.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.val;

import static io.restassured.RestAssured.given;

public class DataHelper {
    private DataHelper() {
    }

    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void setUp(RegistrationDto registerDto) {
        given()
                .spec(requestSpec)
                .body(registerDto)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static RegistrationDto registerActiveValidUser() {
        Faker faker = new Faker();
        val activeValidUser = new RegistrationDto(
                faker.name().firstName(),
                faker.internet().password(),
                "active"
        );
        setUp(activeValidUser);
        return activeValidUser;
    }

    public static RegistrationDto registerBlockedUser() {
        Faker faker = new Faker();
        val blockedUser = new RegistrationDto(
                faker.name().firstName(),
                faker.internet().password(),
                "blocked"
        );
        setUp(blockedUser);
        return blockedUser;
    }

    public static RegistrationDto registerUserNonExists() {
        Faker faker = new Faker();
        val userNonExists = new RegistrationDto(
                faker.name().firstName(),
                faker.internet().password(),
                "active"
        );
        return userNonExists;
    }

    public static RegistrationDto registerValidAndNonValidLogin() {
        Faker faker = new Faker();
        val userWithValidLogin = new RegistrationDto(
                faker.name().firstName(),
                faker.internet().password(),
                "active"
        );
        val userWithNonValidLogin = new RegistrationDto(
                faker.name().firstName(),
                faker.internet().password(),
                "active"
        );
        setUp(userWithValidLogin);
        return (userWithNonValidLogin);
    }

    public static RegistrationDto registerValidAndNonValidPassword() {
        Faker faker = new Faker();
        val userWithValidPassword = new RegistrationDto(
                faker.name().firstName(),
                faker.internet().password(),
                "active"
        );
        val userWithNonValidPassword = new RegistrationDto(
                faker.name().firstName(),
                faker.internet().password(),
                "active"
        );
        setUp(userWithValidPassword);
        return (userWithNonValidPassword);
    }
}
