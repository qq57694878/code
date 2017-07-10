package com.youi.business.project.web;

import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.project.service.ProcurementService;
import com.youi.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目采购
 */
@RestController
public class ProcurementController {
    @Autowired
    private ProcurementService procurementService;
    @Autowired
    private JWTService jwtService;
    /**
     * 获得查询列表
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/procurement/query")
    public RestResult queryProcurementList(@Userid Long userid, @RequestBody(required = false) Map<String,String> param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Page page = procurementService.queryProcurementList(param);
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("pagetotal",page.getTotalCount());
        result.put("projects",page.getResult());
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }
    /**
     * 获得查询列表
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/procurement/detail")
    public RestResult selectProcurementDetail(@Userid Long userid, @RequestBody(required = false) Map<String,String> param) throws Exception {
        //param.put("userid",String.valueOf(userid));
        Long id = Long.parseLong(param.get("id"));
        Object result = procurementService.selectProcurementDetail(id);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }

    /**
     * 保存
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/procurement/save")
    public RestResult saveProcurement(@Userid Long userid,@RequestBody Map<String,Object>param) throws Exception {
        param.put("userid",String.valueOf(userid));

        Object r = procurementService.saveProcurement(param);
        if(r!=null){
            String token =jwtService.getToken(userid);
            return new RestResult(token,r);
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }
    /**
     * 删除
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/procurement/delete")
    public RestResult deleteProcurement(@Userid Long userid,@RequestBody Map<String,Object>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        String token =jwtService.getToken(userid);
        try{
            procurementService.deleteProcurement(param);
            return new RestResult(token,new Object());
        }catch (Exception e){
            Map<String,String> msg = new HashMap<String,String>();
            msg.put("msg","删除项目失败，该项目已被关联使用");
            return new RestResult(Constants.DEFAULT_VERSION,Constants.ERROR_CODE_200,msg,token);
        }
    }
}
