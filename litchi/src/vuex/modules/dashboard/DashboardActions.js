
import Vue from 'vue'

import * as types from '../../mutation-types'

import {ajaxReq} from '../common/ajaxReq'

export const reqZibEvents = (store,comp,success,failure) => {
	ajaxReq(store,{
					url:'monitoring/zibevent.do',
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_ZIBEVENTS,responseJson.data.events)
						if(success)success(comp);
					},
					failure:function(){
						if(failure)failure(comp);
					} 
	});
}

export const reqZibHealth = (store,comp,success,failure) => {
	ajaxReq(store,{
					url:'monitoring/healthindex.do',
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_HEALTH,responseJson.data)
						if(success)success(comp);
					},
					failure:function(){
						if(failure)failure(comp);
					} 
	});
}

export const reqZibCounting = (store,comp,success,failure) => {
	ajaxReq(store,{
					url:'monitoring/counting.do',
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_COUNTING,responseJson.data)
						if(success)success(comp);
					},
					failure:function(){
						if(failure)failure(comp);
					} 
	});
}
