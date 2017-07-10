
<template>

<table id="sample-table-1" class="table table-striped table-bordered table-hover">
<thead>
	<tr>
		<th class="center">
			<label class="position-relative">
				<input type="checkbox" class="ace">
				<span class="lbl"></span>
			</label>
		</th>
		<th>Domain</th>
		<th>Price</th>
		<th class="hidden-480">Clicks</th>
		<th> <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i> Update </th>
		<th class="hidden-480">Status</th>

		<th></th>
	</tr>
</thead>
<tbody>
	<tr v-for="n in 10">
	<td class="center">
		<label class="position-relative">
		<input type="checkbox" class="ace">
		<span class="lbl"></span>
		</label>
	</td>

    <td> <a href="#">ace.com</a> </td>
	<td>$45</td>
	<td class="hidden-480">3,330</td>
	<td>Feb 12</td>
	<td class="hidden-480"> <span class="label label-sm label-warning">Expiring</span> </td>

	<td>
		<div class="hidden-sm hidden-xs btn-group">
		<button class="btn btn-xs btn-success">
		<i class="ace-icon fa fa-check bigger-120"></i>
		</button>

		<button class="btn btn-xs btn-info">
		<i class="ace-icon fa fa-pencil bigger-120"></i>
		</button>

		<button class="btn btn-xs btn-danger">
		<i class="ace-icon fa fa-trash-o bigger-120"></i>
		</button>

		<button class="btn btn-xs btn-warning">
		<i class="ace-icon fa fa-flag bigger-120"></i>
		</button>
		</div>

		<div class="hidden-md hidden-lg">
		<div class="inline position-relative">
		<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
		<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
		</button>

		<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
		<li>
		<a href="#" class="tooltip-info" data-rel="tooltip" title="" data-original-title="View">
		<span class="blue">
		<i class="ace-icon fa fa-search-plus bigger-120"></i>
		</span>
		</a>
		</li>

		<li>
		<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="Edit">
		<span class="green">
		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
		</span>
		</a>
		</li>

		<li>
		<a href="#" class="tooltip-error" data-rel="tooltip" title="" data-original-title="Delete">
		<span class="red">
		<i class="ace-icon fa fa-trash-o bigger-120"></i>
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

<pages total-size="15" page-size="5" v-on:page-set="pageSet" v-on:page-next="next" v-on:page-prev="prev" v-on:page-first="first" v-on:page-end="end"></pages>
</template>

<script>

import Pages from './common/Pages'
export default {
	components: {
		Pages
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
	}

}
</script>

