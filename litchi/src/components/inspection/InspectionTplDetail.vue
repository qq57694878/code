<template>
  <div class="page-header">
    <h1>
      巡检模板
      <small style="font-size=16px" v-if="guard.state == '0'"> <i class="ace-icon fa fa-angle-double-right"></i> 新建页 </small>
      <small style="font-size=16px" v-if="guard.state == '1'"> <i class="ace-icon fa fa-angle-double-right"></i> 编辑页 </small>
      <small style="font-size=16px" v-if="guard.state == '2'"> <i class="ace-icon fa fa-angle-double-right"></i> 详细页 </small>
    </h1>
  </div>

  <div class="row">
    <div class="col-xs-12">
      <div class="row" >
        <div class="col-xs-12">
          <my-alert v-bind:alert-type="alertType" v-bind:content="alertContent" v-bind:show.sync="alertShow"></my-alert>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-12">
          <label class="col-xs-1 control-label no-padding-right" style="padding-top:6px" > 巡检类型:</label>
          <div class="col-xs-2" v-if="guard.state != '2'">
            <combobox placeholder="巡检类型" v-bind:items="vendors" v-bind:selectid="tplDetail.id" v-on:change="change"></combobox>
          </div>
          <div class="col-xs-2" v-else>
            <combobox placeholder="巡检类型" readonly v-bind:items="vendors" v-bind:selectid="tplDetail.id"></combobox>
          </div>
          <label class="col-xs-1 control-label no-padding-right no-margin-bottom"  style="padding-top:6px" > 模版名称:</label>
          <div class="col-xs-2" v-if="guard.state != '2'">
            <input type="text" placeholder="" v-model="tplDetail.name" >
          </div>
          <div class="col-xs-2" v-else>
            <input type="text" readonly placeholder="" v-model="tplDetail.name" >
          </div>
        </div>
      </div>

      <hr>

      <div class="row">
        <div class="col-xs-12">
          <label class="col-sm-3  control-label no-padding-right" style="padding-top:6px" > 该要说明:</label>
          <div class="col-sm-9" v-if="guard.state != '2'">
            <input type="text" placeholder="" v-model="tplDetail.description" >
          </div>
          <div class="col-sm-9" v-else>
            <input type="text" readonly placeholder="" v-model="tplDetail.description" >
          </div>
        </div>
      </div>
    </div>
  </div>

  <hr>

  <div class="row">
    <div class="col-xs-2">
      <button class="btn btn-info " style="border-radius:4px" type="button" v-on:click="additem">
        <i class="ace-icon glyphicon glyphicon-search bigger-110"></i>
        +新增巡检项
      </button>
    </div>
  </div>

  <hr>

  <div class="row">
    <div class="col-xs-12">
      <!--  ===================== separate line ===================== -->

      <div class="dataTables_wrapper form-inline no-footer">
        <table  class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
          <thead>
          <tr>
            <th>序号</th>
            <th>检查项目说明</th>
            <th v-show="guard.state != '2'">操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="item in tplDetail.items">
            <td> {{$index + 1}}</td>
            <td>{{item}}</td>
            <td v-show="guard.state != '2'">
              <div class="hidden-sm hidden-xs btn-group" >
                <button class="btn btn-success" v-on:click="processItem($index)"> <i class="ace-icon fa fa-pencil-square-o"> </i> 编辑 </button>
                <button class="btn btn-success" v-on:click="deleteItem($index)"> <i class="ace-icon fa fa-pencil-square-o"> </i> 删除 </button>
              </div>
            </td>
          </tbody>
        </table>
      </div>
    </div>
  </div>
  <div class="clearfix form-actions">
    <div class="col-md-offset-2 col-md-10">
      <button class="btn btn-info" type="button" v-on:click="saveItem" v-show="guard.state != '2'">
        <i class="ace-icon fa fa-check bigger-110"></i>
      保存
      </button>
      &nbsp; &nbsp; &nbsp;
      <button class="btn btn-info" type="button" v-on:click="startItem" v-show="guard.state != '2'">
       <i class="ace-icon fa fa-check bigger-110"></i>
      保存并启用
      </button>

      &nbsp; &nbsp; &nbsp;
      <button class="btn" type="reset" v-on:click="back">
        <i class="ace-icon fa fa-undo bigger-110"></i>
        返回
      </button>
    </div>
  </div>

</template>

<script>
  import myAlert from '../common/myAlert'
  import Combobox from '../common/Combobox'
  import {getCodeTable} from '../../vuex/modules/common/CodeTableGetters'
  import {getInspectionTplGuard,getInspectionTplDetail} from '../../vuex/modules/inspection/InspectionGetters'
  import {deleteOneItem,saveInspectionTpl} from '../../vuex/modules/inspection/InspectionActions'


  export default {
    data:function(){
      return {
        vendors:[]
      }
    },
    components: {
      myAlert,
      Combobox
    },
    vuex:{
      getters:{
        CT: getCodeTable,
        guard:getInspectionTplGuard,
        tplDetail:getInspectionTplDetail
      },
      actions:{
        deleteOneItem,
        saveInspectionTpl
      }
    },
    methods:{
      processItem:function(id){
        console.debug(id);
      },

      deleteItem:function(id){
        console.debug(id);
         let self=this;
        bootbox.confirm("您确定要删除巡检项目吗？", function(result) {
          if(result) {
            self.deleteOneItem(id);
          }
        });
      },

      back:function(){
        this.$router.go({
          name:'inspection_template'});
      },

      startItem:function(){
        console.debug('start');
        let self = this;
        bootbox.confirm("您确定要启用巡检模版吗？", function(result) {
         if(result) {
            self.saveInspectionTpl(self, self.tplDetail.id, self.tplDetail.name, self.tplDetail.ri_code, self.tplDetail.description, '1', self.tplDetail.items,
            function(comp){
              comp.$router.go({
              name:'inspection_template'});
              }
            );
        }
        });
      },

      saveItem:function(){
        let self = this;
        console.debug('save');
         bootbox.confirm("您确定要保存巡检模版吗？", function(result) {
         if(result) {
            self.saveInspectionTpl(self, self.tplDetail.id, self.tplDetail.name, self.tplDetail.ri_code, self.tplDetail.description, '0', self.tplDetail.items);

        }
        });
      },

      additem:function(){
        console.debug('additem');
      },

      change:function(Value){
        this.tplDetail.ri_code=Value;
      }
    },

    ready:function(){
      var arr = this.CT.ri_type;
			for(var i in arr){
				var data = {
								code:i,
								value:arr[i]
							};

				this.vendors.push(data);
			}
    }
  }

</script>
