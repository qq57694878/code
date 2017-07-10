package com.youi.business.common.zabbix.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.youi.core.spring.ApplicationContextHelper;
import com.youi.core.spring.PropertiesUtils;
import com.youi.core.util.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class ZabbixKit {
	private static final Logger logger = LoggerFactory.getLogger(ZabbixKit.class);

    private static String auth = null;
	private static long createtime=0;
	private final static int timeout=5*1000;
	private static RequestConfig requestConfig ;
	static{
		user= (String)((PropertiesUtils)ApplicationContextHelper.getBean("propertiesUtils")).getPropertiesValue("zabbix.login.user");
		password= (String)((PropertiesUtils)ApplicationContextHelper.getBean("propertiesUtils")).getPropertiesValue("zabbix.login.password");
		url= (String)((PropertiesUtils)ApplicationContextHelper.getBean("propertiesUtils")).getPropertiesValue("zabbix.login.url");
		requestConfig = RequestConfig.custom()
				.setConnectTimeout(timeout).setConnectionRequestTimeout(timeout)
				.setSocketTimeout(timeout).build();
	}




	private final static String user;
	private final static String password;
	private final static String url;

	private static boolean isAuthValid(){
		if(auth!=null&&(System.currentTimeMillis()-createtime)>3600000) return true;
		return false;
	}
	private static void refreshAuth(){
		for(int i=0;i<3;i++){
			Request request = RequestBuilder.newBuilder().paramEntry("user", user)
					.paramEntry("password", password).method("user.login").build();
			JSONObject response = call(request,false);
			String auth = response.getString("result");
			if (!StringUtils.isEmpty(auth)) {
				ZabbixKit.auth = auth;
				break;
			}
		}
	}


	private static String getAuth() {
		if(!isAuthValid()){
			refreshAuth();
		}
         return auth;
	}

	/**
	 * 登录
	 * @param user
	 * @param password
     * @return
     */
	public static String login(String user,String password){
		Request request = RequestBuilder.newBuilder().paramEntry("user", user)
				.paramEntry("password", password).method("user.login").build();
		JSONObject response = call(request,false);
		String auth = response.getString("result");
		if (!StringUtils.isEmpty(auth)) {
	        return auth;
		}
		return null;
	}
	/**
	 * 退出
	 * @param auth
	 * @return
	 */
	public static boolean logout(String auth){
		Request request = RequestBuilder.newBuilder().paramEntry("user", user)
				.paramEntry("password", password).method("user.logout").build();
		request.setAuth(auth);
		JSONObject response = call(request);
		return response.getBoolean("result");
	}

	public static JSONObject call(Request request,boolean isNeedAuth){
		if (StringUtils.isEmpty(request.getAuth())&&isNeedAuth) {
			request.setAuth(getAuth());
		}
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response=null;
		JSONObject result =null;
		try {
			httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
			HttpUriRequest httpRequest = org.apache.http.client.methods.RequestBuilder.post().setUri(url)
					.addHeader("Content-Type", "application/json")
					.setEntity(new StringEntity(JSON.toJSONString(request), ContentType.APPLICATION_JSON)).build();
			String entityJson =JSON.toJSONString(request);
			logger.info("请求参数:"+entityJson);
			response= httpClient.execute(httpRequest);
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entity = response.getEntity();
				byte[] data = EntityUtils.toByteArray(entity);
				result = (JSONObject) JSON.parse(data);
				logger.info("返回结果:"+result.toJSONString());
			}else{
				logger.info("失败，返回状态码:"+response.getStatusLine().getStatusCode());
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("ZabbixApi call exception!", e);
		}finally {
			try {
				if(httpClient!=null){
					httpClient.close();
				}
				if(response!=null){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*public static JSONObject call(Request request,boolean isNeedAuth){
		if (StringUtils.isEmpty(request.getAuth())&&isNeedAuth) {
			request.setAuth(getAuth());
		}
		CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpClients.createDefault();
			String entityJson =JSON.toJSONString(request);
			logger.info("请求参数:"+entityJson);
			HttpUriRequest httpRequest = org.apache.http.client.methods.RequestBuilder
					.post().setUri(url)
					.addHeader("Content-Type", "application/json")
					.setEntity(new StringEntity(entityJson))
					.build();
			HttpResponse response = httpClient.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			byte[] data = EntityUtils.toByteArray(entity);
			JSONObject result = (JSONObject) JSON.parse(data);
			logger.info("返回结果:"+result.toJSONString());
			return result;
		} catch (IOException e) {
			throw new RuntimeException("ZabbixApi call exception!", e);
		}finally {
			try {
				if(httpClient!=null){
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/

	public static JSONObject call(Request request) {
		return call(request,true);
	}

}
