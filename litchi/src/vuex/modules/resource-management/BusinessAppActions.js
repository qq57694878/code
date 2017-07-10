import Vue from 'vue'

import * as types from '../../mutation-types'

import {ajaxReq} from '../common/ajaxReq'

export const reqBusinessAppList = (store,pageNo,pageSize) => {
	let requestBody={ pageno:pageNo,pagesize:pageSize};

	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'sysresource/biz_app/init.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_BUSINESSAPPS,responseJson.data)
					},
					failure:function(){} 
	});
}

export const clearOneBizAppDetail = (store) => {
	let cleardata={ id:'',name:'',org_id_list:'',vendor_id:'',online_time:'',description:''};

	store.dispatch(types.SET_ONEBIZAPP_DETAIL,cleardata)
}

export const reqOneBizAppDetail = (store,bizappId,success,failure) => {
	let requestBody={ id:bizappId};

//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'sysresource/biz_app/detail.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_ONEBIZAPP_DETAIL,responseJson.data)
						if(success)success();
					},
					failure:function(){
						if(failure)failure();
					} 
	});
}

export const stopOneBizApp = (store,component,bizappId) => {
	let requestBody={ id:bizappId};

	console.debug('stopOneBizApp');
	ajaxReq(store,{
					url:'sysresource/biz_app/disable.do',
				    body:requestBody,
		           	success:function(){
						store.dispatch(types.STOP_ONEBIZAPP_SUCCESS,bizappId)

						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','停止业务系统成功！');
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','停止业务系统失败！');
					} 
	});
}

export const resumeOneBizApp = (store,component,bizappId) => {
	let requestBody={ id:bizappId};

//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'sysresource/biz_app/recovery.do',
				    body:requestBody,
		           	success:function(){
						store.dispatch(types.RESUME_ONEBIZAPP_SUCCESS,bizappId)

						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','恢复业务系统成功！');
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','恢复业务系统失败！');
					} 
	});
}

export const saveOneBizApp = (store,component,requestBody) => {
	// let requestBody={ id:roomId,name:roomName,description:roomDescription,area:roomArea,complete_date:roomDate};

//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'sysresource/biz_app/save.do',
				    body:requestBody,
		           	success:function(){
						component.$router.go({name:'businessapp'});
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','保存失败！');
					} 
	});
}
