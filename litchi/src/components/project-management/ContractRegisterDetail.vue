<template>
<div class="page-header">
	<h1>
		合同登记	
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
				<div class="col-md-6" v-if="enterguard.state=='readonly'" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 采购项目名称</label>
					<div class="col-sm-8">
						<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.procurement_name" >
					</div>
				</div>
				<div class="col-md-6" v-else >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 采购项目名称</label>
					<div class="col-sm-8">
						<combobox placeholder="" name="purchase_name" v-bind:model="detail.pid" v-bind:items="purchases" v-on:change="purchasechange" v-validate:purchase_name="['required']" ></combobox>
					</div>
					<div class="help-block"> <span v-if="$validation.purchase_name.required" style="color:#d16e6c">*必填</span> </div>
				</div>
			
				<div class="col-md-6" v-if="enterguard.state=='readonly'" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 合同名称</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.name">
					</div>
				</div>
				<div class="col-md-6" v-else >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 合同名称</label>
					<div class="col-sm-8">
					<input type="text" name="contract_name" id="form-field-1" placeholder="" class="col-xs-12" v-model="detail.name" v-validate:contract_name="['required']" >
					</div>
					<div class="help-block"> <span v-if="$validation.contract_name.required" style="color:#d16e6c">*必填</span> </div>
				</div>
				
			</div>
			
			<div class="form-group">
				<div class="col-md-6" v-if="enterguard.state=='readonly'" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 中标公司</label>
					<div class="col-sm-8">
						<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.vender_name" >
					</div>
				</div>
				<div class="col-md-6" v-else >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 中标公司</label>
					<div class="col-sm-8">
						<combobox placeholder="" name="vender_name" v-bind:model="detail.vender_id" v-bind:items="venders" v-on:change="venderchange" v-validate:vender_name="['required']" ></combobox>
						<!-- <combobox placeholder="" v-bind:items="venders" v-on:change="venderchange"></combobox> -->
					</div>
					<div class="help-block"> <span v-if="$validation.vender_name.required" style="color:#d16e6c">*必填</span> </div>
				</div>
			
				<div class="col-md-6" v-if="enterguard.state=='readonly'" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 合同金额</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.amount">
					</div>
				</div>
				<div class="col-md-6" v-else >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 合同金额</label>
					<div class="col-sm-8">
					<input type="text" name="contract_name" id="form-field-1" placeholder="" class="col-xs-12" v-model="detail.amount" v-validate:contract_name="['required']" >
					</div>
					<div class="help-block"> <span v-if="$validation.contract_name.required" style="color:#d16e6c">*必填</span> </div>
				</div>
				
			</div>
		

			<div class="form-group">
				<div class="col-md-12" v-if="enterguard.state=='readonly'" >
					<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 合同有效期</label>
					<div class="col-sm-8">
						<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-2" v-model="detail.sdate" >
						<label class="col-xs-1 control-label text-align-center no-padding-right" for="form-field-1"> 至</label>
						<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-2" v-model="detail.edate" >
					</div>
				</div>
				<div class="col-md-12" v-else >
					<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 合同有效期</label>
					<div class="col-sm-8">
						<div class="col-xs-2 no-padding-left">
							<input type="text" class="form-control date-picker"class="col-xs-12" name="contract_sdate" placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="detail.sdate" v-validate:contract_sdate="['required']" >
						</div>
						<div class="help-block col-xs-1 no-padding-right"> <span v-if="$validation.contract_sdate.required" style="color:#d16e6c">*必填</span> </div>
						<label class="col-xs-1 control-label text-align-center margin-top-5px no-padding-top no-padding-left no-padding-right" for="form-field-1"> 至</label>
						<div class="col-xs-2 no-padding-left">
							<input type="text"  class="form-control date-picker" class="col-xs-12" data-date-format="yyyy-mm-dd" name="contract_edate" placeholder="" v-model="detail.edate" v-validate:contract_edate="['required']" >
						</div>
						<div class="help-block col-xs-1"> <span v-if="$validation.contract_edate.required" style="color:#d16e6c">*必填</span> </div>
					</div>
					<!-- <div class="help-block"> <span v-if="$validation.contract_edate.required" style="color:#d16e6c">*必填</span> </div> -->
				</div>

			</div>
			
			<div class="form-group">
			
				<div class="col-md-6" v-if="enterguard.state=='readonly'" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 合同编号</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.cno">
					</div>
				</div>
				<div class="col-md-6" v-else >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 合同编号</label>
					<div class="col-sm-8">
					<input type="text" name="contract_cno" id="form-field-1" placeholder="" class="col-xs-12" v-model="detail.cno" v-validate:contract_cno="['required']" >
					</div>
					<div class="help-block"> <span v-if="$validation.contract_cno.required" style="color:#d16e6c">*必填</span> </div>
				</div>	
			
				<div class="col-md-6" v-if="enterguard.state=='readonly'" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 监理公司</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.supervision">
					</div>
				</div>
				<div class="col-md-6" v-else >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 监理公司</label>
					<div class="col-sm-8">
					<input type="text" name="contract_name" id="form-field-1" placeholder="" class="col-xs-12" v-model="detail.supervision" v-validate:contract_name="['required']" >
					</div>
					<div class="help-block"> <span v-if="$validation.contract_name.required" style="color:#d16e6c">*必填</span> </div>
				</div>

				
			</div>

			<div class="form-group">
				<div class="col-md-12" >
					<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 合同影印件</label>
					<div class="col-sm-1">
						<file-input v-bind:attach-code="" v-bind:file-info="detail.contract_file" v-if="enterguard.state=='readonly'" v-bind:readonly="1" v-on:upload="contractUpload" v-on:remove="contractRemove" v-on:error="contractError"></file-input> 
						<file-input v-bind:attach-code="" v-bind:file-info="detail.contract_file" v-on:upload="contractUpload" v-on:remove="contractRemove" v-on:error="contractError" v-else ></file-input> 
					</div>
				</div>
			</div>
		
			<div class="form-group">
			
				<div class="col-md-6" v-if="enterguard.state=='readonly'" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 合同责任人</label>
					<div class="col-sm-8">
						<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.responsible_person_name">
					</div>
				</div>
				<div class="col-md-6" v-else >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 合同责任人</label>
					<div class="col-sm-8">
						<combobox-organization  v-bind:query="treeQuery" v-if="enterguard.state!='readonly'" v-bind:selectid="detail.responsible_person_id" v-bind:selectname="detail.responsible_person_name" v-on:error="treeError" v-on:change="treeChange" ></combobox-organization>
						<!-- <combobox-organization name="contract_responsible_person" v-bind:query="treeQuery" v-if="enterguard.state!='readonly'" v-bind:selectid="detail.responsible_person_id" v-bind:selectname="projectdetail.responsible_person_name" v-on:error="treeError" v-on:change="treeChange" v-validate:contract_responsible_person="['required']" ></combobox-organization> -->
					</div>
					<!-- <div class="help-block"> <span v-if="$validation.contract_responsible_person.required" style="color:#d16e6c">*必填</span> </div> -->
				</div>	
			
				<div class="col-md-6" v-if="enterguard.state=='readonly'" >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 预计验收时间</label>
					<div class="col-sm-8">
					<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="detail.estimated_reception_time">
					</div>
				</div>
				<div class="col-md-6" v-else >
					<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 预计验收时间</label>
					<div class="col-sm-8">
						<input type="text" class="form-control date-picker"class="col-sm-12" name="estimated_reception_time" placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="detail.estimated_reception_time" v-validate:estimated_reception_time="['required']" >
					</div>
					<div class="help-block"> <span v-if="$validation.estimated_reception_time.required" style="color:#d16e6c">*必填</span> </div>
				</div>
			</div>

		</form>
		
		<hr>
		<div class="space-4"></div>
		<div>
			<router-view v-on:alertmsg="childalert" ></router-view>
		</div>
	
		</validator>
	</div>
