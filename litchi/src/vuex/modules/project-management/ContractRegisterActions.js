import Vue from 'vue'
import * as types from '../../mutation-types'
import {ajaxReq} from '../common/ajaxReq'

export const reqContractList = (store,comp,requestBody) => {

  ajaxReq(store,{
    url:'contract/query.do',
    body:requestBody,
    success:function(response){
      let responseJson=response.json();
      store.dispatch(types.SET_CONTRACT_LIST,responseJson.data)
    },
    failure:function(){
      comp.$set('alertType','alert-danger');
      comp.$set('alertShow',true);
      comp.$set('alertContent','查询失败！');
    }
  });
}

export const reqContractDetail = (store, comp, id,success,failure)=>{
  let requestBody={ id:id};

  ajaxReq(store,{
    url:'contract/detail.do',
    body:requestBody,
    success:function(response){
      let responseJson=response.json();
      console.debug(responseJson);
      store.dispatch(types.SET_CONTRACT_REGISTER_DETAIL,responseJson.data);
      if(success)success();
    },
    failure:function(){
      comp.$set('alertType','alert-danger');
      comp.$set('alertShow',true);
      comp.$set('alertContent','查询失败！');
    }
  });
}

export const saveContractDetail = (store,comp,requestBody,success,failure) => {

  ajaxReq(store,{
    url:'contract/save.do',
    body:requestBody,
    success:function(response){
      if(success)success();
		// comp.$router.go({name:'contract_register'});
    },
    failure:function(){
      if(failure)failure();
        // comp.$set('alertType','alert-danger');
        // comp.$set('alertShow',true);
        // comp.$set('alertContent','查询失败！');
    }
  });
}

export const clearContractQuery = (store) => {
	let cleardata={ contractNum:'',contractName:'',vendor:''};

	store.dispatch(types.SET_CONTRACT_QUERY,cleardata)
}
export const clearOneContractDetail = (store) => {
	let cleardata={ 
		id: '',
		pid: '', //采购项目id
		procurement_name: '',// 采购项目名称
		cno: '', //合同编号
		name: '',
		vender_id: '',
		vender_name: '',
		amount: '',
		sdate: '',
		edate: '',
		applicant_id: '',
		applicant_name: '',
		apply_date: '',
		responsible_person_id: '', //负责人
		responsible_person_name: '', //负责人
		estimated_reception_time: '', //预计验收时间
		contract_file: '', //合同影印文件id
		status: '1',// 合同状态,
		ctype: ['0', '2'], //合同类型
		payment:[
		{
			terms:'',
			estimated_amount:'',
			amount:'',
			estimated_date:'',
			paydate:'',
			invoice_no:'',
			invoice_file:
			{
				file_id:'',
				file_name:'',
				file_type:'',
				url:'',
				creator_id:'',
				creator_name:'',
				cdate:'',
				},
			check_no:'',
			check_file:
			{
				file_id:'',
				file_name:'',
				file_type:'',
				url:'',
				creator_id:'',
				creator_name:'',
				cdate:'',
			},
			status:'0',
		}
		],
		reception_time:'',
		expert:'',
		expert_opinion_file:
		{
				file_id:'',
				file_name:'',
				file_type:'',
				url:'',
				creator_id:'',
				creator_name:'',
				cdate:'',
		},
		verdict_file:
		{
				file_id:'',
				file_name:'',
				file_type:'',
				url:'',
				creator_id:'',
				creator_name:'',
				cdate:'',
		}
	};
	store.dispatch(types.SET_CONTRACT_REGISTER_DETAIL,cleardata)
}



export const setEnterContractRegisterDetailGuard = (store,guard) => {

	store.dispatch(types.SET_CONTRACT_REGISTER_DETAIL_ENTERGUARD,guard);
}

export const deleteContractRegisterDetailPayment = (store,data) => {

	store.dispatch(types.DELETE_CONTRACT_REGISTER_DETAIL_PAYMENT,data);
}

export const setEnterContractRegisterDetailPayDetailGuard = (store,guard) => {

	store.dispatch(types.SET_CONTRACT_REGISTER_DETAIL_PAYDETAIL_ENTERGUARD,guard);
}

export const addContractRegisterDetailPayment = (store,paydata) => {

	store.dispatch(types.ADD_CONTRACT_REGISTER_DETAIL_PAYMENT,paydata);
}

export const setContractRegisterDetailPid = (store,id) => {

	store.dispatch(types.SET_CONTRACT_REGISTER_DETAIL_PID,id);
}

export const setContractRegisterDetailVendorid = (store,id) => {

	store.dispatch(types.SET_CONTRACT_REGISTER_DETAIL_VENDORID,id);
}

export const setContractRegisterDetailContractfileid = (store,id) => {

	store.dispatch(types.SET_CONTRACT_REGISTER_DETAIL_CONTRACTFILEID,id);
}

export const setEnterContractAcceptanceDetailGuard = (store,guard) => {

	store.dispatch(types.SET_CONTRACT_ACCEPTANCE_DETAIL_ENTERGUARD,guard);
}

export const setEnterContractPaymentDetailGuard = (store,guard) => {

	store.dispatch(types.SET_CONTRACT_PAYMENT_DETAIL_ENTERGUARD,guard);
}
