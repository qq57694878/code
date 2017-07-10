package com.youi.business.activiti.vo;

import java.util.Date;
import java.util.Map;

/**
 * Created by jinliang on 2016/8/6.
 */
public class VTodoTaskInfo {

    private String task_id;//'activiti中user task 表主键'
    private String task_type;
    private Map<String,Object> task_info;

    public Map<String, Object> getTask_info() {
        return task_info;
    }

    public void setTask_info(Map<String, Object> task_info) {
        this.task_info = task_info;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }
/*    private String instance_id;//'流程id'
    private String instance_type;//'sw_requirement_change', //见码表

    private String node_id;//'activiti中xml定义的user task节点id',
    private String node_name;//'流程定义中节点显示名字',
    private String title;//'标题',
    private Date stime;//'开始时间',
    private Date etime;//'截止时间'
    private String flag;//'normal' //normal 正常流转 reject 驳回 stop   终止

    public String getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }

    public String getInstance_type() {
        return instance_type;
    }

    public void setInstance_type(String instance_type) {
        this.instance_type = instance_type;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }*/
}
