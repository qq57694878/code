package com.youi.example1.websocketclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketClientUtilV2 {
    private static Logger logger = LoggerFactory.getLogger(WebSocketClientUtilV2.class);
    private final static String WEBSOCKET_URL="ws://localhost:{port}/api/async-websocket";
    public static void main(String[]args)throws Exception{
        GreetingIntegrationTests1 test = new GreetingIntegrationTests1();
        test.getGreeting();
    }
}
