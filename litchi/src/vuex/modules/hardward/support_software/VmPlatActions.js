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
  console.info('vmplat_reqQueryInit');
  ajaxReq(store, {

    url: 'hardware/vm_platform/query.do',
    body: requestBody,
    success: function (response) {
      let responseJson = response.json();
      store.dispatch(types.SET_HARDWARD_VMPLAT, responseJson.data);
      bootbox.hideAll();
    },
    failure: function () {
      bootbox.hideAll();
    }
  });
}


export const delInfo = (store, input_id) => {
  let requestBody = {id: input_id};
  ajaxReq(store, {
    url: 'hardware/vm_platform/delete.do',
    body: requestBody,
    success: function (response) {
      let responseJson = response.json();
      console.info(responseJson.data.msg);
      reqQueryInit(store, 1, 10);
    },
    failure: function () {
      console.info(responseJson.data.msg);
    }
  });
}


//请求表单页面初始化
export const reqFormInit = (store, input_id) => {
  if (input_id != null) {
    let requestBody = {id: input_id};
    ajaxReq(store, {
      url: 'hardware/vm_platform/detail.do',
      body: requestBody,
      success: function (response) {
        let responseJson = response.json();
        store.dispatch(types.SET_HARDWARD_VMPLAT_FORM, responseJson.data)
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
    url: 'hardware/vm_platform/save.do',
    body: formData,
    success: function (response) {
      let responseJson = response.json();
      store.dispatch(types.SET_HARDWARD_VMPLAT_FORM, responseJson.data)
      bootbox.hideAll();

    },
    failure: function () {
      bootbox.hideAll();
    }
  });
}
