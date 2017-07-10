package com.youi.business.ehcache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Created by GWCheng on 2016/3/3.
 */
@Component
//这里定义了之后就不必在下面的每个方法上写 cacheNames="books" 了
@CacheConfig(cacheNames = "sampleCache1")
public class CacheService {

    @Cacheable(key="#isbn")
    public Book findBook(String isbn)  {
        System.out.println("isbn="+isbn+" 方法调用");
        Book book = null;
        if (isbn == "123") {
            book = new Book("123", "Thinking in Java");
        } else if (isbn == "456") {
            book = new Book("456", "Effective Java");
        }
        return book;

    }

    @CachePut(key="#isbn")
    public Book updateBook(String isbn,String bookName){
        System.out.println("isbn=" + isbn + " bookName="+bookName+" 更新缓存的方法被调用");
        Book book = new Book(isbn,bookName);
        return book;
    }
}