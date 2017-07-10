package com.youi.business.common.zabbix.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youi.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class ZabbixApi {
	private static final  ZabbixApi LOCK = new ZabbixApi();
	private static final String group_biz_name ="group_lichi_biz";
	private static final String group_host_name ="group_lichi_host";
	private static final String group_db_name ="group_lichi_db";
/*	private static final String template_biz_name ="template_lichi_biz";
	private static final String template_host_name ="template_lichi_host";
	private static final String template_db_name ="template_lichi_db";*/

	public  final static int  interface_type=1; //1 agent 2 SNMP 3 IPMI 4 JMX
	public final  static int  interface_port=10050;

	private static final Logger logger = LoggerFactory.getLogger(ZabbixApi.class);
	private static String  group_biz_id;
	private static String  group_host_id;
	private static String  group_db_id;
	private static String[]  template_biz_ids;
	private static String[]  template_host_ids;
	private static String[]  template_db_ids;
	//业务系统主机命名规则 "biz_"+"{ip}_"+"端口_"+"{业务系统id}"
	public final static String PRE_HOST_BIZ="biz";
	//服务器主机命名规则 "host_"+"{ip}_"+"{业务系统id}"
	public final static String PRE_HOST_HOST="host";
	//数据库主机命名规则 "db"+"{ip}_"+"{业务系统id}"
	public final static String PRE_HOST_DB="db";
	private final static Map<String,String[]> templates = new HashMap<String,String[]>();
	public final static String TEMPLATE_TYPE_BIZ_LINUX = "TEMPLATE_TYPE_BIZ_LINUX";
	public final static String TEMPLATE_TYPE_BIZ_WINDOWS = "TEMPLATE_TYPE_BIZ_WINDOWS";
	public final static String TEMPLATE_TYPE_HOST_LINUX = "TEMPLATE_TYPE_HOST_LINUX";
	public final static String TEMPLATE_TYPE_HOST_WINDOWS = "TEMPLATE_TYPE_HOST_WINDOWS";
	public final static String TEMPLATE_TYPE_DB_MYSQL = "TEMPLATE_TYPE_DB_MYSQL";
	public final static String TEMPLATE_TYPE_DB_ORACLE = "TEMPLATE_TYPE_DB_ORACLE";
	public final static String TEMPLATE_TYPE_DB_MSSQL = "TEMPLATE_TYPE_DB_MSSQL";
	static{
		templates.put(TEMPLATE_TYPE_BIZ_LINUX,new String[]{"template_lichi_biz"});
		templates.put(TEMPLATE_TYPE_BIZ_WINDOWS,new String[]{"template_lichi_biz"});
		templates.put(TEMPLATE_TYPE_HOST_LINUX,new String[]{"template_lichi_host"});
		templates.put(TEMPLATE_TYPE_HOST_WINDOWS,new String[]{"template_lichi_host"});
		templates.put(TEMPLATE_TYPE_DB_MYSQL,new String[]{"template_lichi_db"});
		templates.put(TEMPLATE_TYPE_DB_ORACLE,new String[]{"template_lichi_db"});
		templates.put(TEMPLATE_TYPE_DB_MSSQL,new String[]{"template_lichi_db"});
	}




	public static String getGroup_biz_id() {
		synchronized (LOCK){
			if(StringUtils.isEmpty(group_biz_id)){
				group_biz_id = ZabbixApi.getHostgroupid(group_biz_name);
			}
		}
		return group_biz_id;
	}

	public static String getGroup_host_id() {
		synchronized (LOCK){
			if(StringUtils.isEmpty(group_host_id)){
				group_host_id = ZabbixApi.getHostgroupid(group_host_name);
			}
		}
		return group_host_id;
	}

	public static String getGroup_db_id() {
		synchronized (LOCK){
			if(StringUtils.isEmpty(group_db_id)){
				group_db_id = ZabbixApi.getHostgroupid(group_db_name);
			}
		}
		return group_db_id;
	}

	private static String[] getTemplateNames(String template_type){
		return templates.get(template_type);
	}

	public static String[] getTemplate_biz_ids(String template_type) {
		synchronized (LOCK){
			if(template_biz_ids==null){
				template_biz_ids = ZabbixApi.getTemplateid(getTemplateNames(template_type));
			}
		}
		return template_biz_ids;
	}

	public static String[] getTemplate_host_ids(String template_type) {
		synchronized (LOCK){
			if(template_host_ids==null){
				template_host_ids = ZabbixApi.getTemplateid(getTemplateNames(template_type));
			}
		}
		return template_host_ids;
	}

	public static String[] getTemplate_db_ids(String template_type) {
		synchronized (LOCK){
			if(template_db_ids==null){
				template_db_ids = ZabbixApi.getTemplateid(getTemplateNames(template_type));
			}
		}
		return template_db_ids;
	}

	/**
	 * 判断zabbix环境是否应配置了所需要的魔板和group组数据
	 * @return
     */
	public boolean isZabbixServerPrepare(){
		boolean f = !(StringUtils.isEmpty(group_biz_id)
				||StringUtils.isEmpty(group_host_id)
				||StringUtils.isEmpty(group_db_id)
				||template_biz_ids==null
				||template_host_ids==null
				||template_db_ids==null);
		return f;
	}

	/**
	 * 获取groupid
	 * @param groupname
	 * @return
     */
	public static String getHostgroupid(String groupname) {
		JSONObject filter = new JSONObject();
		filter.put("name",groupname);
		Request request = RequestBuilder.newBuilder().method("hostgroup.get")
				.paramEntry("filter", filter).paramEntry("output","groupid").build();
		JSONObject response = ZabbixKit.call(request);
		JSONArray groups = response.getJSONArray("result");
		if(groups.size()>0){
			return groups.getJSONObject(0).getString("groupid");
		}
		return null;
	}
	/**
	 * 获取templateid
	 * @param templatename
	 * @return
	 */
	public static String[] getTemplateid(String[] templatename) {
		JSONObject filter = new JSONObject();
		filter.put("host",templatename);
		Request request = RequestBuilder.newBuilder().method("template.get")
				.paramEntry("filter", filter).paramEntry("output","templateid").build();
		JSONObject response = ZabbixKit.call(request);
		JSONArray groups = response.getJSONArray("result");
		if(groups.size()>0){
			String[] result = new String[groups.size()];
			for(int i=0;i<groups.size();i++){
				result[i] = groups.getJSONObject(i).getString("templateid");
			}
			return result;
		}
		return null;
	}

	public static String getMacros(){
		JSONObject params = new JSONObject();
		params.put("globalmacro","1");

		Request getRequest = RequestBuilder.newBuilder()
				.method("usermacro.get")
				.build();
		getRequest.setParams(params);
		JSONObject r = ZabbixKit.call(getRequest);
		System.out.println(r);
		return r.toJSONString();
	}


	public static String getEvents(){
		long currentUnix = System.currentTimeMillis()/1000;
		JSONObject params = new JSONObject();
		params.put("output",new String[]{"eventid","objectid","clock","value"});
		params.put("selectHosts",new String[]{"hostid","host"});//
		//params.put("selectRelatedObject",new String[]{"triggerid","description","comments","priority","value","expandDescription"});//
		params.put("source",0);
		params.put("object",0);
		long time_from =0;

		long time_till = currentUnix;
		params.put("time_from",time_from);
		params.put("time_till",time_till);
		Request getRequest = RequestBuilder.newBuilder()
				.method("event.get")
				.build();
		getRequest.setParams(params);
		JSONObject r = ZabbixKit.call(getRequest);
		System.out.println(r);
		return r.toJSONString();
	}
	/**
	 * 删除主机
	 * @param hostid
	 * @return
	 */
	public static JSONObject deleteZabbixHost(Long hostid) {
		Request getRequest = RequestBuilder.newBuilder()
				.method("host.delete")
				.build();
		JSONArray param = new JSONArray();
		param.add(hostid);
		getRequest.setParams(param);
		return ZabbixKit.call(getRequest);
	}


	/**
	 * 创建主机
	 * @param biz_app_id
	 * @param ip
	 * @param port
	 * @return
	 */
	public static JSONObject createBizZabbixHost(String biz_app_id,String ip,String port,String[] templateids){
		JSONObject params = new JSONObject();
		String host =  ZabbixApi.PRE_HOST_BIZ+"_"+ip+"_"+port+"_"+biz_app_id;
		params.put("host", host);
		JSONArray interfaces = new JSONArray();
		JSONObject interface1 = new JSONObject();
		interface1.put("type",ZabbixApi.interface_type);
		interface1.put("main", 1);
		interface1.put("useip",1);
		interface1.put("ip",ip);
		interface1.put("dns","");
		interface1.put("port",ZabbixApi.interface_port);
		interfaces.add(interface1);
		params.put("interfaces",interfaces);
		JSONObject group = new JSONObject();
		group.put("groupid",ZabbixApi.getGroup_biz_id());
		params.put("groups",group);
		JSONArray templates = new JSONArray();
		for(int i=0;i<templateids.length;i++){
			JSONObject template = new JSONObject();
			template.put("templateid",templateids[i]);
			templates.add(template);
		}
		params.put("templates",templates);
		JSONObject macro = new JSONObject();
		macro.put("macro","{$PORT}");
		macro.put("value",port);
		params.put("macros",macro);
		Request getRequest = RequestBuilder.newBuilder()
				.method("host.create")
				.build();
		getRequest.setParams(params);
		return ZabbixKit.call(getRequest);
	}

	/**
	 * 创建服务器主机
	 * @param biz_app_id
	 * @param ip
	 * @return
	 */
	public static JSONObject createHostZabbixHost(String biz_app_id,String ip,String[] templateids){
		JSONObject params = new JSONObject();
		String host =  ZabbixApi.PRE_HOST_HOST+"_"+ip+"_"+biz_app_id;
		params.put("host", host);
		JSONArray interfaces = new JSONArray();
		JSONObject interface1 = new JSONObject();
		interface1.put("type",ZabbixApi.interface_type);
		interface1.put("main", 1);
		interface1.put("useip",1);
		interface1.put("ip",ip);
		interface1.put("dns","");
		interface1.put("port",ZabbixApi.interface_port);
		interfaces.add(interface1);
		params.put("interfaces",interfaces);
		JSONObject group = new JSONObject();
		group.put("groupid",ZabbixApi.getGroup_host_id());
		params.put("groups",group);
		JSONArray templates = new JSONArray();
		for(int i=0;i<templateids.length;i++){
			JSONObject template = new JSONObject();
			template.put("templateid",templateids[i]);
			templates.add(template);
		}
		params.put("templates",templates);
		Request getRequest = RequestBuilder.newBuilder()
				.method("host.create")
				.build();
		getRequest.setParams(params);
		return ZabbixKit.call(getRequest);
	}
	/**
	 * 创建服务器主机
	 * @param biz_app_id
	 * @param ip
	 * @return
	 */
	public static JSONObject createDbZabbixHost(String biz_app_id,String ip,String db_port,String[] templateids){
		JSONObject params = new JSONObject();
		String host =  ZabbixApi.PRE_HOST_DB+"_"+ip+"_"+biz_app_id;
		params.put("host", host);
		JSONArray interfaces = new JSONArray();
		JSONObject interface1 = new JSONObject();
		interface1.put("type",ZabbixApi.interface_type);
		interface1.put("main", 1);
		interface1.put("useip",1);
		interface1.put("ip",ip);
		interface1.put("dns","");
		interface1.put("port",ZabbixApi.interface_port);
		interfaces.add(interface1);
		params.put("interfaces",interfaces);
		JSONObject group = new JSONObject();
		group.put("groupid",ZabbixApi.getGroup_db_id());
		params.put("groups",group);
		JSONArray templates = new JSONArray();
		for(int i=0;i<templateids.length;i++){
			JSONObject template = new JSONObject();
			template.put("templateid",templateids[i]);
			templates.add(template);
		}
		params.put("templates",templates);
		JSONObject macro = new JSONObject();
		macro.put("macro","{$PORT}");
		macro.put("value",db_port);
		params.put("macros",macro);
		Request getRequest = RequestBuilder.newBuilder()
				.method("host.create")
				.build();
		getRequest.setParams(params);
		return ZabbixKit.call(getRequest);
	}


	public static JSONObject getHostTriggersByGroupid(String groupid){
		JSONObject params = new JSONObject();
		params.put("groupids",groupid );
		params.put("output",new String[]{"hostid","host","available"});
		params.put("selectTriggers",new String[]{"value","triggerid","status"});
		Request getRequest = RequestBuilder.newBuilder().method("host.get").build();
		getRequest.setParams(params);
		return ZabbixKit.call(getRequest);
	}
	public static JSONObject getHostTriggersByHostid(String hostid){
		JSONObject params = new JSONObject();
		params.put("hostids",hostid );
		params.put("output",new String[]{"hostid","host","available"});
		params.put("selectTriggers",new String[]{"value","triggerid","status"});
		Request getRequest = RequestBuilder.newBuilder().method("host.get").build();
		getRequest.setParams(params);
		return ZabbixKit.call(getRequest);
	}
	public static JSONObject getHostItemsByHostid(String hostid){
		JSONObject params = new JSONObject();
		params.put("hostids",hostid );
		params.put("output",new String[]{"hostid","name","key_","lastvalue","lastclock","itemid"});
		JSONObject filter = new JSONObject();
		filter.put("status","0");
		params.put("filter",filter);
		Request getRequest = RequestBuilder.newBuilder().method("item.get").build();
		getRequest.setParams(params);
		return ZabbixKit.call(getRequest);
	}
	public static JSONObject getHostItemsByGroupid(String groupid){
		JSONObject params = new JSONObject();
		params.put("groupids",groupid );
		params.put("output",new String[]{"hostid","name","key_","lastvalue","lastclock","itemid"});
		JSONObject filter = new JSONObject();
		filter.put("status","0");
		params.put("filter",filter);
		Request getRequest = RequestBuilder.newBuilder().method("item.get").build();
		getRequest.setParams(params);
		return ZabbixKit.call(getRequest);
	}
	public static Map<String,Map<String,String>> getTriggerInfo(Object[] triggerids){
		Map<String,Map<String,String>> triggers = new HashMap<String,Map<String,String>>();
		if(triggerids.length==0){return triggers;}
		JSONObject params = new JSONObject();
		params.put("triggerids",triggerids);
		params.put("output",new String[]{"value","triggerid","description","lastchange","comments"});
		params.put("expandDescription","1");
		params.put("expandComment","1");
		JSONObject filter = new JSONObject();
		filter.put("status","0");
		params.put("filter",filter);
		Request req2 = RequestBuilder.newBuilder()
				.method("trigger.get")
				.build();
		req2.setParams(params);
		JSONObject r = ZabbixKit.call(req2);
		JSONArray triggerArray = r.getJSONArray("result");

		for(int i=0;i<triggerArray.size();i++){
			JSONObject trigger = triggerArray.getJSONObject(i);
			Map<String,String> triggerInfo = new HashMap<String,String>();
			String triggerid =trigger.getString("triggerid");
			triggerInfo.put("triggerid",triggerid);
			triggerInfo.put("description",trigger.getString("description"));
			triggerInfo.put("value",trigger.getString("value"));
			triggerInfo.put("lastchange",trigger.getString("lastchange"));
			triggerInfo.put("comments",trigger.getString("comments"));
			triggers.put(triggerid,triggerInfo);
		}
		return triggers;
	}



/*	public String login(String user, String password) {
		Request request = RequestBuilder.newBuilder().paramEntry("user", user)
				.paramEntry("password", password).method("user.login").build();
		JSONObject response = ZabbixKit.call(request);
		String auth = response.getString("result");
	    return auth;
	}

	public String apiVersion() {
		Request request = RequestBuilder.newBuilder().method("apiinfo.version")
				.build();
		JSONObject response = ZabbixKit.call(request);
		return response.getString("result");
	}

	public boolean hostExists(String name) {
		Request request = RequestBuilder.newBuilder().method("host.exists")
				.paramEntry("name", name).build();
		JSONObject response = ZabbixKit.call(request);
		return response.getBooleanValue("result");
	}

	public String hostCreate(String host, String groupId) {
		JSONArray groups = new JSONArray();
		JSONObject group = new JSONObject();
		group.put("groupid", groupId);
		groups.add(group);
		Request request = RequestBuilder.newBuilder().method("host.create")
				.paramEntry("host", host).paramEntry("groups", groups).build();
		JSONObject response = ZabbixKit.call(request);
		return response.getJSONObject("result").getJSONArray("hostids")
				.getString(0);
	}

	public boolean hostgroupExists(String name) {
		Request request = RequestBuilder.newBuilder()
				.method("hostgroup.exists").paramEntry("name", name).build();
		JSONObject response = ZabbixKit.call(request);
		return response.getBooleanValue("result");
	}

	*//**
	 *
	 * @param name
	 * @return groupId
	 *//*
	public String hostgroupCreate(String name) {
		Request request = RequestBuilder.newBuilder()
				.method("hostgroup.create").paramEntry("name", name).build();
		JSONObject response = ZabbixKit.call(request);
		return response.getJSONObject("result").getJSONArray("groupids")
				.getString(0);
	}*/

}
