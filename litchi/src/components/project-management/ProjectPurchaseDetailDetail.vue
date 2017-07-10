<template>
<div class="row" >
	<div class="col-xs-12">
		 <my-alert v-bind:alert-type="alertType" v-bind:content="alertContent" v-bind:show.sync="alertShow"></my-alert>
	</div>
</div>
<form class="form-horizontal" role="form" id="myform">
	<div class="form-group">
		<div class="col-md-12">
			<label class="col-sm-1 control-label no-padding-right"> 采购方式</label>
			<div class="col-sm-9">
				<div class="radio col-xs-2 no-margin-top" v-for="(index,methodtype) in CT.procurementMethodtype">
					<label>
						<input name="form-field-radio" type="radio" class="ace" value = {{index}} v-model="purchasedetail.procurement_method"/>
						<span class="lbl" for={{index}} >{{methodtype}}</span>
					</label>
				</div>
				<!-- <div class="radio col-xs-2 no-margin-top"> -->
				<!-- 	<label> -->
				<!-- 		<input name="form-field-radio" type="radio" class="ace" /> -->
				<!-- 		<span class="lbl">公开招标</span> -->
				<!-- 	</label> -->
				<!-- </div> -->
				<!-- <div class="radio col-xs-2 no-margin-top"> -->
				<!-- 	<label> -->
				<!-- 		<input name="form-field-radio" type="radio" class="ace" /> -->
				<!-- 		<span class="lbl">邀请招标</span> -->
				<!-- 	</label> -->
				<!-- </div> -->
				<!-- <div class="radio col-xs-2 no-margin-top"> -->
				<!-- 	<label> -->
				<!-- 		<input name="form-field-radio" type="radio" class="ace" /> -->
				<!-- 		<span class="lbl">竞争性谈判</span> -->
				<!-- 	</label> -->
				<!-- </div> -->
				<!-- <div class="radio col-xs-2 no-margin-top"> -->
				<!-- 	<label> -->
				<!-- 		<input name="form-field-radio" type="radio" class="ace" /> -->
				<!-- 		<span class="lbl">单一来源</span> -->
				<!-- 	</label> -->
				<!-- </div> -->
				<!-- <div class="radio col-xs-2 no-margin-top"> -->
				<!-- 	<label> -->
				<!-- 		<input name="form-field-radio" type="radio" class="ace" /> -->
				<!-- 		<span class="lbl">询价</span> -->
				<!-- 	</label> -->
				<!-- </div> -->
			</div>
		</div>
	</div>
	<div class="space-4"></div>		
		
	<div class="form-group">
		<div class="col-md-6">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 采购项目名称</label>
			<div class="col-sm-8">
				<input type="text" v-if="purchasedetailenterguard.state=='readonly'" readonly id="form-field-1" placeholder="" class="col-xs-12" v-model="purchasedetail.name" >
				<input type="text" id="form-field-1" placeholder="" class="col-xs-12" v-model="purchasedetail.name" v-else >
			</div>
		</div>
	
		<div class="col-md-6">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-2"> 项目责任人</label>
			<div class="col-sm-8">
					<combobox-organization v-bind:query="treeQuery" v-if="purchasedetailenterguard.state!='readonly'" v-bind:selectid="purchasedetail.responsible_person_id" v-bind:selectname="purchasedetail.responsible_person" v-on:error="treeError" v-on:change="treeChange"></combobox-organization>
					<input type="text"  v-if="purchasedetailenterguard.state=='readonly'" readonly id="form-field-2" placeholder="" class="col-xs-12" v-model="purchasedetail.responsible_person" >
			</div>
		</div>
	
	</div>
	
	<div class="form-group">
		<div class="col-md-6">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 预算金额</label>
			<div class="col-sm-8">
			<input type="text"  v-if="purchasedetailenterguard.state=='readonly'" readonly id="form-field-1" placeholder="" class="col-xs-12" v-model="purchasedetail.budget_amount">
			<input type="text" id="form-field-1" placeholder="" class="col-xs-12" v-model="purchasedetail.budget_amount" v-else >
			</div>
		</div>
	
		<div class="col-md-6">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 招标代理公司</label>
			<div class="col-sm-8">
			<input type="text"  v-if="purchasedetailenterguard.state=='readonly'" readonly id="form-field-1" placeholder="" class="col-xs-12" v-model="purchasedetail.bidding_company" >
			<input type="text" id="form-field-1" placeholder="" class="col-xs-12" v-model="purchasedetail.bidding_company" v-else >
			</div>
		</div>
	</div>
	
	<!-- <div class="form-group"> -->
	<!-- 	<div class="col-md-6"> -->
	<!-- 	<label class="col-sm-2 control-label no-padding-right" > 招标相关附件</label> -->
	<!-- 	</div> -->
	<!-- </div> -->
	<div class="form-group">
		<div class="col-md-12">
		<label class="col-sm-1 control-label no-padding-right" > 招标相关附件</label>
		<!-- <div class="col-sm-1"> -->
		<!-- </div> -->
		<div class="col-sm-10">
		<!--  ===================== separate line ===================== -->
		<attach-table v-bind:codetable="CT.procurementAttchmentType" v-if="purchasedetailenterguard.state=='readonly'" readonly="1" v-bind:attachments="purchasedetail.attachments" v-on:getuploads="getuploads" v-on:error="fileInputError"></attach-table>
		<attach-table v-bind:codetable="CT.procurementAttchmentType" v-bind:attachments="purchasedetail.attachments" v-on:getuploads="getuploads" v-on:error="fileInputError"v-else ></attach-table>

		</div>
		<!--  ===================== separate line ===================== -->
	</div>
	</div>


	<!-- <hr> -->

	<div class="space-4"></div>		
	<div class="form-group">
		<div class="col-md-6">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 挂标日期</label>
			<div class="col-sm-8">
				<input type="text" class="form-control date-picker"class="col-sm-12" v-if="purchasedetailenterguard.state!='readonly'" placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="purchasedetail.bidding_hang_date">
				<input type="text"  v-if="purchasedetailenterguard.state=='readonly'" readonly id="form-field-1" placeholder="" class="col-xs-12" v-model="purchasedetail.bidding_hang_date">
			</div>
		</div>
	
		<div class="col-md-6">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 报名截止日期</label>
			<div class="col-sm-8">
				<input type="text" class="form-control date-picker"class="col-sm-12" v-if="purchasedetailenterguard.state!='readonly'" placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="purchasedetail.signup_end_date">
				<input type="text"  v-if="purchasedetailenterguard.state=='readonly'" readonly id="form-field-1" placeholder="" class="col-xs-12" v-model="purchasedetail.signup_end_date" >
			</div>
		</div>
	</div>

	<div class="form-group">
		<div class="col-md-6">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 开标日期</label>
			<div class="col-sm-8">
				<input type="text" class="form-control date-picker"class="col-sm-12" v-if="purchasedetailenterguard.state!='readonly'" placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="purchasedetail.bidding_open_date">
				<input type="text"  v-if="purchasedetailenterguard.state=='readonly'" readonly id="form-field-1" placeholder="" class="col-xs-12" v-model="purchasedetail.bidding_open_date">
			</div>
		</div>
	
		<div class="col-md-6">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 合同备案截止</label>
			<div class="col-sm-8">
				<input type="text" class="form-control date-picker"class="col-sm-12" v-if="purchasedetailenterguard.state!='readonly'" placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="purchasedetail.contract_record_date">
				<input type="text"  v-if="purchasedetailenterguard.state=='readonly'" readonly id="form-field-1" placeholder="" class="col-xs-12" v-model="purchasedetail.contract_record_date" >
			</div>
		</div>
	</div>

	<hr>
	<div class="form-group">
		<div class="col-md-6">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 中标日期</label>
			<div class="col-sm-8">
				<input type="text" class="form-control date-picker"class="col-sm-12" v-if="purchasedetailenterguard.state!='readonly'" placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="purchasedetail.bidding_win_date">
				<input type="text"  v-if="purchasedetailenterguard.state=='readonly'" readonly id="form-field-1" placeholder="" class="col-xs-12" v-model="purchasedetail.bidding_win_date">
			</div>
		</div>
	
		<div class="col-md-6">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 中标单位</label>
			<div class="col-sm-8">
			<input type="text"  v-if="purchasedetailenterguard.state=='readonly'" readonly id="form-field-1" placeholder="" class="col-xs-12" v-model="purchasedetail.bidding_win_company" >
			<input type="text" id="form-field-1" placeholder="" class="col-xs-12" v-model="purchasedetail.bidding_win_company"  v-else >
			</div>
		</div>
	</div>

	<div class="form-group">
		<div class="col-md-6">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 中标金额</label>
			<div class="col-sm-8">
			<input type="text"  v-if="purchasedetailenterguard.state=='readonly'" readonly id="form-field-1" placeholder="" class="col-xs-12" v-model="purchasedetail.bid_amount">
			<input type="text" id="form-field-1" placeholder="" class="col-xs-12" v-model="purchasedetail.bid_amount" v-else >
			</div>
		</div>
	</div>

	<div class="form-group">
		<div class="col-md-6">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 中标通知书</label>
			<div class="col-sm-8">
				<file-input v-bind:attach-code="" v-bind:file-info="purchasedetail.bid_notification" v-if="purchasedetailenterguard.state=='readonly'" v-bind:readonly="1" v-on:upload="bidUpload" v-on:remove="bidRemove" v-on:error="bidError"></file-input> 
				<file-input v-bind:attach-code="" v-bind:file-info=""  v-on:upload="bidUpload" v-on:remove="bidRemove" v-on:error="bidError" v-else ></file-input> 
			</div>
		</div>
	</div>
	<div class="clearfix no-padding-left form-actions">
			<!-- <div class="col-md-offset-2 col-md-10"> -->
			<div class="col-md-12 no-padding-left">
				<button class="btn btn-info" type="button"  v-if="purchasedetailenterguard.state!='readonly'" v-on:click="save">
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
</template>

