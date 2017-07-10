/**
 * Created by cheng on 16/10/5.
 */

import * as types from '../../../hardware-mutation-types'

import {ajaxReq} from '../../common/ajaxReq'


//请求查询页面初始化
export const reqQueryInit = (store, pageNo, pageSize) => {
  bootbox.dialog({
    message: '<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
    buttons: [],
    onEscape: false,
    closeButton: false,
    animate: false
  });
  let requestBody = {pageno: pageNo, pagesize: pageSize};

  ajaxReq(store, {
    url: 'hardware/x86/init.do',
    body: requestBody,
    success: function (response) {
      let responseJson = response.json();
      store.dispatch(types.SET_HARDWARD_X86, responseJson.data);
      bootbox.hideAll();
    },
    failure: function () {
      bootbox.hideAll();
    }
  });
}


export const delX86Info = (store, input_id) => {
  let requestBody = {id: input_id};
  ajaxReq(store, {
    url: 'hardware/x86/delete.do',
    body: requestBody,
    success: function (response) {
      let responseJson = response.json();
      console.info(responseJson.data.msg);
      reqQueryInit(store, 1, 10);
    },
    failure: function () {
    }
  });
}


//请求表单页面初始化
export const reqFormInit = (store, input_id) => {

  if (input_id == null) {
    let requestBody = {device_type: 'hw_x86'};
    ajaxReq(store, {
      url: 'hardware/common/get_new_asset_num.do',
      body: requestBody,
      success: function (response) {
        let responseJson = response.json();
        store.dispatch(types.SET_HARDWARD_X86_FORM, responseJson.data)
      },
      failure: function () {
      }
    });
  }
  else {
    let requestBody = {id: input_id};
    ajaxReq(store, {
      url: 'hardware/x86/detail.do',
      body: requestBody,
      success: function (response) {
        let responseJson = response.json();
        store.dispatch(types.SET_HARDWARD_X86_FORM, responseJson.data)
      },
      failure: function () {
      }
    });

  }
}

//保存数据
export const submitDataChange = (store, component, formData) => {
  bootbox.dialog({
    message: '<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
    buttons: [],
    onEscape: false,
    closeButton: false,
    animate: false
  });
  ajaxReq(store, {
    url: 'hardware/x86/save.do',
    body: formData,
    success: function (response) {
      let responseJson = response.json();
      store.dispatch(types.SET_HARDWARD_X86_FORM, responseJson.data)
      bootbox.hideAll();

    },
    failure: function () {
      bootbox.hideAll();
    }
  });
}
