import {SET_CONTRACT_PAYMENT_DETAIL_ENTERGUARD,SET_CONTRACT_ACCEPTANCE_DETAIL_ENTERGUARD,SET_CONTRACT_REGISTER_DETAIL_CONTRACTFILEID,SET_CONTRACT_REGISTER_DETAIL_VENDORID,SET_CONTRACT_REGISTER_DETAIL_PID,ADD_CONTRACT_REGISTER_DETAIL_PAYMENT, SET_CONTRACT_REGISTER_DETAIL_PAYDETAIL_ENTERGUARD, DELETE_CONTRACT_REGISTER_DETAIL_PAYMENT,SET_CONTRACT_REGISTER_DETAIL_ENTERGUARD, SET_CONTRACT_LIST,SET_CONTRACT_REGISTER_DETAIL,SET_CONTRACT_QUERY } from '../../mutation-types'

const state = {
	contractslist:{
		pagetotal:0,
		contracts:[
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
			estimated_date:'1212', //合同影印文件id
			amount_paid:'1212', //合同影印文件id
			unpaid_amount:'1212', //合同影印文件id
			nextpay_amount:'1212', //合同影印文件id
			status:'1', // 合同状态,
			payment_status:'1', // 合同状态,
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
			estimated_date:'1212', //合同影印文件id
			amount_paid:'1212', //合同影印文件id
			unpaid_amount:'1212', //合同影印文件id
			nextpay_amount:'1212', //合同影印文件id
			status:'3', // 合同状态,
			payment_status:'0', // 合同状态,
			ctype:['0'] //合同类型
		  }
		]
	},
	onecontract_detail:{
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
		supervision:'',
		responsible_person_id: '', //负责人
		responsible_person_name: '', //负责人
		estimated_reception_time: '', //预计验收时间
		contract_file: '', //合同影印文件id
		status: '1',// 合同状态,
		ctype: ['0', '2'], //合同类型
		payment:[
		{
			terms:'付款条件',
			estimated_amount:'预计付款金额',
			amount:'付款金额',
			estimated_date:'预计付款时间',
			paydate:'',
			invoice_no:'',
			invoice_file:
			{
				file_id:'',
				file_name:'',
				file_type:'',
				url:'',
				creator_id:'',
				creator_name:'',
				cdate:'',
				},
			check_no:'',
			check_file:
			{
				file_id:'',
				file_name:'',
				file_type:'',
				url:'',
				creator_id:'',
				creator_name:'',
				cdate:'',
			},
			status:'0',
		}
		],
		reception_time:'',
		expert:'',
		expert_opinion_file:
		{
				file_id:'',
				file_name:'',
				file_type:'',
				url:'',
				creator_id:'',
				creator_name:'',
				cdate:'',
		},
		verdict_file:
		{
				file_id:'',
				file_name:'',
				file_type:'',
				url:'',
				creator_id:'',
				creator_name:'',
				cdate:'',
		}
	},
	contractquery:{
		contractNum:'',
	    contractName:'',
		vendor:'',
	    contractStatus:'',
	    paymentStatus:''
	},
	registerdetailenterguard:{
		state:'',
		contractid:'',
		router:'',
	},
	registerdetailpaydetailenterguard:{
		state:'',
		payindex:''
	},
	acceptancedetailenterguard:{
		state:'',
		contractid:'',
		router:''
	},
	paymentdetailenterguard:{
		state:'',
		contractid:'',
		router:''
	}
}
const mutations = {
	[SET_CONTRACT_LIST] (state,list) {
		state.contractslist = list
	},
	[SET_CONTRACT_REGISTER_DETAIL] (state,detail) {
		state.onecontract_detail = detail
	},
	[SET_CONTRACT_REGISTER_DETAIL_ENTERGUARD] (state,enterguard) {
		state.registerdetailenterguard = enterguard
	},
	[SET_CONTRACT_REGISTER_DETAIL_PID] (state,id) {
		state.onecontract_detail.pid = id
	},
	[SET_CONTRACT_REGISTER_DETAIL_VENDORID] (state,id) {
		state.onecontract_detail.vender_id = id
	},
	[SET_CONTRACT_REGISTER_DETAIL_CONTRACTFILEID] (state,id) {
		state.onecontract_detail.contract_file = id
	},
	[SET_CONTRACT_QUERY] (state,query) {
		state.contractquery = query
	},
	[DELETE_CONTRACT_REGISTER_DETAIL_PAYMENT] (state,payindex) {
		var arr = state.onecontract_detail.payment;
		// var index = -1; 
		// for(var i=0,length = arr.length;i < length; i++){
		// 	if(arr[i].id==purchaseid){
		// 		index= i;
		// 		break;
		// 	}
		// }
		// if(index != -1){
			// console.debug("====DELETE_NOTICE_SUCCESS========"+index);
			arr.splice(payindex,1);
		// }

	// }

	},
	[SET_CONTRACT_REGISTER_DETAIL_PAYDETAIL_ENTERGUARD] (state,enterguard) {
		state.registerdetailpaydetailenterguard = enterguard
	},
	[SET_CONTRACT_ACCEPTANCE_DETAIL_ENTERGUARD] (state,enterguard) {
		state.acceptancedetailenterguard = enterguard
	},
	[SET_CONTRACT_PAYMENT_DETAIL_ENTERGUARD] (state,enterguard) {
		state.paymentdetailenterguard = enterguard
	},SET_CONTRACT_PAYMENT_DETAIL_ENTERGUARD,
	[ADD_CONTRACT_REGISTER_DETAIL_PAYMENT] (state,paydata) {
		var arr = state.onecontract_detail.payment;
		arr.push(paydata)
	},
}

export default {
	state,
	mutations
}
