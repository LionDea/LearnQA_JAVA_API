package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("Authorization cases")
@Feature("Delete user")

public class UserDeleteTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Description("Could not delete user with ID 2")
    @DisplayName("Delete user 2 fail")
    @Test
    public void testDeleteUser2() {

        //Generate User
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .makePostForGeneration("https://playground.learnqa.ru/api/user", userData);
        String userId = responseCreateAuth.getString("id");

        //Login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //Delete
        Response responseDeleteUser = apiCoreRequests
                .makeDeleteRequestWithAuth("https://playground.learnqa.ru/api/user/2", this.getHeader(responseGetAuth, "x-csrf-token"), this.getCookie(responseGetAuth, "auth_sid"), authData);

        Assertions.assertResponseTextEquals(responseDeleteUser, "Please, do not delete test users with ID 1, 2, 3, 4 or 5.");

        //Check result
        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/2", this.getHeader(responseGetAuth, "x-csrf-token"), this.getCookie(responseGetAuth, "auth_sid"));

        String[] expectedFields = {"id", "username", "firstName", "lastName", "email"};
        Assertions.assertJsonHasFields(responseUserData, expectedFields);
        Assertions.assertResponseCodeEquals(responseUserData, 200);

    }

    @Description("Successful delete user")
    @DisplayName("Delete user positive")
    @Test
    public void testDeleteUserPositive() {

        //Generate User
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .makePostForGeneration("https://playground.learnqa.ru/api/user", userData);
        String userId = responseCreateAuth.getString("id");

        //Login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //Delete
        Response responseDeleteUser = apiCoreRequests
                .makeDeleteRequestWithAuth("https://playground.learnqa.ru/api/user/" + userId, this.getHeader(responseGetAuth, "x-csrf-token"), this.getCookie(responseGetAuth,"auth_sid"), authData);

        responseDeleteUser.prettyPrint();

        //Check result
        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, this.getHeader(responseGetAuth, "x-csrf-token"), this.getCookie(responseGetAuth, "auth_sid"));


        Assertions.assertResponseCodeEquals(responseUserData, 404);
        Assertions.assertResponseTextEquals(responseUserData,"User not found");

    }

    @Description("Couldn`t delete user with other user auth")
    @DisplayName("Delete user with other auth")
    @Test
    public void testDeleteUserWithOtherAuth() {

        //Generate User
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .makePostForGeneration("https://playground.learnqa.ru/api/user", userData);
        String userId = responseCreateAuth.getString("id");

        //Login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //Delete
        Response responseDeleteUser = apiCoreRequests
                .makeDeleteRequestWithAuth("https://playground.learnqa.ru/api/user/" + userId, this.getHeader(responseGetAuth, "x-csrf-token"), this.getCookie(responseGetAuth,"auth_sid"), authData);


        //Check result
        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, this.getHeader(responseGetAuth, "x-csrf-token"), this.getCookie(responseGetAuth, "auth_sid"));


        Assertions.assertResponseCodeEquals(responseUserData, 200);
        Assertions.assertJsonHasField(responseUserData, "username");

    }
}
