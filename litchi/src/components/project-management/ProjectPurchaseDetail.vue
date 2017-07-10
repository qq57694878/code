
<template>
<div class="page-header">
	<h1>
		项目采购管理	
	<small style="font-size=16px"> <i class="ace-icon fa fa-angle-double-right"></i> 详细页 </small>
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
		
	<form class="form-horizontal" role="form" id="myform">
	<!-- <form  role="form" id="myform"> -->
		
		<div class="form-group">
			<div class="col-md-6">
				<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 项目名称</label>
				<div class="col-sm-8">
				<input type="text" readonly  id="form-field-1" placeholder="" class="col-xs-12" v-model="projectdetail.name" >
				</div>
			</div>
		
			<div class="col-md-6">
				<label class="col-sm-2 control-label no-padding-right" for="form-field-2"> 申请部门</label>
				<div class="col-sm-8">
				<input type="text" readonly id="form-field-2" placeholder="" class="col-xs-12" v-model="projectdetail.apply_dept_name" >
				</div>
			</div>
		
		</div>
		
		<div class="form-group">
			<div class="col-md-6">
				<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 概算总投资</label>
				<div class="col-sm-8">
				<input type="text" readonly id="form-field-1" placeholder="" class="col-xs-12" v-model="projectdetail.total_investment">
				</div>
			</div>
		
			<div class="col-md-6">
				<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 未分解额度</label>
				<div class="col-sm-8">
				<input type="text" readonly id="form-field-1" placeholder="" class="col-xs-12" v-model="projectdetail.balance_investment" >
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-1 control-label text-align-left no-padding-right" > 项目分解计划</label>
		</div>
	
		<hr>
		<!-- <div class="space-4"></div> -->
		<!-- <div class="hr hr32 hr-dotted"></div> -->
	
	</form>
	<!--  ===================== separate line ===================== -->
		
	<div>
		<router-view></router-view>
	</div>
		
	</div>
</div>
</template>
		
<script>
import myAlert from '../common/myAlert'
		
import {getOneProjectDetail} from '../../vuex/modules/project-management/ProjectApplyGetters'
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
			projectdetail:getOneProjectDetail
		},
		actions:{
		}
	},
	methods:{
	},
	ready:function(){	

	}
}
</script>

<style scoped>
.text-align-left{
	text-align:left;
}
</style>
