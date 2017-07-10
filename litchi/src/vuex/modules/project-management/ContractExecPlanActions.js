/**
 * Created by lishushuai on 2016/12/26.
 */
import Vue from 'vue'
import * as types from '../../mutation-types'
import {ajaxReq} from '../common/ajaxReq'

export const reqContractExecPlans = (store,comp,requestBody) => {

  ajaxReq(store,{
    url:'contract/query.do',
    body:requestBody,
    success:function(response){
      let responseJson=response.json();
      console.debug(responseJson);
      store.dispatch(types.SET_CONTRACT_EXEC_PLAN_LIST,responseJson.data)
    },
    failure:function(){
      comp.$set('alertType','alert-danger');
      comp.$set('alertShow',true);
      comp.$set('alertContent','查询失败！');
    }
  });
}

export const reqContractExecPlanDetail = (store, comp, cno,success,failure)=>{
 /*
 let requestBody={ id:cno};

  ajaxReq(store,{
    url:'contract/exec/detail.do',
    body:requestBody,
    success:function(response){
      let responseJson=response.json();
      console.debug(responseJson);
      store.dispatch(types.SET_CONTRACT_DETAIL,responseJson.data);
      if(success)success();
    },
    failure:function(){
      comp.$set('alertType','alert-danger');
      comp.$set('alertShow',true);
      comp.$set('alertContent','查询失败！');
    }
  });
  */
}

export const reqBusinessList = (store, comp, cno,success,failure)=>{
/*
  let requestBody={ id:cno};

  ajaxReq(store,{
    url:'contract/detail.do',
    body:requestBody,
    success:function(response){
      let responseJson=response.json();
      console.debug(responseJson);
      store.dispatch(types.SET_CONTRACT_BUSINESS_LIST,responseJson.data);
      if(success)success();
    },
    failure:function(){
      comp.$set('alertType','alert-danger');
      comp.$set('alertShow',true);
      comp.$set('alertContent','查询失败！');
    }
  });
  */
}

export const updReadOnlyFlag=(store, val, id,router)=>{
  store.dispatch(types.SET_CONTRACT_DETAIL_READONLY,val, id,router);
}

export const deleteOneHwman=(store, id)=>{
  store.dispatch(types.SET_CONTRACT_HWMAN_DEL, id);
}

export const deleteOneHwWarranty=(store, id)=>{
  store.dispatch(types.SET_CONTRACT_HWWARRANTY_DEL, id);
}

export const saveContractExecPlan=(store, comp, id, softBiz_ids, softmanScope, softmanBiz_ids, hw, hwman, hw_warranty, success, failure)=>{
  let requestBody={ id:id, soft:{biz_ids:softBiz_ids}, softman:{scope:softmanScope, bizs:softmanBiz_ids}, hw:hw, hwman:hwman,hw_warranty:hw_warranty};

  ajaxReq(store,{
    url:'contract/exec/save.do',
    body:requestBody,
    success:function(response){
      let responseJson=response.json();
      console.debug(responseJson);
      store.dispatch(types.SET_CONTRACT_DETAIL,responseJson.data);
      if(success)success();
    },
    failure:function(){
      comp.$set('alertType','alert-danger');
      comp.$set('alertShow',true);
      comp.$set('alertContent','保存失败！');
    }
  });
}

export const saveContractExec=(store, comp, id, softBiz, softmanScope, softmanBiz_ids, hw, hwman, hw_warranty, success, failure)=>{
  let requestBody={ id:id, soft:{bizs:softBiz}, softman:{scope:softmanScope, bizs:softmanBiz_ids}, hw:hw, hwman:hwman,hw_warranty:hw_warranty};

  ajaxReq(store,{
    url:'contract/exec/save.do',
    body:requestBody,
    success:function(response){
      let responseJson=response.json();
      console.debug(responseJson);
      store.dispatch(types.SET_CONTRACT_DETAIL,responseJson.data);
      if(success)success();
    },
    failure:function(){
      comp.$set('alertType','alert-danger');
      comp.$set('alertShow',true);
      comp.$set('alertContent','保存失败！');
    }
  });
}
