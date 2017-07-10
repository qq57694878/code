/**
 * Created by cheng on 16/10/5.
 */

import * as types from '../../../hardware-mutation-types'

import {ajaxReq} from '../../common/ajaxReq'


export const updatePageNum = (store, input_pageNo) =>{
  store.dispatch(types.UPDATE_HARDWARD_CABINET_PAGENUM, input_pageNo)
}

//请求查询页面初始化
export const reqQueryInit = (store, pageNo, pageSize) => {
  bootbox.dialog({
    message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
    buttons:[],
    onEscape:false,
    closeButton:false,
    animate:false
  });
  if (pageNo == null ||pageNo == undefined) {
    pageNo = store.state.hardward_cabinet.cabinetPagenum;
  }
  if (pageSize == null) {
    pageSize = 10;
  }
  let requestBody = {pageno: pageNo, pagesize: pageSize};

  ajaxReq(store, {
    url: 'hardware/cabinet/init.do',
    body: requestBody,
    success: function (response) {
      let responseJson = response.json();
      store.dispatch(types.SET_HARDWARD_CABINET, responseJson.data)
      bootbox.hideAll();
    },
    failure: function () {
      bootbox.hideAll();
    }
  });
}

//请求表单页面初始化
export const reqFormInit = (store, input_id) => {

  if (input_id == null) {
    let requestBody = {device_type: 'hw_cabinet'};
    ajaxReq(store, {
      url: 'hardware/common/get_new_asset_num.do',
      body: requestBody,
      success: function (response) {
        let responseJson = response.json();
        store.dispatch(types.SET_HARDWARD_CABINET_FORM, responseJson.data)
      },
      failure: function () {
      }
    });
  }
  else {
    let requestBody = {id: input_id};
    ajaxReq(store, {
      url: 'hardware/cabinet/detail.do',
      body: requestBody,
      success: function (response) {
        let responseJson = response.json();
        store.dispatch(types.SET_HARDWARD_CABINET_FORM, responseJson.data)
      },
      failure: function () {
      }
    });

  }
}


export const delCabinetInfo = (store, input_id,success,failure) => {
  let requestBody = {id: input_id};
  ajaxReq(store, {
    url: 'hardware/cabinet/delete.do',
    body: requestBody,
    success: function (response) {
      let responseJson = response.json();
      console.info(responseJson.data.msg);
      if(responseJson.data.msg==null||responseJson.data.msg==''){
        if(success)success();
      }
      else{
      //  todo 不能删除提示
        if(failure)failure(responseJson.data.msg);
      }
    },
    failure: function () {


    }
  });
}


//保存数据
export const submitDataChange = (store,component,formData) => {
  bootbox.dialog({
    message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
    buttons:[],
    onEscape:false,
    closeButton:false,
    animate:false
  });
  ajaxReq(store,{
    url:'hardware/cabinet/save.do',
    body:formData,
    success:function(response){
      let responseJson = response.json();
      store.dispatch(types.SET_HARDWARD_CABINET_FORM, responseJson.data)
      bootbox.hideAll();

    },
    failure:function(){
      bootbox.hideAll();
    }
  });
}


