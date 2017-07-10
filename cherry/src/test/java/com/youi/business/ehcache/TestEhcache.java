package com.youi.business.ehcache;


import org.junit.Test;

/**
 * Created by GWCheng on 2016/3/4.
 */
public class TestEhcache {
  /*  @Test
    public void testEhcache() {
        ThreadEhcache a = new ThreadEhcache();
        Thread t = new Thread(a);
        t.start();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Creating a CacheManager based on a specified configuration file.
                CacheManager manager = CacheManager.newInstance("src/main/resources/ehcache.xml");
                // obtains a Cache called sampleCache1, which has been preconfigured in the configuration file
                Cache cache = manager.getCache("sampleCache1");
                System.out.println(cache.getSize());
                System.out.println("key1:"+cache.get("key1").getObjectValue());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(cache.getSize());
                System.out.println("key2:"+cache.get("key2").getObjectValue());
                manager.shutdown();
            }
        });
        t1.start();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
}