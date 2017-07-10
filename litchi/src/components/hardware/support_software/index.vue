<template>

  <div class="page-header">
    <h1>
      支撑平台管理
    </h1>
  </div>
  <div class="tabbable">
    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="myTab">
      <li class="active">
        <a data-toggle="tab" href="#home">
          <i class="green ace-icon fa fa-home bigger-120"></i>
          总体情况
        </a>
      </li>


      <li class="dropdown" id="database">
        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
          数据库 &nbsp;
          <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
        </a>

        <ul class="dropdown-menu dropdown-info">
          <li>
            <a data-toggle="tab" href="#routerview" v-on:click="databaseFormInit()">新增</a>
          </li>

          <li>
            <a data-toggle="tab" href="#routerview" v-on:click="databaseListInit(1,10)">查询</a>
          </li>
        </ul>
      </li>

      <li class="dropdown" id="vm_plat">
        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
          虚拟化平台 &nbsp;
          <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
        </a>

        <ul class="dropdown-menu dropdown-info">
          <li>
            <a data-toggle="tab" href="#routerview" v-on:click="vmplatFormInit()">新增</a>
          </li>

          <li>
            <a data-toggle="tab" href="#routerview" v-on:click="vmplatListInit(1,10)">查询</a>
          </li>
        </ul>
      </li>

      <li class="dropdown" id="middleware">
        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
          中间件 &nbsp;
          <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
        </a>

        <ul class="dropdown-menu dropdown-info">
          <li>
            <a data-toggle="tab" href="#routerview">新增</a>
          </li>

          <li>
            <a data-toggle="tab" href="#routerview">查询</a>
          </li>
        </ul>
      </li>


    </ul>
    <div class="tab-content">
      <div id="home" class="tab-pane fade in active">
        <p>总体情况</p>
      </div>
      <div id="routerview" class="tab-pane fade">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>

  import database_form from './database_form.vue';
  import database_list from './database_list.vue';

  import {reqQueryInit as databaseReqQueryInit} from '../../../vuex/modules/hardward/support_software/DatabaseActions';
  import {reqFormInit as databaseReqFormInit} from '../../../vuex/modules/hardward/support_software/DatabaseActions';


  import vmplat_form from './vm_platform_form.vue';
  import vmplat_list from './vm_platform_list.vue';

  import {reqQueryInit as vmplatReqQueryInit} from '../../../vuex/modules/hardward/support_software/VmPlatActions';
  import {reqFormInit as vmplatReqFormInit} from '../../../vuex/modules/hardward/support_software/VmPlatActions';


  export default {

    vuex: {

      actions: {
        databaseReqQueryInit,
        databaseReqFormInit,
        vmplatReqQueryInit,
        vmplatReqFormInit
      }
    },
    components:{
      database_form,
      database_list,
      vmplat_form,
      vmplat_list
    },
    methods: {
      databaseFormInit: function () {
        console.debug('databaseFormInit');
        this.databaseReqFormInit();
        this.$router.go({
          name: 'support_database_form'
        });
      },
      databaseListInit: function (pageNo, pageSize) {
        console.debug('databaseListInit');
        this.databaseReqQueryInit(pageNo, pageSize);
        this.$router.go({
          name: 'support_database_list'
        });
      },
      vmplatFormInit: function () {
        console.debug('vmplatFormInit');
        this.vmplatReqFormInit();
        this.$router.go({
          name: 'support_vm_platform_form'
        });
      },
      vmplatListInit: function (pageNo, pageSize) {
        console.debug('vmplatListInit');
        this.vmplatReqQueryInit(pageNo, pageSize);
        this.$router.go({
          name: 'support_vm_platform_list'
        });
      },
    }

  }
</script>

