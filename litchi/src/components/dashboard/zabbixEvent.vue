<template>
	<div class="widget-box transparent">
		<div class="widget-header widget-header-flat">
			<h4 class="widget-title lighter"> <i class="ace-icon fa fa-cog fa-spin"></i> 实时报警事件 </h4>
			<div class="widget-toolbar"> <a href="#" data-action="collapse"> <i class="ace-icon fa fa-chevron-up"></i> </a> </div>
		</div>

		<div class="widget-body">
		<div class="widget-main no-padding">
		<table class="table table-bordered table-striped">
		<thead class="thin-border-bottom">
		<tr>
			<th> <i class="ace-icon fa fa-caret-right blue"></i>报警事件来源 </th>
			<th> <i class="ace-icon fa fa-caret-right blue"></i>报警信息 </th>
			<th> <i class="ace-icon fa fa-caret-right blue"></i>报警时间 </th>
			<th> <i class="ace-icon fa fa-caret-right blue"></i>状态 </th>
		</tr>
		</thead>

		<tbody>
			<tr v-for="e in events">
				<td><span class="label label-info arrowed-right">{{e.source_type|fst}}</span>&nbsp; {{e.source_name}}</td>
				<td>{{e.message}}</td>
				<td>{{e.lastClock}}</td>
				<td> <span class="label {{e.status|fvCls}} arrowed-right arrowed-in">{{e.status|fv}}</span> </td>
			</tr>
			
		</tbody>
		</table>
		</div><!-- /.widget-main -->
		</div><!-- /.widget-body -->
	</div><!-- /.widget-box -->

 </template>

<script>

import {getEvents} from '../../vuex/modules/dashboard/DashboardGetters'
import {reqZibEvents } from '../../vuex/modules/dashboard/DashboardActions'

export default {
	vuex:{
		getters:{
			events:getEvents,
		},
		actions:{
			reqZibEvents 
		}
	},
	filters:{
		fst:function(value){
			if(value=='biz')
				return '业务系统';
			else if(value=='host')
				return '服务器';
			else if(value=='db')
				return '数据库';
			else
				return 'unknow';

		},
		fv:function(value){
			if(value==0)
				return 'ok';
			else
				return 'warning';
		},
		fvCls:function(value){
			if(value==0)
				return 'label-success';
			else
				return 'label-danger';
		}

	},
	ready:function(){
		this.reqZibEvents(this); 
	},
	init:function(){
		console.debug('component init');
		var self=this;
		this.timer=setInterval(function(){
			self.reqZibEvents(self); 
		},50000);
	},
	destroyed:function(){
		clearInterval(this.timer);
	}
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
