package com.youi.core.servlet;

import com.youi.core.util.ToolWeb;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jinliang on 2016/7/7.
 */

public class BeforeInvocationSettingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //设置路径
        String ctx = ToolWeb.getContextPath(request);
        req.setAttribute("ctx", ctx);
        req.setAttribute("param", ToolWeb.getParamMap(request));
        req.setAttribute("locate", "zh_CN");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
