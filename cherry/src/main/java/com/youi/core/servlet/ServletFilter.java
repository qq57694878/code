package com.youi.core.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServletFilter extends ProxyFilter {
    private static Logger logger = LoggerFactory.getLogger(ServletFilter.class);
    private Map<String, Servlet> servletMap = Collections.EMPTY_MAP;
    private PathMatcher pathMatcher = new AntPathMatcher();



    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String requestUri = req.getRequestURI();
        String path = requestUri.substring(contextPath.length());
        logger.trace("path : {}", path);

        for (Map.Entry<String, Servlet> entry : servletMap
                .entrySet()) {
            if (isNeedFilter(entry.getKey(),path)) {
                logger.trace("{} match {}", entry.getKey(), path);

                /*PathHttpServletRequestWrapper requestWrapper = new PathHttpServletRequestWrapper(
                        req, entry.getKey());*/
                Servlet servlet = entry.getValue();
                logger.trace("invoke {}", servlet);
                //servlet.service(requestWrapper, response);
                servlet.service(request,response);

                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isNeedFilter(String pattern, String path) {
        if (pathMatcher.match(pattern, path)) {
            return true;
        }
        return false;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        for (Map.Entry<String, Servlet> entry : servletMap
                .entrySet()) {
            Servlet servlet = entry.getValue();
            servlet.init(new ProxyServletConfig(filterConfig
                    .getServletContext()));
        }
    }

    public void destroy() {
        for (Map.Entry<String, Servlet> entry : servletMap
                .entrySet()) {
            Servlet servlet = entry.getValue();
            servlet.destroy();
        }
    }

    public void setServletMap(Map<String, Servlet> servletMap) {
        this.servletMap = servletMap;
    }

}
