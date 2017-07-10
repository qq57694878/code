
import Vue from 'vue'


var gen_id=function(){
	return 1;
}

var zabbix={
 	url:"http://www.tongpinlife.com:10049/zabbix/api_jsonrpc.php",
	auth:"",
	interfaces:{
		type:1, //1 agent 2 SNMP 3 IPMI 4 JMX
		port:"10050"
	}
};

var zabbixAjax=function(url,body,options,success,failure){
	Vue.http.post(zabbix.url,body,options).then((response) => {
		// if(response.status==200){
			console.debug("http request => "+url);
			let responseJson=response.json();
			if(!responseJson.error){

				console.debug(responseJson);

				if(success)success(responseJson);
			}else{
				console.debug(responseJson.error);
				if(failure)failure(responseJson);
			}
		// }
	}, (response) => {
		if(failure)failure(response);
		console.error("http request error=> "+url);
	});
}

export const zabbixLogin=function(user,passwd,success,failure){

	let body={
		jsonrpc:"2.0",
		method:"user.login",
		params:{
			user:user,
			password:passwd
		},
		id:gen_id()
	};
	let options={headers:{"Content-Type":"application/json"}};
	zabbixAjax(zabbix.url,body,options,function(responseJson){
		zabbix.auth=responseJson.result;
		if(success)success(responseJson);
	},failure);
}

export const zabbixLogout=function(){
	let body={
		jsonrpc:"2.0",
		method:"user.logout",
		params:[],
		id:gen_id(),
		auth:zabbix.auth
	};
	let options={headers:{"Content-Type":"application/json"}};
	zabbixAjax(zabbix_url,body,options);
}

export const zabbixHostCreate=function(ip,success,failure){
	let body={
		jsonrpc:"2.0",
		method:"host.create",
		params:{
			host:ip,
			interfaces:{
				type:zabbix.interfaces.type,
				main:1,
				useip:1,
				ip:ip,
				dns:"",
				port:"10050"
			},
			groups:[
				{groupid:"4"}
			],
			templates:[
				{templateid:"10001" }
			]
		},
		id:gen_id(),
		auth:zabbix.auth
	};
	let options={headers:{"Content-Type":"application/json"}};
	zabbixAjax(zabbix.url,body,options,success,failure);
}

export const zabbixHostsGet=function(hostids,success,failure){
	let body={
		jsonrpc:"2.0",
		method:"host.get",
		params:{
			hostids:hostids,
			output:['hostid','host','available','snmp_available'],
			selectInterfaces:['type','ip','port'],
			selectItems:['itemid','key_','name','lastclock','lastvalue'],
		},
		id:gen_id(),
		auth:zabbix.auth
	};
	let options={headers:{"Content-Type":"application/json"}};
	zabbixAjax(zabbix.url,body,options,success,failure);
}

export const zabbixTrendGet=function(itemids,timeFrom,timeTill,success,failure){
	let body={
		jsonrpc:"2.0",
		method:"trend.get",
		params:{
			itemids:itemids,
			time_from:timeFrom,
			time_till:timeFrom
		},
		id:gen_id(),
		auth:zabbix.auth
	};
	let options={headers:{"Content-Type":"application/json"}};
	zabbixAjax(zabbix.url,body,options,success,failure);
}

export const zabbixEventGet=function(timeFrom,timeTill,success,failure){
	let body={
		jsonrpc:"2.0",
		method:"event.get",
		params:{
			output:['eventid','objectid','clock','value'],
			selectHosts:['hostid','host'],
			source:0,
			object:0,
			time_from:timeFrom,
			time_till:timeFrom
		},
		id:gen_id(),
		auth:zabbix.auth
	};
	let options={headers:{"Content-Type":"application/json"}};
	zabbixAjax(zabbix.url,body,options,success,failure);
}
