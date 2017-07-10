import Vue from 'vue'

import * as types from '../../mutation-types'

import {ajaxReq} from '../common/ajaxReq'

export const reqMonDB = (store,comp,pageNo,pageSize,success,failure) => {
	let requestBody={ pageno:pageNo,pagesize:pageSize};
	ajaxReq(store,{
					url:'monitoring/database.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_MONDB,responseJson.data)
						if(success)success(comp);
					},
					failure:function(){
						if(failure)failure(comp);
					} 
	});
}

export const createMonDB = (store,component,dbId,success,failure) => {
	let requestBody={ id:dbId};
	ajaxReq(store,{
					url:'monitoring/create_database.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						//TODO by hq
						store.dispatch(types.CREATE_MONDB_SUCCESS,dbId)

						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','创建数据库监控成功！');

						if(success)success(comp);
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','创建数据库监控失败！');
						if(failure)failure(comp);
					} 
	});
}

export const removeMonDB = (store,component,dbId,success,failure) => {
	let requestBody={ id:dbId};
	ajaxReq(store,{
					url:'monitoring/delete_database.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						//TODO by hq
						store.dispatch(types.DELETE_MONDB_SUCCESS,dbId)

						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','取消数据库监控成功！');
						if(success)success(comp);
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','取消数据库监控失败！');
						if(failure)failure(comp);
					} 
	});
}

