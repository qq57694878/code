package com.youi.business.contract.web;

import com.youi.business.common.Constants;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;

import com.youi.business.contract.service.ContractService;
import com.youi.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 合同管理
 */
@RestController
public class ContractController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private JWTService jwtService;
    /**
     * 获得查询列表
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/contract/query")
    public RestResult queryContractList(@Userid Long userid, @RequestBody(required = false) Map<String,Object> param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Page page = contractService.queryContractList(param);
        Map<String,Object>  result = new HashMap<String,Object>();
        result.put("pagetotal",page.getTotalCount());
        result.put("contracts",page.getResult());
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }
    /**
     * 获得查询列表
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/contract/detail")
    public RestResult selectContractDetail(@Userid Long userid, @RequestBody(required = false) Map<String,String> param) throws Exception {
        //param.put("userid",String.valueOf(userid));
        Long id = Long.parseLong(param.get("id"));
        Object result = contractService.selectContractDetail(id);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }


    /**
     * 付款
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/contract/payment")
    public RestResult paymentContract(@Userid Long userid,@RequestBody Map<String,Object>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = contractService.paymentContract(param);
        if(r!=null){
            String token =jwtService.getToken(userid);
            return new RestResult(token,r);
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }
    /**
     * 验收
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/contract/acceptance")
    public RestResult acceptanceContract(@Userid Long userid,@RequestBody Map<String,Object>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = contractService.acceptanceContract(param);
        if(r!=null){
            String token =jwtService.getToken(userid);
            return new RestResult(token,r);
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }


    /**
     * 执行保存
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/contract/exec/save")
    public RestResult saveExecContract(@Userid Long userid,@RequestBody Map<String,Object>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        Object r = contractService.saveExecContract(param);
        if(r!=null){
            String token =jwtService.getToken(userid);
            return new RestResult(token,r);
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }
    /**
     * 合同执行详细接口
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/contract/exec/detail")
    public RestResult selectExecContractDetail(@Userid Long userid, @RequestBody(required = false) Map<String,String> param) throws Exception {
        //param.put("userid",String.valueOf(userid));
        Long id = Long.parseLong(param.get("id"));
        Object result = contractService.selectExecContractDetail(id);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }
    /**
     * 保存
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/contract/save")
    public RestResult saveContract(@Userid Long userid,@RequestBody Map<String,Object>param) throws Exception {
        param.put("userid",String.valueOf(userid));

        Object r = contractService.saveContract(param);
        if(r!=null){
            String token =jwtService.getToken(userid);
            return new RestResult(token,r);
        }else{
            return new RestResult(Constants.ERROR_CODE_500);
        }
    }
    /**
     * 删除
     * @param userid
     * @return
     * @throws IOException
     */
    @RequestMapping("/contract/delete")
    public RestResult deleteContract(@Userid Long userid,@RequestBody Map<String,Object>param) throws Exception {
        param.put("userid",String.valueOf(userid));
        String token =jwtService.getToken(userid);
        try{
            contractService.deleteContract(param);
            return new RestResult(token,new Object());
        }catch (Exception e){
            Map<String,String> msg = new HashMap<String,String>();
            msg.put("msg","删除项目失败，该项目已被关联使用");
            return new RestResult(Constants.DEFAULT_VERSION,Constants.ERROR_CODE_200,msg,token);
        }
    }
}
