
<template>

<div class="page-header">
	<h1>
	{{ CT.taskType[taskType] }}
	<small style="font-size=16px"> <i class="ace-icon fa fa-angle-double-right"></i> {{ nodeName }} </small>
	</h1>
</div>
<div class="row">
	<div class="col-xs-12">
	<!--  ===================== separate line ===================== -->

	<div class="tabbable">
	<ul class="nav nav-tabs" >
		<li class="active">
			<a data-toggle="tab" href="#myform-form">
			<i class="green ace-icon fa fa-check-square-o bigger-120"></i>
			表单
			</a>
		</li>
		<li>
			<a data-toggle="tab" href="#myform-workflow-img">
			<i class="pink ace-icon fa fa-photo bigger-120"></i>
			流程图
			</a>
		</li>
		<li >
			<a data-toggle="tab" href="#myform-file">
			<i class="green ace-icon fa  fa-folder-open bigger-120"></i>
			附件
			<span class="badge badge-danger">{{task.form_data.attachments.length}}</span>
			</a>
		</li>

	</ul>

	<div class="tab-content">
		<div id="myform-form" class="tab-pane fade in active">
			<component :is="currentView" :task-id="taskId" :task-type="taskType" :instance-id="instanceId" :node-id=nodeId > </component>
		</div>

		<div id="myform-workflow-img" class="tab-pane fade">
			<flow-chart :instance-id="instanceId"></flow-chart>
		</div>

		<div id="myform-file" class="tab-pane fade">
			 <attachment :task="task"></attachment>
		</div>


	</div> <!-- tab content -->
	</div> <!-- tabtable -->



	
	<!--  ===================== separate line ===================== -->
		
	</div>
</div>

</template>

<script>

import Attachment from './Attachment'
import FlowChart from './FlowChart'

import { getOneTaskRequirementChange, getOneTaskDataChange, getOneTaskDataQuery, getOneTaskProgramDeploy} from '../../vuex/modules/task/TaskGetters'
import {getCodeTable} from '../../vuex/modules/common/CodeTableGetters'

import sw_requirement_change_step1 from './FormRequirementChangeStep1'
import sw_requirement_change_step2 from './FormRequirementChangeStep2'
import sw_requirement_change_step3 from './FormRequirementChangeStep3'
import sw_requirement_change_step4 from './FormRequirementChangeStep4'
import sw_requirement_change_step5 from './FormRequirementChangeStep5'
import sw_requirement_change_step6 from './FormRequirementChangeStep6'
import sw_requirement_change_step_detail from './FormRequirementChangeStepDetail'

import sw_data_change_step1 from './FormDataChangeStep1'
import sw_data_change_step2 from './FormDataChangeStep2'
import sw_data_change_step3 from './FormDataChangeStep3'
import sw_data_change_step4 from './FormDataChangeStep4'
import sw_data_change_step5 from './FormDataChangeStep5'
import sw_data_change_step_detail from './FormDataChangeStepDetail'

import sw_data_query_step1 from './FormDataQueryStep1'
import sw_data_query_step2 from './FormDataQueryStep2'
import sw_data_query_step3 from './FormDataQueryStep3'
import sw_data_query_step4 from './FormDataQueryStep4'
import sw_data_query_step5 from './FormDataQueryStep5'
import sw_data_query_step6 from './FormDataQueryStep6'
import sw_data_query_step_detail from './FormDataQueryStepDetail'

import sw_program_deploy_step1 from './FormProgramDeployStep1'
import sw_program_deploy_step2 from './FormProgramDeployStep2'
import sw_program_deploy_step3 from './FormProgramDeployStep3'
import sw_program_deploy_step4 from './FormProgramDeployStep4'
import sw_program_deploy_step_detail from './FormProgramDeployStepDetail'



export default {
	vuex:{
		getters:{
			getOneTaskRequirementChange,
			getOneTaskDataChange,
			getOneTaskDataQuery,
			getOneTaskProgramDeploy,
			CT:getCodeTable
		}
	},
	data:function(){
		return {
			taskId:'',
			taskType:'',
			nodeId:'',
			nodeName:'',
			instanceId:''
		}
	},
	route:{
		data: function(transition){
			let taskId=transition.to.params.taskId;
			let taskType=transition.to.params.taskType;
			let nodeId=transition.to.params.nodeId;
			let nodeName=transition.to.params.nodeName;
			let instanceId=transition.to.params.instanceId;
			transition.next({taskId:taskId,taskType:taskType,nodeId:nodeId,nodeName:nodeName,instanceId:instanceId});
		}
	},
	computed:{
		currentView:function(){
			if(this.taskType && this.nodeId){
				return this.taskType+'_'+this.nodeId;
			}else{
				return '';
			}
		},
		task:function()
			{
				switch(this.taskType){
						case 'sw_requirement_change':
							return this.getOneTaskRequirementChange;
						case 'sw_data_change':
							return this.getOneTaskDataChange;
						case 'sw_data_query':
							return this.getOneTaskDataQuery;
						case 'sw_program_deploy':
							return this.getOneTaskProgramDeploy;
						default:
							return this.getOneTaskRequirementChange;
					}
			}
	},
	components: {
		Attachment,
		FlowChart,
		sw_requirement_change_step1,
		sw_requirement_change_step2,
		sw_requirement_change_step3,
		sw_requirement_change_step4,
		sw_requirement_change_step5,
		sw_requirement_change_step6,
		sw_requirement_change_step_detail,

		sw_data_change_step1,
		sw_data_change_step2,
		sw_data_change_step3,
		sw_data_change_step4,
		sw_data_change_step5,
		sw_data_change_step_detail,

		sw_data_query_step1,
		sw_data_query_step2,
		sw_data_query_step3,
		sw_data_query_step4,
		sw_data_query_step5,
		sw_data_query_step6,
		sw_data_query_step_detail,
		
		sw_program_deploy_step1,
		sw_program_deploy_step2,
		sw_program_deploy_step3,
		sw_program_deploy_step4,
		sw_program_deploy_step_detail
	}
}
</script>

