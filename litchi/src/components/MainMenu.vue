
<template>
	<ul class="nav nav-list">
		<li >
			<a v-link=" '/dashboard' " class="active">
				<i class="menu-icon fa fa-tachometer"></i>
				<span class="menu-text">Dashboard</span>
			</a>

			<b class="arrow"></b>
		</li>
		<li v-for="p in mainmenus" >
			<a v-link="p.path" class="dropdown-toggle">
			<!-- <a class="dropdown-toggle"> -->
				<i class="menu-icon fa" v-bind:class="p.icon"></i>
					<span class="menu-text">
						{{p.name}}
					</span>

				<b v-if="p.children" class="arrow fa fa-angle-down"></b>
			</a>
			<b v-if="p.children" class="arrow"></b>

			<ul v-if="p.children" class="submenu">
				<li v-for="psub in p.children" >
					<a v-link="psub.path" >
						<i class="menu-icon fa fa-caret-right"></i>

						{{psub.name}}
						<b v-if="psub.children" class="arrow fa fa-angle-down"></b>
					</a>

				</li>
			</ul>
		</li>
	</ul>
</template>

<script>
import {getMainMenu} from '../vuex/modules/authentication/MainMenuGetters'
export default {
  vuex: {
  	getters: {
	//	text:({MainMenu})=>MainMenu.text,
	//	mainmenus:({MainMenu})=>MainMenu.menu
	//	mainmenus:function(store) {
		//	console.debug(a);
	//	  return store.MainMenu.menu
	// }

	mainmenus:getMainMenu

	}
  }
}
</script>

