
<template>
<div class="page-header">
	<h1>
		已办任务	
	</h1>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="row" >
			<div class="col-xs-12">
				 <my-alert v-bind:alert-type="alertType" v-bind:content="alertContent" v-bind:show.sync="alertShow"></my-alert>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<label class="col-xs-1 control-label no-padding-right no-margin-bottom" style="padding-top:6px" > 业务系统:</label>
				<div class="col-xs-2">
					<combobox placeholder="业务系统" v-bind:items="businessApps" v-on:change="change"></combobox>
						<!-- <select class="form-control" id="form-field-select-1" > -->
						<!-- 	<option value=""disabled selected>业务系统</option> -->
						<!-- 	<option value="AL">Alabama</option> -->
						<!-- 	<option value="WY">Wyoming</option> -->
						<!-- </select> -->
				</div>
				<label class="col-xs-1 control-label no-padding-right" style="padding-top:6px" > 任务类型:</label>
				<div class="col-xs-2">
					<select class="form-control" id="form-field-select-1" placeholder="任务类型" v-model="taskquery.tasktype">
						<option  v-for="org in taskTypeList "  value="{{org.code}}">{{org.value}}</option>
						<!-- <option value=""disabled selected>任务类型</option> -->
						<!-- <option value="AL">Alabama</option> -->
						<!-- <option value="AK">Alaska</option> -->
					</select>
				</div>
				<label class="col-xs-1 control-label no-padding-right" style="padding-top:6px" > 日期:</label>
				<div class="input-daterange input-group" style="padding-top:4px" data-date-format="yyyy-mm-dd" >
					<input type="text" class="input-sm form-control" placeholder="开始日期" name="start" v-model="taskquery.start"/>
					<span class="input-group-addon">
						<i class="fa fa-exchange"></i>
					</span>

					<input type="text" class="input-sm form-control" placeholder="结束日期" name="end" v-model="taskquery.end"/>
				</div>
				<!-- <div class="col-xs-1"> -->
				<!-- 	<1!-- <button class="btn btn-white btn-info" type="button" v-on:click="query"> --1> -->
				<!-- 	<button class="btn btn-info " style="border-radius:4px" type="button" v-on:click="query"> -->
				<!-- 		<i class="ace-icon fa fa-check bigger-110"></i> -->
				<!-- 		查询 -->	
				<!-- 	</button> -->
				<!-- </div> -->

			<!-- <div class="col-xs-5"> -->

			</div>
		</div>
		<div class="space-4"></div>
		<div class="row">
			<div class="col-xs-12">
				
				<div class="col-xs-2">
					<!-- <button class="btn btn-white btn-info" type="button" v-on:click="query"> -->
					<button class="btn btn-info " style="border-radius:4px" type="button" v-on:click="query">
						<!-- <i class="ace-icon fa fa-check bigger-110"></i> -->
						<i class="ace-icon glyphicon glyphicon-search bigger-110"></i>
						查询	
					</button>
				</div>
			</div>
		</div>

	<hr>

	<div class="row">
		<div class="col-xs-12">
		<!--  ===================== separate line ===================== -->

		<div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer">
		
		<table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
		<thead>
			<tr>
				<!-- 
				<th class="center">
					<label class="position-relative">
						<input type="checkbox" class="ace">
						<span class="lbl"></span>
					</label>
				</th>
				-->
				<th>任务类型</th>
				<th>标题</th>
				<!-- <th class="hidden-480">Clicks</th> -->
				<th> <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i> 创建时间 </th>
				<th> <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i> 截止时间 </th>
				<th >状态</th>

				<th></th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="task in tasklist.tasks">

			<!--
			<td class="center">
				<label class="position-relative">
				<input type="checkbox" class="ace">
				<span class="lbl"></span>
				</label>
			</td>
			-->
			<!-- <td> taskTypeList[task.task_type].value</td> -->
			<td> {{CT.taskType[task.task_type]}}</td>
			<td> {{task.task_info.title}} </td>
			<td> {{task.task_info.stime}} </td>
			<td> {{task.task_info.etime}} </td>
		<td>{{task.task_info.node_name}}&nbsp;&nbsp;<span class="label label-sm label-warning" v-if="task.task_info.flag!='normal'" > {{CT.taskState[task.task_info.flag] }} </span> </td>

			<td>
				<div class="hidden-sm hidden-xs btn-group">
					<button class="btn btn-success" v-on:click="read(task)"> <i class="ace-icon fa fa-pencil-square-o"></i> 查看 </button>
				</div>

				<div class="hidden-md hidden-lg">
					<div class="inline position-relative">
						<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
						<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
						</button>
						
						<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
							
							<li>
							<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="查看" v-on:click="read(task)">
							<span class="green">
							<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
							</span>
							</a>
							</li>

						</ul>
					</div>
				</div>
				</td>
			</tr>

		</tbody>
		</table>

		<div class="row">
			<div class="col-xs-6">
			</div>
			<div class="col-xs-6">
				<div class="dataTables_paginate paging_simple_numbers">
					<pages v-bind:total-size="tasklist.pagetotal" v-bind:page-size="pageSize" v-on:page-set="pageSet" v-on:page-next="next" v-on:page-prev="prev" v-on:page-first="first" v-on:page-end="end"></pages>
				</div>
			</div>
		</div>

		</div>

		<!--  ===================== separate line ===================== -->
	</div>

	</div>

	<!--  ===================== separate line ===================== -->
