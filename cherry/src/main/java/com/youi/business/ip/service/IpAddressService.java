package com.youi.business.ip.service;

import com.youi.business.common.dao.IpAddGroupDao;
import com.youi.business.common.dao.IpAddressDao;
import com.youi.business.common.entity.IP_ADDRESS;
import com.youi.business.common.util.IPv4Util;
import com.youi.business.ip.vo.VIpAddressDetail;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.query.PropertyFilter;
import com.youi.core.spring.DateConverter;
import com.youi.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by lijinliang on 2016/10/7.
 */
@Transactional
@Service
public class IpAddressService {
    @Autowired
    private IpAddressDao ipAddressDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BeanMapper beanMapper;


    /**
     *
     * @param param
     * @return
     * @throws Exception
     */
    public Page getIpAddressList(Map<String,String>param) throws Exception {
        Page result = new Page();
        if(param!=null&&param.get("pageno")!=null){
            //分页
           Page page = new Page(Integer.parseInt(param.get("pageno")),Integer.parseInt(param.get("pagesize")),"ctime",Page.DESC);
            List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
            List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
            if(!StringUtils.isEmpty(param.get("biz_type"))){
                filters.add(new PropertyFilter("EQS_biz_type",param.get("biz_type")));
            }
            if(!StringUtils.isEmpty(param.get("biz_id"))){
                filters.add(new PropertyFilter("EQL_biz_id",param.get("biz_id")));
            }
            Page page1 = ipAddressDao.pagedQuery(page, filters);
            List<IP_ADDRESS> list = (List<IP_ADDRESS>) page1.getResult();
            if (list != null) {
                for (IP_ADDRESS p : list) {
                    Map<String, Object> m = new HashMap<String, Object>();
                    m.put("id", p.getId());
                    m.put("ip_add", p.getIp_add());
                    m.put("subnet_mask", IPv4Util.getMaskString(p.getSubnet_mask().intValue()));
                    m.put("gateway", p.getGateway());
                    m.put("ip_type", p.getIp_type());
                    m.put("mac", p.getMac());
                    m.put("mac_fresh_time", p.getMac_fresh_time());
                    results.add(m);
                }
            }

            result.setResult(page1.getResult());
            result.setTotalCount(page1.getTotalCount());
        }else{
            //不分页
            StringBuilder hql = new StringBuilder();
            List<Object> ps = new ArrayList<Object>();
            hql.append(" from IP_ADDRESS where 1=1") ;
            if(!StringUtils.isEmpty(param.get("biz_type"))){
                hql.append(" and biz_type=?");
                ps.add(param.get("biz_type"));
            }
            if(!StringUtils.isEmpty(param.get("biz_id"))){
                hql.append(" and biz_id=?");
                ps.add(Long.parseLong(param.get("biz_id")));
            }
            hql.append(" order by ctime desc");
            List<IP_ADDRESS> list =  ipAddressDao.createQuery(hql.toString(),ps.toArray()).list();
            List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
            if (list != null) {
                for (IP_ADDRESS p : list) {
                    Map<String, Object> m = new HashMap<String, Object>();
                    m.put("id", p.getId());
                    m.put("ip_add", p.getIp_add());
                    m.put("subnet_mask", IPv4Util.getMaskString(p.getSubnet_mask().intValue()));
                    m.put("gateway", p.getGateway());
                    m.put("ip_type", p.getIp_type());
                    m.put("mac", p.getMac());
                    m.put("mac_fresh_time", p.getMac_fresh_time());
                    results.add(m);
                }
            }
            result.setResult(results);
            result.setTotalCount(results.size());
        }
        return result;
    }


    /**
     * 查询详细
     *
     * @param id
     * @return
     */
    public Object selectIpAddressDetail(Long id) throws Exception {
        IP_ADDRESS o = ipAddressDao.get(id);
        if (o == null) {
            return null;
        }
        VIpAddressDetail detail = new VIpAddressDetail();
        //beanMapper.copy(IpAddress, detail);
        detail.setMac(o.getMac());
        detail.setZabbix_switch(o.getZabbix_switch());
        detail.setIp_add(o.getIp_add());
        detail.setBiz_id(o.getBiz_id());
        detail.setBiz_type(o.getBiz_type());
        detail.setBiz_id(o.getId());
        detail.setCtime(o.getCtime());
        detail.setGateway(o.getGateway());
        detail.setIp_type(o.getIp_type());
        detail.setCuserid(o.getCuserid());
        detail.setMac_fresh_time(o.getMac_fresh_time());
        detail.setZabbixid(o.getZabbixid());
        detail.setId(o.getId());
        if(o.getSubnet_mask()!=null){
            detail.setSubnet_mask(IPv4Util.getMaskString(o.getSubnet_mask().intValue()));
        }
        return detail;
    }

