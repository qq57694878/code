<template>
  <div class="page-header">
    <h1>
      业务系统部署管理
    </h1>
  </div>
  <div class="row">
    <div class="col-xs-12">
      <!--  ===================== separate line ===================== -->

      <div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer">

        <table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
          <thead>
          <tr>
            <th>业务系统名称</th>
            <th>内网地址</th>
            <th>集群地址</th>
            <th>外网地址</th>
            <th>操作</th>

          </tr>
          </thead>
          <tbody>
          <tr>
            <td>业务系统 1</td>
            <td>
              <a href="#">
                实例1<i class="ace-icon fa fa-circle green"></i>
              </a>
              <a href="#">
                实例2<i class="ace-icon fa fa-circle green"></i>
              </a>
              <a href="#">
                实例3<i class="ace-icon fa fa-circle green"></i>
              </a>
              <a href="#">
                实例4<i class="ace-icon fa fa-circle red"></i>
              </a>
            </td>
            <td>集群1<i class="ace-icon fa fa-circle green"></i></td>
            <td>外网1<i class="ace-icon fa fa-circle green"></i></td>

            <td>

              <div class=" btn-group">
                <button class="btn btn-xs btn-info">
                  <i class="ace-icon fa fa-pencil-square-o"></i> 编辑
                </button>
              </div>
            </td>
          </tr>

          <tr>
            <td>业务系统 2</td>
            <td>
              <a href="#">
                实例1<i class="ace-icon fa fa-circle green"></i>
              </a>
              <a href="#">
                实例2<i class="ace-icon fa fa-circle green"></i>
              </a>
              <a href="#">
                实例3<i class="ace-icon fa fa-circle green"></i>
              </a>
              <a href="#">
                实例4<i class="ace-icon fa fa-circle red"></i>
              </a>
            </td>
            <td>集群1<i class="ace-icon fa fa-circle green"></i></td>
            <td>外网1<i class="ace-icon fa fa-circle green"></i></td>

            <td>

              <div class=" btn-group">
                <button class="btn btn-xs btn-info">
                  <i class="ace-icon fa fa-pencil-square-o"></i> 编辑
                </button>
              </div>
            </td>
          </tr>

          <tr>
            <td>业务系统 3</td>
            <td>
              <a href="#">
                实例1<i class="ace-icon fa fa-circle red"></i>
              </a>
              <a href="#">
                实例2<i class="ace-icon fa fa-circle green"></i>
              </a>
              <a href="#">
                实例3<i class="ace-icon fa fa-circle green"></i>
              </a>
              <a href="#">
                实例4<i class="ace-icon fa fa-circle green"></i>
              </a>
            </td>
            <td>集群1<i class="ace-icon fa fa-circle green"></i></td>
            <td>外网1<i class="ace-icon fa fa-circle green"></i></td>

            <td>

              <div class=" btn-group">
                <button class="btn btn-xs btn-info">
                  <i class="ace-icon fa fa-pencil-square-o"></i> 编辑
                </button>
              </div>
            </td>
          </tr>

          <tr>
            <td>业务系统 4</td>
            <td>
              <a href="#">
                实例1<i class="ace-icon fa fa-circle red"></i>
              </a>
              <a href="#">
                实例2<i class="ace-icon fa fa-circle red"></i>
              </a>
              <a href="#">
                实例3<i class="ace-icon fa fa-circle red"></i>
              </a>
              <a href="#">
                实例4<i class="ace-icon fa fa-circle red"></i>
              </a>
            </td>
            <td>集群1<i class="ace-icon fa fa-circle red"></i></td>
            <td>外网1<i class="ace-icon fa fa-circle red"></i></td>

            <td>

              <div class=" btn-group">
                <button class="btn btn-xs btn-info">
                  <i class="ace-icon fa fa-pencil-square-o"></i> 编辑
                </button>
              </div>
            </td>
          </tr>
          <!--<tr v-for="data in listInfo.list_data">-->

          <!--<td> {{data.ip_add}}</td>-->
          <!--<td> {{data.subnet_mask}}</td>-->
          <!--<td> {{data.gateway}}</td>-->
          <!--<td> {{data.mac}}</td>-->
          <!--<td> {{data.mac_fresh_time}}</td>-->
          <!--<td>-->
          <!--<div class="form-group has-success" v-if="data.ip_type=='1'">-->
          <!--<i class="ace-icon fa fa-check-circle"></i>主IP-->
          <!--</div>-->
          <!--<div class="form-group has-error" v-if="data.ip_type=='0'">-->
          <!--<i class="ace-icon fa fa-times-circle"></i>从IP-->
          <!--</div>-->

          <!--</td>-->

          <!--<td>-->

          <!--<div class=" btn-group" >-->
          <!--<button class="btn btn-xs btn-info" v-on:click="go_ip_form(data.id)" v-if="data.ip_type!=''">-->
          <!--<i class="ace-icon fa fa-pencil-square-o"></i> 编辑-->
          <!--</button>-->


          <!--<button class="btn btn-xs btn-info" v-on:click="set_main_ip(data.id)" v-if="data.ip_type=='0'"><i class="ace-icon fa fa-wrench  bigger-110 icon-only"></i>-->
          <!--设为主IP-->
          <!--</button>-->

          <!--<button class="btn btn-xs btn-danger" v-on:click="del(data.id)" v-if="data.ip_type=='0'"><i class="ace-icon fa fa-trash-o"></i>-->
          <!--删除-->
          <!--</button>-->
          <!--</div>-->


          <!--</td>-->
          <!--</tr>-->

          </tbody>
        </table>


      </div>


      <!--  ===================== separate line ===================== -->
    </div>
  </div>


</template>

<script>

  import Pages from '../../common/Pages'

  import {getTaskWaiting} from '../../../vuex/modules/task/TaskGetters'
  import {getCodeTable} from '../../../vuex/modules/common/CodeTableGetters'

  import {reqWaitingTasks} from '../../../vuex/modules/task/TaskActions'

  export default {
    data: function () {
      return {
        pageSize: 10
      }
    },
    vuex: {
      getters: {
        waiting: getTaskWaiting,
        CT: getCodeTable
      },
      actions: {
        reqWaitingTasks
      }
    },
    components: {
      Pages
    },
    methods: {
      next: function (pageNumber) {
        console.debug("this parent page-next event " + pageNumber);
        this.reqWaitingTasks(pageNumber, this.pageSize);
      },
      prev: function (pageNumber) {
        console.debug("this parent page-prev event " + pageNumber);
        this.reqWaitingTasks(pageNumber, this.pageSize);
      },
      first: function (pageNumber) {
        console.debug("this parent page-first event " + pageNumber);
        this.reqWaitingTasks(pageNumber, this.pageSize);
      },
      end: function (pageNumber) {
        console.debug("this parent page-end event " + pageNumber);
        this.reqWaitingTasks(pageNumber, this.pageSize);
      },
      pageSet: function (pageNumber) {
        console.debug("this parent page-set event " + pageNumber);
        this.reqWaitingTasks(pageNumber, this.pageSize);
      },

      process: function (task) {
        console.debug(task);
        //TODO if task type
        this.$router.go({
          // path:'/mywork/mytask/myform',
          name: 'myform',
          params: {
            taskId: task.task_id,
            taskType: task.task_type,
            nodeId: task.task_info.node_id,
            instanceId: task.task_info.instance_id
          }
        });
      },
      del: function (task) {
        console.debug(task);
      }
    },
    ready: function () {
      this.reqWaitingTasks(1, 10);
    }

  }
</script>

