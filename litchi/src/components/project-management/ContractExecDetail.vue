
<template>
  <div class="page-header">
    <h1>
      合同执行
      <small style="font-size=16px" v-if="readonly == true"> <i class="ace-icon fa fa-angle-double-right"></i> 详细页 </small>
      <small style="font-size=16px" v-else> <i class="ace-icon fa fa-angle-double-right"></i> 编辑页 </small>
    </h1>
  </div>
  <div class="row">
    <div class="col-xs-12">
      <!--  ===================== separate line ===================== -->
      <div class="row" >
        <div class="col-xs-12">
          <my-alert v-bind:alert-type="alertType" v-bind:content="alertContent" v-bind:show.sync="alertShow"></my-alert>
        </div>
      </div>

        <form class="form-horizontal" role="form">

          <div class="form-group">
            <div class="col-md-6">
              <label class="col-sm-2 control-label no-padding-right"> 合同名称</label>
              <div class="col-sm-8">
                <input type="text" readonly placeholder="" class="col-xs-12" v-model="planInfo.name" >
              </div>
            </div>

            <div class="col-md-6">
              <label class="col-sm-2 control-label no-padding-right" > 中标公司</label>
              <div class="col-sm-8">
                <input type="text" readonly placeholder="" class="col-xs-12" v-model="planInfo.vender_name" >
              </div>
            </div>
          </div>

          <div class="form-group">
            <div class="col-md-12">
              <label class="col-sm-1 control-label no-padding-right">合同类型</label>
              <div class="col-sm-9">
                <div class="checkbox col-xs-2 no-margin-top">
                  <label>
                    <input name="form-field-checkbox" type="checkbox" disabled class="ace" value="0" v-model="planInfo.ctype">
                    <span class="lbl" for="0"> 软件开发类</span>
                  </label>
                </div>

                <div class="checkbox col-xs-2 no-margin-top">
                  <label>
                    <input name="form-field-checkbox" type="checkbox" disabled class="ace" value="1" v-model="planInfo.ctype" >
                    <span class="lbl" for="1"> 软件运维类</span>
                  </label>
                </div>

                <div class="checkbox col-xs-2 no-margin-top">
                  <label>
                    <input name="form-field-checkbox" type="checkbox" disabled class="ace" value="2" v-model="planInfo.ctype" >
                    <span class="lbl" for="2"> 硬件购置类</span>
                  </label>
                </div>

                <div class="checkbox col-xs-2 no-margin-top">
                  <label>
                    <input name="form-field-checkbox" type="checkbox" disabled class="ace" value="3" v-model="planInfo.ctype" >
                    <span class="lbl" for="3"> 硬件运维类</span>
                  </label>
                </div>

                <div class="checkbox col-xs-2 no-margin-top">
                  <label>
                    <input name="form-field-checkbox" type="checkbox" disabled class="ace" value="4" v-model="planInfo.ctype" >
                    <span class="lbl" for="4"> 硬件质保类</span>
                  </label>
                </div>
              </div>
            </div>
          </div>

          <hr>

          <!--软件开发-->
          <div class="row" v-show="inArray('0')">
            <div class="widget-box">
              <div class="widget-header">
               <h4 class="widget-title">软件开发</h4>
              </div>
              <div class="widget-body">
                <div class="widget-main">
                  <label class="col-sm-12  no-padding-left">业务系统</label>

                  <div class="form-group">
                    <div class="col-sm-2" v-for="biz in soft.bizs">
                      <div class="checkbox no-margin-top">
                        <label>
                          <input name="form-field-checkbox" type="checkbox" disabled class="ace" value={{biz.biz_id}} v-model="softBizScope">
                          <span class="lbl"> {{biz.biz_name}}</span>
                        </label>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-xs-12">
                        <div class="tabbable">
                        <ul class="nav nav-tabs padding-18 tab-size-bigger" >
                        <template v-for="biz in soft.bizs">
                          <li  class= "active" v-if="$index == 0">
                            <a data-toggle="tab" href=#{{biz.biz_id}} aria-expanded="true">{{biz.biz_name}}</a>
                          </li>
                          <li v-else>
                            <a data-toggle="tab" href=#{{biz.biz_id}} aria-expanded="true">{{biz.biz_name}}</a>
                          </li>
                        </template>
                        </ul>
                        <div class="tab-content no-border padding-24">
                          <template v-for="biz in soft.bizs">
                            <div class="tab-pane fadeactive in active" id={{biz.biz_id}} v-if="$index == 0">
                              <attach-table v-bind:codetable="CT.contractExecAttchmentType" v-if="readonly == true" readonly="1" v-bind:attachments="biz.attachments" v-on:getuploads="getuploads" v-on:error="fileInputError"></attach-table>
                              <attach-table v-bind:codetable="CT.contractExecAttchmentType" v-bind:attachments="biz.attachments" v-on:getuploads="getuploads" v-on:error="fileInputError"v-else ></attach-table>
                            </div>
                            <div class="tab-pane fadeactive" id={{biz.biz_id}} v-else>
                              <attach-table v-bind:codetable="CT.contractExecAttchmentType" v-if="readonly == true" readonly="1" v-bind:attachments="biz.attachments" v-on:getuploads="getuploads" v-on:error="fileInputError"></attach-table>
                              <attach-table v-bind:codetable="CT.contractExecAttchmentType" v-bind:attachments="biz.attachments" v-on:getuploads="getuploads" v-on:error="fileInputError"v-else ></attach-table>
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

          <!--软件运维-->
          <div class="row" v-show="inArray('1')">
            <div class="widget-box">
              <div class="widget-header">
                <h4 class="widget-title">软件运维</h4>
              </div>

              <div class="widget-body">
                <div class="widget-main">
                  <label class="col-sm-12  no-padding-left">业务系统</label>
                  <div class="form-group">
                    <div class="col-sm-2" v-for="biz in softman.bizs">
                      <div class="checkbox no-margin-top">
                        <label>
                          <input name="form-field-checkbox" type="checkbox" disabled class="ace" value={{biz.biz_id}} v-model="softmanBizScope">
                          <span class="lbl"> {{biz.biz_name}}</span>
                        </label>
                      </div>
                    </div>
                  </div>
                  <label class="col-sm-12  no-padding-left">请选择业务范围</label>
                  <div class="row">
                    <div class="col-sm-12">
                      <label class="col-sm-1 control-label text-align-left no-padding-right" >合同类型</label>
                      <div>
                        <div class="checkbox col-sm-2 no-margin-top">
                          <label>
                            <input name="form-field-checkbox" type="checkbox" disabled class="ace" value="0" v-model="softman.scope" >
                            <span class="lbl"> 需求变更</span>
                          </label>
                        </div>

                        <div class="checkbox col-sm-2 no-margin-top">
                          <label>
                            <input name="form-field-checkbox" type="checkbox" disabled class="ace" value="1" v-model="softman.scope" >
                            <span class="lbl"> 数据修改</span>
                          </label>
                        </div>

                        <div class="checkbox col-sm-2 no-margin-top">
                          <label>
                            <input name="form-field-checkbox" type="checkbox" disabled class="ace" value="2" v-model="softman.scope" >
                            <span class="lbl"> 数据查询</span>
                          </label>
                        </div>

                        <div class="checkbox col-sm-2 no-margin-top">
                          <label>
                            <input name="form-field-checkbox" type="checkbox" disabled class="ace" value="3" v-model="softman.scope" >
                            <span class="lbl"> 程序发布</span>
                          </label>
                        </div>
                        <div class="col-sm-2"></div>
                      </div>
                    </div>
                  </div>
                  <div class="space-4"></div>
                  <div class="row">
                    <div class="col-xs-12">
                      <!--  ===================== separate line ===================== -->
                      <div  class="dataTables_wrapper form-inline no-footer">
                        <table  class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
                          <thead>
                          <tr>
                            <th>业务范围</th>
                            <th>记录数</th>
                            <th>工作量合集</th>
                            <th v-show = "readonly != true">操作</th>
                          </tr>
                          </thead>
                          <tbody>
                          <tr v-for="work in softman.workload">
                            <td> {{CT.biz_scope_type[work.scope]}}</td>
                            <td> {{work.count}} </td>
                            <td> {{work.total}} </td>
                            <td v-show="readonly != true">
                              <div class="hidden-sm hidden-xs btn-group">
                                <button type="button" class="btn btn-success" v-on:click="softmanDetail(work.inspection_id)">
                                  <i class="ace-icon fa fa-pencil-square-o">
                                  </i> 详细 </button>
                              </div>

                              <div class="hidden-md hidden-lg">
                                <div class="inline position-relative">
                                  <button type="button" class="btn btn-success" data-toggle="dropdown" data-position="auto" aria-expanded="false">
                                    <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                                  </button>

                                  <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                    <li>
                                      <button type="button" class="btn btn-success" data-rel="tooltip" title="" data-original-title="详细" v-on:click="softmanDetail(work.inspection_id)">
								                        <span class="green">
							                          	<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
							                        	</span>
                                      </button>
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
                </div>
              </div>
            </div>
          </div>

          <!--硬件购置-->
          <div class="row" v-show="inArray('2')">
            <div class="widget-box">
              <div class="widget-header">
                <h4 class="widget-title">硬件购置</h4>
              </div>
              <div class="widget-body">
                <div class="widget-main">
                  <div class="form-group">

                    <div class="row">
                      <div class="col-md-6">
                        <label class="col-sm-2 control-label no-padding-right"> 硬件设备购置单</label>
                        <div class="col-sm-8">
                          <input type="text" readonly placeholder="" class="col-xs-12" v-model="hwlist_file" >
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-md-6">
                        <label class="col-sm-2 control-label no-padding-right"> 到货清单模版下载：</label>
                        <div class="col-sm-8">
                          <input type="text" readonly placeholder="" class="col-xs-12" v-model="hwlist_file" >
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-md-6">
                        <label class="col-sm-2 control-label no-padding-right"> 上传到货清单</label>
                        <div class="col-sm-8">
                          <input type="text" readonly placeholder="" class="col-xs-12" v-model="hwlist_file" >
                        </div>
                      </div>
                   </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-xs-12">
                    <!--  ===================== separate line ===================== -->
                    <div  class="dataTables_wrapper form-inline no-footer">
                      <table  class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
                        <thead>
                        <tr>
                          <th>设备类型</th>
                          <th>品牌</th>
                          <th>型号</th>
                          <th>到货日期</th>
                          <th>质保开始日期</th>
                          <th>原厂质保时间</th>
                          <th>状态</th>
                          <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="hw in hw.dev_list">
                          <td> {{CT.equipment_type[hw.dev_type]}}</td>
                          <td> {{hw.brand}} </td>
                          <td> {{hw.model}} </td>
                          <td> {{hw.arrival_date}} </td>
                          <td> {{hw.warranty_sdate}} </td>
                          <td> {{hw.warranty_year}} </td>
                          <td> {{CT.deploy_status[deploy_status]}} </td>
                          <td>
                            <div class="hidden-sm hidden-xs btn-group">
                              <button type="button" class="btn btn-success" v-on:click="hwDevDetail(hw.dev_id)">
                                <i class="ace-icon fa fa-pencil-square-o">
                                </i> 详细 </button>
                            </div>

                            <div class="hidden-md hidden-lg">
                              <div class="inline position-relative">
                                <button type="button" class="btn btn-success" data-toggle="dropdown" data-position="auto" aria-expanded="false">
                                  <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                                </button>

                                <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                  <li>
                                    <button type="button" class="btn btn-success" data-rel="tooltip" title="" data-original-title="详细" v-on:click="hwDevDetail(hw.dev_id)">
								                        <span class="green">
							                          	<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
							                        	</span>
                                    </button>
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
              </div>
            </div>
          </div>

          <!--硬件运维-->
          <div class="row" v-show="inArray('3')">
            <div class="widget-box">
              <div class="widget-header">
                <h4 class="widget-title">硬件运维</h4>
              </div>
              <div class="widget-body">
                <div class="widget-main">
                  <div class="row">
                    <div class="col-sm-12">
                      <label class="col-sm-1 control-label text-align-left no-padding-right"  > 时间范围:</label>
                      <div>
                        <label class="col-sm-3 control-label text-align-left no-padding-right no-margin-top"> {{planInfo.sdate}} 到 {{planInfo.edate}}</label>
                      </div>
                    </div>
                  </div>
                  <div class="space-4"></div>
                  <div class="row">
                    <div class="col-xs-12">
                      <!--  ===================== separate line ===================== -->

                      <div  class="dataTables_wrapper form-inline no-footer">
                        <table  class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
                          <thead>
                          <tr>
                            <th>巡检计划名称</th>
                            <th>巡检人</th>
                            <th>巡检频次</th>
                            <th>操作</th>
                          </tr>
                          </thead>
                          <tbody>
                          <tr v-for="ope in hwman">
                            <td> {{ope.inspection_name}}</td>
                            <td> {{ope.inspection_person}} </td>
                            <td> {{ope.inspection_rate}} </td>
                            <td>
                              <div class="hidden-sm hidden-xs btn-group">
                                <button type="button" class="btn btn-success" v-on:click="hwmanDetail(ope.inspection_id)">
                                  <i class="ace-icon fa fa-pencil-square-o">
                                  </i> 详细 </button>
                              </div>

                              <div class="hidden-md hidden-lg">
                                <div class="inline position-relative">
                                  <button type="button" class="btn btn-success" data-toggle="dropdown" data-position="auto" aria-expanded="false">
                                    <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                                  </button>

                                  <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                    <li>
                                      <button type="button" class="btn btn-success" data-rel="tooltip" title="" data-original-title="详细" v-on:click="hwmanDetail(ope.inspection_id)">
								                        <span class="green">
							                          	<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
							                        	</span>
                                      </button>
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
                </div>
              </div>
            </div>
          </div>

          <!--硬件质保-->
          <div class="row" v-show="inArray('4')">
            <div class="widget-box">
              <div class="widget-header">
                <h4 class="widget-title">硬件质保</h4>
              </div>
              <div class="widget-body">
                <div class="widget-main">
                  <div class="row">
                    <div class="col-sm-12">
                      <label class="col-sm-1 control-label text-align-left no-padding-right"  > 时间范围:</label>
                      <div>
                        <label class="col-sm-3 control-label text-align-left no-padding-right no-margin-top"> {{planInfo.sdate}} 到 {{planInfo.edate}}</label>
                      </div>
                    </div>
                  </div>
                  <div class="space-4"></div>
                  <div class="row">
                    <div class="col-xs-12">
                      <!--  ===================== separate line ===================== -->

                      <div  class="dataTables_wrapper form-inline no-footer">
                        <table  class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
                          <thead>
                          <tr>
                            <th>设备类型</th>
                            <th>设备名称</th>
                            <th>到货时间</th>
                            <th>原厂质保到期日期</th>
                            <th>操作</th>
                          </tr>
                          </thead>
                          <tbody>
                          <tr v-for="qa in hw_warranty">
                            <td> {{CT.equipment_type[qa.dev_type]}}</td>
                            <td> {{qa.dev_name}} </td>
                            <td> {{qa.arrival_date}} </td>
                            <td> {{qa.warranty_date}} </td>
                            <td>
                              <div class="hidden-sm hidden-xs btn-group">
                                <button type="button" class="btn btn-success" v-on:click="hwWarrantyDetail(qa.dev_id)">
                                  <i class="ace-icon fa fa-pencil-square-o">
                                  </i> 详细 </button>
                              </div>

                              <div class="hidden-md hidden-lg" >
                                <div class="inline position-relative">
                                  <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
                                    <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                                  </button>

                                  <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                    <li>
                                      <button type="button" class="btn btn-success" data-original-title="详细" v-on:click="hwWarrantyDetail(qa.dev_id)">
                                                <span class="green">
                                                  <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                </span>
                                      </button>
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
                </div>
              </div>
            </div>
          </div>


          <div class="space-4"></div>
          <div class="clearfix form-actions">
            <div class="col-md-offset-2 col-md-10">
              <button class="btn btn-info" type="button" v-on:click="submit" v-show="readonly != true">
                <i class="ace-icon fa fa-check bigger-110"></i>
                保存
              </button>

              &nbsp; &nbsp; &nbsp;
              <button class="btn" type="reset" v-on:click="back">
                <i class="ace-icon fa fa-undo bigger-110"></i>
                返回
              </button>

            </div>
          </div>
        </form>
      <!--  ===================== separate line ===================== -->

    </div>
  </div>
