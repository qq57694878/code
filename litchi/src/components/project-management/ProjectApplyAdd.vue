
<template>
<div class="page-header">
	<h1>
		项目立项申请	
	<small style="font-size=16px"> <i class="ace-icon fa fa-angle-double-right"></i> 新建/编辑/详细页 </small>
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
	<!-- <form  role="form" id="myform"> -->
		
		<div class="form-group">
			<div class="col-md-6" v-if="projectaddenterguard.state=='readonly'" >
				<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 年度</label>
				<div class="col-sm-8">
				<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="projectdetail.year" >
				</div>
			</div>
			<div class="col-md-6" v-else >
				<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 年度</label>
				<div class="col-sm-8">
				<input type="text" name="biz_name" id="form-field-1" placeholder="" class="col-xs-12" v-model="projectdetail.year" v-validate:biz_name="['required']" >
				</div>
				<div class="help-block"> <span v-if="$validation.biz_name.required" style="color:#d16e6c">*必填</span> </div>
			</div>
			<!-- <div class="col-md-6"> -->
			<!-- 	<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 年度</label> -->
			<!-- 	<div class="col-sm-8"> -->
			<!-- 	<input type="text" id="form-field-1" v-if="projectaddenterguard.state=='readonly'" readonly placeholder="" class="col-xs-12" v-model="projectdetail.year" > -->
			<!-- 	<input type="text" name="biz_name" id="form-field-1" placeholder="" class="col-xs-12" v-model="projectdetail.year" v-validate:biz_name="['required']" v-else > -->
			<!-- 	</div> -->
			<!-- 	<div class="help-block"> <span v-if="$validation.biz_name.required" style="color:#d16e6c">*必填</span> </div> -->
			<!-- </div> -->
		
			<div class="col-md-6" v-if="projectaddenterguard.state=='readonly'" >
				<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 项目名称</label>
				<div class="col-sm-8">
				<input type="text" id="form-field-1" readonly placeholder="" class="col-xs-12" v-model="projectdetail.name">
				</div>
			</div>
			<div class="col-md-6" v-else >
				<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 项目名称</label>
				<div class="col-sm-8">
				<input type="text" name="project_name" id="form-field-1" placeholder="" class="col-xs-12" v-model="projectdetail.name" v-validate:project_name="['required']" >
				</div>
				<div class="help-block"> <span v-if="$validation.project_name.required" style="color:#d16e6c">*必填</span> </div>
			</div>
			<!-- <div class="col-md-6"> -->
			<!-- 	<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 项目名称</label> -->
			<!-- 	<div class="col-sm-8"> -->
			<!-- 	<input type="text" id="form-field-1" v-if="projectaddenterguard.state=='readonly'" readonly placeholder="" class="col-xs-12" v-model="projectdetail.name"> -->
			<!-- 	<input type="text" name="project_name" id="form-field-1" placeholder="" class="col-xs-12" v-model="projectdetail.name" v-validate:project_name="['required']" v-else > -->
			<!-- 	</div> -->
			<!-- 	<div class="help-block"> <span v-if="$validation.project_name.required" style="color:#d16e6c">*必填</span> </div> -->
			<!-- </div> -->
		
		</div>
		
		<div class="form-group">
			<div class="col-md-6" v-if="projectaddenterguard.state=='readonly'" >
				<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 概算总投资</label>
				<div class="col-sm-8">
				<input type="text" id="form-field-1" readonly class="col-xs-12" v-model="projectdetail.total_investment" >
				</div>
			</div>
			<div class="col-md-6" v-else >
				<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 概算总投资</label>
				<div class="col-sm-8">
				<input type="text" name="total_investment" id="form-field-1" placeholder="" class="col-xs-12" v-model="projectdetail.total_investment" v-validate:total_investment="['required']" >
				</div>
				<div class="help-block"> <span v-if="$validation.total_investment.required" style="color:#d16e6c">*必填</span> </div>
			</div>
			<!-- <div class="col-md-6"> -->
			<!-- 	<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 概算总投资</label> -->
			<!-- 	<div class="col-sm-8"> -->
			<!-- 	<input type="text" id="form-field-1" v-if="projectaddenterguard.state=='readonly'" readonly class="col-xs-12" v-model="projectdetail.total_investment" > -->
			<!-- 	<input type="text" name="total_investment" id="form-field-1" placeholder="" class="col-xs-12" v-model="projectdetail.total_investment" v-validate:total_investment="['required']" v-else > -->
			<!-- 	</div> -->
			<!-- 	<div class="help-block"> <span v-if="$validation.total_investment.required" style="color:#d16e6c">*必填</span> </div> -->
			<!-- </div> -->
		
			<div class="col-md-6">
				<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 申请部门</label>
				<div class="col-sm-8">
					<combobox-organization v-bind:query="treeQuery" v-if="projectaddenterguard.state!='readonly'" v-bind:selectid="projectdetail.apply_dept_id" v-bind:selectname="projectdetail.apply_dept_name" v-on:error="treeError" v-on:change="treeChange"></combobox-organization>
					<input type="text"  v-if="projectaddenterguard.state=='readonly'" readonly id="form-field-2" placeholder="" class="col-xs-12" v-model="projectdetail.apply_dept_name" >
					<!-- <combobox-organization v-bind:query="treeQuery" v-bind:selectid="projectdetail.apply_dept_id" v-bind:selectname="projectdetail.apply_dept_name" v-on:error="treeError" v-on:change="treeChange"></combobox-organization> -->
								
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-6">
				<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 项目预计开始时间</label>
				<div class="col-sm-8">
				<input type="text" class="form-control date-picker"class="col-sm-12" v-if="projectaddenterguard.state!='readonly'" placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="projectdetail.expected_sdate">
				<!-- <input type="text" class="form-control date-picker"class="col-sm-12" placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="projectdetail.expected_sdate" v-else > -->
				<input type="text"  v-if="projectaddenterguard.state=='readonly'" readonly id="form-field-2" placeholder="" class="col-xs-12" v-model="projectdetail.expected_sdate" >
				</div>
				<!-- <div class="help-block"> <span v-if="$validation.biz_name.required" style="color:#d16e6c">*必填</span> </div> -->
			</div>
			<div class="col-md-6">
				<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 项目预计完成时间</label>
				<div class="col-sm-8">
				<input type="text" class="form-control date-picker"class="col-sm-12" v-if="projectaddenterguard.state!='readonly'" placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="projectdetail.expected_edate">
				<!-- <input type="text" class="form-control date-picker"class="col-sm-12" placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="projectdetail.expected_edate" v-else > -->
				<input type="text"  v-if="projectaddenterguard.state=='readonly'" readonly id="form-field-2" placeholder="" class="col-xs-12" v-model="projectdetail.expected_edate" >
				</div>
				<!-- <div class="help-block"> <span v-if="$validation.biz_name.required" style="color:#d16e6c">*必填</span> </div> -->
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-6">
				<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 业务描述</label>
				<div class="col-sm-8">
				<textarea id="form-field-20" rows="4" class="col-sm-12" v-if="projectaddenterguard.state=='readonly'" readonly v-model="projectdetail.description" >
				</textarea>
				<textarea id="form-field-20" rows="4" class="col-sm-12" v-model="projectdetail.description" v-else >
				</textarea>
				</div>
			</div>
		</div>
		
		<div class="space-4"></div>
		<div class="hr hr32 hr-dotted"></div>
		<div class="form-group">
			<label class="col-sm-1 control-label text-align-left no-padding-right" > 立项申请附件</label>
		</div>
	
	<div class="row">
		<div class="col-xs-12">
		<!--  ===================== separate line ===================== -->
		<attach-table v-bind:codetable="CT.projectApplyAttchmentType" v-if="projectaddenterguard.state=='readonly'" readonly="1" v-bind:attachments="projectdetail.attachments" v-on:getuploads="getuploads" v-on:error="fileInputError"></attach-table>
		<attach-table v-bind:codetable="CT.projectApplyAttchmentType" v-bind:attachments="projectdetail.attachments" v-on:getuploads="getuploads" v-on:error="fileInputError"v-else ></attach-table>

		<!--  ===================== separate line ===================== -->
	</div>

	</div>
	<div class="clearfix no-padding-left form-actions">
			<!-- <div class="col-md-offset-2 col-md-10"> -->
			<div class="col-md-12 no-padding-left">
				<button class="btn btn-info" type="button"  v-if="projectaddenterguard.state!='readonly'" v-on:click="save">
					<i class="ace-icon fa fa-check bigger-110"></i>
					保存
				</button>

				&nbsp; &nbsp; &nbsp;
				<button class="btn" type="reset" v-on:click="back">
					<i class="ace-icon fa fa-undo bigger-110"></i>
					返回
				</button>

				<button class="btn btn-info pull-right" type="button"  v-if="projectaddenterguard.state!='readonly'" v-on:click="submit">
					<!-- <i class="ace-icon fa fa-undo bigger-110"></i> -->
					提交/启动审核
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
import AttachTable from '../common/AttachTable'
import ComboboxOrganization from '../common/ComboboxOrganization'
import myAlert from '../common/myAlert'
import {getOneProjectDetail,getOneProjectAddEnterGuard} from '../../vuex/modules/project-management/ProjectApplyGetters'
		
