/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarclient;

import java.rmi.RemoteException;

/**
 *
 * @author bruno
 */
public class JaguarClientMain {
  public static void main(String[] args) throws RemoteException, InterruptedException {  
    
   
    if (args.length != 2) {
      System.out.println("The entry should be -> (host) (name)");
      System.exit(1);
    } else {
      String server = args[0];
    
      JaguarClient clientGame = new JaguarClient(server);
      clientGame.initClientMatch(args[1]);
    }
    
  }
}
