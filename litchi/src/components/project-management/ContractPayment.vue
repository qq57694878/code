
<template>
<div class="page-header">
	<h1>
		合同付款	
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
				<label class="col-xs-1 control-label no-padding-right no-margin-bottom" for="form-field-1"style="padding-top:6px" > 合同编号:</label>
				<div class="col-xs-2">
					<input type="text" id="form-field-1" placeholder="" v-model="contractquery.contractNum">
				</div>
				<label class="col-xs-1 control-label no-padding-right" style="padding-top:6px" > 合同名称:</label>
				<div class="col-xs-2">
					<input type="text"  placeholder="" v-model="contractquery.contractName">
				</div>
				<label class="col-xs-1 control-label no-padding-right" style="padding-top:6px" > 中标单位:</label>
				<div class="col-xs-2">
					<combobox placeholder="" v-bind:items="vendors" v-on:change="change"></combobox>
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
	
	<hr>

	<div class="row">
		<div class="col-xs-12">
		<!--  ===================== separate line ===================== -->

		<div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer">
		
		<table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
		<thead>
			<tr>
				<th>合同编号</th>
				<th>合同名称</th>
				<th>合同总金额</th>
				<th>已付金额</th>
				<th>未付金额</th>
				<th>下次付款金额</th>
				<th> <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i> 预计付款时间 </th>
				<th>操作</th>

			</tr>
		</thead>
		<tbody>
			<tr v-for="contract in contractslist.contracts">

			<td> {{contract.cno}} </td>
			<td> {{contract.name}} </td>
			<td> {{contract.amount}} </td>
			<td> {{contract.amount_paid}} </td>
			<td> {{contract.unpaid_amount}} </td>
			<td> {{contract.nextpay_amount}} </td>
			<td> {{contract.estimated_date}} </td>

			<td>
				<div class="hidden-sm hidden-xs btn-group" v-if="contract.payment_status=='0'">
					<button class="btn btn-success" v-on:click="pay(contract.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 付款 </button>
				</div>
				<div class="hidden-sm hidden-xs btn-group" v-else>
					<button class="btn btn-success" v-on:click="read(contract.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 详细 </button>
				</div>

				<div class="hidden-md hidden-lg" v-if="contract.payment_status=='0'">
					<div class="inline position-relative">
						<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
						<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
						</button>
						
						<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
							
							<li>
							<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="付款" v-on:click="pay(contract.id)">
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
							<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="详细" v-on:click="read(contract.id)">
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
					<pages v-bind:total-size="contractslist.pagetotal" v-bind:page-size="pageSize" v-on:page-set="pageSet" v-on:page-next="next" v-on:page-prev="prev" v-on:page-first="first" v-on:page-end="end"></pages>
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
import Combobox from '../common/Combobox'

import {reqComboboxData} from '../../vuex/modules/common/ComboboxActions'
import {setEnterContractPaymentDetailGuard,reqContractList,clearContractQuery} from '../../vuex/modules/project-management/ContractRegisterActions'
import {getCodeTable} from '../../vuex/modules/common/CodeTableGetters'
import {getContractList,getContractQuery} from '../../vuex/modules/project-management/ContractRegisterGetters'

export default {
	data:function(){
		return {
			pageSize:10,
			pageNumber:1,
			vendors:[],
			alertShow:false,
			alertType:'alert-success',
			alertContent:'aaaa',
			vendor_id:''
		}
	},
	components: {
		Pages,
		myAlert,
		Combobox
	},
	vuex:{
		getters:{
			contractslist:getContractList,
			contractquery:getContractQuery,
			CT:getCodeTable
		},
		actions:{
			reqComboboxData,
			reqContractList,
			setEnterContractPaymentDetailGuard,
			clearContractQuery
		}
	},
	methods:{
		change:function(Value){
			this.vendor_id=Value;
		   },
		query:function(){
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,cno:self.contractquery.contractNum,name:self.contractquery.contractName,vendor:self.vendor_id};
			this.reqContractList(self,requestBody);

		},
		next:function(pageNumber) {
			this.pageNumber=pageNumber;
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,cno:self.contractquery.contractNum,name:self.contractquery.contractName,vendor:self.vendor_id};
			this.reqContractList(self,requestBody);
			console.debug("this parent page-next event "+pageNumber);
		},
		prev:function(pageNumber){
			this.pageNumber=pageNumber;
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,cno:self.contractquery.contractNum,name:self.contractquery.contractName,vendor:self.vendor_id};
			this.reqContractList(self,requestBody);
			console.debug("this parent page-prev event "+pageNumber);
		},
		first:function(pageNumber){
			this.pageNumber=pageNumber;
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,cno:self.contractquery.contractNum,name:self.contractquery.contractName,vendor:self.vendor_id};
			this.reqContractList(self,requestBody);
			console.debug("this parent page-first event "+pageNumber);
		},
		end:function(pageNumber){
			this.pageNumber=pageNumber;
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,cno:self.contractquery.contractNum,name:self.contractquery.contractName,vendor:self.vendor_id};
			this.reqContractList(self,requestBody);
			console.debug("this parent page-end event "+pageNumber);
		},
		pageSet:function(pageNumber){
			this.pageNumber=pageNumber;
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,cno:self.contractquery.contractNum,name:self.contractquery.contractName,vendor:self.vendor_id};
			this.reqContractList(self,requestBody);
			console.debug("this parent page-set event "+pageNumber);
		},

		pay:function(id){
			var guard = {state:'',contractid:id,router:'contract_payment'};
			this.setEnterContractPaymentDetailGuard(guard);
			this.$router.go({name:'contract_payment_detail'});
		},
		read:function(id){
			var guard = {state:'readonly',contractid:id,router:'contract_payment'};
			this.setEnterContractPaymentDetailGuard(guard);
			this.$router.go({name:'contract_payment_detail'});
		}
	},

	ready:function(){
		this.reqComboboxData(this,'sysresource/vendor/get-vendor-list.do',function(comp,items){
			comp.vendors=items;
		});
			let self=this;
			let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,cno:self.contractquery.contractNum,name:self.contractquery.contractName,vendor:self.vendor_id};
			this.reqContractList(self,requestBody);
//		this.clearContractQuery();
		
	  }
}

</script>



