package com.youi.business.common.scheduler;

import com.youi.business.common.Constants;
import com.youi.business.common.dao.IpAddGroupDao;
import com.youi.business.common.dao.ResSnmpReqResultDao;
import com.youi.business.common.entity.IP_ADD_GROUP;
import com.youi.business.common.entity.RES_SNMP_REQ_RESULT;
import com.youi.business.common.snmp.MySnmpRequest;
import com.youi.business.common.snmp.MySnmpRequestConfig;
import com.youi.business.common.util.IPv4Util;
import com.youi.core.codetable.ModifyCodeTable;
import com.youi.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
public class SnmpRequestIpJob {
    private static Logger logger = LoggerFactory
            .getLogger(SnmpRequestIpJob.class);
    private boolean enabled = true;
    @Autowired
    private ResSnmpReqResultDao resSnmpReqResultDao;

    @Autowired
    private IpAddGroupDao ipAddGroupDao;
    private String address;

    public String getAddress() {
        return address;
    }

    @Value("${snmp.address}")
    public void setAddress(String address) {
        this.address = address;
    }

    // every 1 day
    @Scheduled(cron = "0 */1 * * * ?")
    public void snmpRequest() throws IOException {
        if (!enabled) {
            return;
        }
        logger.info("snmp request ip start");
        resSnmpReqResultDao.getSession().createSQLQuery("TRUNCATE RES_SNMP_REQ_RESULT").executeUpdate();
        resSnmpReqResultDao.getSession().createSQLQuery("commit").executeUpdate();
        //resSnmpReqResultDao.getJdbcTemplate().execute("TRUNCATE RES_SNMP_REQ_RESULT");
        // resSnmpReqResultDao.getJdbcTemplate().execute("COMMIT");
        List<AddressAndCommunity> as = parseAddress(this.address);
        Long req_time = System.currentTimeMillis();
        for (AddressAndCommunity ac : as) {
            MySnmpRequestConfig config = new MySnmpRequestConfig();
            config.setAddress(ac.getAddress());
            config.setCommunity(ac.getCommunity());
            config.setPduType("GETBULK");
            config.setTimeout("20000");
            List list = new ArrayList();
            list.add(Constants.SNMP_REQ_atPhysAddress);
            config.setVariableBinding(list);
            MySnmpRequest snmpRequest = new MySnmpRequest(config);
            /*MySnmpRequest.DbOneColumnTableListener dbOneColumnTableListener = new MySnmpRequest.DbOneColumnTableListener(resSnmpReqResultDao, ac.getAddress(), Constants.SNMP_REQ_atPhysAddress, System.currentTimeMillis());
            snmpRequest.sendOneColumnTableRequestAsynchronized(dbOneColumnTableListener);*/
            List<VariableBinding> listVariable =   snmpRequest.sendOneColumnTableRequestSynchronized();
            if(listVariable!=null){
                for(int i=0;i<listVariable.size();i++){
                    VariableBinding v = listVariable.get(i);
                    String resp_oid = v.getOid().toString();
                    String resp_value = v.getVariable().toString();
                    String row_index = v.getVariable().getSyntaxString();
                    RES_SNMP_REQ_RESULT o = new RES_SNMP_REQ_RESULT();
                    o.setCtime(new Date(req_time));
                    o.setReq_address(ac.getAddress());
                    o.setReq_oid(Constants.SNMP_REQ_atPhysAddress);
                    o.setReq_time(new Date());
                    o.setResp_oid(resp_oid);
                    o.setResp_value(resp_value);
                        int[] iparray = new int[4];
                        System.arraycopy(v.getOid().toIntArray(), v.getOid().size()-4,iparray , 0, iparray.length);
                        OID ip = new OID(iparray);
                        o.setResp_ext1(ip.toString());
                    resSnmpReqResultDao.save(o);
                }
            }
        }
        //统计信息开始
        List<Map<String,Object>> groupList = ipAddGroupDao.getJdbcTemplate().queryForList("select id from IP_ADD_GROUP");
        if(groupList!=null){
            for(Map<String,Object> group:groupList){
                updateIpGroupStatus(Long.parseLong(String.valueOf(group.get("id"))));
            }
        }
        // 更新ip_address表
        String updateSql = "update ip_address t1 set mac = (select IFNULL(t2.resp_value,t1.mac)  from res_snmp_req_result t2 where t1.ip_add=t2.resp_ext1 limit 1), mac_fresh_time = ( SELECT CASE WHEN EXISTS ( SELECT * FROM res_snmp_req_result t2 WHERE t1.ip_add = t2.resp_ext1 ) THEN t1.mac_fresh_time ELSE NOW() END)";
        resSnmpReqResultDao.getJdbcTemplate().execute(updateSql);
        //统计信息end
        logger.info("snmp request ip end");

    }



