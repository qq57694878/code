package com.youi.business.common.scheduler;

import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jinliang on 2017/3/8.
 */
public class CacheMonitoringUtil {
    private static CacheMonitoringUtil instance = new CacheMonitoringUtil();
    private static ConcurrentMap<String,Cache> concurrentMap = new ConcurrentHashMap<String,Cache>();

    private CacheMonitoringUtil(){

    }
    public static CacheMonitoringUtil getInstance(){
        return CacheMonitoringUtil.instance;
    }
    public static Cache getCache(String cacheName){
        Cache deque =concurrentMap.get(cacheName);
        if(deque==null){
             synchronized (concurrentMap){
                 if(deque==null){
                     deque= new ConcurrentMapCache(cacheName);
                     concurrentMap.put(cacheName,deque);
                 }
             }
        }
        return deque;
    }
}
