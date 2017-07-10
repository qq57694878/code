package com.youi.beetl.functions;

import com.youi.core.codetable.CodeTableUtil;

import org.apache.log4j.Logger;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@Service
public class CodeValue implements Function {

	private static Logger log = Logger.getLogger(CodeValue.class);
	
	/**
	 * 过滤xml文档函数实现
	 */
	@Override
	public Object call(Object[] arg, Context context) {
		
		
		String value = null;// 
		try {
			String type=(String)arg[0];
			String key=(String)arg[1];
			value = CodeTableUtil.code2mean(type, key);
		} catch (Exception e) {
			return "";
		}

		log.debug("CodeView，content=" + value);
		
		return value;
	}

}
