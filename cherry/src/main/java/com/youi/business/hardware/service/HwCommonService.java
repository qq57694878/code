package com.youi.business.hardware.service;

import com.youi.business.common.dao.HwCabinetDao;
import com.youi.business.common.dao.HwComputerRoomDao;
import com.youi.business.common.dao.HwX86Dao;
import com.youi.business.common.entity.HW_CABINET;
import com.youi.business.common.entity.HW_COMPUTER_ROOM;
import com.youi.business.common.entity.HW_X86;
import com.youi.business.hardware.vo.VX86Detail;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jinliang on 2016/9/21.
 */
@Transactional
@Service
public class HwCommonService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获得资产编号
     * @param device_type
     * @return
     */
    public String getNewAssetNum(String device_type) {
       String part1 = getDeviceTypeCode(device_type);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String part2 = sdf.format(new Date());
       long deviceCount =   getDeviceCount(device_type);
        String part3 = getPart3(deviceCount,6);
        return part1+part2+part3;
    }
    private long getDeviceCount(String device_type){
        try{
            Long  num = (Long)jdbcTemplate.queryForObject("SELECT count(*) FROM "+device_type.toUpperCase()+" ",Long.class);
            return  num;
        }catch(Exception e){
            e.printStackTrace();
        }
        return 999999l;
    }
    private String getPart3(long value,int n){
        StringBuilder result = new StringBuilder();
        String  v = String.valueOf(value);
        int j = n>v.length()?n-v.length():0;
        for(int i=0;i<j;i++){
            result.append("0");
        }
       int k=n>v.length()?0:v.length()-n;
        for(int i=k;i<v.length();i++){
            result.append(v.charAt(i));
        }
        return result.toString();
    }



    private String getDeviceTypeCode(String device_type){
        device_type = device_type.toUpperCase();
        if("HW_CABINET".equals(device_type)){
            return "001";
        }else if("HW_X86".equals(device_type)){
            return "101";
        }else if("HW_STORAGE".equals(device_type)){
            return "102";
        }
        return "999";
    }

}
