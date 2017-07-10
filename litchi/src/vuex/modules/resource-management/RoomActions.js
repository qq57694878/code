import Vue from 'vue'

import * as types from '../../mutation-types'

import {ajaxReq} from '../common/ajaxReq'

export const reqRoomList = (store,pageNo,pageSize) => {
	let requestBody={ pageno:pageNo,pagesize:pageSize};

	ajaxReq(store,{
					url:'sysresource/room/init.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_ROOMS,responseJson.data)
					},
					failure:function(){} 
	});
}

export const clearOneRoomDetail = (store) => {
	let cleardata={id:'',name:'',description:'',area:'',complete_date:''};

	store.dispatch(types.SET_ONEROOM_DETAIL,cleardata)
}

export const reqOneRoomDetail = (store,Id) => {
	let requestBody={ id:Id};

//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'sysresource/room/detail.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_ONEROOM_DETAIL,responseJson.data)
					},
					failure:function(){} 
	});
}

export const stopOneRoom = (store,component,roomId) => {
	let requestBody={ id:roomId};

//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'sysresource/room/disable.do',
				    body:requestBody,
		           	success:function(){
						store.dispatch(types.STOP_ONEROOM_SUCCESS,roomId)

						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','停止外协单位成功！');
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','停止外协单位失败！');
					} 
	});
}

export const resumeOneRoom = (store,component,roomId) => {
	let requestBody={ id:roomId};

//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'sysresource/room/recovery.do',
				    body:requestBody,
		           	success:function(){
						store.dispatch(types.RESUME_ONEROOM_SUCCESS,roomId)

						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','恢复外协单位成功！');
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','恢复外协单位失败！');
					} 
	});
}

// export const saveOneRoom = (store,component,roomId,roomName,roomDescription,roomArea,roomDate) => {
export const saveOneRoom = (store,component,requestBody) => {
	// let requestBody={ id:roomId,name:roomName,description:roomDescription,area:roomArea,complete_date:roomDate};

//	console.debug('reqBusinessAppList');
	ajaxReq(store,{
					url:'sysresource/room/save.do',
				    body:requestBody,
		           	success:function(){
						component.$router.go({name:'room'});
					},
					failure:function(){
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','保存失败！');
					} 
	});
}
