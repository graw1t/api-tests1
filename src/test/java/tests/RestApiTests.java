package tests;

import io.restassured.response.ValidatableResponse;
import org.example.dto.TimeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import spec.RequestSpec;


public class RestApiTests {

    @Test
    public void testGetTimeBodyCheck() {
        ValidatableResponse response = RequestSpec.getGetRequest("/time")
                .statusCode(201);
        TimeDto body = response.extract().as(TimeDto.class);

        Assertions.assertNotNull(body.getTime());
        Assertions.assertEquals(9, body.getTime().size());
        System.out.print(body.getTime());
    }

    @Test
    public void testGetOptionsAuthError() {
        RequestSpec.getGetRequest("/options").statusCode(401);
    }
}

