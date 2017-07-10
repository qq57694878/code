
<template>
	<select class="form-control" v-bind:name="name" v-model="model" v-on:change="datachange">
		<option v-for="item in items | orderBy code" v-bind:value="item.code">{{item.value}}</option>
	</select>
</template>

<script>
export default {
	props:["name","model","items"],
	computed:{
	},
	methods:{
		datachange:function(){
			console.debug("========comboxxxxxx   1===================================");
			console.debug(this.model);
			console.debug("========comboxxxxx    2==================================");
			this.$dispatch('change',this.model);
		}
	},
	watch:{
	},
	ready:function(){
	}
}
</script>
