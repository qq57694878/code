
import Vue from 'vue'

import {SET_IPARRAY,SET_IP_NETSELECTED, SET_IPNETARRAY,SET_IPNET,DEL_IPNET,SET_EDITNET,SET_ALERTOBJ} from '../../mutation-types'

const state = {
	ip_array:[],
	net_array:{
			// '1':{
			// 	id:'1',//
			// 	name:'局长办公司',//名称
			// 	description:'这个是很重要的网段',//用途
			// 	ip_add:'192.168.1.1',//
			// 	subnet_mask:'24',
			// 	gateway:'',
			// 	red_count:0,    //未规划的活动IP数量
			// 	green_count:0,  //规划的活动IP数量
			// 	yellow_count:0, //规划的非活动IP数量
			// 	gray_count:0,   //未规划的非活动IP数量
			// },
			// '2':{
			// 	id:'2',//
			// 	name:'局办公司',//名称
			// 	description:'这个是很重要的网段',//用途
			// 	ip_add:'192.168.1.2',//
			// 	subnet_mask:'24',
			// 	gateway:'',
			// 	red_count:0,    //未规划的活动IP数量
			// 	green_count:240,  //规划的活动IP数量
			// 	yellow_count:0, //规划的非活动IP数量
			// 	gray_count:0,   //未规划的非活动IP数量
			// }
		},
	net_selected:'-1',
	edit_net:{
		id:'',
		name:'',
		description:'',
		ip_add:'',
		subnet_mask:'',
		gateway:''
	},
	alertObj:{
		alertType:'',
		alertContent:'',
		alertShow:false
	}
}

const mutations = {
	[SET_IPARRAY] (state,ip_hosts) {
		var row=0;
		var col=0;
		state.ip_array=[];
		for(var i=0;i<ip_hosts.length;++i){
			row=parseInt(i / 20);
			col=i % 20;
			if(!state.ip_array[row])
			{
				state.ip_array[row]=[];
			}
			state.ip_array[row][col]=ip_hosts[i];
		}
	},
	[SET_IP_NETSELECTED] (state,netId) {
		state.net_selected=netId;
	},
	[SET_IPNETARRAY] (state,netList) {
		state.net_array={};
		for(var i=0;i<netList.length;++i){
			state.net_array[netList[i].id]=netList[i];
		}
	},
	[SET_IPNET] (state,net) {
		// state.net_array[net.id]=net;
		Vue.set(state.net_array,net.id,net);
	},
	[DEL_IPNET] (state,netId) {
		// delete state.net_array[netId]
		Vue.delete(state.net_array,netId);
	},
	[SET_EDITNET] (state,net) {
		state.edit_net=net;
	},
	[SET_ALERTOBJ] (state,obj) {
		state.alertObj=obj;
	}

}

export default {
	state,
	mutations
}
