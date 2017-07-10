package com.youi.business.sysresource.service;

import com.youi.business.common.Constants;
import com.youi.business.common.dao.ResBussinessAppDao;
import com.youi.business.common.dao.ResVendorDao;
import com.youi.business.common.dao.SysOrgInfoDao;
import com.youi.business.common.entity.RES_BUSSINESS_APP;
import com.youi.business.common.entity.RES_VENDOR;
import com.youi.business.common.entity.SYS_ORG_INFO;
import com.youi.business.common.vo.VCodeTable;
import com.youi.business.sysresource.vo.VBussinessApp;
import com.youi.business.sysresource.vo.VBussinessAppDetail;
import com.youi.business.sysresource.vo.VVendorDetailInfo;
import com.youi.business.sysresource.vo.VVendorListInfo;
import com.youi.core.codetable.CodeTableUtil;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.query.PropertyFilter;
import com.youi.core.spring.DateConverter;
import com.youi.core.util.StringUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

/**
 * Created by jinliang on 2016/9/21.
 */
@Transactional
@Service
public class BussinessAppService {
    @Autowired
    private ResBussinessAppDao resBussinessAppDao;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private BeanMapper beanMapper;

    /**
     * 分页获得业务系统list
     * @param userid
     * @param page
     * @return
     * @throws Exception
     */
    public Page getBussinessAppList(Long userid, Page page) throws Exception{
        int pageno = page.getPageNo();
        int pagesize = page.getPageSize();
        List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
      /*  List<PropertyFilter> propertyFilters =new ArrayList<PropertyFilter>();
        propertyFilters.add(new PropertyFilter("EQS_status", "1"));*/
        Page page1 = resBussinessAppDao.pagedQuery(page,null);
        long totalCount = page1.getTotalCount();
        List<RES_BUSSINESS_APP> list =   (List<RES_BUSSINESS_APP>)page1.getResult();
        if(list!=null){
            for(RES_BUSSINESS_APP p:list){
                Map<String,Object> m = new HashMap<String,Object>();

                m.put("id",p.getId());
                m.put("name",p.getName());
                StringBuilder org_names =new StringBuilder();
                if(p.getOrg_id_list()!=null){
                   String[] org_ids =  p.getOrg_id_list().split(",");
                    for(String org_id:org_ids){
                        String org_name =  CodeTableUtil.code2mean("SYSORG",org_id);
                        org_names.append(org_name).append(",");
                    }
                    if(org_names.length()>0){
                        org_names.deleteCharAt(org_names.length()-1);
                    }
                }
                m.put("org_name",org_names.toString());
                String vendor="";
                if(p.getVendor_id()!=null){
                    VVendorDetailInfo detail =( VVendorDetailInfo  ) vendorService.selectVendorDetail(p.getVendor_id());
                    if(detail!=null){
                        vendor = detail.getVendor_name();
                    }
                }
                m.put("vendor",vendor);//开发单位
                m.put("online_time",p.getOnline_time());//开发单位
                m.put("status",p.getStatus());//开发单位
                results.add(m);
            }
        }
        page = new Page(results, totalCount);
        page.setPageNo(pageno);
        page.setPageSize(pagesize);
        return page;
    }

    public List<Map<String,Object>> getBussinessAppCodeTableList() {
            List list = resBussinessAppDao.createSQLQuery("SELECT  ID CODE,NAME VALUE  FROM RES_BUSSINESS_APP WHERE STATUS='1'").list();
            return list;
    }


    /**
     * 禁用
     * @param userid
     * @param id
     * @return
     */
    public boolean disableBussinessApp(Long userid, Long id) {
       RES_BUSSINESS_APP resBussinessApp =  resBussinessAppDao.get(id);
        if(resBussinessApp==null){return false;}
        resBussinessApp.setStatus("0");
        resBussinessAppDao.update(resBussinessApp);
        return true;

    }

    /**
     * 启用
     * @param userid
     * @param id
     * @return
     */
    public boolean enableBussinessApp(Long userid, Long id) {
        RES_BUSSINESS_APP resBussinessApp =  resBussinessAppDao.get(id);
        if(resBussinessApp==null){return false;}
        resBussinessApp.setStatus("1");
        resBussinessAppDao.update(resBussinessApp);
        return true;
    }

    /**
     * 查询详细
     * @param id
     * @return
     */
    public Object selectBussinessAppDetail(Long id)throws  Exception {
        RES_BUSSINESS_APP resBussinessApp =  resBussinessAppDao.get(id);
        if(resBussinessApp==null){return null;}
        VBussinessAppDetail detail = new VBussinessAppDetail();
        beanMapper.copy(resBussinessApp,detail);
        return  detail;
    }

    public Object saveBussinessApp(Map<String, String> param)throws  Exception  {
        Long id=null;
        if(StringUtils.isEmpty(param.get("id"))){
            //新增
            RES_BUSSINESS_APP resBussinessApp = new RES_BUSSINESS_APP();
            resBussinessApp.setStatus("1");
            resBussinessApp.setDescription(param.get("description"));
            resBussinessApp.setOrg_id_list(param.get("org_id_list"));
            resBussinessApp.setName(param.get("name"));
            if(!StringUtils.isEmpty(param.get("online_time"))){
                DateConverter d = new DateConverter();
                resBussinessApp.setOnline_time(d.convert( param.get("online_time")));
            }
            resBussinessApp.setVendor_id(Long.parseLong(String.valueOf(param.get("vendor_id"))));
            resBussinessApp.setCdate(new Date());
            resBussinessApp.setUserid(Long.parseLong(param.get("userid")));
            resBussinessAppDao.save(resBussinessApp);
            id=resBussinessApp.getId();
        }else{
            //更新
            RES_BUSSINESS_APP resBussinessApp = resBussinessAppDao.get(Long.parseLong(param.get("id")));
            resBussinessApp.setDescription(param.get("description"));
            resBussinessApp.setOrg_id_list(param.get("org_id_list"));
            resBussinessApp.setName(param.get("name"));
            if(!StringUtils.isEmpty(param.get("online_time"))){
                DateConverter d = new DateConverter();
                resBussinessApp.setOnline_time(d.convert( param.get("online_time")));
            }
            resBussinessApp.setVendor_id(Long.parseLong(String.valueOf(param.get("vendor_id"))));
            resBussinessAppDao.update(resBussinessApp);
            id=resBussinessApp.getId();
        }
        return this.selectBussinessAppDetail(id);
    }


}
