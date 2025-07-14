package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import config.ApiUrl;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeApiTests {

    private static final String TIME_FORMAT = "^$$\\d+(?:,\\d+)*$$$";

    @Test
    void testGetTime() throws JsonProcessingException {
        ExtractableResponse<Response> response = given()
                .baseUri(ApiUrl.BASE_URL_API)
                .when()
                .get("/time")
                .then()
                .statusCode(201)
                .extract();

        assertTrue("[2025,194,12,34,46,128962636,0,0,0]".matches(TIME_FORMAT));
        System.out.println();
    }

    @Test
    void testGetTimeTwo() {
        String responseBody = given()
                .baseUri(ApiUrl.BASE_URL_API)
                .when()
                .get("/time")
                .then()
                .log().all()
                .statusCode(200)
                .body("time", notNullValue())
                .body("time", instanceOf(List.class))
                .body("time[0]", equalTo(2025)).toString();
        System.out.println(responseBody);
    }
}
