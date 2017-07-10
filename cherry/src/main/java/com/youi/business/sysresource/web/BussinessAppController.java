package com.youi.business.sysresource.web;

import com.youi.business.sysresource.vo.VBussinessApp;
import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.sysresource.service.BussinessAppService;
import com.youi.core.codetable.CodeTableUtil;
import com.youi.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/7/19.
 */
@RestController
public class BussinessAppController {
    @Autowired
    private BussinessAppService bussinessAppService;
    @Autowired
    private JWTService jwtService;
    @RequestMapping("/sysresource/biz_app/get-app-list")
    public RestResult getBussinessAppCodeTableList(@Userid Long userid)throws Exception {
        List<Map<String,Object>> list = bussinessAppService.getBussinessAppCodeTableList();
       /* List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        Map<String,String> map = CodeTableUtil.getCodeMap("BUSINESSAPP");
        if(map!=null){
            for(String key:map.keySet()){
                Map m = new HashMap<String, Object>();
                m.put("code",key);
                m.put("value",map.get(key));
                result.add(m);
            }
        }*/
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("list_data",list);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }

    /**
     * 获得查询列表
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/sysresource/biz_app/init")
    public RestResult getBussinessAppList(@Userid Long userid,@RequestBody Map<String,Integer>param) throws Exception {
        Page page = new Page(param.get("pageno"),param.get("pagesize"),"cdate",Page.DESC);
        Page todoPage = bussinessAppService.getBussinessAppList(userid,page);
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("pagetotal",todoPage.getTotalCount());
        List<VBussinessApp> list = (List<VBussinessApp>)todoPage.getResult();
        result.put("list_data",list);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }
    /**
     * 禁用
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/sysresource/biz_app/disable")
    public RestResult disableBussinessApp(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
        if(bussinessAppService.disableBussinessApp(userid,id)){
            String token =jwtService.getToken(userid);
            return new RestResult(token,new Object());
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }
    /**
     * 启用
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/sysresource/biz_app/recovery")
    public RestResult enableBussinessApp(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
        if(bussinessAppService.enableBussinessApp(userid,id)){
            String token =jwtService.getToken(userid);
            return new RestResult(token,new Object());
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }

    /**
     * 查询详细
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/sysresource/biz_app/detail")
    public RestResult selectBussinessAppDetail(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
       Object result = bussinessAppService.selectBussinessAppDetail(id);
            String token =jwtService.getToken(userid);
            return new RestResult(token,result);
    }

    /**
     * 启用
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/sysresource/biz_app/save")
    public RestResult saveBussinessApp(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = bussinessAppService.saveBussinessApp(param);
        if(r!=null){
            String token =jwtService.getToken(userid);
            return new RestResult(token,r);
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }

}
