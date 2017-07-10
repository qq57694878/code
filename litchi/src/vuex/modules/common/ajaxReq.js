
import Vue from 'vue'
import * as types from '../../mutation-types'

import {getJWTtoken,setJWTtoken} from '../authentication/LoginActions'

export const ajaxReq=function({dispatch},req){
	let url=req.url?req.url:'';
	let body=req.body?req.body:{};
	let options=req.options?req.options:{};
	let success=req.success?req.success:null;
	let failure=req.failure?req.failure:null;

	//zabbix 兼容
	let token=getJWTtoken();
	options.headers=options.headers?options.headers:{};
	options.headers.authentication=token;

	Vue.http.post(url,body,options).then((response) => {
		// if(response.status==200){
			//TODO delete
      console.debug("http request => "+url);

			let responseJson=response.json();
			if(responseJson.error_code==200){
				setJWTtoken(responseJson.token);
				if(success)success(response);
			}else if(responseJson.error_code==401){
				if(failure)failure(response);
				dispatch(types.SET_AUTHENTICATION,1)
			}else{
				if(failure)failure(response);
			}
		// }
	}, (response) => {
		if(failure)failure(response);
    //TODO delete
		console.error("http request error=> "+url);
	});

}

