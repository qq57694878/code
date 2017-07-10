<template>
  <div>
    <div class="space-4"></div>
    <form class="form-horizontal" role="form" id="myform">

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 机房</label>
          <div class="col-sm-9">
            <combobox name="room_id" v-bind:model.sync="x86Form.room_id" v-bind:items="rooms"></combobox>
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">机柜</label>
          <div class="col-sm-9">
            <combobox name="cabinet_id" v-bind:model.sync="x86Form.cabinet_id" v-bind:items="cabinets" v-on:change=""></combobox>
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">资产编号</label>
          <div class="col-sm-9">
            <input type="text" readonly="true" id="form-field-1" placeholder="由系统自动分配" class="col-sm-12"
                   v-model="x86Form.asset_num">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">服务器名称</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-2" placeholder="" class="col-sm-12"
                   v-model="x86Form.name">
          </div>
        </div>
      </div>


      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">品牌</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-3" placeholder="" class="col-sm-12"
                   v-model="x86Form.brand">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">型号</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-4" placeholder="" class="col-sm-12"
                   v-model="x86Form.type">
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">主要用途</label>
          <div class="col-sm-9">
            <combobox name="main_usage" v-bind:model.sync="x86Form.main_usage" v-bind:items="x86_main_usage"></combobox>
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">CPU型号</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-6" placeholder="" class="col-sm-12"
                   v-model="x86Form.cpu_type">
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">CPU主频</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-7" placeholder="" class="col-sm-12"
                   v-model="x86Form.cpu_frequency">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">CPU内核心数</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-8" placeholder="" class="col-sm-12"
                   v-model="x86Form.core_num">
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">当前CPU数</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-9" placeholder="" class="col-sm-12"
                   v-model="x86Form.cpu_current_num">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">最大CPU最大数</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-10" placeholder="" class="col-sm-12"
                   v-model="x86Form.cpu_max_num">
          </div>
        </div>
      </div>
      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">内存类型</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-11" placeholder="" class="col-sm-12"
                   v-model="x86Form.memory_type">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">内存容量</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-12" placeholder="" class="col-sm-12"
                   v-model="x86Form.memory_current_capacity">
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">内存最大容量</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-13" placeholder="" class="col-sm-12"
                   v-model="x86Form.memory_max_capacity">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">主板型号</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-14" placeholder="" class="col-sm-12"
                   v-model="x86Form.mainboard_type">
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">磁盘接口类型</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-15" placeholder="" class="col-sm-12"
                   v-model="x86Form.disk_interface_type">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">磁盘容量</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-16" placeholder="" class="col-sm-12"
                   v-model="x86Form.disk_size">
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">当前磁盘数</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-17" placeholder="" class="col-sm-12"
                   v-model="x86Form.disk_current_num">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">最大磁盘数</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-18" placeholder="" class="col-sm-12"
                   v-model="x86Form.disk_max_num">
          </div>
        </div>
      </div>
      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">RAID类型</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-19" placeholder="" class="col-sm-12"
                   v-model="x86Form.disk_raid">
          </div>
        </div>

      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">其他描述</label>
          <div class="col-sm-9">
            <textarea id="form-field-20" rows="4" class="col-sm-12" v-model="x86Form.description" >
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

  import {getInitForm} from '../../../vuex/modules/hardward/server/X86Getters';
  import {reqFormInit} from '../../../vuex/modules/hardward/server/X86Actions';
  import {submitDataChange} from '../../../vuex/modules/hardward/server/X86Actions';
  import {reqQueryInit} from '../../../vuex/modules/hardward/server/X86Actions';
  import {reqComboboxData} from '../../../vuex/modules/common/ComboboxActions';

  import {getCodeList} from '../../../vuex/modules/common/CodeTableGetters';


  export default {

    data: function () {
      return {
        rooms: [],
        cabinets: [],
        x86_main_usage:[]
      }
    },
    vuex: {
      getters: {
        x86Form: getInitForm,


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

            let requestBody = self.x86Form;
            console.info(requestBody);
            self.submitDataChange(self, requestBody);
          }
        });
      },
      goback: function () {

        this.reqQueryInit(1,10);
        this.$router.go({
          name: 'server_x86_list'
        });
      }
    },
    ready: function () {

      this.x86_main_usage = getCodeList('x86_main_usage');


      this.reqComboboxData(this, 'sysresource/room/get-room-list.do', function (comp, items) {
        comp.rooms = items;
      });
      this.reqComboboxData(this, 'hardware/cabinet/get-cabinet-list.do', function (comp, items) {
        comp.cabinets = items;
      });
      //comp.x86_main_usage =x86_main_usage;
    }


  }
</script>

