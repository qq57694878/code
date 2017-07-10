
<template>
<div class="dataTables_wrapper form-inline no-footer">
		<table class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
		<thead>
			<tr>
				<th>类型</th>
				<th>上传人</th>
				<th>上传时间</th>
				<th>附件</th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="(code,codeName) in codetable">
			<td> {{	codeName }}</td>
			<td> {{attachments.attach_code[code].creator_name}}</td>
			<td> {{attachments.attach_code[code].cdate}}</td>
			<td> <file-input v-bind:attach-code="code" v-bind:file-info="attachments.attach_code[code]" v-bind:readonly="readonly" v-on:upload="upload" v-on:remove="remove" v-on:error="error" v-show="isShow(attachments.attach_code[code],readonly)"></file-input> </td>
			</tr>
			<tr v-for="other in attachments.others">
			<td> </td>
			<td> {{other.creator_name}}</td>
			<td> {{other.cdate}}</td>
			<td> <file-input v-show="isShow(other,readonly)" attach-code="" v-bind:file-info="other" v-bind:readonly="readonly" v-on:upload="upload" v-on:remove="remove" v-on:error="error"></file-input> </td>
			</tr>

		</tbody>
		</table>

		<div class="space-4"></div>
		<div class="pull-right" v-if="!readonly">
			<button class="btn btn-info btn-xs" type="button" v-on:click="add"> <i class="ace-icon fa fa-plus"></i> 添加附件</button>
		</div>

		</div>
</template>

<script>

import FileInput from '../common/FileInput'
export default {
	components: {
		FileInput
	},
	props:["codetable","attachments","readonly"],
	data:function(){
		return {
			uploads:[]
		}
	},
	filters:{
	},
	vuex:{
		actions:{
		}
	},
	computed:{
	},
	methods:{
		add:function(){
			this.attachments.others.push({
				attach_code:'',
				file_id:'',
				file_name:'',
				file_type:'', 
				url:'',
				creator_id:'',
				creator_name:'',
				cdate:''
			});

		},
		isShow:function(fileInfo,readonly)
		{
			if(readonly && !fileInfo)
				return false;
			else
				return true;
		},
		upload:function(fileId,attachCode){
			this.uploads.push({attach_code:attachCode,file_id:fileId});
			this.$dispatch('getuploads',this.uploads);
		},
		remove:function(fileId){
			if (!Array.prototype.findIndex) {
				Array.prototype.findIndex = function (fn, thisArg) {
					if (!(typeof fn =="function")) {
						throw new TypeError("fn不是一个有效的函数");
					}

					var arr = this;
					for (var i = 0, length = arr.length; i < length; i++) {
						if (fn.call(thisArg, arr[i], i, arr)) {
							return i;
						}
					}
					return -1;
				}
			}
			let index=this.uploads.findIndex(function(value,index,arr){
				return value.file_id==fileId;
			});
			if(index !=-1){
				console.debug(index);
				this.uploads.splice(index,1);
				this.$dispatch('getuploads',this.uploads);
			}
		},
		error:function(errorMsg){
			return true;
		}
	},
	events:{
		done:function(){
			if(this.attachments && this.attachments.attach_code){
				for(let k in this.attachments.attach_code){
					let v=this.attachments.attach_code[k];
					this.uploads.push({attach_code:v.attach_code,file_id:v.file_id});
				}
				if(this.attachments.others){
					for(let k in this.attachments.others){
						let v=this.attachments.others[k];
						if(v.file_id)
							this.uploads.push({attach_code:v.attach_code,file_id:v.file_id});
					}
				}
			}
			// console.debug(this.uploads);
			this.$dispatch('getuploads',this.uploads);
			return true;
		}
	},
	watch:{
	},
	created:function(){
	},
	ready:function(){
	}
}
</script>

<style scoped>
</style>
