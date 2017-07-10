package com.youi.business.common.zabbix.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youi.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * zabbix为了新的需求增加的类
 */
public class ZabbixV2Api {

	private static final String group_null_id = "9999999999";
	private static final Logger logger = LoggerFactory.getLogger(ZabbixV2Api.class);
	private static final ZabbixV2Api LOCK = new ZabbixV2Api();

	private static final String group_vcenter_xm_name = "Virtual machines";
	private static final String group_vm_name = "group_lichi_vm";//"Hypervisors";//group_lichi_vm
	private static final String group_vcenter_x86_name = "Hypervisors";//"Hypervisors";//group_lichi_vm
	private static final String group_host_name ="group_lichi_host";
	private static final String group_switch_name ="group_lichi_switch";
	private static final String group_env_name ="group_lichi_env";
	private static final String group_water_name ="group_lichi_water";
	private static String  group_vcenter_x86_id = null;
	private static String  group_vcenter_vm_id = null;
	private static String  group_vm_id = null;
	private static String  group_host_id = null;
	private static String  group_switch_id = null;
	private static String  group_env_id = null;
	private static String  group_water_id = null;

	public static String getGroup_vcenter_x86_id() {
		if(group_vcenter_x86_id==null){
			synchronized (LOCK){
				if(group_vcenter_x86_id==null){
					group_vcenter_x86_id = ZabbixV2Api.getHostgroupid(group_vcenter_x86_name);
				}
			}
		}
		return group_vcenter_x86_id;
	}

	public static String getGroup_vcenter_vm_id() {
		if(group_vcenter_vm_id==null){
			synchronized (LOCK){
				if(group_vcenter_vm_id==null){
					group_vcenter_vm_id = ZabbixV2Api.getHostgroupid(group_vcenter_xm_name);
				}
			}
		}
		return group_vcenter_vm_id;
	}
	public static String getGroup_vm_id() {
		if(group_vm_id==null){
			synchronized (LOCK){
				if(group_vm_id==null){
					group_vm_id = ZabbixV2Api.getHostgroupid(group_vm_name);
				}
			}
		}
		return group_vm_id;
	}
	public static String getGroup_switch_id() {
		if(group_switch_id==null){
			synchronized (LOCK){
				if(group_switch_id==null){
					group_switch_id = ZabbixV2Api.getHostgroupid(group_switch_name);
				}
			}
		}
		return group_switch_id;
	}
	public static String getGroup_host_id() {
		if(group_host_id==null){
			synchronized (LOCK){
				if(group_host_id==null){
					group_host_id = ZabbixV2Api.getHostgroupid(group_host_name);
				}
			}
		}
		return group_host_id;
	}
	public static String getGroup_env_id() {
		if(group_env_id==null){
			synchronized (LOCK){
				if(group_env_id==null){
					group_env_id = ZabbixV2Api.getHostgroupid(group_env_name);
				}
			}
		}
		return group_env_id;
	}
	public static String getGroup_water_id() {
		if(group_water_id==null){
			synchronized (LOCK){
				if(group_water_id==null){
					group_water_id = ZabbixV2Api.getHostgroupid(group_water_name);
				}
			}
		}
		return group_water_id;
	}


