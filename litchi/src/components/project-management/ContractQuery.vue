
<template>
<div class="page-header">
	<h1>
		合同查询	
	</h1>
</div>

<h5 class="header smaller grey"> 合同状况一览 </h5>
<div class="row">
	<div class="col-xs-2">
		<div class="block">
			<div class="number">1024</div>
			<div class="name">登记中</div>
		</div>
	</div>
	<div class="col-xs-2">
		<div class="block">
			<div class="number">1024</div>
			<div class="name">计划中</div>
		</div>
	</div>
	<div class="col-xs-2">
		<div class="block">
			<div class="number">1024</div>
			<div class="name">执行中</div>
		</div>
	</div>
	<div class="col-xs-2">
		<div class="block">
			<div class="number">1024</div>
			<div class="name">验收中</div>
		</div>
	</div>
	<div class="col-xs-4">
		<div class="block2">
			<div id="chart"></div>
			<div class="right">
			</div>
		</div>
	</div>

</div>

<h5 class="header smaller grey"> 合同查询 </h5>
<div class="row">
	<div class="col-xs-12">
		<div class="row" >
			<div class="col-xs-12">
				 <my-alert v-bind:alert-type="alertType" v-bind:content="alertContent" v-bind:show.sync="alertShow"></my-alert>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<label class="col-xs-1 control-label no-padding-right no-margin-bottom padding-top-6px" for="form-field-1" > 合同编号:</label>
				<div class="col-xs-2">
					<input type="text" id="form-field-1" placeholder="" v-model="contractquery.contractNum">
				</div>
				<div class="col-xs-6">
				</div>
				<label class="col-xs-1 control-label no-padding-right padding-top-6px" > 合同名称:</label>
				<div class="col-xs-2">
					<input type="text"  placeholder="" v-model="contractquery.contractName">
				</div>
			</div>
		</div>
		<div class="space-4"></div>
		<div class="row">
			<div class="col-xs-12">
				<label class="col-xs-1 control-label no-padding-right padding-top-6px" > 中标单位:</label>
				<div class="col-xs-2">
					<combobox placeholder="" v-bind:items="vendors" v-on:change="change"></combobox>
				</div>
				<div class="col-xs-6">
				</div>
				<label class="col-xs-1 control-label no-padding-right padding-top-6px" for="form-field-1"> 合同状态:</label>
				<div class="col-xs-2">
				<!-- <select id="food" placeholder="" class="multiselect" multiple style="display:none;" > -->
					<select  v-model="contractquery.contractStatus" >
					<option  v-for="(code,status) in CT.contract_status "  value="code">{{status}}</option>
					</select>
				</div>
			</div>
		</div>
		<div class="space-4"></div>
		<div class="row">
			<div class="col-xs-12">

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
				<th>中标单位</th>
				<th> <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i> 合同登记日期 </th>
				<th> <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i> 预计验收日期 </th>
				<th>合同状态</th>
				<th>操作</th>

			</tr>
		</thead>
		<tbody>
			<tr v-for="contract in contractslist.contracts">

			<td> {{contract.cno}} </td>
			<td> {{contract.name}} </td>
			<td> {{contract.vender_name}} </td>
			<td> {{contract.apply_date}} </td>
			<td> {{contract.reception_time}} </td>
			<td> {{CT.contract_status[contract.status]}} </td>

			<td>
				<div class="hidden-sm hidden-xs btn-group" >
					<button class="btn btn-success" v-on:click="info(contract.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 基本信息 </button>
					<button class="btn btn-success" v-if="contract.status!='1'" v-on:click="executionplan(contract.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 执行计划 </button>
					<button class="btn btn-success" v-if="contract.status!='1' && contract.status!='2'" v-on:click="execution(contract.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 执行过程 </button>
					<button class="btn btn-success" v-if="contract.status=='4' || contract.status=='5'" v-on:click="acceptance(contract.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 验收情况 </button>
				</div>

				<div class="hidden-md hidden-lg" >
					<div class="inline position-relative">
						<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
						<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
						</button>
						
						<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
							
							<li>
							<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="基本信息" v-on:click="info(contract.id)">
							<span class="green">
							<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
							</span>
							</a>
							</li>
							
							<li v-if="contract.status!='1'">
							<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="执行计划" v-on:click="executionplan(contract.id)">
							<span class="green">
							<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
							</span>
							</a>
							</li>
							
							<li v-if="contract.status!='1' && contract.status!='2'" >
							<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="执行过程" v-on:click="execution(contract.id)">
							<span class="green">
							<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
							</span>
							</a>
							</li>
							
							<li v-if="contract.status=='4' || contract.status=='5'">
							<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="验收情况" v-on:click="acceptance(contract.id)">
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

