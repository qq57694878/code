import Vue from 'vue'

import * as types from '../../mutation-types'

export const login = ({dispatch},component,username,passwd,remember) => {
	//TODO ajax request ...
	var requestBody={ username:username,passwd:passwd};
	Vue.http.post('login.do',requestBody).then((response) => {
		if(response.status==200){
			var responseJson=response.json();
			if(responseJson.error_code==200){
				console.debug(response.json());
				dispatch(types.SET_AUTHENTICATION,2)
				dispatch(types.SET_LOGIN_ERROR,false)
				setJWTtoken(responseJson.token);

				if(remember){
					window.localStorage.remember=true;
					window.localStorage.username=username;
				}

				component.$router.go({name:'dashboard'});

				return;
			}
		}

		dispatch(types.SET_AUTHENTICATION,1)
		dispatch(types.SET_LOGIN_ERROR,true)
	}, (response) => {
		// error callback
		dispatch(types.SET_AUTHENTICATION,1)
		dispatch(types.SET_LOGIN_ERROR,true)
	});
}

export const logout = ({dispatch}) => {
	//TODO ajax request ...
	//if success
	dispatch(types.SET_AUTHENTICATION,1)
	setJWTtoken('');
}

export const clearLoginError= ({dispatch}) => {
	dispatch(types.SET_LOGIN_ERROR,false)
}

export const setJWTtoken=function(token){
	// console.debug("set token: "+token);
	window.localStorage.jwt_token=token;

	// authentication header zabbix api 不兼容
	// Vue.http.headers.common['authentication'] = window.localStorage.jwt_token;
}

export const getJWTtoken=function(){
	return window.localStorage.jwt_token;
	// return 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0NzI2MTIwNTgsImlzcyI6Indkc2giLCJ1c2VyaWQiOjEsImlhdCI6MTQ3MjYxMTE1OH0.2E4SvIF059OuzaMwEffT-t-af-tiF08C9mPjYn7Ev9g';
}

export const verify=function({dispatch},component){

	let token=getJWTtoken();
	//zabbix 兼容
	let options={headers:{"authentication":token}};
	Vue.http.post('verify.do',{},options).then((response) => {
		if(response.status==200){
			var responseJson=response.json();
			if(responseJson.error_code==200){
				dispatch(types.SET_AUTHENTICATION,2)
				setJWTtoken(responseJson.token);
				component.$router.go({name:'dashboard'});
				return;
			}
		}

		dispatch(types.SET_AUTHENTICATION,1)
	}, (response) => {
		// error callback
		dispatch(types.SET_AUTHENTICATION,1)
	});
}

