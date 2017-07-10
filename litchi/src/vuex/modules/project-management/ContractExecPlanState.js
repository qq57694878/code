/**
 * Created by lishushuai on 2016/12/26.
 */
import {SET_CONTRACT_EXEC_PLAN_LIST,SET_CONTRACT_DETAIL,SET_CONTRACT_BUSINESS_LIST,SET_CONTRACT_DETAIL_READONLY,SET_CONTRACT_HWWARRANTY_DEL,SET_CONTRACT_HWMAN_DEL} from '../../mutation-types'

const state = {
  execPlanList:{
    pagetotal:0,
    plans:[
      {
        id:'0',
        pid:'11111', //采购项目id
        procurement_name:'采购项目名称1111',// 采购项目名称
        cno:'1111', //合同编号
        name:'合同名称',
        vender_id:'vender id',
        vender_name:'vender name',
        amount:'123.00',
        sdate:'2016-12-12',
        edate:'2017-12-12',
        applicant_id:'123456',
        applicant_name:'申请人名字',
        apply_date:'2016-12-12',
        responsible_person_id:'负责人1', //负责人
        responsible_person_name:'负责人2', //负责人
        reception_time:'2017-12-12', //预计验收时间
        contract_file:'1212', //合同影印文件id
        status:'1', // 合同状态,
        ctype:['0','1','2'] //合同类型
      },
      {
        id:'1',
        pid:'2222', //采购项目id
        procurement_name:'采购项目名称2222',// 采购项目名称
        cno:'2222', //合同编号
        name:'合同名称2',
        vender_id:'vender id',
        vender_name:'vender name',
        amount:'123.00',
        sdate:'2016-12-12',
        edate:'2017-12-12',
        applicant_id:'123456',
        applicant_name:'申请人名字',
        apply_date:'2016-12-12',
        responsible_person_id:'负责人1', //负责人
        responsible_person_name:'负责人2', //负责人
        reception_time:'2017-12-12', //预计验收时间
        contract_file:'1212', //合同影印文件id
        status:'5', // 合同状态,
        ctype:['0'] //合同类型
      }
    ],
  },
  contractDetail:{
    id: '',
    pid: '', //采购项目id
    procurement_name: '',// 采购项目名称
    cno: '', //合同编号
    name: '合同名称',
    vender_id: 'vender id',
    vender_name: 'vender name',
    amount: '123.00',
    sdate: '合同开始时间',
    edate: '合同结束时间',
    applicant_id: '申请人id',
    applicant_name: '申请人名字',
    apply_date: '合同登记日期',
    responsible_person_id: '', //负责人
    responsible_person_name: '', //负责人
    estimated_reception_time: '', //预计验收时间
    contract_file: '', //合同影印文件id
    status: '5',// 合同状态,
    ctype: ['0', '1','2','3','4','5'] //合同类型
  },
  execDetail:{
    soft:{
      bizs:[
        {
          biz_id: '1',
          biz_name: '111',
          attachments: {   //附件hash列表
            attach_code: {
              1: {
                attach_code: '项目附件类型',
                file_id: '',
                file_name: '',
                file_type: '', //文件类型 pic | pdf | word | exel
                url: '',//下载url,
                creator_id: '创建者id',
                creator_name: '创建者名字',
                cdate: '创建日期',
              }
            },
            others: [    //除了预先配置要上传的附件外，其他附件的attach_code 都为空
              {
                attach_code: '',
                file_id: '',
                file_name: '',
                file_type: '', //文件类型 pic | pdf | word | exel
                url: '',//下载url,
                creator_id: '创建者id',
                creator_name: '创建者名字',
                cdate: '创建日期',
              }
            ]
          }
        },

        {
          biz_id: '2',
          biz_name: '222',
          attachments: {   //附件hash列表
            attach_code: {
              1: {
                attach_code: '项目附件类型',
                file_id: '',
                file_name: '',
                file_type: '', //文件类型 pic | pdf | word | exel
                url: '',//下载url,
                creator_id: '创建者id',
                creator_name: '创建者名字',
                cdate: '创建日期',
              }
            },
            others: [    //除了预先配置要上传的附件外，其他附件的attach_code 都为空
              {
                attach_code: '',
                file_id: '',
                file_name: '',
                file_type: '', //文件类型 pic | pdf | word | exel
                url: '',//下载url,
                creator_id: '创建者id',
                creator_name: '创建者名字',
                cdate: '创建日期',
              }
            ]
          }
        }
      ]
    },
    softman:{
      scope:['0','3','2','1','4','5'], //业务范围
      bizs:[
        {
          biz_id:'0',
          biz_name:'0000',
        },
        {
          biz_id:'1',
          biz_name:'1111',
        },
        {
          biz_id:'2',
          biz_name:'22222',
        },
        {
          biz_id:'3',
          biz_name:'33333',
        },
        {
          biz_id:'4',
          biz_name:'44444',
        },
        {
          biz_id:'5',
          biz_name:'55555',
        }
      ],
      workload:[
        {
          scope:'1',
          count:'', //记录数
          total:'' ,//工作量合计
        }
      ]
    },
    hw:{
      hwlist_file:{
        file_id:'',
        file_name:'',
        file_type:'', //文件类型 pic | pdf | word | exel
        url:'', //下载url,
        creator_id:'创建者id',
        creator_name:'创建者名字',
        cdate:'创建日期'
      },
      hwlist_arrival_file:{
        //同 hwlist_file
      },
      dev_list:[
        {
          dev_type:'',
          brand:'',
          model:'',
          arrival_date:'',
          warranty_sdate:'质保开始日期', //质保开始日期
          warranty_year:'质保年限', //质保年限
          deploy_status:'1' ,//部署状态
        }
      ]
    },
    hwman:[
      {
        inspection_id:'111', //巡检计划id
        inspection_name:'111', //巡检计划名称
        inspection_person:'333', //巡检计划名称
        inspection_rate:'4444', //巡检计划名称
      },
      {
        inspection_id:'2222', //巡检计划id
        inspection_name:'222', //巡检计划名称
        inspection_person:'333', //巡检计划名称
        inspection_rate:'4444', //巡检计划名称
      },
      {
        inspection_id:'333', //巡检计划id
        inspection_name:'333', //巡检计划名称
        inspection_person:'333', //巡检计划名称
        inspection_rate:'4444', //巡检计划名称
      },
      {
        inspection_id:'444', //巡检计划id
        inspection_name:'444', //巡检计划名称
        inspection_person:'333', //巡检计划名称
        inspection_rate:'4444', //巡检计划名称
      },
      {
        inspection_id:'555', //巡检计划id
        inspection_name:'555', //巡检计划名称
        inspection_person:'333', //巡检计划名称
        inspection_rate:'4444', //巡检计划名称
      },
      {
        inspection_id:'666', //巡检计划id
        inspection_name:'666', //巡检计划名称
        inspection_person:'333', //巡检计划名称
        inspection_rate:'4444', //巡检计划名称
      },
      {
        inspection_id:'777', //巡检计划id
        inspection_name:'777', //巡检计划名称
        inspection_person:'333', //巡检计划名称
        inspection_rate:'4444', //巡检计划名称
      }
    ],
    hw_warranty:[
      {
        dev_id:'1',
        dev_type:'0',
        dev_name:'linux',
        arrival_date:'到货日期',
        warranty_date:'原质保到期日期', //原质保到期日期
      },
      {
        dev_id:'2',
        dev_type:'1',
        dev_name:'linux',
        arrival_date:'到货日期',
        warranty_date:'原质保到期日期', //原质保到期日期
      },
      {
        dev_id:'3',
        dev_type:'2',
        dev_name:'linux',
        arrival_date:'到货日期',
        warranty_date:'原质保到期日期', //原质保到期日期
      },
      {
        dev_id:'4',
        dev_type:'3',
        dev_name:'linux',
        arrival_date:'到货日期',
        warranty_date:'原质保到期日期', //原质保到期日期
      },
      {
        dev_id:'5',
        dev_type:'4',
        dev_name:'linux',
        arrival_date:'到货日期',
        warranty_date:'原质保到期日期', //原质保到期日期
      },
      {
        dev_id:'6',
        dev_type:'5',
        dev_name:'linux',
        arrival_date:'到货日期',
        warranty_date:'原质保到期日期', //原质保到期日期
      },
      {
        dev_id:'7',
        dev_type:'2',
        dev_name:'linux',
        arrival_date:'到货日期',
        warranty_date:'原质保到期日期', //原质保到期日期
      }
    ]
  },
  businessList:{
    pagetotal:100, //总的数据条目数
    apps:[
      {    //app id
        id:'1',
        name:'app1',
        description:'description',
        seted:'1'
      },
      {
        id:'2',
        name:'app2',
        description:'description',
        seted:'1'
      }
      ,
      {
        id:'3',
        name:'app3',
        description:'description',
        seted:'1'
      }
      ,
      {
        id:'4',
        name:'app4',
        description:'description',
        seted:'1'
      }
      ,
      {
        id:'5',
        name:'app5',
        description:'description',
        seted:'1'
      }
      ,
      {
        id:'6',
        name:'app6',
        description:'description',
        seted:'1'
      }
      ,
      {
        id:'7',
        name:'app7',
        description:'description',
        seted:'1'
      }
      ,
      {
        id:'8',
        name:'app8',
        description:'description',
        seted:'1'
      }
      ,
      {
        id:'9',
        name:'app9',
        description:'description',
        seted:'1'
      }
    ]
  },
  softBizScope:['1','2','3','4'],
  softmanBizScope:['1','2','3','4'],
  bEditAble:true,
  detailContractId:'0',
  router:''
}