    public Object saveIpAddress(Map<String, String> param) throws Exception {
        Long id = null;
        if (StringUtils.isEmpty(String.valueOf(param.get("id")))) {
            //新增
            IP_ADDRESS ipAddress = new IP_ADDRESS();
            if (!StringUtils.isEmpty(param.get("biz_type"))) {
                ipAddress.setBiz_type(param.get("biz_type"));
            }
            if (!StringUtils.isEmpty(param.get("biz_id"))) {
                ipAddress.setBiz_id(Long.parseLong(param.get("biz_id")));
            }
            if (!StringUtils.isEmpty(param.get("mac"))) {
                ipAddress.setMac(param.get("mac"));
            }
            if (!StringUtils.isEmpty(param.get("ip_add"))) {
                ipAddress.setIp_add(param.get("ip_add"));
            }
            if (!StringUtils.isEmpty(param.get("mac_fresh_time"))) {
                DateConverter converter = new DateConverter();
                ipAddress.setMac_fresh_time(   converter.convert(param.get("mac_fresh_time")));
            }
            if (!StringUtils.isEmpty(param.get("subnet_mask"))) {
                if (param.get("subnet_mask").indexOf(".") > 0) {
                    ipAddress.setSubnet_mask(Long.parseLong(String.valueOf(IPv4Util.getMaskInt(param.get("subnet_mask")))));
                } else {
                    ipAddress.setSubnet_mask(Long.parseLong(param.get("subnet_mask")));
                }
            }
            if (!StringUtils.isEmpty(param.get("gateway"))) {
                ipAddress.setGateway(param.get("gateway"));
            }
            String ip_type="1";
            if(!StringUtils.isEmpty(param.get("biz_type"))&&!StringUtils.isEmpty(param.get("biz_id"))){
                int count = ipAddressDao.getCount("select count(*) from IP_ADDRESS where biz_type=? and biz_id=?",new Object[]{param.get("biz_type"),Long.parseLong(param.get("biz_id"))});
                if(count>0){
                    ip_type="0";

                }
            }
            ipAddress.setZabbix_switch("0");
            ipAddress.setIp_type(ip_type);
            ipAddress.setCtime(new Date());
            ipAddress.setCuserid(Long.parseLong(String.valueOf(param.get("userid"))));

            ipAddressDao.save(ipAddress);
            id = ipAddress.getId();


        } else {
            //修改
            IP_ADDRESS ipAddress = ipAddressDao.get(Long.parseLong(String.valueOf(param.get("id"))));

            if (!StringUtils.isEmpty(param.get("biz_type"))) {
                ipAddress.setBiz_type(param.get("biz_type"));
            }
            if (!StringUtils.isEmpty(param.get("biz_id"))) {
                ipAddress.setBiz_id(Long.parseLong(param.get("biz_id")));
            }
            if (!StringUtils.isEmpty(param.get("mac"))) {
                ipAddress.setMac(param.get("mac"));
            }
            if (!StringUtils.isEmpty(param.get("ip_add"))) {
                ipAddress.setIp_add(param.get("ip_add"));
            }
            if (!StringUtils.isEmpty(param.get("mac_fresh_time"))) {
                DateConverter converter = new DateConverter();
                ipAddress.setMac_fresh_time(   converter.convert(param.get("mac_fresh_time")));
            }
            if (!StringUtils.isEmpty(param.get("subnet_mask"))) {
                if (param.get("subnet_mask").indexOf(".") > 0) {
                    ipAddress.setSubnet_mask(Long.parseLong(String.valueOf(IPv4Util.getMaskInt(param.get("subnet_mask")))));
                } else {
                    ipAddress.setSubnet_mask(Long.parseLong(param.get("subnet_mask")));
                }
            }
            if (!StringUtils.isEmpty(param.get("gateway"))) {
                ipAddress.setGateway(param.get("gateway"));
            }

            ipAddressDao.update(ipAddress);
            id = ipAddress.getId();
        }

        return this.selectIpAddressDetail(id);
    }

    public boolean deleteIpAddress(Long userid, Long id) {
        IP_ADDRESS address = ipAddressDao.get(id);
        ipAddressDao.removeById(id);
        //选择一个最早录入的为主ip
        if(address!=null){
            IP_ADDRESS m = (IP_ADDRESS)ipAddressDao.createQuery("from IP_ADDRESS where biz_type=? and biz_id=? order by ctime asc ",new Object[]{address.getBiz_type(),address.getBiz_id()}).setMaxResults(1).uniqueResult();
            if(m!=null){
                m.setIp_type("1");
                ipAddressDao.update(m);
            }
        }
        return true;
    }
    public boolean deleteIpAddress(String biz_type, Long biz_id) {
      ipAddressDao.createQuery("delete from  IP_ADDRESS where biz_type=? and biz_id=? ",new Object[]{biz_type,biz_id});
      return true;
    }

    public boolean setMainIpAddress(Map<String, String> param) {
        Long id = Long.parseLong(param.get("id"));
        IP_ADDRESS address = ipAddressDao.get(id);
        if(address==null){
            return false;
        }else{
            //首先把biz_type&biz_id的全部设为非主ip，再设置主ip，保证不重复
            ipAddressDao.batchUpdate("update IP_ADDRESS set ip_type='0' where biz_type=? and biz_id=?",new Object[]{address.getBiz_type(),address.getBiz_id()});
            address.setIp_type("1");
            ipAddressDao.update(address);
        }
        return true;

    }
}
