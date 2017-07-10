package com.youi.business.pim;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youi.base.TestBase;
import com.youi.business.base.TestBussinessBase;
import com.youi.business.base.TestRestBussinessBase;
import com.youi.business.common.Constants;
import com.youi.business.common.util.MD5Encoder;
import com.youi.example1.Pojo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
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
public class TestPimController extends TestRestBussinessBase{
    private ObjectMapper objectMapper = new ObjectMapper();

  //  @Test
   /* public void test0()  throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/restfultest.do").param("name","lilei")

        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("hello,lilei"));
    }
   // @Test
    public void test1()  throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/restfultest1/lilei.do")

        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("hello,lilei"));
    }
   // @Test
    public void test2()  throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/restfultest2.do").content(  objectMapper.writeValueAsString(new Pojo(1,"lilei")))

        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("lilei"));
    }
  // @Test
    public void test3()  throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/restfultest3.do").contentType("application/json;charset=UTF-8").content(  objectMapper.writeValueAsString(new Pojo(1,"lilei")))

        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("lilei"));
    }*/
    @Test
    public void testgetMenus()  throws Exception {
        Response r = RestAssured.given().contentType("application/json")
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/menu/get.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();

    }

}
