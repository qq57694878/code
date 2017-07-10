package com.youi.beetl.functions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.core.om.ObjectAA;
import org.springframework.stereotype.Service;

@Service
public class SortFunction implements Function {
	ObjectAA defaultObjectAA = ObjectAA.defaultObjectAA();
	@Override
	public Object call(Object[] arg, Context arg1) {
		List cols = (List)arg[0];
		String attrName = (String)arg[1];
		sort(cols,attrName);
		return cols;
	}
	
	private void sort(List cols,final String name){
		
		Collections.sort(cols, new Comparator() {
			
			public int compare(Object o1, Object o2) {
				Object v1 = defaultObjectAA.value(o1, name);
				Object v2 = defaultObjectAA.value(o2, name);
				if(v1 instanceof Comparable &&v2 instanceof Comparable){
					return ((Comparable)v1).compareTo(v2);
				}else{
					throw new RuntimeException("can not comparable!");
				}
				
			}
		});
	}
	
	
	

}
