package com.youi.business.common.holder;

import com.youi.business.common.interceptor.JwtInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jinliang on 2016/8/24.
 */
public class CurrentWebPath {
    private static Logger logger = LoggerFactory.getLogger(CurrentWebPath.class);
    private static final ThreadLocal<String> tl = new ThreadLocal<String>();
    public static void setThreadLocalCurrentWebPath(String webpath) {
        tl.set(webpath);
       // logger.info("setwebpath:{}",webpath);
    }

    public static void removeThreadLocalCurrentWebPath() {
        tl.remove();
       // logger.info("webpath removed");
    }

    public static String getCurrentWebPath() {
        String result = tl.get();
        if (result == null)
            throw new IllegalStateException("需要事先使用 CurrentWebPath.setThreadLocalCurrentWebPath(webpath) 将 webpath对象存入，才可以调用 CurrentWebPath.getCurrentWebPath() 方法");
        return result;
    }
}
