package com.youi.business.common.interceptor;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.youi.business.common.Constants;
import com.youi.business.common.dao.SysUserDao;
import com.youi.business.common.holder.CurrentWebPath;
import com.youi.business.common.holder.UserHolder;
import com.youi.business.common.vo.RestResult;
import com.youi.core.mapper.JsonMapper;
import com.youi.core.util.IoUtils;
import com.youi.core.util.ToolWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Map;

/**
 * Created by jinliang on 2016/7/28.
 * JWT拦截器
 */
public class JwtInterceptor implements HandlerInterceptor{
    /**
     * jwt验证私钥
     */
    private String secret;

    /**
     * jwt 目标
     */
    private String iss;

    @Autowired
    private SysUserDao sysUserDao;



    private static Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }
    @Value("${jwt.iss}")
    public void setIss(String iss) {
        this.iss = iss;
    }

    private JsonMapper jsonMapper = new JsonMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(Constants.AUTHENTICATION_HEADER);

        try {
            if(token==null){
                String s = jsonMapper.toJson(new RestResult(Constants.ERROR_CODE_401));//"没有token"
                IoUtils.writeString(response.getOutputStream(),s);
            }
            JWTVerifier verifier = new JWTVerifier(this.secret);
            Map<String,Object> claims= verifier.verify(token);
            if(!this.iss.equals(claims.get("iss"))){
                String s = jsonMapper.toJson(new RestResult(Constants.ERROR_CODE_401));//"禁止访问"
                writeResponse(response, s);
                return false;
            }
            long userid = Long.parseLong(String.valueOf(claims.get("userid")));
            long current = System.currentTimeMillis()/1000;
            long ext =  Long.parseLong(String.valueOf(claims.get("exp")));
            if(current>ext){
                String s = jsonMapper.toJson(new RestResult(Constants.ERROR_CODE_401));//"token已过期"
                writeResponse(response, s);
                return false;
            }
            request.setAttribute(Constants.USERID,userid);
            String version = request.getHeader(Constants.VERSION);
            request.setAttribute(Constants.VERSION,version);
            UserHolder.setThreadLocalUser(sysUserDao.load(userid));
        } catch (JWTVerifyException e) {
            String s = jsonMapper.toJson(new RestResult(Constants.ERROR_CODE_401));//"Invalid Token"
            IoUtils.writeString(response.getOutputStream(),s);
            return false;
        } catch (NoSuchAlgorithmException e) {
            String s = jsonMapper.toJson(new RestResult(Constants.ERROR_CODE_401));//"NoSuchAlgorithm"
            writeResponse(response, s);
            return false;
        } catch (SignatureException e) {
            String s = jsonMapper.toJson(new RestResult(Constants.ERROR_CODE_401));//"验签不通过"
            writeResponse(response, s);
            return false;
        } catch (InvalidKeyException e) {
            String s = jsonMapper.toJson(new RestResult(Constants.ERROR_CODE_401));//"Invalid Key"
            writeResponse(response, s);
            return false;
        } catch (Exception e) {
            String s = jsonMapper.toJson(new RestResult(Constants.ERROR_CODE_401));//"other"
            writeResponse(response, s);
            return false;
        }
        CurrentWebPath.setThreadLocalCurrentWebPath(ToolWeb.getContextPath(request));


        return true;
    }
    private void writeResponse(HttpServletResponse response,String s)throws Exception{
        response.setHeader("content-type","application/json;charset=UTF-8");
        IoUtils.writeString(response.getOutputStream(), s);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        CurrentWebPath.removeThreadLocalCurrentWebPath();
        UserHolder.removeThreadLocalUser();
    }
}
