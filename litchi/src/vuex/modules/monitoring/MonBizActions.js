import Vue from 'vue'

import * as types from '../../mutation-types'

import {ajaxReq} from '../common/ajaxReq'

export const reqMonBiz = (store,comp,pageNo,pageSize,success,failure) => {
	let requestBody={ pageno:pageNo,pagesize:pageSize};
	ajaxReq(store,{
					url:'monitoring/biz.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_MONBIZ,responseJson.data)
						if(success)success(comp);
					},
					failure:function(){
						if(failure)failure(comp);
					} 
	});
}

export const createMonBiz = (store,component,bizId,success,failure) => {
	let requestBody={ id:bizId};
	ajaxReq(store,{
					url:'monitoring/create_biz.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						//TODO by hq
						store.dispatch(types.CREATE_MONBIZ_SUCCESS,bizId)

						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','创建业务系统监控成功！');

						if(success)success(comp);
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','创建业务系统监控失败！');

						if(failure)failure(comp);
					} 
	});
}

export const removeMonBiz = (store,component,bizId,success,failure) => {
	let requestBody={ id:bizId};
	ajaxReq(store,{
					url:'monitoring/delete_biz.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						//TODO by hq
						store.dispatch(types.DELETE_MONBIZ_SUCCESS,bizId)

						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','取消业务系统监控成功！');
						if(success)success(comp);
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','取消业务系统监控失败！');
						if(failure)failure(comp);
					} 
	});
}

