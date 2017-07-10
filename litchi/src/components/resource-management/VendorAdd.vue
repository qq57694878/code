<template>
<div class="page-header">
	<h1>
		外协单位登记	
	<small style="font-size=16px"> <i class="ace-icon fa fa-angle-double-right"></i> 新建/修改页 </small>
	</h1>
</div>
<div class="row">
	<div class="col-xs-12">
	<!--  ===================== separate line ===================== -->
		
	<div class="row" >
		<div class="col-xs-12">
			 <my-alert v-bind:alert-type="alertType" v-bind:content="alertContent" v-bind:show.sync="alertShow"></my-alert>
		</div>
	</div>
		
	<validator name="validation">
	<form class="form-horizontal" role="form" id="myform">
		
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 企业名称</label>
			<div class="col-sm-6">
			<input type="text" id="form-field-1" name="vendor_name" placeholder="" class="col-sm-12" v-model="vendordetail.vendor_name" v-validate:vendor_name="['required']">
			</div>
			<div class="help-block"> <span v-if="$validation.vendor_name.required" style="color:#d16e6c">*必填</span> </div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 企业性质</label>
			<div class="col-sm-6">
			<input type="text" id="form-field-1" placeholder="" class="col-sm-12" v-model="vendordetail.property">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 经营范围</label>
			<div class="col-sm-6">
			<input type="text" id="form-field-1" placeholder="" class="col-sm-12" v-model="vendordetail.scope">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 成立时间</label>
			<div class="col-sm-6">
				<input type="text" class="form-control date-picker"class="col-sm-12"  placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="vendordetail.register_date">
			<!-- <input type="text" id="form-field-1" placeholder="" class="col-sm-12" v-model="vendordetail.register_date"> -->
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 注册资金</label>
			<div class="col-sm-6">
			<input type="text" id="form-field-1" placeholder="" class="col-sm-12" v-model="vendordetail.register_money">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 员工人数</label>
			<div class="col-sm-6">
			<input type="text" id="form-field-1" placeholder="" class="col-sm-12" v-model="vendordetail.num_people">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 驻场负责人</label>
			<div class="col-sm-6">
			<input type="text" id="form-field-1" placeholder="" class="col-sm-12" v-model="vendordetail.manager">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 联系电话</label>
			<div class="col-sm-6">
			<input type="text" id="form-field-1" placeholder="" class="col-sm-12" v-model="vendordetail.tel">
			</div>
		</div>
		
		<div class="space-4"></div>
		<div class="clearfix form-actions">
			<div class="col-md-offset-2 col-md-10">
				<button class="btn btn-info" type="button" v-on:click="submit">
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
	</form>
	</validator>
	<!--  ===================== separate line ===================== -->
		
	</div>
</div>
</template>
		
<script>
import myAlert from '../common/myAlert'
import {getOneVendorDetail} from '../../vuex/modules/resource-management/VendorGetters'
import {saveOneVendor} from '../../vuex/modules/resource-management/VendorActions'
		
export default {
	data:function(){
		return {
			alertShow:false,
			alertType:'alert-success',
			alertContent:'aaaa'
		}
	},
	components: {
		myAlert
	},
	vuex:{
		getters:{
			vendordetail:getOneVendorDetail
			//CT:getCodeTable
		},
		actions:{
			saveOneVendor
		}
	},
	methods:{
		back:function(){
			//console.debug('myform go');
				this.$router.go({
                                  name:'vendor'});
		},
		submit:function(){
			if(this.$validation.valid){
			let self=this;
			let requestBody={ id:self.vendordetail.id,org_id:self.vendordetail.org_id,vendor_name:self.vendordetail.vendor_name,manager:self.vendordetail.manager,tel:self.vendordetail.tel,property:self.vendordetail.property,scope:self.vendordetail.scope,register_date:self.vendordetail.register_date,register_money:self.vendordetail.register_money,num_people:self.vendordetail.num_people};
			//this.saveOneRoom(self,self.roomdetail.id,self.roomdetail.name,self.roomdetail.description,self.roomdetail.area,self.roomdetail.complete_date);
			this.saveOneVendor(self,requestBody);
		}
			   }
	},
	ready:function(){
		 console.debug('AAAAAAAAAAAAAAAAAAAAAAAA');
		
		//datepicker plugin
		$('.date-picker').datepicker({
			autoclose: true,
			todayHighlight: true
		}) .next().on(ace.click_event, function(){
			$(this).prev().focus();
		});

		  $('input-daterange').datepicker({autoclose:true});
				  
		   //to translate the daterange picker, please copy the "examples/daterange-fr.js" contents here before initialization
		  /*
		   $('input[name=date-range-picker]').daterangepicker({
					  'applyClass' : 'btn-sm btn-success',
					  'cancelClass' : 'btn-sm btn-default',
					  locale: {
						   applyLabel: 'Apply',
						   cancelLabel: 'Cancel',
					   }
				   })
		   .prev().on(ace.click_event, function(){
					   $(this).next().focus();
				   });*/
		  }
}
