package com.youi.beetl.tags;

import com.youi.core.codetable.CodeTableUtil;
import org.apache.log4j.Logger;
import org.beetl.core.Tag;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
@Service
@Scope("prototype")
public class CodeSelectTag extends Tag {

	private static Logger log = Logger.getLogger(CodeSelectTag.class);

	@Override
	public void render() {
		try {
			Map<String, String> param = (Map<String, String>) args[1];

			log.debug("获取标签属性");

			String id = param.get("id") == null ? "" : param.get("id");
			String name = param.get("name") == null ? "" : param.get("name");
			String value = param.get("value") == null ? "" : param.get("value");
			String class_ = param.get("class") == null ? "" : param.get("class");
			String style = param.get("style") == null ? "" : param.get("style");
			String codeType = param.get("codeType") == null ? "" : param.get("codeType");
			StringBuilder sb = new StringBuilder();
			Map<String,String> m = CodeTableUtil.getCodeMap(codeType);
			sb.append("<select  id=\"" + id
					+ "\" name=\"" + name + "\" style=\"" + style
					+ "\" class=\"" + class_
					+ "\" data-rel=\"chosen\"><option value=\"\">请选择...</option>");
			for (String key : m.keySet()) {
                 String value1 = m.get(key);
						if(key.equals(value)){
							sb.append("<option selected=\"selected\" value=\"" + key + "\">" + value1 + "</option>");
						}else{
							sb.append("<option value=\"" + key + "\">" + value1 + "</option>");
				        }
			}
			sb.append("</select>");
			ctx.byteWriter.writeString(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
