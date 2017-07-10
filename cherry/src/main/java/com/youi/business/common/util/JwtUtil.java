package com.youi.business.common.util;

import com.auth0.jwt.JWTSigner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinliang on 2016/7/28.
 */
public class JwtUtil {

    public static String createJwtToken(String secret,Map<String,Object> claims){
        JWTSigner signer = new JWTSigner(secret);
        return  signer.sign(claims);
    }
}
