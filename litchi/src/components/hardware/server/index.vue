<template>

  <div class="page-header">
    <h1>
      服务器管理
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


      <li class="dropdown" id="x86">
        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
          X86服务器 &nbsp;
          <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
        </a>

        <ul class="dropdown-menu dropdown-info">
          <li>
            <a data-toggle="tab" href="#routerview" v-on:click="x86FormInit()">新增</a>
          </li>

          <li>
            <a data-toggle="tab" href="#routerview" v-on:click="x86ListInit(1,10)">查询</a>
          </li>
        </ul>
      </li>


      <li class="dropdown" id="storage">
        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
          磁盘阵列 &nbsp;
          <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
        </a>

        <ul class="dropdown-menu dropdown-info">
          <li>
            <a data-toggle="tab" href="#routerview" v-on:click="storageFormInit()">新增</a>
          </li>

          <li>
            <a data-toggle="tab" href="#routerview" v-on:click="storageListInit(1,10)">查询</a>
          </li>
        </ul>
      </li>

      <li class="dropdown">
        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
          虚拟机 &nbsp;
          <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
        </a>

        <ul class="dropdown-menu dropdown-info">
          <li>
            <a data-toggle="tab" href="#vm1">新增</a>
          </li>

          <li>
            <a data-toggle="tab" href="#vm2">查询</a>
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

  import x86_list from './x86_list.vue';
  import x86_form from './x86_form.vue';
  import storage_form from './storage_form.vue';
  import storage_list from './storage_list.vue';
  import {reqQueryInit as x86ReqQueryInit} from '../../../vuex/modules/hardward/server/X86Actions';
  import {reqQueryInit as storageReqQueryInit} from '../../../vuex/modules/hardward/server/StorageActions';
  import {reqFormInit as x86ReqFormInit} from '../../../vuex/modules/hardward/server/X86Actions';
  import {reqFormInit as storageReqFormInit} from '../../../vuex/modules/hardward/server/StorageActions';
  export default {
    vuex: {
      actions: {
        x86ReqQueryInit,
        x86ReqFormInit,
        storageReqQueryInit,
        storageReqFormInit
      }
    },
    components: {
      x86_list,
      x86_form,
      storage_list,
      storage_form
    },
    methods: {
      x86FormInit: function () {
        this.x86ReqFormInit();
        this.$router.go({
          name: 'server_x86_form'
        });
      },
      x86ListInit: function (pageNo, pageSize) {
        this.x86ReqQueryInit(pageNo, pageSize);
        this.$router.go({
          name: 'server_x86_list'
        });
      },
      storageFormInit: function () {
        this.storageReqFormInit();
        this.$router.go({
          name: 'server_storage_form'
        });
      },
      storageListInit: function (pageNo, pageSize) {
        this.storageReqQueryInit(pageNo, pageSize);
        this.$router.go({
          name: 'server_storage_list'
        });
      }
    }


  }
</script>