    public void updateIpGroupStatus(Long id) {
        IP_ADD_GROUP group = ipAddGroupDao.get(id);
        if(StringUtils.isEmpty(group.getIp_add())||group.getSubnet_mask()==null){
            return;
        }
        String ipAndMask = group.getIp_add() + "/" + group.getSubnet_mask();
        int[] ipscope = IPv4Util.getIPIntScope(ipAndMask);
        //查询已用的ip地址信息
        StringBuilder sqlAllUsedIp = new StringBuilder();
        sqlAllUsedIp.append("SELECT  t1.id,t1.ip_add ");
        sqlAllUsedIp.append(" FROM ip_address t1");
        sqlAllUsedIp.append(" where INET_ATON(t1.ip_add) BETWEEN INET_ATON(?) AND INET_ATON(?)");
        List<Map<String, Object>> allUsedIpList = ipAddGroupDao.getJdbcTemplate().queryForList(sqlAllUsedIp.toString(), new Object[]{IPv4Util.intToIp(ipscope[0]), IPv4Util.intToIp(ipscope[1])});
        Map<String, String> allUsedIpMap = new HashMap<String,String>();
        if (allUsedIpList != null) {
            for (Map<String, Object> m : allUsedIpList) {
                String ipAddress = (String) m.get("ip_add");
                allUsedIpMap.put(ipAddress, ipAddress);
            }
        }
        //查询探测的ip信息
        StringBuilder sqlSearchedIp = new StringBuilder();
        sqlSearchedIp.append("SELECT t1.resp_value mac, t1.resp_ext1 ip ");
        sqlSearchedIp.append(" FROM res_snmp_req_result t1");
        sqlSearchedIp.append(" where INET_ATON(t1.resp_ext1) BETWEEN INET_ATON(?) AND INET_ATON(?)");
        List<Map<String, Object>> searchedIpList = ipAddGroupDao.getJdbcTemplate().queryForList(sqlSearchedIp.toString(), new Object[]{IPv4Util.intToIp(ipscope[0]), IPv4Util.intToIp(ipscope[1])});
        Map<String, String> searchedIpMap = new HashMap<String, String>();
        if (allUsedIpList != null) {
            for (Map<String, Object> m : searchedIpList) {
                String ip = (String) m.get("ip");
                searchedIpMap.put(ip, ip);
            }
        }
        long greenCount =0;
        long redCount =0;
        long yellowCount =0;
        long grayCount =0;


        //组装返回结果
        for (int i = ipscope[0]; i <= ipscope[1]; i++) {
            String ip = IPv4Util.intToIp(i);
            String m1 = allUsedIpMap.get(ip);
            //规划
            boolean is_gh = false;
            if (m1 != null) {
                is_gh = true;
            }
            String m2 = searchedIpMap.get(ip);
            //活动
            boolean is_hd = false;
            if (m2 != null) {
                is_hd = true;
            }
            //  status:red 未规划的活动IP green 规划的活动IP yellow 规划的非活动IP gray 未规划的非活动IP
            String status = "gray";
            if (is_hd && is_gh) {
                status = "green";
                greenCount++;
            } else if (is_hd && !is_gh) {
                status = "red";
                redCount++;
            } else if (!is_hd && is_gh) {
                status = "yellow";
                yellowCount++;
            }else{
                status = "gray";
                grayCount++;
            }
        }
        group.setYellow_count(yellowCount);
        group.setGray_count(grayCount);
        group.setRed_count(redCount);
        group.setGreen_count(greenCount);
        ipAddGroupDao.update(group);
    }









    static class AddressAndCommunity {
        private String address;
        private String community;

        public AddressAndCommunity(String address, String community) {
            this.address = address;
            this.community = community;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String toString() {
            return address + "|" + community;
        }
    }

    private List<AddressAndCommunity> parseAddress(String address) {
        List<AddressAndCommunity> result = new ArrayList<AddressAndCommunity>();
        String[] addressArray = address.split(";");
        for (String a : addressArray) {
            if (StringUtils.isEmpty(a)) {
                continue;
            }
            ;
            String[] addressAndCommunity = a.split("\\|");
            if (addressAndCommunity.length == 2) {
                result.add(new AddressAndCommunity(addressAndCommunity[0], addressAndCommunity[1]));
            } else if (addressAndCommunity.length == 1) {
                result.add(new AddressAndCommunity(addressAndCommunity[0], "public"));
            }
        }
        return result;
    }
 /*   public static void main(String args[]){
        SnmpRequestIpJob o = new SnmpRequestIpJob();
        System.out.println(o.parseAddress("udp:www.tongpinlife.com/161|public;udp:10.25.101.254/161|Sn6!sKmP"));
    }*/

}
