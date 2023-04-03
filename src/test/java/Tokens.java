import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class Tokens {
    @Test
    public void testRestAssured() throws InterruptedException {

        JsonPath response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        response.prettyPrint();
        Integer seconds = response.get("seconds");
        String token = response.get("token");

        Response response2 = RestAssured
                .given()
                .queryParam("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn();
        response2.print();
    Thread.sleep(seconds*1000);
        JsonPath response3 = RestAssured
                .given()
                .queryParam("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        response3.prettyPrint();

        String result = response3.get("result");
        String status = response3.get("status");
        if ((result == null) && (status =="Job is NOT ready")){
            System.out.println("ERROR");
        }
        else{
            System.out.println(result);
            System.out.println(status);
        }



    }
}
