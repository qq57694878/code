package com.youi.core.init;

import com.youi.core.codetable.ModifyCodeTable;
import com.youi.core.spring.ServletContextHelper;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by jinliang on 2016/7/6.
 */
public class InitListener implements ServletContextListener {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(InitListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("码表加载start");
        ModifyCodeTable m = new ModifyCodeTable();
        m.loadCodeTable();
        logger.info("码表加载end");
        //全局的ServletContext
        ServletContextHelper.setServletContext( sce.getServletContext());

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
