package com.youi.business.activiti.listener;

import javax.annotation.Resource;


import com.youi.business.activiti.cmd.CompleteTaskWithCommentCmd;
import com.youi.business.activiti.util.TaskUtil;
import com.youi.business.common.Constants;
import com.youi.business.common.dao.SysActLogDao;
import com.youi.business.common.entity.SYS_ACT_LOG;
import com.youi.business.common.holder.UserHolder;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.IdentityLink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 自动完成第一个任务.
 */
public class AutoCompleteFirstTaskListener extends DefaultTaskListener {
    /** logger. */
    private static Logger logger = LoggerFactory
            .getLogger(AutoCompleteFirstTaskListener.class);

    @Autowired
    private SysActLogDao sysActLogDao;


    @Override
    public void onCreate(DelegateTask delegateTask) throws Exception {

        String initiatorId = Authentication.getAuthenticatedUserId();

        if (initiatorId == null) {
            return;
        }

        String assignee = delegateTask.getAssignee();

        if (assignee == null) {
            return;
        }

        PvmActivity targetActivity = this.findFirstActivity(delegateTask.getProcessDefinitionId());

        if (!targetActivity.getId().equals(delegateTask.getExecution().getCurrentActivityId())) {
            return;
        }

        if (!initiatorId.equals(assignee)) {
            return;
        }
        DelegateExecution execution = delegateTask.getExecution();
        long count = execution.getEngineServices().getHistoryService().createHistoricTaskInstanceQuery().executionId(execution.getId()).finished().count();
        if(count>0){
            //说明是回退而来，不能再自动提交第一个任务
            return;
        }

        logger.debug("auto complete first task : {}", delegateTask);

        for (IdentityLink identityLink : delegateTask.getCandidates()) {
            String userId = identityLink.getUserId();
            String groupId = identityLink.getGroupId();

            if (userId != null) {
                delegateTask.deleteCandidateUser(userId);
            }

            if (groupId != null) {
                delegateTask.deleteCandidateGroup(groupId);
            }
        }

        new CompleteTaskWithCommentCmd(delegateTask.getId(), null, "发起流程")
                .execute(Context.getCommandContext());

        //保存第一个流程的日志
        SYS_ACT_LOG act_log = new SYS_ACT_LOG();
        String task_type =execution.getEngineServices().getRepositoryService().getProcessDefinition(execution.getProcessDefinitionId()).getKey();
        act_log.setBiz_id(execution.getProcessBusinessKey());
        act_log.setBiz_table(TaskUtil.getBizTableByTaskType(task_type));
        act_log.setDate(new Date());
        act_log.setExecutor(String.valueOf(UserHolder.getThreadLocalUser().getUser_id()));
        act_log.setInstance_id(execution.getProcessInstanceId());
        act_log.setTask_def_key(task_type);
        act_log.setTask_id(delegateTask.getId());
        act_log.setNode_id(delegateTask.getTaskDefinitionKey());
        act_log.setOpinion("");
        act_log.setType(Constants.TASK_FLAG_NORMAL);
        act_log.setTask_name(delegateTask.getName());
        sysActLogDao.save(act_log);
    }

    /**
     * 获得第一个节点.
     */
    public PvmActivity findFirstActivity(String processDefinitionId) {
        ProcessDefinitionEntity processDefinitionEntity = Context.getProcessEngineConfiguration().getProcessDefinitionCache().get(processDefinitionId);
        ActivityImpl startActivity = processDefinitionEntity.getInitial();

        if (startActivity.getOutgoingTransitions().size() != 1) {
            throw new IllegalStateException(
                    "start activity outgoing transitions cannot more than 1, now is : "
                            + startActivity.getOutgoingTransitions().size());
        }

        PvmTransition pvmTransition = startActivity.getOutgoingTransitions().get(0);
        PvmActivity targetActivity = pvmTransition.getDestination();

        if (!"userTask".equals(targetActivity.getProperty("type"))) {
            logger.debug("first activity is not userTask, just skip");
            return null;
        }

        return targetActivity;
    }

}
