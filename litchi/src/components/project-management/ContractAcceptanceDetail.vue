<template>
<div class="page-header">
	<h1>
		合同验收	
	<small style="font-size=16px"> <i class="ace-icon fa fa-angle-double-right"></i> 详细页 </small>
	</h1>
</div>
<div class="row" >
	<div class="col-xs-12">
		 <my-alert v-bind:alert-type="alertType" v-bind:content="alertContent" v-bind:show.sync="alertShow"></my-alert>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">

		<validator name="validation">
	
		<form class="form-horizontal" role="form" id="myform">
			
			<div class="form-group">
				<div class="col-md-6" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 采购项目名称</label>
					<div class="col-sm-8">
						<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.procurement_name" >
					</div>
				</div>
			
				<div class="col-md-6" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 合同名称</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.name">
					</div>
				</div>
				
			</div>
			
			<div class="form-group">
				<div class="col-md-6">
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 中标公司</label>
					<div class="col-sm-8">
						<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.vender_name" >
					</div>
				</div>
			
				<div class="col-md-6">
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 合同金额</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.amount">
					</div>
				</div>
				
			</div>
		

			<div class="form-group">
				<div class="col-md-12">
					<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 合同有效期</label>
					<div class="col-sm-8">
						<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-2" v-model="detail.sdate" >
						<label class="col-xs-1 control-label text-align-center no-padding-right" for="form-field-1"> 至</label>
						<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-2" v-model="detail.edate" >
					</div>
				</div>

			</div>
			
			<div class="form-group">
			
				<div class="col-md-6">
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 合同编号</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.cno">
					</div>
				</div>
			
				<div class="col-md-6">
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 监理公司</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.supervision">
					</div>
				</div>

				
			</div>

			<div class="form-group">
				<div class="col-md-12" >
					<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 合同影印件</label>
					<div class="col-sm-1">
						<file-input v-bind:attach-code="" v-bind:file-info="detail.contract_file" v-bind:readonly="1" v-on:upload="contractUpload" v-on:remove="contractRemove" v-on:error="contractError"></file-input> 
						<!-- <file-input v-bind:attach-code="" v-bind:file-info=""  v-on:upload="contractUpload" v-on:remove="contractRemove" v-on:error="contractError" v-else ></file-input> --> 
					</div>
				</div>
			</div>
		
			<div class="form-group">
			
				<div class="col-md-6" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 合同责任人</label>
					<div class="col-sm-8">
						<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.responsible_person_name">
					</div>
				</div>
			
				<div class="col-md-6" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 预计验收时间</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.estimated_reception_time">
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="col-md-1" >
				</div>
				<div class="col-md-2" >
					<button class="btn btn-info" style="border-radius:4px" type="button" v-on:click="readexeplan">
						<!-- <i class="ace-icon glyphicon glyphicon-plus bigger-90"></i> -->
						查看合同执行计划	
					</button>
				</div>
				<div class="col-md-2" >
					<button class="btn btn-info " style="border-radius:4px" type="button" v-on:click="readexe">
						<!-- <i class="ace-icon glyphicon glyphicon-plus bigger-90"></i> -->
						查看合同执行	
					</button>
				</div>
				<div class="col-md-2" >
					<button class="btn btn-info " style="border-radius:4px" type="button" v-on:click="readpay">
						<!-- <i class="ace-icon glyphicon glyphicon-plus bigger-90"></i> -->
						查看合同付款	
					</button>
				</div>
				<div class="col-md-3" >
				</div>
			</div>

		<hr>
		<div class="space-4"></div>

			<div class="form-group">
				<div class="col-md-6" v-if="enterguard.state=='readonly'" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 实际验收时间</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.reception_time">
					</div>
				</div>
				<div class="col-md-6" v-else >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 实际验收时间</label>
					<div class="col-sm-8">
						<input type="text" class="form-control date-picker"class="col-sm-12" name="reception_time" placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="detail.reception_time" v-validate:reception_time="['required']" >
					</div>
					<div class="help-block"> <span v-if="$validation.reception_time.required" style="color:#d16e6c">*必填</span> </div>
				</div>
			</div>	
			<div class="form-group">
				<div class="col-md-6" v-if="enterguard.state=='readonly'" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 参与验收专家</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.expert">
					</div>
				</div>
				<div class="col-md-6" v-else >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 参与验收专家</label>
					<div class="col-sm-8">
					<input type="text" name="expert" id="form-field-1" placeholder="" class="col-xs-12" v-model="detail.expert" v-validate:expert="['required']" >
					</div>
					<div class="help-block"> <span v-if="$validation.expert.required" style="color:#d16e6c">*必填</span> </div>
				</div>
			</div>	


			<div class="form-group">
				<div class="col-md-12" >
					<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 专家意见</label>
					<div class="col-sm-1">
						<file-input v-bind:attach-code="" v-bind:file-info="detail.expert_opinion_file" v-if="enterguard.state=='readonly'" v-bind:readonly="1" v-on:upload="expertUpload" v-on:remove="expertRemove" v-on:error="expertError"></file-input> 
						<file-input v-bind:attach-code="" v-bind:file-info="detail.expert_opinion_file" v-on:upload="expertUpload" v-on:remove="expertRemove" v-on:error="expertError" v-else ></file-input> 
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="col-md-12" >
					<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 验收结论</label>
					<div class="col-sm-1">
						<file-input v-bind:attach-code="" v-bind:file-info="detail.verdict_file" v-if="enterguard.state=='readonly'" v-bind:readonly="1" v-on:upload="verdictUpload" v-on:remove="verdictRemove" v-on:error="verdictError"></file-input> 
						<file-input v-bind:attach-code="" v-bind:file-info="detail.verdict_file" v-on:upload="verdictUpload" v-on:remove="verdictRemove" v-on:error="verdictError" v-else ></file-input> 
					</div>
				</div>
			</div>

		</form>
		</validator>
	</div>
