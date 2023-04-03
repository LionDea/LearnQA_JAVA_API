import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class LongRedirect2 {

    @Test
    public void testRestAssured() {

        int a=1;

                Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();
        int statusCode = response.getStatusCode();
        String url = response.getHeader("Location");
        while (statusCode == 301) {
                       a = a+1;
            Response response2 = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(url)
                    .andReturn();
            statusCode = response2.getStatusCode();
            url = response2.getHeader("Location");
        }
        System.out.println(a);


    }
}
