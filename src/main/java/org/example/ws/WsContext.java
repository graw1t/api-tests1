package org.example.ws;

import lombok.Getter;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Getter
public class WsContext {

    public WsContext(URI serverURI) {
        this.serverURI = serverURI;
    }

    private final URI serverURI;
    private final Map<Long, String> messageMap = new HashMap<>();
}
