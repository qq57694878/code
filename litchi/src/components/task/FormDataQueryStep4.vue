
<template>
<div>
	<div class="profile-user-info profile-user-info-striped">
		<div class="profile-info-row">
			<div class="profile-info-name"> 标题 </div>
			<div class="profile-info-value"> <span >{{ task.form_data.title }}</span> </div>
		</div>
		<div class="profile-info-row">
			<div class="profile-info-name"> 业务系统 </div>
			<div class="profile-info-value"> <span >{{ task.form_data.business_name }}</span> </div>
		</div>

		<div class="profile-info-row">
			<div class="profile-info-name"> 详细描述 </div>
			<div class="profile-info-value"> 
			<span>
					{{{task.form_data.description}}}
			</span> 
			</div>
		</div>

		<div class="profile-info-row">
			<div class="profile-info-name"> 开始时间 </div>
			<div class="profile-info-value"> <span >{{task.form_data.stime}}</span> </div>
		</div>
		<div class="profile-info-row">
			<div class="profile-info-name"> 截止时间 </div>
			<div class="profile-info-value"> <span >{{task.form_data.etime}}</span> </div>
		</div>

		<div class="profile-info-row">
			<div class="profile-info-name"> 外协公司 </div>
			<div class="profile-info-value"> 
			<span> {task.form_data.cocompany.company_name}}</span> 
			</div>
		</div>
		<div class="profile-info-row">
			<div class="profile-info-name"> 负责人 </div>
			<div class="profile-info-value"> 
			<span> {task.form_data.cocompany.leader_name}}</span> 
			</div>
		</div>

	</div>

	<hr>

	<validator name="validation">
	<form class="form-horizontal" role="form">

		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 实际开始时间</label>
			<div class="col-sm-5">
				<div class="input-group">
					<input class="form-control date-picker"  type="text" data-date-format="yyyy-mm-dd" name="actually_stime" v-model="task.form_data.actually_stime">
					<span class="input-group-addon">
					<i class="fa fa-calendar bigger-110"></i>
					</span>
				</div>
			</div>
			<div class="help-block"> <span v-if="$validation.actually_stime.required" style="color:#d16e6c">*必填</span> </div>
		</div>

		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 实际完成时间</label>
			<div class="col-sm-5">
				<div class="input-group">
					<input class="form-control date-picker"  type="text" data-date-format="yyyy-mm-dd" name="actually_etime" v-model="task.form_data.actually_etime">
					<span class="input-group-addon">
					<i class="fa fa-calendar bigger-110"></i>
					</span>
				</div>
			</div>
			<div class="help-block"> <span v-if="$validation.actually_etime.required" style="color:#d16e6c">*必填</span> </div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1">查询SQL</label>
			<div class="col-sm-9">
				<textarea type="text"   class="col-xs-10 col-sm-5" v-model="task.form_data.actually_sql">
			</div>
		</div>

		<div class="space-4"></div>
		<div class="clearfix form-actions">
			<div class="col-md-offset-3 col-md-9">
				<button class="btn btn-info" type="button" v-on:click="submit" >
					<i class="ace-icon fa fa-check bigger-110"></i>
					提交
				</button>
				&nbsp; &nbsp; &nbsp;
				<button class="btn" type="reset" >
					<i class="ace-icon fa fa-undo bigger-110"></i>
					清空
				</button>
				&nbsp; &nbsp; &nbsp;
				<button class="btn btn-warning" type="button" v-if="task.actions.reject" v-on:click="reject">
					<i class="ace-icon fa fa-refresh bigger-110"></i>
					驳回
				</button>
				&nbsp; &nbsp; &nbsp;
				<button class="btn btn-danger" type="button" v-if="task.actions.stop" v-on:click="stop">
					<i class="ace-icon fa fa-trash bigger-110"></i>
					终止
				</button>
			</div>
		</div>
	</form>
	</validator>

</div>
</template>

<script>

import {getOneTaskDataQuery} from '../../vuex/modules/task/TaskGetters'
import {getCodeTable} from '../../vuex/modules/common/CodeTableGetters'
import {submitDataQuery,stopDataQuery,rejectDataQuery,getDataQuery } from '../../vuex/modules/task/TaskActions'

export default {
	props:["taskId","taskType","instanceId","nodeId"],
	vuex:{
		getters:{
			task:getOneTaskDataQuery,
			CT:getCodeTable
		},
		actions:{
			submitDataQuery,
			rejectDataQuery,
			stopDataQuery,
			getDataQuery
		}
	},
	methods:{
		submit:function(){
			if(this.$validation.valid){
			let self=this;
			bootbox.prompt("请输入意见", function(result) {
				if(result!=null) {
			 		let requestBody={ task_id:self.taskId,node_id:self.nodeId,task_type:self.taskType,instance_id:self.instanceId,
						form_data:{
							actually_stime:self.task.form_data.actually_stime,
							actually_etime:self.task.form_data.actually_etime,
							actually_sql:self.task.form_data.actually_sql,
							opinion:result
						}};
			 		self.submitDataQuery(self,requestBody);
				}
			});
			}
		},
		reject:function(){
			let self=this;
			bootbox.prompt("请输入驳回意见", function(result) {
				if(result!=null) {
			 		let requestBody={ task_id:self.taskId,node_id:self.nodeId,task_type:self.taskType,instance_id:self.instanceId,opinion:result};
			 		self.rejectDataQuery(self,requestBody);
				}
			});
		},
		stop:function(){
			let self=this;
			bootbox.confirm("您确定要终止进程吗？", function(result) {
				if(result) {
			 		let requestBody={ task_id:self.taskId,node_id:self.nodeId,task_type:self.taskType,instance_id:self.instanceId};
			 		self.stopDataQuery(self,requestBody);
				}
			});
		}

	},
	ready:function(){

		var self=this;
		//datepicker plugin
		//link
		$('.date-picker').datepicker({
			autoclose: true,
			todayHighlight: true
		})
		.on('changeDate',function(e){
			self.$validate(true);
		})
		//show datepicker when clicking on the icon
		.next().on(ace.click_event, function(){
			$(this).prev().focus();
		});

		this.getDataQuery(this.taskId,this.taskType,this.instanceId);

	}

}
</script>

