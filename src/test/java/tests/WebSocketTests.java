package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.ApiUrl;

import org.example.dto.MessageDto;
import org.example.dto.SubscriptionDto;
import org.example.util.JsonUtil;
import org.example.ws.WsClient;
import org.example.ws.WsContext;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.junit.jupiter.api.*;

import java.net.URI;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebSocketTests {

    private static WsContext context;
    private static WebSocketClient webSocketClient;

    private final String SUBSCRIBE_BTCUSDT_1SEC = "t://klines/BTCUSDT?duration=1s";
    private final String UNSUBSCRIBE_BTCUSDT_1SEC = "t://klines/BTCUSDT?duration=1s";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public void setupWebSocket() {
        context = new WsContext(URI.create(ApiUrl.BASE_URL_WS));
        webSocketClient = new WsClient(context);
    }

    @AfterAll
    public void closeWebSocket() throws InterruptedException {
        if (!webSocketClient.isClosed())
            webSocketClient.closeBlocking();
    }

    @Test
    void testWebSocketConnection() {
        Assertions.assertTrue(!webSocketClient.isOpen());
        Assertions.assertThrows(WebsocketNotConnectedException.class, () -> webSocketClient.send("msg"));
    }

    @Test
    void testWebSocketSendMessages() throws InterruptedException, JsonProcessingException {
        webSocketClient.connectBlocking();
        Assertions.assertTrue(webSocketClient.isOpen());

        // подписка на получение курса BTC/USDT каждую секунду
        SubscriptionDto subscription = new SubscriptionDto(
                new String[]{SUBSCRIBE_BTCUSDT_1SEC}, new String[]{}
        );
        String subscriptionMsg = objectMapper.writeValueAsString(subscription);
        webSocketClient.send(subscriptionMsg);

        // ждем 5с ожидая сообщения каждую секунду
        Thread.sleep(5000);

        List<MessageDto> receivedMessageList = context.getMessageMap().values().stream()
                .map(msg -> JsonUtil.getObjectFromString(msg, MessageDto.class))
                .toList();

        Assertions.assertFalse(receivedMessageList.isEmpty());

        List<String> topicList = receivedMessageList.stream().map(MessageDto::getTopic).toList();
        Assertions.assertTrue(topicList.contains(SUBSCRIBE_BTCUSDT_1SEC),
                "В списке топиков полученных сообщений нет ожидаемого значения %s".formatted(SUBSCRIBE_BTCUSDT_1SEC));

        // отписка от получения курса BTC/USDT каждую секунду
        SubscriptionDto unsubscription = new SubscriptionDto(
                new String[]{UNSUBSCRIBE_BTCUSDT_1SEC}, new String[]{}
        );
        String unsubscriptionMsg = objectMapper.writeValueAsString(unsubscription);
        webSocketClient.send(unsubscriptionMsg);

        // очищаем мапу сообщений
        context.getMessageMap().clear();

        Thread.sleep(5000);

        Assertions.assertTrue(context.getMessageMap().isEmpty(), "Список сообщений должен быть пуст");
        webSocketClient.closeBlocking();
    }
}
