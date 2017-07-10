package com.youi.business.inspect.web;

import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.inspect.service.InspectPlanService;
import com.youi.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 巡检计划
 */
@RestController
public class InspectPlanController {
    @Autowired
    private InspectPlanService inspectPlanService;
    @Autowired
    private JWTService jwtService;
    /**
     * 获得查询列表
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/ri/plan/query")
    public RestResult queryInspectPlanList(@Userid Long userid, @RequestBody(required = false) Map<String,String> param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object plan = inspectPlanService.queryInspectPlanList(param);
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("plan",plan);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }
    /**
     * 获得查询列表
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/ri/plan/detail")
    public RestResult selectInspectPlanDetail(@Userid Long userid, @RequestBody(required = false) Map<String,String> param) throws Exception {
        //param.put("userid",String.valueOf(userid));
        Long id = Long.parseLong(param.get("id"));
        Object result = inspectPlanService.selectInspectPlanDetail(id);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }

    /**
     * 保存
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/ri/plan/save")
    public RestResult saveInspectPlan(@Userid Long userid,@RequestBody Map<String,Object>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = inspectPlanService.saveInspectPlan(param);
        if(r!=null){
            String token =jwtService.getToken(userid);
            return new RestResult(token,r);
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }

}
