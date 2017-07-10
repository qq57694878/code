package com.youi.business.common.zabbix.api;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Request {
	private String jsonrpc = "2.0";

	private Object params;

	private String method;

	private String auth;

	private Integer id;

	public void putParam(String key, Object value) {
		if(params==null){
			params = new JSONObject();
		}
		((JSONObject)params).put(key, value);
	}

	public Object removeParam(String key) {
		if(params==null){
			return null;
		}
		return ((JSONObject)params).remove(key);
	}

	public String getJsonrpc() {
		return jsonrpc;
	}

	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	public Object getParams() {
		return params;
	}

	public void setParams(Object params) {
		this.params = params;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
