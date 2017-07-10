package com.youi.business.sysresource.web;

import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.sysresource.service.BussinessAppService;
import com.youi.business.sysresource.service.HwComputerRoomService;
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
public class HwComputerRoomController {
    @Autowired
    private HwComputerRoomService hwComputerRoomService;
    @Autowired
    private JWTService jwtService;

    @RequestMapping("/sysresource/room/get-room-list")
    public RestResult getComputerRoomCodeTableList(@Userid Long userid) throws Exception {
        List<Map<String,Object>> list = hwComputerRoomService.getComputerRoomCodeTableList();
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
    @RequestMapping("/sysresource/room/init")
    public RestResult getComputerRoomList(@Userid Long userid,@RequestBody Map<String,Integer>param) throws Exception {
        Page page = new Page(param.get("pageno"),param.get("pagesize"),"cdate",Page.DESC);
        Page todoPage = hwComputerRoomService.getComputerRoomList(userid,page);
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
    @RequestMapping("/sysresource/room/disable")
    public RestResult disableComputerRoom(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
        if(hwComputerRoomService.disableComputerRoom(userid,id)){
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
    @RequestMapping("/sysresource/room/recovery")
    public RestResult enableComputerRoom(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
        if(hwComputerRoomService.enableComputerRoom(userid,id)){
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
    @RequestMapping("/sysresource/room/detail")
    public RestResult selectComputerRoomDetail(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
       Object result = hwComputerRoomService.selectComputerRoomDetail(id);
            String token =jwtService.getToken(userid);
            return new RestResult(token,result);
    }

    /**
     * 启用
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/sysresource/room/save")
    public RestResult saveComputerRoom(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = hwComputerRoomService.saveComputerRoom(param);
        if(r!=null){
            String token =jwtService.getToken(userid);
            return new RestResult(token,r);
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }

}
