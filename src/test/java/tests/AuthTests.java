package tests;

import org.junit.jupiter.api.*;
import io.restassured.RestAssured;
import config.AuthHelper;
import static config.ApiUrl.BASE_URL_AUTH;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthTests {

    @BeforeAll
    void setup() {
        // Можно установить базовый URI глобально, если нужно:
        RestAssured.baseURI = BASE_URL_AUTH;
        AuthHelper.login();
    }

    @AfterAll
    void teardown() {
        AuthHelper.logout();
    }

    @Test
    void testSuccessfulLogin() {
        String sessionId = AuthHelper.getSessionId();
        Assertions.assertNotNull(sessionId);
        // Можно дополнительно проверить, что сессия активна через API или куки.
    }
}
