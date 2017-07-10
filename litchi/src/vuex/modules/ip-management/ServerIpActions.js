import Vue from 'vue'

import * as types from '../../hardware-mutation-types'

import {ajaxReq} from '../common/ajaxReq'

export const reqQueryInit = (store,biz_id, biz_type) => {

  bootbox.dialog({
    message: '<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
    buttons: [],
    onEscape: false,
    closeButton: false,
    animate: false
  });
  let requestBody = {biz_type: biz_type, biz_id: biz_id};
  ajaxReq(store, {
    url: 'ip/ip_manage/query.do',
    body: requestBody,
    success: function (response) {
      let responseJson = response.json();
      store.dispatch(types.SET_SERVER_ID_LIST, responseJson.data);
      console.debug(responseJson.data);
      bootbox.hideAll();
    },
    failure: function () {
      bootbox.hideAll();
    }
  });
}

//请求表单页面初始化
export const reqFormInit = (store, input_id) => {
  if(input_id == ':ip_id'){
    store.dispatch(types.SET_SERVER_ID_FORM, initForm);

  }
  else {
    let requestBody = {id: input_id};
    ajaxReq(store, {
      url: 'ip/ip_manage/detail.do',
      body: requestBody,
      success: function (response) {
        let responseJson = response.json();
        store.dispatch(types.SET_SERVER_ID_FORM, responseJson.data)
      },
      failure: function () {
      }
    });
  }

}

//保存数据
export const submitDataChange = (store, component, formData,biz_id, biz_type) => {
  bootbox.dialog({
    message: '<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
    buttons: [],
    onEscape: false,
    closeButton: false,
    animate: false
  });
  formData.biz_id=biz_id;
  formData.biz_type=biz_type;

  console.debug(formData);
  ajaxReq(store, {
    url: 'ip/ip_manage/save.do',
    body: formData,
    success: function (response) {
      let responseJson = response.json();
      store.dispatch(types.SET_SERVER_ID_FORM, responseJson.data)
      bootbox.hideAll();

    },
    failure: function () {
      bootbox.hideAll();
    }
  });
}


export const delIPInfo = (store, input_id,success) => {
  let requestBody = {id: input_id};
  ajaxReq(store, {
    url: 'ip/ip_manage/delete.do',
    body: requestBody,
    success: function (response) {
      let responseJson = response.json();
      console.info(responseJson.data.msg);
      if(success)success();
    },
    failure: function () {
    }
  });
}


export const setMainIp = (store, input_id,success) => {
  let requestBody = {id: input_id};
  console.info(input_id);
  ajaxReq(store, {
    url: 'ip/ip_manage/set_main_ip.do',
    body: requestBody,
    success: function (response) {
      let responseJson = response.json();

      if(success)success();
    },
    failure: function () {
      console.info("failure");
    }
  });
}


const initForm = {
  id:'',//主键
  biz_type:'',//业务类型，表名
  biz_id:'',//业务表主键
  ip_add:'',//ip地址
  subnet_mask:'',//子网掩码
  gateway:'',//网关地址
  ip_type:'',//服务器监控主IP，1-主IP，2-从IP
  mac:'',//mac地址
  mac_fresh_time:''//mac地址刷新时间
}

