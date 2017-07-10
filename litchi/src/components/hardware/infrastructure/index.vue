<template>

  <div class="page-header">
    <h1>
      基础设施管理
    </h1>
  </div>
  <div class="tabbable" id="">
    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="myTab">
      <li class="active">
        <a data-toggle="tab" href="#home">
          <i class="green ace-icon fa fa-home bigger-120"></i>
          机房
        </a>
      </li>



      <li class="dropdown" id='cabinet'>
        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
          机柜 &nbsp;
          <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
        </a>

        <ul class="dropdown-menu dropdown-info">
          <li>
            <a data-toggle="tab" href="#routerview" v-on:click="cabinetNewForm()">新增</a>
          </li>

          <li>
            <a data-toggle="tab" href="#routerview" v-on:click="cabinetList()">查询</a>
          </li>
        </ul>
      </li>




    </ul>
    <div class="tab-content">
      <div id="home" class="tab-pane fade in active">
        <total_msg></total_msg>
      </div>

      <div id="routerview" class="tab-pane fade" >
        <router-view></router-view>
      </div>





    </div>
  </div>
</template>

<script>
import cabinet_list from './cabinet_list.vue';
import total_msg from './total_msg.vue';
import cabinet_form from './cabinet_form.vue';
import {reqQueryInit as cabinetReqQueryInit} from '../../../vuex/modules/hardward/infrastructure/CabinetActions';
import {reqFormInit as cabinetReqFormInit} from '../../../vuex/modules/hardward/infrastructure/CabinetActions';


  export default {

    vuex:{

      actions:{
        cabinetReqQueryInit,
        cabinetReqFormInit
      }
    },
    components: {
      cabinet_list,
      cabinet_form,
      total_msg
    },
    methods: {
      cabinetNewForm : function () {
        this.cabinetReqFormInit();
        this.$router.go({
          name: 'cabinet_form'
        });
      },
      cabinetList : function () {
        this.cabinetReqQueryInit();
        this.$router.go({
          name: 'cabinet_list'
        });
      }
    }

  }
</script>

