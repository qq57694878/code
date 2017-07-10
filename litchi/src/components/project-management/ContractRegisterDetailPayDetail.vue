<template>
<div class="row">
	<div class="col-xs-12">

		<validator name="validation">
	
		<form class="form-horizontal" role="form" id="myform">
			
			<div class="form-group">
				<div class="col-md-12" v-if="enterguard.state=='readonly'" >
					<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 付款条件</label>
					<div class="col-sm-8">
						<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="termsdata" >
					</div>
				</div>
				<div class="col-md-6" v-else >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 付款条件</label>
					<div class="col-sm-8">
						<input type="text" id="form-field-1" name="terms" placeholder="" class="col-xs-12" v-model="termsdata" v-validate:terms="['required']" >
					</div>
					<div class="help-block"> <span v-if="$validation.terms.required" style="color:#d16e6c">*必填</span> </div>
				</div>
				
			</div>
			
			<div class="form-group">
				<div class="col-md-6" v-if="enterguard.state=='readonly'" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 预计付款时间</label>
					<div class="col-sm-8">
						<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="timedata">
					</div>
				</div>
				<div class="col-md-6" v-else >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 预计付款时间</label>
					<div class="col-sm-8">
						<input type="text" class="form-control date-picker"class="col-sm-12" name="time" placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="timedata" v-validate:time="['required']" >
					</div>
					<div class="help-block"> <span v-if="$validation.time.required" style="color:#d16e6c">*必填</span> </div>
				</div>
			
				<div class="col-md-6" v-if="enterguard.state=='readonly'" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 付款金额</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="amountdata">
					</div>
				</div>
				<div class="col-md-6" v-else >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 付款金额</label>
					<div class="col-sm-8">
					<input type="text" name="amount" id="form-field-1" placeholder="" class="col-xs-12" v-model="amountdata" v-validate:amount="['required']" >
					</div>
					<div class="help-block"> <span v-if="$validation.amount.required" style="color:#d16e6c">*必填</span> </div>
				</div>
				
			</div>
			
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

					<!-- <button class="btn btn-info pull-right" type="button"  v-if="purchasedetailenterguard.state!='readonly'" v-on:click="enter"> -->
					<!-- 	<1!-- <i class="ace-icon fa fa-undo bigger-110"></i> --1> -->
					<!-- 	进入合同管理 -->
					<!-- </button> -->
				</div>
			</div>


		</form>
	
		</validator>
	</div>
</div>
</template>

<script>
import {getOneContractDetail,getOneContractDetailPayDetailEnterGuard} from '../../vuex/modules/project-management/ContractRegisterGetters'		

import {addContractRegisterDetailPayment } from '../../vuex/modules/project-management/ContractRegisterActions'

export default {
	data:function(){
		return {
			termsdata:'',
			timedata:'',
			amountdata:'',
		}
	},
	vuex:{
		getters:{
			detail:getOneContractDetail,
			enterguard:getOneContractDetailPayDetailEnterGuard
		},
		actions:{
			addContractRegisterDetailPayment
		}
	},
	methods:{
		save:function(){
			var paymentdata ={
				terms:this.termsdata,
				estimated_amount:this.amountdata,
				amount:'',
				estimated_date:this.timedata,
				paydate:'',
				invoice_no:'',
				invoice_file:
				{
					file_id:'',
					file_name:'',
					file_type:'',
					url:'',
					creator_id:'',
					creator_name:'',
					cdate:'',
					},
				check_no:'',
				check_file:
				{
					file_id:'',
					file_name:'',
					file_type:'',
					url:'',
					creator_id:'',
					creator_name:'',
					cdate:'',
				},
				status:'0',
			}

			this.addContractRegisterDetailPayment(paymentdata);
			this.$router.go({ name:'contract_register_detail_payadd'});
		},
		back:function(){
			this.$router.go({ name:'contract_register_detail_payadd'});

			 }
	},
	ready:function(){	

		 let self=this;
		//datepicker plugin
		$('.date-picker').datepicker({
			autoclose: true,
			todayHighlight: true
		}) .next().on(ace.click_event, function(){
			$(this).prev().focus();
		});

	    $('input-daterange').datepicker({autoclose:true});

		if(this.enterguard.payindex != -1){
			console.debug("=========indexdata====payindex==============================="+ this.enterguard.payindex);
			var arr = this.detail.payment;
			this.termsdata = arr[this.enterguard.payindex].terms;
			this.amountdata = arr[this.enterguard.payindex].estimated_amount;
			this.timedata = arr[this.enterguard.payindex].estimated_date;

		}

	}

}

