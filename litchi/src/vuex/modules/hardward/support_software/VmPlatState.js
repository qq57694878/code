/**
 * Created by cheng on 16/10/5.
 */

import * as types from '../../../hardware-mutation-types';

const state = {
  vmplatListInfo: {
    pagetotal: 0, //总的数据条目数
    list_data: [
      {
        id: '',//
        name: '',//平台名称
        brand: '',//品牌
        type: '',//版本号
        console: '',//控制台地址
        x86_num: '',//服务器数量
        storage_num: ''//存储数量
      }
    ]
  },
  vmplatForm: {
    id: '',//
    name: '',//平台名称
    brand: '',//品牌
    type: '',//版本号
    console: '',//控制台地址
    x86_list: [//服务器信息
      // {
        // x86_id: '',
        // x86_brand: '品牌',
        // x86_type: '型号',
        // core_num: '',//核心数
        // memory_current_capacity: ''//内存容量
      // }
    ],
    storage_list: [
      // {
        // storage_id: '',
        // brand: '',//品牌
        // type: '',//型号
        // disk_size: '',//磁盘容量
        // disk_current_num: '',//当前磁盘数
        // disk_raid: ''//raid
      // }
    ]
  }
}

const mutations = {
  [types.SET_HARDWARD_VMPLAT] (state, data) {
    state.vmplatListInfo = data;
  },
  [types.SET_HARDWARD_VMPLAT_FORM] (state, data) {
    state.vmplatForm = data;

  },
}

export default {
  state,
  mutations
}
