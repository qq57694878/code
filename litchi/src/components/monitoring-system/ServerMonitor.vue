
<template>
<div class="page-header">
	<h1>
		服务器监控
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

	<div class="zib-header">
		<div class="zib-title">
			<div class="zib-title-date bk-title"><i class="ace-icon fa fa-cog fa-spin"></i>上次更新时间</div>
			<div class="zib-title-normal bk-title">正常</div>
			<div class="zib-title-warning bk-title">报警</div>
		</div>
		<div class="zib-title">
			<div class="zib-title-date">{{servermon.lastUpdate}}</div>
			<div class="zib-title-normal bk-green">{{servermon.normalNumber}}</div>
			<div class="zib-title-warning bk-red">{{servermon.warningNumber}}</div>
		</div>
	</div>
	
	<div class="row br-b"></div>
	<div class="row br-lbr"  v-for="serveritem in servermon.list">
		<div class="col-sm-2 pad-8" > <i class="ace-icon fa fa-warning fa-lg red"></i>&nbsp;{{serveritem.host_name}}  </div>
		<div class="col-sm-9"> 
				<div class="row" >
					<div class="col-sm-2 pad-8">{{serveritem.ip}} </div>
					<div class="col-sm-10">
						<div class="row">
							<div class="col-sm-12 pad-8">
								<div class="zib-item"><span class="label label-light">CPU</span><span class="label">{{serveritem.items['cpu'].value}}</span> </div>
								<div class="zib-item"><span class="label label-light">Memory</span><span class="label">{{serveritem.items['memory'].value}}</div>
								<div class="zib-item"><span class="label label-light">Disk</span><span class="label">{{serveritem.items['disk'].value}}</div>
								<div class="zib-item"><span class="label label-light">Network</span><span class="label">{{serveritem.items['net'].value}}</div>
							</div>
						</div>

						<table class="table table-bordered" style="margin-bottom:0px;table-layout:fixed;word-break:break-all">
						<tbody>
							<tr v-for="zabbix in serveritem.triggers">
								<td class="pad-8">{{zabbix.key}} </td>
								<td class="pad-8" style="background-color:#87b87f;">{{zabbix.status|fv}}</td>
								<td class="pad-8" style="background-color:#f2dede;">{{zabbix.message}}</td>
								<td class="pad-8" >{{zabbix.lastClock}}</td>
							</tr>
						</tbody>
						</table>
					</div>
				</div>
				
		</div>
		<div class="col-sm-1 pad-8">
			<div class="hidden-sm hidden-xs btn-group">
				<button class="btn btn-success btn-minier" v-if="serveritem.zabbix_on==0" v-on:click="create(serveritem.host_id)"  > <i class="ace-icon fa fa-pencil-square-o"></i> 监控 </button>
				<button class="btn btn-danger btn-minier"  v-else v-on:click="remove(serveritem.host_id)" > <i class="ace-icon fa fa-trash-o"></i> 取消监控</button>
			</div>

			<div class="hidden-md hidden-lg">
				<div class="inline position-relative">
					<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
					<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
					</button>
					
					<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
						
						<li v-if="serveritem.zabbix_on==0" >
						<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="监控" v-on:click="create(serveritem.host_id)" >
						<span class="green">
						<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
						</span>
						</a>
						</li>

						<li v-else >
						<a href="#" class="tooltip-error" data-rel="tooltip" title="" data-original-title="取消监控" v-on:click="remove(serveritem.host_id)" >
						<span class="red">
						<i class="ace-icon fa fa-trash-o bigger-120"></i>
						</span>
						</a>
						</li>
					</ul>
				</div>
			</div>
			</div>
		</div>

		<div class="row page-row-wrapper">
				<div class="col-xs-6">
				</div>
				<div class="col-xs-6">
					<div class="dataTables_paginate paging_simple_numbers">
						<pages v-bind:total-size="servermon.pagetotal" v-bind:page-size="pageSize" v-on:page-set="pageSet" v-on:page-next="next" v-on:page-prev="prev" v-on:page-first="first" v-on:page-end="end"></pages>
					</div>
				</div>
		</div>


	<!--  ===================== separate line ===================== -->
	</div>
</div>


</template>

<script>

