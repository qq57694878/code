
<template>

  <alert alert-type="alert-warning" content="未添加机柜信息,请点击'机柜'->'新增'." show="true"></alert>
  <div class="row">
    <div class="col-xs-12">
      <!--  ===================== separate line ===================== -->

      <div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer">

        <table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
          <thead>
          <tr>
            <th>资产编号</th>
            <th>机柜名称</th>
            <th>品牌</th>
            <th>型号</th>
            <th>机柜容量(U)</th>
            <th>尺寸(高*宽*深)(mm)</th>
            <th>最大载重(kg)</th>
            <th>操作</th>

          </tr>
          </thead>
          <tbody>
          <tr v-for="task in waiting.tasks">

            <td> {{task.task_info.title}} </td>
            <td> {{task.task_info.title}} </td>
            <td> {{task.task_info.stime}} </td>
            <td> {{task.task_info.etime}} </td>
            <td> {{task.task_info.etime}} </td>
            <td> {{task.task_info.etime}} </td>
            <td>{{task.task_info.node_name}}&nbsp;&nbsp;<span class="label label-sm label-warning" v-if="task.task_info.flag!='normal'" > {{CT.taskState[task.task_info.flag] }} </span> </td>

            <td>
              <div class="hidden-sm hidden-xs btn-group">
                <button class="btn btn-xs btn-info" v-on:click="process(task)"> <i class="ace-icon fa fa-pencil-square-o"></i> 编辑 </button>
                <button class="btn btn-xs btn-danger"  v-on:click="del(task)"> <i class="ace-icon fa fa-trash-o"></i> 删除</button>
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
              <pages v-bind:total-size="waiting.pagetotal" v-bind:page-size="pageSize" v-on:page-set="pageSet" v-on:page-next="next" v-on:page-prev="prev" v-on:page-first="first" v-on:page-end="end"></pages>
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

  import {getTaskWaiting} from '../../../vuex/modules/task/TaskGetters'
  import {getCodeTable} from '../../../vuex/modules/common/CodeTableGetters'

  import {reqWaitingTasks} from '../../../vuex/modules/task/TaskActions'

  export default {
    data:function(){
      return {
        pageSize:10
      }
    },
    vuex:{
      getters:{
        waiting:getTaskWaiting,
        CT:getCodeTable
      },
      actions:{
        reqWaitingTasks
      }
    },
    components: {
      Pages,
      Alert
    },
    methods:{
      next:function(pageNumber) {
        console.debug("this parent page-next event "+pageNumber);
        this.reqWaitingTasks(pageNumber,this.pageSize);
      },
      prev:function(pageNumber){
        console.debug("this parent page-prev event "+pageNumber);
        this.reqWaitingTasks(pageNumber,this.pageSize);
      },
      first:function(pageNumber){
        console.debug("this parent page-first event "+pageNumber);
        this.reqWaitingTasks(pageNumber,this.pageSize);
      },
      end:function(pageNumber){
        console.debug("this parent page-end event "+pageNumber);
        this.reqWaitingTasks(pageNumber,this.pageSize);
      },
      pageSet:function(pageNumber){
        console.debug("this parent page-set event "+pageNumber);
        this.reqWaitingTasks(pageNumber,this.pageSize);
      },

      process:function(task){
        console.debug(task);
        //TODO if task type
        this.$router.go({
          // path:'/mywork/mytask/myform',
          name:'myform',
          params:{
            taskId:task.task_id,
            taskType:task.task_type,
            nodeId:task.task_info.node_id,
            instanceId:task.task_info.instance_id
          }
        });
      },
      del:function(task){
        console.debug(task);
      }
    },
    ready:function(){
      this.reqWaitingTasks(1,10);
    }

  }
</script>

