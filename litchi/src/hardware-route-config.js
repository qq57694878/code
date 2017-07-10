import infrastructure_index from './components/hardware/infrastructure/index';

import infrastructure_cabinet_form from './components/hardware/infrastructure/cabinet_form.vue';

import infrastructure_cabinet_list from './components/hardware/infrastructure/cabinet_list.vue';


import server_index from './components/hardware/server/index';
import server_x86_form from './components/hardware/server/x86_form';
import server_x86_list from './components/hardware/server/x86_list';
import server_storage_form from './components/hardware/server/storage_form';
import server_storage_list from './components/hardware/server/storage_list';


import server_ip_list from './components/ip-management/server_ip_list.vue';
import server_ip_form from './components/ip-management/server_ip_form.vue';

import network_index from './components/hardware/network_equipment/index';

import support_software_index from './components/hardware/support_software/index';
import support_database_form from './components/hardware/support_software/database_form.vue';
import support_database_list from './components/hardware/support_software/database_list.vue';
import support_vm_platform_form from './components/hardware/support_software/vm_platform_form.vue';
import support_vm_platform_list from './components/hardware/support_software/vm_platform_list.vue';

import office_equipment_index from './components/hardware/office_equipment/index';

import hd_biz_deploy_index from './components/hardware/biz_deploy/index';

export function hd_configRouter(router) {
  //normal routes
  router.map({

    //机房基础设施_首页
    '/hardware/infrastructure/index': {
      name: 'infrastructure_index',
      component: infrastructure_index,
      subRoutes: {
        'cabinet_form': {
          name: 'cabinet_form',
          component: infrastructure_cabinet_form
        },
        'cabinet_list': {
          name: 'cabinet_list',
          component: infrastructure_cabinet_list
        }
      }
    },
    //服务器_首页
    '/hardware/server/index': {
      name: 'server_index',
      component: server_index,
      subRoutes: {
        '/server_x86_form': {
          name: 'server_x86_form',
          component: server_x86_form
        },
        '/server_x86_list': {
          name: 'server_x86_list',
          component: server_x86_list
        },
        '/server_storage_form': {
          name: 'server_storage_form',
          component: server_storage_form
        },
        '/server_storage_list': {
          name: 'server_storage_list',
          component: server_storage_list
        },
        '/server_ip_form/:biz_id/:biz_type/:ip_id':{
          name: 'server_ip_form',
          component: server_ip_form
        },
        '/server_ip_list/:biz_id/:biz_type':{
          name: 'server_ip_list',
          component: server_ip_list
        }
      }
    },
    //网络及安全设备_首页
    '/hardware/network_equipment/index': {
      name: 'network_index',
      component: network_index,
      subRoutes: {
        '/x86_ip_form': {
          name: 'test',
          component: server_ip_list
        },
        '/x86_ip_form1': {
          name: 'test1',
          component: {
            template: '<p>Default sub view for Foo1</p>'
          }
        },
        '/x86_ip_form2': {
          name: 'test2',
          component: {
            template: '<p>Default sub view for Foo2</p>'
          }
        }
      }
    },
    //支撑平台管理_首页
    '/hardware/support_software/index': {
      name: 'support_software_index',
      component: support_software_index,
      subRoutes: {
        '/support_database_form': {
          name: 'support_database_form',
          component: support_database_form
        },
        '/support_database_list': {
          name: 'support_database_list',
          component: support_database_list
        },
        '/support_vm_platform_form': {
          name: 'support_vm_platform_form',
          component: support_vm_platform_form
        },
        '/support_vm_platform_list': {
          name: 'support_vm_platform_list',
          component: support_vm_platform_list
        }
      }
    },
    //办公设备_首页
    '/hardware/office_equipment/index': {
      name: 'office_equipment_index',
      component: office_equipment_index
    },
    //业务系统部署管理_首页
    '/hardware/biz_deploy/index': {
      name: 'hd_biz_deploy_index',
      component: hd_biz_deploy_index
    }

  })
};
