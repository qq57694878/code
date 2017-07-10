package com.youi.core.codetable;

import com.youi.core.spring.ApplicationContextHelper;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ServletContextResourcePatternResolver;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 */
public class ModifyCodeTable
{
	private static Logger logger = LoggerFactory
			.getLogger(ModifyCodeTable.class);

	public void loadCodeTable()
	{
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext context = webApplicationContext.getServletContext();
		JdbcTemplate jdbcTemplate = ApplicationContextHelper.getBean(JdbcTemplate.class);
		ResourcePatternResolver resourcePatternResolver = new ServletContextResourcePatternResolver(context);

		try
		{
			logger.info("ModifyCodeTable - 开始 ");
			
			String sql = loadCodetableConfig(resourcePatternResolver, "classpath:codetable/*.xml");
			List<Map<String,Object>> list = null;
			Map map = new LinkedHashMap();
			if (sql != null && !sql.trim().equals(""))
			{
				list =  jdbcTemplate.queryForList(sql);
				if(list!=null){
					Map mapList = null;
					String preType = "";
					for (int i = 0; i < list.size(); i++)
					{
						Map<String,Object> r = list.get(i);
						String type =(String) r.get("TYPE");
						if (map.containsKey(type))
						{
							mapList = (Map) map.get(type);
							mapList.put(String.valueOf(r.get("CODE")), String.valueOf(r.get("VALUE")));
							map.put(type, mapList);
						}
						else
						{
							mapList = new LinkedHashMap();
							mapList.put(String.valueOf(r.get("CODE")), String.valueOf(r.get("VALUE")));
							map.put(type, mapList);
						}
					}
				}
				
			}
			context.setAttribute("CODE_TABLE_CONTENT", map);
			if (list == null)
			{
				logger.info("ModifyCodeTable - 0条");
			}
			else
			{
				logger.info("ModifyCodeTable - 码表：" + list.size() + "条记录");
			}
		}
		catch (Exception e)
		{
			logger.error("ModifyCodeTable - 错误");
			e.printStackTrace();
		}

	}

	 
	public String loadCodetableConfig(ResourcePatternResolver resourcePatternResolver, String codeTableConfig) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		try
		{
			Resource[] sqlResources = resourcePatternResolver.getResources(codeTableConfig);
			for (int i = 0; i < sqlResources.length; i++)
			{
				InputStream in = sqlResources[i].getInputStream();
				SAXBuilder builder = new SAXBuilder();
				Document document = builder.build(in);
				Element rootElement = document.getRootElement();
				List list = rootElement.getChildren("sql");
				Element child = null;
				logger.info("InitListener - Load XML data from " + sqlResources[i].getDescription());
				for (int j = 0; j < list.size(); j++)
				{
				
					child = (Element) list.get(j);
					sql.append(child.getAttributeValue("sqlstr")).append(" union ");
				}
			}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
			throw e1;
		}
		if (sql != null && sql.length() > 7)
		{
			return sql.substring(0, sql.length() - 7).toString()+" ORDER BY SORT";
		}
		else
		{
			return null;
		}
	}

}
