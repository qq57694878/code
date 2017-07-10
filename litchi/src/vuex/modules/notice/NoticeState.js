import {SET_NOTICE_UNREAD,SET_NOTICE_RECEIVE,SET_NOTICE_SEND,READ_NOTICE_SUCCESS,DELETE_NOTICE_SUCCESS} from '../../mutation-types'

const state = {
	receivelist:{
		pagetotal:0,
		// list_data:[
		// 	{ 
		// 		id:'1',
		// 		notice_type:'1',
		// 		notice_info:'receive hello 个人通知',
		// 		ctime:'2016-11-01',
		// 		cuser_name:'hq',
		// 		ruser_name:'lishushuai',
		// 		has_read:'1',
		// 		read_time:'2016-11-02'
		// 	},
		// 	{ 
		// 		id:'2',
		// 		notice_type:'11',
		// 		notice_info:'receive 需求变更通知需求变更通知需求变更通知需求变更通知需求变更通知需求变更通知需求变更通知',
		// 		ctime:'2016-11-02',
		// 		cuser_name:'hq',
		// 		ruser_name:'lishushuai',
		// 		has_read:'0',
		// 		read_time:''
		// 	}
		// ]
	},
	sendlist:{
		pagetotal:0,
		// list_data:[
		// 	{ 
		// 		id:'1',
		// 		notice_type:'1',
		// 		notice_info:'send hello 个人通知',
		// 		ctime:'2016-11-01',
		// 		cuser_name:'hq',
		// 		ruser_name:'lishushuai',
		// 		has_read:'1',
		// 		read_time:'2016-11-02'
		// 	},
		// 	{ 
		// 		id:'2',
		// 		notice_type:'11',
		// 		notice_info:'send 需求变更通知需求变更通知需求变更通知需求变更通知需求变更通知需求变更通知需求变更通知',
		// 		ctime:'2016-11-02',
		// 		cuser_name:'hq',
		// 		ruser_name:'lishushuai',
		// 		has_read:'0',
		// 		read_time:''
		// 	}
		// ]
	},
	unreadlist:{
		// number:'1',
		// list_data:[
		// 	{
		// 		id:'1',
		// 		notice_type:'1',
		// 		notice_info:'hello 个人通知hello 个人通知hello 个人通知hello 个人通知hello 个人通知hello 个人通知hello 个人通知hello 个人通知',
		// 		ctime:'2016-11-01',
		// 		cuser_name:'hq'
		// 	},			
		// 	{
		// 		id:'2',
		// 		notice_type:'11',
		// 		notice_info:'需求变更通知',
		// 		ctime:'2016-11-01',
		// 		cuser_name:'hq'
		// 	},		
		// 	{
		// 		id:'3',
		// 		notice_type:'12',
		// 		notice_info:'数据修改通知',
		// 		ctime:'2016-11-01',
		// 		cuser_name:'hq'
		// 	},　		
		// 	{
		// 		id:'4',
		// 		notice_type:'13',
		// 		notice_info:'数据查询通知',
		// 		ctime:'2016-11-01',
		// 		cuser_name:'hq'
		// 	},		
		// 	{
		// 		id:'5',
		// 		notice_type:'14',
		// 		notice_info:'程序发布通知',
		// 		ctime:'2016-11-01',
		// 		cuser_name:'hq'
		// 	},		
		// 	{
		// 		id:'6',
		// 		notice_type:'99',
		// 		notice_info:'其他通知',
		// 		ctime:'2016-11-01',
		// 		cuser_name:'hq'
		// 	}		
		// ]
	}
}

const mutations = {
	[SET_NOTICE_UNREAD] (state,list) {
		// console.debug('SET_NOTICE_UNREAD=============='+list.number);
		state.unreadlist = list;
	},
	[SET_NOTICE_RECEIVE] (state,list) {
		state.receivelist = list
	},
	[SET_NOTICE_SEND] (state,list) {
		state.sendlist = list
	},
	[READ_NOTICE_SUCCESS] (state,noticeid) {
		for(let data of state.receivelist.list_data){
			if (noticeid== data.id) {
				data.has_read = '1';
			}
		}
	},
	[DELETE_NOTICE_SUCCESS] (state,noticeid) {
		var arr = state.sendlist.list_data;
		var index = -1; 
		for(var i=0,length = arr.length;i < length; i++){
			if(arr[i].id==noticeid){
				index= i;
				break;
			}
		}
		if(index != -1){
			// console.debug("====DELETE_NOTICE_SUCCESS========"+index);
			arr.splice(index,1);
		}
	}
}

export default {
	state,
	mutations
}
