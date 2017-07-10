package com.youi.core.spring;

import org.springframework.beans.BeansException;


import javax.servlet.ServletContext;

/**
 * 在ServletContext环境外获取bean的工具类.
 */
public class ServletContextHelper{
    /**
     * 向ServletContextHolder里设置ServletContext.
     * 
     * @param ServletContext
     *
     */
    public static void setServletContext(ServletContext ServletContext)
            throws BeansException {
        ServletContextHolder.getInstance().setServletContext(
                ServletContext);
    }

    /**
     * 获得ServletContext.
     * 
     * @return ServletContext
     */
    public static ServletContext getServletContext() {
        return ServletContextHolder.getInstance().getServletContext();
    }

}
