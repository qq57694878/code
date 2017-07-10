<template>


  <div class="row">
    <div class="col-xs-12">
      <!--  ===================== separate line ===================== -->

      <div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer">

        <table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
          <thead>
          <tr>
            <th>资产编号</th>
            <th>品牌</th>
            <th>名称</th>
            <th>主要用途</th>
            <th>CPU型号</th>
            <th>核心总数</th>
            <th>内存容量(GB)</th>
            <th>操作</th>

          </tr>
          </thead>
          <tbody>
          <tr v-for="data in listInfo.list_data">

            <td> {{data.asset_num}}</td>
            <td> {{data.brand}}</td>
            <td> {{data.name}}</td>
            <td> {{codetable.x86_main_usage[data.main_usage]}}</td>
            <td> {{data.cpu_type}}</td>
            <td> {{data.core_num*data.cpu_current_num}}</td>
            <td> {{data.memory_current_capacity}}</td>

            <td>
              <div class="hidden-sm hidden-xs btn-group">
                <button class="btn btn-xs btn-info" v-on:click="edit(data.id)"><i
                  class="ace-icon fa fa-pencil-square-o"></i> 基本信息
                </button>
                <button class="btn btn-xs btn-info" v-on:click="list(data.id)"><i
                  class="ace-icon fa fa-pencil-square-o"></i> IP地址
                </button>
                <button class="btn btn-xs btn-info" disabled="true" v-on:click="edit(data.id)"><i
                  class="ace-icon fa fa-pencil-square-o"></i> 巡检
                </button>
                <button class="btn btn-xs btn-info" disabled="true" v-on:click="edit(data.id)"><i
                  class="ace-icon fa fa-pencil-square-o"></i> 质保
                </button>
                <button class="btn btn-xs btn-danger" v-on:click="del(data.id)"><i class="ace-icon fa fa-trash-o"></i>
                  删除
                </button>
              </div>
              <div class="hidden-md hidden-lg">
                <div class="inline position-relative">
                  <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto"
                          aria-expanded="false">
                    <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                  </button>

                  <ul
                    class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">

                    <li>
                      <a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="编辑"
                         v-on:click="edit(data.id)">
                        <span class="green">
                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                        </span>
                      </a>
                    </li>

                    <li>
                      <a href="#" class="tooltip-error" data-rel="tooltip" title="" data-original-title="删除"
                         v-on:click="del(data.id)">
                        <span class="red">
                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                        </span>
                      </a>
                    </li>
                  </ul>
                </div>
              </div>

            </td>
          </tr>

          </tbody>
        </table>

        <div class="row">
          <div class="col-xs-6">
          </div>
          <div class="col-xs-6">
            <div class="dataTables_paginate paging_simple_numbers">
              <pages v-bind:total-size="listInfo.pagetotal" v-bind:page-size="pageSize" v-on:page-set="pageSet"
                     v-on:page-next="next" v-on:page-prev="prev" v-on:page-first="first" v-on:page-end="end"></pages>
            </div>
          </div>
        </div>

      </div>

      <!--  ===================== separate line ===================== -->
    </div>
  </div>


</template>

<script>

  import Pages from '../../common/Pages'

  import Alert from '../../common/myAlert'


  import {reqQueryInit} from '../../../vuex/modules/hardward/server/X86Actions';
  import {getInitList} from '../../../vuex/modules/hardward/server/X86Getters';
  import {delX86Info} from '../../../vuex/modules/hardward/server/X86Actions';
  import {reqFormInit as x86ReqFormInit} from '../../../vuex/modules/hardward/server/X86Actions';
  import {getCodeTable} from '../../../vuex/modules/common/CodeTableGetters'

  export default {
    data: function () {
      return {
        pageNumber: 1,
        pageSize: 10
      }
    },
    vuex: {
      getters: {
        listInfo: getInitList,
        codetable: getCodeTable

      },
      actions: {
        reqQueryInit,
        x86ReqFormInit,
        delX86Info
      }
    },
    components: {
      Pages,
      Alert
    },
    methods: {
      next: function (pageNumber) {
        console.debug("this parent page-next event " + pageNumber);
        this.reqQueryInit(pageNumber, this.pageSize);
      },
      prev: function (pageNumber) {
        console.debug("this parent page-prev event " + pageNumber);
        this.reqQueryInit(pageNumber, this.pageSize);
      },
      first: function (pageNumber) {
        console.debug("this parent page-first event " + pageNumber);
        this.reqQueryInit(pageNumber, this.pageSize);
      },
      end: function (pageNumber) {
        console.debug("this parent page-end event " + pageNumber);
        this.reqQueryInit(pageNumber, this.pageSize);
      },
      pageSet: function (pageNumber) {
        console.debug("this parent page-set event " + pageNumber);
        this.reqQueryInit(pageNumber, this.pageSize);
      },

      del: function (input_id) {
        let self = this;
        bootbox.confirm("您确定要删除此条记录吗？", function (result) {
          if (result) {
            console.debug(input_id);
            self.delX86Info(input_id);
            self.reqQueryInit(1, 10);
          }
        });

      },
      edit: function (id) {
        this.x86ReqFormInit(id);
        console.debug(id);
        this.$router.go({
          name: 'server_x86_form'
        });
      },
      list: function (id) {
        console.debug(id);
        this.$router.go({
          name: 'server_ip_list',
          params:{
            biz_type:'HW_X86',//业务类型，表名
            biz_id:id//业务表主键
          }
        });
      }
    },
    ready: function () {

    }

  }
</script>

