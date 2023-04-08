import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeworkCookie {

    @Test
    public void checkCookie(){
        Response responseCheckCookie = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();

        Map<String, String> Cookies = responseCheckCookie.getCookies();
        System.out.println(Cookies);

        assertTrue(Cookies.containsKey("HomeWork")&&Cookies.containsValue("hw_value") , "Not expected cookie");

    }
}
