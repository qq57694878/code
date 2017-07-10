package com.youi.business.contract;

import com.youi.business.base.TestRestBussinessBase;
import com.youi.business.common.Constants;
import com.youi.core.util.StringUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.*;

/**
 * Created by jinliang on 2016/8/19.
 */
public class TestContractController extends TestRestBussinessBase {


    @Test
    public void testDeleteContract() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        List<Map> ContractList = getContractList();
        if (ContractList != null && ContractList.size() > 0) {
            Map Contract = ContractList.get(0);
            param.put("id", Long.parseLong(String.valueOf(Contract.get("id"))));
        }
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post("" + PRE_PATH + "/contract/delete.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testgetContractDetail() throws Exception {
        getContractDetail();
    }

    public Map getContractDetail() throws Exception {
        List<Map> ContractList = getContractList();
        if (ContractList != null) {
            for (Map Contract : ContractList) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("id", Contract.get("id"));
                Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                        .post("" + PRE_PATH + "/contract/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m = r.as(Map.class);
                return (Map) m.get("data");
            }
        }
        return null;
    }

    @Test
    public void testSelectExecContractDetail() throws Exception {
        List<Map> ContractList = getContractList();
        if (ContractList != null) {
            for (Map Contract : ContractList) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("id", Contract.get("id"));
                Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                        .post("" + PRE_PATH + "/contract/exec/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
            }
        }
    }

