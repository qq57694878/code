/**
 * Created by lishushuai on 2016/12/26.
 */

export function getContractExecPlanList(store){
  return store.ContractExecPlan.execPlanList
}

export function getContractExecPlanDetail(store) {
  return store.ContractExecPlan.contractDetail
}

export function getBusinessList(store) {
  return store.ContractExecPlan.businessList
}

export function getHw(store) {
  return store.ContractExecPlan.execDetail.hw
}

export function getHwman(store) {
  return store.ContractExecPlan.execDetail.hwman
}

export function getHwWarranty(store) {
  return store.ContractExecPlan.execDetail.hw_warranty
}

export function getSoftman(store){
  return store.ContractExecPlan.execDetail.softman
}

export function getSoft(store) {
  return store.ContractExecPlan.execDetail.soft
}

export  function getReadonly(store) {
  return store.ContractExecPlan.bEditAble
}

export function getSoftBizScope(store) {
  return store.ContractExecPlan.softBizScope
}

export function getSoftmanBizScope(store) {
  return store.ContractExecPlan.softmanBizScope
}

export function getDetailContractId(store){
  return store.ContractExecPlan.detailContractId
}

export function getDetailRouter(store){
  return store.ContractExecPlan.router
}