	/**
	 * 判断zabbix环境是否应配置了所需要的魔板和group组数据
	 * @return
     */
	public boolean isZabbixServerPrepare(){
		boolean f = !(StringUtils.isEmpty(group_vm_id)
				||StringUtils.isEmpty(group_host_id)
				||StringUtils.isEmpty(group_switch_id)
				||StringUtils.isEmpty(group_env_id)
				);
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
	public static JSONObject getVcenterHostAndItemsByGroupid(String groupid){
		JSONObject params = new JSONObject();
		if(StringUtils.isEmpty(groupid)){groupid=group_null_id;};
		params.put("groupids",groupid );
		params.put("output",new String[]{"hostid","host","status","description","name"});
		params.put("selectItems",new String[]{"hostid","name","key_","lastvalue","lastclock","itemid","status"});
		Request getRequest = RequestBuilder.newBuilder().method("host.get").build();
		getRequest.setParams(params);
		return ZabbixKit.call(getRequest);
	}


	public static JSONObject getHostAndItemsByGroupid(String groupid){
		JSONObject params = new JSONObject();
		if(StringUtils.isEmpty(groupid)){groupid=group_null_id;};
		params.put("groupids",groupid );
		params.put("output",new String[]{"hostid","host","status","description","name"});
		params.put("selectItems",new String[]{"hostid","name","key_","lastvalue","lastclock","itemid","status"});
		Request getRequest = RequestBuilder.newBuilder().method("host.get").build();
		getRequest.setParams(params);
		return ZabbixKit.call(getRequest);
	}
	public static JSONObject getVmAndItemsByHostids(String groupid,List<Object> hostids){
		JSONObject params = new JSONObject();
		if(StringUtils.isEmpty(groupid)){groupid=group_null_id;};
		params.put("groupids",groupid );
		params.put("hostids",hostids);
		params.put("output",new String[]{"hostid","host","status","description","name"});
		params.put("selectItems",new String[]{"hostid","name","key_","lastvalue","lastclock","itemid","status"});
		Request getRequest = RequestBuilder.newBuilder().method("host.get").build();
		getRequest.setParams(params);
		return ZabbixKit.call(getRequest);
	}

	public static JSONObject getVmAndItemsByGroupid(String groupid){
		JSONObject params = new JSONObject();
		if(StringUtils.isEmpty(groupid)){groupid=group_null_id;};
		params.put("groupids",groupid );
		params.put("output",new String[]{"hostid","host","status","description","name"});
		params.put("selectItems",new String[]{"hostid","name","key_","lastvalue","lastclock","itemid","status"});
		Request getRequest = RequestBuilder.newBuilder().method("host.get").build();
		getRequest.setParams(params);
		return ZabbixKit.call(getRequest);
	}
	public static JSONObject getEnvAndItemsByGroupid(String groupid){
		JSONObject params = new JSONObject();
		if(StringUtils.isEmpty(groupid)){groupid=group_null_id;};
		params.put("groupids",groupid );
		params.put("output",new String[]{"hostid","host","status","description","name"});
		params.put("selectItems",new String[]{"hostid","name","key_","lastvalue","lastclock","itemid","status"});
		Request getRequest = RequestBuilder.newBuilder().method("host.get").build();
		getRequest.setParams(params);
		return ZabbixKit.call(getRequest);
	}
	public static JSONObject getWaterAndItemsByGroupid(String groupid){
		JSONObject params = new JSONObject();
		if(StringUtils.isEmpty(groupid)){groupid=group_null_id;};
		params.put("groupids",groupid );
		params.put("output",new String[]{"hostid","host","status","description","name"});
		params.put("selectItems",new String[]{"hostid","name","key_","lastvalue","lastclock","itemid","status"});
		Request getRequest = RequestBuilder.newBuilder().method("host.get").build();
		getRequest.setParams(params);
		return ZabbixKit.call(getRequest);
	}
	public static JSONObject getSwitchAndItemsByGroupid(String groupid){
		JSONObject params = new JSONObject();
		if(StringUtils.isEmpty(groupid)){groupid=group_null_id;};
		params.put("groupids",groupid );
		params.put("output",new String[]{"hostid","host","status","description","name"});
		params.put("selectItems",new String[]{"hostid","name","key_","lastvalue","lastclock","itemid","status"});
		Request getRequest = RequestBuilder.newBuilder().method("host.get").build();
		getRequest.setParams(params);
		return ZabbixKit.call(getRequest);
	}
	public static JSONObject getHostCountByGroupids(List<String> groupid){
		JSONObject params = new JSONObject();
		if(groupid==null||groupid.size()==0)
		{
			groupid=new ArrayList<>();
			groupid.add(group_null_id);
		};
		params.put("groupids",groupid );
		params.put("countOutput","true");
		Request getRequest = RequestBuilder.newBuilder().method("host.get").build();
		getRequest.setParams(params);
		return ZabbixKit.call(getRequest);
	}


}
