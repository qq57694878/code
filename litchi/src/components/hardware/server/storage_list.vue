<template>


  <div class="row">
    <div class="col-xs-12">
      <!--  ===================== separate line ===================== -->

      <div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer">

        <table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
          <thead>
          <tr>
            <th>机柜名称</th>
            <th>资产编号</th>
            <th>品牌</th>
            <th>型号</th>
            <th>主要用途</th>
            <th>磁盘接口类型</th>
            <th>磁盘容量</th>
            <th>当前磁盘数</th>
            <th>操作</th>

          </tr>
          </thead>
          <tbody>
          <tr v-for="data in listInfo.list_data">

            <td> {{data.cabinet_name}} </td>
            <td> {{data.asset_num}} </td>
            <td> {{data.brand}} </td>
            <td> {{data.type}} </td>
            <td> {{data.main_usage}} </td>
            <td> {{data.disk_interface_type}} </td>
            <td> {{data.disk_size}} </td>
            <td> {{data.disk_current_num}} </td>

            <td>
              <div class="hidden-sm hidden-xs btn-group">
                <button class="btn btn-xs btn-info" v-on:click="edit(data.id)"><i
                  class="ace-icon fa fa-pencil-square-o"></i> 编辑
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


  import {reqQueryInit} from '../../../vuex/modules/hardward/server/StorageActions';
  import {getInitList} from '../../../vuex/modules/hardward/server/StorageGetters';
  import {delInfo} from '../../../vuex/modules/hardward/server/StorageActions';
  import {reqFormInit as storageReqFormInit} from '../../../vuex/modules/hardward/server/StorageActions';

  export default {
    data: function () {
      return {
        pageNumber: 1,
        pageSize: 10
      }
    },
    vuex: {
      getters: {
        listInfo: getInitList

      },
      actions: {
        reqQueryInit,
        storageReqFormInit,
        getInitList,
        delInfo
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
            console.info('111');
            self.delInfo(input_id);
          }

        });

      },
      edit: function (id) {
        this.storageReqFormInit(id);
        this.$router.go({
          name: 'server_storage_form'
        });

      }
    },
    ready: function () {

    }

  }
</script>

