
<template>
<div class="page-header">
	<h1>
		外协单位管理	
	</h1>
</div>
<div class="row">
	<div class="col-xs-12">
		<!-- <div class="row"> -->
		<!-- 	<div class="col-xs-2"> -->
		<!-- 		<input type="text" id="form-field-1" placeholder="协力公司"> -->
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
				<i class="ace-icon glyphicon glyphicon-plus bigger-90"></i>
				外协单位登记	
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
					<!-- 
					<th class="center">
						<label class="position-relative">
							<input type="checkbox" class="ace">
							<span class="lbl"></span>
						</label>
					</th>
					-->
					<th>外协单位</th>
					<th>负责人</th>
					<th>负责人联系电话</th>
					<th>状态</th>

					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="vendor in vendorsList.list_data">

				<!--
				<td class="center">
					<label class="position-relative">
					<input type="checkbox" class="ace">
					<span class="lbl"></span>
					</label>
				</td>
				-->

				<td> {{vendor.vendor_name}} </td>
				<td> {{vendor.manager}} </td>
				<td> {{vendor.tel}} </td>
				<td v-if="vendor.state=='1'"> 有效</td>
				<td v-else> 无效</td>

				<td>
					<div class="hidden-sm hidden-xs btn-group" v-if="vendor.state!='0'">
						<button class="btn btn-success" v-on:click="process(vendor.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 编辑 </button>
						<button class="btn btn-danger"  v-on:click="stop(vendor.id)"> <i class="ace-icon fa fa-trash-o"></i> 停用</button>
					</div>

					<div class="hidden-sm hidden-xs btn-group" v-else>
						<button class="btn btn-success" v-on:click="resume(vendor.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 恢复 </button>
					</div>

					<div class="hidden-md hidden-lg" v-if="vendor.state!='0'">
						<div class="inline position-relative">
							<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
							<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
							</button>
							
							<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
								
								<li>
								<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="编辑" v-on:click="process(vendor.id)">
								<span class="green">
								<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
								</span>
								</a>
								</li>

								<li>
								<a href="#" class="tooltip-error" data-rel="tooltip" title="" data-original-title="停用" v-on:click="stop(vendor.id)">
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
								<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="恢复" v-on:click="resume(vendor.id)">
								<span class="green">
								<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
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
						<pages v-bind:total-size="vendorsList.pagetotal" v-bind:page-size="pageSize" v-on:page-set="pageSet" v-on:page-next="next" v-on:page-prev="prev" v-on:page-first="first" v-on:page-end="end"></pages>
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

import {getVendorList} from '../../vuex/modules/resource-management/VendorGetters'

import {reqVendorList,stopOneVendor,resumeOneVendor,reqOneVendorDetail,clearOneVendorDetail} from '../../vuex/modules/resource-management/VendorActions'

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
			vendorsList:getVendorList
			//CT:getCodeTable
		},
		actions:{
			reqVendorList,
			resumeOneVendor,
			stopOneVendor,
			reqOneVendorDetail,
			clearOneVendorDetail
		}
	},
	methods:{
		next:function(pageNumber) {
			console.debug("this parent page-next event "+pageNumber);
			this.reqVendorList(pageNumber,this.pageSize);
		},
		prev:function(pageNumber){
			console.debug("this parent page-prev event "+pageNumber);
			this.reqVendorList(pageNumber,this.pageSize);
		},
		first:function(pageNumber){
			console.debug("this parent page-first event "+pageNumber);
			this.reqVendorList(pageNumber,this.pageSize);
		},
		end:function(pageNumber){
			console.debug("this parent page-end event "+pageNumber);
			this.reqVendorList(pageNumber,this.pageSize);
		},
		pageSet:function(pageNumber){
			console.debug("this parent page-set event "+pageNumber);
			this.reqVendorList(pageNumber,this.pageSize);
		},

		add:function(){
			this.clearOneVendorDetail();
			this.$router.go({name:'vendor_add'});
		},
		process:function(id){
			console.debug('=========Vendor===process==id======='+id);
			this.reqOneVendorDetail(id);
			this.$router.go({ name:'vendor_add'});
		},
		stop:function(id){
			let self=this;
			bootbox.confirm("您确定要停用外协单位吗？", function(result) {
				if(result) {
					self.stopOneVendor(self,id);
				}
			});
		//	this.stopOneVendor(this,id);
		},
	    resume:function(id){
			let self=this;
			bootbox.confirm("您确定要恢复外协单位吗？", function(result) {
				if(result) {
					self.resumeOneVendor(self,id);
				}
			});
		//	this.resumeOneVendor(this,id);
		}
	},
	ready:function(){
		console.debug("=======Vendor=ready=================== ");
		this.reqVendorList(1,10);
	}
}
</script>

