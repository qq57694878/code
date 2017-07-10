
<template>
	<div>
		<input type="text" v-bind:name="name" v-bind:id="tree_combobox_id" readonly v-model="selectname" placeholder="点击选择..." v-on:click="showMenu">
	</div>
	<div v-bind:id="ztree_wrap_id"  style="display:none;position:absolute;z-index:1001">
		<ul v-bind:id="ztree_id" class="ztree" style="margin-top:0;height:280px;background-color:#f5f5f5;border:1px solid #dddddd;overflow:auto;">
		</ul>
	</div>
</template>

<script>

import {S4} from '../../vuex/modules/common/guid'
import {ajaxReq} from '../../vuex/modules/common/ajaxReq'
const getOrgTree= (store,component,data,success,failure) => {
	ajaxReq(store,{
					url:"organization/gettree.do", 
				    body:data,
		           	success:function(response){  
						let responseJson=response.json();
						if(success)success(component,responseJson);
					},
					failure:function(){
						if(failure)failure(component);
					} 
	});
}



export default {
	props:["name","selectid","selectname","query"],
	data:function(){
		return {
			tree_combobox_id:'',
			ztree_id:'',
			ztree_wrap_id:'',
		}
	},
	vuex:{
		actions:{
			getOrgTree
		}
	},
	computed:{
	},
	methods:{
		showMenu:function(){
			// var cityObj=$('#comboboxTree');
			// var cityOffset=$('#comboboxTree').offset();
			//$('#menuContent').css({left:cityOffset.left+"px",top:cityOffset.top+cityObj.outerHeight()+"px"}).slideDown("fast");

			$('#'+this.ztree_wrap_id).css({left:"12px",top:"35px"}).slideDown("fast");
			$('#'+this.ztree_id).width($('#'+this.tree_combobox_id).width());
			$("body").bind("mousedown",this.onBodyDown);
		},
		hideMenu:function(){
			$("#"+this.ztree_wrap_id).fadeOut("fast");
			$("body").unbind("mousedown",this.onBodyDown);
		},
		onBodyDown:function(event){
			// console.debug('onbodydown');
			if(!(event.target.id ==this.tree_combobox_id || event.target.id ==this.ztree_wrap_id || $(event.target).parents("#"+this.ztree_wrap_id).length>0)){
				// console.debug(this.tree_combobox_id);
				// console.debug(this.ztree_wrap_id);
				this.hideMenu();
			}
		}
	},
	watch:{
	},
	created:function(){
		let s4=S4();
		this.tree_combobox_id='tree-combobox-'+s4;
		this.ztree_id='ztree-'+s4;
		this.ztree_wrap_id='ztree-wrap-'+s4;

	},
	ready:function(){
		var self = this;
		var setting = {
	//		check:{
	//				 enable:true,
	//				 chkStyle:"radio",
	//				 radioType:"all"
	//			  },
			view:{
					 dblClickExpand:false
				 },
			data:{
					simpleData:{
						enable:true
				   }
				 },
			callback:{
					 onClick:onClick,
					// onCheck:onCheck
					 }
		};

		function onClick(e,treeId,treeNode){
	//		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	//		zTree.checkNode(treeNode,!treeNode.checked,null,true);
		//	self.model = v;
			self.selectname = treeNode.name;
			self.selectid = treeNode.id;
			self.hideMenu();

			self.$dispatch('change',self.selectid,self.selectname);
		}	

		// var zNodes = [
		// 	{ id:1, name:'beijing'},
		// 	{ id:1, name:'beijing'}
		// ];
		var queryObj=self.query?self.query:{};
		// console.debug(queryObj);
		
		self.getOrgTree(self,queryObj,function(comp,treedata){
			$.fn.zTree.init($("#"+self.ztree_id), setting,treedata.data.root);
			// console.debug(treedata);
			// $.fn.zTree.init($("#"+this.ztree_id), setting,zNodes);
		},function(){
			$.dispatch('error','组织机构下拉树数据加载失败！');
		});
	}
}
</script>

<style scoped>
#treeDemo{
	background-color:#f5f5f5;
	border:1px solid #dddddd;
	overflow:auto;
}

</style>
