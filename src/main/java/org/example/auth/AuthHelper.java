package org.example.auth;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.config.ApiUrl;


public class AuthHelper {
    private static String accessToken;
    private static String refreshToken;

    public static Response login(String login, String password) {
        Response response = RestAssured.given()
                .baseUri(ApiUrl.BASE_URL_AUTH)
                .contentType("application/json")
                .body("{\"login\": %s, \"password\": %s}".formatted(login,password))
                .post("/login");
        accessToken = response.getCookie("access_token");
        refreshToken = response.getCookie("refresh_token");
        return response;
    }


    public static Response logout(String token) {
        return RestAssured.given()
                .baseUri(ApiUrl.BASE_URL_AUTH)
                .cookie("access_token", token)
                .post("/logout");
    }

    public static String getAccessToken() {
        return accessToken;
    }
    public static String getRefreshToken() {
        return refreshToken;
    }
}