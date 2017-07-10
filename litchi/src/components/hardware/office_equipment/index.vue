<template>

  <div class="page-header">
    <h1>
      办公设备管理
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



      <li class="dropdown">
        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
          PC机 &nbsp;
          <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
        </a>

        <ul class="dropdown-menu dropdown-info">
          <li>
            <a data-toggle="tab" href="#cabinet1">新增</a>
          </li>

          <li>
            <a data-toggle="tab" href="#cabinet2">查询</a>
          </li>
        </ul>
      </li>

      <li class="dropdown">
        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
          笔记本 &nbsp;
          <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
        </a>

        <ul class="dropdown-menu dropdown-info">
          <li>
            <a data-toggle="tab" href="#cabinet1">新增</a>
          </li>

          <li>
            <a data-toggle="tab" href="#cabinet2">查询</a>
          </li>
        </ul>
      </li>

      <li class="dropdown">
        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
          打印机 &nbsp;
          <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
        </a>

        <ul class="dropdown-menu dropdown-info">
          <li>
            <a data-toggle="tab" href="#cabinet1">新增</a>
          </li>

          <li>
            <a data-toggle="tab" href="#cabinet2">查询</a>
          </li>
        </ul>
      </li>

    <li class="dropdown">
      <a data-toggle="dropdown" class="dropdown-toggle" href="#">
        投影仪 &nbsp;
        <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
      </a>

      <ul class="dropdown-menu dropdown-info">
        <li>
          <a data-toggle="tab" href="#ups1">新增</a>
        </li>

        <li>
          <a data-toggle="tab" href="#ups2">查询</a>
        </li>
      </ul>
    </li>

    </ul>
    <div class="tab-content">
      <div id="home" class="tab-pane fade in active">
        <p>总体情况</p>
      </div>

      <div id="cabinet1" class="tab-pane fade">
        <p>添加机柜表单</p>
      </div>

      <div id="cabinet2" class="tab-pane fade">
        <p>机柜查询列表</p>
      </div>


      <div id="ups1" class="tab-pane fade">
        <p>添加UPS</p>
      </div>

      <div id="ups2" class="tab-pane fade">
        <p>UPS查询列表</p>
      </div>
    </div>
  </div>
</template>

<script>

  export default {
    methods: {
      startRequirementChange: function () {
        this.$router.go({name: 'start_requirement_change'});
      }
    }

  }
</script>