    @Test
    public void testAcceptanceContract() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        List<Map> ContractList = getContractList();
        Long id  = null;
        if (ContractList != null) {
            for (Map Contract : ContractList) {
                id = Long.parseLong(StringUtils.null2String(Contract.get("id")));
                break;
            }
        }
        param.put("id", id);
        param.put("reception_time", "2017-01-01");
        param.put("expert", "2017-01-01");
        param.put("expert_opinion_file", 444760939577344l);
        param.put("verdict_file", 444781940375552l);

        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post("" + PRE_PATH + "/ contract/acceptance.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testPaymentContract() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        List<Map> ContractList = getContractList();
        Long id  = null;
        if (ContractList != null) {
            for (Map Contract : ContractList) {
               id = Long.parseLong(StringUtils.null2String(Contract.get("id")));
                break;
            }
        }
        param.put("cid", id);
        List<Map<String,Object>> payments = new ArrayList<Map<String,Object>>();
       for(int j=0;j<3;j++){
           if(j==0){
               Map<String,Object> payment = new HashMap<String,Object>();
               payment.put("terms","付款条件"+i);
               payment.put("estimated_amount","10.00");
               payment.put("amount","10.00");
               payment.put("estimated_date","2017-01-01");
               payment.put("paydate","2017-01-01");
               payment.put("invoice_no","210281");
               payment.put("invoice_file",444781940375552l);
               payment.put("check_no","210282");
               payment.put("check_file",444781940375552l);
               payments.add(payment);
           }
           if(j==1){
               Map<String,Object> payment = new HashMap<String,Object>();
               payment.put("terms","付款条件"+i);
               payment.put("estimated_amount","10.00");
               payment.put("amount","10.00");
               payment.put("estimated_date","2017-02-01");
               payment.put("paydate","2017-01-03");
               payment.put("invoice_no","210281");
               payment.put("invoice_file",444781940375552l);
               payment.put("check_no","210282");
               payment.put("check_file",444781940375552l);
               payments.add(payment);
           }
           if(j==2){
               Map<String,Object> payment = new HashMap<String,Object>();
               payment.put("terms","付款条件"+i);
               payment.put("estimated_amount","10.00");
               payment.put("amount","10");
               payment.put("estimated_date","2017-12-31");
               payment.put("paydate","2017-12-31");
               payment.put("invoice_no","210281");
               payment.put("invoice_file",444781940375552l);
               payment.put("check_no","210282");
               payment.put("check_file",444781940375552l);
               payments.add(payment);
           }


       }
        param.put("payment",payments);
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post("" + PRE_PATH + "/contract/payment.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testSaveExecContract() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        List<Map> ContractList = getContractList();
        Long id  = null;
        if (ContractList != null) {
            for (Map Contract : ContractList) {
                id = Long.parseLong(StringUtils.null2String(Contract.get("id")));
                break;
            }
        }
        param.put("id", id);
        Map<String, Object> soft = new HashMap<String, Object>();
        Map<String, Object> softman = new HashMap<String, Object>();
        Map<String, Object> hw = new HashMap<String, Object>();
        List<Object> hwman = new ArrayList<Object>();
        List<Map<String,Object>> hw_warranty = new ArrayList<Map<String,Object>>();
        param.put("soft",soft);
        param.put("softman",softman);
        param.put("hw",hw);
        param.put("hwman",hwman);
        param.put("hw_warranty",hw_warranty);
        //软件开发
        if(true){
            List<Map<String,Object>>bizs = new ArrayList<Map<String,Object>>();
            Map<String,Object> biz= new HashMap<String,Object>();
            bizs.add(biz);
            biz.put("biz_id",396827789000704l);
            List<Map<String,Object>>attachments = new ArrayList<Map<String,Object>>();
            Map<String,Object> attachment= new HashMap<String,Object>();
            attachment.put("attach_code","1");
            attachment.put("file_id",444760939577344l);
            attachments.add(attachment);
            biz.put("attachments",attachments);
            soft.put("bizs",bizs);
        }
        //软件运维
        if(true){
            List<Map<String,Object>>bizs = new ArrayList<Map<String,Object>>();
            Map<String,Object> biz= new HashMap<String,Object>();
            bizs.add(biz);
            biz.put("biz_id",396827789000704l);
            List<Map<String,Object>>attachments = new ArrayList<Map<String,Object>>();
            Map<String,Object> attachment= new HashMap<String,Object>();
            attachment.put("attach_code","1");
            attachment.put("file_id",444760939577344l);
            attachments.add(attachment);
            biz.put("attachments",attachments);
            soft.put("bizs",bizs);
        }
        if(true){
            softman.put("scope",new String[]{"0","1","2"});
            softman.put("bizs",new Long[]{396827789000704l});
        }
        if(true){
            hw.put("hwlist_file",444760939577344l);
            hw.put("hwlist_arrival_file",444781940375552l);
            List<Map<String,Object>>dev_list = new ArrayList<Map<String,Object>>();
            Map<String,Object> dev= new HashMap<String,Object>();
            dev.put("dev_id",396685959380992l);
            dev.put("dev_type","HW_X86");
            dev_list.add(dev);
            hw.put("dev_list",dev_list);
        }
        if(true){
            hwman.add(396685959380992l);
            hwman.add(396685959380993l);
        }
        if(true){
            Map<String,Object> dev= new HashMap<String,Object>();
            dev.put("dev_id",396685959380992l);
            dev.put("dev_type","HW_X86");
            hw_warranty.add(dev);
        }

        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post("" + PRE_PATH + "/contract/exec/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testSaveContract() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);

        param.put("pid", "448940351258624");
        param.put("name", "合同名称" + i);
        param.put("cno", "1");
        param.put("vender_id", "4");
        param.put("amount", 30.00);
        param.put("sdate", "2017-01-01");
        param.put("edate", "2017-12-31");
        param.put("responsible_person_id", 14);
        param.put("reception_time", "2017-12-26");
        param.put("contract_file", 444760939577344l);
        List<Map> payments = new ArrayList<Map>();
        Map m0 = new HashMap();
        m0.put("terms", "付款条件" + i);
        m0.put("estimated_amount", "10.00");
        m0.put("estimated_date", "2017-01-01");
        payments.add(m0);
        Map m1 = new HashMap();
        m1.put("terms", "付款条件" + i);
        m1.put("estimated_amount", "10.00");
        m1.put("estimated_date", "2017-02-01");
        payments.add(m1);
        Map m2 = new HashMap();
        m2.put("terms", "付款条件" + i);
        m2.put("estimated_amount", "10.00");
        m2.put("estimated_date", "2017-12-31");
        payments.add(m2);
        param.put("payment", payments);


        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post("" + PRE_PATH + "/contract/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testUpdateContract() throws Exception {
        Map project = getContractDetail();
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("id", project.get("id"));
        param.put("pid", "448940351258624");
        param.put("name", "合同名称" + i);
        param.put("cno", "1");
        param.put("vender_id", "4");
        param.put("amount", 30.00);
        param.put("sdate", "2017-01-01");
        param.put("edate", "2017-12-31");
        param.put("responsible_person_id", 14);
        param.put("reception_time", "2017-12-26");
        param.put("contract_file", 444760939577344l);
        List<Map> payments = new ArrayList<Map>();
        Map m0 = new HashMap();
        m0.put("terms", "付款条件" + i);
        m0.put("estimated_amount", "10.00");
        m0.put("estimated_date", "2017-01-01");
        payments.add(m0);
        Map m1 = new HashMap();
        m1.put("terms", "付款条件" + i);
        m1.put("estimated_amount", "10.00");
        m1.put("estimated_date", "2017-02-01");
        payments.add(m1);
        Map m2 = new HashMap();
        m2.put("terms", "付款条件" + i);
        m2.put("estimated_amount", "10.00");
        m2.put("estimated_date", "2017-12-31");
        payments.add(m2);
        param.put("payment", payments);
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post("" + PRE_PATH + "/contract/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();


    }


    @Test
    public void testgetContractList() throws Exception {
        getContractList();
    }

    public List getContractList() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("pageno", "1");
        param.put("pagesize", "10");
        param.put("cno", "1");
        param.put("name", "合同");
        param.put("vender", "4");
        param.put("filter_applicant", "1");
        List<String> status = new ArrayList<String>();
        status.add("1");
        status.add("0");
        status.add("4");
        param.put("status",status);
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post("" + PRE_PATH + "/contract/query.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m = r.as(Map.class);
        List list = (List) ((Map) m.get("data")).get("contracts");
        return list;
    }


}
