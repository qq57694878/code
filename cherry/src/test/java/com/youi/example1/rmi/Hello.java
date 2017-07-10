package com.youi.example1.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by jinliang on 2017/2/22.
 */
public interface Hello extends Remote {
    public void sayHello(String content) throws RemoteException;
}
