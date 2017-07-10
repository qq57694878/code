<template>
  <div class="page-header">
    <h1>
      巡检模板管理
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
          <div class="col-xs-2">
            <combobox placeholder="巡检类型" v-bind:items="vendors" v-on:change="change"></combobox>
          </div>

          <label class="col-xs-1 control-label no-padding-right no-margin-bottom" style="padding-top:6px" > 模版名称:</label>
          <div class="col-xs-2">
            <input type="text" placeholder="" v-model="tplName" >
          </div>

          <div class="col-xs-2">
            <button class="btn btn-info " style="border-radius:4px" type="button" v-on:click="query">
              <i class="ace-icon glyphicon glyphicon-search bigger-110"></i>
              查询
            </button>
          </div>
        </div>
      </div>

      <hr>

      <div class="row">
        <div class="col-xs-2">
          <button class="btn btn-info " style="border-radius:4px" type="button" v-on:click="addTpl">
            <i class="ace-icon glyphicon glyphicon-search bigger-110"></i>
            +新增模版
          </button>
        </div>
      </div>
    </div>
  </div>

  <hr>

  <div class="row">
    <div class="col-xs-12">
      <!--  ===================== separate line ===================== -->

      <div class="dataTables_wrapper form-inline no-footer">
        <table class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
          <thead>
          <tr>
            <th>巡检类型</th>
            <th>模版名称</th>
            <th>概要说明</th>
            <th>检查项目数</th>
            <th>模版状态</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="pcl in plcList.tpls">
            <td> {{CT.ri_type[pcl.ri_code]}}</td>
            <td> {{pcl.name}} </td>
            <td> {{pcl.description}} </td>
            <td> {{pcl.item_count}} </td>
            <td> {{CT.tpl_status[pcl.status]}} </td>

            <td>
              <div class="hidden-sm hidden-xs btn-group" v-if="pcl.status != '1'">
                <button class="btn btn-success" v-on:click="start(pcl.id)"> <i class="ace-icon fa fa-pencil-square-o"> </i> 启用 </button>
                <button class="btn btn-success" v-on:click="process(pcl.id)"> <i class="ace-icon fa fa-pencil-square-o"> </i> 编辑 </button>
              </div>

              <div class="hidden-sm hidden-xs btn-group" v-else>
                <button class="btn btn-success" v-on:click="stop(pcl.id)"> <i class="ace-icon fa fa-pencil-square-o"> </i> 停用 </button>
              </div>

              <div class="hidden-md hidden-lg" v-if="pcl.status!='1'">
                <div class="inline position-relative">
                  <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
                   <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                  </button>
                  <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">

                    <li>
                      <a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="启用" v-on:click="start(pcl.id)">
                        <span class="green">
                          <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                        </span>
                      </a>
                    </li>

                    <li>
                      <a href="#" class="tooltip-error" data-rel="tooltip" title="" data-original-title="编辑" v-on:click="process(pcl.id)">
                        <span class="red">
                          <i class="ace-icon fa fa-trash-o bigger-120"></i>
                        </span>
                      </a>
                    </li>
                  </ul>
                </div>
              </div>
              <div class="hidden-md hidden-lg" v-else>
                <div class="inline position-relative">
                  <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
                    <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                  </button>

                  <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                    <li>
                      <a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="停用" v-on:click="stop(pcl.id)">
                        <span class="green">
                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                        </span>
                      </a>
                    </li>
                  </ul>
                </div>
              </div>
            </td>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
  import myAlert from '../common/myAlert'
  import Combobox from '../common/Combobox'
  import {getCodeTable} from '../../vuex/modules/common/CodeTableGetters'
  import {getInspectionTplList} from '../../vuex/modules/inspection/InspectionGetters'
  import {reqInspectionTplList,clearInspectionTplDetail,setEnterInspectionTplGuard,setInspectionTplDetail} from '../../vuex/modules/inspection/InspectionActions'


  export default {
    data:function(){
      return {
        vendors:[],
        riType:'0',
        tplName:'',
      }
    },
    components: {
      myAlert,
      Combobox
    },
    vuex:{
      getters:{
        plcList:getInspectionTplList,
        CT: getCodeTable
      },
      actions:{
      reqInspectionTplList,
      clearInspectionTplDetail,
      setEnterInspectionTplGuard,
      setInspectionTplDetail
      }
    },
    methods:{
      change:function(Value){
        this.riType=Value;
      },

      query:function(){
        let self=this;
        let requestBody={ri_code:self.riType, name:self.tplName};
        console.debug(requestBody);
        this.reqInspectionTplList(self,requestBody);
      },

      addTpl:function(){
     //   this.clearInspectionTplDetail();
        var guard = {state:'0',id:'-1'};
			  this.setEnterInspectionTplGuard(guard);
			  this.$router.go({ name:'inspection_tpl_detail'});
      },

      start:function(id){

      },

      stop:function(id){

      },

      process:function(id){
        let self=this;
       // this.clearInspectionTplDetail();
        var guard = {state:'1',id:id};
			  this.setEnterInspectionTplGuard(guard);
			  this.setInspectionTplDetail(self, id);
			  this.$router.go({ name:'inspection_tpl_detail'});
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
