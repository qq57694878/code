package com.youi.business.ehcache;


/**
 * Created by jinliang on 2017/2/21.
 */
public class ThreadEhcache implements Runnable{
    @Override
    public void run() {

    }
  /*  @Override
    public void run() {
        // Creating a CacheManager based on a specified configuration file.
        CacheManager manager = CacheManager.newInstance("src/main/resources/ehcache.xml");
        // obtains a Cache called sampleCache1, which has been preconfigured in the configuration file
        Cache cache = manager.getCache("sampleCache1");
        // puts an element into a cache
        Element element = new Element("key1", "哈哈");
        cache.put(element);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // puts an element into a cache
        Element element1 = new Element("key2", "哈哈2");
        cache.put(element1);
        manager.shutdown();
    }*/
}
