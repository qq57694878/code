package com.youi.example1.websocketclient;

import com.alibaba.fastjson.JSONObject;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingIntegrationTests2 {


    private int port;

    private SockJsClient sockJsClient;

    private WebSocketStompClient stompClient;

    private final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

    public static void main(String[] args) throws Exception {
        GreetingIntegrationTests2 s= new GreetingIntegrationTests2();
        s.getGreeting();
    }
    public void setup() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        this.sockJsClient = new SockJsClient(transports);

        this.stompClient = new WebSocketStompClient(sockJsClient);
        this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    public void getGreeting() throws Exception {
        setup();
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<Throwable> failure = new AtomicReference<>();

        StompSessionHandler handler = new TestSessionHandler(failure) {

            @Override
            public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
                session.subscribe("/topic/subscribe_host_items", new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return JSONObject.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        JSONObject greeting = (JSONObject) payload;
                        try {
                            System.out.println(greeting.toJSONString());
                        } catch (Throwable t) {
                            failure.set(t);
                        } finally {
                            session.disconnect();
                            latch.countDown();
                        }
                    }
                });
                try {
                    Map<String,Object> p = new HashMap<String,Object>();
                    p.put("key11","nihao");
                    session.send("/app/send_host_items", new JSONObject(p));
                } catch (Throwable t) {
                    failure.set(t);
                    latch.countDown();
                }
            }
        };

        this.stompClient.connect("ws://192.168.31.196:{port}/api/async-websocket", this.headers, handler, 8888);

        if (latch.await(30, TimeUnit.SECONDS)) {
            if (failure.get() != null) {
                throw new AssertionError("", failure.get());
            }
        }

    }

    private class TestSessionHandler extends StompSessionHandlerAdapter {

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
}
