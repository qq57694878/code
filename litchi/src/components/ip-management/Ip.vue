<template>
  <div class="page-header">
    <h1>
      IP地址管理
    </h1>
  </div>
  <div class="row">
    <div class="col-xs-12">
      <!--  ===================== separate line ===================== -->
	<div class="row" >
			<div class="col-xs-12">
				 <my-alert v-bind:alert-type="alertObj.alertType" v-bind:content="alertObj.alertContent" v-bind:show.sync="alertObj.alertShow"></my-alert>
			</div>
	</div>

      <div class="row">
        <div class="col-xs-3">


          <div class="widget-box transparent">
            <div class="widget-header widget-header-small">
              <h4 class="widget-title blue smaller">
                <i class="ace-icon fa fa-wifi orange"></i>
                网段列表
              </h4>

              <div class="widget-toolbar action-buttons">
                <a v-on:click="add">
                  <i class="ace-icon fa fa-plus blue"></i>
                </a>
                &nbsp;
              </div>
            </div>

            <div class="widget-body">
              <div class="widget-main padding-8">
                <!-- #section:pages/profile.feed -->
                <div id="ip-nest" class="profile-feed">

                  <div class="profile-activity clearfix" v-bind:class="{ 'net-selected':net_selected==net.id}"
                       v-for="net in ip_net" v-on:click="clickNet(net.id)">
                    <div>
                      <i class="pull-left thumbicon {{net | colorCls}} no-hover">{{net |colorNumber}}</i>
                      <!-- <a class="user" > {{net.ip_add}}/{{net.subnet_mask}} </a> -->
					  <span style="font-weight:bold; color:#9585BF;" >{{net.ip_add}}/{{net.subnet_mask}}</span>
                      {{net.name}}
                    </div>

                    <div class="tools action-buttons">
                      <a href="#" class="blue" v-on:click.stop="edit(net)">
                        <i class="ace-icon fa fa-pencil bigger-125"></i>
                      </a>

                      <a href="#" class="red" v-on:click.stop="del(net)">
                        <i class="ace-icon fa fa-times bigger-125"></i>
                      </a>
                    </div>
                  </div>

                </div>

                <!-- /section:pages/profile.feed -->
              </div>
            </div>
          </div>


        </div>
        <div class="col-xs-9">
          <div id="ip-array">
            <!-- <div id="ip-array" style="position:fixed;"> -->

            <template v-for="row in ip_array">
              <div class="h10" v-if="$index!=0 && $index % 5==0"></div>
              <div>
                <template v-for="cell in row">
                  <div class="w10" v-if="$index!=0 && $index % 5==0 "></div>
                  <span class="cell cell-{{cell.status}}" data-rel="tooltip"
                        title="<div><a>{{cell.ip}}</a></div><div><a>{{CT.ip_host_type[cell.host_type]?CT.ip_host_type[cell.host_type]:''}}</a></div><div>{{cell.host_name}}</div>">{{cell.hostid}}</span>
                </template>
              </div>
            </template>

            <div class="h25"></div>

            <div id="ip-sign">
              <h6 class="header smaller lighter blue">图例说明</h6>
              <ul class="list-unstyled spaced">
                <li>
                  <i class="ace-icon fa fa-square bigger-110 sign-red"></i>
                  未规划的活动IP
                </li>

                <li>
                  <i class="ace-icon fa fa-square bigger-110 sign-green"></i>
                  规划的活动IP
                </li>

                <li>
                  <i class="ace-icon fa fa-square bigger-110 sign-yellow"></i>
                  规划的非活动IP
                </li>

                <li>
                  <i class="ace-icon fa fa-square bigger-110 sign-grey"></i>
                  未规划的非活动IP
                </li>

              </ul>
            </div>

          </div>

        </div>
      </div>
      <!--  ===================== separate line ===================== -->
    </div>
  </div>

</template>

<script>

  import {getCodeTable} from '../../vuex/modules/common/CodeTableGetters'
  import {getIpArray, getIpNet, getIpNetSelected,getAlertObj} from '../../vuex/modules/ip-management/IpGetters'
  import {reqIpArray, selIpNet, reqIpnet, reqIpnetSave, reqIpnetDel,selEditNet } from '../../vuex/modules/ip-management/IpActions'

