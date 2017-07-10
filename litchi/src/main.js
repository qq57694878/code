import Vue from 'vue'
import store from './vuex/store'
import VueRouter from 'vue-router'
import VueResource from 'vue-resource'
import Validator from 'vue-validator'

//路由配置-begin
import {configRouter} from './route-config'
import {hd_configRouter} from './hardware-route-config'
//路由配置-end

import App from './App'
import Login from './Login'

import { getAuthentication} from './vuex/modules/authentication/LoginGetters'
import {login,getJWTtoken,verify} from './vuex/modules/authentication/LoginActions'
/* eslint-disable no-new */

import { echartsThemeConfig} from './echarts-theme-config'

console.debug("=============== console debug log test ===================");
echartsThemeConfig();

Vue.use(VueRouter)
Vue.use(VueResource)
Vue.use(Validator)

Vue.http.options.root = '/api';
// Vue.http.options.root = '';
// authentication header zabbix api 不兼容
// Vue.http.headers.common['authentication'] = getJWTtoken();

Vue.transition('fade', {
	  enterClass: 'fadeIn',
	  leaveClass: 'fadeOut'
})

// Vue.filter('nullValue',function(value,newValue){
// 	if(value)
// 		return value;
// 	else
// 		console.debug('filter nullValue ====================> '+value);
// 		return newValue;
// });

// const mainapp = new Vue({
const mainapp = Vue.extend({
  // el: 'body',
  store,
  // data: {
	//maybe something
  // },
  computed:{
	//maybe something
  },
  components: {
	  App,
	  Login
  },
  vuex:{
	getters:{
		getAuthentication
	},
	actions:{
		verify
	}
  },
  ready:function(){
	  this.verify(this);
  }
  // http:{
	// root:'/',
	// headers:{
		// Authorization:getJWTtoken
	// }
  // }
})

const router = new VueRouter({
	hashbang:false,
	abstract:true
})

configRouter(router);
hd_configRouter(router);

router.start(mainapp,'body')

// import {zabbixLogin, zabbixHostCreate, zabbixHostGet} from './vuex/modules/common/ajaxZabbix'
// zabbixLogin('admin','zabbix',function(){
// 	// zabbixHostCreate("127.0.0.1");
// 	zabbixHostGet([10084]);
// });


