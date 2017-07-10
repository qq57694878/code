package com.youi.business.test;

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
public class TestTestController extends TestRestBussinessBase {
    @Test
    public void testIndex()  throws Exception {
        Response r = RestAssured.given().contentType("application/json")
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/test/index.do");
        r.then().statusCode(200);
        r.prettyPrint();
    }

}
