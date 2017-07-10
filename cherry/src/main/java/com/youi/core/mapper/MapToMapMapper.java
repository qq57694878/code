package com.youi.core.mapper;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

import java.util.Map;

/**
 * Created by jinliang on 2017/1/17.
 */
public class MapToMapMapper extends CustomMapper<Map,Map> {
    @Override
    public void mapAtoB(Map map, Map map2, MappingContext context) {
        for(Object key:map.keySet()){
            map2.put(key,map.get(key));
        }
    }

    @Override
    public void mapBtoA(Map map, Map map2, MappingContext context) {
        for(Object key:map.keySet()){
            map2.put(key,map.get(key));
        }
    }
}
