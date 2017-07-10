package com.youi.business.activiti.web;

import com.youi.business.activiti.service.MyTaskService;
import com.youi.business.activiti.vo.VTodoTaskInfo;
import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.store.StoreDTO;
import com.youi.business.common.vo.RestResult;
import com.youi.core.page.Page;
import com.youi.core.store.MultipartHandler;
import com.youi.core.util.StringUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;

/**
 * Created by jinliang on 2016/8/6.
 */
@RestController
public class MyTaskController {
    private static Logger logger = LoggerFactory
            .getLogger(MyTaskController.class);
    @Autowired
    private MyTaskService myTaskService;
    @Autowired
    private JWTService jwtService;
    private MultipartResolver multipartResolver;
    @Resource
    public void setMultipartResolver(MultipartResolver multipartResolver) {
        this.multipartResolver = multipartResolver;
    }


    /**
     * 获得代办任务列表
     * @param userid
     * @return
     */
    @RequestMapping("/mywork/task/waiting")
    public RestResult getTodoTaskList(@Userid Long userid,@RequestBody Map<String,Integer>param)throws Exception {
        Page page = new Page(param.get("pageno"),param.get("pagesize"),"ctime",Page.ASC);
        Page todoPage = myTaskService.getTodoTaskList(userid,page);
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("pagetotal",todoPage.getTotalCount());
        List<VTodoTaskInfo> list = (List<VTodoTaskInfo>)todoPage.getResult();
        result.put("tasks",list);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }


    /**
     * 获得已办任务列表
     * @param userid
     * @return
     */
    @RequestMapping("/mywork/task/history")
    public RestResult getHasdoTaskList(@Userid Long userid,@RequestBody Map<String,Object>param)throws Exception {
       // Page page = new Page(param.get("pageno"),param.get("pagesize"),"ctime",Page.ASC);
        param.put("userid",String.valueOf(userid));
        Page todoPage = myTaskService.getHasdoTaskList(param);
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("pagetotal",todoPage.getTotalCount());
        List<VTodoTaskInfo> list = (List<VTodoTaskInfo>)todoPage.getResult();
        result.put("tasks",list);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }
//  @RequestBody Map<String,Object> paramMap
    @RequestMapping("/task/{task_type}/start")
    public RestResult startTask(@Userid Long userid,@PathVariable("task_type") String task_type,HttpServletRequest request) throws Exception{
        MultipartHandler multipartHandler = new MultipartHandler(this.multipartResolver);
        multipartHandler.handle(request);
        logger.info("{}", multipartHandler.getMultiValueMap());
        logger.info("{}", multipartHandler.getMultiFileMap());
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(multipartHandler.getMultiValueMap()!=null){
            for (Map.Entry<String, List<String>> entry : multipartHandler.getMultiValueMap().entrySet()) {
                String key = entry.getKey();
                List<String> values = entry.getValue();
                if ((values == null) || (values.isEmpty())) {
                    continue;
                }
                String value = values.get(0);
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                paramMap.put(key,value);
            }
        }
        paramMap.put("userid",String.valueOf(userid));
        paramMap.put("task_type",String.valueOf(task_type));
        if(myTaskService.startTask(paramMap,multipartHandler.getMultiFileMap())){
            String token =jwtService.getToken(userid);
            return new RestResult(token,new Object());
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }

    /**
     * 获得任务详细
     * @param param
     * @return
     */
    @RequestMapping("/task/{task_type}/get")
    public RestResult getTaskDetail(@Userid Long userid,@PathVariable("task_type") String task_type,@RequestBody Map<String,String> param, HttpServletRequest request) throws Exception{
        param.put("userid",String.valueOf(userid));
        param.put("ctx",(String)request.getAttribute("ctx"));
        //获得工作流详细
        Object result=  myTaskService.getTaskDetail(param);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }
    /**
     * 提交任务
     * @param userid
     * @return
     */
    @RequestMapping("/task/{task_type}/submit")
    public RestResult submitTask(@Userid Long userid,@PathVariable("task_type") String task_type, @RequestBody Map<String,Object> paramMap,HttpServletRequest request) throws Exception {
       /* paramMap.put("userid",String.valueOf(userid));
        if(myTaskService.submitTask(paramMap)){
            String token =jwtService.getToken(userid);
            return new RestResult(token,new Object());
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }*/
        MultipartHandler multipartHandler = new MultipartHandler(this.multipartResolver);
        multipartHandler.handle(request);
        logger.info("{}", multipartHandler.getMultiValueMap());
        logger.info("{}", multipartHandler.getMultiFileMap());
        if(paramMap==null){
            paramMap = new HashMap<String,Object>();
        }
        if(multipartHandler.getMultiValueMap()!=null){
            for (Map.Entry<String, List<String>> entry : multipartHandler.getMultiValueMap().entrySet()) {
                String key = entry.getKey();
                List<String> values = entry.getValue();
                if ((values == null) || (values.isEmpty())) {
                    continue;
                }
                String value = values.get(0);
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                paramMap.put(key,value);
            }
        }
        paramMap.put("userid",String.valueOf(userid));
        paramMap.put("task_type",String.valueOf(task_type));
        if(myTaskService.submitTask(paramMap,multipartHandler.getMultiFileMap())){
            String token =jwtService.getToken(userid);
            return new RestResult(token,new Object());
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }

    /**
     * 驳回任务
     * @param userid
     * @return
     */
    @RequestMapping("/task/{task_type}/return")
    public RestResult rejectTask(@Userid Long userid,@PathVariable("task_type") String task_type,@RequestBody Map<String,Object> paramMap,HttpServletRequest request)throws Exception {
      /*  paramMap.put("userid",String.valueOf(userid));
        if(myTaskService.rejectTask(paramMap)){
            String token =jwtService.getToken(userid);
            return new RestResult(token,new Object());
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }*/
        MultipartHandler multipartHandler = new MultipartHandler(this.multipartResolver);
        multipartHandler.handle(request);
        logger.info("{}", multipartHandler.getMultiValueMap());
        logger.info("{}", multipartHandler.getMultiFileMap());
        if(paramMap==null){
            paramMap = new HashMap<String,Object>();
        }
        if(multipartHandler.getMultiValueMap()!=null){
            for (Map.Entry<String, List<String>> entry : multipartHandler.getMultiValueMap().entrySet()) {
                String key = entry.getKey();
                List<String> values = entry.getValue();
                if ((values == null) || (values.isEmpty())) {
                    continue;
                }
                String value = values.get(0);
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                paramMap.put(key,value);
            }
        }
        paramMap.put("userid",String.valueOf(userid));
        paramMap.put("task_type",String.valueOf(task_type));
        if(myTaskService.rejectTask(paramMap,multipartHandler.getMultiFileMap())){
            String token =jwtService.getToken(userid);
            return new RestResult(token,new Object());
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }


    /**
     * 终止任务
     */
    @RequestMapping("/task/{task_type}/stop")
    public RestResult stopTask(@Userid Long userid,@PathVariable("task_type") String task_type, @RequestBody Map<String,Object> paramMap,HttpServletRequest request) throws Exception {
        /*paramMap.put("userid",String.valueOf(userid));
        if(myTaskService.stopTask(paramMap)){
            String token =jwtService.getToken(userid);
            return new RestResult(token,new Object());
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }*/
        MultipartHandler multipartHandler = new MultipartHandler(this.multipartResolver);
        multipartHandler.handle(request);
        logger.info("{}", multipartHandler.getMultiValueMap());
        logger.info("{}", multipartHandler.getMultiFileMap());
        if(paramMap==null){
            paramMap = new HashMap<String,Object>();
        }
        if(multipartHandler.getMultiValueMap()!=null){
            for (Map.Entry<String, List<String>> entry : multipartHandler.getMultiValueMap().entrySet()) {
                String key = entry.getKey();
                List<String> values = entry.getValue();
                if ((values == null) || (values.isEmpty())) {
                    continue;
                }
                String value = values.get(0);
                if (StringUtils.isEmpty(value)) {
                    continue;
                }
                paramMap.put(key,value);
            }
        }
        paramMap.put("userid",String.valueOf(userid));
        paramMap.put("task_type",String.valueOf(task_type));
        if(myTaskService.stopTask(paramMap,multipartHandler.getMultiFileMap())){
            String token =jwtService.getToken(userid);
            return new RestResult(token,new Object());
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }
    /**
     * 读取带跟踪的图片
     */
    @RequestMapping(value = "/exclude/flowchart/{processInstanceId}")
    public ResponseEntity<byte[]> readDiagram(@PathVariable("processInstanceId") String processInstanceId, HttpServletResponse response)
            throws Exception {
            InputStream imageStream = myTaskService.readDiagram(processInstanceId);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "image/png");
            return new ResponseEntity<byte[]>(IOUtils.toByteArray(imageStream), responseHeaders, HttpStatus.OK);
        // 输出资源内容到相应对象
   /*   byte[] b = new byte[1024];
        int len;
        while ((len = imageStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }*/
    }



    /**
     * 上传任务
     * @param userid
     * @return
     */
    @RequestMapping("/file/upload")
    public Object uploadAttachment(@Userid Long userid,HttpServletRequest request,HttpServletResponse response)throws Exception {
        try{
            MultipartHandler multipartHandler = new MultipartHandler(this.multipartResolver);
            multipartHandler.handle(request);
            logger.info("{}", multipartHandler.getMultiValueMap());
            logger.info("{}", multipartHandler.getMultiFileMap());
            Map<String,Object>  paramMap = new HashMap<String,Object>();
            if(multipartHandler.getMultiValueMap()!=null){
                for (Map.Entry<String, List<String>> entry : multipartHandler.getMultiValueMap().entrySet()) {
                    String key = entry.getKey();
                    List<String> values = entry.getValue();
                    if ((values == null) || (values.isEmpty())) {
                        continue;
                    }
                    String value = values.get(0);
                    if (StringUtils.isEmpty(value)) {
                        continue;
                    }
                    paramMap.put(key,value);
                }
            }
            paramMap.put("userid",String.valueOf(userid));
            List<StoreDTO> list = myTaskService.uploadAttachment(paramMap,multipartHandler.getMultiFileMap());
            if(list!=null&&list.size()>0){
                List<Map<String,Object>> rlist = new ArrayList<Map<String,Object>>();
                for(StoreDTO dto:list){
                    Map<String,Object> m = new HashMap<String,Object>();
                    m.put("url",dto.getUrl());
                    m.put("file_type",dto.getFile_type());
                    m.put("file_name",dto.getShow_name());
                    m.put("file_id",String.valueOf(dto.getId()));
                    rlist.add(m);
                }

                String token =jwtService.getToken(userid);
                Map<String,Object> result = new HashMap<String,Object>();
                result.put("attachments",rlist);
                return new RestResult(token,result);
            }else{
                return new RestResult(Constants.ERROR_CODE_500);
            }
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Map<String,Object> r= new HashMap<String,Object>();
            r.put("ver",Constants.DEFAULT_VERSION);
            r.put("error",Constants.ERROR_CODE_500);
            r.put("token","");
            r.put("data",new Object());
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }

    /**
     * 获得任务详细
     * @param param
     * @return
     */
    @RequestMapping("/file/delete")
    public RestResult removeAttachment(@Userid Long userid,@RequestBody Map<String,Object> param) throws Exception{
        param.put("userid",String.valueOf(userid));
        //获得工作流详细
        myTaskService.removeAttachment(param);
        String token =jwtService.getToken(userid);
        return new RestResult(token,new Object());
    }

}
