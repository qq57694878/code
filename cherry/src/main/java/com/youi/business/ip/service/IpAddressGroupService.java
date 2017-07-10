package com.youi.business.ip.service;

import com.youi.business.common.dao.IpAddGroupDao;
import com.youi.business.common.entity.IP_ADD_GROUP;
import com.youi.business.common.util.IPv4Util;
import com.youi.business.ip.vo.VIpAddressGroupDetail;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lijinliang on 2016/10/7.
 */
@Transactional
@Service
public class IpAddressGroupService {
    @Autowired
    private IpAddGroupDao ipAddGroupDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BeanMapper beanMapper;


    /**
     * 分页获得业务系统list
     *
     * @param userid
     * @param page
     * @return
     * @throws Exception
     */
    public Page getIpAddressGroupList(Long userid, Page page) throws Exception {
        if (page == null) {
            List<IP_ADD_GROUP> list = ipAddGroupDao.getAll("ctime", false);
            page = new Page(list, list.size());
        } else {
            int pageno = page.getPageNo();
            int pagesize = page.getPageSize();
            List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
            Page page1 = ipAddGroupDao.pagedQuery(page, null);
            long totalCount = page1.getTotalCount();
            List<IP_ADD_GROUP> list = (List<IP_ADD_GROUP>) page1.getResult();
            if (list != null) {
                for (IP_ADD_GROUP p : list) {
                    Map<String, Object> m = new HashMap<String, Object>();
                    m.put("id", p.getId());
                    m.put("name", p.getName());
                    m.put("description", p.getDescription());
                    m.put("ip_add", p.getIp_add());
                    m.put("gateway", p.getGateway());
                    if(p.getSubnet_mask()!=null){
                        m.put("subnet_mask",IPv4Util.getMaskString(p.getSubnet_mask().intValue()));
                    }

                    m.put("red_count", p.getRed_count());
                    m.put("yellow_count", p.getYellow_count());
                    m.put("green_count", p.getGreen_count());
                    m.put("gray_count", p.getGray_count());
                    results.add(m);
                }
            }
            page = new Page(results, totalCount);
            page.setPageNo(pageno);
            page.setPageSize(pagesize);
        }

        return page;
    }


    /**
     * 查询详细
     *
     * @param id
     * @return
     */
    public Object selectIpAddressGroupDetail(Long id) throws Exception {
        IP_ADD_GROUP IpAddressGroup = ipAddGroupDao.get(id);
        if (IpAddressGroup == null) {
            return null;
        }
        VIpAddressGroupDetail detail = new VIpAddressGroupDetail();
        beanMapper.copy(IpAddressGroup, detail);
        if(IpAddressGroup.getSubnet_mask()!=null){
            detail.setSubnet_mask(IPv4Util.getMaskString(IpAddressGroup.getSubnet_mask().intValue()));
        }
        return detail;
    }

    public Object saveIpAddressGroup(Map<String, String> param) throws Exception {
        Long id = null;
        if (StringUtils.isEmpty(String.valueOf(param.get("id")))) {
            //新增
            IP_ADD_GROUP ipAddressGroup = new IP_ADD_GROUP();
            if (!StringUtils.isEmpty(param.get("name"))) {

                ipAddressGroup.setName(param.get("name"));
            }
            if (!StringUtils.isEmpty(param.get("description"))) {
                ipAddressGroup.setDescription(param.get("description"));
            }
            if (!StringUtils.isEmpty(param.get("ip_add"))) {
                ipAddressGroup.setIp_add(param.get("ip_add"));
            }
            if (!StringUtils.isEmpty(param.get("subnet_mask"))) {
                if (param.get("subnet_mask").indexOf(".") > 0) {
                    ipAddressGroup.setSubnet_mask(Long.parseLong(String.valueOf(IPv4Util.getMaskInt(param.get("subnet_mask")))));
                } else {
                    ipAddressGroup.setSubnet_mask(Long.parseLong(param.get("subnet_mask")));
                }

            }
            if (!StringUtils.isEmpty(param.get("gateway"))) {
                ipAddressGroup.setGateway(param.get("gateway"));
            }
            ipAddressGroup.setCtime(new Date());
            ipAddressGroup.setCuserid(Long.parseLong(String.valueOf(param.get("userid"))));
            ipAddGroupDao.save(ipAddressGroup);
            id = ipAddressGroup.getId();


        } else {
            //修改
            IP_ADD_GROUP ipAddressGroup = ipAddGroupDao.get(Long.parseLong(String.valueOf(param.get("id"))));
            //新增
            if (!StringUtils.isEmpty(param.get("name"))) {
                ipAddressGroup.setName(param.get("name"));
            }
            if (!StringUtils.isEmpty(param.get("description"))) {
                ipAddressGroup.setDescription(param.get("description"));
            }
            if (!StringUtils.isEmpty(param.get("ip_add"))) {
                ipAddressGroup.setIp_add(param.get("ip_add"));
            }
            if (!StringUtils.isEmpty(param.get("subnet_mask"))) {
                if (param.get("subnet_mask").indexOf(".") > 0) {
                    ipAddressGroup.setSubnet_mask(Long.parseLong(String.valueOf(IPv4Util.getMaskInt(param.get("subnet_mask")))));
                } else {
                    ipAddressGroup.setSubnet_mask(Long.parseLong(param.get("subnet_mask")));
                }
            }
            if (!StringUtils.isEmpty(param.get("gateway"))) {
                ipAddressGroup.setGateway(param.get("gateway"));
            }

            ipAddGroupDao.update(ipAddressGroup);
            id = ipAddressGroup.getId();
        }

        return this.selectIpAddressGroupDetail(id);
    }

