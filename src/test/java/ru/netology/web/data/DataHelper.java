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

    private static Faker faker = new Faker();

    public static RegistrationDto registerActiveValidUser() {
        val activeValidUser = new RegistrationDto(
                faker.name().firstName(),
                faker.internet().password(),
                "active"
        );
        setUp(activeValidUser);
        return activeValidUser;
    }

    public static RegistrationDto registerBlockedUser() {
        val blockedUser = new RegistrationDto(
                faker.name().firstName(),
                faker.internet().password(),
                "blocked"
        );
        setUp(blockedUser);
        return blockedUser;
    }

    public static RegistrationDto registerUserNonExists() {
        val userNonExists = new RegistrationDto(
                faker.name().firstName(),
                faker.internet().password(),
                "active"
        );
        return userNonExists;
    }

    public static RegistrationDto registerUserWithNonValidLogin() {
        String password = faker.internet().password();
        setUp(new RegistrationDto(faker.name().firstName(), password, "active"));
        return (new RegistrationDto(faker.name().firstName(), password, "active"));
    }

    public static RegistrationDto registerUserWithNonValidPassword() {
        String login = faker.name().firstName();
        setUp(new RegistrationDto(login, faker.internet().password(), "active"));
        return (new RegistrationDto(login, faker.internet().password(), "active"));
    }
}
