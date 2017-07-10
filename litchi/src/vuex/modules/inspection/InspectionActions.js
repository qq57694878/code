import Vue from 'vue'
import * as types from '../../mutation-types'
import {ajaxReq} from '../common/ajaxReq'


export const reqInspectionTplList = (store,comp,requestBody) => {
  ajaxReq(store,{
    url:'ri/tpl/query.do',
    body:requestBody,
    success:function(response){
      let responseJson=response.json();
      console.debug(responseJson);
      store.dispatch(types.SET_INSPECTION_TPL_LIST,responseJson.data)
    },
    failure:function(){
      comp.$set('alertType','alert-danger');
      comp.$set('alertShow',true);
      comp.$set('alertContent','查询失败！');
    }
  });
}

export const clearInspectionTplDetail=(store)=>{
  let cleardata={ id:'',name:'',ri_code:'',description:'',item_count:'',status:'',items:[]};

  store.dispatch(types.SET_INSPECTION_TPL_DETAIL,cleardata)
}


export const setInspectionTplDetail=(store, comp, id, success, failed)=>{
  /*
   let requestBody={ id:id};

   ajaxReq(store,{
   url:'ri/tpl/detail.do',
   body:requestBody,
   success:function(response){
   let responseJson=response.json();
   console.debug(responseJson);
   store.dispatch(types.SET_INSPECTION_TPL_DETAIL,responseJson.data);
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

export const setEnterInspectionTplGuard=(store, guard)=>{
  store.dispatch(types.SET_INSPECTION_TPL_DETAIL_STATE,guard)
}

export const deleteOneItem=(store, id)=>{
  store.dispatch(types.DEL_INSPECTION_TPL_ITEM, id)
}

export const saveInspectionTpl=(store, comp, id, name, ri_code, description, status, items, success, failure)=>{
  let requestBody={ id:id, name:name, ri_code:ri_code, description:description, status:status, items:items};

  ajaxReq(store,{
    url:'ri/tpl/save.do',
    body:requestBody,
    success:function(response){
      let responseJson=response.json();
      console.debug(responseJson);
      store.dispatch(types.SET_INSPECTION_TPL_DETAIL,responseJson.data);
      if(success)success(comp);
    },
    failure:function(){
      comp.$set('alertType','alert-danger');
      comp.$set('alertShow',true);
      comp.$set('alertContent','保存失败！');
    }
  });
}

export const reqInspectionPlans=(store, comp, success, failure)=>{
  /*
   let requestBody={};

   ajaxReq(store,{
   url:'ri/plan/query.do',
   body:requestBody,
   success:function(response){
   let responseJson=response.json();
   console.debug(responseJson);
   store.dispatch(types.SET_INSPECTION_PLAN_LIST,responseJson.data);
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

export const setInspectionPlanDetail=(store, comp, id, success, failed)=>{
  /*
   let requestBody={ id:id};

   ajaxReq(store,{
   url:'ri/plan/detail.do',
   body:requestBody,
   success:function(response){
   let responseJson=response.json();
   console.debug(responseJson);
   store.dispatch(types.SET_INSPECTION_PLAN_DETAIL,responseJson.data);
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

export const setEnterInspectionPlanGuard=(store, guard)=>{
  store.dispatch(types.SET_INSPECTION_PLAN_DETAIL_STATE,guard)
}

export const clearInspectionPlanDetail=(store)=>{
  let cleardata={ id:'',ri_code:'',name:'',period:'',worker_id:'',worker_name:'',status:'',tpl:'',dev_list:[]};
  store.dispatch(types.SET_INSPECTION_PLAN_DETAIL,cleardata)
}

export const saveInspectionPlan=(store, comp, id, ri_code, name, tpl_id, period, worker_id, worker_name, status, dev_list, success, failure)=>{
  let requestBody={ id:'id', ri_code:'ri_code', name:'name', tpl_id:'tpl_id', period:'period', worker_id:'worker_id', worker_name:'worker_name', status:'status', dev_list:'dev_list'};

  ajaxReq(store,{
    url:'ri/plan/save.do',
    body:requestBody,
    success:function(response){
      let responseJson=response.json();
      console.debug(responseJson);
      store.dispatch(types.SET_INSPECTION_PLAN_DETAIL,responseJson.data);
      if(success)success(comp);
    },
    failure:function(){
      comp.$set('alertType','alert-danger');
      comp.$set('alertShow',true);
      comp.$set('alertContent','保存失败！');
    }
  });
}
