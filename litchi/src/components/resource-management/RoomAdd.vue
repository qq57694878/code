<template>
<div class="page-header">
	<h1>
		机房	
	<small style="font-size=16px"> <i class="ace-icon fa fa-angle-double-right"></i> 新建/修改页 </small>
	</h1>
</div>
<div class="row">
	<div class="col-xs-12">
	<!--  ===================== separate line ===================== -->
		
	<div class="row" >
		<div class="col-xs-12">
			 <my-alert v-bind:alert-type="alertType" v-bind:content="alertContent" v-bind:show.sync="alertShow"></my-alert>
		</div>
	</div>
		
	<validator name="validation">
	<form class="form-horizontal" role="form" id="myform">
		
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 机房名称</label>
			<div class="col-sm-6">
			<input type="text" id="form-field-1" name="room_name" placeholder="" class="col-sm-12" v-model="roomdetail.name" v-validate:room_name="['required']">
			</div>
			<div class="help-block"> <span v-if="$validation.room_name.required" style="color:#d16e6c">*必填</span> </div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 机房描述</label>
			<div class="col-sm-6">
			<input type="text" id="form-field-1" placeholder="" class="col-sm-12" v-model="roomdetail.description">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 面积</label>
			<div class="col-sm-6">
			<input type="text" id="form-field-1" placeholder="" class="col-sm-12" v-model="roomdetail.area">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 竣工日期</label>
			<div class="col-sm-6">
				<input type="text" class="form-control date-picker"class="col-sm-12"  placeholder="" id="form-field-1" data-date-format="yyyy-mm-dd" v-model="roomdetail.complete_date">
			<!-- <input type="text" id="form-field-1" placeholder="" class="col-sm-12" v-model="roomdetail.complete_date"> -->
			</div>
		</div>
		
		<div class="space-4"></div>
		<div class="clearfix form-actions">
			<div class="col-md-offset-2 col-md-10">
				<button class="btn btn-info" type="button" v-on:click="submit">
					<i class="ace-icon fa fa-check bigger-110"></i>
					保存
				</button>

				&nbsp; &nbsp; &nbsp;
				<button class="btn" type="reset" v-on:click="back">
					<i class="ace-icon fa fa-undo bigger-110"></i>
					返回
				</button>

			</div>
		</div>
	</form>
	</validator>
	<!--  ===================== separate line ===================== -->
		
	</div>
</div>
</template>
		
<script>
import myAlert from '../common/myAlert'
import {getOneRoomDetail} from '../../vuex/modules/resource-management/RoomGetters'
import {saveOneRoom} from '../../vuex/modules/resource-management/RoomActions'
		
export default {
	data:function(){
		return {
			alertShow:false,
			alertType:'alert-success',
			alertContent:'aaaa'
		}
	},
	components: {
		myAlert
	},
	vuex:{
		getters:{
			roomdetail:getOneRoomDetail
			//CT:getCodeTable
		},
		actions:{
			saveOneRoom
		}
	},
	methods:{
		back:function(){
			//console.debug('myform go');
				this.$router.go({
                                  name:'room'});
		},
		submit:function(){
			if(this.$validation.valid){
			let self=this;
			let requestBody={ id:self.roomdetail.id,name:self.roomdetail.name,description:self.roomdetail.description,area:self.roomdetail.area,complete_date:self.roomdetail.complete_date};
			//this.saveOneRoom(self,self.roomdetail.id,self.roomdetail.name,self.roomdetail.description,self.roomdetail.area,self.roomdetail.complete_date);
			this.saveOneRoom(self,requestBody);
			}
			   }
	},
	ready:function(){
		 console.debug('AAAAAAAAAAAAAAAAAAAAAAAA');
		
		//datepicker plugin
		$('.date-picker').datepicker({
			autoclose: true,
			todayHighlight: true
		}) .next().on(ace.click_event, function(){
			$(this).prev().focus();
		});

		  $('input-daterange').datepicker({autoclose:true});
				  
		   //to translate the daterange picker, please copy the "examples/daterange-fr.js" contents here before initialization
		  /*
		   $('input[name=date-range-picker]').daterangepicker({
					  'applyClass' : 'btn-sm btn-success',
					  'cancelClass' : 'btn-sm btn-default',
					  locale: {
						   applyLabel: 'Apply',
						   cancelLabel: 'Cancel',
					   }
				   })
		   .prev().on(ace.click_event, function(){
					   $(this).next().focus();
				   });*/
		  }

}
