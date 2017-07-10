package com.youi.example1.websocketclient;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class MyWebSocketClient2 {
    private static Logger logger = LoggerFactory.getLogger(MyWebSocketClient2.class);
    private SockJsClient sockJsClient;

    private WebSocketStompClient stompClient;

    private final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
    public void setup() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        this.sockJsClient = new SockJsClient(transports);
        this.stompClient = new WebSocketStompClient(sockJsClient);
        this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        this.stompClient.setReceiptTimeLimit(3000);
        //配置心跳频率，默认就是下面这个间隔
        this.stompClient.setDefaultHeartbeat(new long[]{10000,10000});

        //stompsession 使用必须配置Receiptable， taskScheduler作用是
        //Configure a scheduler to use for heartbeats and for receipt tracking.
        //为配置心跳频率和跟踪发送状态 准备的线程池
        ThreadPoolTaskScheduler task = new ThreadPoolTaskScheduler();
        task.initialize();
        this.stompClient.setTaskScheduler(task);


    }

    public  void sendMessage(final String sendUrl,final String subscribeUrl, Object message) throws Exception{
        this.sendMessage(sendUrl,subscribeUrl,message,new WebSocketHttpHeaders());
    }

    public  void sendMessage(final String sendUrl,final String subscribeUrl,Object message,WebSocketHttpHeaders headers) throws Exception {
        setup();
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<Throwable> failure = new AtomicReference<>();

        StompSessionHandler handler = new MyWebSocketClient2.TestSessionHandler(failure) {

            @Override
            public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
                logger.info("websocket client connected");
                logger.info("connected session : "+session.getSessionId());
                session.setAutoReceipt(true);
                session.subscribe(subscribeUrl, new StompFrameHandler() {
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
                    session.send(sendUrl,message).addReceiptTask(new Runnable() {
                        @Override
                        public void run() {
                            session.disconnect();
                            System.out.println("session : "+session.getSessionId()+" disconnect,message send ok!");
                            latch.countDown();
                        }
                    });;
                } catch (Throwable t) {
                    failure.set(t);
                    latch.countDown();
                }
                logger.info("afterConnected method end ");
            }
        };

        this.stompClient.connect("ws://192.168.31.196:{port}/api/async-websocket", this.headers, handler, 8888);

        if (latch.await(10, TimeUnit.SECONDS)) {
            if (failure.get() != null) {
                throw new AssertionError("", failure.get());
            }
        }
        logger.info("websocket message send successed!");

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
