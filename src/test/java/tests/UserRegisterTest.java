package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
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
    @Feature("Registration")
    @Severity(value = SeverityLevel.NORMAL)
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
    @Feature("Registration")
    @Severity(value = SeverityLevel.BLOCKER)
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
    @Feature("Registration")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testCreateUserIncorrectEmail() {

        Map<String, String> userData = DataGenerator.getRegistrationDataIncorrectEmail();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertJsonHasNotField(responseCreateAuth, "id");
    }

    @Description("This test checks authorization without a field")
    @DisplayName("Test negative auth user without a field")
    @Feature("Registration")
    @ParameterizedTest
    @ValueSource(strings = {"email", "password", "username", "firstName", "lastName"})
    @Severity(value = SeverityLevel.NORMAL)
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
    @Feature("Registration")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCreateUserShortName() {

        Map<String, String> userData = DataGenerator.getRegistrationDataWithShortName();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertJsonHasNotField(responseCreateAuth, "id");
    }

    @Test
    @Description("This test authorize user with long Name")
    @DisplayName("Test auth user long name")
    @Feature("Registration")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCreateUserLongName() {

        Map<String, String> userData = DataGenerator.getRegistrationDataWithLongName();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertJsonHasNotField(responseCreateAuth, "id");
    }
}
