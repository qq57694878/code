<template>
  <div>
    <div class="space-4"></div>
    <form class="form-horizontal" role="form" id="myform">

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 机房</label>
          <div class="col-sm-9">
            <combobox name="room_id" v-bind:model.sync="storageForm.room_id" v-bind:items="rooms"></combobox>
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">机柜</label>
          <div class="col-sm-9">
            <combobox name="cabinet_id" v-bind:model.sync="storageForm.cabinet_id" v-bind:items="cabinets"></combobox>
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">资产编号</label>
          <div class="col-sm-9">
            <input type="text" readonly="true" id="form-field-1" placeholder="由系统自动分配" class="col-sm-12"
                   v-model="storageForm.asset_num">
          </div>
        </div>

      </div>


      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">品牌</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-3" placeholder="" class="col-sm-12"
                   v-model="storageForm.brand">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">型号</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-4" placeholder="" class="col-sm-12"
                   v-model="storageForm.type">
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">主要用途</label>
          <div class="col-sm-9">
            <combobox name="main_usage" v-bind:model.sync="storageForm.main_usage"
                      v-bind:items="x86_main_usage"></combobox>
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">磁盘接口类型</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-15" placeholder="" class="col-sm-12"
                   v-model="storageForm.disk_interface_type">
          </div>
        </div>
      </div>


      <div class="form-group">

        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">磁盘容量</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-16" placeholder="" class="col-sm-12"
                   v-model="storageForm.disk_size">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">RAID类型</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-19" placeholder="" class="col-sm-12"
                   v-model="storageForm.disk_raid">
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">当前磁盘数</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-17" placeholder="" class="col-sm-12"
                   v-model="storageForm.disk_current_num">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">最大磁盘数</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-18" placeholder="" class="col-sm-12"
                   v-model="storageForm.disk_max_num">
          </div>
        </div>
      </div>
      <div class="form-group">


      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">其他描述</label>
          <div class="col-sm-9">
            <textarea id="form-field-20" rows="4" class="col-sm-12" v-model="storageForm.description">
            </textarea>
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

  import {getInitForm} from '../../../vuex/modules/hardward/server/StorageGetters';
  import {reqFormInit} from '../../../vuex/modules/hardward/server/StorageActions';
  import {submitDataChange} from '../../../vuex/modules/hardward/server/StorageActions';
  import {reqQueryInit} from '../../../vuex/modules/hardward/server/StorageActions';
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
        storageForm: getInitForm
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

            let requestBody = self.storageForm;
            console.info(requestBody);
            self.submitDataChange(self, requestBody);
          }
        });
      },
      goback: function () {
        //TODO 返回数据刷新
        this.reqQueryInit(1,10);
        this.$router.go({
          name: 'server_storage_list'
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
    }


  }
</script>

