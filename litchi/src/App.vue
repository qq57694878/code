<template>
<div id="app">
	<!-- #section:basics/navbar.layout -->
	<navbar></navbar>
	<!-- /section:basics/navbar.layout -->

	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try{ace.settings.check('main-container' , 'fixed')}catch(e){}
		</script>

		<!-- #section:basics/sidebar -->
			<sidebar></sidebar>
		<!-- /section:basics/sidebar -->

		<!-- .main-content -->
		<div class="main-content">

			<!-- /.breadcrumb -->
			<!--
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
				try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
				</script>
				<ul class="breadcrumb">
				<li>
				<i class="ace-icon fa fa-home home-icon"></i>
				<a href="#">Home</a>
				</li>

				<li>
				<a href="#">Tables</a>
				</li>
				<li class="active">Simple &amp; Dynamic</li>
				</ul>
			</div>
			-->
			<!-- /.breadcrumb -->

			<!-- .page-content -->
			<div class="page-content">

				<router-view></router-view>
				<!-- <work-flows></work-flows> -->
				<!-- <my-table></my-table> -->
		
			</div>
			<!-- /.page-content -->

		</div>
		<!-- /.main-content -->

		<!-- footer -->
		<my-footer></my-footer>
		<!-- /footer -->

		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
		<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
		</div>
		<!-- /.main-container -->
		</div>

</template>

<script>

import Navbar from './components/Navbar'
import Sidebar from './components/Sidebar'
import MyFooter from './components/Footer'

export default {
	components: {
		Navbar,
		Sidebar,
		MyFooter
	}
}
</script>

<style>
</style>
