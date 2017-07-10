package com.youi.business.ehcache;

import com.youi.base.TestBase;
import com.youi.base.TestRestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by GWCheng on 2016/3/3.
 */

public class TestCacheService extends TestBase{
    @Autowired
    private CacheService cacheService;

    @Test
    public void testCacheable() {
        Book book = null;
        for (int i = 0; i < 10; i++) {
            book = cacheService.findBook("456");
        }
        System.out.println("bookName= "+book.getBookName());
        for (int i = 0; i < 10; i++) {
            cacheService.updateBook("456", "Spring 3.x企业应用开发实战");
        }
        book = cacheService.findBook("456");
        System.out.println("bookName= "+book.getBookName());
    }
}