/**
 * Created by cheng on 16/10/5.
 */

import * as types from '../../../hardware-mutation-types';

const state = {
  storageListInfo: {
    pagetotal:0, //总的数据条目数
    list_data:[
      {
        id:'', // 主键ID
        room_name:'',//机房名称
        cabinet_name:'', //机柜名称
        asset_num:'',//资产编号
        brand:'',//品牌
        type:'',//型号
        high:'',//高度
        width:'',//宽度
        depth:'',//深度
        main_usage:'',//主要用途
        disk_interface_type:'',//存储接口类型
        disk_size:'',//磁盘容量
        disk_current_num:'',//当前磁盘数
        disk_max_num:'',//最大磁盘数
        disk_raid:''//raid
      }
    ]
  },
  storageForm:{
    id:'', // 主键ID
    room_id:'',//机房ID
    cabinet_id:'', //机柜ID
    asset_num:'',//资产编号
    brand:'',//品牌
    type:'',//型号
    high:'',//高度
    width:'',//宽度
    depth:'',//深度
    main_usage:'',//主要用途,1-业务服务器,2-虚拟化服务器,3-数据库服务器
    disk_interface_type:'',//存储接口类型
    disk_size:'',//磁盘容量
    disk_current_num:'',//当前磁盘数
    disk_max_num:'',//最大磁盘数
    disk_raid:'',//raid
    description:''//其他描述
  }
}

const mutations = {
  [types.SET_HARDWARD_STORAGE] (state, data) {
    state.storageListInfo = data;
  },
  [types.SET_HARDWARD_STORAGE_FORM] (state, data) {
    state.storageForm = data;

  },
}

export default {
  state,
  mutations
}
