<template>
<div class="page-header">
	<h1>
		通知	
	</h1>
</div>
<div class="row">
	<div class="col-xs-12">
		<!-- <div class="row" > -->
		<!-- 	<div class="col-xs-12"> -->
		<!-- 		 <my-alert v-bind:alert-type="alertType" v-bind:content="alertContent" v-bind:show.sync="alertShow"></my-alert> -->
		<!-- 	</div> -->
		<!-- </div> -->
		<div class="space-4"></div>
		<div class="tabbable" id="">
			<ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="myTab">
				<li class="active">
				<a data-toggle="tab" v-on:click="receive">
					<i class="green ace-icon fa fa-download bigger-120"></i>
					收件箱
				</a>
				</li>
				<li id='cabinet'>
				<a data-toggle="tab" v-on:click="send">
					<i class="green ace-icon fa fa-edit bigger-120"></i>
					发件箱
				</a>
				</li>
			</ul>
			<div class="tab-content">
				<router-view></router-view>
			</div>
		</div>
		
	</div>
</div>
</template>

<script>
import Pages from '../common/Pages'
import myAlert from '../common/myAlert'

export default {
	components: {
		Pages,
		myAlert
	},
	vuex:{
		getters:{
			//CT:getCodeTable
		},
		actions:{
		}
	},
	methods:{
		receive:function(){
			this.$router.go({ name:'receive_notice'});
		},
		send:function(){
			this.$router.go({ name:'send_notice'});
		}

	},
	ready:function(){
	//	this.reqRoomList(1,10);
	}
}
</script>
