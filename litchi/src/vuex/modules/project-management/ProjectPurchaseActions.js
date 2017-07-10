

import Vue from 'vue'

import * as types from '../../mutation-types'

import {ajaxReq} from '../common/ajaxReq'

export const reqProjectProcurement = (store,requestBody,success,failure) => {
	// let requestBody={ id:projectId};

//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'procurement/query.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_PROJECT_PURCHASE_LIST,responseJson.data);
						if(success)success();
					},
					failure:function(){
						if(failure)failure();
					} 
	});
}

export const reqAllProcurement = (store,comp,success,failure) => {
	let requestBody={};
	ajaxReq(store,{
					url:'procurement/query.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						if(success)success(comp, responseJson.data.projects);
					},
					failure:function(){
						if(failure)failure();
					} 
	});
}


export const clearOneProjectProcurementDetail = (store,pid) => {
	// let cleardata={ id:'',pid:'',name:'',budget_amount:'',apply_dept_name:'',responsible_person:'',procurement_method:'',bidding_hang_date:'',bidding_open_date:'',bidding_win_date:'',bid_amount:'',signup_end_date:'',contract_record_date:'',bidding_win_company:'',bidding_company:'',bid_notification.file_id:'',bid_notification.file_name:'',bid_notification.file_type:'',bid_notification.url:'',bid_notification.creator_id:'',bid_notification.creator_name:'',bid_notification.cdate:'',status:'0',attachments:{}};
	let cleardata={
		 id:'',
		 pid:pid,
		name:'',
		budget_amount:'',
		apply_dept_name:'',
		responsible_person:'',
		procurement_method:'',//采购方式码值 0 公开招标~4 询价
		bidding_hang_date:'',
		bidding_open_date:'',
		bidding_win_date:'',
		bid_amount:'',
		signup_end_date:'',
		contract_record_date:'',
		bidding_win_company:'',
		bidding_company:'',
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
			attach_code:{},
			others:[]
		}
		
	}

	store.dispatch(types.SET_ONE_PROJECT_PROCUREMENT_DETAIL,cleardata)
}

export const setEnterProcurementDetailGuard = (store,guard) => {

	store.dispatch(types.SET_PROCUREMENT_DETAIL_ENTERGUARD,guard);
}

export const reqOneProjectProcurementDetail = (store,procurementId,success,failure) => {
	let requestBody={ id:procurementId};

//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'procurement/detail.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
 	// console.debug('reqOneProjectProcurementDetail');
	// console.debug(responseJson);
						store.dispatch(types.SET_ONE_PROJECT_PROCUREMENT_DETAIL,responseJson.data);
						if(success)success();
					},
					failure:function(){
						if(failure)failure();
					} 
	});
}

export const saveOneProjectProcurementDetail = (store,component,requestBody,success,failure) => {

//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'procurement/save.do',
				    body:requestBody,
		           	success:function(response){  
						if(success)success();
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','保存采购项目失败！');
						if(failure)failure();
					} 
	});
}
export const deleteOneProjectProcurement = (store,component,purchaseid,success,failure) => {
	var requestBody = {id:purchaseid};
//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'procurement/delete.do',
				    body:requestBody,
		           	success:function(response){  
						store.dispatch(types.DELETE_PROJECT_PROCUREMENT_SUCCESS,purchaseid);

						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','删除采购项目成功！');
						if(success)success();
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','删除采购项目失败！');
						if(failure)failure();
					} 
	});
}
