<template>
	<div class="infobox-container">
	<!-- #section:pages/dashboard.infobox -->

	<div class="infobox infobox-green">
	<div class="infobox-icon"> <i class="ace-icon fa fa-server"></i> </div>
	<div class="infobox-data">
	<span class="infobox-data-number">{{counting.x86}}</span>
	<div class="infobox-content">x86服务器设备</div>
	</div>
	<!-- <div class="stat stat-success">8台</div>-->
	</div>
	<!---------------------------------------->

	<div class="infobox infobox-blue">
	<div class="infobox-icon"> <i class="ace-icon fa fa-cubes"></i> </div>
	<div class="infobox-data">
	<span class="infobox-data-number">{{counting.vm}}</span>
	<div class="infobox-content">虚拟机</div>
	</div>
	<!-- <div class="badge badge-success"> +32% <i class="ace-icon fa fa-arrow-up"></i> </div>-->
	</div>
	<!---------------------------------------->

	<div class="infobox infobox-pink">
	<div class="infobox-icon"> <i class="ace-icon fa fa-database"></i> </div>
	<div class="infobox-data">
	<span class="infobox-data-number">{{counting.db}}</span>
	<div class="infobox-content">数据库</div>
	</div>
	<!-- <div class="stat stat-important">4</div>-->
	</div>
	<!---------------------------------------->

	<div class="infobox infobox-red">
	<div class="infobox-icon"> <i class="ace-icon fa fa-tasks"></i> </div>
	<div class="infobox-data">
	<span class="infobox-data-number">{{counting.netdev}}</span>
	<div class="infobox-content">网络设备</div>
	</div>
	</div>
	<!---------------------------------------->

	<div class="infobox infobox-orange2">
	<div class="infobox-icon"> <i class="ace-icon fa fa-cogs"></i> </div>
	<div class="infobox-data">
	<span class="infobox-data-number">{{counting.biz}}</span>
	<div class="infobox-content">业务系统</div>
	</div>
	<!-- <div class="badge badge-success"> 7.2% <i class="ace-icon fa fa-arrow-up"></i> </div> -->
	</div>
	<!---------------------------------------->

	<div class="infobox infobox-blue2">
	<div class="infobox-icon"> <i class="ace-icon fa fa-anchor"></i> </div>
	<div class="infobox-data">
	<span class="infobox-data-number">{{counting.wf}}</span>
	<div class="infobox-content">已办结审批流程</div>
	</div>
	</div>
	<!---------------------------------------->

	<!-- /section:pages/dashboard.infobox -->
	<div class="space-6"></div>

	<!-- #section:pages/dashboard.infobox.dark -->
	<div class="infobox infobox-green infobox-small infobox-dark">
	<div class="infobox-icon"> <i class="ace-icon fa fa-circle"></i> </div>
	<div class="infobox-data">
	<div class="infobox-content">需求变更</div>
	<div class="infobox-content">{{counting.wf_detail['sw_requirement_change']}}</div>
	</div>
	</div>
	<!---------------------------------------->

	<div class="infobox infobox-blue infobox-small infobox-dark">
	<div class="infobox-icon"> <i class="ace-icon fa fa-square"></i> </div>
	<div class="infobox-data">
	<div class="infobox-content">数据变更</div>
	<div class="infobox-content">{{counting.wf_detail['sw_data_change']}}</div>
	</div>
	</div>
	<!---------------------------------------->

	<div class="infobox infobox-grey infobox-small infobox-dark">
	<div class="infobox-icon"> <i class="ace-icon fa fa-star"></i> </div>
	<div class="infobox-data">
	<div class="infobox-content">数据查询</div>
	<div class="infobox-content">{{counting.wf_detail['sw_data_query']}}</div>
	</div>
	</div>

	<!---------------------------------------->

	<div class="infobox infobox-grey infobox-small infobox-dark">
	<div class="infobox-icon"> <i class="ace-icon fa fa-th-large"></i> </div>
	<div class="infobox-data">
	<div class="infobox-content">程序发布</div>
	<div class="infobox-content">{{counting.wf_detail['sw_program_deploy']}}</div>
	</div>
	</div>


	<!-- /section:pages/dashboard.infobox.dark -->
	</div>

 </template>

<script>

import {  getCounting} from '../../vuex/modules/dashboard/DashboardGetters'
import {reqZibCounting } from '../../vuex/modules/dashboard/DashboardActions'
export default {
	vuex:{
		getters:{
			counting:getCounting,
		},
		actions:{
			reqZibCounting 
		}
	},
	ready:function(){
		this.reqZibCounting(this); 
	},
	init:function(){
		var self=this;
		this.timer=setInterval(function(){
			self.reqZibCounting(self); 
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
