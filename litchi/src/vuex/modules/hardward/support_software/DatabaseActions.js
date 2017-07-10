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
  console.info('database_reqQueryInit');
  ajaxReq(store, {

    url: 'hardware/database/query.do',
    body: requestBody,
    success: function (response) {
      let responseJson = response.json();
      store.dispatch(types.SET_HARDWARD_DATABASE, responseJson.data);
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
    url: 'hardware/database/delete.do',
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
  if (input_id != null && input_id != '') {
    let requestBody = {id: input_id};
    ajaxReq(store, {
      url: 'hardware/database/detail.do',
      body: requestBody,
      success: function (response) {
        let responseJson = response.json();

        store.dispatch(types.SET_HARDWARD_DATABASE_FORM, responseJson.data)
      },
      failure: function () {
      }
    });
  }
  else {
    store.dispatch(types.SET_HARDWARD_DATABASE_FORM, initForm);
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
    url: 'hardware/database/save.do',
    body: formData,
    success: function (response) {
      let responseJson = response.json();
      store.dispatch(types.SET_HARDWARD_DATABASE_FORM, responseJson.data)
      bootbox.hideAll();

    },
    failure: function () {
      bootbox.hideAll();
    }
  });
}

const initForm = {
  id: '',//
  name: '',//名称
  brand: '',//品牌
  version: '',//版本号
  address: '',//IP地址
  instance_name: '',//实例名
  port: '',//端口
  x86_list: [//服务器信息
    // {
    // x86_id: '',
    // x86_brand: '',
    // x86_type: '',
    // core_num: '',//核心数
    // memory_current_capacity: ''//内存容量
    // }
  ],

  storage_list: [
    // {
    // storage_id: '',
    // brand: '',//品牌
    // type: '',//版本号
    // disk_size: '',//磁盘容量
    // disk_current_num: '',//当前磁盘数
    // disk_raid: ''//raid
    // }
  ]
}