    public boolean deleteIpAddressGroup(Long userid, Long id) {
        ipAddGroupDao.removeById(id);
        return true;
    }

    public Object selectIpAddressGroupMonitoringDetail(Long id) {
        IP_ADD_GROUP group = ipAddGroupDao.get(id);
        String ipAndMask = group.getIp_add() + "/" + group.getSubnet_mask();
        int[] ipscope = IPv4Util.getIPIntScope(ipAndMask);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        //查询已用的ip地址信息
        StringBuilder sqlAllUsedIp = new StringBuilder();
        sqlAllUsedIp.append(" select biz_type,ip_add,host_name from ( select  t1.biz_type,t1.ip_add ,case when biz_type='HW_X86' then (select name from hw_x86 t11 where t11.id=t1.biz_id)");
        sqlAllUsedIp.append(" else case when biz_type='HW_VIRTUAL_MACHINE' then (select name from HW_VIRTUAL_MACHINE t11 where t11.id=t1.biz_id) end end as host_name");
        sqlAllUsedIp.append("  FROM ip_address t1 where biz_type in('HW_X86','HW_VIRTUAL_MACHINE')");
        sqlAllUsedIp.append(" union all");
        sqlAllUsedIp.append("  select 'HW_DATABASE' biz_type,t2.address,t2.`name` host_name from hw_database t2");
        sqlAllUsedIp.append("  union all");
        sqlAllUsedIp.append(" select 'RES_BIZ_APP_DEPLOY' biz_type,t3.ip,t3.server_name host_name from res_biz_app_deploy t3) as table_ip");
        sqlAllUsedIp.append("  where INET_ATON(ip_add) BETWEEN INET_ATON(?) AND INET_ATON(?)");
        List<Map<String, Object>> allUsedIpList = ipAddGroupDao.getJdbcTemplate().queryForList(sqlAllUsedIp.toString(), new Object[]{IPv4Util.intToIp(ipscope[0]), IPv4Util.intToIp(ipscope[1])});
        Map<String, List<Map<String, Object>>> allUsedIpMap = new HashMap<String, List<Map<String, Object>>>();
        if (allUsedIpList != null) {
            for (Map<String, Object> m : allUsedIpList) {
                String tableName = (String) m.get("biz_type");
                String ipAddress = (String) m.get("ip_add");
                List<Map<String, Object>> list1 =   (List<Map<String, Object>>)allUsedIpMap.get("ipAddress");
                if(list1==null){
                    list1 = new ArrayList<Map<String, Object>>();
                    allUsedIpMap.put(ipAddress,list1);
                }
                Map<String, Object> m2 = new HashMap<String, Object>();
                m2.put("host_name",  StringUtils.null2String((String)m.get("name")));
                m2.put("ip", ipAddress);
                m2.put("host_type", StringUtils.null2String(tableName));
                list1.add(m2);
            }
        }

       /* StringBuilder sqlAllUsedIp = new StringBuilder();
        sqlAllUsedIp.append("SELECT  t1.id,t1.biz_type,t1.biz_id,t1.ip_add ");
        sqlAllUsedIp.append(" FROM ip_address t1");
        sqlAllUsedIp.append(" where biz_type in('HW_X86','HW_VIRTUAL_MACHINE') and INET_ATON(t1.ip_add) BETWEEN INET_ATON(?) AND INET_ATON(?) ");
        List<Map<String, Object>> allUsedIpList = ipAddGroupDao.getJdbcTemplate().queryForList(sqlAllUsedIp.toString(), new Object[]{IPv4Util.intToIp(ipscope[0]), IPv4Util.intToIp(ipscope[1])});
        Map<String, Map<String, Object>> allUsedIpMap = new HashMap<String, Map<String, Object>>();
        if (allUsedIpList != null) {
            for (Map<String, Object> m : allUsedIpList) {
                String tableName = (String) m.get("biz_type");
                String ipAddress = (String) m.get("ip_add");
                Long biz_id = (Long) m.get("biz_id");
                String sqlBiz = "select name from " + tableName + " where id=?";
                Map m1 = ipAddGroupDao.getJdbcTemplate().queryForMap(sqlBiz, new Object[]{biz_id});

                Map<String, Object> m2 = new HashMap<String, Object>();
                if(m1!=null){
                    m2.put("host_name",  StringUtils.null2String((String)m1.get("name")));
                }else{
                    m2.put("host_name",  "");
                }
                m2.put("ip", ipAddress);
                m2.put("host_type", StringUtils.null2String(tableName));

                allUsedIpMap.put(ipAddress, m2);
            }
        }*/
        //查询探测的ip信息
        StringBuilder sqlSearchedIp = new StringBuilder();
        sqlSearchedIp.append("SELECT t1.resp_value mac, t1.resp_ext1 ip ");
        sqlSearchedIp.append(" FROM res_snmp_req_result t1");
        sqlSearchedIp.append(" where INET_ATON(t1.resp_ext1) BETWEEN INET_ATON(?) AND INET_ATON(?)");
        List<Map<String, Object>> searchedIpList = ipAddGroupDao.getJdbcTemplate().queryForList(sqlSearchedIp.toString(), new Object[]{IPv4Util.intToIp(ipscope[0]), IPv4Util.intToIp(ipscope[1])});
        Map<String, Map<String, Object>> searchedIpMap = new HashMap<String, Map<String, Object>>();
        if (allUsedIpList != null) {
            for (Map<String, Object> m : searchedIpList) {
                String mac = (String) m.get("mac");
                String ip = (String) m.get("ip");
                Map<String, Object> m2 = new HashMap<String, Object>();
                m2.put("ip", ip);
                m2.put("mac", mac);
                searchedIpMap.put(ip, m2);
            }
        }
        //组装返回结果
        for (int i = ipscope[0]; i <= ipscope[1]; i++) {
            String ip = IPv4Util.intToIp(i);
            int hostid = i - ipscope[0];
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("hostid", hostid);
            m.put("ip", ip);
            List<Map<String,Object>> list1 = allUsedIpMap.get(ip);
            boolean is_gh = false;
            if (list1 != null) {
                is_gh = true;
                StringBuilder  host_type = new StringBuilder();
                StringBuilder  host_name = new StringBuilder();
                for(Map<String,Object> m2:list1){
                    host_type.append(m2.get("host_type")).append("|");
                    host_type.append(m2.get("host_name")).append("|");
                }
                if(host_type.length()>0){
                    host_type.deleteCharAt(host_type.length()-1);
                }
                if(host_name.length()>0){
                    host_name.deleteCharAt(host_name.length()-1);
                }
                m.put("host_type", host_type.toString());
                m.put("host_name", host_name.toString());
            }else{
                m.put("host_type", "");
                m.put("host_name","");
            }

            Map m2 = searchedIpMap.get(ip);
            boolean is_hd = false;
            if (m2 != null) {
                is_hd = true;
                m.put("mac", StringUtils.null2String((String)m2.get("mac")));
            }else{
                m.put("mac", "");
            }

            //  status:red 未规划的活动IP green 规划的活动IP yellow 规划的非活动IP gray 未规划的非活动IP
            String status = "gray";
            if (is_hd && is_gh) {
                status = "green";
            } else if (is_hd && !is_gh) {
                status = "red";
            } else if (!is_hd && is_gh) {
                status = "yellow";
            }
            m.put("status", status);
            result.add(m);
        }
        return result;
    }
}
