import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeworkHeader {
    @Test
    public void checkHeader(){
        Response responseCheckHeader = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        Headers CheckHeaders = responseCheckHeader.getHeaders();
        System.out.println(CheckHeaders);

        assertTrue(CheckHeaders.hasHeaderWithName("x-secret-homework-header"), "No secret header");
        String secretHeader = CheckHeaders.getValue("x-secret-homework-header");
        assertEquals(secretHeader,"Some secret value", "Wrong value for header");


    }
}
