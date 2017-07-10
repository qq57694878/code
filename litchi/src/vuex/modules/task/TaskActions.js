import Vue from 'vue'

import * as types from '../../mutation-types'

import {ajaxReq} from '../common/ajaxReq'

export const reqWaitingTasks = (store,pageNo,pageSize) => {
	let requestBody={ pageno:pageNo,pagesize:pageSize};

	ajaxReq(store,{
					url:'mywork/task/waiting.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_TASK_WAITING,responseJson.data)
					},
					failure:function(){} 
	});
}

export const reqHistoryTasks = (store,comp,requestBody) => {
	// let requestBody={ pageno:pageNo,pagesize:pageSize};

	ajaxReq(store,{
					url:'mywork/task/history.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						// console.debug('AAAAAAAAA=====reqHistoryTasks===== AAAAAAAAAAAAAA'+responseJson);
						console.debug(responseJson);
						store.dispatch(types.SET_TASK_HISTORY,responseJson.data)
					},
					failure:function(){ 
						comp.$set('alertType','alert-danger');
						comp.$set('alertShow',true);
						comp.$set('alertContent','查询失败！');
					} 
	});
}

export const clearHistoryQuery = (store) => {
	let cleardata={ biz_app:'',tasktype:'',start:'',end:''};

	store.dispatch(types.SET_TASK_HISTORY_QUERY,cleardata)
}

export const getRequirementChange= (store,taskId,taskType,instanceId,success,failure) => {
	let requestBody={ task_id:taskId,task_type:taskType,instance_id:instanceId};
	ajaxReq(store,{
					url:'task/'+taskType+'/get.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_ONETASK_REQUIREMENT_CHANGE,responseJson.data)
						if(success)success(responseJson);
					},
					failure:function(){
						if(failure)failure();
					} 
	});
}

export const submitRequirementChange = (store,component,formData) => {
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	ajaxReq(store,{
					url:'task/sw_requirement_change/submit.do',
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作成功!',goback:'task_waiting' }});
					},
					failure:function(){
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作失败!',goback:'task_waiting' }});
					} 
	});
}

export const rejectRequirementChange = (store,component,formData) => {
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	ajaxReq(store,{
					url:'task/sw_requirement_change/return.do',
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作成功!',goback:'task_waiting' }});
					},
					failure:function(){
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作失败!',goback:'task_waiting' }});
					} 
	});
}

export const stopRequirementChange = (store,component,formData) => {
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	ajaxReq(store,{
					url:'task/sw_requirement_change/stop.do',
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作成功!',goback:'task_waiting' }});
					},
					failure:function(){
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作失败!',goback:'task_waiting' }});
					} 
	});
}


export const startRequirementChange = (store,component,formData) => {
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	ajaxReq(store,{
					url:'task/sw_requirement_change/start.do',
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作成功!',goback:'start_requirement_change' }});
					},
					failure:function(){
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作失败!',goback:'start_requirement_change' }});
					} 
	});
}

export const successUploadAttachment =(store,attachments) =>{
	for(let attachment of attachments){
		console.debug(attachment);
		store.dispatch(types.ADD_ATTACHMENT,attachment)
	}
}

export const removeAttachment =(store,component,fileId) =>{

	component.$set('alertShow',false);
	let requestBody={ file_id:fileId};
	ajaxReq(store,{
					url:'file/delete.do',
				    body: requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.DEL_ATTACHMENT,fileId)

						component.$set('alertType','alert-success');
						component.$set('alertShow',true);
						component.$set('alertContent','删除文件成功！');
					},
					failure:function(){
						
						console.debug('failure');
						component.$set('alertType','alert-danger');
						component.$set('alertShow',true);
						component.$set('alertContent','删除文件失败！');
					} 
	});
}

////////////////////////////////////////////////////////////////////////////////////////////////
//
export const getDataChange= (store,taskId,taskType,instanceId,success,failure) => {
	let requestBody={ task_id:taskId,task_type:taskType,instance_id:instanceId};
	ajaxReq(store,{
					url:'task/'+taskType+'/get.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_ONETASK_DATA_CHANGE,responseJson.data)
						if(success)success(responseJson);
					},
					failure:function(){
						if(failure)failure();
					} 
	});
}

export const submitDataChange = (store,component,formData) => {
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	ajaxReq(store,{
					url:'task/sw_data_change/submit.do',
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作成功!',goback:'task_waiting' }});
					},
					failure:function(){
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作失败!',goback:'task_waiting' }});
					} 
	});
}

export const rejectDataChange = (store,component,formData) => {
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	ajaxReq(store,{
					url:'task/sw_data_change/return.do',
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作成功!',goback:'task_waiting' }});
					},
					failure:function(){
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作失败!',goback:'task_waiting' }});
					} 
	});
}

