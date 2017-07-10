
<template>
<div class="page-header">
	<h1>
		巡检
	</h1>
</div>
<div class="row">
	<div class="col-xs-12">
	<!--  ===================== separate line ===================== -->

	<div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer">
	
	<table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
	<thead>
		<tr>
			<th style="width:80%">巡检任务</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<tr v-for="1 in 5">
		<td>check server 1</td>

		<td>
			<div class="hidden-sm hidden-xs btn-group">
				<button class="btn btn-success" v-on:click="process"> <i class="ace-icon fa fa-pencil-square-o"></i> 填写报告 </button>
			</div>

			<div class="hidden-md hidden-lg">
				<div class="inline position-relative">
					<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
					<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
					</button>
					
					<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
						
						<li>
						<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="填写报告" v-on:click="process">
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


	</div>

	<!--  ===================== separate line ===================== -->
	</div>
</div>


</template>

<script>

// import Pages from './Pages'

export default {
	vuex:{
		getters:{
		}
	},
	components: {
		// Pages
	},
	methods:{
		next:function(pageNumber) {
			console.debug("this parent page-next event "+pageNumber);
		},
		prev:function(pageNumber){
			console.debug("this parent page-prev event "+pageNumber);
		},
		first:function(pageNumber){
			console.debug("this parent page-first event "+pageNumber);
		},
		end:function(pageNumber){
			console.debug("this parent page-end event "+pageNumber);
		},
		pageSet:function(pageNumber){
			console.debug("this parent page-set event "+pageNumber);
		},

		process:function(){
			//console.debug('myform go');
			this.$router.go('/mywork/inspecting/form');
		}
	}

}
</script>

