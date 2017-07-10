import Vue from 'vue'

import * as types from '../../mutation-types'

import {ajaxReq} from '../common/ajaxReq'

export const reqVendorList = (store,pageNo,pageSize) => {
	let requestBody={ pageno:pageNo,pagesize:pageSize};

	ajaxReq(store,{
					url:'sysresource/vendor/init.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						console.debug(responseJson.data);
						store.dispatch(types.SET_VENDORS,responseJson.data)
					},
					failure:function(){} 
	});
}

export const clearOneVendorDetail = (store) => {
	let cleardata={ id:'',org_id:'',vendor_name:'',manager:'',tel:'',property:'',scope:'',register_date:'',register_money:'',num_people:''};

	store.dispatch(types.SET_ONEVENDOR_DETAIL,cleardata)
}

export const reqOneVendorDetail = (store,Id) => {
	let requestBody={ id:Id};

//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'sysresource/vendor/detail.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_ONEVENDOR_DETAIL,responseJson.data)
					},
					failure:function(){} 
	});
}

export const stopOneVendor = (store,component,vendorId) => {
	let requestBody={ id:vendorId};

//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'sysresource/vendor/disable.do',
				    body:requestBody,
		           	success:function(){
						store.dispatch(types.STOP_ONEVENDOR_SUCCESS,vendorId)

						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','停止外协单位成功！');
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','停止外协单位失败！');
					} 
	});
}

export const resumeOneVendor = (store,component,vendorId) => {
	let requestBody={ id:vendorId};

//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'sysresource/vendor/recovery.do',
				    body:requestBody,
		           	success:function(){
						store.dispatch(types.RESUME_ONEVENDOR_SUCCESS,vendorId)

						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','恢复外协单位成功！');
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','恢复外协单位失败！');
					} 
	});
}


export const saveOneVendor = (store,component,requestBody) => {
	// let requestBody={ id:roomId,name:roomName,description:roomDescription,area:roomArea,complete_date:roomDate};

//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'sysresource/vendor/save.do',
				    body:requestBody,
		           	success:function(){
						component.$router.go({name:'vendor'});
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','保存失败！');
					} 
	});
}

export const reqVendorDropdownList = (store) => {
	let requestBody={ };
	ajaxReq(store,{
					url:'sysresource/vendor/get-vendor-list.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_VENDORS_DROPDOWN_LIST,responseJson.data)
					},
					failure:function(){} 
	});
}

