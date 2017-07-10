
import {  SET_MONDB,CREATE_MONDB_SUCCESS,DELETE_MONDB_SUCCESS } from '../../mutation-types'

const state = {
	data:{
	pagetotal:1, 
	normalNumber:0, 
	warningNumber:0,
	lastUpdate:'',
	list:[
		// {
		// 	db_id:'db id', 
		// 	db_name:'db名称', 
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
		// 			message:'报警信息'
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
	[ SET_MONDB] (state,mon) {
		state.data=mon;
	},
	[CREATE_MONDB_SUCCESS] (state,id) {
		// console.debug('STOP_ONEBIZAPP_SUCCESS');
		for(let dbdata of state.list){
			if (id == dbdata.db_id) {
				dbdata.zabbix_on = 1;
			}
		}
	},
	[DELETE_MONDB_SUCCESS] (state,id) {
		// console.debug('STOP_ONEBIZAPP_SUCCESS');
		for(let dbdata of state.list){
			if (id == dbdata.db_id) {
				dbdata.zabbix_on = 0;
			}
		}
	}
}

export default {
	state,
	mutations
}
