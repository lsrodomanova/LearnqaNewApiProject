package tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

public class UserRegisterTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("This test register existing user")
    @DisplayName("Test register existing user")
    public void testCreateUserWithExistingEmail() {
        String email = "vinkotov@example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' already exists");
    }

    @Test
    @Description("This test register user successfully")
    @DisplayName("Test register user")
    public void testCreateUserSuccessfully() {

        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");
    }

    @Test
    @Description("This test does not authorize user with incorrect email")
    @DisplayName("Test negative auth user by email")
    public void testCreateUserIncorrectEmail() {

        Map<String, String> userData = DataGenerator.getRegistrationDataIncorrectEmail();

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertJsonHasNotField(responseCreateAuth, "id");
    }

    @Description("This test checks authorization without a field")
    @DisplayName("Test negative auth user without a field")
    @ParameterizedTest
    @ValueSource(strings = {"email", "password", "username", "firstName", "lastName"})
    public  void testNegativeCreateUser(String condition) {

        if(condition.equals("email")) {
            Response responseForCheck = apiCoreRequests.makePostRequestWithoutEmail(
                    "https://playground.learnqa.ru/api/user/"
            );
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertJsonHasNotField(responseForCheck, "id");
        } else if(condition.equals("password")) {
            Response responseForCheck = apiCoreRequests.makePostRequestWithoutPassword(
                    "https://playground.learnqa.ru/api/user/"
            );
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertJsonHasNotField(responseForCheck, "id");
        } else if(condition.equals("username")) {
            Response responseForCheck = apiCoreRequests.makePostRequestWithoutUserName(
                    "https://playground.learnqa.ru/api/user/"
            );
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertJsonHasNotField(responseForCheck, "id");
        } else if(condition.equals("firstName")) {
            Response responseForCheck = apiCoreRequests.makePostRequestWithoutFirstName(
                    "https://playground.learnqa.ru/api/user/"
            );
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertJsonHasNotField(responseForCheck, "id");
        } else if(condition.equals("lastName")) {
            Response responseForCheck = apiCoreRequests.makePostRequestWithoutLastName(
                    "https://playground.learnqa.ru/api/user/"
            );
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertJsonHasNotField(responseForCheck, "id");
        }
        else {
            throw new IllegalArgumentException("Condition value is not known: " + condition);
        }
    }

    @Test
    @Description("This test authorize user with short name")
    @DisplayName("Test auth user short name")
    public void testCreateUserShortName() {

        Map<String, String> userData = DataGenerator.getRegistrationDataWithShortName();

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertJsonHasNotField(responseCreateAuth, "id");
    }

    @Test
    @Description("This test authorize user with long Name")
    @DisplayName("Test auth user long name")
    public void testCreateUserLongName() {

        Map<String, String> userData = DataGenerator.getRegistrationDataWithLongName();

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertJsonHasNotField(responseCreateAuth, "id");
    }

}