</div>
<div class="space-4"></div>
<div class="row">
	<div class="col-xs-12">
		<div class="clearfix no-padding-left form-actions">
				<!-- <div class="col-md-offset-2 col-md-10"> -->
				<div class="col-md-12 no-padding-left">
					<button class="btn btn-info" type="button"  v-if="enterguard.state!='readonly'" v-on:click="save">
						<i class="ace-icon fa fa-check bigger-110"></i>
						保存
					</button>

					&nbsp; &nbsp; &nbsp;
					<button class="btn" type="reset" v-on:click="back">
						<i class="ace-icon fa fa-undo bigger-110"></i>
						返回
					</button>

					<button class="btn btn-info pull-right" type="button"  v-if="enterguard.state!='readonly'" v-on:click="acceptance">
						<!-- <i class="ace-icon fa fa-undo bigger-110"></i> -->
					    完成验收
					</button>
				</div>
		</div>

	</div>
</div>

</template>

<script>
import FileInput from '../common/FileInput'
import myAlert from '../common/myAlert'
import {reqContractDetail} from '../../vuex/modules/project-management/ContractRegisterActions'
import {getContractAcceptanceDetailEnterGuard,getOneContractDetail} from '../../vuex/modules/project-management/ContractRegisterGetters'		

export default {
	data:function(){
		return {
			alertShow:false,
			alertType:'alert-success',
			alertContent:'aaaa',
			expert_attach_code:'',
			expert_opinion_file:'',
			verdict_attach_code:'',
			verdict_file_id:'',
			contract_attach_code:'',
			contract_file_id:'',
			treeQuery:{ root:{code:'xxzx'} }
		}
	},
	components: {
		myAlert,
		FileInput,
	},
	vuex:{
		getters:{
			detail:getOneContractDetail,
			enterguard:getContractAcceptanceDetailEnterGuard
		},
		actions:{
			reqContractDetail,
		}
	},
	methods:{
		contractUpload:function(fileId,attachCode){
			console.debug("========contractUpload===================================");
			console.debug(fileId);
			this.contract_file_id = fileId;
			this.contract_attach_code = attachCode;
		},
		contractRemove:function(fileId,attachCode){
					  if(this.contract_file_id = fileId){
						  this.contract_file_id = '';
						  this.contract_attach_code = '';
					  }
		},
		contractError:function(errMsg){
					   },
		expertUpload:function(fileId,attachCode){
			console.debug("=====expert===Upload===================================");
			console.debug(fileId);
			this.expert_opinion_file = fileId;
			this.expert_attach_code = attachCode;
		},
		expertRemove:function(fileId,attachCode){
					  if(this.expert_opinion_file = fileId){
						  this.expert_opinion_file = '';
						  this.expert_attach_code = '';
					  }
		},
	   expertError:function(errMsg){
	   },
		verdictUpload:function(fileId,attachCode){
			this.verdict_file_id = fileId;
			this.verdict_attach_code = attachCode;
		},
		verdictRemove:function(fileId,attachCode){
					  if(this.verdict_file_id = fileId){
						  this.verdict_file_id = '';
						  this.verdict_attach_code = '';
					  }
		},
	   verdictError:function(errMsg){
	   },
		back:function(){
		//	this.$router.go({ name:'contract_acceptance'});
			this.$router.go({ name:this.enterguard.router});
		 }
	},
	ready:function(){	
		let self=this;
		//datepicker plugin
//		$('.date-picker').datepicker({
//			autoclose: true,
//			todayHighlight: true
//		}) .next().on(ace.click_event, function(){
//			$(this).prev().focus();
//		});

//	    $('input-daterange').datepicker({autoclose:true});

		//this.$broadcast('done');

	  if(this.enterguard.contractid != ''){
			  this.reqContractDetail(self,this.enterguard.contractid,function(){
					self.$nextTick(function(){
							self.$broadcast('done');
							$('.date-picker').datepicker({
								autoclose: true,
								todayHighlight: true
							}) .next().on(ace.click_event, function(){
								$(this).prev().focus();
							});

							$('input-daterange').datepicker({autoclose:true});
						})
					});
	  }else{
			this.$broadcast('done');
	  }
	}
}

</script>

<style scoped>
.text-align-center{
	text-align:center;
}
.margin-top-5px{
	margin-top:5px;
}
</style>
