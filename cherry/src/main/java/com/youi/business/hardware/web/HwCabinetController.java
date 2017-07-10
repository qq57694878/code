package com.youi.business.hardware.web;

import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.hardware.service.HwCabinetService;
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
public class HwCabinetController {
    @Autowired
    private HwCabinetService hwCabinetService;
    @Autowired
    private JWTService jwtService;

    @RequestMapping("/hardware/cabinet/get-cabinet-list")
    public RestResult getCabinetCodeTableList(@Userid Long userid)throws Exception {
        List<Map<String,Object>> list = hwCabinetService.getCabinetCodeTableList();
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
    @RequestMapping("/hardware/cabinet/init")
    public RestResult getCabinetList(@Userid Long userid,@RequestBody Map<String,Integer>param) throws Exception {
        Page page = new Page(param.get("pageno"),param.get("pagesize"),"cdate",Page.DESC);
        Page todoPage = hwCabinetService.getCabinetList(userid,page);
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
    @RequestMapping("/hardware/cabinet/delete")
    public RestResult deleteCabinet(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
        String token =jwtService.getToken(userid);
        try{
            hwCabinetService.deleteCabinet(userid,id);
                return new RestResult(token,new Object());
        }catch (Exception e){
            Map<String,String> msg = new HashMap<String,String>();
            msg.put("msg","有设备安装在此机柜无法删除");
            return new RestResult(Constants.DEFAULT_VERSION,Constants.ERROR_CODE_200,msg,token);
        }
    }


    /**
     * 查询详细
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/hardware/cabinet/detail")
    public RestResult selectCabinetDetail(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
       Object result = hwCabinetService.selectCabinetDetail(id);
            String token =jwtService.getToken(userid);
            return new RestResult(token,result);
    }

    /**
     * 启用
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/hardware/cabinet/save")
    public RestResult saveCabinet(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = hwCabinetService.saveCabinet(param);
        if(r!=null){
            String token =jwtService.getToken(userid);
            return new RestResult(token,r);
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }

}
