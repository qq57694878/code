package com.youi.business.activiti.util;

/**
 * Created by jinliang on 2016/8/13.
 */
public class TaskUtil {
    /**
     * 获得业务服务类名
     * @param task_type
     * @return
     */
    public static String getMyTaskServiceName(String task_type){
       if("sw_requirement_change".equals(task_type)){
          return  "RequirementChangeService";
       }else if("sw_data_change".equals(task_type)){
           return  "DataChangeService";
       }else if("sw_data_query".equals(task_type)){
           return  "DataQueryService";
       }else if("sw_program_deploy".equals(task_type)){
           return  "ProgramDeployService";
       }
        return "";
    }

    /**
     * 获得业务表名
     * @param task_type
     * @return
     */
    public static String getBizTableByTaskType(String task_type){
        if("sw_requirement_change".equals(task_type)){
            return  "sw_requirement_change";
        }else if("sw_data_change".equals(task_type)){
            return  "sw_data_change";
        }else if("sw_data_query".equals(task_type)){
            return  "sw_data_query";
        }else if("sw_program_deploy".equals(task_type)){
            return  "sw_program_deploy";
        }
        return "";
    }
}
