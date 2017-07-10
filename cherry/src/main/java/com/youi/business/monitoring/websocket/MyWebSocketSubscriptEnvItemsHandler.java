package com.youi.business.monitoring.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jinliang on 2017/3/20.
 */
@Component
public class MyWebSocketSubscriptEnvItemsHandler extends TextWebSocketHandler {

    private static Logger logger = LoggerFactory.getLogger(MyWebSocketSubscriptEnvItemsHandler.class);

    private final static List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<WebSocketSession>());
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for(WebSocketSession s1:sessions){
            if(!s1.getId().equals(session.getId())){
                s1.sendMessage(message);
            }
        }
    }

    //连接建立后处理
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("connect to the websocket chat success......");
        sessions.add(session);

    }
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        logger.debug("websocket chat connection closed......");
        sessions.remove(session);
    }
    //连接关闭后处理
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket chat connection closed......");
        sessions.remove(session);
    }
}
