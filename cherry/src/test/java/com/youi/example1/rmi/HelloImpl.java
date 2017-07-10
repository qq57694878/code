package com.youi.example1.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by jinliang on 2017/2/22.
 */
public class HelloImpl extends UnicastRemoteObject implements Hello {
    protected HelloImpl() throws RemoteException {
        super();
    }

    @Override
    public void sayHello(String content) throws RemoteException {
              System.out.println("sayHello:"+content);
    }

    public static void main(String[] args) throws RemoteException {
        try {
            Hello hello = new HelloImpl();
            LocateRegistry.createRegistry(1099); //加上此程序，就可以不要在控制台上开启RMI的注册程序，1099是RMI服务监视的默认端口
            Naming.rebind("rmi://localhost:1099/hello",hello);
            System.out.println("rebind hello!");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
