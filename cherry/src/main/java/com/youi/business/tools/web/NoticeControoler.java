package com.youi.business.tools.web;

import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;

import com.youi.business.sysresource.vo.VBussinessApp;
import com.youi.business.tools.service.NoticeService;
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
 * Created by jinliang on 2016/9/28.
 */
@RestController
public class NoticeControoler {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private JWTService jwtService;

    /**
     * 获得查询列表
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/tools/notice/get_unread_notice_list")
    public RestResult getUnreadNoticeList(@Userid Long userid) throws Exception {
        List list = noticeService.getUnreadNoticeList(userid);
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("list_data",list);
        result.put("number",list.size());
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }
    /**
     * 发送通知
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/tools/notice/send_notice")
    public RestResult sendNotice(@Userid Long userid,@RequestBody Map<String,Object>param) throws Exception {
        param.put("cuserid",String.valueOf(userid));
        if(noticeService.sendNotice(param)){
            String token =jwtService.getToken(userid);
            return new RestResult(token,new Object());
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }

    /**
     * 查询发送的通知
     * @param userid
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping("/tools/notice/query_send_notice")
    public RestResult querySendNotice(@Userid Long userid,@RequestBody Map<String,Integer>param)throws Exception {
        Page page = new Page(param.get("pageno"),param.get("pagesize"),"ctime",Page.DESC);
        Page todoPage = noticeService.querySendNotice(userid,page);
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("pagetotal",todoPage.getTotalCount());
        List<VBussinessApp> list = (List<VBussinessApp>)todoPage.getResult();
        result.put("list_data",list);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }
    /**
     * 查询接收的通知
     * @param userid
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping("/tools/notice/query_receive_notice")
    public RestResult queryReceiveNotice(@Userid Long userid,@RequestBody Map<String,Integer>param)throws Exception {
        Page page = new Page(param.get("pageno"),param.get("pagesize"),"ctime",Page.DESC);
        Page todoPage = noticeService.queryReceiveNotice(userid,page);
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("pagetotal",todoPage.getTotalCount());
        List<VBussinessApp> list = (List<VBussinessApp>)todoPage.getResult();
        result.put("list_data",list);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }

    /**
     * 查询通知详细
     * @param userid
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping("/tools/notice/detail")
    public RestResult selectNoticeDetail(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
        Object result = noticeService.selectNoticeDetail(id);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }

    /**
     * 读通知
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/tools/notice/read_notice")
    public RestResult readNotice(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        Long id = Long.parseLong(param.get("id"));
        if(noticeService.readNotice(id)){
            String token =jwtService.getToken(userid);
            return new RestResult(token,new Object());
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }
    /**
     * 删除通知
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/tools/notice/delete")
    public RestResult deleteNotice(@Userid Long userid,@RequestBody Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        if(noticeService.deleteNotice(param)){
            String token =jwtService.getToken(userid);
            return new RestResult(token,new Object());
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }
}
