package com.youi.business.activiti.beans;

import com.youi.business.common.dao.SwDataChangeDao;
import com.youi.business.common.dao.SwRequirementChangeDao;
import com.youi.business.common.entity.SW_DATA_QUERY;
import com.youi.business.common.entity.SW_REQUIREMENT_CHANGE;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinliang on 2016/8/21.
 */
@Service("candidateUsersRequirementChange")
public class CandidateUsersRequirementChange {
    @Autowired
    private CanditateUsersUtil canditateUsersUtil;
    @Autowired
    private SwRequirementChangeDao swRequirementChangeDao;
    private boolean debug;

    public boolean isDebug() {
        return debug;
    }
    @Value("${debug.enable}")
    public void setDebug(boolean debug) {
        this.debug = debug;
    }


    /**
     * 申请部门领导审核
     * @param execution
     * @param creator
     * @return
     */
    public List<String> getStep2Users(DelegateExecution execution, String creator,String roles){
        List<String> list= new ArrayList<String>();
        List<String> r = canditateUsersUtil.getLeaderList(Long.parseLong(creator),roles);
        if(r!=null){
            list.addAll(r) ;
        }
        if(debug){
            list.add(creator);
        }
        return list;
    }

    /**
     * 信息中心受理
     * @param execution
     * @param creator
     * @return
     */
    public List<String> getStep3Users(DelegateExecution execution,String creator,String roles){
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
     * 外协单位处理
     * @param execution
     * @param creator
     * @return
     */
    public List<String> getStep4Users(DelegateExecution execution, String creator,String roles){
        List<String> list= new ArrayList<String>();
        SW_REQUIREMENT_CHANGE sw_requirement_change = swRequirementChangeDao.get(Long.parseLong(execution.getProcessBusinessKey()));
        List<String> r = canditateUsersUtil.getVendorLeaderList(String.valueOf(sw_requirement_change.getCompany_id()),roles);
        if(r!=null){
            list.addAll(r) ;
        }
        if(debug){
            list.add(creator);
        }
        return list;
    }

    /**
     * 信息中心审批
     * @param execution
     * @param creator
     * @return
     */
    public List<String> getStep5Users(DelegateExecution execution, String creator,String roles){
        List<String> list= new ArrayList<String>();
        List<String> r = canditateUsersUtil.getNodeAssigneeOneList(execution.getProcessInstanceId(),"step3");
        if(r!=null){
            list.addAll(r) ;
        }
        if(debug){
            list.add(creator);
        }
        return list;
    }

    /**
     * 申请部门验收
     * @param execution
     * @param creator
     * @return
     */
    public List<String> getStep6Users(DelegateExecution execution, String creator,String roles){
        List<String> list= new ArrayList<String>();
        list.add(creator);
        return list;
    }

}
