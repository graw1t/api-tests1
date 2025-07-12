package config;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthHelper {
    private static String sessionId;

    public static String login() {
        Response response = RestAssured.given()
                .baseUri(ApiUrl.BASE_URL_AUTH)
                .contentType("application/json")
                .body("{\"login\": \"testuser\", \"password\": \"testpassword\"}")
                .post("/login");

        if (response.statusCode() == 200) {
            // Предположим, что сервер возвращает cookie или токен в заголовках или теле.
            // Например, cookie:
            sessionId = response.getCookie("session_id");
            return sessionId;
        } else {
            throw new RuntimeException("Login failed");
        }
    }

    public static void logout() {
        if (sessionId != null) {
            RestAssured.given()
                    .baseUri(ApiUrl.BASE_URL_AUTH)
                    .cookie("session_id", sessionId)
                    .post("/logout");
        }
    }

    public static String getSessionId() {
        return sessionId;
    }
}
