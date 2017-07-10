package com.youi.business.sysresource.service;

import com.youi.business.common.Constants;
import com.youi.business.common.dao.ResVendorDao;
import com.youi.business.common.dao.SysOrgInfoDao;
import com.youi.business.common.entity.RES_VENDOR;
import com.youi.business.common.entity.SYS_ORG_INFO;
import com.youi.business.sysresource.vo.VVendorDetailInfo;
import com.youi.business.sysresource.vo.VVendorListInfo;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.spring.DateConverter;
import com.youi.core.util.StringUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/9/21.
 */
@Transactional
@Service
public class VendorService {
    @Autowired
    private ResVendorDao vendorDao;
    @Autowired
    private SysOrgInfoDao sysOrgInfoDao;
    @Autowired
    private BeanMapper beanMapper;

    /**
     * 分页查询外协单位列表
     * @param userid
     * @param page
     * @return
     */
    public Page getVendorList(Long userid, Page page) {
        int pageno = page.getPageNo();
        int pagesize = page.getPageSize();
        List<VVendorListInfo> results = new ArrayList<VVendorListInfo>();
        String sql = "SELECT T1.ID,T1.MANAGER,T2.VALID_TYPE AS STATE,T2.ORG_NAME AS VENDOR_NAME,T1.TEL FROM RES_VENDOR T1,SYS_ORG_INFO T2 WHERE T1.SYS_ORG_INFO_ID=T2.ORG_ID ORDER BY T1.C_DATE DESC";
        Page page1 = vendorDao.pagedSQLQuery(sql,pageno,pagesize);
        long totalCount = page1.getTotalCount();
        List<Map<String,Object>> list =   (List<Map<String,Object>>)page1.getResult();
        if(list!=null){
            for(Map<String,Object> p:list){
               VVendorListInfo v= new VVendorListInfo();
                v.setId(((BigInteger)p.get("id")).longValue());
                v.setManager((String)p.get("manager"));
                v.setState((String)p.get("state"));
                v.setTel((String)p.get("tel"));
                v.setVendor_name((String)p.get("vendor_name"));
                results.add(v);
            }
        }
        page = new Page(results, totalCount);
        page.setPageNo(pageno);
        page.setPageSize(pagesize);
        return page;
    }

    /**
     * 禁用外协单位
     * @param userid
     * @param id
     * @return
     */
    public boolean disableVendor(Long userid, Long id) {
       RES_VENDOR vendor =  vendorDao.get(id);
        if(vendor==null){return false;}
       SYS_ORG_INFO  orgInfo =  sysOrgInfoDao.get(vendor.getSys_org_info_id());
        if(orgInfo==null){return false;}
            orgInfo.setValid_type("0");
            sysOrgInfoDao.update(orgInfo);
        return true;

    }

    /**
     * 启用
     * @param userid
     * @param id
     * @return
     */
    public boolean enableVendor(Long userid, Long id) {
        RES_VENDOR vendor =  vendorDao.get(id);
        if(vendor==null){return false;}
        SYS_ORG_INFO  orgInfo =  sysOrgInfoDao.get(vendor.getSys_org_info_id());
        if(orgInfo==null){return false;}
        orgInfo.setValid_type("1");
        sysOrgInfoDao.update(orgInfo);
        return true;
    }

    /**
     * 查询详细
     * @param id
     * @return
     */
    public Object selectVendorDetail( Long id)throws  Exception {
        RES_VENDOR vendor =  vendorDao.get(id);
        if(vendor==null){return null;}
        SYS_ORG_INFO  orgInfo =  sysOrgInfoDao.get(vendor.getSys_org_info_id());
        if(orgInfo==null){return null;}
        VVendorDetailInfo detail = new VVendorDetailInfo();
       /*  detail.setId(vendor.getId());
       detail.setManager(vendor.getManager());
        detail.setNum_people(vendor.getNum_people());
        detail.setProperty(vendor.getProperty());*/
        beanMapper.copy(vendor,detail);
        detail.setVendor_name(orgInfo.getOrg_name());
        detail.setOrg_id(orgInfo.getOrg_id());
        return  detail;
    }

