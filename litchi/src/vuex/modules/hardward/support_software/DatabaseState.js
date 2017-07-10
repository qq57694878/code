/**
 * Created by cheng on 16/10/5.
 */

import * as types from '../../../hardware-mutation-types';

const state = {
  databaseListInfo: {
    pagetotal: 0, //总的数据条目数
    list_data: [
      {
        id: '',//
        name: '',//名称
        brand: '',//品牌
        version: '',//版本号
        address: '',//IP地址
        instance_name: '',//实例名
        port: '',//端口
        x86_num: '',//服务器数量
        storage_num: ''//存储数量
      }
    ]
  },
  databaseForm: {
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
    x86_list_data:[],

    storage_list: [
      // {
        // storage_id: '',
        // brand: '',//品牌
        // type: '',//版本号
        // disk_size: '',//磁盘容量
        // disk_current_num: '',//当前磁盘数
        // disk_raid: ''//raid
      // }
    ],
    storage_list_data:[]
  }
}

const mutations = {
  [types.SET_HARDWARD_DATABASE] (state, data) {
    state.databaseListInfo = data;
  },
  [types.SET_HARDWARD_DATABASE_FORM] (state, data) {
    state.databaseForm = data;
    if(data.x86_list != undefined&&data.x86_list.length>0){
      var arrayObj = new Array();
        for(var tmp in data.x86_list){
          // console.debug(data.x86_list[tmp].x86_id);
          arrayObj.push(data.x86_list[tmp].x86_id.toString());
        }
      data.x86_list_data=arrayObj;
    }
    else{
      data.x86_list_data=[];
    }
    if(data.storage_list != undefined&&data.storage_list.length>0){
      var arrayObj = new Array();
      for(var tmp in data.storage_list){
        // console.debug(data.x86_list[tmp].x86_id);
        arrayObj.push(data.storage_list[tmp].storage_id.toString());
      }
      data.storage_list_data=arrayObj;
    }
    else{
      data.storage_list_data=[];
    }

  },
}

export default {
  state,
  mutations
}
