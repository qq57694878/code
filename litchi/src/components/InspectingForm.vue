
<template>
<div>
	<form class="form-horizontal" role="form">

		<h3 class="header smaller lighter green">Check List</h3>
		<div class="control-group">
			<div class="checkbox">
			<label>
			<input name="form-field-checkbox" class="ace ace-checkbox-2" type="checkbox">
			<span class="lbl"> choice 1</span>
			</label>
			</div>

			<div class="checkbox">
			<label>
			<input name="form-field-checkbox" class="ace ace-checkbox-2" type="checkbox">
			<span class="lbl"> choice 2</span>
			</label>
			</div>

			<div class="checkbox">
			<label>
			<input name="form-field-checkbox" class="ace ace-checkbox-2" type="checkbox">
			<span class="lbl"> choice 3</span>
			</label>
			</div>


			<div class="checkbox">
			<label>
			<input name="form-field-checkbox" class="ace ace-checkbox-2" type="checkbox">
			<span class="lbl"> choice 4</span>
			</label>
			</div>

		</div>
	
		<h3 class="header smaller lighter green">巡检详情</h3>
		<div class="form-group">
			<div class="col-sm-12">
				<div id="htmleditor" ></div>
			</div>
		</div>

		<div class="space-4"></div>
		<div class="clearfix form-actions">
			<div class="col-md-offset-3 col-md-9">
				<button class="btn btn-info" type="button" v-on:click="submit">
					<i class="ace-icon fa fa-check bigger-110"></i>
					提交
				</button>

				&nbsp; &nbsp; &nbsp;
				<button class="btn" type="reset">
					<i class="ace-icon fa fa-undo bigger-110"></i>
					清空
				</button>
				&nbsp; &nbsp; &nbsp;
				<button class="btn btn-warning" type="button">
					<i class="ace-icon fa fa-refresh bigger-110"></i>
					返回
				</button>

			
			</div>
		</div>
	</form>

</div>
</template>

<script>

import {getBusinessApps} from '../vuex/modules/resource-management/BusinessAppGetters'
export default {
	vuex:{
		getters:{
			businessApps:getBusinessApps
		}
	},
	methods:{
		submit:function() {
			bootbox.prompt("请输入留言", function(result) {
				if (result === null) {
							
				} else {
							
				}
			});
		}
	},
	ready:function(){

		// var formData=new FormData();

		$('#htmleditor').summernote({
			height:300,
			fontSize:14,
			toolbar: [
				['style', ['bold', 'italic', 'underline', 'clear']],
				['fontsize', ['fontsize']],
				['color', ['color']],
				['para', ['style','ul', 'ol', 'paragraph']],
				['insert', ['table','hr']],
				['misc', ['undo','redo','fullscreen','codeview','help']],
			]
		});


	}

}
</script>

