package com.youi.business.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jinliang on 2016/11/21.
 */
public class DateKit {
    public static String date2StrSecond(Date d){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }
    public static String date2Strdate(Date d){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }
}
