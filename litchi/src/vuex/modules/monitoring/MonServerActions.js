import Vue from 'vue'

import * as types from '../../mutation-types'

import {ajaxReq} from '../common/ajaxReq'

export const reqMonServer = (store,comp,pageNo,pageSize,success,failure) => {
	let requestBody={ pageno:pageNo,pagesize:pageSize};
	ajaxReq(store,{
					url:'monitoring/hosts.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_MONSERVER,responseJson.data)
						if(success)success(comp);
					},
					failure:function(){
						if(failure)failure(comp);
					} 
	});
}

export const createMonServer = (store,component,serverId,success,failure) => {
	let requestBody={ id:serverId};
	ajaxReq(store,{
					url:'monitoring/create_host.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						//TODO by hq
						store.dispatch(types.CREATE_MONSERVER_SUCCESS,serverId)

						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','创建服务器监控成功！');
						if(success)success(comp);
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','创建服务器监控失败！');
						if(failure)failure(comp);
					} 
	});
}

export const removeMonServer = (store,component,serverId,success,failure) => {
	let requestBody={ id:serverId};
	ajaxReq(store,{
					url:'monitoring/delete_host.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						//TODO by hq
						store.dispatch(types.DELETE_MONSERVER_SUCCESS,serverId)

						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','取消服务器监控成功！');
						if(success)success(comp);
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','取消服务器监控失败！');
						if(failure)failure(comp);
					} 
	});
}

