
import {SET_TASK_WAITING,SET_TASK_HISTORY,SET_TASK_HISTORY_QUERY,SET_ONETASK_DATA_QUERY,SET_ONETASK_DATA_CHANGE,SET_ONETASK_REQUIREMENT_CHANGE,SET_ONETASK_PROGRAM_DEPLOY,ADD_ATTACHMENT,DEL_ATTACHMENT} from '../../mutation-types'

const state = {
	waiting:{
    pagetotal: 0,
		tasks:[
		// {
		// 	task_id:'task id', // activiti中ru_task表主键 或 巡检任务表主键，将来可能还要增加其它种类
		// 	task_type:'requirement_change', //见码表 
		// 	task_info:{   //根据task_type 类型task_info 不同
		// 		instance_id:'27513',
		// 		node_id:'step1',
		// 		node_name:'流程定义中节点显示名字',
		// 		title:'标题', 
		// 		stime:'开始时间',
		// 		etime:'截止时间',
		// 		flag:'normal' //normal 正常流转
		// 			//reject 驳回
		// 			//stop   终止
              // }

		// },
		// {
		// 	task_id:'xxx id', // activiti中ru_task表主键 或 巡检任务表主键，将来可能还要增加其它种类
		// 	task_type:'requirement_change', //见码表 
		// 	task_info:{   //根据task_type 类型task_info 不同
		// 		instance_id:'27513',
		// 		node_id:'step2',
		// 		node_name:'流程定义中节点显示名字',
		// 		title:'标题2', 
		// 		stime:'开始时间',
		// 		etime:'截止时间',
		// 		flag:'normal' //normal 正常流转
		// 			//reject 驳回
		// 			//stop   终止
              // }

		// }

		]
	},
	historyquery:{
		biz_app:'',
	    tasktype:'',
		start:'',
		end:''
	},
	historytask:{
		pagetotal: 0,
		tasks:[
			]
	},
	onetask_requirement_change:{
		task_id:'activiti中user task 表主键',
		task_type:'requirement_change', //见码表     
		instance_id:'27513',
		node_id:'activiti中xml定义的user task节点id',
		node_name:'流程定义中节点显示名字',
		flag:'normal', 
		form_data:{  //根据不同task type返回不同表单的数据
			title:'标题', 
			business_app:'1',
			change_type:'2', //表单radio 对应的value
			description:'详细描述',
			stime:'',
			etime:'2016-09-02',
			cocompany:{
				company_id:'',
				company_name:'',
				leader_id:'',
				leader_name:''
			},
			actually_stime:'',
			actually_etime:'',
			actually_work:'',
			attachments:[   //附件列表
			{
				file_id:'',
				file_name:'',
				file_type:'', //文件类型 pic | pdf | word | exel
				url:'' //下载url
			}
			]
				//表单数据不全，以后增加
		},
		actions:{  //当前节点可以操作的功能
			submit:true,
			reject:true,
			stop:false
		},
		history:{
			flowchart_url:'流程图url',
			hispath:[ //历史路径
				//缺路径数据
				]
		}
	},
	onetask_data_change:{
		task_id:'',
		task_type:'data_change', //见码表     
		instance_id:'',
		node_id:'',
		node_name:'',
		flag:'normal', 
		form_data:{  
			title:'标题', 
			business_app:'',
			description:'详细描述',
			stime:'',
			etime:'2016-09-02',
			cocompany:{
				company_id:'',
				company_name:'',
				leader_id:'',
				leader_name:''
			},
			actually_stime:'',
			actually_etime:'',
			actually_sql:'',
			backup_sql:'',
			scheme:'',
			attachments:[  
			{
				file_id:'',
				file_name:'',
				file_type:'', //文件类型 pic | pdf | word | exel
				url:'' 
			}
			]
		},
		actions:{  //当前节点可以操作的功能
			submit:true,
			reject:true,
			stop:false
		},
		history:{
			flowchart_url:'流程图url',
			hispath:[ //历史路径
				//缺路径数据
				]
		}
	},
	onetask_data_query:{
		task_id:'',
		task_type:'data_query', //见码表     
		instance_id:'',
		node_id:'',
		node_name:'',
		flag:'normal', 
		form_data:{  
			title:'标题', 
			business_app:'',
			description:'详细描述',
			stime:'',
			etime:'2016-09-02',
			cocompany:{
				company_id:'',
				company_name:'',
				leader_id:'',
				leader_name:''
			},
			actually_stime:'',
			actually_etime:'',
			actually_sql:'',
			attachments:[  
			{
				file_id:'',
				file_name:'',
				file_type:'', //文件类型 pic | pdf | word | exel
				url:'' 
			}
			]
		},
		actions:{  //当前节点可以操作的功能
			submit:true,
			reject:true,
			stop:false
		},
		history:{
			flowchart_url:'流程图url',
			hispath:[ //历史路径
				//缺路径数据
				]
		}
	},
	onetask_program_deploy:{
		task_id:'',
		task_type:'data_change', //见码表     
		instance_id:'',
		node_id:'',
		node_name:'',
		flag:'normal', 
		form_data:{  
			title:'标题', 
			business_app:'',
			business_name:'',
			req_change_id:'',
			req_change_title:'',
			description:'',
			plan_deploy_time:'',
			company_id:'',
			company_name:'',
			attachments:[  
			{
				file_id:'',
				file_name:'',
				file_type:'', //文件类型 pic | pdf | word | exel
				url:'' 
			}
			]
		},
		actions:{  //当前节点可以操作的功能
			submit:true,
			reject:true,
			stop:false
		},
		history:{
			flowchart_url:'流程图url',
			hispath:[ //历史路径
				//缺路径数据
				]
		}

	}

}

const mutations = {
	[SET_TASK_WAITING] (state,waiting) {
		state.waiting = waiting
	},
	[SET_TASK_HISTORY] (state,history) {
		state.historytask = history
	},
	[SET_TASK_HISTORY_QUERY] (state,data) {
		state.historyquery = data
	},
	[SET_ONETASK_REQUIREMENT_CHANGE] (state,one) {
		state.onetask_requirement_change= one
	},
	[SET_ONETASK_DATA_CHANGE] (state,one) {
		state.onetask_data_change= one
	},
	[SET_ONETASK_DATA_QUERY] (state,one) {
		state.onetask_data_query= one
	},
	[SET_ONETASK_PROGRAM_DEPLOY] (state,one) {
		state.onetask_program_deploy= one
	},



	[ADD_ATTACHMENT] (state,attachment) {
		state.onetask.form_data.attachments.push(attachment);
	},
	[DEL_ATTACHMENT] (state,fileId) {
		if (!Array.prototype.findIndex) {
			Array.prototype.findIndex = function (fn, thisArg) {
				if (!(typeof fn =="function")) {
					throw new TypeError("fn不是一个有效的函数");
				}

				var arr = this;
				for (var i = 0, length = arr.length; i < length; i++) {
					if (fn.call(thisArg, arr[i], i, arr)) {
						return i;
					}
				}
				return -1;
			}
		}
		let index=state.onetask.form_data.attachments.findIndex(function(value,index,arr){
			return value.file_id==fileId;
		});
		if(index !=-1){
			console.debug(index);
			state.onetask.form_data.attachments.splice(index,1);
		}
	}

}

export default {
	state,
	mutations
}