import echarts from 'echarts'

import Pages from '../common/Pages'
import myAlert from '../common/myAlert'
import Combobox from '../common/Combobox'

import {reqComboboxData} from '../../vuex/modules/common/ComboboxActions'
import {reqContractList,setEnterContractRegisterDetailGuard,setEnterContractAcceptanceDetailGuard,clearContractQuery} from '../../vuex/modules/project-management/ContractRegisterActions'
import {updReadOnlyFlag} from '../../vuex/modules/project-management/ContractExecPlanActions'
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
			setEnterContractRegisterDetailGuard,
			setEnterContractAcceptanceDetailGuard,
			updReadOnlyFlag,
			clearContractQuery
		}
	},
	methods:{
		acceptance:function(id){
			var guard = {state:'readonly',contractid:id,router:'contract_query'};
			this.setEnterContractAcceptanceDetailGuard(guard);
			this.$router.go({ name:'contract_acceptance_detail'});
		   },

		info:function(id){
			var guard = {state:'readonly',contractid:id,router:'contract_query'};
			this.setEnterContractRegisterDetailGuard(guard);
			this.$router.go({ name:'contract_register_detail_payadd'});
		   },
		executionplan:function(id){
			this.updReadOnlyFlag(true,id,'contract_query');
			this.$router.go({ name:'project_contract_exec_plan_detail'});
		   },
		execution:function(id){
			this.updReadOnlyFlag(true,id,'contract_query');
			this.$router.go({ name:'project_contract_exec_detail'});
		   },
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

		read:function(task){
			//TODO if task type
		}
	},

	ready:function(){
		this.reqComboboxData(this,'sysresource/vendor/get-vendor-list.do',function(comp,items){
			comp.vendors=items;
		});
		//this.clearContractQuery();

		let self=this;
		let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,cno:self.contractquery.contractNum,name:self.contractquery.contractName,vendor:self.vendor_id};
		this.reqContractList(self,requestBody);

		var myChart = echarts.init($('#chart')[0],'walden');

		var option = {
			title : {
				text: '合同付款统计',
				x:'left',
				textStyle:{
					// color:'#dddddd',
					fontWeight:'normal',
					fontSize:12
					// fontFamily:'Open Sans'
				}
			},
			tooltip : {
				trigger: 'item',
				formatter: "{a} <br/>{b} : {c} ({d}%)"
			},
			legend: {
				orient:'vertical',
				right:25,
				top:25,
				textStyle:{
					fontSize:10
					// fontFamily:'Open Sans'
				},
				data: ['未付金额','已付金额']
			},
			series : [
			{
				name: '合同付款统计',
				type: 'pie',
				radius : '75%',
				center: ['25%', '55%'],
				data:[
				{value:335, name:'未付金额'},
				{value:310, name:'已付金额'}
				],
				itemStyle: {
					emphasis: {
						shadowBlur: 10,
						shadowOffsetX: 0,
						shadowColor: 'rgba(0, 0, 0, 0.5)'
					}
				},
				label:{
					normal:{
						show:true,
						position:'inside',
						formatter:'{d}%',
						textStyle:{
							fontSize:10
							// fontFamily:'Open Sans'
						}
					}
				},
				labelLine:{
					normal:{
						show:false
					}
				}
			}
			]
		};

		
		myChart.setOption(option);


		
	  }
}

</script>

<style scoped>
.padding-top-6px{
	padding-top:6px;
}

.block{
	margin:0 auto;
	width:75px;
	height:75px;
	border:1px solid #dddddd;
	background-color:#ededed;
}
.block .number{
	margin:0 auto;
	font-size:24px;
	color:#428bca;
	font-weight:fold;
	line-height:45px;
	height:45px;
	text-align:center;
}
.block .name{
	margin:0 auto;
	line-height:30px;
	height:30px;
	text-align:center;
}

.block2{
	margin:0 auto;
	width:245px;
	height:125px;
	border:1px solid #dddddd;
	/*background-color:#ededed;*/
}

.block2 #chart{
	width:245px;
	height:125px;
}
</style>


