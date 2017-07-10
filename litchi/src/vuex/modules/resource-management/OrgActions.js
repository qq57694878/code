import Vue from 'vue'

import * as types from '../../mutation-types'

import {ajaxReq} from '../common/ajaxReq'

export const reqBizOrgList = (store,comp,success,failure) => {
	let requestBody={ };
	ajaxReq(store,{
					url:'sysresource/org/get_biz_org_list.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_BIZ_ORG_LIST,responseJson.data)
	 					if(success)success(comp);
					},
					failure:function(){
	 					if(failure)failure(comp);
					} 
	});
}
