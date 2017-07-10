
<template>
<div>
	
	<validator name="validation">
	<form class="form-horizontal" role="form" id="myform">
		
		<div class="form-group">
			<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 标题</label>
			<div class="col-sm-8">
			<input type="text" name="title" placeholder="Title" class="col-sm-12" v-model="task.form_data.title" v-validate:title="['required']">
			</div>
			<div class="help-block"> <span v-if="$validation.title.required" style="color:#d16e6c">*必填</span> </div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 业务系统</label>
			<div class="col-sm-8">
				<combobox name="business_app" v-bind:model="task.form_data.business_app" v-bind:items="businessApps"  v-validate:business_app="['required']"></combobox>
			</div>
			<div class="help-block"> <span v-if="$validation.business_app.required" style="color:#d16e6c">*必填</span> </div>
		</div>
		<div class="form-group">
			<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 截止时间</label>
			<div class="col-sm-5">
				<div class="input-group">
					<input class="form-control date-picker" name="etime" type="text" data-date-format="yyyy-mm-dd" v-model="task.form_data.etime" v-validate:etime="['required']">
					<span class="input-group-addon">
					<i class="fa fa-calendar bigger-110"></i>
					</span>
				</div>
			</div>
			<div class="help-block"> <span v-if="$validation.etime.required" style="color:#d16e6c">*必填</span> </div>
		</div>

		<div class="space-8"></div>
		<div class="form-group">
			<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 详细描述</label>
			<div class="col-sm-9">
				<div id="htmleditor" ></div>
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

</div>
</template>

<script>


import {submitDataChange ,stopDataChange,rejectDataChange,getDataChange } from '../../vuex/modules/task/TaskActions'

import {getOneTaskDataChange} from '../../vuex/modules/task/TaskGetters'

import Combobox from '../common/Combobox'
import {reqComboboxData} from '../../vuex/modules/common/ComboboxActions'

export default {
	props:["taskId","taskType","instanceId","nodeId"],
	data:function(){
		return {
			businessApps:[],
		}
	},
	vuex:{
		getters:{
			task:getOneTaskDataChange
		},
		actions:{
			submitDataChange,
			rejectDataChange,
			stopDataChange,
			getDataChange,
			reqComboboxData 
		}
	},
	components: {
		Combobox
	},
	methods:{

		submit:function(){
			if(this.$validation.valid){
			let self=this;
			bootbox.prompt("请输入意见", function(result) {
				if(result!=null) {
			 		let requestBody={ task_id:self.taskId,node_id:self.nodeId,task_type:self.taskType,instance_id:self.instanceId,
						form_data:{
							business_app:self.task.form_data.business_app,
							title:self.task.form_data.title,
							description:$('#htmleditor').summernote('code'),
							etime:self.task.form_data.etime,
							opinion:result}};
			 		self.submitDataChange(self,requestBody);
				}
			});
			}
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

		var self=this;

		$('#htmleditor').summernote({
			height:300,
			fontSize:14,
			toolbar: [
				['style', ['bold', 'italic', 'underline', 'clear']],
				['fontsize', ['fontsize']],
				['color', ['color']],
				['para', ['style','ul', 'ol', 'paragraph']],
				['insert', ['table','hr']],
				['misc', ['undo','redo','fullscreen','codeview','help']],
			]
		});

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


		var callbackSuccess=function(responseJson){
			$('#htmleditor').summernote('code',responseJson.data.form_data.description);
			self.$validate(true);
		}
		this.getDataChange(this.taskId,this.taskType,this.instanceId,callbackSuccess);
		this.reqComboboxData(this,'sysresource/biz_app/get-app-list.do',function(comp,items){
			comp.businessApps=items;
		});


	}

}
</script>

