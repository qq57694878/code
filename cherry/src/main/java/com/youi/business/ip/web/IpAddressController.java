package com.youi.business.ip.web;


import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.ip.service.IpAddressService;
import com.youi.core.page.Page;
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
public class IpAddressController {
    @Autowired
    private IpAddressService ipAddressService;
    @Autowired
    private JWTService jwtService;

    /**
     * 获得查询列表
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/ip/ip_manage/query")
    public RestResult getIpAddressList(@Userid Long userid,@RequestBody(required = false) Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Page todoPage = ipAddressService.getIpAddressList(param);
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("pagetotal",todoPage.getTotalCount());
        result.put("list_data",todoPage.getResult());
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }



    /**
     * 查询详细
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/ip/ip_manage/detail")
    public RestResult selectIpAddressDetail(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
       Object result = ipAddressService.selectIpAddressDetail(id);
            String token =jwtService.getToken(userid);
            return new RestResult(token,result);
    }



    /**
     * 保存
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/ip/ip_manage/save")
    public RestResult saveIpAddress(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = ipAddressService.saveIpAddress(param);
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
    @RequestMapping("/ip/ip_manage/delete")
    public RestResult deleteIpAddress(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
        String token =jwtService.getToken(userid);
        try{
            ipAddressService.deleteIpAddress(userid,id);
            return new RestResult(token,new Object());
        }catch (Exception e){
            Map<String,String> msg = new HashMap<String,String>();
            msg.put("msg","删除失败，此ip地址可能被使用");
            return new RestResult(Constants.DEFAULT_VERSION,Constants.ERROR_CODE_200,msg,token);
        }
    }
    /**
     * 设为主ip
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/ip/ip_manage/set_main_ip")
    public RestResult setMainIpAddress(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        String token =jwtService.getToken(userid);
        try{
            ipAddressService.setMainIpAddress(param);
            return new RestResult(token,new Object());
        }catch (Exception e){
            Map<String,String> msg = new HashMap<String,String>();
            msg.put("msg","删除失败，此ip地址可能被使用");
            return new RestResult(Constants.DEFAULT_VERSION,Constants.ERROR_CODE_200,msg,token);
        }
    }

}