</div>
</template>

<script>
import Combobox from '../common/Combobox'
import FileInput from '../common/FileInput'
import myAlert from '../common/myAlert'
import ComboboxOrganization from '../common/ComboboxOrganization'
import {reqComboboxData} from '../../vuex/modules/common/ComboboxActions'
import {setContractRegisterDetailContractfileid,setContractRegisterDetailPid,setContractRegisterDetailVendorid,reqContractDetail} from '../../vuex/modules/project-management/ContractRegisterActions'
import {reqAllProcurement} from '../../vuex/modules/project-management/ProjectPurchaseActions'
import {getOneContractDetailEnterGuard,getOneContractDetail} from '../../vuex/modules/project-management/ContractRegisterGetters'		

export default {
	data:function(){
		return {
			purchaseid:'',
			purchases:[],
			venderid:'',
			venders:[],
			responsible_personid:'', 
			alertShow:false,
			alertType:'alert-success',
			alertContent:'aaaa',
			contract_attach_code:'',
			contract_file_id:'',
			treeQuery:{ root:{code:'xxzx'} }
		}
	},
	components: {
		ComboboxOrganization,
		myAlert,
		FileInput,
		Combobox
	},
	vuex:{
		getters:{
			detail:getOneContractDetail,
			enterguard:getOneContractDetailEnterGuard
		},
		actions:{
			reqComboboxData, 
			reqContractDetail,
			setContractRegisterDetailPid,
			setContractRegisterDetailVendorid,
			setContractRegisterDetailContractfileid,
			reqAllProcurement
		}
	},
	methods:{
		contractUpload:function(fileId,attachCode){
			console.debug("========contractUpload===================================");
			console.debug(fileId);
			this.contract_file_id = fileId;
			this.contract_attach_code = attachCode;
			this.setContractRegisterDetailContractfileid(this.contract_file_id);
		},
		contractRemove:function(fileId,attachCode){
					  if(this.contract_file_id = fileId){
						  this.contract_file_id = '';
						  this.contract_attach_code = '';
						this.setContractRegisterDetailContractfileid(this.contract_file_id);
					  }
		},
		contractError:function(errMsg){
					   },
		childalert:function(Value){
			if(Value == 'failure'){
				this.$set('alertType','alert-danger');
				this.$set('alertShow',true);
				this.$set('alertContent','查询失败！');
			}
		   },
		purchasechange:function(Value){
			console.debug("========purchasechange===================================");
			console.debug(Value);
			this.purchaseid=Value;
			this.setContractRegisterDetailPid(Value);
		   },
		venderchange:function(Value){
			console.debug("============venderchange===============================");
			console.debug(Value);
			this.venderid=Value;
			this.setContractRegisterDetailVendorid(Value);
		   },
		treeChange:function(id){
			console.debug("==============treechange=============================="+id);
			this.responsible_personid = id; 
	    },
		treeError:function(errMsg){
			//TODO 此处增加错误提示alert 显示内容为errMsg
			console.error(errMsg);
		},
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

		//this.$broadcast('done');

		this.reqAllProcurement(self,function(comp,projects){
			var arr = projects;
			for(var i=0,length = arr.length;i < length; i++){
				var data = {
								code:arr[i].id,
								value:arr[i].name
							};
				comp.purchases.push(data);
			}
		});
		this.reqComboboxData(this,'sysresource/vendor/get-vendor-list.do',function(comp,items){
			comp.venders=items;
		});
	  if(this.enterguard.contractid != ''){
			  this.reqContractDetail(self,this.enterguard.contractid,function(){
							self.$nextTick(function(){
								self.$broadcast('done');
							})
						});
	  }else{
		this.$broadcast('done');
		  }
//	  if(this.enterguard.contractid != ''){
//			  this.reqContractDetail(self,this.enterguard.contractid);
//	  }
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
