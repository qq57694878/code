<template>
<div class="page-header">
	<h1>
		业务系统管理	
	</h1>
</div>
<div class="row">
	<div class="col-xs-12">
		<!-- <div class="row"> -->
		<!-- 	<div class="col-xs-2"> -->
		<!-- 		<input type="text" id="form-field-1" placeholder="业务系统" > -->
		<!-- 	</div> -->
		<!-- 	<div class="col-xs-2"> -->
		<!-- 		<button class="btn btn-white btn-info" type="button"> -->
		<!-- 		<i class="ace-icon fa fa-check bigger-110"></i> -->
		<!-- 		搜索 -->	
		<!-- 		</button> -->
		<!-- 	</div> -->
		<!-- </div> -->

		<!-- <hr> -->
		<div class="row" >
			<div class="col-xs-12">
				 <my-alert v-bind:alert-type="alertType" v-bind:content="alertContent" v-bind:show.sync="alertShow"></my-alert>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-2">
				<!-- <button class="btn btn-white btn-info" type="button" v-on:click="add"> -->
				<button class="btn btn-info " style="border-radius:4px" type="button" v-on:click="add">
				<!-- <i class="ace-icon fa fa-pencil-square-o bigger-110"></i> -->
				<i class="ace-icon glyphicon glyphicon-plus bigger-90"></i>
				新建业务系统	
				</button>
			</div>
		</div>

		<div class="space-4"></div>

		<div class="row">
			<div class="col-xs-12">
			<!--  ===================== separate line ===================== -->

			<div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer">
			
			<table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
			<thead>
				<tr>
					<!-- <th style="width:80%">名称</th> -->
					<th>业务系统名称</th>
					<th>使用部门</th>
					<th>建设单位</th>
					<th> <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i> 上线日期 </th>

					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="app in appList.list_data">

				<td> {{app.name}} </td>
				<td> {{app.org_name}} </td>
				<td> {{app.vendor}} </td>
				<td> {{app.online_time}} </td>

				<!-- <tr v-for="appid in appList.id_list"> -->

				<!-- <td> {{appList.apps_data[appid].name}} </td> -->
				<!-- <td> {{appList.apps_data[appid].org_name}} </td> -->
				<!-- <td> {{appList.apps_data[appid].vendor}} </td> -->
				<!-- <td> {{appList.apps_data[appid].online_time}} </td> -->

				<td>
					<div class="hidden-sm hidden-xs btn-group" v-if="app.status!='0'">
						<button class="btn btn-success" v-on:click="process(app.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 编辑 </button>
						<button class="btn btn-danger"  v-on:click="stop(app.id)"> <i class="ace-icon fa fa-trash-o"></i> 停用</button>
					</div>

					<div class="hidden-sm hidden-xs btn-group" v-else>
						<button class="btn btn-success" v-on:click="resume(app.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 恢复 </button>
					</div>

					<div class="hidden-md hidden-lg" v-if="app.status!='0'">
						<div class="inline position-relative">
							<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
							<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
							</button>
							
							<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
								
								<li>
								<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="编辑" v-on:click="process(app.id)">
								<span class="green">
								<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
								</span>
								</a>
								</li>

								<li>
								<a href="#" class="tooltip-error" data-rel="tooltip" title="" data-original-title="停用" v-on:click="stop(app.id)">
								<span class="red">
								<i class="ace-icon fa fa-trash-o bigger-120"></i>
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
								<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="恢复" v-on:click="resume(app.id)">
								<span class="green">
								<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
								</span>
								</a>
								</li>

							</ul>
						</div>
					</div>
				</td>
				<!-- <td> -->
				<!-- 	<div class="hidden-sm hidden-xs btn-group" v-show="appList.apps_data[appid].status!='0'"> -->
				<!-- 		<button class="btn btn-success" v-on:click="process(appid)"> <i class="ace-icon fa fa-pencil-square-o"></i> 编辑 </button> -->
				<!-- 		<button class="btn btn-danger"  v-on:click="stop(appid)"> <i class="ace-icon fa fa-trash-o"></i> 停用</button> -->
				<!-- 	</div> -->

				<!-- 	<div class="hidden-sm hidden-xs btn-group" v-else> -->
				<!-- 		<button class="btn btn-success" v-on:click="resume(appid)"> <i class="ace-icon fa fa-pencil-square-o"></i> 恢复 </button> -->
				<!-- 	</div> -->

				<!-- 	<div class="hidden-md hidden-lg" v-show="appList.apps_data[appid].status!='0'"> -->
				<!-- 		<div class="inline position-relative"> -->
				<!-- 			<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false"> -->
				<!-- 			<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i> -->
				<!-- 			</button> -->
							
				<!-- 			<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close"> -->
								
				<!-- 				<li> -->
				<!-- 				<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="编辑" v-on:click="process(appid)"> -->
				<!-- 				<span class="green"> -->
				<!-- 				<i class="ace-icon fa fa-pencil-square-o bigger-120"></i> -->
				<!-- 				</span> -->
				<!-- 				</a> -->
				<!-- 				</li> -->

				<!-- 				<li> -->
				<!-- 				<a href="#" class="tooltip-error" data-rel="tooltip" title="" data-original-title="停用" v-on:click="stop(appid)"> -->
				<!-- 				<span class="red"> -->
				<!-- 				<i class="ace-icon fa fa-trash-o bigger-120"></i> -->
				<!-- 				</span> -->
				<!-- 				</a> -->
				<!-- 				</li> -->
				<!-- 			</ul> -->
				<!-- 		</div> -->
				<!-- 	</div> -->

				<!-- 	<div class="hidden-md hidden-lg" v-else> -->
				<!-- 		<div class="inline position-relative"> -->
				<!-- 			<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false"> -->
				<!-- 			<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i> -->
				<!-- 			</button> -->
							
				<!-- 			<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close"> -->
								
				<!-- 				<li> -->
				<!-- 				<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="恢复" v-on:click="resume(appid)"> -->
				<!-- 				<span class="green"> -->
				<!-- 				<i class="ace-icon fa fa-pencil-square-o bigger-120"></i> -->
				<!-- 				</span> -->
				<!-- 				</a> -->
				<!-- 				</li> -->

				<!-- 			</ul> -->
				<!-- 		</div> -->
				<!-- 	</div> -->
				<!-- 	</td> -->



				</tr>

			</tbody>
			</table>

			<div class="row">
				<div class="col-xs-6">
				</div>
				<div class="col-xs-6">
					<div class="dataTables_paginate paging_simple_numbers">
						<pages v-bind:total-size="appList.pagetotal" v-bind:page-size="pageSize" v-on:page-set="pageSet" v-on:page-next="next" v-on:page-prev="prev" v-on:page-first="first" v-on:page-end="end"></pages>
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
import Pages from '../common/Pages'
import myAlert from '../common/myAlert'

