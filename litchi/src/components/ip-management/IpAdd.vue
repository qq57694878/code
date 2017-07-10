<template>
  <div class="page-header">
    <h1>
      IP地址管理
    </h1>
  </div>
  <div class="row">
    <div class="col-xs-12">
      <!--  ===================== separate line ===================== -->
		<validator name="validation">
		<form class="form-horizontal" role="form" id="myform">
		
		<div class="form-group">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-1">网段名称</label>
			<div class="col-sm-8">
			<input type="text" name="name" id="form-field-1" placeholder="" class="col-sm-12" v-validate:name="['required']" v-model="net.name">
			</div>
			<div class="help-block"> <span v-if="$validation.name.required" style="color:#d16e6c">*必填</span> </div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-2">网段名称</label>
			<div class="col-sm-8">
			<input type="text" name="description" id="form-field-2" placeholder="" class="col-sm-12" v-model="net.descritpion" >
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-3">IP</label>
			<div class="col-sm-8">
			<input type="text" name="ip" id="form-field-3" placeholder="" class="col-sm-12" data-inputmask="'mask':'9{1,3}.9{1,3}.9{1,3}.9{1,3}'" v-validate:ip="['required']" v-model="net.ip_add">
			</div>
			<div class="help-block"> <span v-if="$validation.ip.required" style="color:#d16e6c">*必填</span> </div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-4">NETMASK</label>
			<div class="col-sm-8">
			<input type="text" name="netmask" id="form-field-4" placeholder="" class="col-sm-12" data-inputmask="'mask':'9{1,3}.9{1,3}.9{1,3}.9{1,3}'" v-validate:netmask="['required']" v-model="net.subnet_mask">
			</div>
			<div class="help-block"> <span v-if="$validation.netmask.required" style="color:#d16e6c">*必填</span> </div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label no-padding-right" for="form-field-5">Gateway</label>
			<div class="col-sm-8">
			<input type="text" name="gateway" id="form-field-5" placeholder="" class="col-sm-12" data-inputmask="'mask':'9{1,3}.9{1,3}.9{1,3}.9{1,3}'" v-validate:gateway="['required']" v-model="net.gateway">
			</div>
			<div class="help-block"> <span v-if="$validation.gateway.required" style="color:#d16e6c">*必填</span> </div>
		</div>
		
		<div class="space-4"></div>
		<div class="clearfix form-actions">
			<div class="col-md-offset-2 col-md-10">
				<button class="btn btn-light" type="button" v-on:click="ret">
					<i class="ace-icon fa fa-undo bigger-110"></i>
					返回	
				</button>

				&nbsp; &nbsp; &nbsp;
				<button class="btn btn-info" type="button" v-on:click="submit">
					<i class="ace-icon fa fa-check bigger-110"></i>
					提交
				</button>

				&nbsp; &nbsp; &nbsp;
				<button class="btn" type="reset">
					<i class="ace-icon fa fa-undo bigger-110"></i>
					清空
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

import {getIpArray, getIpNet, getIpNetSelected, getIpEditNet} from '../../vuex/modules/ip-management/IpGetters'
import {reqIpArray, selIpNet, reqIpnet, reqIpnetSave, reqIpnetDel,setAlertObj } from '../../vuex/modules/ip-management/IpActions'

export default {
	vuex: {
      getters: {
		net:getIpEditNet
      },
      actions: {
        reqIpnetSave,
		setAlertObj 
      }
    },
	methods:{
		submit:function(){
			this.reqIpnetSave(this, this.net,function(comp){
				comp.setAlertObj(comp,{
					alertType:'alert-success',
					alertContent:'保存成功！',
					alertShow:true
				});
				comp.$router.go({ name:'ip_man' });
			},function(comp){
				comp.setAlertObj(comp,{
					alertType:'alert-danger',
					alertContent:'保存失败！',
					alertShow:true
				});
				comp.$router.go({ name:'ip_man' });
			});
		},
	   ret:function(){
			this.$router.go({ name:'ip_man' });
	   }
	},
	ready:function(){
		$(':input').inputmask();
	}
}
</script>

<style scoped>
  
</style>
