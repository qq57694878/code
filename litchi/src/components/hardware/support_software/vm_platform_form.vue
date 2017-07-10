<template>
  <div>
    <div class="space-4"></div>
    <form class="form-horizontal" role="form" id="myform">

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">虚拟平台名称</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-1" placeholder="建议使用主要用途命名" class="col-sm-12"
                   v-model="vmplatForm.name">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">品牌</label>
          <div class="col-sm-9">
            <combobox name="cabinet_id" v-bind:model.sync="vmplatForm.brand" v-bind:items="cabinets"></combobox>
          </div>
        </div>
      </div>


      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">版本号</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-3" placeholder="" class="col-sm-12"
                   v-model="vmplatForm.version">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">控制台</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-5" placeholder="" class="col-sm-12"
                   v-model="vmplatForm.console">
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


  import Combobox from '../../common/Combobox'

  import {getInitForm} from '../../../vuex/modules/hardward/support_software/DatabaseGetters';
  import {reqFormInit} from '../../../vuex/modules/hardward/support_software/DatabaseActions';
  import {submitDataChange} from '../../../vuex/modules/hardward/support_software/DatabaseActions';
  import {reqQueryInit} from '../../../vuex/modules/hardward/support_software/DatabaseActions';
  import {reqComboboxData} from '../../../vuex/modules/common/ComboboxActions';


  export default {

    data: function () {
      return {
        rooms: [],
        cabinets: []
      }
    },
    vuex: {
      getters: {
        vmplatForm: getInitForm
      },
      actions: {
        reqFormInit,
        reqComboboxData,
        submitDataChange,
        reqQueryInit
      }
    },
    components: {
      Combobox
    },
    methods: {
      submit: function () {
        let self = this;
        bootbox.confirm("确定提交吗?", function (result) {
          if (result) {

            let requestBody = self.vmplatForm;
            console.info(requestBody);
            self.submitDataChange(self, requestBody);
          }
        });
      },
      goback: function () {

        this.reqQueryInit(1, 10);
        $('#vm_plat a[href="#vm_plat2"]').tab('show');


      }
    },
    ready: function () {

      $('#htmleditor').summernote({
        height: 300,
        fontSize: 14,
        toolbar: [
          ['style', ['bold', 'italic', 'underline', 'clear']],
          ['fontsize', ['fontsize']],
          ['color', ['color']],
          ['para', ['style', 'ul', 'ol', 'paragraph']],
          ['insert', ['table', 'hr']],
          ['misc', ['undo', 'redo', 'fullscreen', 'codeview', 'help']],
        ]
      });
      this.reqComboboxData(this, 'sysresource/room/get-room-list.do', function (comp, items) {
        comp.rooms = items;
      });
      this.reqComboboxData(this, 'hardware/cabinet/get-cabinet-list.do', function (comp, items) {
        comp.cabinets = items;
      });
    }


  }
</script>

