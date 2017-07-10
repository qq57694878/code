package com.youi.business.monitoring.web;

import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.monitoring.service.MonitoringEventService;
import com.youi.business.monitoring.service.MonitoringStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/7/19.
 */
@RestController
public class MonitoringStatController {
    @Autowired
    private MonitoringStatService monitoringStatService;
    @Autowired
    private JWTService jwtService;
    /**
     * 查询库监控结果
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/monitoring/healthindex")
    public RestResult getMonitoringStatInfo(@Userid Long userid,@RequestBody(required = false) Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Map<String,Map<String,Object>> result= monitoringStatService.getMonitoringStatInfo(param);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }

    /**
     * 查询库监控结果
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/monitoring/counting")
    public RestResult getSysCounting(@Userid Long userid,@RequestBody(required = false) Map<String,String>param) throws Exception {
       // param.put("userid",String.valueOf(userid));
        Map<String,Integer> r= monitoringStatService.getSysCounting(param);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("x86",r.get("x86"));//x86服务器数量
        result.put("vm",r.get("vm"));//虚拟机数量
        result.put("db",r.get("db")); //数据库数量
        result.put("netdev",r.get("netdev"));//网络设备数量
        result.put("biz",r.get("biz"));//业务系统
        result.put("wf",r.get("wf"));//已办结流程
        Map<String,Integer> wf_detail = new HashMap<String,Integer>();
        wf_detail.put("sw_requirement_change",r.get("sw_requirement_change"));
        wf_detail.put("sw_data_change",r.get("sw_data_change"));
        wf_detail.put("sw_data_query",r.get("sw_data_query"));
        wf_detail.put("sw_program_deploy",r.get("sw_program_deploy"));
        result.put("wf_detail",wf_detail);//已办结流程
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }



}
