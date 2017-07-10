
import myForm from './components/task/myForm'
import Dashboard from './components/Dashboard'

import TaskWaiting from './components/task/TaskWaiting'
import TaskStart from './components/task/TaskStart'


import Inspecting from './components/Inspecting'
import InspectingForm from './components/InspectingForm'
import FailureEmergency from './components/FailureEmergency'

import Calendar from './components/Calendar'

import TaskHistory from './components/task/TaskHistory'
import BusinessApp from './components/resource-management/BusinessApp'
import BusinessAppAdd from './components/resource-management/BusinessAppAdd'
import Room from './components/resource-management/Room'
import RoomAdd from './components/resource-management/RoomAdd'
import Vendor from './components/resource-management/Vendor'
import VendorAdd from './components/resource-management/VendorAdd'

import Info from './components/common/Info'


import StartRequirementChange from './components/task/StartRequirementChange'
import StartDataChange from './components/task/StartDataChange'
import StartDataQuery from './components/task/StartDataQuery'
import StartProgramDeploy from './components/task/StartProgramDeploy'



import Ip from './components/ip-management/Ip'
import IpAdd from './components/ip-management/IpAdd'

import Notice from './components/notice/Notice'
import SendNotice from './components/notice/SendNotice'
import ReceiveNotice from './components/notice/ReceiveNotice'

import BizMonitor from './components/monitoring-system/BizMonitor'
import ServerMonitor from './components/monitoring-system/ServerMonitor'
import DBMonitor from './components/monitoring-system/DBMonitor'

import ProjectApply from './components/project-management/ProjectApply'
import ProjectApplyAdd from './components/project-management/ProjectApplyAdd'
import ProjectPurchase from './components/project-management/ProjectPurchase'
import ProjectPurchaseDetail from './components/project-management/ProjectPurchaseDetail'
import ProjectPurchaseDetailAdd from './components/project-management/ProjectPurchaseDetailAdd'
import ProjectPurchaseDetailDetail from './components/project-management/ProjectPurchaseDetailDetail'
import ContractRegister from './components/project-management/ContractRegister'
import ContractRegisterDetail  from './components/project-management/ContractRegisterDetail'
import ContractRegisterDetailPayAdd  from './components/project-management/ContractRegisterDetailPayAdd'
import ContractRegisterDetailPayDetail  from './components/project-management/ContractRegisterDetailPayDetail'
import ContractExecPlan from './components/project-management/ContractExecutionPlan'
import ContractExecPlanDetail from './components/project-management/ContractExecPlanDetail'
import ContractExec from './components/project-management/ContractExecution'
import ContractExecDetail from './components/project-management/ContractExecDetail'
import ContractAcceptance from './components/project-management/ContractAcceptance'
import ContractAcceptanceDetail from './components/project-management/ContractAcceptanceDetail'
import ContractPayment from './components/project-management/ContractPayment'
import ContractPaymentDetail from './components/project-management/ContractPaymentDetail'
import ContractQuery from './components/project-management/ContractQuery'
import ContractPaymentOverview from './components/project-management/ContractPaymentOverview'
import InspectionTpl from './components/inspection/InspectionTemplate'
import InspectionTplDetail from './components/inspection/InspectionTplDetail'
import InspectionPlans from './components/inspection/InspectionPlans'
import InspectionPlanDetail from './components/inspection/InspectionPlanDetail'

