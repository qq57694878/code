package com.youi.business.monitoring.websocket;

import com.youi.core.spring.ApplicationContextHelper;
import com.youi.core.spring.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyWebSocketSockjsClient {
    private static Logger logger = LoggerFactory.getLogger(MyWebSocketSockjsClient.class);
    private final static String WEBSOCKET_URL;
    static{
        WEBSOCKET_URL= (String)((PropertiesUtils) ApplicationContextHelper.getBean("propertiesUtils")).getPropertiesValue("websoket.url");
    }



    public  void sendMessage(final String sendUrl,Object message) throws Exception{
        this.sendMessage(sendUrl,message,new WebSocketHttpHeaders());
    }

    public  void sendMessage(final String sendUrl,Object message,WebSocketHttpHeaders headers) throws Exception {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.start();
        StompSessionHandler handler = new MyWebSocketSockjsClient.TestSessionHandler() {
            @Override
            public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
                logger.info("websocket client connected");
                logger.info("connected session : "+session.getSessionId());
            }
        };
        StompSession session = stompClient.connect(WEBSOCKET_URL, headers, handler).get();
        try{
            session.send(sendUrl,message);
        }catch (Exception e){
             e.printStackTrace();
        }finally {
            if(session.isConnected()){
                session.disconnect();
            }
            stompClient.stop();
        }
        logger.info("websocket message send over!");

    }

    private class TestSessionHandler extends StompSessionHandlerAdapter {


    }

}
