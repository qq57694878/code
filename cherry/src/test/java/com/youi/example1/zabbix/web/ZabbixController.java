package com.youi.example1.zabbix.web;

import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.common.zabbix.api.ZabbixApi;
import com.youi.example1.zabbix.service.ZabbixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinliang on 2016/9/23.
 */
@RestController
public class ZabbixController {

    @Autowired
    private ZabbixService zabbixService;
    @Autowired
    private JWTService jwtService;

    /**
     * 登录
     * @param userid
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping("/zabbix/user/login")
    public RestResult login(@Userid Long userid, @RequestBody Map<String,String> param)throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = zabbixService.login(param);
        String token =jwtService.getToken(userid);
        return new RestResult(token,r);
    }

    /**
     * 退出
     * @param userid
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping("/zabbix/user/logout")
    public RestResult logout(@Userid Long userid, @RequestBody Map<String,String> param)throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = zabbixService.logout(param);
        String token =jwtService.getToken(userid);
        return new RestResult(token,r);
    }

    @RequestMapping("/zabbix/host/get")
    public RestResult getHost(@Userid Long userid, @RequestBody Map<String,Object> param)throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = zabbixService.getHost(param);
        String token =jwtService.getToken(userid);
        return new RestResult(token,r);
    }

    @RequestMapping("/zabbix/host/create")
    public RestResult createHost(@Userid Long userid, @RequestBody Map<String,Object> param)throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = zabbixService.createHost(param);
        String token =jwtService.getToken(userid);
        return new RestResult(token,r);
    }
    @RequestMapping("/zabbix/event/get")
    public RestResult getEvents(@Userid Long userid, @RequestBody Map<String,Object> param)throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = zabbixService.getEvents(param);
        String token =jwtService.getToken(userid);
        return new RestResult(token,r);
    }
    @RequestMapping("/zabbix/trend/get")
    public RestResult getTrends(@Userid Long userid, @RequestBody Map<String,Object> param)throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = zabbixService.getTrends(param);
        String token =jwtService.getToken(userid);
        return new RestResult(token,r);
    }

    @RequestMapping("/zabbix/trigger/get")
    public RestResult getTriggers(@Userid Long userid, @RequestBody Map<String,Object> param)throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = zabbixService.getTriggers(param);
        String token =jwtService.getToken(userid);
        return new RestResult(token,r);
    }
    @RequestMapping("/zabbix/test")
    public RestResult test(@Userid Long userid, @RequestBody Map<String,Object> param)throws Exception {
        param.put("userid",String.valueOf(userid));
        Map r = new HashMap();
/*        r.put("group_biz_id", ZabbixApi.getGroup_biz_id());
        r.put("group_host_id", ZabbixApi.getGroup_host_id());
        r.put("group_db_id", ZabbixApi.getGroup_db_id());
        r.put("template_db_id", ZabbixApi.getTemplate_db_id());
        r.put("template_host_id", ZabbixApi.getTemplate_host_id());
        r.put("template_biz_id", ZabbixApi.getTemplate_biz_id());*/
        String token =jwtService.getToken(userid);
       // r.put("get_events",ZabbixApi.getEvents());
       // r.put("macros",ZabbixApi.getMacros());
    //  r.put("triggers",ZabbixApi.getTriggerInfo(new Object[]{"13997"}))  ;
     //   r.put("rr",ZabbixApi.getTemplate_host_ids(ZabbixApi.TEMPLATE_TYPE_DB_MYSQL));
         r.put("1",ZabbixApi.getTemplate_host_ids(ZabbixApi.TEMPLATE_TYPE_DB_MYSQL));
        //r.put("2",ZabbixKit.login("admin","zabbix"));
       // ZabbixKit.login("admin","zabbix");
        return new RestResult(token,r);
    }


}
