/**
 * Created by cheng on 16/10/5.
 */

import * as types from '../../../hardware-mutation-types';

const state = {
  cabinetPagenum:1,
  cabinetListInfo: {
    pagetotal: 1, //总的数据条目数
    list_data: [
      {
        id: '', // 主键ID
        cabinet_name: '', //机柜名称
        brand: '',//品牌
        type: '',//型号
        high: '',//高度
        width: '',//宽度
        depth: '',//深度
        unit: '',//容量
        load_capacity: ''//承重（kg）
      }
    ]
  },
  cabinetForm:{
    id:'', // 主键ID
    room_id:'',//机房ID
    cabinet_name:'', //机柜名称
    brand:'',//品牌
    type:'',//型号
    high:'',//高度
    width:'',//宽度
    depth:'',//深度
    description:'',//详细说明
    unit:'',//容量
    materials:'',//材料及工艺
    def_level:'',//防护等级
    load_capacity:'',//承重（kg）
    asset_num:''//资产编号
  }


}

const mutations = {
  [types.SET_HARDWARD_CABINET] (state, data) {
    state.cabinetListInfo = data;
  },
  [types.SET_HARDWARD_CABINET_FORM] (state, data) {
    //console.info(state.cabinetForm);
    state.cabinetForm= data;
  },
  [types.UPDATE_HARDWARD_CABINET_PAGENUM] (state, input_pageNo) {
    state.cabinetPagenum=input_pageNo;

  }
}

export default {
  state,
  mutations
}
