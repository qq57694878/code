package com.youi.business.inspect.web;

import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.inspect.service.InspectTemplateService;
import com.youi.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 巡检模板
 */
@RestController
public class InspectTemplateController {
    @Autowired
    private InspectTemplateService inspectTemplateService;
    @Autowired
    private JWTService jwtService;
    /**
     * 获得查询列表
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/ri/tpl/query")
    public RestResult queryInspectTemplateList(@Userid Long userid, @RequestBody(required = false) Map<String,String> param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object list = inspectTemplateService.queryInspectTemplateList(param);
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("tpls",list);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }
    /**
     * 获得查询列表
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/ri/tpl/detail")
    public RestResult selectInspectTemplateDetail(@Userid Long userid, @RequestBody(required = false) Map<String,String> param) throws Exception {
        //param.put("userid",String.valueOf(userid));
        Long id = Long.parseLong(param.get("id"));
        Object result = inspectTemplateService.selectInspectTemplateDetail(id);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }

    /**
     * 保存
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/ri/tpl/save")
    public RestResult saveInspectTemplate(@Userid Long userid,@RequestBody Map<String,Object>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = inspectTemplateService.saveInspectTemplate(param);
        if(r!=null){
            String token =jwtService.getToken(userid);
            return new RestResult(token,r);
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }

}
