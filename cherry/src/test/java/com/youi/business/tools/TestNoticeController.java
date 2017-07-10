package com.youi.business.tools;

import com.youi.business.base.TestRestBussinessBase;
import com.youi.business.common.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.*;

/**
 * Created by jinliang on 2016/8/19.
 */
public class TestNoticeController extends TestRestBussinessBase {


    @Test
    public void testSendNotice() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("notice_info", "通知内容"+i);
        List<String> ruserid_list = new ArrayList<String>();
        ruserid_list.add("1");
        ruserid_list.add("11");
        param.put("ruserid_list", ruserid_list);
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/tools/notice/send_notice.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testread_notice() throws Exception {
        Map notice = getNoticeDetail();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", notice.get("id"));
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/tools/notice/read_notice.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();

    }


    @Test
    public void testgetUnreadNoticeList() throws Exception {
        getUnreadNoticeList();
    }
    @Test
    public void testquery_send_notice() throws Exception {
        query_send_notice();
    }
    @Test
    public void testquery_receive_notice() throws Exception {
        query_receive_notice();
    }
    public List getUnreadNoticeList() throws Exception {
        Response r = RestAssured.given().contentType("application/json")
                .header(Constants.AUTHENTICATION_HEADER, token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/tools/notice/get_unread_notice_list.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m = r.as(Map.class);
        List list = (List) ((Map) m.get("data")).get("list_data");
        return list;
    }

    public List query_send_notice() throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        param.put("pageno", "1");
        param.put("pagesize", "10");
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/tools/notice/query_send_notice.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m = r.as(Map.class);
        List list = (List) ((Map) m.get("data")).get("list_data");
        return list;
    }

    public List query_receive_notice() throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        param.put("pageno", "1");
        param.put("pagesize", "10");
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/tools/notice/query_receive_notice.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m = r.as(Map.class);
        List list = (List) ((Map) m.get("data")).get("list_data");
        return list;
    }

    @Test
    public void testDeleteNotice() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
           // Map notice = getNoticeDetail();
            param.put("id", "368428324372480");//notice.get("id")//
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/tools/notice/delete.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testgetNoticeDetail() throws Exception {
        getNoticeDetail();
    }

    public Map getNoticeDetail() throws Exception {
        List<Map> noticeList = query_send_notice();
        if (noticeList != null) {
            for (Map notice : noticeList) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("id", notice.get("id"));
                Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/tools/notice/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m = r.as(Map.class);
                return (Map) m.get("data");
            }
        }
        return null;
    }




}
