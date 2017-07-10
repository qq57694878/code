package com.youi.business.activiti.extend;


import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.activiti.engine.impl.interceptor.Session;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wkq on 16/7/13.
 */
public class CustomUserManagerFactory implements SessionFactory{
    private CustomUserManager customUserManager;

    @Override
    public Class<?> getSessionType() {
        //注意此处也必须为Activiti原生类
        return UserIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return customUserManager;
    }

    @Autowired
    public void setCustomUserManager(CustomUserManager customUserManager) {
        this.customUserManager = customUserManager;
    }
}
