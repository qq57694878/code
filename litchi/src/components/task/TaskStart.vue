
<template>

<div class="page-header">
	<h1>
	发起流程
	</h1>
</div>
<div class="row">
	<div class="col-xs-12">
	<!--  ===================== separate line ===================== -->

	<div class="row" >
		<div class="col-xs-2" ><button type="button" class="btn btn-app btn-info btn-sm" v-on:click="startRequirementChange"><i class="ace-icon fa fa-moon-o bigger-230"></i> 需求变更</button> </div>
		<div class="col-xs-2" ><button type="button" class="btn btn-app btn-purple btn-sm" v-on:click="startDataChange"><i class="ace-icon fa fa-pencil-square-o bigger-230"></i>数据修改</button> </div>
		<div class="col-xs-2" ><button type="button" class="btn btn-app btn-default btn-sm" v-on:click="startDataQuery"><i class="ace-icon fa fa-paw bigger-230"></i>数据查询</button> </div>
		<div class="col-xs-2" ><button type="button" class="btn btn-app btn-yellow btn-sm" v-on:click="startProgramDeploy"><i class="ace-icon fa fa-sun-o bigger-230"></i>程序发布</button> </div>
	</div>
	
	<!--  ===================== separate line ===================== -->
		
	</div>
</div>

</template>

<script>

export default {
	methods:{
		startRequirementChange:function(){
			this.$router.go({ name:'start_requirement_change' });
		},
		startDataChange:function(){
			this.$router.go({ name:'start_data_change' });
		},
		startDataQuery:function(){
			this.$router.go({ name:'start_data_query' });
		},
		startProgramDeploy:function(){
			this.$router.go({ name:'start_program_deploy' });
		}
	}

}
</script>

