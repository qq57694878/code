
import {  SET_MONBIZ,CREATE_MONBIZ_SUCCESS,DELETE_MONBIZ_SUCCESS } from '../../mutation-types'

const state = {
	data:{
	pagetotal:1, 
	normalNumber:0, 
	warningNumber:0,
	lastUpdate:'',
	list:[
		// {
		// 	biz_id:'biz id', 
		// 	biz_name:'业务系统名称', 
		// 	hosts:[
		// 		{   
		// 			ip:'192.168.1.1',
		// 			services:[
		// 				{
		// 					port:'8080',
		// 					description:'这个端口提供web服务',
		// 					status:0, 
		// 					lastClock:'2016-10-10 10:00:00',
		// 					message:'报警信息aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'
		// 				},
		// 				{
		// 					port:'8080',
		// 					description:'这个端口提供web服务',
		// 					status:0, 
		// 					lastClock:'2016-10-10 10:00:00',
		// 					message:'报警信息aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'
		// 				}
		// 			]
		// 		}
		// 	],
		// 	zabbix_on:1
		// },{
		// 	biz_id:'biz id', 
		// 	biz_name:'业务系统名称', 
		// 	hosts:[
		// 		{   
		// 			ip:'192.168.1.1',
		// 			services:[
		// 				{
		// 					port:'8080',
		// 					description:'这个端口提供web服务',
		// 					status:0, 
		// 					lastClock:'2016-10-10 10:00:00',
		// 					message:'报警信息aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'
		// 				},
		// 				{
		// 					port:'8080',
		// 					description:'这个端口提供web服务',
		// 					status:0, 
		// 					lastClock:'2016-10-10 10:00:00',
		// 					message:'报警信息aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'
		// 				}
		// 			]
		// 		}
		// 	],
		// 	zabbix_on:1
		// }
	]
	}
}

const mutations = {
	[ SET_MONBIZ] (state,mon) {
		state.data=mon;
	},
	[CREATE_MONBIZ_SUCCESS] (state,id) {
		// console.debug('STOP_ONEBIZAPP_SUCCESS');
		for(let bizdata of state.list){
			if (id == bizdata.biz_id) {
				bizdata.status = 1;
			}
		}
	},
	[DELETE_MONBIZ_SUCCESS] (state,id) {
		// console.debug('STOP_ONEBIZAPP_SUCCESS');
		for(let bizdata of state.list){
			if (id == bizdata.biz_id) {
				bizdata.status = 0;
			}
		}
	}
}

export default {
	state,
	mutations
}
