package com.youi.example1.websocketclient;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinliang on 2017/2/25.
 */
public class WebSocketClient1 {
    public static void main(String[] args) {
        /*List<Transport> transports = new ArrayList<>(2);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());

        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.doHandshake(new WebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

            }

            @Override
            public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {

            }

            @Override
            public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

            }

            @Override
            public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

            }

            @Override
            public boolean supportsPartialMessages() {
                return false;
            }
        }, "ws://example.com:8080/sockjs");*/
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
      //  stompClient.setMessageConverter(new StringMessageConverter());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
      //  stompClient.setTaskScheduler(taskScheduler); // for heartbeats

        String url = "ws://127.0.0.1:8080/endpoint";
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        stompClient.connect(url, sessionHandler);
    }

}