export const stopDataChange = (store,component,formData) => {
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	ajaxReq(store,{
					url:'task/sw_data_change/stop.do',
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作成功!',goback:'task_waiting' }});
					},
					failure:function(){
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作失败!',goback:'task_waiting' }});
					} 
	});
}


export const startDataChange = (store,component,formData) => {
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	ajaxReq(store,{
					url:'task/sw_data_change/start.do',
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作成功!',goback:'start_data_change' }});
					},
					failure:function(){
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作失败!',goback:'start_data_change' }});
					} 
	});
}

////////////////////////////////////////////////////////////////////////////////////////////////
//data_query
export const getDataQuery= (store,taskId,taskType,instanceId,success,failure) => {
	let requestBody={ task_id:taskId,task_type:taskType,instance_id:instanceId};
	ajaxReq(store,{
					url:'task/'+taskType+'/get.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
						store.dispatch(types.SET_ONETASK_REQUIREMENT_CHANGE,responseJson.data)
						if(success)success(responseJson);
					},
					failure:function(){
						if(failure)failure();
					} 
	});
}

export const submitDataQuery = (store,component,formData) => {
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	ajaxReq(store,{
					url:'task/sw_data_change/submit.do',
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作成功!',goback:'task_waiting' }});
					},
					failure:function(){
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作失败!',goback:'task_waiting' }});
					} 
	});
}

export const rejectDataQuery = (store,component,formData) => {
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	ajaxReq(store,{
					url:'task/sw_data_change/return.do',
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作成功!',goback:'task_waiting' }});
					},
					failure:function(){
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作失败!',goback:'task_waiting' }});
					} 
	});
}

export const stopDataQuery = (store,component,formData) => {
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	ajaxReq(store,{
					url:'task/sw_data_change/stop.do',
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作成功!',goback:'task_waiting' }});
					},
					failure:function(){
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作失败!',goback:'task_waiting' }});
					} 
	});
}


export const startDataQuery = (store,component,formData) => {
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	ajaxReq(store,{
					url:'task/sw_data_change/start.do',
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作成功!',goback:'start_data_change' }});
					},
					failure:function(){
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作失败!',goback:'start_data_change' }});
					} 
	});
}

////////////////////////////////////////////////////////////////////////////////////////////////
//ProgramDeploy
export const getProgramDeploy= (store,taskId,taskType,instanceId,success,failure) => {
	let requestBody={ task_id:taskId,task_type:taskType,instance_id:instanceId};
			 console.debug(requestBody);
	ajaxReq(store,{
					url:'task/'+taskType+'/get.do',
				    body:requestBody,
		           	success:function(response){  
						let responseJson=response.json();
			 // console.debug("===============getProgramDeploy==============");
			 console.debug(responseJson);
						store.dispatch(types.SET_ONETASK_PROGRAM_DEPLOY,responseJson.data)
						if(success)success(responseJson);
					},
					failure:function(){
						if(failure)failure();
					} 
	});
}

export const submitProgramDeploy = (store,component,formData) => {
			 // console.debug(formData);
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	ajaxReq(store,{
					url:'task/sw_program_deploy/submit.do',
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作成功!',goback:'task_waiting' }});
					},
					failure:function(){
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作失败!',goback:'task_waiting' }});
					} 
	});
}

export const rejectProgramDeploy = (store,component,formData) => {
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	ajaxReq(store,{
					url:'task/sw_program_deploy/return.do',
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作成功!',goback:'task_waiting' }});
					},
					failure:function(){
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作失败!',goback:'task_waiting' }});
					} 
	});
}

export const stopProgramDeploy = (store,component,formData) => {
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	ajaxReq(store,{
					url:'task/sw_program_deploy/stop.do',
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作成功!',goback:'task_waiting' }});
					},
					failure:function(){
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作失败!',goback:'task_waiting' }});
					} 
	});
}


export const startProgramDeploy = (store,component,formData) => {
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	// console.debug(formData);
	ajaxReq(store,{
					url:'task/sw_program_deploy/start.do',
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作成功!',goback:'start_program_deploy' }});
					},
					failure:function(){
						bootbox.hideAll();
						component.$router.go({name:'info',params:{ info:'操作失败!',goback:'start_program_deploy' }});
					} 
	});
}


export const reqVendorLeader = (store,component,success,failure) => {
	ajaxReq(store,{
					url:'sysresource/vendor/get-render-manager.do',
		           	success:function(response){  
						let responseJson=response.json();
						if(success)success(component,responseJson.data);
					},
					failure:function(){
						if(failure)failure();
					} 
	});
}
