package com.youi.business.monitoring.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.youi.core.codetable.ModifyCodeTable;
import com.youi.core.spring.ServletContextHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by jinliang on 2016/7/6.
 */
@Component
public class MessageSenderListener implements ServletContextListener {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(MessageSenderListener.class);
    private  ConcurrentMap<String,List> concurrentMap ;
    private ConcurrentMap<String,MessageSender> senders;
    @Autowired
    private WebSocketMonitoringV2MessageConvert webSocketMonitoringV2MessageConvert;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        concurrentMap = new ConcurrentHashMap<String,List>();
        senders= new ConcurrentHashMap<String,MessageSender>();
        String args[]=new String[]{"/subscribe_host_items","/subscribe_vm_items","/subscribe_switch_items","/subscribe_env_items","/subscribe_water_items"};
        for(String key:args){
            MessageSender   messageSender = new MessageSender(key,getMessageList(key));
            Thread messageSenderThread = new Thread(messageSender, "MessageSender["+ key + "]");
            messageSenderThread.setDaemon(true);
            messageSenderThread.start();
            senders.put(key,messageSender);
        }


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        for(String key:senders.keySet()){
            MessageSender   messageSender =senders.get(key);
            messageSender.stop();
        }
        senders = null;
    }

    public void sendMessage(String key,JSONObject message){
        senders.get(key).send(message);
    }



    private  List<JSONObject> getMessageList(String cacheName){
        List<JSONObject> list =concurrentMap.get(cacheName);
        if(list==null){
            synchronized (concurrentMap){
                if(list==null){
                    list= new ArrayList<JSONObject>();
                    concurrentMap.put(cacheName,list);
                }
            }
        }
        return list;
    }



    /**
     * Poller class.
     */
    public class MessageSender implements Runnable {

        protected boolean running = true;
        protected List<JSONObject> messages;
        protected  String key;

        public MessageSender(String key,List<JSONObject> messages) {
            this.key = key;
            this.messages = messages;
        }

        public void stop() {
            running = false;
            synchronized (messages) {
                messages.notify();
            }
        }

        public void send(JSONObject message) {
            synchronized (messages) {
                messages.add(message);
                messages.notify();
            }
        }


        /**
         * The background thread that listens for incoming TCP/IP connections and
         * hands them off to an appropriate processor.
         */
        @Override
        public void run() {

            // Loop until we receive a shutdown command
            while (running) {
                JSONObject[] pendingMessages;
                synchronized (messages) {
                    try {
                        if (messages.size() == 0) {
                            messages.wait();
                        }
                    } catch (InterruptedException e) {
                        // Ignore
                    }
                    pendingMessages = messages.toArray(new JSONObject[0]);
                    messages.clear();
                }

                        try {
                            for (int j = 0; j < pendingMessages.length; j++) {
                              Method m =   webSocketMonitoringV2MessageConvert.getClass().getMethod(key.split("/")[1], JSONObject.class);
                              JSONObject o = (JSONObject)m.invoke(webSocketMonitoringV2MessageConvert,pendingMessages[j]);
                               MyWebSocketStandardClient client = new MyWebSocketStandardClient();
                                client.sendMessage(key,o.toJSONString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


            }

        }


    }

}
