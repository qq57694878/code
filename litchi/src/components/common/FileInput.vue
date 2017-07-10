<template>
	<form role="form" v-bind:id="form_id" enctype="multipart/form-data">
		<div> <input multiple="" type="file" name="attach" v-bind:id="file_input_id"  /> </div>
	</form>
</template>

<script>

import {S4} from '../../vuex/modules/common/guid'
import {ajaxReq} from '../../vuex/modules/common/ajaxReq'
import {getJWTtoken} from '../../vuex/modules/authentication/LoginActions'

const submitForUpload = (store,component,formData,success,failure) => {
	bootbox.dialog({
				message:'<h3 class="header smaller lighter grey"> <i class="ace-icon fa fa-spinner fa-spin orange bigger-275"></i> 喝咖啡的时间到了! <small>玩命加载中...</small> </h3>',
				buttons:[],
				onEscape:false,
				closeButton:false,
				animate:false
	});
	ajaxReq(store,{
					url:"file/generic/upload.do", 
				    body:formData,
		           	success:function(response){  
						let responseJson=response.json();
						if(success)success(component,responseJson);
						bootbox.hideAll();
					},
					failure:function(){
						if(failure)failure(component);
						bootbox.hideAll();
					} 
	});
}

const deleteForUpload = (store,component,fileId,success,failure) => {
	
	ajaxReq(store,{
					url:"file/generic/delete.do", 
				    body:{file_id:fileId},
		           	success:function(response){  
						let responseJson=response.json();
						if(success)success(component,responseJson);
					},
					failure:function(){
						if(failure)failure(component);
					} 
	});
}


export default {
	props:['attachCode','fileInfo','readonly'],
	data:function(){
		return {
			file_check_error:false,
			form_id:'',
			file_input_id:'',
			file:false,
			icon_remove:'fa fa-times'
		}
	},
	vuex:{
		actions:{
			submitForUpload,
			deleteForUpload
		}
	},
	computed:{
	},
	methods:{
	},
	events:{
		done:function(){
			console.debug('receive event done');
			if(this.fileInfo){
				this.file=this.fileInfo;
				// this.file={
				// 	file_name:'流程图',
				// 	url:"http://192.168.1.55:9999/api/exclude/file/download/453282430812160.do?t=1483085464810"
				// };

				let files=[
				// {type:'image',name:'testabcefd.jpg',path: this.file.url}
				{name:this.file.file_name,path: this.file.url}
				];
				$('#'+this.file_input_id).my_ace_file_input('show_file_list',files);
			}

		}
	},
	watch:{
	},
	created:function(){
		let s4=S4();
		this.file_input_id='input-file-'+s4;
		this.form_id='form-'+s4;
		if(!!this.readonly)
			this.icon_remove='';
	},
	ready:function(){
		var self=this;
		console.debug(this.file_input_id);
		// this.$nextTick(function(){

					$('#'+this.file_input_id).my_ace_file_input({
										style:'well',
										btn_choose:'上传',
										btn_change:null,
										icon_remove: self.icon_remove,
										// droppable:true,
										thumbnail:'false',
										preview_error : function(filename, error_code) {
										},
										before_remove:function(element){
											console.debug('before remove');
											// var ret=true;

											// $.ajax({
											// 	url:'/api/file/generic/delete.do',
											// 	contentType:'application/json;charset=utf-8',
											// 	dataType:'json',
											// 	data:{file_id:self.file.file_id},
											// 	headers:{
											// 		'authentication':getJWTtoken()
											// 	},
											// 	method:'POST',
											// 	async:true,
											// 	success:function(data,textStatus,jqXHR){
											// 		$('#'+self.file_input_id).my_ace_file_input('reset_input');
											// 		// ret=true;
											// 		console.debug('sync ajax remove success');
											// 		self.$dispatch('remove',self.file.file_id,self.attachCode);
											// 		self.file='';
											// 	},
											// 	error:function(jqXHR,textStatus,errorThrown){
											// 		// ret=false;
											// 		console.debug('sync ajax remove error');
											// 		self.$dispatch('error','文件删除失败！');
											// 	}

											// });

											self.deleteForUpload(self,self.file.file_id,function(comp,data){
													$('#'+self.file_input_id).my_ace_file_input('reset_input');
													console.debug('sync ajax remove success');
													self.$dispatch('remove',self.file.file_id,self.attachCode);
													self.file='';
											},function(comp){
													console.debug('sync ajax remove error');
													//TODO send event parent error
													self.$dispatch('error','文件删除失败！');
											});

											// console.debug('before remove end');
											// return ret;
											return false;
										},
										maxSize:10000000
										
									}).on('change', function(){
										console.debug('change');

										if(!self.file_check_error){
											let formData=new FormData($('#'+self.form_id)[0]);
											console.debug(formData);
											self.submitForUpload(self,formData,function(comp,resJson){

												// self.file={
												// 	file_id:'',
												// 	file_name:'',
												// 	file_type:'', //文件类型 pic | pdf | word | exel
												// 	url:'' //下载url
												// }
												comp.file=resJson.data;
												self.$dispatch('upload',self.file.file_id,self.attachCode);
												console.debug('upload success');

											},function(comp){
												$('#'+comp.file_input_id).my_ace_file_input('reset_input');
												console.debug('upload failed');
												//TODO send event parent error
												self.$dispatch('error','文件上传失败！');

											});

											
										}else {
											self.file_check_error=false; //reset file check error
										}

									}).on('click', function(e){

										if(!!self.file){
											e.preventDefault();
											e.stopPropagation();
											console.debug('download');
											$.download(self.file.url);
										}else{
										}
										console.debug('click click click');
										// console.debug($('#id-input-file-3').data('ace_input_files'));
									}).on('file.error.ace', function(e){
										console.debug('file error ace');
										self.file_check_error=true;
										//TODO send event parent error
										self.$dispatch('error','文件太大了，不能上传！');
									});

			  
			// });

	
	}
}

jQuery.download = function(url, method, filedir, filename){
	jQuery('<form action="'+url+'" method="'+(method||'post')+'">' +  // action请求路径及推送方法
			'<input type="text" name="filedir" value="'+filedir+'"/>' + // 文件路径
			'<input type="text" name="filename" value="'+filename+'"/>' + // 文件名称
			'</form>')
		.appendTo('body').submit().remove();
};


</script>

<style scoped>

</style>
