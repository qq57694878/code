package com.youi.example1;

/**
 * Created by jinliang on 2017/3/7.
 */
public class Jzt {
    public static void main(String[] args) {
        printjzt(5);
    }

    public static void printjzt(int n){
        for(int i=1;i<=n;i++){
            //打印空格
            for(int j=0;j<n-i;j++){
                System.out.print(" ");
            }
            for(int k=0;k<i;k++){
                System.out.print(" *");
            }
            System.out.println();
        }
    }
}
