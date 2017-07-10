import {SET_BIZ_ORG_LIST} from '../../mutation-types'
const state = {
	biz_org_list:{
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
}

const mutations = {
	[SET_BIZ_ORG_LIST] (state,list) {
		state.biz_org_list = list
	}
}

export default {
	state,
	mutations
}
