package com.youi.business.project;

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
public class TestProcurementController extends TestRestBussinessBase {



    @Test
    public void testDeleteProcurement()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        List<Map> ProcurementList = getProcurementList();
        if(ProcurementList!=null&&ProcurementList.size()>0){
            Map Procurement = ProcurementList.get(0);
            param.put("id", Long.parseLong(String.valueOf(Procurement.get("id"))));
        }
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/procurement/delete.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testgetProcurementDetail()  throws Exception {
        getProcurementDetail();
    }
    public Map getProcurementDetail()  throws Exception {
        List<Map> ProcurementList = getProcurementList();
        if(ProcurementList!=null){
            for(Map Procurement:ProcurementList){
                Map<String,Object> param = new HashMap<String,Object>();
                param.put("id",Procurement.get("id"));
                Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/procurement/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m =   r.as(Map.class);
               return (Map)m.get("data");
            }
        }
        return null;
    }

    @Test
    public void testSaveProcurement()  throws Exception {
                Map<String,Object> param = new HashMap<String,Object>();
        int i = new Random().nextInt(100);
        param.put("pid","451789310558208");
        param.put("name","采购项目名称"+i);
        param.put("budget_amount",11.00);
        param.put("responsible_person",11);
        param.put("procurement_method","0");
        param.put("bidding_hang_date","2016-12-25");
        param.put("bidding_open_date","2016-12-27");
        param.put("bidding_win_date","2016-12-31");
        param.put("bidding_company","招标代理公司"+i);
        param.put("bid_amount",11);
        param.put("signup_end_date","2016-12-26");
        param.put("contract_record_date","2016-12-31");
        param.put("bidding_win_company","中标单位"+i);
        param.put("bid_notification",444760939577344l);
        List<Map> attachments = new ArrayList<Map>();
        Map m1 = new HashMap();
        m1.put("attach_code","1");
        m1.put("file_id","444760939577344");
        attachments.add(m1);
        Map m2 = new HashMap();
        m2.put("attach_code","1");
        m2.put("file_id","444781940375552");
        attachments.add(m2);
        param.put("attachments",attachments);


                Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/procurement/save.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
    }
    @Test
    public void testUpdateProcurement()  throws Exception {
            Map project = getProcurementDetail();
            Map<String,Object> param = new HashMap<String,Object>();
            int i = new Random().nextInt(100);
        param.put("id",project.get("id"));
        param.put("pid","451789310558208");
        param.put("name","采购项目名称"+i);
        param.put("budget_amount",11.00);
        param.put("responsible_person",11);
        param.put("procurement_method","0");
        param.put("bidding_hang_date","2016-12-25");
        param.put("bidding_open_date","2016-12-27");
        param.put("bidding_win_date","2016-12-31");
        param.put("bidding_company","招标代理公司"+i);
        param.put("bid_amount",11);
        param.put("signup_end_date","2016-12-26");
        param.put("contract_record_date","2016-12-31");
        param.put("bidding_win_company","中标单位"+i);
        param.put("bid_notification",444760939577344l);
        List<Map> attachments = new ArrayList<Map>();
        Map m1 = new HashMap();
        m1.put("attach_code","1");
        m1.put("file_id","444760939577344");
        attachments.add(m1);
        Map m2 = new HashMap();
        m2.put("attach_code","1");
        m2.put("file_id","444781940375552");
        attachments.add(m2);
        param.put("attachments",attachments);
            Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                    .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                    .post(""+PRE_PATH+"/procurement/save.do");
            r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
            r.prettyPrint();


    }


   @Test
    public void testgetProcurementList()  throws Exception {
       getProcurementList();
    }
    public List getProcurementList()  throws Exception {
        Map<String,String> param = new HashMap<String,String>();
        param.put("project_id", "451789310558208");
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/procurement/query.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m =   r.as(Map.class);
        List list =(List)((Map)m.get("data")).get("projects");
        return list;
    }


}
