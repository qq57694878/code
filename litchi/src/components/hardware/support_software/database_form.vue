<template>
  <div>
    <div class="space-4"></div>
    <form class="form-horizontal" role="form" id="myform">

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-4 control-label no-padding-right" for="form-field-1">数据库名称</label>
          <div class="col-sm-8">
            <input type="text" id="form-field-1" placeholder="建议使用数据库主要用途命名,例如:生产库" class="col-sm-12"
                   v-model="databaseForm.name">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-4 control-label no-padding-right" for="form-field-1">数据库类型</label>
          <div class="col-sm-8">
            <combobox name="cabinet_id" v-bind:model.sync="databaseForm.brand" v-bind:items="cabinets"></combobox>
          </div>
        </div>
      </div>


      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-4 control-label no-padding-right" for="form-field-1">版本号</label>
          <div class="col-sm-8">
            <input type="text" id="form-field-3" placeholder="" class="col-sm-12"
                   v-model="databaseForm.version">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-4 control-label no-padding-right" for="form-field-1">实例名</label>
          <div class="col-sm-8">
            <input type="text" id="form-field-5" placeholder="" class="col-sm-12"
                   v-model="databaseForm.instance_name">
          </div>

        </div>
      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-4 control-label no-padding-right" for="form-field-1">IP地址</label>
          <div class="col-sm-8">
            <input type="text" id="form-field-4" placeholder="" class="col-sm-12"
                   v-model="databaseForm.address">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-4 control-label no-padding-right" for="form-field-1">端口号</label>
          <div class="col-sm-8">
            <input type="text" id="form-field-6" placeholder="" class="col-sm-12"
                   v-model="databaseForm.port">
          </div>
        </div>
      </div>


      <!--<dual-list-box description="磁盘阵列" v-bind:model.sync="databaseForm.x86_list_data" name="storage_list_data"-->
      <!--v-bind:items="storages"></dual-list-box>-->

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
  import DualListBox from '../../common/DualListBox.vue'

  import {getInitForm} from '../../../vuex/modules/hardward/support_software/DatabaseGetters';
  import {reqFormInit} from '../../../vuex/modules/hardward/support_software/DatabaseActions';
  import {submitDataChange} from '../../../vuex/modules/hardward/support_software/DatabaseActions';
  import {reqQueryInit} from '../../../vuex/modules/hardward/support_software/DatabaseActions';

  import {reqComboboxData} from '../../../vuex/modules/common/ComboboxActions';

  import {reqDuailListboxData} from '../../../vuex/modules/common/DuailListBoxActions';


  export default {

    data: function () {
      return {
        rooms: [],
        cabinets: [],
        database_main_usage :{main_usage:'3'},
        storages: []
      }
    },
    vuex: {
      getters: {
        databaseForm: getInitForm
      },
      actions: {
        reqFormInit,
        reqComboboxData,
        submitDataChange,
        reqQueryInit,
        reqDuailListboxData
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

            let requestBody = self.databaseForm;
            console.info(requestBody);
            self.submitDataChange(self, requestBody);
          }
        });
      },
      goback: function () {

        this.reqQueryInit(1, 10);
        this.$router.go({
          name: 'support_database_list'
        });


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
//      this.reqDuailListboxData(this,'x86',{main_usage:'1'},function (comp, items) {
//        comp.x86s = items;
//        console.debug(items);
//      });

    }


  }
</script>

