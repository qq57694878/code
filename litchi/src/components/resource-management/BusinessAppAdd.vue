
<template>
<div class="page-header">
	<h1>
		业务系统	
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
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 业务系统名称</label>
			<div class="col-sm-6">
			<input type="text" name="biz_name" id="form-field-1" placeholder="" class="col-sm-12" v-model="appdetail.name" v-validate:biz_name="['required']">
			</div>
			<div class="help-block"> <span v-if="$validation.biz_name.required" style="color:#d16e6c">*必填</span> </div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 系统使用部门</label>
			<div class="col-sm-6">
				<!-- <select id="food" placeholder="" class="multiselect" multiple style="display:none;" > -->
				<select id="multiselect" class="multiselect" v-model="appdetail.org_id_list" multiple>
				<option  v-for="org in orglist.list_data "  value="{{org.code}}">{{org.value}}</option>
				</select>
							
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 建设单位</label>
			<div class="col-sm-6">
				<select class="form-control" placeholder="" v-model="appdetail.vendor_id">
				<option  v-for="vendor in vendorlist.list_data "  value="{{vendor.code}}">{{vendor.value}}</option>
				</select>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 系统上线时间</label>
			<div class="col-sm-6">
				<input type="text" class="form-control date-picker"class="col-sm-12"  placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="appdetail.online_time">
			<!-- <input type="text" id="form-field-1" placeholder="" class="col-sm-12" v-model="appdetail.online_time"> -->
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 业务描述</label>
			<div class="col-sm-6">
			<input type="text" id="form-field-1" placeholder="" class="col-sm-12" v-model="appdetail.description">
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
import {getOneBizAppDetail} from '../../vuex/modules/resource-management/BusinessAppGetters'
import {getVendorDropdownList} from '../../vuex/modules/resource-management/VendorGetters'
import {getBizOrgList} from '../../vuex/modules/resource-management/OrgGetters'
		
import {reqVendorDropdownList} from '../../vuex/modules/resource-management/VendorActions'
import {reqBizOrgList} from '../../vuex/modules/resource-management/OrgActions'
import {saveOneBizApp} from '../../vuex/modules/resource-management/BusinessAppActions'
		
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
			appdetail:getOneBizAppDetail,
			vendorlist:getVendorDropdownList,
			orglist:getBizOrgList
			//CT:getCodeTable
		},
		actions:{
			reqVendorDropdownList,
			reqBizOrgList,
			saveOneBizApp
		}
	},
	methods:{
		back:function(){
			//console.debug('myform go');
				this.$router.go({
                                  name:'businessapp'});
		},
		submit:function(){
			//if(this.appdetail.name!=''){
			if(this.$validation.valid){
				let self=this;

				let requestBody={ id:self.appdetail.id,name:self.appdetail.name,org_id_list:self.appdetail.org_id_list,vendor_id:self.appdetail.vendor_id,online_time:self.appdetail.online_time,description:self.appdetail.description};
			// console.debug(requestBody);
				this.saveOneBizApp(self,requestBody);
			}
			//else{
			//	bootbox.alert("系统名称不能为空！");
			//}
		}
	},
	ready:function(){	
		//datepicker plugin
		$('.date-picker').datepicker({
			autoclose: true,
			todayHighlight: true
		}) .next().on(ace.click_event, function(){
			$(this).prev().focus();
		});

		  $('input-daterange').datepicker({autoclose:true});

		  //////////////////
//		  $('.multiselect').multiselect({
//				enableFiltering: true,
//				buttonClass: 'btn btn-white btn-primary',
//				templates: {
//				button: '<button type="button" class="multiselect dropdown-toggle" data-toggle="dropdown"></button>',
//				ul: '<ul class="multiselect-container dropdown-menu"></ul>',
//				filter: '<li class="multiselect-item filter"><div class="input-group"><span class="input-group-addon"><i class="fa fa-search"></i></span><input class="form-control multiselect-search" type="text"></div></li>',
//				filterClearBtn: '<span class="input-group-btn"><button class="btn btn-default btn-white btn-grey multiselect-clear-filter" type="button"><i class="fa fa-times-circle red2"></i></button></span>',
//				li: '<li><a href="javascript:void(0);"><label></label></a></li>',
//				divider: '<li class="multiselect-item divider"></li>',
//				liGroup: '<li class="multiselect-item group"><label class="multiselect-group"></label></li>'
//				}
//		});
		this.reqVendorDropdownList();
		this.reqBizOrgList(this,function(comp) {
			comp.$nextTick(function(){
				  $('.multiselect').multiselect({
						enableFiltering: true,
						buttonClass: 'btn btn-white btn-primary',
						templates: {
						button: '<button type="button" class="multiselect dropdown-toggle" data-toggle="dropdown"></button>',
						ul: '<ul class="multiselect-container dropdown-menu"></ul>',
						filter: '<li class="multiselect-item filter"><div class="input-group"><span class="input-group-addon"><i class="fa fa-search"></i></span><input class="form-control multiselect-search" type="text"></div></li>',
						filterClearBtn: '<span class="input-group-btn"><button class="btn btn-default btn-white btn-grey multiselect-clear-filter" type="button"><i class="fa fa-times-circle red2"></i></button></span>',
						li: '<li><a href="javascript:void(0);"><label></label></a></li>',
						divider: '<li class="multiselect-item divider"></li>',
						liGroup: '<li class="multiselect-item group"><label class="multiselect-group"></label></li>'
						},
					  onChange: function(element, checked) {
						  var brands = $('#multiselect option:selected');
						  var selected = [];
						  $(brands).each(function(index, brand){
							  selected.push($(this).val());
						  });
						  // comp.appdetail.org_id_list=selected;
						  // console.log(selected);
					  }
				  });
			});
		});
	}
}
