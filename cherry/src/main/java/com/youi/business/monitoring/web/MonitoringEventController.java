package com.youi.business.monitoring.web;

import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.monitoring.service.MonitoringEventService;
import com.youi.business.monitoring.service.MonitoringHostsService;
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
public class MonitoringEventController {
    @Autowired
    private MonitoringEventService monitoringEventService;
    @Autowired
    private JWTService jwtService;
    /**
     * 查询库监控结果
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/monitoring/zibevent")
    public RestResult getMonitoringEentList(@Userid Long userid,@RequestBody(required = false) Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        List<Map<String,Object>> r= monitoringEventService.getMonitoringEentList(param);
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("events",r);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }



}
