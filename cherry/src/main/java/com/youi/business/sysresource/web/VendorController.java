package com.youi.business.sysresource.web;

import com.youi.business.sysresource.vo.VBussinessApp;
import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.sysresource.service.VendorService;
import com.youi.core.page.Page;
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
public class VendorController {
    @Autowired
    private VendorService vendorService;
    @Autowired
    private JWTService jwtService;


    @RequestMapping("/sysresource/vendor/get-vendor-list")
    public RestResult getVendorCodeTableList(@Userid Long userid)throws Exception {
        List<Map<String,Object>> list = vendorService.getVendorCodeTableList();
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
    @RequestMapping("/sysresource/vendor/init")
    public RestResult getVendorList(@Userid Long userid,@RequestBody Map<String,Integer>param) throws Exception {
        Page page = new Page(param.get("pageno"),param.get("pagesize"),"c_date",Page.DESC);
        Page rpage = vendorService.getVendorList(userid,page);
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("pagetotal",rpage.getTotalCount());
        List<VBussinessApp> list = (List<VBussinessApp>)rpage.getResult();
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
    @RequestMapping("/sysresource/vendor/disable")
    public RestResult disableVendor(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
        if(vendorService.disableVendor(userid,id)){
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
    @RequestMapping("/sysresource/vendor/recovery")
    public RestResult enableVendor(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
        if(vendorService.enableVendor(userid,id)){
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
    @RequestMapping("/sysresource/vendor/detail")
    public RestResult selectVendorDetail(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
       Object result = vendorService.selectVendorDetail(id);
            String token =jwtService.getToken(userid);
            return new RestResult(token,result);
    }

    /**
     * 启用
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/sysresource/vendor/save")
    public RestResult saveVendor(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = vendorService.saveVendor(param);
        if(r!=null){
            String token =jwtService.getToken(userid);
            return new RestResult(token,r);
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }
    /**
     * 获取外协单位负责人
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/sysresource/vendor/get-vender-manager")
    public RestResult getVendorManager(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Map<String,Object> r = vendorService.getVendorManager(param);
        if(r!=null){
            String token =jwtService.getToken(userid);
            return new RestResult(token,r);
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }



}