import {getBusinessAppList} from '../../vuex/modules/resource-management/BusinessAppGetters'

import {reqBusinessAppList} from '../../vuex/modules/resource-management/BusinessAppActions'
import {reqOneBizAppDetail} from '../../vuex/modules/resource-management/BusinessAppActions'
import {clearOneBizAppDetail} from '../../vuex/modules/resource-management/BusinessAppActions'
import {stopOneBizApp} from '../../vuex/modules/resource-management/BusinessAppActions'
import {resumeOneBizApp} from '../../vuex/modules/resource-management/BusinessAppActions'

export default {
	data:function(){
		return {
			pageSize:10,
			alertShow:false,
			alertType:'alert-success',
			alertContent:'aaaa'
		}
	},
	components: {
		Pages,
		myAlert
	},
	vuex:{
		getters:{
			appList:getBusinessAppList
			//CT:getCodeTable
		},
		actions:{
			reqBusinessAppList,
			reqOneBizAppDetail,
			clearOneBizAppDetail,
			stopOneBizApp,
			resumeOneBizApp
		}
	},
	methods:{
		next:function(pageNumber) {
			console.debug("this parent page-next event "+pageNumber);
			this.reqBusinessAppList(pageNumber,this.pageSize);
		},
		prev:function(pageNumber){
			console.debug("this parent page-prev event "+pageNumber);
			this.reqBusinessAppList(pageNumber,this.pageSize);
		},
		first:function(pageNumber){
			console.debug("this parent page-first event "+pageNumber);
			this.reqBusinessAppList(pageNumber,this.pageSize);
		},
		end:function(pageNumber){
			console.debug("this parent page-end event "+pageNumber);
			this.reqBusinessAppList(pageNumber,this.pageSize);
		},
		pageSet:function(pageNumber){
			console.debug("this parent page-set event "+pageNumber);
			this.reqBusinessAppList(pageNumber,this.pageSize);
		},

		add:function(){
			this.clearOneBizAppDetail();
			this.$router.go({ name:'businessapp_add'});
		},
		process:function(appid){
			console.debug('==========bizapp===process==id======='+appid);
			var self=this;
			this.reqOneBizAppDetail(appid,function(){
				self.$router.go({ name:'businessapp_add'});
			});
			// this.$router.go({ name:'businessapp_add'});
		},
		stop:function(appid){
			let self=this;
			bootbox.confirm("您确定要停用系统吗？", function(result) {
				if(result) {
					self.stopOneBizApp(self,appid);
				}
			});
		},
	    resume:function(appid){
			let self=this;
			bootbox.confirm("您确定要恢复系统吗？", function(result) {
				if(result) {
					self.resumeOneBizApp(self,appid);
				}
			});
		}
	},
	ready:function(){
		console.debug("========bizapp=ready=================== ");
		this.reqBusinessAppList(1,10);
	}
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
