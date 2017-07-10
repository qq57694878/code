package com.youi.example1.websocketclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
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
public class MyWebSocketClientBAK {
    private static Logger logger = LoggerFactory.getLogger(MyWebSocketClientBAK.class);

    private String websocket_url;

    public String getWebsocket_url() {
        return websocket_url;
    }
    @Value("${websoket.url}")
    public void setWebsocket_url(String websocket_url) {
        this.websocket_url = websocket_url;
    }

    public  void sendMessage(final String sendUrl,final String subscribeUrl, Object message) throws Exception{
        this.sendMessage(sendUrl,subscribeUrl,message,new WebSocketHttpHeaders());
    }

    public  void sendMessage(final String sendUrl,final String subscribeUrl,Object message,WebSocketHttpHeaders headers) throws Exception {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

   /*     stompClient.setReceiptTimeLimit(3000);
        //配置心跳频率，默认就是下面这个间隔
        stompClient.setDefaultHeartbeat(new long[]{10000,10000});

        //stompsession 使用必须配置Receiptable， taskScheduler作用是
        //Configure a scheduler to use for heartbeats and for receipt tracking.
        //为配置心跳频率和跟踪发送状态 准备的线程池
        ThreadPoolTaskScheduler task = new ThreadPoolTaskScheduler();
        task.initialize();
        stompClient.setTaskScheduler(task);
           session.setAutoReceipt(true);
        .addReceiptTask(new Runnable() {
                        @Override
                        public void run() {
                            session.disconnect();
                            System.out.println("session : "+session.getSessionId()+" disconnect,message send ok!");
                            latch.countDown();
                        }
                    });
*/



        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<Throwable> failure = new AtomicReference<>();
        StompSessionHandler handler = new TestSessionHandler(failure) {
            @Override
            public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
                logger.info("websocket client connected");
                logger.info("connected session : "+session.getSessionId());
                session.subscribe(subscribeUrl, new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return Object.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        try {
                            logger.info("data received:"+payload);
                        } catch (Throwable t) {
                            failure.set(t);
                        } finally {
                            session.disconnect();
                            latch.countDown();
                        }
                    }
                });
                try {
                    session.send(sendUrl, message);
                } catch (Throwable t) {
                    failure.set(t);
                    latch.countDown();
                }
                logger.info("afterConnected method end ");
            }
        };
        stompClient.connect(this.getWebsocket_url(), headers, handler);
        if (latch.await(30, TimeUnit.SECONDS)) {
            if (failure.get() != null) {
                throw new AssertionError("", failure.get());
            }
        }
        if (failure.get() != null) {
            throw new AssertionError("", failure.get());
        }
        logger.info("websocket message send successed!");
    }


    private  class TestSessionHandler extends StompSessionHandlerAdapter {

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
