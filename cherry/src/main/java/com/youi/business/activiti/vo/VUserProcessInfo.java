package com.youi.business.activiti.vo;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

/**
 * Created by jinliang on 2016/8/6.
 */
public class VUserProcessInfo {

    public VUserProcessInfo(Deployment deployment, ProcessDefinition processDefinition) {
        this.deployment = deployment;
        this.processDefinition = processDefinition;
    }

    private ProcessDefinition processDefinition;

    private Deployment deployment;

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }
}
