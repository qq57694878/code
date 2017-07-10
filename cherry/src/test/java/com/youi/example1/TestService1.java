package com.youi.example1;

import com.youi.base.TestBase;
import com.youi.subsys.web.SubsysController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Random;

/**
 * Created by jinliang on 2016/7/13.
 *  基于JUNIT的测试springwebmvc的例子，都需要继承TestBase即可。
 */
public class TestService1 extends TestBase {
    @Autowired
    private HelloService helloService;

   // @Autowired
 //   private SubsysController subsysController;

  //  @Test
    public void testHelloService(){
        helloService.sayHello();
    }
    //@Test
    public void testSubsysList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/subsys/subsys-list.do").param("filter_LIKES_name", "1")).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

   // @Test
    public void testSubsysSave()  throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/subsys/subsys-save.do")
                .param("developed_department_id", "1")
                .param("use_department_id", "1")
                .param("name", "测试名字"+ Math.random())
                .param("online_time", "2016-07-13")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
