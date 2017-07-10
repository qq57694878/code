package com.youi.business.activiti.beans;

import com.youi.business.common.dao.SwDataChangeDao;
import com.youi.business.common.dao.SwProgramDeployDao;
import com.youi.business.common.entity.SW_PROGRAM_DEPLOY;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.Execution;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinliang on 2016/8/21.
 */
@Service("candidateUsersProgramDeploy")
public class CandidateUsersProgramDeploy {
    private Logger logger = LoggerFactory.getLogger(CandidateUsersProgramDeploy.class);
    @Autowired
    private CanditateUsersUtil canditateUsersUtil;
    @Autowired
    private SwProgramDeployDao swProgramDeployDao;
    private boolean debug;

    public boolean isDebug() {
        return debug;
    }
    @Value("${debug.enable}")
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * 信息中心审批
     * @param execution
     * @param creator
     * @return
     */
    public List<String> getStep2Users(DelegateExecution execution, String creator,String roles){
        List<String> list= new ArrayList<String>();
        List<String> r = canditateUsersUtil.getXxzxEmployeList(roles);
        if(r!=null){
            list.addAll(r) ;
        }
        if(debug){
            list.add(creator);
        }
        return list;
    }


    /**
     * 业务部门审批会签
     * @param execution
     * @param creator
     * @return
     */
    public List<String> getStep3Users(DelegateExecution execution,String creator,String roles){
        List<String> list= new ArrayList<String>();
        SW_PROGRAM_DEPLOY sw_program_deploy = swProgramDeployDao.get(Long.parseLong(execution.getProcessBusinessKey()));
        List<String> r = canditateUsersUtil.getBizLeaderList(sw_program_deploy.getOrg_id_list(),roles);
        if(r!=null){
            list.addAll(r) ;
        }
        if(debug){
            list.add(creator);
        }
        return list;
    }
    public Boolean canCompleteStep3(DelegateExecution execution, Integer nrOfInstances,
                               Integer nrOfActiveInstances, Integer nrOfCompletedInstances,
                               Integer loopCounter) {
        return canComplete(execution, 100, nrOfInstances, nrOfActiveInstances,
                nrOfCompletedInstances, loopCounter);
    }

    /**
     * 是否允许结束会签（多实例） 参数的含义请参考用户手册
     */
    public Boolean canComplete(DelegateExecution execution, Integer rate,
                               Integer nrOfInstances, Integer nrOfActiveInstances,
                               Integer nrOfCompletedInstances, Integer loopCounter) {

        logger.debug("execution: {}",ToStringBuilder.reflectionToString(execution));
        logger.debug("rate={}, nrOfInstances={}, nrOfActiveInstances={}, nrOfComptetedInstances={}, loopCounter={}",
                new Object[] { rate, nrOfInstances, nrOfActiveInstances, nrOfCompletedInstances, loopCounter });
        // 计算通过的比例，以此决定是否结束会签
        double completeRate = nrOfCompletedInstances.doubleValue()/ nrOfInstances;
        boolean canComlete = (completeRate * 100) >= rate;
        logger.debug("rate: {}, completeRate: {}, canComlete={}", new Object[] {rate, completeRate, canComlete });
        return canComlete;
    }


    /**
     * 信息中心发布
     * @param execution
     * @param creator
     * @return
     */
    public List<String> getStep4Users(DelegateExecution execution, String creator,String roles){
        List<String> list= new ArrayList<String>();
        List<String> r = canditateUsersUtil.getNodeAssigneeOneList(execution.getProcessInstanceId(),"step2");
        if(r!=null){
            list.addAll(r) ;
        }
        if(debug){
            list.add(creator);
        }
        return list;
    }


}
