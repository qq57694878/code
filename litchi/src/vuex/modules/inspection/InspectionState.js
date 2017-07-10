/**
 * Created by lishushuai on 2016/12/26.
 */
import {SET_INSPECTION_TPL_LIST,SET_INSPECTION_TPL_DETAIL,SET_INSPECTION_TPL_DETAIL_STATE,DEL_INSPECTION_TPL_ITEM,SET_INSPECTION_PLAN_LIST,SET_INSPECTION_PLAN_DETAIL,SET_INSPECTION_PLAN_DETAIL_STATE} from '../../mutation-types'

const state = {
  tplList:{
    tpls:[
      {
        id:'1',
        name:'模板名称1',
        ri_code:'1',
        description:'概要说明', //概要说明
        item_count:'100', //检查项目数量
        status:'0'  //模板状态 0 停用 1 启用
      },
      {
        id:'2',
        name:'模板名称2',
        ri_code:'2',
        description:'概要说明', //概要说明
        item_count:'100', //检查项目数量
        status:'1'  //模板状态 0 停用 1 启用
      },
      {
        id:'3',
        name:'模板名称3',
        ri_code:'3',
        description:'概要说明', //概要说明
        item_count:'100', //检查项目数量
        status:'1'  //模板状态 0 停用 1 启用
      },
      {
        id:'4',
        name:'模板名称4',
        ri_code:'4',
        description:'概要说明', //概要说明
        item_count:'100', //检查项目数量
        status:'1'  //模板状态 0 停用 1 启用
      }
    ]
  },
  tplDetail:{
    id:'',
    name:'模板名称',
    ri_code:'巡检类型',
    description:'概要说明', //概要说明
    item_count:'100', //检查项目数量
    status:'0', //模板状态 0 停用 1 启用
    items:['item1 检查内存','item2检查CPU利用率'] //检查项数组
  },
  guard:{
    state:'',
    id:''
  },
  planList:{
    '0':[
      {
        id:'1',
        name:'111',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID11',
        worker_name:'执行人名称11',
        status:'0'
      },
      {
        id:'2',
        name:'222',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID22',
        worker_name:'执行人名称22',
        status:'0'
      },
      {
        id:'3',
        name:'333',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID33',
        worker_name:'执行人名称33',
        status:'1'
      },
      {
        id:'4',
        name:'444',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID44',
        worker_name:'执行人名称44',
        status:'1'
      }

    ],
    '1':[
      {
        id:'0',
        name:'000',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID00',
        worker_name:'执行人名称00',
        status:'0'
      },
      {
        id:'2',
        name:'222',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID22',
        worker_name:'执行人名称22',
        status:'0'
      },
      {
        id:'3',
        name:'333',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID33',
        worker_name:'执行人名称33',
        status:'1'
      },
      {
        id:'4',
        name:'444',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID44',
        worker_name:'执行人名称44',
        status:'1'
      }
    ],
    '2':[
      {
        id:'0',
        name:'000',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID00',
        worker_name:'执行人名称00',
        status:'0'
      },
      {
        id:'1',
        name:'111',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID11',
        worker_name:'执行人名称11',
        status:'0'
      },
      {
        id:'2',
        name:'222',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID22',
        worker_name:'执行人名称22',
        status:'0'
      },
      {
        id:'3',
        name:'333',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID33',
        worker_name:'执行人名称33',
        status:'1'
      },
      {
        id:'4',
        name:'444',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID44',
        worker_name:'执行人名称44',
        status:'1'
      }
    ],
    '3':[
      {
        id:'0',
        name:'000',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID00',
        worker_name:'执行人名称00',
        status:'0'
      },
      {
        id:'1',
        name:'111',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID11',
        worker_name:'执行人名称11',
        status:'0'
      },
      {
        id:'3',
        name:'333',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID33',
        worker_name:'执行人名称33',
        status:'1'
      },
      {
        id:'4',
        name:'444',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID44',
        worker_name:'执行人名称44',
        status:'1'
      }
    ],
    '4':[
      {
        id:'0',
        name:'000',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID00',
        worker_name:'执行人名称00',
        status:'0'
      },
      {
        id:'1',
        name:'111',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID11',
        worker_name:'执行人名称11',
        status:'0'
      },
      {
        id:'2',
        name:'222',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID22',
        worker_name:'执行人名称22',
        status:'0'
      },
      {
        id:'3',
        name:'333',
        period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
        worker_id:'执行人ID33',
        worker_name:'执行人名称33',
        status:'1'
      }
    ]
  },
  planDetail:{
    id:'1',
    ri_code:'1', //巡检类型
    name:'2222',
    period:'周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 ',
    worker_id:'执行人ID',
    worker_name:'执行人名称',
    status:'状态 1 启用 0 停用',
    tpl:{
      id:'1',
      name:'模板名称',
      description:'', //概要说明
      status:'0', //模板状态 0 停用 1 启用
      items:['item1 检查内存','item2检查CPU利用率'] //检查项数组
    },
    dev_list:[
      {
        dev_id:'111',
        dev_name:'111',
        dev_type:'1',
        dev_no:'资产编号',
        is_other_plan:'1' //是否已加入其它巡检计划
      },
      {
        dev_id:'222',
        dev_name:'222',
        dev_type:'1',
        dev_no:'资产编号222',
        is_other_plan:'0' //是否已加入其它巡检计划
      },
      {
        dev_id:'333',
        dev_name:'333',
        dev_type:'1',
        dev_no:'资产编号333',
        is_other_plan:'0' //是否已加入其它巡检计划
      }
    ]
  },
  devList:[
    {
      dev_id:'111',
      dev_name:'111',
      dev_type:'1',
      dev_no:'资产编号',
      is_other_plan:'1' //是否已加入其它巡检计划
    },
    {
      dev_id:'222',
      dev_name:'222',
      dev_type:'1',
      dev_no:'资产编号222',
      is_other_plan:'0' //是否已加入其它巡检计划
    },
    {
      dev_id:'333',
      dev_name:'333',
      dev_type:'1',
      dev_no:'资产编号333',
      is_other_plan:'0' //是否已加入其它巡检计划
    },
    {
      dev_id:'444',
      dev_name:'333',
      dev_type:'1',
      dev_no:'资产编号333',
      is_other_plan:'1' //是否已加入其它巡检计划
    },
    {
      dev_id:'555',
      dev_name:'555',
      dev_type:'1',
      dev_no:'资产编号2555',
      is_other_plan:'0' //是否已加入其它巡检计划
    },
    {
      dev_id:'666',
      dev_name:'666',
      dev_type:'1',
      dev_no:'资产编号3666',
      is_other_plan:'0' //是否已加入其它巡检计划
    },
    {
      dev_id:'777',
      dev_name:'777',
      dev_type:'1',
      dev_no:'资产编号777',
      is_other_plan:'1' //是否已加入其它巡检计划
    },
    {
      dev_id:'888',
      dev_name:'888',
      dev_type:'1',
      dev_no:'资产编号888',
      is_other_plan:'0' //是否已加入其它巡检计划
    },
    {
      dev_id:'999',
      dev_name:'999',
      dev_type:'1',
      dev_no:'资产编号999',
      is_other_plan:'0' //是否已加入其它巡检计划
    }
  ],
  planGuard:{
    state:'',
    id:'',
    tplType:''
  },
  devListScope:[
    '111','222','333'
  ]
}

const mutations = {
  [SET_INSPECTION_TPL_LIST] (state,list) {
    state.tplList = list
  },

  [SET_INSPECTION_TPL_DETAIL] (state,data) {
    state.tplDetail = data
  },

  [SET_INSPECTION_TPL_DETAIL_STATE](state, guard){
   state.guard = guard
  },

  [DEL_INSPECTION_TPL_ITEM](state, index){
    console.debug(index);
    if(index != -1){
      state.tplDetail.items.splice(index,1);
    }
  },

  [SET_INSPECTION_PLAN_LIST](state, data){
    state.planList = data.plan
  },

  [SET_INSPECTION_PLAN_DETAIL](state, data){
    state.planDetail = data;

    var dev_list = state.planDetail.dev_list;
    for(var i=0,length = dev_list.length;i < length; i++){
      state.devListScope.$set(i, dev_list[i].dev_id);
    }
  },

  [SET_INSPECTION_PLAN_DETAIL_STATE](state, guard){
    state.planGuard = guard
  }
}

export default {
  state,
  mutations
}
