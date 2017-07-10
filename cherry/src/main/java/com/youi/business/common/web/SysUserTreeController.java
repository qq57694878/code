package com.youi.business.common.web;

import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.service.SysUserTreeService;
import com.youi.business.common.vo.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinliang on 2016/10/25.
 */
@RestController
public class SysUserTreeController {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private SysUserTreeService sysUserTreeService;

    @RequestMapping("/common/get_users_tree")
    public RestResult getOrgUserTreeList(@Userid Long userid) {
        Map<String,Object> r = new HashMap<String,Object>();
        r.put("users", sysUserTreeService.getOrgUserTreeList());
        String token = jwtService.getToken(userid);
        return new RestResult(token,r);
    }
    @RequestMapping("/organization/gettree")
    public RestResult getOrgUserTree(@Userid Long userid,@RequestBody Map<String,Object>param) {
        param.put("userid",String.valueOf(userid));
        Map<String,Object> r = new HashMap<String,Object>();
        r.put("root", sysUserTreeService.getOrgUserTree(param));
        String token = jwtService.getToken(userid);
        return new RestResult(token,r);
    }
}
