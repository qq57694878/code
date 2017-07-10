package com.youi.business.hardware.web;

import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.hardware.service.HwVmPlatformService;
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
public class HwVmPlatformController {
    @Autowired
    private HwVmPlatformService hwVmPlatformService;
    @Autowired
    private JWTService jwtService;

    @RequestMapping("/hardware/vm_platform/get-vm-platform-list")
    public RestResult getVmPlatformCodeTableList(@Userid Long userid)throws Exception {
        List<Map<String,Object>> list = hwVmPlatformService.getVmPlatformCodeTableList();
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
    @RequestMapping("/hardware/vm_platform/query")
    public RestResult getVmPlatformList(@Userid Long userid,@RequestBody Map<String,Integer>param) throws Exception {
        Page page = new Page(param.get("pageno"),param.get("pagesize"),"ctime",Page.DESC);
        Page todoPage = hwVmPlatformService.getVmPlatformList(userid,page);
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
    @RequestMapping("/hardware/vm_platform/detail")
    public RestResult selectVmPlatformDetail(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
       Object result = hwVmPlatformService.selectVmPlatformDetail(id);
            String token =jwtService.getToken(userid);
            return new RestResult(token,result);
    }

    /**
     * 删除
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/hardware/vm_platform/delete")
    public RestResult deleteVmPlatform(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
        String token =jwtService.getToken(userid);
        try{
           hwVmPlatformService.deleteVmPlatform(userid,id);
                return new RestResult(token,new Object());
        }catch (Exception e){
            Map<String,String> msg = new HashMap<String,String>();
            msg.put("msg","删除失败,此虚拟平台正在被使用");
            return new RestResult(Constants.DEFAULT_VERSION,Constants.ERROR_CODE_200,msg,token);
        }

    }
    /**
     * 启用
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/hardware/vm_platform/save")
    public RestResult saveVmPlatform(@Userid Long userid,@RequestBody Map<String,Object>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = hwVmPlatformService.saveVmPlatform(param);
        if(r!=null){
            String token =jwtService.getToken(userid);
            return new RestResult(token,r);
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }

}
