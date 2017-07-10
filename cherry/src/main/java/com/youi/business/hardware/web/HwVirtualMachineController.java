package com.youi.business.hardware.web;

import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.hardware.service.HwVirtualMachineService;
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
public class HwVirtualMachineController {
    @Autowired
    private HwVirtualMachineService hwVirtualMachineService;
    @Autowired
    private JWTService jwtService;

    /**
     * 获得查询列表
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/hardware/vm/query")
    public RestResult getVirtualMachineList(@Userid Long userid,@RequestBody Map<String,Integer>param) throws Exception {
        Page page = new Page(param.get("pageno"),param.get("pagesize"),"ctime",Page.DESC);
        Page todoPage = hwVirtualMachineService.getVirtualMachineList(userid,page);
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
    @RequestMapping("/hardware/vm/delete")
    public RestResult deleteVirtualMachine(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
        String token =jwtService.getToken(userid);
        try{
            hwVirtualMachineService.deleteVirtualMachine(userid,id);
                return new RestResult(token,new Object());
        }catch (Exception e){
            Map<String,String> msg = new HashMap<String,String>();
            msg.put("msg","删除失败,此虚拟机已被占用");
            return new RestResult(Constants.DEFAULT_VERSION,Constants.ERROR_CODE_200,msg,token);
        }

    }


    /**
     * 查询详细
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/hardware/vm/detail")
    public RestResult selectVirtualMachineDetail(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
       Object result = hwVirtualMachineService.selectVirtualMachineDetail(id);
            String token =jwtService.getToken(userid);
            return new RestResult(token,result);
    }

    /**
     * 启用
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/hardware/vm/save")
    public RestResult saveVirtualMachine(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = hwVirtualMachineService.saveVirtualMachine(param);
        if(r!=null){
            String token =jwtService.getToken(userid);
            return new RestResult(token,r);
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }

}