    public Object saveVendor(Map<String, String> param)throws  Exception  {
        Long id=null;
        if(StringUtils.isEmpty(param.get("id"))&&StringUtils.isEmpty(param.get("org_id"))){
            //新增
            HanyuPinyinOutputFormat format =  new HanyuPinyinOutputFormat();
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            String vendor_name =param.get("vendor_name");
            String org_code =   PinyinHelper.toHanYuPinyinString(vendor_name,format,"",false);
            //新增sys_org_info
            SYS_ORG_INFO org = new SYS_ORG_INFO();
            org.setValid_type("1");
            org.setOrg_code(org_code);
            org.setOrg_name(vendor_name);
            org.setOrg_order(99L);
            org.setUp_org_id(Constants.TOP_ORG_ID);
            org.setOrg_type(Constants.OTG_TYPE_VENDOR);
            sysOrgInfoDao.save(org);
            RES_VENDOR vendor = new RES_VENDOR();
            vendor.setManager(param.get("manager"));
            if(!StringUtils.isEmpty(param.get("register_date"))){
                DateConverter d = new DateConverter();
                vendor.setRegister_date(d.convert( param.get("register_date")));
            }
            vendor.setProperty( param.get("property"));
            vendor.setNum_people(param.get("num_people"));
            vendor.setC_date(new Date());
            vendor.setC_userid(Long.parseLong(param.get("userid")));
            vendor.setRegister_money(param.get("register_money"));
            vendor.setScope(param.get("scope"));
            vendor.setSys_org_info_id(org.getOrg_id());
            vendor.setTel(param.get("tel"));
            vendorDao.save(vendor);
            id=vendor.getId();
        }else{
            //更新
            RES_VENDOR vendor = vendorDao.get(Long.parseLong(param.get("id")));
            vendor.setManager(param.get("manager"));
            if(!StringUtils.isEmpty(param.get("register_date"))){
                DateConverter d = new DateConverter();
                vendor.setRegister_date(d.convert( param.get("register_date")));
            }
            vendor.setProperty( param.get("property"));
            vendor.setNum_people(param.get("num_people"));
            vendor.setRegister_money(param.get("register_money"));
            vendor.setScope(param.get("scope"));
            vendor.setSys_org_info_id(Long.parseLong(param.get("org_id")));
            vendor.setTel(param.get("tel"));
            vendorDao.update(vendor);
            id=vendor.getId();
            SYS_ORG_INFO org = sysOrgInfoDao.get(Long.parseLong(param.get("org_id")));
            if(org!=null){
                org.setOrg_name(param.get("vendor_name"));
                sysOrgInfoDao.update(org);
            }
        }
        return this.selectVendorDetail(id);
    }

    public List<Map<String,Object>> getVendorCodeTableList() {
        String sql = "SELECT T1.ID CODE,T2.ORG_NAME  VALUE FROM RES_VENDOR T1,SYS_ORG_INFO T2 WHERE T1.SYS_ORG_INFO_ID=T2.ORG_ID ORDER BY T1.C_DATE DESC";
        List list = vendorDao.createSQLQuery(sql).list();
        return list;
    }

    public Map<String,Object> getVendorManager(Map<String, String> param) {
        String vendor_id = param.get("vendor_id");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT concat(T1.USER_ID,'') user_id,username  FROM SYS_USER T1 WHERE T1.VALID_TYPE='1'");
        sql.append(" AND EXISTS (SELECT * FROM sys_user_group T2,sys_group T3 WHERE T1.USER_ID=T2.USER_ID AND T2.GROUP_ID = T3.GROUP_ID AND T3.GROUP_CODE ='vendor_manager')");
        sql.append("  AND T1.ORG_ID in (SELECT T4.SYS_ORG_INFO_ID FROM RES_VENDOR T4 WHERE T4.ID =?)");
        List<Map<String,Object>> list = vendorDao.createSQLQuery(sql.toString(),vendor_id).list();
        if(list!=null&&list.size()>0){
              return list.get(0);
        }
        return null;

    }
}
