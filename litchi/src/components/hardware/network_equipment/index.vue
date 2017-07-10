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
          核心交换机 &nbsp;
          <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
        </a>

        <ul class="dropdown-menu dropdown-info">
          <li>
            <a data-toggle="tab" href="#routerview" >新增</a>
          </li>

          <li>
            <a data-toggle="tab" href="#routerview" >查询</a>
          </li>
        </ul>
      </li>

      <li class="dropdown" id="vm_plat">
        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
          防火墙 &nbsp;
          <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
        </a>

        <ul class="dropdown-menu dropdown-info">
          <li>
            <a data-toggle="tab" href="#routerview" >新增</a>
          </li>

          <li>
            <a data-toggle="tab" href="#routerview" >查询</a>
          </li>
        </ul>
      </li>

      <li class="dropdown" id="middleware">
        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
          VPN &nbsp;
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

      <li class="dropdown" id="fuzaijunheng">
        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
          负载均衡 &nbsp;
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



  export default {

    vuex: {

      actions: {
      }
    },
    components:{
    },
    methods: {

    }

  }
</script>