import {saveOneProject,reqOneProjectDetail} from '../../vuex/modules/project-management/ProjectApplyActions'
import {getCodeTable} from '../../vuex/modules/common/CodeTableGetters'
		
export default {
	data:function(){
		return {
			orgid:'',
			alertShow:false,
			alertType:'alert-success',
			alertContent:'aaaa',
			attach:{},
			treeQuery:{ with_users:'0' }

		}
	},
	components: {
		myAlert,
		ComboboxOrganization,
		AttachTable
	},
	vuex:{
		getters:{
			projectdetail:getOneProjectDetail,
			projectaddenterguard:getOneProjectAddEnterGuard,
			CT:getCodeTable
		},
		actions:{
			reqOneProjectDetail,
			saveOneProject
		}
	},
	methods:{
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
			this.orgid = id; 
	    },
		treeError:function(errMsg){
			//TODO 此处增加错误提示alert 显示内容为errMsg
			console.error(errMsg);
		},
		back:function(){
				this.$router.go({
                                  name:'project_apply'});
		},
		submit:function(){
			if(this.$validation.valid){
				let self=this;

				let requestBody={ id:self.projectdetail.id,name:self.projectdetail.name,year:self.projectdetail.year,apply_dept_id:self.orgid,total_investment:self.projectdetail.total_investment,expected_sdate:self.projectdetail.expected_sdate,expected_edate:self.projectdetail.expected_edate,description:self.projectdetail.description,attachments:self.attach,is_submit:'1'};
			// console.debug(requestBody);
				this.saveOneProject(self,'1',requestBody);
			}
			else{
				bootbox.alert("请填写必填项！");
			}
		},
		save:function(){
			if(this.$validation.valid){
				let self=this;

				let requestBody={ id:self.projectdetail.id,name:self.projectdetail.name,year:self.projectdetail.year,apply_dept_id:self.orgid,total_investment:self.projectdetail.total_investment,expected_sdate:self.projectdetail.expected_sdate,expected_edate:self.projectdetail.expected_edate,description:self.projectdetail.description,attachments:self.attach};
			// console.debug(requestBody);
				this.saveOneProject(self,'0',requestBody);
			}
			else{
				bootbox.alert("请填写必填项！");
			}
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

		//this.$broadcast('done');

		console.debug('====this.projectaddenterguard============================',this.projectaddenterguard.state);
		  if(this.projectaddenterguard.projectid != ''){
			  this.reqOneProjectDetail(this.projectaddenterguard.projectid,function(){
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
</style>
