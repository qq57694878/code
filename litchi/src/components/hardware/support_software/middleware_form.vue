<template>
  <div>

    <form class="form-horizontal" role="form" id="myform">

      <div class="form-group">
        <label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 机房</label>
        <div class="col-sm-5">
          <combobox name="business_app" v-bind:model="task.form_data.business_app"
                    v-bind:items="businessApps"></combobox>
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-1 control-label no-padding-right" for="form-field-1">资产编号</label>
        <div class="col-sm-9">
          <input type="text" readonly="true" id="form-field-1" placeholder="由系统自动分配" class="col-sm-8" v-model="">
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-1 control-label no-padding-right" for="form-field-1">机柜名称</label>
        <div class="col-sm-9">
          <input type="text" id="form-field-2" placeholder="机柜名称" class="col-sm-8" v-model="">
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-1 control-label no-padding-right" for="form-field-1">机柜品牌</label>
        <div class="col-sm-9">
          <input type="text" id="form-field-3" placeholder="品牌名称" class="col-sm-8" v-model="">
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-1 control-label no-padding-right" for="form-field-1">机柜型号</label>
        <div class="col-sm-9">
          <input type="text" id="form-field-4" placeholder="机柜型号" class="col-sm-8" v-model="">
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-1 control-label no-padding-right" for="form-field-1">机柜高度</label>
        <div class="col-sm-9">
          <input type="text" id="form-field-5" placeholder="单位mm" class="col-sm-8" v-model="">
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-1 control-label no-padding-right" for="form-field-1">机柜宽度</label>
        <div class="col-sm-9">
          <input type="text" id="form-field-6" placeholder="单位mm" class="col-sm-8" v-model="">
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-1 control-label no-padding-right" for="form-field-1">机柜深度</label>
        <div class="col-sm-9">
          <input type="text" id="form-field-7" placeholder="单位mm" class="col-sm-8" v-model="">
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-1 control-label no-padding-right" for="form-field-1">机柜容量</label>
        <div class="col-sm-9">
          <input type="text" id="form-field-8" placeholder="单位U" class="col-sm-8" v-model="">
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-1 control-label no-padding-right" for="form-field-1">材料及工艺</label>
        <div class="col-sm-9">
          <input type="text" id="form-field-9" placeholder="文字描述" class="col-sm-8" v-model="">
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-1 control-label no-padding-right" for="form-field-1">最大承重</label>
        <div class="col-sm-9">
          <input type="text" id="form-field-10" placeholder="单位kg" class="col-sm-8" v-model="">
        </div>
      </div>

      <div class="form-group">
        <label class="col-sm-1 control-label no-padding-right" for="form-field-1">防护等级</label>
        <div class="col-sm-9">
          <input type="text" id="form-field-11" placeholder="文字描述" class="col-sm-8" v-model="">
        </div>
      </div>




      <div class="form-group">
        <label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 详细参数</label>
        <div class="col-sm-9">
          <div id="htmleditor"></div>
        </div>
      </div>

      <div class="space-4"></div>
      <div class="clearfix form-actions">
        <div class="col-md-offset-1 col-md-1">
          <button class="btn btn-info" type="button" >
            <i class="ace-icon fa fa-check bigger-110"></i>
            提交
          </button>


        </div>
      </div>
    </form>

  </div>
</template>

<script>


  import Combobox from '../../common/Combobox'

  export default {


    ready: function () {


      //详细内容初始化
      $('#htmleditor').summernote({
        height: 200,
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


      var callbackSuccess = function (responseJson) {
        $('#htmleditor').summernote('code', responseJson.data.form_data.description);
      }
      this.getDataChange(this.taskId, this.taskType, this.instanceId, callbackSuccess);
      this.reqComboboxData('sysresource/biz_app/get-app-list.do');

    }

  }
</script>

