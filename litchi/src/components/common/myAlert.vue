
<template>
		<div class="alert animated" v-bind:class="alertType" v-if="show" transition="fade">
			<button type="button" class="close" data-dismiss="alert" v-on:click="close">
			<i class="ace-icon fa fa-times"></i>
			</button>

			<strong>
			<i class="ace-icon fa " v-bind:class="iconClass"></i>
				{{title}}
			</strong>
				{{content}}
			<br>
		</div>

</template>

<script>
export default {
	props:["alertType","content","show"],
	computed:{
		iconClass:function(){
			switch(this.alertType){
				case 'alert-danger':
					return 'fa-ban';
				case 'alert-success':
					return 'fa-check';
        case 'alert-warning':
          return 'fa-bell';
				default:
					return '';
			}
		},
		title:function(){
			switch(this.alertType){
				case 'alert-danger':
					return '失败! ';
				case 'alert-success':
					return '成功! ';
        case 'alert-warning':
          return '提示! ';
				default:
					return '';
			}
		}
	},
	methods:{
		close:function(){
			this.show=false;
		}
	},
	watch:{
		'show':function(val,oldVal){
			console.debug('show change ');
			clearTimeout(this.Timer);
			var self=this;
			if(this.show){
				this.Timer=setTimeout(function(){
						self.show=false;
					},10000);

			}
		}
	},
	ready:function(){
		var self=this;
		if(this.show){
			this.Timer=setTimeout(function(){
				clearTimeout(self.Timer);
				self.show=false;
			},10000);

		}

	}
}
</script>
