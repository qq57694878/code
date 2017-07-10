package com.youi.business.common.auth;

import com.youi.business.common.Constants;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * Created by jinliang on 2016/7/29.
 */
public class VersionMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果参数类型是User并且有CurrentUser注解则支持
        if (parameter.hasParameterAnnotation(Version.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //取出鉴权时存入的登录用户Id
        String version = (String) webRequest.getAttribute(Constants.VERSION, RequestAttributes.SCOPE_REQUEST);
        if (version != null) {
            return version;
        }
        throw new MissingServletRequestPartException(Constants.VERSION);
    }
}
