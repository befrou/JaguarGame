/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarclient;

import jaguarshared.JaguarServerInterface;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

        
/**
 *
 * @author bruno
 */
public class JaguarClient {

  private JaguarServerInterface clientGame;
  private int clientId;
  
  public JaguarClient(String server) {
    try {
      clientGame = (JaguarServerInterface) Naming.lookup("//" + server + "/JaguarServerBruno");
    } catch (NotBoundException | MalformedURLException | RemoteException ex) {
      System.out.println("JaguarClient failed:");
    }
  }
  
  public void initClientGame(String username) throws RemoteException, InterruptedException {
    int id = clientGame.registerPlayer(username);
    
    if(id == -2) {
      System.out.println("Server has reached maximum user capacity!");
      System.exit(0);
    } else if(id == -1) {
      System.out.println("Username is already being used. Pleas try again.");
      System.exit(0);
    }
    
    waitForMatchUp();
    
    this.clientId = id;
    
    
 
    // match = clientGame.startMatch(id);
    
    
  }  
  
  private void waitForMatchUp() throws RemoteException, InterruptedException {
    
    while(!clientGame.hasOpponent(this.clientId)) {
      sleep(700);
      System.out.println("*");
    }
  }
}
