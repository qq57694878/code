<template>
  <div class="page-header">
    <h1>
      巡检计划管理
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
          <label class="col-xs-1 control-label no-padding-right no-margin-bottom"  style="padding-top:6px" > 巡检计划名称:</label>
          <div class="col-xs-2" v-if="guard.state != '2'">
            <input type="text" placeholder="" v-model="planDetail.name" >
          </div>
          <div class="col-xs-2" v-else>
            <input type="text" readonly placeholder="" v-model="planDetail.name" >
          </div>
          <label class="col-xs-1 control-label no-padding-right" style="padding-top:6px" > 巡检频率:</label>
          <div class="col-xs-2" v-if="guard.state != '2'">
            <combobox placeholder="巡检频率" v-bind:items="periods" v-bind:selectid="planDetail.period" v-on:change="changePriods"></combobox>
          </div>
          <div class="col-xs-2" v-else>
            <combobox placeholder="巡检频率" readonly v-bind:items="periods" v-bind:selectid="planDetail.period"></combobox>
          </div>
        </div>
      </div>
      <div class="space-4"></div>
      <div class="row">
        <div class="col-xs-12">
          <label class="col-xs-1  control-label no-padding-right" style="padding-top:6px" > 执行人员:</label>
          <div class="col-xs-2" v-if="guard.state != '2'">
            <combobox placeholder="执行人员" v-bind:items="workers" v-bind:selectid="planDetail.worker_id" v-on:change="changeWorkers"></combobox>
          </div>
          <div class="col-xs-2" v-else>
            <combobox placeholder="执行人员" readonly v-bind:items="workers" v-bind:selectid="planDetail.worker_id"></combobox>
          </div>
        </div>
      </div>
    </div>
  </div>

  <hr>
  <div class="row">
    <div class="col-xs-12">
      <div class="widget-box">
        <div class="widget-header widget-header-flat">
          <h4 class="widget-title lighter">巡检模版</h4>
        </div>
        <div class="whdget-body">
          <div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer" v-if="guard.state!= '2'">
            <table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
              <thead>
              <tr>
                <th v-show="guard.state != '2'"></th>
                <th>模版名称</th>
                <th>操作</th>
              </tr>
              </thead>
              <tbody>
              <template v-for="item in  tplList.tpls">
                <tr>
                  <td v-show="guard.state != '2'" class = "center">
                    <label class = "position-relative">
                      <input type="radio" v-bind:value="item.id" v-model="planDetail.tpl.id" >
                    </label>
                  </td>
                  <td> {{item.name}} </td>

                  <td>
                    <div class="hidden-sm hidden-xs btn-group" >
                      <button class="btn btn-success" v-on:click="info(item.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 详细 </button>
                     </div>

                    <div class="hidden-md hidden-lg" >
                      <div class="inline position-relative">
                        <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
                          <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                        </button>
                        <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                          <li>
                            <a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="详细" v-on:click="info(item.id)">
                        </ul>
                      </div>
                    </div>
                  </td>
                </tr>
              </template>
              </tbody>
            </table>
          </div>
          <div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer" v-else>
            <table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
              <thead>
              <tr>
                <th>序号</th>
                <th>模版名称</th>
                <th>操作</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="tpl in planDetail.tpl">
                <td> {{$index+1}} </td>
                <td> {{tpl.name}} </td>

                <td>
                  <div class="hidden-sm hidden-xs btn-group" >
                    <button class="btn btn-success" v-on:click="info(tpl.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 详细 </button>
                  </div>

                  <div class="hidden-md hidden-lg" >
                    <div class="inline position-relative">
                      <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
                        <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                      </button>
                      <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                        <li>
                          <a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="详细" v-on:click="info(tpl.id)">
                      </ul>
                    </div>
                  </div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="space-4"></div>

  <div class="row">
    <div class="col-xs-12">
      <div class="widget-box">
        <div class="widget-header widget-header-flat">
          <h4 class="widget-title lighter">巡检目标设备</h4>
        </div>
        <div class="whdget-body">
          <div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer" v-if="guard.state!= '2'">
            <table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
              <thead>
              <tr>
                <th class="center">
                  <label class="position-relative">
                    <input type="checkbox" class="ace">
                    <span class="lbl"></span>
                  </label>
                </th>
                <th>设备类型</th>
                <th>设备名称</th>
                <th>资产编号</th>
                <th>是否已加入巡检计划</th>
                <th>设备详情</th>
              </tr>
              </thead>
              <tbody>
              <template v-for="dev in devList">
                <tr>
                  <td class = "center">
                    <label class = "position-relative">
                      <input type="checkbox" v-bind:value="dev.dev_id" v-model="devlistScope" >
                    </label>
                  </td>
                  <td> {{CT.equipment_type[dev.dev_type]}} </td>
                  <td> {{dev.dev_name}} </td>
                  <td> {{dev.dev_no}} </td>
                  <td> {{CT.yes_no[dev.is_other_plan]}} </td>
                  <td>
                    <div class="hidden-sm hidden-xs btn-group" >
                      <button class="btn btn-success" v-on:click="devInfo(dev.dev_id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 详细 </button>
                    </div>

                    <div class="hidden-md hidden-lg" >
                      <div class="inline position-relative">
                        <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
                          <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                        </button>
                        <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                          <li>
                            <a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="详细" v-on:click="devInfo(dev.dev_id)">
                        </ul>
                      </div>
                    </div>
                  </td>
                </tr>
              </template>
              </tbody>
            </table>
          </div>
          <div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer" v-else>
            <table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
              <thead>
              <tr>
                <th>序号</th>
                <th>设备类型</th>
                <th>设备名称</th>
                <th>资产编号</th>
                <th>是否已加入巡检计划</th>
                <th>设备详情</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="dev in planDetail.tpl">
                <td> {{$index+1}} </td>
                <td> {{CT.equipment_type[dev.dev_type]}} </td>
                <td> {{dev.dev_name}} </td>
                <td> {{dev.dev_no}} </td>
                <td> {{CT.yes_no[dev.is_other_plan]}} </td>

                <td>
                  <div class="hidden-sm hidden-xs btn-group" >
                    <button class="btn btn-success" v-on:click="devInfo(dev.dev_id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 详细 </button>
                  </div>

                  <div class="hidden-md hidden-lg" >
                    <div class="inline position-relative">
                      <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
                        <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                      </button>
                      <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                        <li>
                          <a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="详细" v-on:click="devInfo(dev.dev_id)">
                      </ul>
                    </div>
                  </div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="space-4" v-show="guard.state == '2'"></div>
  <div class="row" v-show="guard.state == '2'">
    <div class="col-xs-12">
      <div class="widget-box">
        <div class="widget-header widget-header-flat">
          <h4 class="widget-title lighter">巡检记录</h4>
        </div>
        <div class="whdget-body">
          <!--<div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer" v-if="guard.state!= '2'">-->
            <!--<table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">-->
              <!--<thead>-->
              <!--<tr>-->
                <!--<th>执行人</th>-->
                <!--<th>计划执行时间</th>-->
                <!--<th>实际执行时间</th>-->
                <!--<th>巡检结论</th>-->
              <!--</tr>-->
              <!--</thead>-->
              <!--<tbody>-->
              <!--<template v-for="dev in devList">-->
                <!--<tr>-->
                  <!--<td> {{CT.equipment_type[dev.dev_type]}} </td>-->
                  <!--<td> {{dev.dev_name}} </td>-->
                  <!--<td> {{dev.dev_no}} </td>-->
                  <!--<td> {{CT.yes_no[dev.is_other_plan]}} </td>-->
                <!--</tr>-->
              <!--</template>-->
              <!--</tbody>-->
            <!--</table>-->
          <!--</div>-->
        </div>
      </div>
    </div>
  </div>

  <div class="clearfix form-actions">
    <div class="col-md-offset-2 col-md-10">
      <button class="btn btn-info" type="button" v-on:click="savePlan" v-show="guard.state != '2'">
        <i class="ace-icon fa fa-check bigger-110"></i>
        保存
      </button>
      &nbsp; &nbsp; &nbsp;
      <button class="btn btn-info" type="button" v-on:click="startPlan" v-show="guard.state != '2'">
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
  import {getInspectionPlanDetail,getInspectionPlanGuard,getInspectionTplList,getDevList,getDevListScope} from '../../vuex/modules/inspection/InspectionGetters'
  import {saveInspectionPlan} from '../../vuex/modules/inspection/InspectionActions'


  export default {
    data:function(){
      return {
        periods:[]

      }
    },
    components: {
      myAlert,
      Combobox
    },
    vuex:{
      getters:{
        CT: getCodeTable,
        guard:getInspectionPlanGuard,
        planDetail:getInspectionPlanDetail,
        tplList:getInspectionTplList,
        devList:getDevList,
        devlistScope:getDevListScope
      },
      actions:{
      saveInspectionPlan
      }
    },
    methods:{
      info:function(id){
      },

      changePriods:function(Value){
        this.planDetail.period=Value;
      },

      changeWorkers:function(Value){
        this.planDetail.worker_id=Value;
      },

      savePlan:function(){
        let self = this;
        console.debug('save');
         bootbox.confirm("您确定要保存巡检计划吗？", function(result) {
         if(result) {
            self.saveInspectionPlan(self, self.planDetail.id, self.planDetail.ri_code, self.planDetail.name, self.planDetail.tpl_id, self.planDetail.period, self.planDetail.worker_id, self.planDetail.worker_name, self.planDetail.tpl_id, '0', self.devlistScope);
        }
        });
      },

      startPlan:function(){
      let self = this;
        console.debug('save');
         bootbox.confirm("您确定要启用巡检计划吗？", function(result) {
         if(result) {
            self.saveInspectionPlan(self, self.planDetail.id, self.planDetail.ri_code, self.planDetail.name, self.planDetail.tpl_id, self.planDetail.period, self.planDetail.worker_id, self.planDetail.worker_name, self.planDetail.tpl_id, '1', self.devlistScope,function(comp){
              comp.$router.go({
              name:'inspection_plans'});
              }
            );
        }
        });
      },

      back:function(){
        this.$router.go({
          name:'inspection_plans'});
      }
    },

    ready:function(){
      var arr = this.CT.ri_times;
			for(var i in arr){
				var data = {
								code:i,
								value:arr[i]
							};

				this.periods.push(data);
			}
    }
  }

</script>