</template>

<script>
  import AttachTable from '../common/AttachTable'
  import myAlert from '../common/myAlert'
  import {reqContractExecPlanDetail,reqBusinessList,deleteOneHwman,deleteOneHwWarranty,saveContractExecPlan,saveContractExec} from '../../vuex/modules/project-management/ContractExecPlanActions'
  import {getDetailRouter,getContractExecPlanDetail, getBusinessList,getReadonly,getSoft,getSoftman,getHw,getHwman,getHwWarranty,getSoftBizScope,getSoftmanBizScope,getDetailContractId} from '../../vuex/modules/project-management/ContractExecPlanGetters'
  import {getCodeTable} from '../../vuex/modules/common/CodeTableGetters'

  export default {
    data:function(){
      return {
        alertShow:false,
        alertType:'alert-success',
        alertContent:'aaaa',
        hwScope:['0','1','2','3'],
        pageSize:10,
        pageNumber:1,
        attach:{},

      }
    },
    components: {
      myAlert,
      AttachTable
    },
    vuex:{
      getters:{
        planInfo:getContractExecPlanDetail,
        businessList:getBusinessList,
        readonly:getReadonly,
        soft:getSoft,
        softman:getSoftman,
        hw:getHw,
        hwman:getHwman,
        hw_warranty:getHwWarranty,
        softBizScope:getSoftBizScope,
        softmanBizScope:getSoftmanBizScope,
        detailContractId:getDetailContractId,
		router:getDetailRouter,
        CT: getCodeTable

      },
      actions:{
        reqContractExecPlanDetail,
        reqBusinessList,
        deleteOneHwman,
        deleteOneHwWarranty,
        saveContractExecPlan,
        saveContractExec
      }
    },
    methods:{
      getuploads:function(uploads){
         this.attach = uploads;
        console.debug(uploads);
      },
      fileInputError:function(errMsg){
        //TODO 此处增加错误提示alert 显示内容为errMsg
        console.error(errMsg);
      },
      submit:function () {
        let self = this;
        bootbox.confirm("您确定要保存合同执行吗？", function(result) {
         if(result) {
            self.saveContractExec(self, self.detailContractId, self.soft.bizs, self.softman.scope, self.softmanBizScope, self.hw, self.hwman, self.hw_warranty);
          }
        });
      },
      back:function () {
        //this.$router.go({name:'project_contract_exec'});
        this.$router.go({name:this.router});
      },
      addHwIns:function () {

      },
      inArray:function (val) {
        return $.inArray(val, this.planInfo.ctype) != -1;
      },
		  softmanDetail:function(id){

      },
		  hwDevDetail:function(id){

      },
		  hwmanDetail:function(id){

      },
		  hwWarrantyDetail:function(id){

      }
    },
    ready:function(){
      this.reqBusinessList(this, this.detailContractId);

      this.reqContractExecPlanDetail(this.detailContractId,function(){
        self.$nextTick(function(){
          self.$broadcast('done');
        })
      });
		  }
  }
</script>

<style scoped>
  .text-align-left{
    text-align:left;
  }
</style>
