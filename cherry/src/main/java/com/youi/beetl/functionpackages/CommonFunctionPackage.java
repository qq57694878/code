package com.youi.beetl.functionpackages;

import org.springframework.stereotype.Service;

@Service
public class CommonFunctionPackage {
	public String H1(String value){
		return "<H1>"+value+"<H1>";
	}
	public String H2(String value){
		return "<H2>"+value+"<H2>";
	}
}
