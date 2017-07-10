
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
			<div class="profile-info-name"> 计划部署时间</div>
			<div class="profile-info-value"> <span >{{ task.form_data.plan_deploy_time }}</span> </div>
		</div>
		<div class="profile-info-row">
			<div class="profile-info-name"> 备注</div>
			<div class="profile-info-value"> <span >{{ task.form_data.description }}</span> </div>
		</div>
	</div>

	<validator name="validation">
	<form class="form-horizontal" role="form" id="myform">
		
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
				<button class="btn btn-warning" type="button" v-if="task.actions.reject">
					<i class="ace-icon fa fa-refresh bigger-110"></i>
					驳回
				</button>
				&nbsp; &nbsp; &nbsp;
				<button class="btn btn-danger" type="button" v-if="task.actions.stop">
					<i class="ace-icon fa fa-trash bigger-110"></i>
					终止
				</button>

			</div>
		</div>
	</form>
	</validator>

	<!--  ===================== separate line ===================== -->
		
</div>

</template>

<script>


import {submitProgramDeploy,stopProgramDeploy,rejectProgramDeploy,getProgramDeploy } from '../../vuex/modules/task/TaskActions'

import {getOneTaskProgramDeploy} from '../../vuex/modules/task/TaskGetters'
 
export default {
	props:["taskId","taskType","instanceId","nodeId"],
	vuex:{
		getters:{
			task:getOneTaskProgramDeploy
		},
		actions:{
			submitProgramDeploy,
			rejectProgramDeploy,
			stopProgramDeploy,
			getProgramDeploy
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
							opinion:result}};
			 		self.submitProgramDeploy(self,requestBody);
				}
			});
			}
		},
		reject:function(){
			let self=this;
			bootbox.prompt("请输入驳回意见", function(result) {
				if(result!=null) {
			 		let requestBody={ task_id:self.taskId,node_id:self.nodeId,task_type:self.taskType,instance_id:self.instanceId,opinion:result};
			 		self.rejectProgramDeploy(self,requestBody);
				}
			});
		},
		stop:function(){
			let self=this;
			bootbox.confirm("您确定要终止进程吗？", function(result) {
				if(result) {
			 		let requestBody={ task_id:self.taskId,node_id:self.nodeId,task_type:self.taskType,instance_id:self.instanceId};
			 		self.stopProgramDeploy(self,requestBody);
				}
			});
		}
	},
	ready:function(){

		this.getProgramDeploy(this.taskId,this.taskType,this.instanceId);


	}

}
</script>

