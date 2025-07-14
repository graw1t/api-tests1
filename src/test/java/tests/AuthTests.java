package tests;

import config.AuthHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import javax.security.sasl.AuthenticationException;

import static config.ApiUrl.BASE_URL_AUTH;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthTests {

    @BeforeAll
    void setup() {
        RestAssured.baseURI = BASE_URL_AUTH;
    }

    @AfterAll
    void teardown() {
        AuthHelper.logout();
    }

    @Test
    @DisplayName("Валидный логин")
    void testSuccessfulLogin() throws AuthenticationException {
        Response response = AuthHelper.login("testuser", "testpassword");
        Assertions.assertEquals(200, response.statusCode());
        String sessionId = AuthHelper.getAccessToken();
        Assertions.assertNotNull(sessionId);
    }

    @Test
    @DisplayName("Невалидный логин")
    void testWithoutLogin() throws AuthenticationException {
        Response response = AuthHelper.login("werqew", "qweqewq");
        Assertions.assertEquals(401, response.statusCode());
    }
}
