package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import lib.ApiCoreRequests;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Epic("Authorization cases")
@Feature("Create users")

public class UserRegisterTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    public void testCreateUserWithExistingEmail(){
        String email = "vinkotov@example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user")
                .andReturn();
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" +email + "' already exists");

    }

    @Test
    public void testCreateUserSuccessfully(){
        String email = DataGenerator.getRandomEmail();

        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user")
                .andReturn();
        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");

    }

    @Description("This test create user with invalid email format")
    @DisplayName("User with wrong email")
    @Test
    public void testUserWithWrongEmail(){
        String email = "wrongmailexample.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Invalid email format");
    }

    @Description("This test create user with invalid first name in 1 symbol")
    @DisplayName("User with short firstName")
    @Test
    public void testUserWithShortName(){
        String firstName = "A";

        Map<String, String> userData = new HashMap<>();
        userData.put("firstName", firstName);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'firstName' field is too short");
    }

    @Description("This test create user with invalid first name in 251 symbols")
    @DisplayName("User with long firstName")
    @Test
    public void testUserWithLongName(){
        String firstName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        Map<String, String> userData = new HashMap<>();
        userData.put("firstName", firstName);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'firstName' field is too long");
    }

    @Description("This test checks that all strings from User Data are not empty")
    @DisplayName("Empty strings in registration form")
    @ParameterizedTest
    @ValueSource (strings = {"email", "password", "username", "firstName", "lastName"})

    public void testEmptyString(String parameter){

        Map<String, String> userData = new HashMap<>();
        userData.put(parameter, "");
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user", userData);

        if (parameter.equals("email")) {
            Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
            Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'email' field is too short");
        } else if (parameter.equals("password")) {
            Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
            Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'password' field is too short");

        }else if (parameter.equals("username")) {
            Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
            Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'username' field is too short");

        }else if (parameter.equals("firstName")) {
            Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
            Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'firstName' field is too short");

        } else {
            Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
            Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'lastName' field is too short");
        }
    }

}