export function configRouter (router) {
	//normal routes
	router.map({
		'/dashboard':{
			name:'dashboard',
			component:Dashboard
		},
		'/info/:goback/:info':{
			name:'info',
			component:Info
		},
		// '/mywork':	{
		// 	component:hello,
		// 	subRoutes: {
		// 		'/tab': {
		// 			component:yes
		// 		}
		// 	}
		// },
		'/mywork/task_waiting': {
			name:'task_waiting',
			component:TaskWaiting
		},
		'/mywork/mytask/myform/:taskId/:taskType/:nodeId/:instanceId/:nodeName': {
			name:'myform',
			component:myForm
		},
		'/mywork/task_start': {
			component:TaskStart
		},
		'/mywork/task_start/requirement_change': {
			name:'start_requirement_change',
			component:StartRequirementChange
		},
		'/mywork/task_start/data_change': {
			name:'start_data_change',
			component:StartDataChange
		},
		'/mywork/task_start/data_query': {
			name:'start_data_query',
			component:StartDataQuery
		},
		'/mywork/task_start/program_deploy': {
			name:'start_program_deploy',
			component:StartProgramDeploy
		},

		'/mywork/task_history': {
			component:TaskHistory
		},
		'/mywork/inspecting': {
			component:Inspecting
		},
		'/mywork/inspecting/form': {
			component:InspectingForm
		},
		'/mywork/failureemergency': {
			component:FailureEmergency
		},
		'/mywork/calendar': {
			component:Calendar
		},
		'/mywork/notice': {
			name:'notice',
			component:Notice,
			subRoutes: {
				'/send': {
					name:'send_notice',
					component:SendNotice
				},
				'/receive':{
					name:'receive_notice',
					component:ReceiveNotice
				}
			}
		},
		'/resource/man/businessapp': {
			name:'businessapp',
			component:BusinessApp
		},
		'/resource/man/businessappadd': {
			name:'businessapp_add',
			component:BusinessAppAdd
		},
		'/resource/man/room': {
			name:'room',
			component:Room
		},
		'/resource/man/roomadd': {
			name:'room_add',
			component:RoomAdd
		},
		'/resource/man/vendor': {
			name:'vendor',
			component:Vendor
		},
		'/resource/man/vendoradd': {
			name:'vendor_add',
			component:VendorAdd
		},
		'/resource/man/ip': {
			name:'ip_man',
			component:Ip
		},
		'/resource/man/ip/add': {
			name:'ip_add',
			component:IpAdd
		},
		'/monitor/biz': {
			component:BizMonitor
		},
		'/monitor/database': {
			component:DBMonitor
		},
		'/monitor/server': {
			component:ServerMonitor
		},
		'/project/man/project_apply': {
			name:'project_apply',
			component:ProjectApply
		},
		'/project/man/project_apply_add': {
			name:'project_apply_add',
			component:ProjectApplyAdd
		},
		'/project/man/project_purchase': {
			name:'project_purchase',
			component:ProjectPurchase
		},
		'/project/man/project_purchase_detail': {
			name:'project_purchase_detail',
			component:ProjectPurchaseDetail,
			subRoutes: {
				'/add':{
					name:'project_purchase_detail_add',
					component:ProjectPurchaseDetailAdd
				},
				'/detail':{
					name:'project_purchase_detail_detail',
					component:ProjectPurchaseDetailDetail
				}
			}
		},
		'/project/man/contract_register': {
			name:'contract_register',
			component:ContractRegister
		},

    '/project/man/contract_exec_plan': {
      name:'project_contract_exec_plan',
      component:ContractExecPlan
    },
    '/project/man/contract_exec_plan_detail': {
      name:'project_contract_exec_plan_detail',
      component:ContractExecPlanDetail
    },
    '/project/man/contract_exec': {
      name:'project_contract_exec',
      component:ContractExec
    },
    '/project/man/contract_exec_detail': {
      name:'project_contract_exec_detail',
      component:ContractExecDetail
    },

		'/project/man/contract_register_detail': {
			name:'contract_register_detail',
			component:ContractRegisterDetail,
			subRoutes: {
				'/payadd':{
					name:'contract_register_detail_payadd',
					component:ContractRegisterDetailPayAdd
				},
				'/detail':{
					name:'contract_register_detail_paydetail',
					component:ContractRegisterDetailPayDetail
				}
			}

		},
		'/project/man/contract_acceptance': {
			name:'contract_acceptance',
			component:ContractAcceptance
		},
		'/project/man/contract_acceptance_detail': {
			name:'contract_acceptance_detail',
			component:ContractAcceptanceDetail
		},
		'/project/man/contract_payment': {
			name:'contract_payment',
			component:ContractPayment
		},
		'/project/man/contract_payment_detail': {
			name:'contract_payment_detail',
			component:ContractPaymentDetail
		},
		'/project/man/contract_query': {
			name:'contract_query',
			component:ContractQuery
		},
		'/project/man/contract_payment_overview': {
			name:'contract_payment_overview',
			component:ContractPaymentOverview
		},
    '/inspection/inspectionTemplate':{
      name:'inspection_template',
      component:InspectionTpl
    },
    '/inspection/inspectionTplDetail':{
      name:'inspection_tpl_detail',
      component:InspectionTplDetail
    },
    '/inspection/inspectionPlans':{
      name:'inspection_plans',
      component:InspectionPlans
    },
    '/inspection/inspectionPlanDetail':{
      name:'inspection_plan_detail',
      component:InspectionPlanDetail
    }
	})
}
