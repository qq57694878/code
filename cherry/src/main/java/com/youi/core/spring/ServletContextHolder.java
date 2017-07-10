package com.youi.core.spring;

import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * 保存ApplicationContext的单例.
 */
public class ServletContextHolder {
    /** instance. */
    private static ServletContextHolder instance = new ServletContextHolder();

    /** ApplicationContext. */
    private ServletContext servletContext;

    /**
     * get ApplicationContext.
     * 
     * @return ApplicationContext
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * set ApplicationContext.
     * 
     * @param servletContext
     *            ApplicationContext
     */
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * get instance.
     * 
     * @return ApplicationContextHolder
     */
    public static ServletContextHolder getInstance() {
        return instance;
    }
}
