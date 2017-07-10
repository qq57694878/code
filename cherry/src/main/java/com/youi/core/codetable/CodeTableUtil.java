package com.youi.core.codetable;

import com.youi.core.spring.ServletContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;


public class CodeTableUtil
{
	private static Logger log = LoggerFactory
			.getLogger(CodeTableUtil.class);

	public CodeTableUtil()
	{
	}



	public static String code2mean(String dataMeta, Object obj)
	{
		LinkedHashMap list = null;
		Map map = (Map) ServletContextHelper.getServletContext().getAttribute("CODE_TABLE_CONTENT");
		if (map==null || map.isEmpty())
		{
			log.error("码表未加载！");
			return null;
		}
		list = (LinkedHashMap) map.get(dataMeta.toUpperCase());
		if (list != null)
		{
			return (String) list.get(String.valueOf(obj));
		}
		else
		{
			return null;
		}
	}

	
 
	public static Map<String,String> getCodeMap( String dataMeta)
	{
		LinkedHashMap codeMap = null;
		Map map = (Map) ServletContextHelper.getServletContext().getAttribute("CODE_TABLE_CONTENT");
		if (map==null || map.isEmpty())
		{
			log.error("码表未加载！");
			return null;
		}
		codeMap = (LinkedHashMap<String,String>) map.get(dataMeta.toUpperCase());
		return codeMap;
	}

  
 
	private static String code2mean(List list, String sCode, String splitChar)
	{
		String sName = "";
		String[] listTmp = sCode.split(splitChar);
		for (int j = 0; j < listTmp.length; j++)
		{
			for (int i = 0; i < list.size(); i++)
			{
				Map map = (Map) list.get(i);
				if (map != null)
				{
					String sTmpCode = (String) map.get("code");
					sTmpCode = sTmpCode == null ? "" : sTmpCode;
					if (listTmp[j].equalsIgnoreCase(sTmpCode))
					{
						String sTmpName = (String) map.get("name");
						sName = sName + splitChar + sTmpName;
					}
				}
				else
				{
					return null;
				}
			}
		}

		return sName.substring(splitChar.length());
	}

 
	public static Map getCodeMap(List list, String keyName, String valueName)
	{
		Map map = new LinkedHashMap();
		for (int i = 0; i < list.size(); i++)
		{
			Map tMap = (Map) list.get(i);
			map.put(tMap.get(keyName), tMap.get(valueName));
		}
		return map;
	}
	
 
	public static Map getLinkedHashMap(List list, String keyName, String valueName)
	{
		Map map = new LinkedHashMap();
		for (int i = 0; i < list.size(); i++)
		{
			Map tMap = (Map) list.get(i);
			map.put(tMap.get(keyName), tMap.get(valueName));
		}
		return map;
	}

	public boolean modifyData( String kind, Object key, Object value)
	{
		boolean jdg = false;
		ServletContext context = ServletContextHelper.getServletContext();
		Map map = (LinkedHashMap) context.getAttribute("CODE_TABLE_CONTENT");
		if (map != null && !map.isEmpty())
		{
			Object obj = map.get(kind);
			if (obj != null)
			{
				Map tpMap = (Map) obj;
				tpMap.put(key, value);
				jdg = true;
			}
		}
		else
		{
			System.out.println("更新失败！");
		}
		return jdg;
	}

	public boolean deleteData(String kind, Object key)
	{
		boolean jdg = false;
		ServletContext context = ServletContextHelper.getServletContext();
		Map map = (Map) context.getAttribute("CODE_TABLE_CONTENT");
		if (map != null && !map.isEmpty())
		{
			Object obj = map.get(kind);
			if (obj != null)
			{
				Map tpMap = (Map) obj;
				tpMap.remove(key);
				jdg = true;
			}
		}
		else
		{
			System.out.println("删除失败！");
		}
		return jdg;
	}

}
