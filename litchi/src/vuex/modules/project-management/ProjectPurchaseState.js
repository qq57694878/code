import {DELETE_PROJECT_PROCUREMENT_SUCCESS,SET_PROCUREMENT_DETAIL_ENTERGUARD, SET_PROJECT_PURCHASE_LIST,SET_ONE_PROJECT_PROCUREMENT_DETAIL} from '../../mutation-types'

const state = {
	projectpurchaselist:{
		projects:[
			{ 
				id:'1',
				pid:'所属项目id',
				name:'app1',
				budget_amount:'预算金额',
				apply_dept_name:"dept_name",
				responsible_person_id:'项目负责人',
				responsible_person:'项目负责人',
				procurement_method:'0',//采购方式码值 0 公开招标~4 询价
				status:'0'
			},
			{ 
				id:'1',
				pid:'所属项目id',
				name:'app1',
				budget_amount:'预算金额',
				apply_dept_name:"dept_name",
				responsible_person_id:'项目负责人',
				responsible_person:'项目负责人',
				procurement_method:'0',//采购方式码值 0 公开招标~4 询价
				status:'1'
			},
			{ 
				id:'1',
				pid:'所属项目id',
				name:'app1',
				budget_amount:'预算金额',
				apply_dept_name:"dept_name",
				responsible_person_id:'项目负责人',
				responsible_person:'项目负责人',
				procurement_method:'0',//采购方式码值 0 公开招标~4 询价
				status:'3'
			}
		]
	},
	purchasedetailenterguard:{
		state:'',
		purchaseid:''
	},
	onepurchase_detail:{
		id:'1',
		pid:'所属项目id',
		name:'app1',
		budget_amount:'预算金额',
		apply_dept_name:"app2",
		responsible_person_id:'项目负责人',
		responsible_person:'项目负责人',
		procurement_method:'0',//采购方式码值 0 公开招标~4 询价
		bidding_hang_date:'挂标日期',
		bidding_open_date:'开标日期',
		bidding_win_date:'中标日期',
		bid_amount:'中标金额',
		signup_end_date:'报名截止日期',
		contract_record_date:'合同备案日期',
		bidding_win_company:'中标单位',
		bidding_company:'招标代理公司',
		bid_notification:{
			file_id:'',
			file_name:'',
			file_type:'',
			url:'',
			creator_id:'',
			creator_name:'',
			cdate:''
		},
		status:'0',
		attachments:{  
			'1':{
				attach_code:'1',
				file_id:'',
				file_name:'hello',
				file_type:'', //文件类型 pic | pdf | word | exel
				url:'',
				creator_id:'',
				creator_name:'hhh',
				cdate:'2016-09-08'
			},
			'others':[
			{
				attach_code:'1',
				file_id:'',
				file_name:'hello',
				file_type:'', //文件类型 pic | pdf | word | exel
				url:'',
				creator_id:'',
				creator_name:'hhh',
				cdate:'2016-09-08'
			}
			]
		}
	}
	
}

const mutations = {
	[SET_PROJECT_PURCHASE_LIST] (state,list) {
		state.projectpurchaselist = list
	},
	[SET_ONE_PROJECT_PROCUREMENT_DETAIL] (state,detail) {
		state.onepurchase_detail = detail
	},
	[SET_PROCUREMENT_DETAIL_ENTERGUARD] (state,enterguard) {
		state.purchasedetailenterguard = enterguard
	},
	[DELETE_PROJECT_PROCUREMENT_SUCCESS] (state,purchaseid) {
		var arr = state.projectpurchaselist.projects;
		var index = -1; 
		for(var i=0,length = arr.length;i < length; i++){
			if(arr[i].id==purchaseid){
				index= i;
				break;
			}
		}
		if(index != -1){
			// console.debug("====DELETE_NOTICE_SUCCESS========"+index);
			arr.splice(index,1);
		}

	}
}

export default {
	state,
	mutations
}
