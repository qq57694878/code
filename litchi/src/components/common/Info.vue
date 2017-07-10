
<template>

<div class="widget-box">

<div class="widget-body">
<div class="widget-main" style="height:417px">
	<div class="center" style="margin:160px 0 100px 0">
		<h3 class="green">{{info}}</h3>
		<small>请点击[返回]，选择[Dashboard]回到首页!</small>
	</div>
	<hr>
	<div class="center">
		<a href="#" class="btn btn-grey" v-on:click="gobackClick"> <i class="ace-icon fa fa-arrow-left"></i> 返回 </a>
		<a href="#" class="btn btn-primary" v-on:click="godashboard"> <i class="ace-icon fa fa-tachometer"></i> Dashboard </a>
	</div>
</div><!-- /.widget-main -->
</div><!-- /.widget-body -->
</div>
</template>

<script>
export default {
	data:function(){
		return {
			info:'',
			goback:'dashboard'
		}
	},
	route:{
		data: function(transition){
			let info=transition.to.params.info;
			let goback=transition.to.params.goback;
			console.debug(goback);
			transition.next({info:info,goback:goback});
		}
	},
	methods:{
		gobackClick:function(){
			console.debug(this.goback);
			this.$router.go({name:this.goback });
		},
		godashboard:function(){
			this.$router.go({name:'dashboard' });
		}
	}
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
