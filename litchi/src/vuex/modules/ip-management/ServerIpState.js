/**
 * Created by cheng on 16/10/5.
 */

import * as types from '../../hardware-mutation-types';

const state = {
  ServerIpListInfo: {
    pagetotal: 0, //总的数据条目数
    list_data: [
      {
        id:'',//主键
        ip_add:'',//ip地址
        subnet_mask:'',//子网掩码
        gateway:'',//网关地址
        ip_type:'',//服务器监控主IP，1-主IP，2-从IP
        mac:'',//mac地址
        mac_fresh_time:''//mac地址刷新时间
      }
    ]
  },
  ServerIpForm:{
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


}

const mutations = {
  [types.SET_SERVER_ID_LIST] (state, data) {
    state.ServerIpListInfo = data;
  },
  [types.SET_SERVER_ID_FORM] (state, data) {
    //console.info(state.cabinetForm);
    state.ServerIpForm= data;
  }
}

export default {
  state,
  mutations
}
