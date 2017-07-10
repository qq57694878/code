
import {SET_BUSINESSAPPS} from '../../mutation-types'
import {SET_ONEBIZAPP_DETAIL} from '../../mutation-types'
import {STOP_ONEBIZAPP_SUCCESS} from '../../mutation-types'
import {RESUME_ONEBIZAPP_SUCCESS} from '../../mutation-types'

const state = {
	appslist:{
		pagetotal:0,
		list_data:[
			{ 
				id:'1',
				name:'app1',
				org_name:'app1',
				vendor:'app1',
				online_time:'2016-09-08',
				status:'0'
			},
			{ 
				id:'2',
				name:'app2',
				org_name:'app2',
				vendor:'app2',
				online_time:'2016-09-07',
				status:'1'
			}
		]
	},
	// appslist:{
	// 	pagetotal:0,
	// 	id_list:['1','2'],
	// 	apps_data:{
	// 		"1":{ 
	// 				id:'1',
	// 				name:'app1',
	// 				org_name:'app1',
	// 				vendor:'app1',
	// 				online_time:'2016-09-08',
	// 				status:'0'
	// 			},
	// 		'2':{ 
	// 				id:'2',
	// 				name:'app2',
	// 				org_name:'app2',
	// 				vendor:'app2',
	// 				online_time:'2016-09-07',
	// 				status:'1'
	// 			}

	// 	}
	// },
	onebizapp_detail:{
		id:'1',
		name:'app1',
		org_id_list:[],
		vendor_id:'',
		online_time:'2016-10-11',
		description:''
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
	[SET_BUSINESSAPPS] (state,appslist) {
		state.appslist = appslist
		// state.appslist.pagetotal = appslist.pagetotal;
		// state.appslist.id_list=[];
		// state.appslist.apps_data={};
		// for(let app of appslist.list_data){
		// 	state.appslist.id_list.push(app.id); 
		// 	state.appslist.apps_data[app.id]=app; 
		// }
	},
	[STOP_ONEBIZAPP_SUCCESS] (state,bizapp_id) {
		// console.debug('STOP_ONEBIZAPP_SUCCESS');
		for(let bizappdata of state.appslist.list_data){
			if (bizapp_id == bizappdata.id) {
				bizappdata.status = '0';
			}
		}
	},
	[RESUME_ONEBIZAPP_SUCCESS] (state,bizapp_id) {
		for(let bizappdata of state.appslist.list_data){
			if (bizapp_id == bizappdata.id) {
				bizappdata.status = '1';
			}
		}
	},
	[SET_ONEBIZAPP_DETAIL] (state,onebizapp_detail) {
		state.onebizapp_detail = onebizapp_detail
	}
}

export default {
	state,
	mutations
}
