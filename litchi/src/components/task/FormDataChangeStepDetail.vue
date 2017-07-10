
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
			<span> {{task.form_data.cocompany.company_name}}</span> 
			</div>
		</div>
		<div class="profile-info-row">
			<div class="profile-info-name"> 负责人 </div>
			<div class="profile-info-value"> 
			<span> {{task.form_data.cocompany.leader_name}}</span> 
			</div>
		</div>

		<div class="profile-info-row">
			<div class="profile-info-name"> 实际开始时间 </div>
			<div class="profile-info-value"> 
			<span> {{task.form_data.actually_stime}}</span> 
			</div>
		</div>
		<div class="profile-info-row">
			<div class="profile-info-name"> 实际结束时间 </div>
			<div class="profile-info-value"> 
			<span> {{task.form_data.actually_etime}}</span> 
			</div>
		</div>
		<div class="profile-info-row">
			<div class="profile-info-name"> 实际执行SQL </div>
			<div class="profile-info-value"> 
			<span> {{task.form_data.actually_sql}}</span> 
			</div>
		</div>
		<div class="profile-info-row">
			<div class="profile-info-name"> 备份SQL </div>
			<div class="profile-info-value"> 
			<span> {{task.form_data.backup_sql}}</span> 
			</div>
		</div>
		<div class="profile-info-row">
			<div class="profile-info-name"> 处理方案 </div>
			<div class="profile-info-value"> 
					{{{task.form_data.scheme}}}
			</div>
		</div>

	</div>

	<hr>

	<!-- <form class="form-horizontal" role="form"> -->

	<!-- 	<div class="space-4"></div> -->
	<!-- 	<div class="clearfix form-actions"> -->
	<!-- 		<div class="col-md-offset-3 col-md-9"> -->
	<!-- 			<button class="btn btn-info" type="button" v-on:click="submit" > -->
	<!-- 				<i class="ace-icon fa fa-check bigger-110"></i> -->
	<!-- 				提交 -->
	<!-- 			</button> -->
	<!-- 			&nbsp; &nbsp; &nbsp; -->
	<!-- 			<button class="btn" type="reset" > -->
	<!-- 				<i class="ace-icon fa fa-undo bigger-110"></i> -->
	<!-- 				清空 -->
	<!-- 			</button> -->
	<!-- 			&nbsp; &nbsp; &nbsp; -->
	<!-- 			<button class="btn btn-warning" type="button" v-if="task.actions.reject" v-on:click="reject"> -->
	<!-- 				<i class="ace-icon fa fa-refresh bigger-110"></i> -->
	<!-- 				驳回 -->
	<!-- 			</button> -->
	<!-- 			&nbsp; &nbsp; &nbsp; -->
	<!-- 			<button class="btn btn-danger" type="button" v-if="task.actions.stop" v-on:click="stop"> -->
	<!-- 				<i class="ace-icon fa fa-trash bigger-110"></i> -->
	<!-- 				终止 -->
	<!-- 			</button> -->
	<!-- 		</div> -->
	<!-- 	</div> -->
	<!-- </form> -->

</div>
</template>

<script>

import {getOneTaskDataChange} from '../../vuex/modules/task/TaskGetters'
import {getCodeTable} from '../../vuex/modules/common/CodeTableGetters'
import {submitDataChange,stopDataChange,rejectDataChange,getDataChange } from '../../vuex/modules/task/TaskActions'

export default {
	props:["taskId","taskType","instanceId","nodeId"],
	vuex:{
		getters:{
			task:getOneTaskDataChange,
			CT:getCodeTable
		},
		actions:{
			submitDataChange,
			rejectDataChange,
			stopDataChange,
			getDataChange
		}
	},
	methods:{
		submit:function(){
			let self=this;
			bootbox.prompt("请输入意见", function(result) {
				if(result!=null) {
			 		let requestBody={ task_id:self.taskId,node_id:self.nodeId,task_type:self.taskType,instance_id:self.instanceId,
						form_data:{
							opinion:result
						}};
			 		self.submitDataChange(self,requestBody);
				}
			});
		},
		reject:function(){
			let self=this;
			bootbox.prompt("请输入驳回意见", function(result) {
				if(result!=null) {
			 		let requestBody={ task_id:self.taskId,node_id:self.nodeId,task_type:self.taskType,instance_id:self.instanceId,opinion:result};
			 		self.rejectDataChange(self,requestBody);
				}
			});
		},
		stop:function(){
			let self=this;
			bootbox.confirm("您确定要终止进程吗？", function(result) {
				if(result) {
			 		let requestBody={ task_id:self.taskId,node_id:self.nodeId,task_type:self.taskType,instance_id:self.instanceId};
			 		self.stopDataChange(self,requestBody);
				}
			});
		}

	},
	ready:function(){
		this.getDataChange(this.taskId,this.taskType,this.instanceId);
	}

}

</script>

