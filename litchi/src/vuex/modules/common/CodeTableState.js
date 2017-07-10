

const taskType={
	'sw_requirement_change':'需求变更',
	'sw_data_change':'数据变更',
	'sw_data_query':'数据查询',
	'sw_program_deploy':'程序发布'
}

const taskState={
	'normal':'正常',
	'reject':'驳回',
	'stop':'终止'
}

const requirementChangeType={
	1:'新增业务需求',
	2:'原有业务调整',
	3:'BUG修复'
}

const projectApplyAttchmentType={
	1:'条件',
	2:'个性',
	3:'哈哈'
}

const procurementMethodtype={
	'0':'公开招标',
	'1':'邀请招标',
	'2':'竞争性谈判',
	'3':'单一来源',
	'4':'询价'
}

const procurementStatustype={
	0:'未招标',
	1:'招标中',
	2:'已中标',
	3:'签订合同'
}

const procurementAttchmentType={
  1:'条件',
  2:'个性',
  3:'哈哈'
}

const contractExecAttchmentType={
  1:'条件',
  2:'个性',
  3:'哈哈'
}

const x86_main_usage={
  1:'业务服务器',
  2:'虚拟化服务器',
  3:'数据库服务器',
  99:'其他'
}

const ip_host_type={
  'hw_database':'数据库',
  'hw_storage':'存储设备',
  'hw_virtual_machine':'虚拟机',
  'hw_x86':'x86服务器'
}

const contract_status={
  1:'登记中',
  2:'计划中',
  3:'执行中',
  4:'验收中',
  5:'已完成'
}

const payment_status={
  0:'未完成',
  1:'已完成'
}

const contract_type={
  0:'软件开发类',
  1:'软件维护类',
  2:'硬件购置类',
  3:'硬件运维类',
  4:'硬件质保类'
}
const biz_scope_type={
  0:'需求变更',
  1:'数据变更',
  2:'数据查询',
  3:'程序发布'
}

const equipment_type={
  0:'服务器',
  1:'linux服务器',
  2:'windows服务器',
  3:'交换机',
  4:'电源',
  5:'网线',
  6:'内存'
}
const deploy_status={
  0:'已部署',
  1:'未部署',
  2:'已安装',
  3:'未安装',
  4:'已过保',
  5:'未过保',
  6:'报废'
}

const ri_type={
  0:'已部署',
  1:'未部署',
  2:'已安装',
  3:'未安装',
  4:'已过保',
  5:'未过保',
  6:'报废'
}

const tpl_status={
  0:'停用',
  1:'启用'
}

const ri_times={
  '01':'每日一次',
  '11':'每周一次',
  '21':'每月一次',
  '31':'每季一次'
}

const yes_no={
  0:'否',
  1:'是'
}

const state={
	taskType,
	taskState,
	requirementChangeType,
 	x86_main_usage,
  ip_host_type,
  contract_status,
	payment_status,
  contract_type,
	projectApplyAttchmentType,
	procurementMethodtype,
	procurementStatustype,
	procurementAttchmentType,
  equipment_type,
  contractExecAttchmentType,
  biz_scope_type,
  deploy_status,
  ri_type,
  tpl_status,
  ri_times,
  yes_no
}


export default {
	state
}
