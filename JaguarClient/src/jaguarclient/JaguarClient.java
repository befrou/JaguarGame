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
      this.clientGame = (JaguarServerInterface) Naming.lookup("//" + server + "/JaguarServerBruno");
    } catch (NotBoundException | MalformedURLException | RemoteException ex) {
      System.out.println("JaguarClient failed:");
    }
  }
  
  public void initClientMatch(String username) throws RemoteException, InterruptedException {
    int id = this.clientGame.registerPlayer(username);
    
    if(id == -2) {
      System.out.println("Server has reached maximum user capacity!");
      System.exit(0);
    } else if(id == -1) {
      System.out.println("Username is already being used. Pleas try again.");
      System.exit(0);
    }
    
    this.clientId = id;
    
    System.out.print("Waiting for match up...");
    waitForMatchUp();
    
    // match = clientGame.startMatch(id);    
  }  
  
  public void play() {
    
  }
  
  private int isMyTurn() throws RemoteException {
    return this.clientGame.isMyTurn(this.clientId);
  }
  
  private void waitForMatchUp() throws RemoteException, InterruptedException {
   
    while(this.clientGame.isVacant(this.clientId)) {
      sleep(700);
      System.out.print(".");
    }
  }
}