import myAlert from '../common/myAlert'
  export default {
	components: {
		myAlert
	},
    vuex: {
      getters: {
        CT: getCodeTable,
        ip_array: getIpArray,
        ip_net: getIpNet,
        net_selected: getIpNetSelected,
		alertObj:getAlertObj
      },
      actions: {
        reqIpArray,
        selIpNet,
        reqIpnet,
        reqIpnetSave,
        reqIpnetDel,
		selEditNet 
      }
    },
    methods: {
      add: function () {
        // netDialog(this);
		let net={
			id:'',
			name:'',
			description:'',
			ip_add:'',
			subnet_mask:'',
			gateway:''
		};
		this.selEditNet(this,net); 
		this.$router.go({ name:'ip_add' });
      },
      edit: function (net) {
        // netDialog(this, net);
		this.selEditNet(this,net); 
		this.$router.go({ name:'ip_add' });
      },
      del: function (net) {
		  let self=this;
		  bootbox.confirm("您确定要删除吗？", function(result) {
				if(result) {
					self.reqIpnetDel(self, net.id);
				}
		  });
      },

      clickNet: function (netId) {
        this.selIpNet(this, netId);
        this.reqIpArray(this, netId, function (comp) {
          comp.$nextTick(function () {
            $('[data-rel=tooltip]').tooltip({html: true});
          });
        });
      }
    },
    filters: {
      colorCls: function (value) {
        if (value.red_count > 0)
          return 'btn-danger';
        else if (value.green_count > 0)
          return 'btn-success';
        else if (value.yellow_count > 0)
          return 'btn-yellow';
        else
          return 'btn-grey';
      },
      colorNumber: function (value) {
        if (value.red_count > 0)
          return value.red_count;
        else if (value.green_count > 0)
          return value.green_count;
        else if (value.yellow_count > 0)
          return value.yellow_count;
        else
          return value.gray_count;
      }
    },
    ready: function () {

      var ip_scroll = $('#ip-nest').ace_scroll({
        // size:$('body').height()-400
        size: 600
      });
      // $(window).on('resize.ace_scroll', function() {
      // 		console.debug('resize');
      // 	});

      // $('[data-rel=tooltip]').tooltip({html:true});
      // $('[data-rel=popover]').popover({html:true});

	  //set default
      this.selIpNet(this,-1);

      this.reqIpnet(this);
    }
  }

  // var netDialog = function (comp, net) {
  //   if (!net) {
  //     net = {
  //       id: '',//
  //       name: '',//名称
  //       description: '',//用途
  //       ip_add: '',//
  //       subnet_mask: '',
  //       gateway: ''
  //     };
  //   }
  //   var dialog = bootbox.confirm({
  //     title: "网段信息",
  //     message: '<form class="form-horizontal" role="form" >\
								// <div class="form-group">\
									// <label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 网段名称</label>\
									// <div class="col-sm-9">\
									// <input type="text" id="form-field-1" placeholder="" class="col-sm-12" value="'+net.name+'">\
									// </div>\
								// </div>\
								// <div class="form-group">\
									// <label class="col-sm-2 control-label no-padding-right" for="form-field-2"> 网段描述</label>\
									// <div class="col-sm-9">\
									// <input type="text" id="form-field-2" placeholder="" class="col-sm-12" value="' + net.description + '">\
									// </div>\
								// </div>\
								// <div class="form-group">\
									// <label class="col-sm-2 control-label no-padding-right" for="form-field-3"> IP</label>\
									// <div class="col-sm-9">\
									// <input type="text" id="form-field-3" placeholder="" data-inputmask="\'mask\':\'999.999.999.999\'" class="col-sm-12" value="' + net.ip_add + '">\
									// </div>\
								// </div>\
								// <div class="form-group">\
									// <label class="col-sm-2 control-label no-padding-right" for="form-field-4"> NETMASK</label>\
									// <div class="col-sm-9">\
									// <input type="text" id="form-field-4" placeholder="" data-inputmask="\'mask\':\'999.999.999.999\'" class="col-sm-12" value="'+net.subnet_mask+'">\
									// </div>\
								// </div>\
								// <div class="form-group">\
									// <label class="col-sm-2 control-label no-padding-right" for="form-field-5"> Gateway</label>\
									// <div class="col-sm-9">\
									// <input type="text" id="form-field-5" placeholder="" data-inputmask="\'mask\':\'999.999.999.999\'" class="col-sm-12" value="'+net.gateway+'">\
									// </div>\
								// </div>\
							 // </form>\
							// ',
  //     buttons: {
  //       cancel: {
  //         label: '<i class="fa fa-times"></i> 取消',
  //         className: 'btn-danger'
  //       },
  //       confirm: {
  //         label: '<i class="fa fa-check"></i> 确定',
  //         className: 'btn-success'
  //       }
  //     },
  //     callback: function (result) {
  //       console.log($('#form-field-1').val());

  //       net.name = $('#form-field-1').val();
  //       net.description = $('#form-field-2').val();
  //       net.ip_add = $('#form-field-3').val();
  //       net.subnet_mask = $('#form-field-4').val();
  //       net.gateway = $('#form-field-5').val();
  //       comp.reqIpnetSave(comp, net);
  //     }
  //     // size:'small'
  //   });
  //   dialog.init(function () {
  //     console.debug('dialog init');
	  // // Inputmask('99-99').mask($('#form-field-13')[0]);
	  // $(':input').inputmask();
  //   });
  // }


</script>

<style scoped>
  .cell {
    display: inline-block;
    border: 1px solid #DCE8F1;
    width: 32px;
    height: 24px;
    background-color: #abbac3;
    text-align: center;
    line-height: 24px;
  }

  .cell-yellow {
    color: #996633;
    background-color: #fee188;
  }

  .cell-green {
    color: #FFF;
    background-color: #87b87f;
  }

  .cell-grey {
    color: #FFF;
    background-color: #abbac3;
  }

  .cell-red {
    color: #FFF;
    background-color: #d15b47;
  }

  .sign-yellow {
    color: #fee188;
  }

  .sign-green {
    color: #87b87f;
  }

  .sign-grey {
    color: #abbac3;
  }

  .sign-red {
    color: #d15b47;
  }

  .w10 {
    display: inline-block;
    width: 10px;
  }

  .h10 {
    height: 10px;
  }

  .h25 {
    height: 25px;
  }

  .w75 {
    width: 75px
  }

  #ip-sign {
    width: 250px;
    font-size: 12px;
  }

  .net-selected {
    background-color: #F4F9FD;
    border-left: 1px dotted #D0D8E0;
    border-right: 1px dotted #D0D8E0;
  }


</style>
