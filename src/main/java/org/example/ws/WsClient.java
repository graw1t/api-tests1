package org.example.ws;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.util.Date;

public class WsClient extends WebSocketClient {

    private WsContext context;

    public WsClient(WsContext context) {
        super(context.getServerURI());
        this.context = context;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("new connection is opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("received <- " + message);
        context.getMessageMap().put(new Date().getTime(), message);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }

    @Override
    public void send(String text) {
        System.out.println("send -> " + text);
        super.send(text);
    }
}
