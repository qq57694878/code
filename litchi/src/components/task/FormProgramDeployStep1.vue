
<template>
<div>

	<validator name="validation">
	<form class="form-horizontal" role="form" id="myform">
		
		<div class="form-group">
			<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 标题</label>
			<div class="col-sm-8">
			<input type="text" name="title" id="form-field-1" placeholder="Title" class="col-sm-12" v-model="task.form_data.title" v-validate:title="['required']">
			</div>
			<div class="help-block"> <span v-if="$validation.title.required" style="color:#d16e6c">*必填</span> </div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 业务系统</label>
			<div class="col-sm-8">
				<combobox name="business_app" v-bind:model="task.form_data.business_app" v-bind:items="businessApps" v-validate:business_app="['required']"></combobox>
			</div>
			<div class="help-block"> <span v-if="$validation.business_app.required" style="color:#d16e6c">*必填</span> </div>
		</div>
		<div class="form-group">
			<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 计划部署时间</label>
			<div class="col-sm-5">
				<div class="input-group">
					<input class="form-control date-picker" name="etime" type="text" data-date-format="yyyy-mm-dd" v-bind:model="task.form_data.plan_deploy_time" v-validate:etime="['required']">
					<span class="input-group-addon">
					<i class="fa fa-calendar bigger-110"></i>
					</span>
				</div>
			</div>
			<div class="help-block"> <span v-if="$validation.etime.required" style="color:#d16e6c">*必填</span> </div>
		</div>

		<div class="space-8"></div>
		<div class="form-group">
			<label class="col-sm-1 control-label no-padding-right" for="form-field-1">备注</label>
			<div class="col-sm-8">
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

	<!--  ===================== separate line ===================== -->
		
</div>

</template>

<script>


import {submitProgramDeploy,stopProgramDeploy,rejectProgramDeploy,getProgramDeploy } from '../../vuex/modules/task/TaskActions'

import {getOneTaskProgramDeploy} from '../../vuex/modules/task/TaskGetters'
import Combobox from '../common/Combobox'
import {reqComboboxData} from '../../vuex/modules/common/ComboboxActions'
 
export default {
	props:["taskId","taskType","instanceId","nodeId"],
	data:function(){
		return {
			businessApps:[]
		}
	},
	vuex:{
		getters:{
			task:getOneTaskProgramDeploy
		},
		actions:{
			submitProgramDeploy,
			rejectProgramDeploy,
			stopProgramDeploy,
			getProgramDeploy,
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
							req_change_id:self.task.form_data.req_change_id,
							title:self.task.form_data.title,
							description:$('#htmleditor').summernote('code'),
							plan_deploy_time:self.task.form_data.plan_deploy_time,
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
		var self=this;
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


		$('#id-input-file-1').ace_file_input({
					style:'well',
					btn_choose:'Drop files here or click to choose',
					btn_change:null,
					no_icon:'ace-icon fa fa-cloud-upload',
					droppable:true,
					thumbnail:'small'//large | fit
					//,icon_remove:null//set null, to hide remove/reset button
					/**,before_change:function(files, dropped) {
						//Check an example below
						//or examples/file-upload.html
						return true;
					}*/
					/**,before_remove : function() {
						return true;
					}*/
					,
					preview_error : function(filename, error_code) {
						//name of the file that failed
						//error_code values
						//1 = 'FILE_LOAD_FAILED',
						//2 = 'IMAGE_LOAD_FAILED',
						//3 = 'THUMBNAIL_FAILED'
						//alert(error_code);
					}
			
				}).on('change', function(){
					// console.log($(this).data('ace_input_files'));
					// console.log($(this).data('ace_input_method'));
				});
				
				
				//$('#id-input-file-3')
				//.ace_file_input('show_file_list', [
					//{type: 'image', name: 'name of image', path: 'http://path/to/image/for/preview'},
					//{type: 'file', name: 'hello.txt'}
				//]);
			
				//dynamically change allowed formats by changing allowExt && allowMime function
				// $('#id-file-format').removeAttr('checked').on('change', function() {
				// 	var whitelist_ext, whitelist_mime;
				// 	var btn_choose
				// 	var no_icon
				// 	if(this.checked) {
				// 		btn_choose = "Drop images here or click to choose";
				// 		no_icon = "ace-icon fa fa-picture-o";
			
				// 		whitelist_ext = ["jpeg", "jpg", "png", "gif" , "bmp"];
				// 		whitelist_mime = ["image/jpg", "image/jpeg", "image/png", "image/gif", "image/bmp"];
				// 	}
				// 	else {
				// 		btn_choose = "Drop files here or click to choose";
				// 		no_icon = "ace-icon fa fa-cloud-upload";
						
				// 		whitelist_ext = null;//all extensions are acceptable
				// 		whitelist_mime = null;//all mimes are acceptable
				// 	}
				// 	var file_input = $('#id-input-file-3');
				// 	file_input
				// 	.ace_file_input('update_settings',
				// 	{
				// 		'btn_choose': btn_choose,
				// 		'no_icon': no_icon,
				// 		'allowExt': whitelist_ext,
				// 		'allowMime': whitelist_mime
				// 	})
				// 	file_input.ace_file_input('reset_input');
					
				// 	file_input
				// 	.off('file.error.ace')
				// 	.on('file.error.ace', function(e, info) {
				// 		//console.log(info.file_count);//number of selected files
				// 		//console.log(info.invalid_count);//number of invalid files
				// 		//console.log(info.error_list);//a list of errors in the following format
						
				// 		//info.error_count['ext']
				// 		//info.error_count['mime']
				// 		//info.error_count['size']
						
				// 		//info.error_list['ext']  = [list of file names with invalid extension]
				// 		//info.error_list['mime'] = [list of file names with invalid mimetype]
				// 		//info.error_list['size'] = [list of file names with invalid size]
						
						
				// 		/**
				// 		if( !info.dropped ) {
				// 			//perhapse reset file field if files have been selected, and there are invalid files among them
				// 			//when files are dropped, only valid files will be added to our file array
				// 			e.preventDefault();//it will rest input
				// 		}
				// 		*/
						
						
				// 		//if files have been selected (not dropped), you can choose to reset input
				// 		//because browser keeps all selected files anyway and this cannot be changed
				// 		//we can only reset file field to become empty again
				// 		//on any case you still should check files with your server side script
				// 		//because any arbitrary file can be uploaded by user and it's not safe to rely on browser-side measures
				// 	});
				
				// });

		$('#id-input-file-2').ace_file_input({
					style:'well',
					btn_choose:'Drop files here or click to choose',
					btn_change:null,
					no_icon:'ace-icon fa fa-cloud-upload',
					droppable:true,
					thumbnail:'small',
					preview_error : function(filename, error_code) {
					}
				}).on('change', function(){
				});
		$('#id-input-file-3').ace_file_input({
					style:'well',
					btn_choose:'Drop files here or click to choose',
					btn_change:null,
					no_icon:'ace-icon fa fa-cloud-upload',
					droppable:true,
					thumbnail:'small',
					preview_error : function(filename, error_code) {
					}
				}).on('change', function(){
				});

		var callbackSuccess=function(responseJson){
			$('#htmleditor').summernote('code',responseJson.data.form_data.description);
			self.$validate(true);
		}
		this.getProgramDeploy(this.taskId,this.taskType,this.instanceId,callbackSuccess);
		this.reqComboboxData(this,'sysresource/biz_app/get-app-list.do',function(comp,items){
			comp.businessApps=items;
		});


	}

}
</script>

