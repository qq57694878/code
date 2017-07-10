package com.youi.business.monitoring.web;

import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.monitoring.service.MonitoringHostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinliang on 2016/7/19.
 */
@RestController
public class MonitoringHostsController {
    @Autowired
    private MonitoringHostsService monitoringHostsService;
    @Autowired
    private JWTService jwtService;
    /**
     * 查询库监控结果
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/monitoring/hosts")
    public RestResult getHostMonitoringList(@Userid Long userid,@RequestBody(required = false) Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Map<String,Object> r= monitoringHostsService.getHostMonitoringList(param);
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("pagetotal",r.get("pagetotal"));
        result.put("normalNumber",r.get("normalNumber"));//当前总的正常数量
        result.put("warningNumber",r.get("warningNumber"));//当前总的报警数量
        result.put("lastUpdate",r.get("lastUpdate"));//最后监控周期时间戳
        result.put("list",r.get("list"));
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }
    /**
     * 开启监控
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/monitoring/create_host")
    public RestResult createMnotoring(@Userid Long userid,@RequestBody(required = false) Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        monitoringHostsService.createMonitoring(param);
        String token =jwtService.getToken(userid);
        return new RestResult(token,new Object());
    }
    /**
     * 取消监控
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/monitoring/delete_host")
    public RestResult deleteMnotoring(@Userid Long userid,@RequestBody(required = false) Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        monitoringHostsService.deleteMonitoring(param);
        String token =jwtService.getToken(userid);
        return new RestResult(token,new Object());
    }


}
