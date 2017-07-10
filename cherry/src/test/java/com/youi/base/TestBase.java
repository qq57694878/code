package com.youi.base;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/**
 * Created by jinliang on 2016/7/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@WebAppConfiguration("webapp")
@ContextConfiguration({"classpath*:/spring/ctx*.xml"}) //加载配置文件
public class TestBase {

    // 模拟request,response
    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;



    // 执行测试方法之前初始化模拟request,response
    @Before
    public void init() throws Exception{
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    @Test
    public void test(){

    }
}
