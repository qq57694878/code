package com.youi.example1.rmi;

import java.rmi.Naming;

/**
 * Created by jinliang on 2017/2/22.
 */
public class HelloClinet {
    public static void main(String[] args) throws Exception{
        Hello hello = (Hello)Naming.lookup("rmi://localhost:1099/hello");
        hello.sayHello("你好");
    }
}
