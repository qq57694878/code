
<template>
<div>
	<div class="row" >
		<div class="col-xs-12">
			 <my-alert v-bind:alert-type="alertType" v-bind:content="alertContent" v-bind:show.sync="alertShow"></my-alert>
		</div>
	</div>

	<div class="row" >

		<div class="col-xs-5">
		<table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
		<thead>
			<tr>
				<th style="width:80%">文件名称</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="file in task.form_data.attachments">
				<td> <a v-bind:href="file.url" >{{file.file_name }} </a></td>
				<td>
					<div class="hidden-sm hidden-xs btn-group">
						<button class="btn btn-xs btn-danger" v-on:click="remove(file.file_id)" > <i class="ace-icon fa fa-trash-o"></i> 删除</button>
					</div>

					<div class="hidden-md hidden-lg">
						<div class="inline position-relative">
							<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
							<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
							</button>
							
							<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
								<li>
								<a href="#" class="tooltip-error" data-rel="tooltip" title="" data-original-title="删除" >
								<span class="red">
								<i class="ace-icon fa fa-trash-o bigger-120"></i>
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
		</div>

		<div class="col-xs-7">
		<!-- <form action="file/upload.do" class="dropzone" id="dropzone"> -->
		<form action="/api/file/upload.do" class="dropzone" id="dropzone">
			<input type="hidden" name="instance_id" v-bind:value="task.instance_id" />
			<div class="fallback">
				<input name="file" type="file" multiple="" />
			</div>
		</form>
		</div>
	</div>
</div>
</template>

<script>

import myAlert from '../common/myAlert'
import {successUploadAttachment,removeAttachment } from '../../vuex/modules/task/TaskActions'
import {getJWTtoken} from '../../vuex/modules/authentication/LoginActions'

export default {
	props:["task"],
	vuex:{
		actions:{
			successUploadAttachment,
			removeAttachment 
		}
	},
	data:function(){
		return {
			alertShow:false,
			alertType:'alert-success',
			alertContent:'aaaa'
		}
	},
	methods:{
		remove:function(fileId){
			this.removeAttachment(this,fileId); 
		}
	},
	components: {
		myAlert
	},
	// watch:{
	// 	'task.form_data.attachments':function(val,oldVal){
	// 		// console.debug('watch attachment');
	// 		// for(let file of val){
	// 		// 	// Create the mock file:
	// 		// 	let mockFile={name:file.file_name,size:0};

	// 		// 	// Call the default addedfile event handler
	// 		// 	this.myDropzone.emit("addedfile", mockFile);
				
	// 		// 	// And optionally show the thumbnail of the file:
	// 		// 	// myDropzone.emit("thumbnail", mockFile, "/image/url");
	// 		// 	// Or if the file on your server is not yet in the right
	// 		// 	// size, you can let Dropzone download and resize it
	// 		// 	// callback and crossOrigin are optional.
	// 		// 	// myDropzone.createThumbnailFromUrl(file, imageUrl, callback, crossOrigin);
				
	// 		// 	// Make sure that there is no progress bar, etc...
	// 		// 	this.myDropzone.emit("complete", mockFile);
				
	// 		// 	// If you use the maxFiles option, make sure you adjust it to the
	// 		// 	// correct amount:
	// 		// 	var existingFileCount = 1; // The number of files already uploaded
	// 		// 	this.myDropzone.options.maxFiles = this.myDropzone.options.maxFiles - existingFileCount;
	// 		// }
	// 	}
	//},
	ready:function(){

		console.debug('attachment ready readay readay');

		try {
			Dropzone.autoDiscover = false;
			var myDropzone = new Dropzone("#dropzone" , {
				paramName: "file", // The name that will be used to transfer the file
				maxFilesize: 5, // MB
				addRemoveLinks : true,
				dictDefaultMessage :
				'<span class="bigger-110 bolder"><i class="ace-icon fa fa-caret-right red"></i> 拖拽文件到此处</span> 上传文件\
				<span class="smaller-80 grey">(或者单击此处)</span> <br /> \
				<i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>'
				,
				dictResponseError: 'Error while uploading file!',

				//change the previewTemplate to use Bootstrap progress bars
				previewTemplate: "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-details\">\n    <div class=\"dz-filename\"><span data-dz-name></span></div>\n    <div class=\"dz-size\" data-dz-size></div>\n    <img data-dz-thumbnail />\n  </div>\n  <div class=\"progress progress-small progress-striped active\"><div class=\"progress-bar progress-bar-success\" data-dz-uploadprogress></div></div>\n  <div class=\"dz-success-mark\"><span></span></div>\n  <div class=\"dz-error-mark\"><span></span></div>\n  <div class=\"dz-error-message\"><span data-dz-errormessage></span></div>\n</div>"

			});
			myDropzone.on('sending',function(file,xhr,formData){
				xhr.setRequestHeader("authentication",getJWTtoken());
			});

			var self=this;
			myDropzone.on('success',function(file,response){
				console.debug('upload file success');
				console.debug(response);
				self.successUploadAttachment(response.data.attachments);
			});

			this.myDropzone=myDropzone;

			$(document).one('ajaxloadstart.page', function(e) {
				try {
					myDropzone.destroy();
				} catch(e) {}
			});

		} catch(e) {
			// alert('Dropzone.js does not support older browsers!');
		}

	}
}
</script>