import Pages from '../common/Pages'
import myAlert from '../common/myAlert'
import { getMonServer} from '../../vuex/modules/monitoring/MonServerGetters'
import { reqMonServer,createMonServer,removeMonServer   } from '../../vuex/modules/monitoring/MonServerActions'
export default {
	vuex:{
		getters:{
			servermon:getMonServer,
		},
		actions:{
			reqMonServer,
			createMonServer,
			removeMonServer
		}
	},
	filters:{
		fv:function(value){
			if(value==0)
				return 'ok';
			else
				return 'warning';
		}
	},
	ready:function(){
		$('[data-rel=tooltip]').tooltip({html:true});
		this.reqMonServer(this,this.pageNo,this.pageSize);
	},
	data:function(){
		return {
			pageNo:1,
			pageSize:50,
			alertShow:false,
			alertType:'alert-success',
			alertContent:'aaaa'
		}
	},
	components: {
		Pages,
		myAlert
	},
	methods:{
		create:function(serverId){
			this.createMonServer(this,serverId);
		},
		remove:function(serverId){
			this.removeMonServer(this,serverId);
		},
		next:function(pageNumber) {
			// console.debug("this parent page-next event "+pageNumber);
			this.pageNo=pageNumber;
			this.reqMonServer(this,this.pageNo,this.pageSize);
			// this.reqWaitingTasks(pageNumber,this.pageSize);
		},
		prev:function(pageNumber){
			// console.debug("this parent page-prev event "+pageNumber);
			this.pageNo=pageNumber;
			this.reqMonServer(this,this.pageNo,this.pageSize);
			// this.reqWaitingTasks(pageNumber,this.pageSize);
		},
		first:function(pageNumber){
			// console.debug("this parent page-first event "+pageNumber);
			this.pageNo=pageNumber;
			this.reqMonServer(this,this.pageNo,this.pageSize);
			// this.reqWaitingTasks(pageNumber,this.pageSize);
		},
		end:function(pageNumber){
			// console.debug("this parent page-end event "+pageNumber);
			this.pageNo=pageNumber;
			this.reqMonServer(this,this.pageNo,this.pageSize);
			// this.reqWaitingTasks(pageNumber,this.pageSize);
		},
		pageSet:function(pageNumber){
			// console.debug("this parent page-set event "+pageNumber);
			this.pageNo=pageNumber;
			this.reqMonServer(this,this.pageNo,this.pageSize);
			// this.reqWaitingTasks(pageNumber,this.pageSize);
		}
	},
	init:function(){
		var self=this;
		this.timer=setInterval(function(){
			// console.debug('Server mon timer event debug');
			self.reqMonServer(self,self.pageNo,self.pageSize);
		},60000);
	},
	destroyed:function(){
		// console.debug('counting component destoryed');
		clearInterval(this.timer);
	}

}
</script>

<style scoped>
.br-lbr{
	border-left:1px solid #dddddd;
	border-bottom:1px solid #dddddd;
	border-right:1px solid #dddddd;
}
.br-t{
	border-top:1px solid #dddddd;
}
.br-b{
	border-bottom:1px solid #dddddd;
}
.pad-8{
	padding:8px;
}

.zib-header{
	padding:4px;
	border:1px solid #dddddd;
	width:408px;
	margin:5px auto 15px;
}

.zib-title{
	height:45px;
	width:400px;
}

.bk-title{
	background-color:#f5f5f5;
}

.bk-yellow{
	background-color:#fee188;
}
.bk-green{
	background-color:#87b87f;
}
.bk-grey{
	background-color:#abbac3;
}
.bk-red{
	background-color:#d15b47;
}

.zib-title-date{
	height:43px;
	width:198px;
	margin:1px;
	border:1px solid #dddddd;
	float:left;
	text-align:center;
	line-height:43px;
}
.zib-title-normal{
	height:43px;
	width:98px;
	margin:1px;
	border:1px solid #dddddd;
	float:left;
	text-align:center;
	line-height:43px;
}

.zib-title-warning{
	height:43px;
	width:98px;
	margin:1px;
	border:1px solid #dddddd;
	float:left;
	text-align:center;
	line-height:43px;
}

.page-row-wrapper{
	  border-bottom: 1px solid #e0e0e0;
	    padding-top: 12px;
		  padding-bottom: 12px;
		    background-color: #EFF3F8;
}

.zib-item{
	padding:0 5px;
	display:inline-block;
}


</style>
