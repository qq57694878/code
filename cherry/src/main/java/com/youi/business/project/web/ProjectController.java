package com.youi.business.project.web;

import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.ip.service.IpAddressService;
import com.youi.business.project.service.ProjectService;
import com.youi.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *项目管理
 */
@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private JWTService jwtService;

    /**
     * 获得查询列表
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/project/query")
    public RestResult queryProjectList(@Userid Long userid, @RequestBody(required = false) Map<String,String> param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Page page = projectService.queryProjectList(param);
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("pagetotal",page.getTotalCount());
        result.put("projects",page.getResult());
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }
    /**
     * 获得查询列表
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/project/detail")
    public RestResult selectProjectDetail(@Userid Long userid, @RequestBody(required = false) Map<String,String> param) throws Exception {
        //param.put("userid",String.valueOf(userid));
        Long id = Long.parseLong(param.get("id"));
        Object result = projectService.selectProjectDetail(id);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }

    /**
     * 保存
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/project/save")
    public RestResult saveProject(@Userid Long userid,@RequestBody Map<String,Object>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        if("1".equals(param.get("is_submit"))){
            param.put("status","1");
        }else{
            param.put("status","0");
        }
        Object r = projectService.saveProject(param);
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
    @RequestMapping("/project/delete")
    public RestResult deleteProject(@Userid Long userid,@RequestBody Map<String,Object>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        String token =jwtService.getToken(userid);
        try{
            projectService.deleteProject(param);
            return new RestResult(token,new Object());
        }catch (Exception e){
            Map<String,String> msg = new HashMap<String,String>();
            msg.put("msg","删除项目失败，该项目已被关联使用");
            return new RestResult(Constants.DEFAULT_VERSION,Constants.ERROR_CODE_200,msg,token);
        }
    }


}
