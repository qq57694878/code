package com.youi.business.hardware.web;

import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.hardware.service.HwX86Service;
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
public class HwX86Controller {
    @Autowired
    private HwX86Service hwX86Service;
    @Autowired
    private JWTService jwtService;




    @RequestMapping("/hardware/x86/get-x86-list-by-main-usage")
    public RestResult getX86CodeTableList(@Userid Long userid,@RequestBody(required = false) Map<String,String>param)throws Exception {
        if(param==null){
            param = new HashMap<String,String>();
        }
        List<Map<String,Object>> list = hwX86Service.getX86CodeTableList(param);
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
    @RequestMapping("/hardware/x86/init")
    public RestResult getX86List(@Userid Long userid,@RequestBody Map<String,Integer>param) throws Exception {
        Page page = new Page(param.get("pageno"),param.get("pagesize"),"cdate",Page.DESC);
        Page todoPage = hwX86Service.getX86List(userid,page);
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
    @RequestMapping("/hardware/x86/delete")
    public RestResult deleteX86(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
        String token =jwtService.getToken(userid);
        try{
            hwX86Service.deleteX86(userid,id);
                return new RestResult(token,new Object());
        }catch (Exception e){
            Map<String,String> msg = new HashMap<String,String>();
            msg.put("msg","删除失败,此设备正在被使用");
            return new RestResult(Constants.DEFAULT_VERSION,Constants.ERROR_CODE_200,msg,token);
        }

    }


    /**
     * 查询详细
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/hardware/x86/detail")
    public RestResult selectX86Detail(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
       Object result = hwX86Service.selectX86Detail(id);
            String token =jwtService.getToken(userid);
            return new RestResult(token,result);
    }

    /**
     * 启用
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/hardware/x86/save")
    public RestResult saveX86(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = hwX86Service.saveX86(param);
        if(r!=null){
            String token =jwtService.getToken(userid);
            return new RestResult(token,r);
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }

}
