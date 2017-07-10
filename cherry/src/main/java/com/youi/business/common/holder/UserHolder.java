package com.youi.business.common.holder;

import com.youi.business.common.entity.SYS_USER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jinliang on 2016/9/6.
 */
public class UserHolder {
    private static Logger logger = LoggerFactory.getLogger(UserHolder.class);
    private static final ThreadLocal<SYS_USER> tl = new ThreadLocal<SYS_USER>();
    public static void setThreadLocalUser(SYS_USER user) {
        tl.set(user);
       // logger.info("user:{}",user);
    }

    public static void removeThreadLocalUser() {
        tl.remove();
      //  logger.info("user removed");
    }

    public static SYS_USER getThreadLocalUser() {
        SYS_USER result = tl.get();
        if (result == null)
            throw new IllegalStateException("需要事先使用 UserHolder.setThreadLocalUser(user) 将 user对象存入，才可以调用 UserHolder.getThreadLocalUser(user) 方法");
        return result;
    }
}
