package com.youi.business.activiti.extend;

import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.activiti.engine.impl.interceptor.Session;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wkq on 16/7/13.
 */
public class CustomGroupManagerFactory implements SessionFactory{
    private CustomGroupManager customGroupManager;

    @Override
    public Class<?> getSessionType() {
        //注意此处必须为Activiti原生的类，否则自定义类不会生效
        return GroupIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return customGroupManager;
    }

    @Autowired
    public void setCustomGroupManager(CustomGroupManager customGroupManager) {
        this.customGroupManager = customGroupManager;
    }
}
