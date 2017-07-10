<template>
  <div>
    <div class="space-4"></div>
    <form class="form-horizontal" role="form" id="myform">

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 机房</label>
          <div class="col-sm-9">
            <combobox name="room_id" v-bind:model.sync="cabinetForm.room_id" v-bind:items="rooms"></combobox>
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">资产编号</label>
          <div class="col-sm-9">
            <input type="text" readonly="true" id="form-field-1" placeholder="由系统自动分配" class="col-sm-12"
                   v-model="cabinetForm.asset_num">
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">机柜名称</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-2" placeholder="机柜名称" class="col-sm-12"
                   v-model="cabinetForm.cabinet_name">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">机柜品牌</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-3" placeholder="品牌名称" class="col-sm-12" v-model="cabinetForm.brand">
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">机柜型号</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-4" placeholder="机柜型号" class="col-sm-12" v-model="cabinetForm.type">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">机柜高度</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-5" placeholder="单位mm" class="col-sm-12" v-model="cabinetForm.high">
          </div>
        </div>
      </div>


      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">机柜宽度</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-6" placeholder="单位mm" class="col-sm-12" v-model="cabinetForm.width">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">机柜深度</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-7" placeholder="单位mm" class="col-sm-12" v-model="cabinetForm.depth">
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">机柜容量</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-8" placeholder="单位U" class="col-sm-12" v-model="cabinetForm.unit">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">材料及工艺</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-9" placeholder="文字描述" class="col-sm-12" v-model="cabinetForm.materials">
          </div>
        </div>
      </div>

      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">最大承重</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-10" placeholder="单位kg" class="col-sm-12"
                   v-model="cabinetForm.load_capacity">
          </div>
        </div>
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">防护等级</label>
          <div class="col-sm-9">
            <input type="text" id="form-field-11" placeholder="文字描述" class="col-sm-12" v-model="cabinetForm.def_level">
          </div>
        </div>
      </div>


      <div class="form-group">
        <div class="box1 col-md-6">
          <label class="col-sm-3 control-label no-padding-right" for="form-field-1">其他描述</label>
          <div class="col-sm-9">
          <textarea id="form-field-12" rows="4" class="col-sm-12" v-model="cabinetForm.description">
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

  import {getInitForm} from '../../../vuex/modules/hardward/infrastructure/CabinetGetters';
  import {reqFormInit} from '../../../vuex/modules/hardward/infrastructure/CabinetActions';
  import {submitDataChange} from '../../../vuex/modules/hardward/infrastructure/CabinetActions';
  import {reqQueryInit} from '../../../vuex/modules/hardward/infrastructure/CabinetActions';
  import {reqComboboxData} from '../../../vuex/modules/common/ComboboxActions';

  import {getPageNum} from '../../../vuex/modules/hardward/infrastructure/CabinetGetters';

  export default {

    data: function () {
      return {
        rooms: []
      }
    },
    vuex: {
      getters: {
        cabinetForm: getInitForm,
        pageNum:getPageNum
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
            let requestBody = self.cabinetForm;
            self.submitDataChange(self, requestBody);
          }
        });
      },
      goback: function () {

        this.reqQueryInit();
        this.$router.go({
          name: 'cabinet_list'
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
    }


  }
</script>

