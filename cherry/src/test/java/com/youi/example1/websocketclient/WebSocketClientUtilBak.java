package com.youi.example1.websocketclient;

import com.alibaba.fastjson.JSONObject;
import com.youi.core.spring.ApplicationContextHelper;
import com.youi.core.spring.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class WebSocketClientUtilBak {
    private static Logger logger = LoggerFactory.getLogger(WebSocketClientUtilBak.class);
    private final static String WEBSOCKET_URL;
    static{
            WEBSOCKET_URL= (String)((PropertiesUtils) ApplicationContextHelper.getBean("propertiesUtils")).getPropertiesValue("websoket.url");
    }
    public static void sendMessage(String url,Object message) throws Exception{
        WebSocketClientUtilBak.sendMessage(url,message,new WebSocketHttpHeaders());
    }

    public static void sendMessage(String url,Object message,WebSocketHttpHeaders headers) throws Exception {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<Throwable> failure = new AtomicReference<>();
        StompSessionHandler handler = new TestSessionHandler(failure) {
            @Override
            public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
                logger.info("websocket client connected");
                try {
                    session.send(url, message).addReceiptTask(new Runnable() {
                        @Override
                        public void run() {
                            session.disconnect();
                            latch.countDown();
                        }
                    });
                } catch (Throwable t) {
                    failure.set(t);
                    latch.countDown();
                }
            }
        };
        stompClient.connect(WEBSOCKET_URL, headers, handler);
        if (latch.await(10, TimeUnit.SECONDS)) {
            if (failure.get() != null) {
                throw new AssertionError("", failure.get());
            }
        }

    }


    private static class TestSessionHandler extends StompSessionHandlerAdapter {

        private final AtomicReference<Throwable> failure;


        public TestSessionHandler(AtomicReference<Throwable> failure) {
            this.failure = failure;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            this.failure.set(new Exception(headers.toString()));
        }

        @Override
        public void handleException(StompSession s, StompCommand c, StompHeaders h, byte[] p, Throwable ex) {
            this.failure.set(ex);
        }

        @Override
        public void handleTransportError(StompSession session, Throwable ex) {
            this.failure.set(ex);
        }
    }

    public static void main(String[]args)throws Exception{
        Map<String,Object> p = new HashMap<String,Object>();
        p.put("key11","nihao");
        WebSocketClientUtilBak.sendMessage("/app/send_host_items",new JSONObject(p));
    }
}
