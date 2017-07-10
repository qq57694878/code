<template>
  <div class="page-header">
    <h1>
      巡检计划管理
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
        <div class="widget-box">
          <div class="widget-body">
            <div class="widget-main">
              <div class="form-group">
                <div class="row">
                  <div class="col-xs-12">
                    <div class="tabbable">
                      <ul class="nav nav-tabs padding-18 tab-size-bigger" >
                        <template v-for="(key,val) in plansList">
                          <li  class= "active" v-if="$index == 0">
                            <a data-toggle="tab" href=#{{key}} aria-expanded="true" v-on:click="tabSel(key)">{{CT.ri_type[key]}}</a>
                          </li>
                          <li v-else>
                            <a data-toggle="tab" href=#{{key}} aria-expanded="true" v-on:click="tabSel(key)">{{CT.ri_type[key]}}</a>
                          </li>
                        </template>
                      </ul>
                      <div class="space-4"></div>
                      <div class="row">
                        <div class="col-xs-2">
                          <button class="btn btn-info " style="border-radius:4px" type="button" v-on:click="addPlan">
                            <i class="ace-icon glyphicon glyphicon-search bigger-110"></i>
                            +新增巡检计划
                          </button>
                        </div>
                      </div>
                      <div class="tab-content no-border padding-24">
                        <template v-for="(key, val) in plansList">
                          <div class="tab-pane fadeactive " v-bind:class="{'in active':$index == 0}" id={{key}} >
                            <div class="col-xs-12">
                              <div class="dataTables_wrapper form-inline no-footer">
                                <table class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
                                  <thead>
                                  <tr>
                                    <th>巡检计划名称</th>
                                    <th>执行人员</th>
                                    <th>巡检频次</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                  </tr>
                                  </thead>
                                  <tbody>
                                  <tr v-for="plan in val">
                                    <td> {{plan.name}}</td>
                                    <td> {{plan.worker_name}} </td>
                                    <td> {{CT.ri_times[plan.period]}} </td>
                                    <td> {{CT.tpl_status[plan.status]}} </td>
                                    <td>
                                      <div class="hidden-sm hidden-xs btn-group" v-if="plan.status != '1'">
                                        <button class="btn btn-success" v-on:click="start(plan.id)"> <i class="ace-icon fa fa-pencil-square-o"> </i> 启用 </button>
                                        <button class="btn btn-success" v-on:click="process(plan.id)"> <i class="ace-icon fa fa-pencil-square-o"> </i> 编辑 </button>
                                      </div>

                                      <div class="hidden-sm hidden-xs btn-group" v-else>
                                        <button class="btn btn-success" v-on:click="stop(plan.id)"> <i class="ace-icon fa fa-pencil-square-o"> </i> 停用 </button>
                                        <button class="btn btn-success" v-on:click="detail(plan.id)"> <i class="ace-icon fa fa-pencil-square-o"> </i> 详细 </button>
                                      </div>

                                      <div class="hidden-md hidden-lg" v-if="plan.status!='1'">
                                        <div class="inline position-relative">
                                          <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
                                            <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                                          </button>
                                          <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">

                                            <li>
                                              <a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="启用" v-on:click="start(plan.id)">
                                                <span class="green">
                                                  <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                </span>
                                              </a>
                                            </li>

                                            <li>
                                              <a href="#" class="tooltip-error" data-rel="tooltip" title="" data-original-title="编辑" v-on:click="process(plan.id)">
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
                                              <a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="停用" v-on:click="stop(plan.id)">
                                                <span class="green">
                                                  <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                </span>
                                              </a>
                                            </li>

                                            <li>
                                              <a href="#" class="tooltip-error" data-rel="tooltip" title="" data-original-title="详细" v-on:click="detail(plan.id)">
                                                <span class="red">
                                                  <i class="ace-icon fa fa-trash-o bigger-120"></i>
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
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import myAlert from '../common/myAlert'
  import Combobox from '../common/Combobox'
  import {getCodeTable} from '../../vuex/modules/common/CodeTableGetters'
  import {getInspectionPlansList} from '../../vuex/modules/inspection/InspectionGetters'
  import {reqInspectionPlans,setEnterInspectionPlanGuard,setInspectionPlanDetail,clearInspectionPlanDetail} from '../../vuex/modules/inspection/InspectionActions'


  export default {
    data:function(){
      return {
      selectTplType:''
      }
    },
    components: {
      myAlert,
      Combobox
    },
    vuex:{
      getters:{
        CT: getCodeTable,
        plansList:getInspectionPlansList
      },
      actions:{
      reqInspectionPlans,
      setEnterInspectionPlanGuard,
      setInspectionPlanDetail,
      clearInspectionPlanDetail
      }
    },
    methods:{
      addPlan:function(){
        this.clearInspectionPlanDetail();
        var guard = {state:'1',id:'',tplType:this.selectTplType};
			  this.setEnterInspectionPlanGuard(guard);
			  this.setInspectionPlanDetail(self, '');
			  this.$router.go({ name:'inspection_plan_detail'});
     },
     detail:function(id){
        this.clearInspectionPlanDetail();
        var guard = {state:'2',id:id,tplType:this.selectTplType};
			  this.setEnterInspectionPlanGuard(guard);
			  this.setInspectionPlanDetail(self, id);
			  this.$router.go({ name:'inspection_plan_detail'});
     },
     stop:function(id){
     },
     process:function(id){
        this.clearInspectionPlanDetail();
        var guard = {state:'1',id:id,tplType:this.selectTplType};
			  this.setEnterInspectionPlanGuard(guard);
			  this.setInspectionPlanDetail(self, id);
			  this.$router.go({ name:'inspection_plan_detail'});
     },
     start:function(id){
     },
     tabSel:function(id){
        this.selectTplType = id
     }
    },

    ready:function(){
      this.reqInspectionPlans(this);
    }
  }

</script>
