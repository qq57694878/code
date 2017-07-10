
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
	</div>

	<hr>

	<validator name="validation">
	<form class="form-horizontal" role="form">
		<div class="form-group">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 外协公司</label>
			<div class="col-sm-5">
				<combobox name="company_id" v-bind:model="task.form_data.cocompany.company_id" v-bind:items="cocompanys" v-on:change="comboboxChange" v-validate:company_id="['required']"></combobox>
			</div>
			<div class="help-block"> <span v-if="$validation.company_id.required" style="color:#d16e6c">*必填</span> </div>
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 负责人</label>
			<div class="col-sm-5">
				<select class="form-control" v-model="task.form_data.cocompany.leader_id">
					<option v-bind:value="task.form_data.cocompany.leader_id">{{task.form_data.cocompany.leader_name}}</option>
				</select>
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
				<button class="btn btn-danger" type="button" v-if="task.actions.stop">
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
import {submitDataQuery,stopDataQuery,rejectDataQuery,getDataQuery,reqVendorLeader } from '../../vuex/modules/task/TaskActions'

import Combobox from '../common/Combobox'
import {reqComboboxData} from '../../vuex/modules/common/ComboboxActions'

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
			getDataQuery,
			reqComboboxData,
			reqVendorLeader  
		}
	},
	components: {
		Combobox
	},
	methods:{
		comboboxChange:function(Value){
			// console.debug(Value);
			this.reqVendorLeader(this,function(comp,leader){
				this.task.form_data.cocompany.leader_id=leader.user_id;
			});
		},
		submit:function(){
			if(this.$validation.valid){
			let self=this;
			bootbox.prompt("请输入意见", function(result) {
				if(result!=null) {
			 		let requestBody={ task_id:self.taskId,node_id:self.nodeId,task_type:self.taskType,instance_id:self.instanceId,
						form_data:{
						cocompany:{
							id:self.task.form_data.cocompany.id,
							leader_id:self.task.form_data.cocompany.leader_id
						},
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
		this.getDataQuery(this.taskId,this.taskType,this.instanceId);

		this.reqComboboxData(this,'sysresource/vendor/get-vendor-list.do',function(comp,items){
			comp.cocompanys=items;
		});
	}

}
</script>

