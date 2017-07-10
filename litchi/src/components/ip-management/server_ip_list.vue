<template>

  <div class="widget-toolbox padding-8 clearfix">
    <a href="#" class="btn btn-link" v-on:click="go_ip_form()">
      <i class="ace-icon fa fa-plus-circle bigger-120 green"></i>
      添加一个新IP地址
    </a>
  </div>


  <div class="row">
    <div class="col-xs-12">
      <!--  ===================== separate line ===================== -->

      <div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer">

        <table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
          <thead>
          <tr>
            <th>IP地址</th>
            <th>子网掩码</th>
            <th>网关</th>
            <th>MAC地址</th>
            <th>MAC地址刷新时间</th>
            <th>监控主IP</th>
            <th>操作</th>

          </tr>
          </thead>
          <tbody>
          <tr v-for="data in listInfo.list_data">

            <td> {{data.ip_add}}</td>
            <td> {{data.subnet_mask}}</td>
            <td> {{data.gateway}}</td>
            <td> {{data.mac}}</td>
            <td> {{data.mac_fresh_time}}</td>
            <td>
              <div class="form-group has-success" v-if="data.ip_type=='1'">
                <i class="ace-icon fa fa-check-circle"></i>主IP
              </div>
              <div class="form-group has-error" v-if="data.ip_type=='0'">
                <i class="ace-icon fa fa-times-circle"></i>从IP
              </div>

            </td>

            <td>

              <div class=" btn-group" >
                <button class="btn btn-xs btn-info" v-on:click="go_ip_form(data.id)" v-if="data.ip_type!=''">
                  <i class="ace-icon fa fa-pencil-square-o"></i> 编辑
                </button>


                <button class="btn btn-xs btn-info" v-on:click="set_main_ip(data.id)" v-if="data.ip_type=='0'"><i class="ace-icon fa fa-wrench  bigger-110 icon-only"></i>
                  设为主IP
                </button>

                <button class="btn btn-xs btn-danger" v-on:click="del(data.id)" v-if="data.ip_type=='0'"><i class="ace-icon fa fa-trash-o"></i>
                  删除
                </button>
              </div>


            </td>
          </tr>

          </tbody>
        </table>


      </div>


      <!--  ===================== separate line ===================== -->
    </div>
  </div>
  <div class="widget-toolbox padding-8 clearfix">
    <a href="#" class="btn btn-link" v-on:click="go_x86_list">
      <i class="ace-icon fa fa-arrow-left"></i>
      <span class="bigger-110">返回</span>
    </a>

  </div>

</template>

<script>

  import {reqQueryInit as x86ReqQueryInit} from '../../vuex/modules/hardward/server/X86Actions';

  import {reqQueryInit} from '../../vuex/modules/ip-management/ServerIpActions';
  import {delIPInfo} from '../../vuex/modules/ip-management/ServerIpActions';
  import {setMainIp} from '../../vuex/modules/ip-management/ServerIpActions';

  import {getInitList} from '../../vuex/modules/ip-management/ServerIpGetters';

  export default {
    data: function () {
      return {
        biz_type: '',//业务类型，表名
        biz_id: ''//业务表主键
      }
    },
    route: {
      data: function (transition) {
        let biz_id = transition.to.params.biz_id;
        let biz_type = transition.to.params.biz_type;
        transition.next({biz_id: biz_id, biz_type: biz_type});
      }
    },
    vuex: {
      getters: {
        listInfo: getInitList
      },
      actions: {
        x86ReqQueryInit,
        reqQueryInit,
        delIPInfo,
        setMainIp
      }
    },
    methods: {
      go_x86_list: function () {
        if (this.biz_type == 'HW_X86') {
          this.$router.go({
            name: 'server_x86_list'
          });
        }
      },
      go_ip_form: function (ip_id) {
        this.$router.go({
          name: 'server_ip_form',
          params: {
            biz_type: this.biz_type,
            biz_id: this.biz_id,
            ip_id:ip_id
          }
        });
      },
      del: function (input_id) {
        let self = this;
        bootbox.confirm("您确定要删除此条记录吗？", function (result) {
          if (result) {

            self.delIPInfo(input_id,function () {
              console.debug("11111");
              self.reqQueryInit(self.biz_id, self.biz_type);
            });

          }
        });

      },
      set_main_ip: function (input_id) {
        let self = this;
        bootbox.confirm("您确定将此ip设为主IP吗？", function (result1) {
          if (result1) {
            self.setMainIp(input_id,function () {
              console.debug("22222");
              self.reqQueryInit(self.biz_id, self.biz_type);
            });

          }
        });

      }

    },
    ready: function () {
      this.reqQueryInit(this.biz_id, this.biz_type);
    }

  }
</script>

