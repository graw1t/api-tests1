package tests;

import config.ApiUrl;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WebSocketTests {

    @Test
    void testWebSocketConnection() throws InterruptedException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(ApiUrl.BASE_URL_WS)
                .build();

        CountDownLatch latch = new CountDownLatch(1);

        WebSocketListener listener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                webSocket.send("subscribe to some channel");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                System.out.println("Received message: " + text);
                // Можно добавить проверки содержимого сообщения.
                latch.countDown();
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                t.printStackTrace();
                latch.countDown();
            }
        };

        WebSocket ws = client.newWebSocket(request, listener);

        // Ждем получения сообщения или таймаута:
        latch.await(10, TimeUnit.SECONDS);

        ws.close(1000, "Test complete");

        // Можно добавить ассерты по полученным сообщениям.
    }
}
