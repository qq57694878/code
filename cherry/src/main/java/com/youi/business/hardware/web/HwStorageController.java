package com.youi.business.hardware.web;

import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.hardware.service.HwStorageService;
import com.youi.business.sysresource.vo.VBussinessApp;
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
public class HwStorageController {
    @Autowired
    private HwStorageService hwStorageService;
    @Autowired
    private JWTService jwtService;

    @RequestMapping("/hardware/storage/get-storage-list-by-main-usage")
    public RestResult getStorageCodeTableList(@Userid Long userid,@RequestBody(required = false) Map<String,String>param)throws Exception {
        if(param==null){
            param = new HashMap<String,String>();
        }
        List<Map<String,Object>> list = hwStorageService.getStorageCodeTableList(param);
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
    @RequestMapping("/hardware/storage/init")
    public RestResult getStorageList(@Userid Long userid,@RequestBody Map<String,Integer>param) throws Exception {
        Page page = new Page(param.get("pageno"),param.get("pagesize"),"cdate",Page.DESC);
        Page todoPage = hwStorageService.getStorageList(userid,page);
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("pagetotal",todoPage.getTotalCount());
        result.put("list_data",todoPage.getResult());
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }
    /**
     * 删除
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/hardware/storage/delete")
    public RestResult deleteStorage(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
        String token =jwtService.getToken(userid);
        try{
            hwStorageService.deleteStorage(userid,id);
                return new RestResult(token,new Object());
        }catch (Exception e){
            Map<String,String> msg = new HashMap<String,String>();
            msg.put("msg","删除失败,此存储正在被使用");
            return new RestResult(Constants.DEFAULT_VERSION,Constants.ERROR_CODE_200,msg,token);
        }

    }


    /**
     * 查询详细
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/hardware/storage/detail")
    public RestResult selectStorageDetail(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
       Object result = hwStorageService.selectStorageDetail(id);
            String token =jwtService.getToken(userid);
            return new RestResult(token,result);
    }

    /**
     * 启用
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/hardware/storage/save")
    public RestResult saveStorage(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = hwStorageService.saveStorage(param);
        if(r!=null){
            String token =jwtService.getToken(userid);
            return new RestResult(token,r);
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }

}
