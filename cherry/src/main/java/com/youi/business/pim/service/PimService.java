package com.youi.business.pim.service;

import com.youi.business.pim.vo.Menu;
import com.youi.core.hibernate.HibernatePagingDao;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import org.apache.commons.collections.list.TreeList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/7/19.
 */
@Service
public class PimService {
    private HibernatePagingDao hibernatePagingDao;
    private JdbcTemplate jdbcTemplate;
    private BeanMapper beanMapper = new BeanMapper();
    @Resource
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Resource
    public void setHibernatePagingDao(HibernatePagingDao hibernatePagingDao) {
        this.hibernatePagingDao = hibernatePagingDao;
    }


    public List<Menu> getMenus(Long userid) {
        String sql ="select t3.PRIVILEGE_ID,t3.MENU_NAME,t3.URL,t3.PARENTID,t3.IMAGE" +
                " from sys_privilege t3" +
                " where t3.PRIVILEGE_ID in (select t2.PRIVILEGE_ID from  sys_user_group t1,sys_group_privilege t2" +
                " where t1.GROUP_ID=t2.GROUP_ID " +
                " and t1.USER_ID=? )" +
                " order by t3.PARENTID,t3.SORT";
        List<Map<String, Object>>rlist =  jdbcTemplate.queryForList(sql,new Object[]{userid});
        List<Menu> result = new ArrayList<Menu>();
        createTreeList(rlist,result,Integer.parseInt("0"),0);
        return result;
    }

    private void createTreeList(List<Map<String, Object>> rlist, List<Menu> result,Object root,int index) {
        if(rlist!=null){
            for(int i=index;i<rlist.size();i++){
                Map<String, Object> m = rlist.get(i);
                if(root.equals(m.get("PARENTID"))){
                    Menu menu = new Menu();
                    menu.setName((String)m.get("MENU_NAME"));
                    menu.setPath((String)m.get("URL"));
                    menu.setIcon((String)m.get("IMAGE"));
                    List<Menu> children= new ArrayList<Menu>();
                    menu.setChildren(children);
                    result.add(menu);
                    createTreeList(rlist,children,m.get("PRIVILEGE_ID"),i);
                }
            }
        }
    }


    public Page test1(String userid) {
        String sql ="select t3.PRIVILEGE_ID,t3.MENU_NAME,t3.URL,t3.PARENTID" +
                " from sys_privilege t3" +
                " where t3.PRIVILEGE_ID in (select t2.PRIVILEGE_ID from  sys_user_group t1,sys_group_privilege t2" +
                " where t1.GROUP_ID=t2.GROUP_ID " +
                " and t1.USER_ID=? )" +
                " order by t3.PARENTID,t3.SORT";
        Page p =  hibernatePagingDao.pagedSQLQuery(sql,1,10,new Object[]{userid});
        return p;
    }
}
