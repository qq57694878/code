
<template>

<table cellspacing="0" cellpadding="0" border="0" style="table-layout:auto;display:inline-block;" class="ui-pg-table">
	<tbody>
	<tr>
		<td id="first_grid-pager" class="ui-pg-button ui-corner-all " v-bind:class="{'ui-state-disabled':leftDisabled}" v-on:click="first"> <span class="ui-icon ace-icon fa fa-angle-double-left bigger-140"></span> </td>
		<td id="prev_grid-pager" class="ui-pg-button ui-corner-all " style="cursor: default;" v-bind:class="{'ui-state-disabled':leftDisabled}" v-on:click="prev"><span class="ui-icon ace-icon fa fa-angle-left bigger-140"></span></td>
		<td class="ui-pg-button ui-state-disabled" style="width: 4px; cursor: default;"><span class="ui-separator"></span></td>
		<td dir="ltr">Page <input class="ui-pg-input" type="text" size="2" maxlength="7" v-model="InputNumber" role="textbox" v-on:keyup.enter="pageSet"> of <span id="sp_1_grid-pager">{{ Math.ceil(totalSize/pageSize) }}</span></td>
		<td class="ui-pg-button ui-state-disabled" style="width:4px;"><span class="ui-separator"></span></td>
		<td id="next_grid-pager" class="ui-pg-button ui-corner-all" style="cursor: default;" v-bind:class="{'ui-state-disabled':rightDisabled}" v-on:click="next"><span class="ui-icon ace-icon fa fa-angle-right bigger-140"></span></td>
		<td id="last_grid-pager" class="ui-pg-button ui-corner-all" style="cursor: default;" v-bind:class="{'ui-state-disabled':rightDisabled}" v-on:click="end"><span class="ui-icon ace-icon fa fa-angle-double-right bigger-140"></span></td>
		<!-- <td dir="ltr"><select class="ui-pg-selbox" role="listbox"><option role="option" value="10" selected="selected">10</option><option role="option" value="20">20</option><option role="option" value="30">30</option></select></td> -->
	</tr></tbody>
</table>

</template>

<script>
export default {
	props:["totalSize","pageSize"],
	data:function(){
		return {
		 pageNumber:1
		};
	},
	computed:{

		InputNumber:{
			get:function(){
				return this.pageNumber;
			},
			set:function(Num){
				var iNum=parseInt(Num);
				if(iNum<1){
					iNum=1;
				}
				if(iNum>Math.ceil(this.totalSize/this.pageSize))
				{
					iNum=Math.ceil(this.totalSize/this.pageSize);
				}
				this.pageNumber=iNum;
				console.debug("set page number"+this.pageNumber);
				//this.$dispatch('page-set',this.pageNumber); //but nothing happened
			}
		},

		leftDisabled:function(){
			// console.debug(this.pageNumber);
			return this.pageNumber<=1?true:false;
		},
		rightDisabled:function(){
			return this.pageNumber>=Math.ceil(this.totalSize/this.pageSize)?true:false;
		}
	},
	methods:{
		next:function(){
			console.debug("next page number"+this.pageNumber);
			if(this.pageNumber<Math.ceil(this.totalSize/this.pageSize)){
				this.pageNumber+=1;
				this.$dispatch('page-next',this.pageNumber);
			}
		},
		prev:function(){
			console.debug("prev page number"+this.pageNumber);
			if(this.pageNumber>1 ){
				this.pageNumber-=1;
				this.$dispatch('page-prev',this.pageNumber);
			}
		},
		first:function(){
			console.debug("first page number"+this.pageNumber);
			if(this.pageNumber>1){
				this.pageNumber=1;
				this.$dispatch('page-first',this.pageNumber);
			}
		},
		end:function(){
			console.debug("end page number"+this.pageNumber);
			if(this.pageNumber<Math.ceil(this.totalSize/this.pageSize)){
				this.pageNumber=Math.ceil(this.totalSize/this.pageSize);
				this.$dispatch('page-end',this.pageNumber);
			}
		},
		pageSet:function(){
			console.debug("trigger page set event"+this.pageNumber);
			this.$dispatch('page-set',this.pageNumber);
		},

	}

}
</script>

