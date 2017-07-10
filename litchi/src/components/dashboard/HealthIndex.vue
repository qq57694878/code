<template>

<div class="widget-box">
<div class="widget-header widget-header-flat widget-header-small">
<h5 class="widget-title">
<i class="ace-icon fa fa-cog fa-spin"></i>
实时监控健康指数
</h5>

<div class="widget-body">
<div class="widget-main">

<div class="clearfix">
<!-- #section:custom/extra.grid -->
<div class="grid3">
<span class="{{ makeCls2(health.biz.warningNumber)}} ">
<i class="ace-icon fa fa-2x {{ makeCls(health.biz.warningNumber,'fa-cogs green')}} " ></i>
&nbsp; 业务系统
</span>
<h4 class="bigger pull-right {{ makeCls2(health.biz.warningNumber)}} ">{{health.biz.warningNumber>0?health.biz.warningNumber:health.biz.normalNumber}}</h4>
</span>

</div>

<div class="grid3">
<span class="{{ makeCls2(health.host.warningNumber)}}">
<i class="ace-icon fa fa-2x {{ makeCls(health.host.warningNumber,'fa-server green')}} " ></i>
&nbsp; 服务器
</span>
<h4 class="bigger pull-right {{ makeCls2(health.host.warningNumber)}}">{{health.host.warningNumber>0?health.host.warningNumber:health.host.normalNumber}}</h4>
</div>

<div class="grid3">
<span class=" {{ makeCls2(health.db.warningNumber)}}">
<i class="ace-icon fa fa-2x {{ makeCls(health.db.warningNumber,'fa-database green')}}"></i>
&nbsp; 数据库
</span>
<h4 class="bigger pull-right {{ makeCls2(health.db.warningNumber)}}">{{health.db.warningNumber>0?health.db.warningNumber:health.db.normalNumber}}</h4>
</div>

<!-- /section:custom/extra.grid -->
</div>
</div><!-- /.widget-main -->
</div><!-- /.widget-body -->
</div>
 </template>

<script>
import {  getHealth} from '../../vuex/modules/dashboard/DashboardGetters'
import {reqZibHealth } from '../../vuex/modules/dashboard/DashboardActions'
export default {
	vuex:{
		getters:{
			health:getHealth,
		},
		actions:{
			reqZibHealth 
		}
	},
	methods:{
		makeCls:function(value,defaultCls){
			if(value>0)
				return 'fa-warning red';
			else
				return defaultCls;
		},
		makeCls2:function(value){
			if(value>0)
				return 'red';
			else
				return 'green';
		}
		
	},
	ready:function(){
		this.reqZibHealth(this); 
	},
	init:function(){
		var self=this;
		this.timer=setInterval(function(){
			 self.reqZibHealth(self); 
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
