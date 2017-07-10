
export function getContractList(store){
	  return store.ContractRegister.contractslist
}

export function getOneContractDetail(store){
	  return store.ContractRegister.onecontract_detail
}
export function getContractQuery(store){
	  return store.ContractRegister.contractquery
}

export function getOneContractDetailEnterGuard(store){
	  return store.ContractRegister.registerdetailenterguard
}

export function getOneContractDetailPayDetailEnterGuard(store){
	  return store.ContractRegister.registerdetailpaydetailenterguard
}

export function getContractAcceptanceDetailEnterGuard(store){
	  return store.ContractRegister.acceptancedetailenterguard
}

export function getContractPaymentDetailEnterGuard(store){
	  return store.ContractRegister.paymentdetailenterguard
}