const mutations = {
  [SET_CONTRACT_EXEC_PLAN_LIST] (state,list) {
    state.execPlanList = list
  },

  [SET_CONTRACT_DETAIL] (state,list) {
    state.execDetail = list;

    //
    var softBizs = state.execDetail.soft.bizs;
    for(var i=0,length = softBizs.length;i < length; i++){
      state.softBizScope.$set(i, softBizs[i].biz_id);
    }

    var softmanBizs = state.execDetail.softman.bizs;
    for(var i=0,length = softmanBizs.length;i < length; i++){
      state.softmanBizScope.$set(i, softmanBizs[i].biz_id);
    }
  },

  [SET_CONTRACT_BUSINESS_LIST] (state,list) {
    state.businessList = list
  },

  [SET_CONTRACT_DETAIL_READONLY] (state, val, id,router){
    state.bEditAble = val;
    state.detailContractId= id;
    state.router= router
  },

  [SET_CONTRACT_HWWARRANTY_DEL] (state, id){
    var index = -1;

    console.debug(id);
    var hw_warranty = state.execDetail.hw_warranty;
    for(var i=0,length = hw_warranty.length;i < length; i++){
      if(hw_warranty[i].dev_id == id){
        index = i;
        break;
      }
    }
    console.debug(index);
    if(index != -1){
      state.execDetail.hw_warranty.splice(index,1);
    }
  },
  [SET_CONTRACT_HWMAN_DEL] (state, id){
    var index = -1;
    console.debug(id);
    var hwman = state.execDetail.hwman;
    for(var i=0,length = hwman.length;i < length; i++){
      if(hwman[i].inspection_id == id){
        index = i;
        break;
      }
    }
    console.debug(index);
    if(index != -1){
      state.execDetail.hwman.splice(index,1);
    }
  },


}

export default {
  state,
  mutations
}