</div>
</template>

<script>

import Pages from '../common/Pages'
import myAlert from '../common/myAlert'
import Combobox from '../common/Combobox'

import {reqComboboxData} from '../../vuex/modules/common/ComboboxActions'
import {reqHistoryTasks,clearHistoryQuery} from '../../vuex/modules/task/TaskActions'

import {getCodeList} from '../../vuex/modules/common/CodeTableGetters'
import {getCodeTable} from '../../vuex/modules/common/CodeTableGetters'

import {getTaskHistory,getTaskHistoryQuery} from '../../vuex/modules/task/TaskGetters'

export default {
	data:function(){
		return {
			pageSize:10,
			pageNumber:1,
			businessApps:[],
			taskTypeList:[],
			alertShow:false,
			alertType:'alert-success',
			alertContent:'aaaa',
			biz_id:''
		}
	},
	components: {
		Pages,
		myAlert,
		Combobox
	},
	vuex:{
		getters:{
			tasklist:getTaskHistory,
			taskquery:getTaskHistoryQuery,
			CT:getCodeTable
		},
		actions:{
			reqComboboxData,
			reqHistoryTasks,
			clearHistoryQuery
		}
	},
	methods:{
		change:function(Value){
			this.biz_id=Value;
		   },
		query:function(){
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,biz_id:self.biz_id,task_type:self.taskquery.tasktype,start:self.taskquery.start,end:self.taskquery.end};
						console.debug(requestBody);
			this.reqHistoryTasks(self,requestBody);

		},
		next:function(pageNumber) {
			this.pageNumber=pageNumber;
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,biz_id:self.biz_id,task_type:self.taskquery.tasktype,start:self.taskquery.start,end:self.taskquery.end};
			this.reqHistoryTasks(self,requestBody);
			console.debug("this parent page-next event "+pageNumber);
		},
		prev:function(pageNumber){
			this.pageNumber=pageNumber;
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,biz_id:self.biz_id,task_type:self.taskquery.tasktype,start:self.taskquery.start,end:self.taskquery.end};
			this.reqHistoryTasks(self,requestBody);
			console.debug("this parent page-prev event "+pageNumber);
		},
		first:function(pageNumber){
			this.pageNumber=pageNumber;
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,biz_id:self.biz_id,task_type:self.taskquery.tasktype,start:self.taskquery.start,end:self.taskquery.end};
			this.reqHistoryTasks(self,requestBody);
			console.debug("this parent page-first event "+pageNumber);
		},
		end:function(pageNumber){
			this.pageNumber=pageNumber;
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,biz_id:self.biz_id,task_type:self.taskquery.tasktype,start:self.taskquery.start,end:self.taskquery.end};
			this.reqHistoryTasks(self,requestBody);
			console.debug("this parent page-end event "+pageNumber);
		},
		pageSet:function(pageNumber){
			this.pageNumber=pageNumber;
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,biz_id:self.biz_id,task_type:self.taskquery.tasktype,start:self.taskquery.start,end:self.taskquery.end};
			this.reqHistoryTasks(self,requestBody);
			console.debug("this parent page-set event "+pageNumber);
		},

		read:function(task){
			console.debug(task);
			//TODO if task type
			this.$router.go({
								// path:'/mywork/mytask/myform',
								name:'myform',
								params:{
									taskId:task.task_id,
									taskType:task.task_type,
									nodeId:'step_detail',
									nodeName:task.task_info.node_name,
									instanceId:task.task_info.instance_id
								}
							});
		}
	},

	ready:function(){
		 console.debug('AAAAAAAAAAAAAAAAAAAAAAAA');
		
		  $('.input-daterange').datepicker({autoclose:true});
		
		this.reqComboboxData(this,'sysresource/biz_app/get-app-list.do',function(comp,items){
			comp.businessApps=items;
		});
		this.taskTypeList=getCodeList('taskType');		  
		this.clearHistoryQuery();
	  }
}

</script>



