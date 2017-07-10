package com.youi.business.pim;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youi.base.TestBase;
import com.youi.business.base.TestBussinessBase;
import com.youi.business.common.Constants;
import com.youi.business.common.util.MD5Encoder;
import com.youi.example1.Pojo;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinliang on 2016/7/13.
 *  基于JUNIT的测试springwebmvc的例子，都需要继承TestBase即可。
 */
public class TestLoginController extends TestBussinessBase {


    @Test
    public void testLogout()  throws Exception {
        String token = this.token;

        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.post("/logout").contentType("application/json;charset=UTF-8")
                .header(Constants.AUTHENTICATION_HEADER,token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

    }

}
