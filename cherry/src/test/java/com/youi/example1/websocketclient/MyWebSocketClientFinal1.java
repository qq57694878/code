package com.youi.example1.websocketclient;

import com.youi.core.spring.ApplicationContextHelper;
import com.youi.core.spring.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
public class MyWebSocketClientFinal1 {
    private static Logger logger = LoggerFactory.getLogger(MyWebSocketClientFinal1.class);
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
        stompClient.setReceiptTimeLimit(1000);
        //配置心跳频率，默认就是下面这个间隔
        ThreadPoolTaskScheduler task = new ThreadPoolTaskScheduler();
        task.initialize();
        stompClient.setTaskScheduler(task);
        stompClient.setDefaultHeartbeat(new long[]{0,0});
        stompClient.start();
        final CountDownLatch latch = new CountDownLatch(1);
        StompSessionHandler handler = new MyWebSocketClientFinal1.TestSessionHandler() {
            @Override
            public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
                logger.info("websocket client connected");
                logger.info("connected session : "+session.getSessionId());
            }
        };
        StompSession session = stompClient.connect(WEBSOCKET_URL, headers, handler).get();
        try{
            session.setAutoReceipt(true);
            StompSession.Receiptable receiptable = session.send(sendUrl,message);
            receiptable.addReceiptTask(new Runnable() {
                @Override
                public void run() {
                    logger.info("message send sucessed!");
                    latch.countDown();
                }
            });
            receiptable.addReceiptLostTask(new Runnable() {
                @Override
                public void run() {
                    logger.info("message send lost!");
                    latch.countDown();
                }
            });
            if(!latch.await(2, TimeUnit.SECONDS)){
                logger.info("wait message receive overtime!");
            }

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
