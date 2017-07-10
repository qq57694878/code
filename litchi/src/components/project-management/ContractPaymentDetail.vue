<template>
<div class="page-header">
	<h1>
		合同付款
	<small style="font-size:16px"> <i class="ace-icon fa fa-angle-double-right"></i> 详细页 </small>
	</h1>
</div>
<div class="row" >
	<div class="col-xs-12">
		 <my-alert v-bind:alert-type="alertType" v-bind:content="alertContent" v-bind:show.sync="alertShow"></my-alert>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">

		<form class="form-horizontal" role="form" id="myform">

			<div class="form-group">

				<div class="col-md-6" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 合同名称</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.name">
					</div>
				</div>
				<div class="col-md-6">
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 中标公司</label>
					<div class="col-sm-8">
						<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.vender_name" >
					</div>
				</div>

			</div>

			<div class="form-group">
				<div class="col-md-6">
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 合同金额</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.amount">
					</div>
				</div>
			</div>
		</form>
		<hr>
		<form class="form-horizontal" v-for="pay in detail.payment" role="form" id="myform">
			<div class="form-group">
				<div class="col-md-6">
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 第{{$index +1}}次付款</label>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-6" v-if="pay.status=='1'||enterguard.state=='readonly'" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 付款条件</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="pay.terms">
					</div>
				</div>
				<div class="col-md-6" v-else >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 付款条件</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" placeholder="" class="col-xs-12" v-model="pay.terms">
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="col-md-6" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 预计付款时间</label>
					<div class="col-sm-8">
					<input type="text" v-if="pay.status=='1'||enterguard.state=='readonly'" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="pay.estimated_date">
					<input type="text" v-else class="form-control date-picker"class="col-xs-12" placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="pay.estimated_date">
					</div>
				</div>

				<div class="col-md-6" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 付款金额</label>
					<div class="col-sm-8">
						<input type="text" v-if="pay.status=='1'||enterguard.state=='readonly'" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="pay.amount">
						<input type="text" v-else id="form-field-1" placeholder="" class="col-xs-12" v-model="pay.amount">
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-6" >
					<label class="col-sm-2 control-label no-padding-right"> 实际付款时间</label>
					<div class="col-sm-8">
					<input type="text" v-if="pay.status=='1'||enterguard.state=='readonly'" readonly placeholder="" class="col-xs-12" v-model="pay.paydate">
					<input type="text" v-else class="form-control date-picker"class="col-xs-12" placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="pay.paydate">
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="col-md-6" >
					<label class="col-sm-2 control-label no-padding-right"> 发票号</label>
					<div class="col-sm-8">
					<input type="text" v-if="pay.status=='1'||enterguard.state=='readonly'" readonly placeholder="" class="col-xs-12" v-model="pay.invoice_no">
					<input type="text" v-else placeholder="" class="col-xs-12" v-model="pay.invoice_no">
					</div>
				</div>
				<div class="col-md-6" >
					<label class="col-sm-2 control-label no-padding-right"> 发票照片</label>
					<div class="col-sm-8">
						<file-input v-bind:attach-code="" v-bind:file-info="pay.invoice_file" v-if="pay.status=='1'||enterguard.state=='readonly'" v-bind:readonly="1" v-on:upload="expertUpload" v-on:remove="expertRemove" v-on:error="expertError"></file-input>
						<file-input v-bind:attach-code="" v-bind:file-info="pay.invoice_file" v-on:upload="expertUpload" v-on:remove="expertRemove" v-on:error="expertError" v-else ></file-input>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-6" >
					<label class="col-sm-2 control-label no-padding-right"> 支票号</label>
					<div class="col-sm-8">
					<input type="text" v-if="pay.status=='1'||enterguard.state=='readonly'" readonly placeholder="" class="col-xs-12" v-model="pay.check_no">
					<input type="text" v-else placeholder="" class="col-xs-12" v-model="pay.check_no">
					</div>
				</div>
				<div class="col-md-6" >
					<label class="col-sm-2 control-label no-padding-right"> 支票照片</label>
					<div class="col-sm-8">
						<file-input v-bind:attach-code="" v-bind:file-info="pay.check_file" v-if="pay.status=='1'||enterguard.state=='readonly'" v-bind:readonly="1" v-on:upload="expertUpload" v-on:remove="expertRemove" v-on:error="expertError"></file-input>
						<file-input v-bind:attach-code="" v-bind:file-info="pay.check_file" v-on:upload="expertUpload" v-on:remove="expertRemove" v-on:error="expertError" v-else ></file-input>
					</div>
				</div>
			</div>

		<hr>
		</form>

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

				</div>
		</div>

	</div>
</div>

</template>

<script>
import FileInput from '../common/FileInput'
import myAlert from '../common/myAlert'
import {reqContractDetail} from '../../vuex/modules/project-management/ContractRegisterActions'
import {getContractPaymentDetailEnterGuard,getOneContractDetail} from '../../vuex/modules/project-management/ContractRegisterGetters'

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
			enterguard:getContractPaymentDetailEnterGuard
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
			this.$router.go({ name:this.enterguard.router});
			//this.$router.go({ name:'contract_payment'});
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
