import {SET_ROOMS} from '../../mutation-types'
import {STOP_ONEROOM_SUCCESS} from '../../mutation-types'
import {RESUME_ONEROOM_SUCCESS} from '../../mutation-types'
import {SET_ONEROOM_DETAIL} from '../../mutation-types'

const state = {
	roomslist:{
		pagetotal:0,
		list_data:[
			{ 
				id:'1',
				name:'app1',
				description:'app1',
				area:'50',
				complete_date:'2016-10-10',
				status:'0'
			},
			{ 
				id:'2',
				name:'app2',
				description:'app2',
				area:'100',
				complete_date:'2016-10-09',
				status:'1'
			}
		]
	},
	oneroom_detail:{
		id:'1',
		name:'app1',
		description:'',
		area:'100',
		complete_date:'2016-10-09'
	}
	// apps:{
	// 	"2":{ 
	// 		id:'1',
	// 		name:'app1',
	// 		description:'description'
	// 	},
	// 	"1":{ 
	// 		id:'2',
	// 		name:'app2',
	// 		description:'description'
	// 	}
	// }

}

const mutations = {
	[SET_ROOMS] (state,roomslist) {
		state.roomslist = roomslist
	},
	[STOP_ONEROOM_SUCCESS] (state,id) {
		// console.debug('STOP_ONEBIZAPP_SUCCESS');
		for(let roomdata of state.roomslist.list_data){
			if (id == roomdata.id) {
				roomdata.status = '0';
			}
		}
	},
	[RESUME_ONEROOM_SUCCESS] (state,id) {
		for(let roomdata of state.roomslist.list_data){
			if (id == roomdata.id) {
				roomdata.status = '1';
			}
		}
	},
	[SET_ONEROOM_DETAIL] (state,oneroom_detail) {
		state.oneroom_detail = oneroom_detail
	}
}

export default {
	state,
	mutations
}
