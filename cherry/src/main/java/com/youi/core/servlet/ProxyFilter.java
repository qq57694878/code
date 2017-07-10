package com.youi.core.servlet;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ProxyFilter implements Filter {
    private String name;
    private Filter filter;
    private PathMatcher pathMatcher = new AntPathMatcher();
    private Map<String, String> map = Collections.EMPTY_MAP;
    private boolean enable = true;

    private  List<String> includePatterns;

    private  List<String> excludePatterns;

    public void destroy() {
        if (enable) {
            filter.destroy();
        }
    }

    public void init(FilterConfig config) throws ServletException {
        if (enable) {
            ProxyFilterConfig proxyFilterConfig = new ProxyFilterConfig(
                    config.getServletContext());
            proxyFilterConfig.setFilterName(name);
            proxyFilterConfig.setMap(map);
            filter.init(proxyFilterConfig);
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (enable) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String contextPath = req.getContextPath();
            String requestUri = req.getRequestURI();
            String path = requestUri.substring(contextPath.length());
            // 如果都没问题，才会继续进行判断
            if (isNeedFilter(path)) {
                filter.doFilter(request, response, chain);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isNeedFilter(String path){
        if (this.excludePatterns != null) {
            for (String pattern : this.excludePatterns) {
                if (pathMatcher.match(pattern, path)) {
                    return false;
                }
            }
        }
        if (this.includePatterns == null) {
            return true;
        }
        else {
            for (String pattern : this.includePatterns) {
                if (pathMatcher.match(pattern, path)) {
                    return true;
                }
            }
            return false;
        }
    }



    // ~ ==================================================
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }


    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<String> getIncludePatterns() {
        return includePatterns;
    }

    public void setIncludePatterns(List<String> includePatterns) {
        this.includePatterns = includePatterns;
    }

    public List<String> getExcludePatterns() {
        return excludePatterns;
    }

    public void setExcludePatterns(List<String> excludePatterns) {
        this.excludePatterns = excludePatterns;
    }


}
