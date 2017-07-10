package com.youi.business.common.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/9/23.
 */
public class DevelopKit {

    public static String array2String(Object[] array){
        StringBuilder r = new StringBuilder();
        for(Object o:array){
            r.append(o).append(",");
        }
        if(r.length()>0){
            r.deleteCharAt(r.length()-1);
        }
        return r.toString();
    }
    public static String array2SqlString(Object[] array){
        StringBuilder r = new StringBuilder();
        for(int i=0;i<array.length-1;i++){
            r.append(array[i]).append("','");
        }
        if(array.length>0){
            r.append(array[array.length-1]);
        }
        if(r.length()>0){
            return "'"+r.toString()+"'";
        }
        return r.toString();
    }

    public static void toStrMap(Map<String,Object> m){
        if(m!=null){
            for(String key:m.keySet()){
                Object value = m.get(key);
                if(value instanceof List){

                }else{
                    m.put(key,String.valueOf(value));
                }
            }
        }
    }
    public static Map<String,Object> transation2ObjectMap(Map<String,String> m){
        Map<String,Object> r =null;
        if(m!=null){
             r = new HashMap<String,Object>();
            for(String key:m.keySet()){
                Object value = m.get(key);
                r.put(key,value);
            }
        }
        return r;
    }
    public static String getSqlInString(String in){
        return "'"+in.replaceAll(",","','")+"'";
    }

    public static void main(String args[]){
        //System.out.println(getSqlInString("biz_leader,xxzx_leader"));
        System.out.println(convertFormatNetworkSpeed(1234456789d));
    }
/*    public static String convertFormatNetworkSpeed(Double d){
        String unit="";
        String a[]={"K","M","G"};
        Double v =d;
        for(int i=3;i>=1;i--){
            double cs = Math.pow(1000,i);
            if(d/cs>1){
                v=d/cs;
                unit = a[i-1];
                break;
            }
        }
        DecimalFormat df= new DecimalFormat("#####0.00"+unit+"bps");
        return  df.format(v);
    }*/
public static String convertFormatNetworkSpeed(Double d){
   Double v = d/1000;
    DecimalFormat df= new DecimalFormat("#####0.00");
    return  df.format(v);
}

}
