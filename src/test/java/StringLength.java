import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class StringLength {
    @Test
    public void checkString() {
        String Str = "This string contains 31 symbols";

        assertTrue(Str.length() > 15, "String is not longer then 15 symbols");


    }
}
