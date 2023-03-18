package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequests {

    @Step("Make a GET request with token and auth cookie")
    public Response makeGetRequest(String url, String token, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Make a GET request with auth cookie only")
    public Response makeGetRequestWithCookie(String url,String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Make a GET request with token only")
    public Response makeGetRequestWithToken(String url, String token) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .get(url)
                .andReturn();
    }

    @Step("Make a POST request")
    public Response makePostRequest(String url, Map<String,String> authData) {
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .post(url)
                .andReturn();
    }

    @Step("Make a POST request without email")
    public Response makePostRequestWithoutEmail(String url) {
        Map<String, String> wrongData = DataGenerator.getRegistrationDataWithoutEmail();
        return given()
                .filter(new AllureRestAssured())
                .body(wrongData)
                .post(url)
                .andReturn();
    }

    @Step("Make a POST request without password")
    public Response makePostRequestWithoutPassword(String url) {
        Map<String, String> wrongData = DataGenerator.getRegistrationDataWithoutPassword();
        return given()
                .filter(new AllureRestAssured())
                .body(wrongData)
                .post(url)
                .andReturn();
    }

    @Step("Make a POST request without username")
    public Response makePostRequestWithoutUserName(String url) {
        Map<String, String> wrongData = DataGenerator.getRegistrationDataWithoutUserName();
        return given()
                .filter(new AllureRestAssured())
                .body(wrongData)
                .post(url)
                .andReturn();
    }

    @Step("Make a POST request without firstName")
    public Response makePostRequestWithoutFirstName(String url) {
        Map<String, String> wrongData = DataGenerator.getRegistrationDataWithoutFirstName();
        return given()
                .filter(new AllureRestAssured())
                .body(wrongData)
                .post(url)
                .andReturn();
    }

    @Step("Make a POST request without lastName")
    public Response makePostRequestWithoutLastName(String url) {
        Map<String, String> wrongData = DataGenerator.getRegistrationDataWithoutLastName();
        return given()
                .filter(new AllureRestAssured())
                .body(wrongData)
                .post(url)
                .andReturn();
    }
}
