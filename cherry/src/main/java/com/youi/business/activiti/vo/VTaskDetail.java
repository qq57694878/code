package com.youi.business.activiti.vo;

import java.util.Date;
import java.util.Map;

/**
 * Created by jinliang on 2016/8/13.
 */
public class VTaskDetail {
    private String instance_id;//'流程id'
    private String task_type;//'sw_requirement_change', //见码表
    private String task_id;//'activiti中user task 表主键',
    private String node_id;//'activiti中xml定义的user task节点id',
    private String node_name;//'流程定义中节点显示名字',
    private String flag;//'normal' //normal 正常流转 reject 驳回 stop   终止
    private Map<String,Object> form_data;//用户数据
    private Map<String,Boolean> actions;//当前节点可以操作的功能
    private Map<String,Object> history;// 历史

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public Map<String, Object> getForm_data() {
        return form_data;
    }

    public void setForm_data(Map<String, Object> form_data) {
        this.form_data = form_data;
    }

    public Map<String, Boolean> getActions() {
        return actions;
    }

    public void setActions(Map<String, Boolean> actions) {
        this.actions = actions;
    }

    public Map<String, Object> getHistory() {
        return history;
    }

    public void setHistory(Map<String, Object> history) {
        this.history = history;
    }

    public String getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
