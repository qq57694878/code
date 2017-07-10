package com.youi.business.activiti.listener;

import com.youi.business.activiti.cmd.CompleteTaskWithCommentCmd;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.IdentityLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 把task参数设置进去
 */
public class ValiableTaskListener extends DefaultTaskListener {
    /** logger. */
    private static Logger logger = LoggerFactory
            .getLogger(ValiableTaskListener.class);
    @Override
    public void onCreate(DelegateTask delegateTask) throws Exception {
        delegateTask.setVariable("delegateTask",delegateTask);

    }



}
