package com.youi.example1;

/**
 * Created by jinliang on 2016/9/27.
 */
public class AA {
    public static void main(String args[]){
       String s=  getPart3(1,6);
        System.out.println(s);
    }
    private static String getPart3(long value,int n){
        StringBuilder result = new StringBuilder();
        String  v = String.valueOf(value);
        int j = n>v.length()?n-v.length():0;
        for(int i=0;i<j;i++){
            result.append("0");
        }
        int k=n>v.length()?0:v.length()-n;
        for(int i=k;i<v.length();i++){
            result.append(v.charAt(i));
        }
        return result.toString();
    }
}
