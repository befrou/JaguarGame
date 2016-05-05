/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarserver;

import java.rmi.RemoteException;
import jaguarshared.JaguarServerInterface;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bruno
 */
public class JaguarServer extends UnicastRemoteObject implements JaguarServerInterface {

  private final AlocationManager alocManager;
  
  public JaguarServer() throws RemoteException {  
    alocManager = AlocationManager.getInstance();
    alocManager.init();
  }

  @Override
  public int registerPlayer(String username) throws RemoteException { 
    try {   
      return this.alocManager.registerPlayer(username);
    } catch(Exception e) {
      throw new RemoteException(e.getMessage());
    }
  }
  
  @Override
  public boolean hasOpponent(int userId) {
    boolean has = false;
    try {
      has = this.alocManager.verifyUserMatchUp(userId);
    } catch (InterruptedException ex) {
      Logger.getLogger(JaguarServer.class.getName()).log(Level.SEVERE, null, ex);
    }
    return has;
  }

  @Override
  public void startMatch(int userId) {
    try {
      this.alocManager.getUserMatch(userId).startMatch();
    } catch (InterruptedException ex) {
      Logger.getLogger(JaguarServer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
}
