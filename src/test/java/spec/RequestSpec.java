package spec;

import org.example.config.ApiUrl;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RequestSpec {

    public static ValidatableResponse getGetRequest(String url) {
        return given()
                .baseUri(ApiUrl.BASE_URL_API)
                .get(url)
                .then();
    }

    public static ValidatableResponse getGetRequestWithQueryParam(String url, Map<String, String> params) {
        return given()
                .baseUri(ApiUrl.BASE_URL_API)
                .get(url, params)
                .then();
    }
}
