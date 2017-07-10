package com.youi.business.ip.web;

import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.ip.service.IpAddressGroupService;
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
public class IpAddressGroupController {
    @Autowired
    private IpAddressGroupService ipAddressGroupService;
    @Autowired
    private JWTService jwtService;

    /**
     * 获得查询列表
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/ip/ip_group/query")
    public RestResult getIpAddressGroupList(@Userid Long userid,@RequestBody(required = false) Map<String,String>param) throws Exception {
        Page page = null;
        if(param!=null&&param.get("pageno")!=null){
            page = new Page(Integer.parseInt(param.get("pageno")),Integer.parseInt(param.get("pagesize")),"ctime",Page.DESC);
        }
        Page todoPage = ipAddressGroupService.getIpAddressGroupList(userid,page);
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
    @RequestMapping("/ip/ip_group/detail")
    public RestResult selectIpAddressGroupDetail(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
       Object result = ipAddressGroupService.selectIpAddressGroupDetail(id);
            String token =jwtService.getToken(userid);
            return new RestResult(token,result);
    }

    /**
     * 查询详细
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/ip/ip_group/get_ip_monitoring_detail")
    public RestResult selectIpAddressGroupMonitoringDetail(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
        Object list = ipAddressGroupService.selectIpAddressGroupMonitoringDetail(id);
        String token =jwtService.getToken(userid);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("ip_hosts",list);
        return new RestResult(token,result);
    }

    /**
     * 启用
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/ip/ip_group/save")
    public RestResult saveIpAddressGroup(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = ipAddressGroupService.saveIpAddressGroup(param);
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
    @RequestMapping("/ip/ip_group/delete")
    public RestResult deleteIpAddressGroup(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
        String token =jwtService.getToken(userid);
        try{
            ipAddressGroupService.deleteIpAddressGroup(userid,id);
            return new RestResult(token,new Object());
        }catch (Exception e){
            Map<String,String> msg = new HashMap<String,String>();
            msg.put("msg","删除失败，此ip地址段可能被使用");
            return new RestResult(Constants.DEFAULT_VERSION,Constants.ERROR_CODE_200,msg,token);
        }
    }

}
