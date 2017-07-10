package com.youi.business.activiti.beans;

import com.youi.business.common.util.DevelopKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jinliang on 2016/10/21.
 */
@Component
public class CanditateUsersUtil {

    private final static String SYS_GROUP_ADMIN ="admin";//系统管理员
    private final static String SYS_GROUP_BIZ_EMPLOYE ="biz_employe";//业务部门职员
    private final static String SYS_GROUP_VENDOR_MANAGER ="vendor_manager";//外协公司负责人
    private final static String SYS_GROUP_BIZ_LEADER ="biz_leader";//业务部门领导
    private final static String SYS_GROUP_XXZX_EMPLOYE ="xxzx_employe";//信息中心职员
    private final static String SYS_GROUP_XXZX_LEADER ="xxzx_leader";//信息中心领导





    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询根据申请人userid，查询直属上级领导
     * @param userid 业务部门员工id
     * @param groupCodes 业务部门领导角色code
     * @return
     */
    public List<String> getLeaderList(Long userid,String groupCodes){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT concat(T1.USER_ID,'') user_id  FROM SYS_USER T1 WHERE VALID_TYPE='1'");
        sql.append(" AND EXISTS (SELECT * FROM sys_user_group T2,sys_group T3 WHERE T1.USER_ID=T2.USER_ID AND T2.GROUP_ID = T3.GROUP_ID AND T3.GROUP_CODE in("+DevelopKit.getSqlInString(groupCodes)+"))");
        sql.append("  AND T1.ORG_ID=(SELECT T4.ORG_ID FROM SYS_USER T4 WHERE T4.USER_ID=?)");
        List r1 =jdbcTemplate.queryForList(sql.toString(),String.class,new Object[]{userid});
        return r1;
    }

    /**
     * 通过业务部门ids查询业务部门领导
     * @param ordIdList
     * @param groupCodes
     * @return
     */
    public List<String> getBizLeaderList(String ordIdList,String groupCodes){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT concat(T1.USER_ID,'') user_id  FROM SYS_USER T1 WHERE VALID_TYPE='1'");
        sql.append(" AND EXISTS (SELECT * FROM sys_user_group T2,sys_group T3 WHERE T1.USER_ID=T2.USER_ID AND T2.GROUP_ID = T3.GROUP_ID AND T3.GROUP_CODE in("+DevelopKit.getSqlInString(groupCodes)+"))");
        sql.append("  AND T1.ORG_ID in ("+ordIdList+")");
        List r1 =jdbcTemplate.queryForList(sql.toString(),String.class);
        return r1;
    }


    /**
     * 查询外协单位负责人
     * @param vendorids 外协单位id逗号分隔
     * @param groupCode 外协单位领导角色code
     * @return
     */
    public List<String> getVendorLeaderList(String vendorids,String groupCode){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT concat(T1.USER_ID,'') user_id  FROM SYS_USER T1 WHERE T1.VALID_TYPE='1'");
        sql.append(" AND EXISTS (SELECT * FROM sys_user_group T2,sys_group T3 WHERE T1.USER_ID=T2.USER_ID AND T2.GROUP_ID = T3.GROUP_ID AND T3.GROUP_CODE =?)");
        sql.append("  AND T1.ORG_ID in (SELECT T4.SYS_ORG_INFO_ID FROM RES_VENDOR T4 WHERE T4.ID IN("+vendorids+"))");
        return jdbcTemplate.queryForList(sql.toString(),String.class,new Object[]{groupCode});
    }

    /**
     * 查询信息中心职员有可能包含领导
     * @param groupCodes 信息中心职员和领导角色角色codes
     * @return
     */
    public List<String> getXxzxEmployeList(String groupCodes){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT concat(T1.USER_ID,'') user_id  FROM SYS_USER T1 WHERE T1.VALID_TYPE='1'");
        sql.append(" AND EXISTS (SELECT * FROM sys_user_group T2,sys_group T3 WHERE T1.USER_ID=T2.USER_ID AND T2.GROUP_ID = T3.GROUP_ID AND T3.GROUP_CODE in("+DevelopKit.getSqlInString(groupCodes)+"))");
        return jdbcTemplate.queryForList(sql.toString(),String.class);
    }
    /**
     * 查询业务部门领导或职员
     * @param groupCodes 业务部门领导或职员角色codes
     * @return
     */
    public List<String> getBizEmployeList(String groupCodes){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT concat(T1.USER_ID,'') user_id  FROM SYS_USER T1 WHERE T1.VALID_TYPE='1'");
        sql.append(" AND EXISTS (SELECT * FROM sys_user_group T2,sys_group T3 WHERE T1.USER_ID=T2.USER_ID AND T2.GROUP_ID = T3.GROUP_ID AND T3.GROUP_CODE in("+DevelopKit.getSqlInString(groupCodes)+"))");
        return jdbcTemplate.queryForList(sql.toString(),String.class);
    }

    /**
     * 查询某一步骤之前的执行人，此方法不适用于会签
     * @param instanceid 流程实例id
     * @param nodeid 流程nodeid
     * @return
     */
    public List<String> getNodeAssigneeOneList(String instanceid,String nodeid){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T1.ASSIGNEE_ FROM ACT_HI_TASKINST T1" );
        sql.append(" WHERE T1.PROC_INST_ID_=? AND T1.TASK_DEF_KEY_=? ");
        sql.append(" ORDER BY T1.END_TIME_ DESC LIMIT 1");
        return jdbcTemplate.queryForList(sql.toString(),String.class,new Object[]{instanceid,nodeid});
    }

}
