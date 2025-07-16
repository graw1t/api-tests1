package tests;

import io.restassured.response.Response;
import org.example.auth.AuthHelper;
import org.junit.jupiter.api.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthTests {

    @Test
    @DisplayName("Валидный логин и логаут")
    void testSuccessfulLogin() {
        Response response = AuthHelper.login("testuser", "testpassword");
        Assertions.assertEquals(200, response.statusCode());
        String accessToken = AuthHelper.getAccessToken();
        Assertions.assertNotNull(accessToken);

        // логаут с невалидным токеном
        Response logoutResponse = AuthHelper.logout("invalid_token");
        Assertions.assertEquals(401, logoutResponse.statusCode());

        // логаут с валидным токеном
        logoutResponse = AuthHelper.logout(accessToken);
        Assertions.assertEquals(200, logoutResponse.statusCode());
    }

    @Test
    @DisplayName("Невалидный логин")
    void testWithoutLogin() {
        Response response = AuthHelper.login("werqew", "qweqewq");
        Assertions.assertEquals(401, response.statusCode());
    }
}