package com.youi.example1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youi.base.TestBase;
import com.youi.business.common.util.MD5Encoder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TestRestController extends TestBase {
    private ObjectMapper objectMapper = new ObjectMapper();

  //  @Test
    public void test0()  throws Exception {
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
    }
   // @Test
    public void testgetMenus()  throws Exception {
        String token = getToken();
        Map<String,String> param = new HashMap<String,String>();
        param.put("userid","1");
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.post("/pim/getMenus.do").contentType("application/json;charset=UTF-8")
                .header("Authorization",token)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();

    }
    public String getToken()throws Exception{
        Map<String,String> param = new HashMap<String,String>();
        param.put("usercode","ljl");
        param.put("password", "88888888");
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.post("/login.do").contentType("application/json;charset=UTF-8").content(  objectMapper.writeValueAsString(param))

        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        String r = result.getResponse().getContentAsString();
        Map m = objectMapper.readValue(r,Map.class);
        return  (String)m.get("r");
    }

    @Test
    public void testMd5()  throws Exception {
        System.out.println(MD5Encoder.encode("88888888","wdsh")) ;
    }
}
