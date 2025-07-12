package tests;

import config.ApiUrl;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.instanceOf;

public class TimeApiTests {

    @Test
    void testGetTime() {
        given()
                .baseUri(ApiUrl.BASE_URL_API)
                .when()
                .get("/time")
                .then()
                .statusCode(200)
                .body("", instanceOf(Integer.class));
    }
}
