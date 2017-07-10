package com.youi.business.monitoring;

import com.youi.business.base.TestRestBussinessBase;
import com.youi.business.common.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by jinliang on 2016/8/19.
 */
public class TestMonotoringEventController extends TestRestBussinessBase {


    @Test
    public void testgetMonitoringEentList() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
      /*  param.put("pageno",1);
        param.put("pagesize",10);*/
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/monitoring/zibevent.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }


}
