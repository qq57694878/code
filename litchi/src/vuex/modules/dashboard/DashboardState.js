
import { SET_ZIBEVENTS,SET_HEALTH,SET_COUNTING} from '../../mutation-types'

const state = {
	health:{
		biz:{
			normalNumber:0, 
			warningNumber:0,
		},
		host:{
			normalNumber:0,
			warningNumber:0,
		},
		db:{
			normalNumber:0,
			warningNumber:0,
		}
	},
	events:[
		// {
		// 	source_type:'biz', 
		// 	source_id:'', 
		// 	source_name:'host ', 
		// 	message:'报警消息 event 关联的triger 信息', 
		// 	lastClock:'2016-10-10 10:00:00', 
		// 	status:0 
		// },
		// {
		// 	source_type:'db', 
		// 	source_id:'', 
		// 	source_name:'host ', 
		// 	message:'报警消息 event 关联的triger 信息', 
		// 	lastClock:'2016-10-10 10:00:00', 
		// 	status:1 
		// }
	],
	counting:{
			x86:0, 
			vm:0,   
			db:0,  
			netdev:0,
			biz:0,
			wf:0, 
			wf_detail:{
				'sw_requirement_change':0, 
				'sw_data_change':0,
				'sw_data_query':0,
				'sw_program_deploy':0 
			}

	}
}

const mutations = {
	[SET_ZIBEVENTS] (state,events) {
		state.events=events
	},
	[SET_HEALTH] (state,health) {
		state.health=health
	},
	[SET_COUNTING] (state,counting) {
		state.counting=counting
	}
}

export default {
	state,
	mutations
}
