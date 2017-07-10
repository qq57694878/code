package com.youi.business.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youi.base.TestBase;
import com.youi.business.common.util.MD5Encoder;
import com.youi.example1.Pojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinliang on 2016/7/13.
 *  基于JUNIT的测试springwebmvc的例子，都需要继承TestBase即可。
 */
public class TestBussinessBase extends TestBase {

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected String token;

    // 执行测试方法之前初始化模拟request,response
    @Before
    public void init() throws Exception{
        super.init();
        this.token=this.getToken();
    }

    public String getToken()throws Exception{
        Map<String,String> param = new HashMap<String,String>();
        param.put("username","ljl");
        param.put("passwd", "88888888");
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.post("/login.do").contentType("application/json;charset=UTF-8").content(  objectMapper.writeValueAsString(param))

        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        String r = result.getResponse().getContentAsString();
        Map m = objectMapper.readValue(r,Map.class);
        return  (String)(((Map)m.get("data")).get("token"));
    }

}
