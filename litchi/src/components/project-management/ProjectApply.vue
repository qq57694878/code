
<template>
<div class="page-header">
	<h1>
		项目立项申请	
	</h1>
</div>
<div class="row">
	<div class="col-xs-12">
		<div class="row" >
			<div class="col-xs-12">
				 <my-alert v-bind:alert-type="alertType" v-bind:content="alertContent" v-bind:show.sync="alertShow"></my-alert>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<label class="col-xs-1 control-label no-padding-right no-margin-bottom" for="form-field-1"style="padding-top:6px" > 年度:</label>
				<div class="col-xs-2">
					<input type="text" id="form-field-1" placeholder="2016" v-model="projectquery.year">
				</div>
				<label class="col-xs-1 control-label no-padding-right" style="padding-top:6px" > 项目名称:</label>
				<div class="col-xs-2">
					<input type="text" placeholder=""  v-model="projectquery.name">
				</div>
				<label class="col-xs-1 control-label no-padding-right" style="padding-top:6px" > 申请部门:</label>
				<div class="col-xs-2">
					<combobox-organization v-bind:query="treeQuery" v-on:error="treeError" v-on:change="treeChange"></combobox-organization>
				</div>
				<div class="col-xs-2">
					<!-- <button class="btn btn-white btn-info" type="button" v-on:click="query"> -->
					<button class="btn btn-info " style="border-radius:4px" type="button" v-on:click="query">
						<!-- <i class="ace-icon fa fa-check bigger-110"></i> -->
						<i class="ace-icon glyphicon glyphicon-search bigger-110"></i>
						查询	
					</button>
				</div>

			<!-- <div class="col-xs-5"> -->

			</div>
		</div>
		<div class="space-4"></div>
		<div class="row">
			<div class="col-xs-12">
				
				<!-- <div class="col-xs-2"> -->
				<!-- 	<1!-- <button class="btn btn-white btn-info" type="button" v-on:click="query"> --1> -->
				<!-- 	<button class="btn btn-info " style="border-radius:4px" type="button" v-on:click="query"> -->
				<!-- 		<1!-- <i class="ace-icon fa fa-check bigger-110"></i> --1> -->
				<!-- 		<i class="ace-icon glyphicon glyphicon-search bigger-110"></i> -->
				<!-- 		查询 -->	
				<!-- 	</button> -->
				<!-- </div> -->
				<div class="col-xs-2">
					<!-- <button class="btn btn-white btn-info" type="button" v-on:click="query"> -->
					<button class="btn btn-info " style="border-radius:4px" type="button" v-on:click="apply">
						<i class="ace-icon glyphicon glyphicon-plus bigger-90"></i>
						立项申请	
					</button>
				</div>
			</div>
		</div>

	<hr>

	<div class="row">
		<div class="col-xs-12">
		<!--  ===================== separate line ===================== -->

		<div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer">
		
		<table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
		<thead>
			<tr>
				<th>年度</th>
				<th>项目名称</th>
				<th>申请单位/部门</th>
				<th>概算总投资</th>
				<th>项目状态</th>
				<!-- <th class="hidden-480">Clicks</th> -->
				<th> <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i> 立项申请日期 </th>
				<th> <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i> 预计开始时间 </th>
				<th> <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i> 预计完成时间 </th>
				<th >操作</th>

			</tr>
		</thead>
		<tbody>
			<tr v-for="project in projectlist.projects">

			<td> {{project.year}}</td>
			<td> {{project.name}}</td>
			<td> {{project.apply_dept_name}}</td>
			<td> {{project.total_investment}}</td>
			<td v-if="project.status=='1'"> 已申请</td>
			<td v-else> 草稿</td>
			<td> {{project.apply_date}}</td>
			<td> {{project.expected_sdate}}</td>
			<td> {{project.expected_edate}}</td>

			<td>
				<div class="hidden-sm hidden-xs btn-group" v-if="project.status=='1'">
					<button class="btn btn-success" v-on:click="read(project.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 查看详细 </button>
				</div>

				<div class="hidden-sm hidden-xs btn-group" v-else>
					<button class="btn btn-success" v-on:click="process(project.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 编辑 </button>
					<button class="btn btn-danger"  v-on:click="del(project.id)"> <i class="ace-icon fa fa-trash-o"></i> 删除</button>
				</div>

				<div class="hidden-md hidden-lg" v-if="project.status=='1'">
					<div class="inline position-relative">
						<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
						<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
						</button>
						
						<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
							
							<li>
							<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="查看详细" v-on:click="read(project.id)">
							<span class="green">
							<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
							</span>
							</a>
							</li>

						</ul>
					</div>
				</div>
				<div class="hidden-md hidden-lg" v-else>
					<div class="inline position-relative">
						<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
						<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
						</button>
						
						<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
							
							<li>
							<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="编辑" v-on:click="process(project.id)">
							<span class="green">
							<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
							</span>
							</a>
							</li>

							<li>
							<a href="#" class="tooltip-error" data-rel="tooltip" title="" data-original-title="删除" v-on:click="del(project.id)">
							<span class="red">
							<i class="ace-icon fa fa-trash-o bigger-120"></i>
							</span>
							</a>
							</li>
						</ul>
					</div>
				</div>
				</td>
			</tr>

		</tbody>
		</table>

		<div class="row">
			<div class="col-xs-6">
			</div>
			<div class="col-xs-6">
				<div class="dataTables_paginate paging_simple_numbers">
					<pages v-bind:total-size="projectlist.pagetotal" v-bind:page-size="pageSize" v-on:page-set="pageSet" v-on:page-next="next" v-on:page-prev="prev" v-on:page-first="first" v-on:page-end="end"></pages>
				</div>
			</div>
		</div>

		</div>

		<!--  ===================== separate line ===================== -->
	</div>

	</div>

	<!--  ===================== separate line ===================== -->
	</div>
