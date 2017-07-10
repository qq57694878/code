package com.youi.business.pim.service;

import com.youi.business.common.dao.SysUserDao;
import com.youi.business.common.entity.SYS_USER;
import com.youi.core.hibernate.HibernatePagingDao;
import com.youi.core.mapper.BeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jinliang on 2016/7/19.
 */
@Service
public class LoginService {
    private HibernatePagingDao hibernatePagingDao;
    private SysUserDao sysUserDao;
    private BeanMapper beanMapper = new BeanMapper();


    @Resource
    public void setHibernatePagingDao(HibernatePagingDao hibernatePagingDao) {
        this.hibernatePagingDao = hibernatePagingDao;
    }
    @Resource
    public void setSysUserDao(SysUserDao sysUserDao) {
        this.sysUserDao = sysUserDao;
    }

    public SYS_USER getUserByUsercode(String usercode) {
        List<SYS_USER> list = sysUserDao.findBy("usercode",usercode);
        if (list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }
    public SYS_USER getUserByUserid(Long userid) {
       return  sysUserDao.get(userid);
    }


}
