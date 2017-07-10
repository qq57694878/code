import Vue from 'vue'

import * as types from '../../mutation-types'

import {ajaxReq} from '../common/ajaxReq'

export const reqIpnet = (store,comp,success,failure) => {
	ajaxReq(store,{
					url:'ip/ip_group/query.do',
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_IPNETARRAY,responseJson.data.list_data)
						if(success)success(comp);
					},
					failure:function(){
						if(failure)failure(comp);
					} 
	});
}

export const reqIpArray = (store,comp,netId,success,failure) => {
	let requestBody={ id:netId};
	ajaxReq(store,{
					url:'ip/ip_group/get_ip_monitoring_detail.do',
					body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_IPARRAY,responseJson.data.ip_hosts)
						if(success)success(comp);
					},
					failure:function(){
						if(failure)failure(comp);
					} 
	});
}

export const selIpNet = (store,comp,netId) => {
	store.dispatch(types.SET_IP_NETSELECTED,netId)
}

export const reqIpnetSave = (store,comp,net,success,failure) => {
	ajaxReq(store,{
					url:'ip/ip_group/save.do',
					body:net,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_IPNET,responseJson.data)
						if(success)success(comp,responseJson.data);
					},
					failure:function(){
						if(failure)failure(comp);
					} 
	});
}

export const reqIpnetDel = (store,comp,netId,success,failure) => {
	let requestBody={ id:netId};
	ajaxReq(store,{
					url:'ip/ip_group/delete.do',
					body:requestBody,
		           	success:function(response){  
						store.dispatch(types.DEL_IPNET,netId)
						if(success)success(comp);
					},
					failure:function(){
						if(failure)failure(comp);
					} 
	});
}

export const selEditNet = (store,comp,net) => {
	store.dispatch(types.SET_EDITNET,net)
}

export const setAlertObj = (store,comp,obj) => {
	store.dispatch(types.SET_ALERTOBJ,obj)
}