<script>		
import AttachTable from '../common/AttachTable'
import FileInput from '../common/FileInput'
import ComboboxOrganization from '../common/ComboboxOrganization'
import myAlert from '../common/myAlert'
import {getOnePurchaseDetail,getOnePurchaseDetailEnterGuard} from '../../vuex/modules/project-management/ProjectPurchaseGetters'
import {reqOneProjectProcurementDetail,saveOneProjectProcurementDetail } from '../../vuex/modules/project-management/ProjectPurchaseActions'
import {getCodeTable} from '../../vuex/modules/common/CodeTableGetters'
export default {
	data:function(){
		return {
			personid:'',  
			alertShow:false,
			alertType:'alert-success',
			alertContent:'aaaa',
			attach:{},
			bidnotification:'',
			bid_attach_code:'',
			bid_file_id:'',
			treeQuery:{ root:{code:'xxzx'} }
		}
	},
	components: {
		myAlert,
		ComboboxOrganization,
		FileInput,
		AttachTable
	},
	vuex:{
		getters:{
			purchasedetail:getOnePurchaseDetail,
			purchasedetailenterguard:getOnePurchaseDetailEnterGuard,
			CT:getCodeTable
		},
		actions:{
			reqOneProjectProcurementDetail,
			saveOneProjectProcurementDetail
		}
	},
	methods:{
		bidUpload:function(fileId,attachCode){
			this.bid_file_id = fileId;
			this.bid_attach_code = attachCode;
		},
		bidRemove:function(fileId,attachCode){
					  if(this.bid_file_id = fileId){
						  this.bid_file_id = '';
						  this.bid_attach_code = '';
					  }
		},
		bidError:function(errMsg){
					   },
		getuploads:function(uploads){
		   this.attach = uploads;
			console.debug(uploads);
		},
		fileInputError:function(errMsg){
			//TODO 此处增加错误提示alert 显示内容为errMsg
			console.error(errMsg);
		},
		treeChange:function(id){
			console.debug("==============treechange=============================="+id);
			this.personid = id; 
	    },
		treeError:function(errMsg){
			//TODO 此处增加错误提示alert 显示内容为errMsg
			console.error(errMsg);
		},
		back:function(){
			this.$router.go({ name:'project_purchase_detail_add'});
		},
		save:function(){
			var self=this;
			let requestBody={ id:self.purchasedetail.id,pid:self.purchasedetail.pid,name:self.purchasedetail.name,budget_amount:self.purchasedetail.budget_amount,responsible_person_id:self.personid,procurement_method:self.purchasedetail.procurement_method,bidding_hang_date:self.purchasedetail.bidding_hang_date,bidding_open_date:self.purchasedetail.bidding_open_date,bidding_win_date:self.purchasedetail.bidding_win_date,bid_amount:self.purchasedetail.bid_amount,signup_end_date:self.purchasedetail.signup_end_date,contract_record_date:self.purchasedetail.contract_record_date,bidding_win_company:self.purchasedetail.bidding_win_company,bidding_company:self.purchasedetail.bidding_company,bid_notification:self.bid_file_id,attachments:self.attach};
			this.saveOneProjectProcurementDetail(self,requestBody,function(){
				self.$router.go({ name:'project_purchase_detail_add'});
			});
		},
		enter:function(){
			this.$router.go({ name:'project_purchase_detail_add'});
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


//	console.debug('====this.purchasedetailenterguard.============================',this.purchasedetailenterguard.state);
//	console.debug('====this.purchasedetailenterguard.===id=========================',this.purchasedetailenterguard.purchaseid);
		  if(this.purchasedetailenterguard.purchaseid != ''){
				this.reqOneProjectProcurementDetail(this.purchasedetailenterguard.purchaseid,function(){
						self.$nextTick(function(){
							self.$broadcast('done');
						})
					});
		  }else{
			this.$broadcast('done');
		  }

	}
}
</script>

<style scoped>
.text-align-left{
	text-align:left;
}
.text-align-center{
	text-align:center;
}
.text-align-right{
	text-align:right;
}
.no-margin-top{
	margin-top:0px;
}
.padding-right-70{
	padding-right:70px;
}
.padding-left-130{
	padding-left:130px;
}
.padding-right-130{
	padding-right:130px;
}
</style>