</div>
</template>

<script>

import ComboboxOrganization from '../common/ComboboxOrganization'
import Pages from '../common/Pages'
import myAlert from '../common/myAlert'

import {reqProjectList,setEnterProjectApplyAddGuard,deleteOneProject,clearProjectQuery,clearOneProjectDetail} from '../../vuex/modules/project-management/ProjectApplyActions'

import {getProjectList,getProjectQuery} from '../../vuex/modules/project-management/ProjectApplyGetters'

export default {
	data:function(){
		return {
			orgid:'',
			pageSize:10,
			pageNumber:1,
			alertShow:false,
			alertType:'alert-success',
			alertContent:'aaaa',
			treeQuery:{ with_users:'0' }
		}
	},
	components: {
		Pages,
		myAlert,
		ComboboxOrganization 
	},
	vuex:{
		getters:{
			projectlist:getProjectList,
			projectquery:getProjectQuery
		},
		actions:{
			reqProjectList,
			deleteOneProject,
			clearProjectQuery,
			setEnterProjectApplyAddGuard,
			clearOneProjectDetail
		}
	},
	methods:{
		treeChange:function(id){
			console.debug("==============treechange=============================="+id);
			this.orgid = id; 
	    },
		treeError:function(errMsg){
			//TODO 此处增加错误提示alert 显示内容为errMsg
		},
		apply:function(){
			this.clearOneProjectDetail();
			var guard = {state:'',projectid:''};
			this.setEnterProjectApplyAddGuard(guard);
			this.$router.go({ name:'project_apply_add'});
		},
		query:function(){
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,year:self.projectquery.year,name:self.projectquery.name,apply_dept:self.orgid,filter_application:'1'};
			this.reqProjectList(self,requestBody);
		},
		next:function(pageNumber) {
			this.pageNumber=pageNumber;
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,year:self.projectquery.year,name:self.projectquery.name,apply_dept:self.orgid,filter_application:'1'};
			this.reqProjectList(self,requestBody);
			console.debug("this parent page-next event "+pageNumber);
		},
		prev:function(pageNumber){
			this.pageNumber=pageNumber;
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,year:self.projectquery.year,name:self.projectquery.name,apply_dept:self.orgid,filter_application:'1'};
			this.reqProjectList(self,requestBody);
			console.debug("this parent page-prev event "+pageNumber);
		},
		first:function(pageNumber){
			this.pageNumber=pageNumber;
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,year:self.projectquery.year,name:self.projectquery.name,apply_dept:self.orgid,filter_application:'1'};
			this.reqProjectList(self,requestBody);
			console.debug("this parent page-first event "+pageNumber);
		},
		end:function(pageNumber){
			this.pageNumber=pageNumber;
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,year:self.projectquery.year,name:self.projectquery.name,apply_dept:self.orgid,filter_application:'1'};
			this.reqProjectList(self,requestBody);
			console.debug("this parent page-end event "+pageNumber);
		},
		pageSet:function(pageNumber){
			this.pageNumber=pageNumber;
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,year:self.projectquery.year,name:self.projectquery.name,apply_dept:self.orgid,filter_application:'1'};
			this.reqProjectList(self,requestBody);
			console.debug("this parent page-set event "+pageNumber);
		},

		read:function(id){
			this.clearOneProjectDetail();
			var guard = {state:'readonly',projectid:id};
			this.setEnterProjectApplyAddGuard(guard);
			var self=this;
			self.$router.go({ name:'project_apply_add'});
		},
		process:function(id){
			this.clearOneProjectDetail();
			var guard = {state:'',projectid:id};
			this.setEnterProjectApplyAddGuard(guard);
			var self=this;
			self.$router.go({ name:'project_apply_add'});
		},
		del:function(id){
			let self=this;
			bootbox.confirm("您确定要删除项目吗？", function(result) {
				if(result) {
					self.deleteOneProject(self,id);
				}
			});
		}
	},

	ready:function(){
		let self=this;
		let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,year:self.projectquery.year,name:self.projectquery.name,apply_dept:self.orgid,filter_application:'1'};
		this.reqProjectList(self,requestBody);
		//this.clearProjectQuery();
		
	  }
}

</script>



