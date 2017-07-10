package com.youi.business.common.service;

import com.youi.business.pim.vo.Menu;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.query.PropertyFilter;
import com.youi.core.query.PropertyFilterUtils;
import com.youi.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/10/25.
 */
@Service
public class SysUserTreeService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BeanMapper beanMapper;

    /**
     * 获得组织机构和用户树
     * @return
     */
    public List<Map> getOrgUserTreeList() {
        StringBuilder sql = new StringBuilder();
        sql.append(" select CONCAT('1_',t1.user_id) id, t1.username name, CONCAT('0_',t1.org_id)  parentid,'1' type,t1.USER_SORT sort,t1.usercode code");
        sql.append(" from sys_user t1 where t1.VALID_TYPE='1'");
        sql.append("  union all");
        sql.append(" select CONCAT('0_',t2.org_id) id,t2.org_name name,CONCAT('0_',t2.UP_ORG_ID) parentid, '0' type,t2.ORG_ORDER sort,t2.org_code code");
        sql.append(" from sys_org_info t2 where t2.VALID_TYPE='1'");
        sql.append("  order by parentid ,sort");

        List<Map<String, Object>>rlist =  jdbcTemplate.queryForList(sql.toString());
        List<Map> result = new ArrayList<Map>();
        createTreeList(rlist,result,"0_*",0);
        return result;
    }

    private void createTreeList(List<Map<String, Object>> rlist, List<Map> result,Object root,int index) {
        if(rlist!=null){
            for(int i=index;i<rlist.size();i++){
                Map<String, Object> m = rlist.get(i);
                if(root.equals(m.get("parentid"))){
                    Map node = new HashMap();
                    node.put("id",String.valueOf(m.get("id")).substring(2));
                    node.put("name",m.get("name"));
                    node.put("code",m.get("code"));
                    node.put("type",m.get("type"));
                    result.add(node);
                    List<Map> children= new ArrayList<Map>();
                    createTreeList(rlist,children,m.get("id"),i);
                    if(children.size()>0){
                        node.put("children",children);
                    }
                }
            }
        }
    }

    public Object getOrgUserTree(Map<String,Object>param) {
        String with_users = String.valueOf( param.get("with_users"));
        String with_valid = String.valueOf( param.get("with_valid"));
        Map<String,Object> root= (Map<String,Object>)param.get("root");
        String rootId = "0_*";
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        if(root!=null&&root.size()>0){
            String id = String.valueOf(root.get("id"));
            String code = String.valueOf(root.get("code"));
            if(!StringUtils.isEmpty(id)){
                rootId ="0_"+id;
            }
            if(!StringUtils.isEmpty(code)){
                List<Map<String,Object>> rl1 = jdbcTemplate.queryForList("select CONCAT('0_',org_id) id from sys_org_info where org_code =?",new Object[]{code});
               if(rl1!=null&&rl1.size()>0){
                   rootId = String.valueOf(rl1.get(0).get("id"));
               }
            }
        }
        if("0".equals(with_valid)){
            filters.add(new PropertyFilter("EQS_valid_type","1"));
        }
        if("0".equals(with_users)){
            filters.add(new PropertyFilter("EQS_type","0"));
        }
        StringBuilder sql = new StringBuilder();
        sql.append("select id ,name,parentid,type,sort,code,valid_type from(");
        sql.append(" select CONCAT('1_',t1.user_id) id, t1.username name, CONCAT('0_',t1.org_id)  parentid,'1' type,t1.USER_SORT sort,t1.usercode code,t1.valid_type");
        sql.append(" from sys_user t1");
        sql.append("  union all");
        sql.append(" select CONCAT('0_',t2.org_id) id,t2.org_name name,CONCAT('0_',t2.UP_ORG_ID) parentid, '0' type,t2.ORG_ORDER sort,t2.org_code code,t2.valid_type");
        sql.append(" from sys_org_info t2 ");
        sql.append(" ) as t3 ");
        List<Object> params= new ArrayList<Object>();
        PropertyFilterUtils.buildConfigurations(filters,sql,params);
        sql.append("  order by parentid ,sort");
        List<Map<String, Object>>rlist =  jdbcTemplate.queryForList(sql.toString(),params.toArray());
        List<Map> result = new ArrayList<Map>();
        createTreeList(rlist,result,rootId,0);
        return result;
    }
}
