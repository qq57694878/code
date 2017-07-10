package com.youi.business.monitoring;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2017/3/14.
 */
public class a {

    public static void main(String[] args) {
        List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
        Map<String,Object> m1 = new HashMap<String,Object>();
        m1.put("id","3");
        list.add(m1);
        Map<String,Object> m2 = new HashMap<String,Object>();
        m2.put("id","2");
        list.add(m2);
        Map<String,Object> m3 = new HashMap<String,Object>();
        m3.put("id","1");
        list.add(m3);
        sortHostList(list);
        JSONObject p=  new JSONObject();
        p.put("bj",list);
        System.out.println(p.toJSONString());

    }

    private static void sortHostList(List<Map<String,Object>> list){
        if(list!=null){
            //采用选择排序
            int size = list.size();
            for(int i=0;i<size;i++){
                int k=i;
                Map<String,Object> m1 = list.get(i);
                for(int j=i+1;j<size;j++){
                    Map<String,Object> m2 = list.get(j);
                    if(Integer.parseInt(String.valueOf(m1.get("id")))>Integer.parseInt(String.valueOf(m2.get("id")))){
                        k=j;
                    }
                }
                if(k!=i){
                    Map<String,Object> temp =  list.get(i);
                    list.set(i,list.get(k));
                    list.set(k,temp);
                }
            }

        }
    }
}
