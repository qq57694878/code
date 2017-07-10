package com.youi.beetl.format;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.beetl.core.Format;

/**
 * beetl页面日期格式化，重写是为了处理Oracle的TIMESTAMP
 * @author 董华健
 */
public class DateFormat implements Format {
	
	/**
	 * 实现格式化处理方法
	 */
	public Object format(Object data, String pattern) {
		if (data == null)
			return null;
		if (Date.class.isAssignableFrom(data.getClass())) {
			SimpleDateFormat sdf = null;
			if (pattern == null) {
				sdf = new SimpleDateFormat();
			} else {
				sdf = new SimpleDateFormat(pattern);
			}
			return sdf.format((Date) data);
		} else if (data.getClass() == Long.class) {
			// Date date = new Date((Long) data);
			SimpleDateFormat sdf = null;
			if (pattern == null) {
				sdf = new SimpleDateFormat();
			} else {
				sdf = new SimpleDateFormat(pattern);
			}
			return sdf.format((Date) data);

		} else {
			throw new RuntimeException("Arg Error:Type should be Date:" + data.getClass());
		}
	}

}