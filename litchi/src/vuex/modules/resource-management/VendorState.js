import {SET_VENDORS} from '../../mutation-types'
import {SET_VENDORS_DROPDOWN_LIST} from '../../mutation-types'
import {STOP_ONEVENDOR_SUCCESS} from '../../mutation-types'
import {RESUME_ONEVENDOR_SUCCESS} from '../../mutation-types'
import {SET_ONEVENDOR_DETAIL} from '../../mutation-types'

const state = {
	vendorslist:{
		pagetotal:0,
		list_data:[
			{ 
				id:'1',
				vendor_name:'app1',
				manager:'app1',
				tel:'app1',
				state:'0'
			},
			{ 
				id:'2',
				vendor_name:'app2',
				manager:'app2',
				tel:'app2',
				state:'1'
			}
		]
	},
	onevendor_detail:{
		id:'1',
		org_id:'',
		vendor_name:'app1',
		manager:'app1',
		tel:'app1',
		property:'app1',
		scope:'',
	    register_date:'2016-10-11',
	    register_money:'1000万',
		num_people:''
	},
	vendors_dropdown_list:{
		list_data:[
			{
				code:'1',
				value:'test1'
			},
			{
				code:'2',
				value:'test2'
			}
		]
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
	[SET_VENDORS] (state,vendorslist) {
		state.vendorslist = vendorslist
	},
	[STOP_ONEVENDOR_SUCCESS] (state,id) {
		for(let vendordata of state.vendorslist.list_data){
			if (id == vendordata.id) {
				vendordata.state = '0';
			}
		}
	},
	[RESUME_ONEVENDOR_SUCCESS] (state,id) {
		for(let vendordata of state.vendorslist.list_data){
			if (id == vendordata.id) {
				vendordata.state = '1';
			}
		}
	},
	[SET_VENDORS_DROPDOWN_LIST] (state,list) {
		state.vendors_dropdown_list = list
	},
	[SET_ONEVENDOR_DETAIL] (state,onevendor_detail) {
		state.onevendor_detail = onevendor_detail
	}
}

export default {
	state,
	mutations
}
