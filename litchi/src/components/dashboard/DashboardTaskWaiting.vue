
<template>
<div class="widget-box transparent">
	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			待办任务	
		</h4>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
	<!--  ===================== separate line ===================== -->

	<div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer">
	
	<table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
	<thead>
		<tr>
			<th>任务类型</th>
			<th>标题</th>
			<th> <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i> 创建时间 </th>
			<th> <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i> 截止时间 </th>
			<th >状态</th>

			<th></th>
		</tr>
	</thead>
	<tbody>
		<!-- <tr v-for="task in waiting.tasks"> -->
		<tr v-for="task in waiting.tasks|taskfilter">

		<td> {{CT.taskType[task.task_type]}} </td>
		<td> {{task.task_info.title}} </td>
		<td> {{task.task_info.stime}} </td>
		<td> {{task.task_info.etime}} </td>
		<td>{{task.task_info.node_name}}&nbsp;&nbsp;<span class="label label-sm label-warning" v-if="task.task_info.flag!='normal'" > {{CT.taskState[task.task_info.flag] }} </span> </td>

		<td>
			<div class="hidden-sm hidden-xs btn-group">
				<button class="btn btn-success" v-on:click="process(task)"> <i class="ace-icon fa fa-pencil-square-o"></i> 办理 </button>
				<!-- <button class="btn btn-danger"  v-on:click="del(task)"> <i class="ace-icon fa fa-trash-o"></i> 删除</button> -->
			</div>

			<div class="hidden-md hidden-lg">
				<div class="inline position-relative">
					<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
					<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
					</button>
					
					<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
						
						<li>
						<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="办理" v-on:click="process(task)">
						<span class="green">
						<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
						</span>
						</a>
						</li>

						<!-- <li> -->
						<!-- <a href="#" class="tooltip-error" data-rel="tooltip" title="" data-original-title="删除" v-on:click="del(task)"> -->
						<!-- <span class="red"> -->
						<!-- <i class="ace-icon fa fa-trash-o bigger-120"></i> -->
						<!-- </span> -->
						<!-- </a> -->
						<!-- </li> -->
					</ul>
				</div>
			</div>
			</td>
		</tr>

	</tbody>
	</table>

	<!-- <div class="row"> -->
	<!-- 	<div class="col-xs-6"> -->
	<!-- 	</div> -->
	<!-- 	<div class="col-xs-6"> -->
	<!-- 		<div class="dataTables_paginate paging_simple_numbers"> -->
	<!-- 			<pages v-bind:total-size="waiting.pagetotal" v-bind:page-size="pageSize" v-on:page-set="pageSet" v-on:page-next="next" v-on:page-prev="prev" v-on:page-first="first" v-on:page-end="end"></pages> -->
	<!-- 		</div> -->
	<!-- 	</div> -->
	<!-- </div> -->

	</div>

	<div>
	<a v-link="'/mywork/task_waiting'" class="dropdown-footer">
		查看全部待办任务
		<i class="ace-icon fa fa-arrow-right"></i>
	</a>
	</div>
	<!--  ===================== separate line ===================== -->
	</div>
</div>


</template>

<script>

import Pages from '../common/Pages'

import {getTaskWaiting} from '../../vuex/modules/task/TaskGetters'
import {getCodeTable} from '../../vuex/modules/common/CodeTableGetters'

import {reqWaitingTasks} from '../../vuex/modules/task/TaskActions'

export default {
/*	data:function(){
		return {
			pageSize:10
		}
	},*/
	vuex:{
		getters:{
			waiting:getTaskWaiting,
			CT:getCodeTable
		},
		actions:{
			reqWaitingTasks
		}
	},
	filters:{
		taskfilter:function(tasks){
			var arr;	
			//if(tasks.length >10){
			//	arr = tasks.slice(0,10);
			if(tasks.length >2){
				arr = tasks.slice(0,2);
			}else{
				arr = tasks;
			}
			return arr;
		}
	},
	components: {
		Pages
	},
	methods:{
		next:function(pageNumber) {
			console.debug("this parent page-next event "+pageNumber);
			this.reqWaitingTasks(pageNumber,this.pageSize);
		},
		prev:function(pageNumber){
			console.debug("this parent page-prev event "+pageNumber);
			this.reqWaitingTasks(pageNumber,this.pageSize);
		},
		first:function(pageNumber){
			console.debug("this parent page-first event "+pageNumber);
			this.reqWaitingTasks(pageNumber,this.pageSize);
		},
		end:function(pageNumber){
			console.debug("this parent page-end event "+pageNumber);
			this.reqWaitingTasks(pageNumber,this.pageSize);
		},
		pageSet:function(pageNumber){
			console.debug("this parent page-set event "+pageNumber);
			this.reqWaitingTasks(pageNumber,this.pageSize);
		},

		process:function(task){
			console.debug(task);
			//TODO if task type
			this.$router.go({
								// path:'/mywork/mytask/myform',
								name:'myform',
								params:{
									taskId:task.task_id,
									taskType:task.task_type,
									nodeId:task.task_info.node_id,
									nodeName:task.task_info.node_name,
									instanceId:task.task_info.instance_id
								}
							});
		},
		del:function(task){
			console.debug(task);
		}
	},
	ready:function(){
		this.reqWaitingTasks(1,10);
	}

}
</script>

