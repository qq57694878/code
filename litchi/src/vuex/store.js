import Vue from 'vue'
import Vuex from 'vuex'
//import * as actions from './actions'
import MainMenu from './modules/authentication/MainMenuState'
import Login from './modules/authentication/LoginState'
import BusinessApp from './modules/resource-management/BusinessAppState'
import CodeTable from './modules/common/CodeTableState'
import Vendor from './modules/resource-management/VendorState'
import Room from './modules/resource-management/RoomState'
import Org from './modules/resource-management/OrgState'
import DuailListBox from './modules/common/DuailListBoxState'

import task from './modules/task/TaskState'

import hardward_cabinet from './modules/hardward/infrastructure/CabinetState';
import hardward_x86 from './modules/hardward/server/X86State';
import hardward_storage from './modules/hardward/server/StorageState';
import hardward_database from './modules/hardward/support_software/DatabaseState';
import hardward_vmplat from './modules/hardward/support_software/VmPlatState';

import IP from './modules/ip-management/IpState';
import Notice from './modules/notice/NoticeState';
import dashboard from './modules/dashboard/DashboardState';
import monbiz from './modules/monitoring/MonBizState';
import monserver from './modules/monitoring/MonServerState';
import mondb from './modules/monitoring/MonDBState';


import ServerIP from './modules/ip-management/ServerIpState';

import ProjectApply from './modules/project-management/ProjectApplyState';
import ProjectPurchase from './modules/project-management/ProjectPurchaseState';
import ContractRegister from './modules/project-management/ContractRegisterState';
import ContractExecPlan from './modules/project-management/ContractExecPlanState';
import Inspection from './modules/inspection/InspectionState'

Vue.use(Vuex)

export default new Vuex.Store({

  modules: {
    CodeTable,
    DuailListBox,
    MainMenu,
    Login,
    BusinessApp,
    Vendor,
    Org,
    Room,
    task,
    hardward_cabinet,
    hardward_x86,
    hardward_storage,
    hardward_database,
    hardward_vmplat,
	  IP,
	  Notice,
	  dashboard,
	  monbiz,
	  monserver,
	  mondb,
    ServerIP,
    ContractExecPlan,
    ProjectApply,
	  ProjectPurchase,
	  ContractRegister,
    Inspection
  }
})
