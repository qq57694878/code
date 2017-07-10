<template>
  <div class="page-header">
    <h1>
      合同执行
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
          <label class="col-xs-1 control-label no-padding-right no-margin-bottom" style="padding-top:6px" > 合同编号:</label>
          <div class="col-xs-2">
            <input type="text" name="contractId" placeholder="" v-model="contractId" >
          </div>
          <label class="col-xs-1 control-label no-padding-right" style="padding-top:6px" > 合同名称:</label>
          <div class="col-xs-2">
            <input type="text" name="ContactName" placeholder="" v-model="contractName">
          </div>
          <label class="col-xs-1 control-label no-padding-right" style="padding-top:6px" > 中标单位:</label>
          <div class="col-xs-2">
            <combobox placeholder="中标单位" v-bind:items="vendors" v-on:change="change"></combobox>
          </div>
          <div class="col-xs-2">
            <!-- <button class="btn btn-white btn-info" type="button" v-on:click="query"> -->
            <button class="btn btn-info " style="border-radius:4px" type="button" v-on:click="query">
              <!-- <i class="ace-icon fa fa-check bigger-110"></i> -->
              <i class="ace-icon glyphicon glyphicon-search bigger-110"></i>
              查询
            </button>
          </div>
        </div>
      </div>

    </div>
  </div>

  <hr>

  <div class="row">
    <div class="col-xs-12">
      <!--  ===================== separate line ===================== -->

      <div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer">
        <table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
          <thead>
          <tr>
            <th>合同编号</th>
            <th>合同名称</th>
            <th>中标单位</th>
            <th>合同登记日期</th>
            <th>合同登记人</th>
            <th>合同有效期</th>
            <th>合同类型</th>
            <th>合同状态</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="plan in planlist.plans">
            <td> {{plan.cno}}</td>
            <td> {{plan.name}} </td>
            <td> {{plan.vender_name}} </td>
            <td> {{plan.apply_date}} </td>
            <td> {{plan.applicant_name}} </td>
            <td> {{plan.sdate}}至{{plan.edate}} </td>
            <td>
              <span v-for="type in plan.ctype">
                {{CT.contract_type[type]}}
                <span v-if = "$index < plan.ctype.length - 1"> + </span>
              </span>
            </td>
            <td> {{CT.contract_status[plan.status]}} </td>

            <td>
              <div class="hidden-sm hidden-xs btn-group" v-if="plan.status != '5'">
                <button class="btn btn-success" v-on:click="process(plan.cno)"> <i class="ace-icon fa fa-pencil-square-o"> </i> 编辑 </button>
              </div>

              <div class="hidden-sm hidden-xs btn-group" v-else>
                <button class="btn btn-success" v-on:click="detail(plan.cno)"> <i class="ace-icon fa fa-pencil-square-o"> </i> 详细 </button>
              </div>

              <div class="hidden-md hidden-lg" v-if="plan.status !='4'">
                <div class="inline position-relative">
                  <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
                    <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                  </button>

                  <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                    <li>
                      <a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="编辑" v-on:click="process(plan.cno)">
                        <span class="green">
                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
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
                      <a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="详细" v-on:click="detail(plan.cno)">
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

        <div class="row">
          <div class="col-xs-6">
          </div>
          <div class="col-xs-6">
            <div class="dataTables_paginate paging_simple_numbers">
              <pages v-bind:total-size="planlist.pagetotal" v-bind:page-size="pageSize" v-on:page-set="pageSet" v-on:page-next="next" v-on:page-prev="prev" v-on:page-first="first" v-on:page-end="end"></pages>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>

  import Pages from '../common/Pages'
  import myAlert from '../common/myAlert'
  import Combobox from '../common/Combobox'
  import {getCodeTable} from '../../vuex/modules/common/CodeTableGetters'
  import {reqContractExecPlans,reqContractExecPlanDetail,updReadOnlyFlag} from '../../vuex/modules/project-management/ContractExecPlanActions'
  import {getContractExecPlanList} from '../../vuex/modules/project-management/ContractExecPlanGetters'
  import {reqComboboxData} from '../../vuex/modules/common/ComboboxActions'

  export default {
    data:function(){
      return {
        pageSize:10,
        pageNumber:1,
        alertShow:false,
        alertType:'alert-success',
        alertContent:'bbbb',
        venderId:'',
        contractId:'',
        contractName:'',
        vendors:[]
      }
    },
    components: {
      Pages,
      myAlert,
      Combobox
    },
    vuex:{
      getters:{
        planlist:getContractExecPlanList,
        CT: getCodeTable
      },
      actions:{
        reqContractExecPlans,
        reqContractExecPlanDetail,
        reqComboboxData,
        updReadOnlyFlag
      }
    },
    methods:{
      change:function(Value){
        this.venderId=Value;
      },
      query:function(){
        let self=this;
        let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,cno:self.contractId,name:self.contractName.tasktype,vender:self.venderId,filter_applicant:'1'};
        console.debug(requestBody);
        this.reqContractExecPlans(self,requestBody);

      },
      next:function(pageNumber) {
        this.pageNumber=pageNumber;
        let self=this;
        let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,cno:self.contractId,name:self.contractName.tasktype,vender:self.venderId,filter_applicant:'1'};
        this.reqContractExecPlans(self,requestBody);
        console.debug("this parent page-next event "+pageNumber);
      },
      prev:function(pageNumber){
        this.pageNumber=pageNumber;
        let self=this;
        let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,cno:self.contractId,name:self.contractName.tasktype,vender:self.venderId,filter_applicant:'1'};
        this.reqContractExecPlans(self,requestBody);
        console.debug("this parent page-prev event "+pageNumber);
      },
      first:function(pageNumber){
        this.pageNumber=pageNumber;
        let self=this;
        let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,cno:self.contractId,name:self.contractName.tasktype,vender:self.venderId,filter_applicant:'1'};
        this.reqContractExecPlans(self,requestBody);
        console.debug("this parent page-first event "+pageNumber);
      },
      end:function(pageNumber){
        this.pageNumber=pageNumber;
        let self=this;
        let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,cno:self.contractId,name:self.contractName.tasktype,vender:self.venderId,filter_applicant:'1'};
        this.reqContractExecPlans(self,requestBody);
        console.debug("this parent page-end event "+pageNumber);
      },
      pageSet:function(pageNumber){
        this.pageNumber=pageNumber;
        let self=this;
        let requestBody={ pageno:self.pageNumber,pagesize:self.pageSize,cno:self.contractId,name:self.contractName.tasktype,vender:self.venderId,filter_applicant:'1'};
        this.reqContractExecPlans(self,requestBody);
        console.debug("this parent page-set event "+pageNumber);
      },
      process:function(cno){
        var self=this;
        this.updReadOnlyFlag(false, cno,'project_contract_exec');
        console.debug("========False ");
   //     this.reqContractExecPlanDetail(self, cno,function(){
    //      self.$router.go({ name:'project_contract_exec_plan_detail'});
    //    });

        self.$router.go({ name:'project_contract_exec_detail'});
      },
      detail:function(cno){
        var self=this;
        this.updReadOnlyFlag(true, cno,'project_contract_exec');
        console.debug("=======True ");
        //     this.reqContractExecPlanDetail(self, cno,function(){
        //      self.$router.go({ name:'project_contract_exec_plan_detail'});
        //    });

        self.$router.go({ name:'project_contract_exec_detail'});
      }
    },

    ready:function(){
      this.reqComboboxData(this,'sysresource/vendor/get-vendor-list.do',function(comp,items){
        comp.vendors=items;
      });

    }
  }

</script>
