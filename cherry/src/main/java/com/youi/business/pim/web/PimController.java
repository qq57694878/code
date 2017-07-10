package com.youi.business.pim.web;

import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.pim.service.PimService;
import com.youi.business.pim.vo.Menu;
import com.youi.core.hibernate.HibernatePagingDao;
import com.youi.core.mapper.JsonMapper;
import com.youi.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/7/19.
 */
@RestController
public class PimController {

    private PimService pimService;
    @Autowired
    private JWTService jwtService;

    @Resource
    public void setPimService(PimService pimService) {
        this.pimService = pimService;
    }

    @RequestMapping("/menu/get")
    public RestResult getMenus(@Userid Long userid) throws IOException {
       // String userid = param.get("userid");
        Map<String,Object> r = new HashMap<String,Object>();
        r.put("sidemenus", pimService.getMenus(userid));
        String token = jwtService.getToken(userid);
        return new RestResult(token,r);
    }
/*
    @RequestMapping("/test1")
    public Page test1(@RequestBody Map<String,String> param, @ModelAttribute String userid) throws IOException {
        return pimService.test1(userid);
    }
    @RequestMapping("/test2")
    public Page test2(@RequestBody Map<String,String> param, @ModelAttribute String userid) throws IOException {
        return pimService.test1(userid);
    }*/

}
