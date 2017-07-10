package com.youi.business.activiti.extend;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;


import com.youi.business.common.dao.SysOrgInfoDao;
import org.activiti.engine.identity.Group;

import org.activiti.engine.identity.GroupQuery;

import org.activiti.engine.impl.GroupQueryImpl;

import org.activiti.engine.impl.Page;

import org.activiti.engine.impl.persistence.entity.GroupEntity;

import org.activiti.engine.impl.persistence.entity.GroupEntityManager;

import org.apache.commons.logging.Log;

import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * Created by wkq on 16/7/13.
 */
@Service
public class CustomGroupManager extends GroupEntityManager {

    @Autowired
    private SysOrgInfoDao sysOrgInfoDao;

    @Override
    public List<Group> findGroupsByUser(final String userid) {
        List<Group> list =sysOrgInfoDao.getJdbcTemplate().query("SELECT T1.ORG_ID,T1.ORG_NAME,'assignment' TYPE  FROM SYS_ORG_INFO T1,SYS_USER_ORG T2 WHERE T1.ORG_ID=T2.ORG_ID AND T1.VALID_TYPE='1' AND T2.USERID=? ", new Object[]{userid}, new RowMapper<Group>() {
            @Override
            public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
                Group group = new GroupEntity();
                group.setId(String.valueOf( rs.getLong("org_id")));
                group.setName(rs.getString("org_name"));
                group.setType(rs.getString("type"));
                return group;
            }
        });

        return list;
    }
  /*  public List<Group> findGroupsByUser(final String userCode) {
            List<Group> list=new ArrayList<Group>();
            Group g1=new GroupEntity();
            g1.setId("g1");
            g1.setName("部门1");

            list.add(g1);
            Group g2=new GroupEntity();
            g1.setId("g2");
            g1.setName("部门2");
            list.add(g2);
            return list;
    }*/

}
