package com.youi.business.activiti.service;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by jinliang on 2016/8/13.
 */
public interface UserTaskService {
    /**
     * 获得用户任务业务数据
     * @param task_id
     * @param bussinessid
     * @return
     */
    public Map<String,Object> getTaskFormData(String task_id,String task_type, String bussinessid,String bussiness_type)throws  Exception;

    /**
     * 提交
     * @param task_id
     * @param formData
     * @return
     */
    public boolean completeTaskFormData(String task_id, Map<String,Object> formData, MultiValueMap<String, MultipartFile> multipartFiles)throws  Exception;

    /**
     * 驳回 任务
     * @param param
     * @return
     */
    public boolean rejectTask(Map<String,Object> param, MultiValueMap<String, MultipartFile> multipartFiles)throws  Exception;
    /**
     * 终止 任务
     * @param param
     * @return
     */
    public boolean stopTask(Map<String,Object> param, MultiValueMap<String, MultipartFile> multipartFiles)throws  Exception;

    /**
     * 开启 任务
     * @param param
     * @return
     */
    public boolean startTask(Map<String,Object> param,MultiValueMap<String, MultipartFile> multipartFiles)throws  Exception;

    /**
     * 获得提交的参数
     * @param task_id
     * @param formData
     * @return
     * @throws Exception
     */
  //  public Map<String, Object> getCommitVariables(String taskid, Map<String,Object> formData)throws  Exception;

}
