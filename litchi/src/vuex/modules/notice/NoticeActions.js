import Vue from 'vue'

import * as types from '../../mutation-types'

import {ajaxReq} from '../common/ajaxReq'

export const reqNoticeUnreadList = (store) => {
	let requestBody={};

	// console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'tools/notice/get_unread_notice_list.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						// console.debug(responseJson);
						store.dispatch(types.SET_NOTICE_UNREAD,responseJson.data)
					},
					failure:function(){} 
	});
}

export const reqReceiveNoticeList = (store,pageNo,pageSize) => {
	let requestBody={ pageno:pageNo,pagesize:pageSize};

	// console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'tools/notice/query_receive_notice.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_NOTICE_RECEIVE,responseJson.data)
					},
					failure:function(){} 
	});
}

export const reqSendNoticeList = (store,pageNo,pageSize) => {
	let requestBody={ pageno:pageNo,pagesize:pageSize};

	// console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'tools/notice/query_send_notice.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_NOTICE_SEND,responseJson.data)
					},
					failure:function(){} 
	});
}

export const readNotice = (store,component,noticeid) => {
	let requestBody={ id:noticeid};

	// console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'tools/notice/read_notice.do',
				    body:requestBody,
		           	success:function(response){  
						store.dispatch(types.READ_NOTICE_SUCCESS,noticeid)
						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','通知确认成功！');
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','通知确认失败！');
					} 
	});
}
export const deleteNotice = (store,component,noticeid) => {
	let requestBody={ id:noticeid};

	// console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'tools/notice/delete.do',
				    body:requestBody,
		           	success:function(response){  
						store.dispatch(types.DELETE_NOTICE_SUCCESS,noticeid)
						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','通知删除成功！');
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','通知删除失败！');
					} 
	});
}
