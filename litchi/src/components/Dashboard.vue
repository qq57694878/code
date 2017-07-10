
<template>

<div class="page-header">
<h1>
Dashboard
</h1>
</div>
<div class="row">
<div class="col-xs-12">
<!--  ===================== separate line ===================== -->

	<div class="row">
	<div class="space-6"></div>

	<div class="col-sm-7">
		<counting></counting>
	</div>

	<div class="vspace-12-sm"></div>
	<div class="col-sm-5">
		<health-index></health-index>
	</div><!-- /.col -->
	</div><!-- /.row -->

	<!-- #section:custom/extra.hr -->
	<div class="hr hr32 hr-dotted"></div>

	<!-- /section:custom/extra.hr -->
	<div class="row">
	<div class="col-sm-12">
		<zabbix-event></zabbix-event>
	</div><!-- /.col -->
	</div><!-- /.row -->

	<div class="hr hr32 hr-dotted"></div>
	<div class="row">
	<div class="space-6"></div>
	<div class="col-sm-12">
		<dashboard-task-waiting></dashboard-task-waiting> 
	</div><!-- /.col -->
	</div><!-- /.row -->

	
	<!--  ===================== separate line ===================== -->
		
	</div>
</div>

</template>

<script>
import Counting from './dashboard/Counting'
import zabbixEvent from './dashboard/zabbixEvent'
import HealthIndex from './dashboard/HealthIndex'
import DashboardTaskWaiting from './dashboard/DashboardTaskWaiting'

export default {
	components:{
		Counting,
		zabbixEvent,
		HealthIndex,
		DashboardTaskWaiting
	},
	ready:function(){
/*		var echarts = require('echarts');

		// 基于准备好的dom，初始化echarts实例
		// var myChart = echarts.init(document.getElementById('echarts-test'));
		var myChart = echarts.init($('#echarts-test')[0],'walden');
		// 绘制图表
		myChart.setOption({
			title: { text: 'ECharts 入门示例' },
			tooltip: {},
			xAxis: {
				data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
			},
			yAxis: {},
			series: [{
				name: '销量',
			type: 'bar',
			data: [5, 20, 36, 10, 10, 20]
			}]
		});
		*/

	}
}
</script>


