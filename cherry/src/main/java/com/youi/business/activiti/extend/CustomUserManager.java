package com.youi.business.activiti.extend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.youi.business.common.dao.SysUserDao;
import com.youi.business.common.entity.SYS_USER;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.identity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * Created by wkq on 16/7/13.
 */
@Service
public class CustomUserManager extends UserEntityManager{

    @Autowired
    private SysUserDao sysUserDao;
        @Override
        public UserEntity findUserById(String userId) {
            SYS_USER sys_user = sysUserDao.load(Long.parseLong(userId));
            UserEntity userEntity=new UserEntity();
            userEntity.setId(String.valueOf(sys_user.getUser_id()));
            userEntity.setFirstName(sys_user.getUsername());
            userEntity.setLastName("");
            return userEntity;
        }

        @Override
        public List<Group> findGroupsByUser(final String userid) {
            List<Group> list =sysUserDao.getJdbcTemplate().query("SELECT T1.ORG_ID,T1.ORG_NAME,'assignment' TYPE  FROM SYS_ORG_INFO T1,SYS_USER_ORG T2 WHERE T1.ORG_ID=T2.ORG_ID AND T1.VALID_TYPE='1' AND T2.USERID=? ", new Object[]{userid}, new RowMapper<Group>() {
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


}
