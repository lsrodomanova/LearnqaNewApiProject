package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserDeleteTest extends BaseTestCase {

    String cookie;
    String header;
    int userIdOnAuth;
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("This test try to delete user with id 2")
    @DisplayName("Test negative delete user with id 2")
    public void testDeleteUserAuthAsSameUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth  = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseGetAuth,"user_id");

        Response responseUserDelete = apiCoreRequests
                .makeDeleteRequest("https://playground.learnqa.ru/api/user/2", this.header, this.cookie);

        Assertions.assertResponseCodeEquals(responseUserDelete,400);

        Response responseGetNewAuth  = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        Assertions.assertResponseCodeEquals(responseGetNewAuth,200);
        Assertions.assertJsonHasField(responseGetNewAuth, "user_id");
        Assertions.assertJsonByName(responseGetNewAuth,"user_id", 2);
    }

    @Test
    @Description("This test delete user")
    @DisplayName("Test positive delete user")
    public void testDeleteUser() {
        //Generate user
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);


        //Login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseGetAuth,"user_id");

        //Delete

        Response responseDeleteUser = apiCoreRequests
                .makeDeleteRequest("https://playground.learnqa.ru/api/user/" + userIdOnAuth, this.header, this.cookie);
        Assertions.assertResponseCodeEquals(responseDeleteUser, 200);

        //Get
        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userIdOnAuth, this.header, this.cookie);
        Assertions.assertResponseCodeEquals(responseUserData, 404);
        Assertions.assertJsonHasNotField(responseUserData, "user_id");
    }

    @Test
    @Description("This test try to delete other user")
    @DisplayName("Test negative delete other user")
    public void testEditJustCreatedTest() {
        //Generate user
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        int userId = this.getIntFromJson(responseCreateAuth,"id");
        String name = userData.get("username");
        System.out.println(userId);

        //Login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");

        //Delete

        Response responseDeleteUser = apiCoreRequests
                .makeDeleteRequest("https://playground.learnqa.ru/api/user/" + userIdOnAuth, this.header, this.cookie);
        Assertions.assertResponseCodeEquals(responseDeleteUser, 400);

        //Get
        Response responseUserData = apiCoreRequests
                .makeGetRequestWithoutTokenCookie("https://playground.learnqa.ru/api/user/" + userId);
        Assertions.assertResponseCodeEquals(responseUserData, 200);
        Assertions.assertJsonByName(responseUserData, "username",name);
    }
}
