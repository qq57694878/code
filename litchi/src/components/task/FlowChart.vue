
<template>
<div>

<div class="row">
	<div class="col-xs-12">
	<!--  ===================== separate line ===================== -->
	<img class="cboxPhoto" v-bind:src="url"  style="cursor: pointer;width:100%; float: none;">
	<!--  ===================== separate line ===================== -->
		
	</div>
</div>

<div class="space-6"></div>
<div class="row">
	<div class="col-xs-12">
		<table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
		<thead>
			<tr>
				<th>节点名称</th>
				<th> <i class="hidden-480"></i> 审批意见 </th>
				<th> <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i> 处理时间 </th>
				<th >办理人</th>
				<th>状态</th>

			</tr>
		</thead>
		<tbody>
			<tr v-for="path in task.history.hispath">
				<td> {{path.node_name}} </td>
				<td> {{path.opinion}} </td>
				<td> {{path.ctime}} </td>
				<td> {{path.operator}} </td>
				<td><span class="label label-sm label-warning" > {{CT.taskState[path.flag] }} </span> </td>
			</tr>
		</tbody>
		</table>
	</div>
</div>

</div>
</template>

<script>

import {getCodeTable} from '../../vuex/modules/common/CodeTableGetters'
import { getOneTaskRequirementChange} from '../../vuex/modules/task/TaskGetters'

export default {
	props:["instanceId"],
	vuex:{
		getters:{
			task: getOneTaskRequirementChange,
			CT:getCodeTable
		}
	},
	computed:{
		url:function(){
			if(this.instanceId)
			{
				return '/api/exclude/flowchart/'+this.instanceId+'.do';
				// return 'exclude/flowchart/'+this.instanceId+'.do';
				// return this.task.history.flowchart_url;
			}else
			{
				return '';
			}
		}
	}
}
</script>

