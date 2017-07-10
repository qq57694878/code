
import Vue from 'vue'

import * as types from '../../mutation-types'

import {ajaxReq} from '../common/ajaxReq'

export const reqProjectList = (store,comp,requestBody) => {
	// let requestBody={ pageno:pageNo,pagesize:pageSize};
	ajaxReq(store,{
					url:'project/query.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_PROJECT_LIST,responseJson.data)
					},
					failure:function(){ 
					  comp.$set('alertType','alert-danger');
					  comp.$set('alertShow',true);
					  comp.$set('alertContent','查询失败！');
    }
	});
}

export const clearOneProjectDetail = (store) => {
	let cleardata={ 
		id:'',
		name:'',
		year:'',
		apply_dept_id:"",
		apply_dept_name:"",
		total_investment:"",
		balance_investment:"",
		apply_date:'',
		expected_sdate:'',
		expected_edate:'',
		description:'',
		attachments:{  
			attach_code:{},
			others:[]
		},
		status:'0',
		applicant_id:'',
		applicant_name:''
	};
	store.dispatch(types.SET_ONE_PROJECT_DETAIL,cleardata)
}

export const setEnterProjectApplyAddGuard = (store,guard) => {

	store.dispatch(types.SET_PROJECTAPPLY_ADD_ENTERGUARD,guard);
}

export const reqOneProjectDetail = (store,projectId,success,failure) => {
	let requestBody={ id:projectId};

//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'project/detail.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_ONE_PROJECT_DETAIL,responseJson.data);
						if(success)success();
					},
					failure:function(){
						if(failure)failure();
					} 
	});
}

export const clearProjectQuery = (store) => {
	let cleardata={ year:'',projectname:'',apply_dept:''};

	store.dispatch(types.SET_PROJECT_QUERY,cleardata)
}

export const deleteOneProject = (store,component,projectId,success,failure) => {
	let requestBody={ id:projectId};

//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'project/delete.do',
				    body:requestBody,
		           	success:function(response){  
						store.dispatch(types.DELETE_PROJECT_SUCCESS,projectId)
						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','删除项目成功！');
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','删除项目失败！');
					} 
	});
}

export const saveOneProject = (store,component,isSubmit,requestBody,success,failure) => {

	console.debug('saveOneProject '+isSubmit);
	ajaxReq(store,{
					url:'project/save.do',
				    body:requestBody,
		           	success:function(response){  
						component.$router.go({name:'project_apply'});
					},
					failure:function(){
						if(isSubmit=='1'){
							console.debug('========if=============saveOneProject=================== ');
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','提交项目失败！');
						}else {
							console.debug('=========else============saveOneProject=================== ');
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','保存项目失败！');
						}
					} 
	});
}
