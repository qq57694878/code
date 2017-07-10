package com.youi.business.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jinliang on 2017/3/28.
 */
@RestController
public class TestController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping("/test/index")
    public List index(){
        String sql ="select * from (select * from sys_user) as t where 1=1";
        List list =  jdbcTemplate.queryForList(sql);
        return list;
    }

}
