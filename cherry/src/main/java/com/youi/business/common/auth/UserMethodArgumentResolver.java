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
public class UserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果参数类型是User并且有CurrentUser注解则支持
        if (parameter.hasParameterAnnotation(Userid.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //取出鉴权时存入的登录用户Id
        Long currentUserId = (Long) webRequest.getAttribute(Constants.USERID, RequestAttributes.SCOPE_REQUEST);
        if (currentUserId != null) {
            return currentUserId;
        }
        throw new MissingServletRequestPartException(Constants.USERID);
    }
}
