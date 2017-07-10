
import {SET_MONSERVER,CREATE_MONSERVER_SUCCESS,DELETE_MONSERVER_SUCCESS } from '../../mutation-types'

const state = {
	data:{
	pagetotal:1, 
	normalNumber:0, 
	warningNumber:0,
	lastUpdate:'',
	list:[
		// {
		// 	host_id:'host id', 
		// 	host_name:'服务器名称', 
		// 	ip:'192.168.1.1',
		// 	items:{
		// 		'cpu':{
		// 			key:'cpu',
		// 			value:'80%'
		// 		},
		// 		'memory':{
		// 			key:'memory',
		// 			value:'80%'
		// 		},
		// 		'disk':{
		// 			key:'disk',
		// 			value:'80%'
		// 		},
		// 		'net':{
		// 			key:'net',
		// 			value:'80%'
		// 		}
		// 	},
		// 	triggers:[
		// 		{   
		// 			key:'triger1',
		// 			status:0, 
		// 			lastClock:'2016-10-10 10:00:00',
		// 			message:'报警信息aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd,w'
		// 		},
		// 		{   
		// 			key:'triger2',
		// 			status:0, 
		// 			lastClock:'2016-10-10 10:00:00',
		// 			message:'报警信息'
		// 		}
		// 	],
		// 	zabbix_on:1
		// },
// {
		// 	host_id:'host id', 
		// 	host_name:'服务器名称', 
		// 	ip:'192.168.1.1',
		// 	items:{
		// 		'cpu':{
		// 			key:'cpu',
		// 			value:'80%'
		// 		},
		// 		'memory':{
		// 			key:'memory',
		// 			value:'80%'
		// 		},
		// 		'disk':{
		// 			key:'disk',
		// 			value:'80%'
		// 		},
		// 		'net':{
		// 			key:'net',
		// 			value:'80%'
		// 		}
		// 	},
		// 	triggers:[
		// 		{   
		// 			key:'triger1',
		// 			status:0, 
		// 			lastClock:'2016-10-10 10:00:00',
		// 			message:'报警信息aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd,w'
		// 		},
		// 		{   
		// 			key:'triger2',
		// 			status:0, 
		// 			lastClock:'2016-10-10 10:00:00',
		// 			message:'报警信息'
		// 		}
		// 	],
		// 	zabbix_on:1
		// }

	]
	}
}

const mutations = {
	[ SET_MONSERVER] (state,mon) {
		state.data=mon;
	},
	[CREATE_MONSERVER_SUCCESS] (state,id) {
		// console.debug('STOP_ONEBIZAPP_SUCCESS');
		for(let serverdata of state.list){
			if (id == serverdata.host_id) {
				serverdata.zabbix_on = 1;
			}
		}
	},
	[DELETE_MONSERVER_SUCCESS] (state,id) {
		// console.debug('STOP_ONEBIZAPP_SUCCESS');
		for(let serverdata of state.list){
			if (id == serverdata.host_id) {
				serverdata.zabbix_on = 0;
			}
		}
	}
}

export default {
	state,
	mutations
}
