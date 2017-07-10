package com.youi.business.pim.web;

import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.util.JwtUtil;
import com.youi.business.common.util.MD5Encoder;
import com.youi.business.common.vo.EmptyObject;
import com.youi.business.common.vo.RestResult;
import com.youi.business.common.entity.SYS_USER;
import com.youi.business.pim.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinliang on 2016/7/23.
 * 登录
 */
@RestController
public class LoginController {

    private LoginService loginService;
    @Autowired
    private JWTService jwtService;

    /**
     * 密码加密盐值
     */
    private String salt;
    @Value("${password.salt}")
    public void setSalt(String salt) {
        this.salt = salt;
    }

    @RequestMapping("login")
    public RestResult login(@RequestBody Map<String,String> param){
        String usercode= param.get("username");
        String password = param.get("passwd");
        SYS_USER user = loginService.getUserByUsercode(usercode);
        if(user!=null){
            //用户状态是否有效
            if(!"1".equals(user.getValid_type())){
                return new RestResult(Constants.ERROR_CODE_401);//"用户已经停用!"
            }

            //校验密码
           if(!MD5Encoder.encode(password,this.salt).equals( user.getPassword())){
                return new RestResult(Constants.ERROR_CODE_401);//"密码不正确!"
            }


        }else{
            return new RestResult(Constants.ERROR_CODE_401);//"用户名不存在!"
        }
       String token =  jwtService.getToken(user.getUser_id());
       return  new RestResult(token,new EmptyObject());
    }



    @RequestMapping("userinfo")
    public RestResult userinfo(@Userid Long userid){
        SYS_USER user = loginService.getUserByUserid(userid);
        if(user==null){
            return new RestResult(Constants.ERROR_CODE_401);//"用户名不存在!"
        }
        String token =  jwtService.getToken(user.getUser_id());
        return  new RestResult(token,user);
    }

    /**
     * 注销
     */
    @RequestMapping("logout")
    public RestResult logout() {
           return new RestResult(null,new EmptyObject());
    }

    @RequestMapping("verify")
    public RestResult verify(@Userid Long userid) {
        String token =  jwtService.getToken(userid);
        return  new RestResult(token,new EmptyObject());
    }



    @Resource
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
}
