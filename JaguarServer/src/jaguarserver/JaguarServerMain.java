/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarserver;

import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 *
 * @author bruno
 */
public class JaguarServerMain {
  public static void main(String args[]) {
    try {
      java.rmi.registry.LocateRegistry.createRegistry(1099);
      System.out.println("RMI registry ready.");
    }
    catch (RemoteException e) {
      System.out.println("RMI registry already running.");
    }
    
    try {
      Naming.rebind("JaguarServerBruno", new JaguarServer());
      System.out.println("JaguarServerBruno is ready.");
    }
    catch(Exception e) {
      System.out.println("JaguarServerMain failed:");
      e.printStackTrace();  
    }
  }
}
