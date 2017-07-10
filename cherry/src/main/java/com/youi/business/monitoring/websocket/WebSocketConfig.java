package com.youi.business.monitoring.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private static Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);
    @Autowired
    private MyWebSocketSubscriptHostItemsHandler myWebSocketSubscriptHostItemsHandler;
    @Autowired
    private MyWebSocketSubscriptVmItemsHandler myWebSocketSubscriptVmItemsHandler;
    @Autowired
    private MyWebSocketSubscriptSwitchItemsHandler myWebSocketSubscriptSwitchItemsHandler;
    @Autowired
    private MyWebSocketSubscriptEnvItemsHandler myWebSocketSubscriptEnvItemsHandler;
    @Autowired
    private MyWebSocketSubscriptWaterItemsHandler myWebSocketSubscriptWaterItemsHandler;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(myWebSocketSubscriptHostItemsHandler, "/async-websocket/subscribe_host_items").setAllowedOrigins("*");
        webSocketHandlerRegistry.addHandler(myWebSocketSubscriptVmItemsHandler, "/async-websocket/subscribe_vm_items").setAllowedOrigins("*");
        webSocketHandlerRegistry.addHandler(myWebSocketSubscriptSwitchItemsHandler, "/async-websocket/subscribe_switch_items").setAllowedOrigins("*");
        webSocketHandlerRegistry.addHandler(myWebSocketSubscriptEnvItemsHandler, "/async-websocket/subscribe_env_items").setAllowedOrigins("*");
        webSocketHandlerRegistry.addHandler(myWebSocketSubscriptWaterItemsHandler, "/async-websocket/subscribe_water_items").setAllowedOrigins("*");
    }

}