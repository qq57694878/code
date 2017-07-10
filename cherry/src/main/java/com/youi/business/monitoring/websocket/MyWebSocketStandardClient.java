package com.youi.business.monitoring.websocket;

import com.alibaba.fastjson.JSONObject;
import com.youi.core.spring.ApplicationContextHelper;
import com.youi.core.spring.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyWebSocketStandardClient {
    private static Logger logger = LoggerFactory.getLogger(MyWebSocketStandardClient.class);
    private final static String WEBSOCKET_URL;
    static{
        WEBSOCKET_URL= (String)((PropertiesUtils) ApplicationContextHelper.getBean("propertiesUtils")).getPropertiesValue("websoket.url");
    }



    public  void sendMessage(final String sendUrl,String message) throws Exception{
        this.sendMessage(sendUrl,message,new WebSocketHttpHeaders());
    }

    public  void sendMessage(final String sendUrl,String message,WebSocketHttpHeaders headers) throws Exception {
        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketHandler handler = new MyWebSocketStandardClient.TestSessionHandler() {

        };
        URI uri = new URI(WEBSOCKET_URL+sendUrl);
        WebSocketSession session = client.doHandshake(handler, headers, uri).get();
        try{
            session.sendMessage(new TextMessage(message));
        }catch (Exception e){
             e.printStackTrace();
        }finally {
            if(session.isOpen()){
                session.close();
            }
        }
        logger.info("websocket message send over!");

    }

    private class TestSessionHandler extends TextWebSocketHandler {
        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            super.afterConnectionEstablished(session);
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
            super.handleTransportError(session, exception);
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
            super.afterConnectionClosed(session, status);
        }
    }

}
