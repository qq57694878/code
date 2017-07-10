import {ADD_TO_MAINMENU} from '../../mutation-types'

const state = {
	menu: [
		// {name:'软件运维',
		//  path:'/software',
		//  children:
		// 	[
		// 		{name:'需求变更',path:'/requirement'},
		// 		{name:'数据修改',path:'/datamodify'},
		// 		{name:'程序发布',path:'/release'},
		// 		{name:'数据查询',path:'/query'},
		// 		{name:'成果物',path:'/deliverables'}
		// 	]
		// }
		{name:'我的工作',
		 path:'',
		 icon:'fa-desktop',
		 children:
			[
			{name:'发起任务',path:'/mywork/task_start'},
			{name:'待办任务',path:'/mywork/task_waiting'},
			{name:'已办任务',path:'/mywork/task_history'},
				{name:'巡检',path:'/mywork/inspecting'},
				{name:'故障应急',path:'/mywork/failureemergency'},
					{name:'我的日历',path:'/mywork/calendar'},
					{name:'通知',path:'/mywork/notice/receive'}
			]
		},
		{name:'巡检管理',
		 path:'',
		 icon:'fa-desktop',
		 children:
			[
			{name:'巡检模板管理',path:'/inspection/inspectionTemplate'},
			{name:'巡检计划管理',path:'/inspection/inspectionPlans'},
			{name:'巡检执行',path:''},
      {name:'巡检记录查询',path:''}
			]
		},
		{name:'硬件管理',
		 path:'',
		 icon:'fa-hdd-o',
		 children:
			[
				{name:'机房基础设施管理',path:'/hardware/infrastructure/index'},
				{name:'服务器管理',path:'/hardware/server/index'},
				{name:'网络及安全设备管理',path:'/hardware/network_equipment/index'},
				{name:'支撑软件管理',path:'/hardware/support_software/index'},
				{name:'办公设备管理',path:'/hardware/office_equipment/index'},
        {name:'业务系统部署管理',path:'/hardware/biz_deploy/index'},
				{name:'质保期管理',path:'/hardware/warranty/index'}
			]
		},
    {name:'项目管理',
      path:'',
      icon:'fa-briefcase',
      children:
        [
          {name:'业务系统管理',path:'/resource/man/businessapp'},
          {name:'项目立项申请',path:'/project/man/project_apply'},
          {name:'项目采购管理',path:'/project/man/project_purchase'},
          {name:'合同登记',path:'/project/man/contract_register'},
          {name:'合同执行计划',path:'/project/man/contract_exec_plan'},
          {name:'合同执行',path:'/project/man/contract_exec'},
          {name:'合同验收',path:'/project/man/contract_acceptance'},
          {name:'合同付款',path:'/project/man/contract_payment'},
          {name:'合同查询',path:'/project/man/contract_query'},
          {name:'合同付款一览',path:'/project/man/contract_payment_overview'}
        ]
    },
    {name:'IP地址管理',
      path:'/resource/man/ip',
      icon:'fa-wifi'
    },
    {name:'系统监控',
      path:'',
      icon:'fa-bar-chart',
      children:
        [
          {name:'业务系统监控',path:'/monitor/biz'},
          {name:'服务器监控',path:'/monitor/server'},
          {name:'数据库监控',path:'/monitor/database'},
          {name:'网络监控',path:'/monitor/network'}

        ]
    },
    {name:'资源管理',
      path:'',
      icon:'fa-cog',
      children:
        [
          {name:'组织机构管理',path:'/resource/man/synergy'},
          {name:'系统用户管理',path:'/resource/man/synergy'},
          {name:'外协单位管理',path:'/resource/man/vendor'},
          {name:'机房管理',path:'/resource/man/room'},
          {name:'角色权限管理',path:'/deliverables'},
          {name:'数据资源管理',path:''}
        ]
    }
	],
	text:"txt"
}

const mutations = {
	[ADD_TO_MAINMENU] (state,menulist) {
		state.menu = menulist
	}
}

export default {
	state,
	mutations
}

