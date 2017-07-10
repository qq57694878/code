<template>
  <div>
    <div class="space-4"></div>
    <form class="form-horizontal" role="form" id="myform">


      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-3">ip地址</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-3" placeholder="" class="col-sm-12"
                   v-model="ServerIpForm.ip_add">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-4">子网掩码</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-4" placeholder="" class="col-sm-12"
                   v-model="ServerIpForm.subnet_mask">
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-5">网关地址</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-5" placeholder="" class="col-sm-12"
                   v-model="ServerIpForm.gateway">
          </div>
        </div>

      </div>
      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-6">MAC地址</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-6" placeholder="" class="col-sm-12" readonly="true"
                   v-model="ServerIpForm.mac">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-7">刷新时间</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-7" placeholder="" class="col-sm-12" readonly="true"
                   v-model="ServerIpForm.mac_fresh_time">
          </div>
        </div>
      </div>


      <div class="clearfix form-actions">
        <div class="col-md-offset-1 col-md-6">
          <button class="btn" type="button" v-on:click="goback">
            <i class="fa fa-arrow-left"></i>
            返回
          </button>

          <button class="btn btn-primary" type="button" v-on:click="submit">
            <i class="ace-icon fa fa-floppy-o"></i>
            提交
          </button>
        </div>
      </div>
    </form>

  </div>
</template>

<script>


  import {getInitForm} from '../../vuex/modules/ip-management/ServerIpGetters';

  import {reqFormInit} from '../../vuex/modules/ip-management/ServerIpActions';

  import {submitDataChange} from '../../vuex/modules/ip-management/ServerIpActions';

  import {reqQueryInit} from '../../vuex/modules/ip-management/ServerIpActions';

  export default {

    data: function () {
      return {
        biz_type: '',//业务类型，表名
        biz_id: '',//业务表主键
        ip_id: ''
      }
    },
    route: {
      data: function (transition) {
        let biz_id = transition.to.params.biz_id;
        let biz_type = transition.to.params.biz_type;
        let ip_id = transition.to.params.ip_id;
        transition.next({biz_id: biz_id, biz_type: biz_type, ip_id: ip_id});
      }
    },
    vuex: {
      getters: {
        ServerIpForm: getInitForm
      },
      actions: {
        reqFormInit,
        submitDataChange,
        reqQueryInit
      }
    },

    methods: {
      submit: function () {
        let self = this;
        bootbox.confirm("确定提交吗?", function (result) {
          if (result) {

            let requestBody = self.ServerIpForm;
            self.submitDataChange(self, requestBody,self.biz_id, self.biz_type);
          }
        });
      },
      goback: function () {
        this.reqQueryInit(this.biz_id, this.biz_type);
        this.$router.go({
          name: 'server_ip_list',
          params: {
            biz_type: this.biz_type,//业务类型，表名
            biz_id: this.biz_id//业务表主键
          }
        });
      }
    },
    ready: function () {
      this.reqFormInit(this.ip_id);
    }


  }
</script>

