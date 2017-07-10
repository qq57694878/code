<template>
<div class="row" >
	<div class="col-xs-12">
		 <my-alert v-bind:alert-type="alertType" v-bind:content="alertContent" v-bind:show.sync="alertShow"></my-alert>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		
		<div class="col-xs-2 no-padding-left">
			<button class="btn btn-info" style="border-radius:4px" type="button" v-on:click="add">
				<i class="ace-icon glyphicon glyphicon-plus bigger-90"></i>
				添加采购项目	
			</button>
		</div>
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
				<th>采购项目名称</th>
				<th>预算金额</th>
				<th>项目负责人</th>
				<th>采购方式</th>
				<th>采购状态</th>
				<th >操作</th>

			</tr>
		</thead>
		<tbody>
			<tr v-for="purchase in purchaselist.projects">

			<!-- <td> taskTypeList[task.task_type].value</td> -->
			<td> {{purchase.name}}</td>
			<td> {{purchase.budget_amount}}</td>
			<td> {{purchase.responsible_person}}</td>
			<td> {{	CT.procurementMethodtype[purchase.procurement_method]}}</td>
			<td> {{	CT.procurementStatustype[purchase.status]}}</td>

			<td>
				<div class="hidden-sm hidden-xs btn-group" v-if="purchase.status=='3'">
					<button class="btn btn-success" v-on:click="read(purchase.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 详细 </button>
				</div>
				<!-- <div class="hidden-sm hidden-xs btn-group" v-if="purchase.status=='0'"> -->
				<!-- 	<button class="btn btn-success" v-on:click="edit(purchase)"> <i class="ace-icon fa fa-pencil-square-o"></i> 编辑 </button> -->
				<!-- 	<button class="btn btn-danger"  v-on:click="del(project.id)"> <i class="ace-icon fa fa-trash-o"></i> 删除</button> -->
				<!-- </div> -->
				<div class="hidden-sm hidden-xs btn-group" v-else>
					<button class="btn btn-success" v-on:click="edit(purchase.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 编辑 </button>
					<button class="btn btn-danger" v-if="purchase.status=='0'" v-on:click="del(purchase.id)"> <i class="ace-icon fa fa-trash-o"></i> 删除</button>
				</div>

				<div class="hidden-md hidden-lg" v-if="purchase.status=='3'">
					<div class="inline position-relative">
						<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
						<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
						</button>
						
						<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
							
							<li>
							<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="详细" v-on:click="read(purchase.id)">
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
							<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="编辑" v-on:click="edit(purchase.id)">
							<span class="green">
							<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
							</span>
							</a>
							</li>

							<li>
							<a href="#" v-if="purchase.status=='0'" class="tooltip-error" data-rel="tooltip" title="" data-original-title="删除" v-on:click="del(purchase.id)">
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

		</div>
		<!--  ===================== separate line ===================== -->
	</div>
</div>
</template>

<script>		
import myAlert from '../common/myAlert'
import {clearOneProjectProcurementDetail,setEnterProcurementDetailGuard,deleteOneProjectProcurement } from '../../vuex/modules/project-management/ProjectPurchaseActions'
import {getPurchaseList} from '../../vuex/modules/project-management/ProjectPurchaseGetters'
import {getCodeTable} from '../../vuex/modules/common/CodeTableGetters'
export default {
	data:function(){
		return {
			alertShow:false,
			alertType:'alert-success',
			alertContent:'aaaa',
		}
	},
	components: {
		myAlert
	},
	vuex:{
		getters:{
			purchaselist:getPurchaseList,
			CT:getCodeTable
		},
		actions:{
			clearOneProjectProcurementDetail,
			setEnterProcurementDetailGuard,
			deleteOneProjectProcurement 
		}
	},
	methods:{
		del:function(purchaseid){
			let self=this;
			bootbox.confirm("您确定要删除采购项目吗？", function(result) {
				if(result) {
					self.deleteOneProjectProcurement(self,purchaseid);
				}
			});

		},
		add:function(){
			this.clearOneProjectProcurementDetail(this.purchaselist.projects[0].pid);
			var guard = {state:'',purchaseid:''};
			this.setEnterProcurementDetailGuard(guard);
			this.$router.go({ name:'project_purchase_detail_detail'});
		},
		read:function(id){
			this.clearOneProjectProcurementDetail(this.purchaselist.projects[0].pid);
			var guard = {state:'readonly',purchaseid:id};
			this.setEnterProcurementDetailGuard(guard);
			this.$router.go({ name:'project_purchase_detail_detail'});
		},
		edit:function(id){
			this.clearOneProjectProcurementDetail(this.purchaselist.projects[0].pid);
			var guard = {state:'',purchaseid:id};
			this.setEnterProcurementDetailGuard(guard);
			this.$router.go({ name:'project_purchase_detail_detail'});
		}
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
