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
    this.alocManager = AlocationManager.getInstance();
    this.alocManager.init();
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
  public boolean isVacant(int userId) {
    boolean has = false;
    
    try {
      has = this.alocManager.userMatchAvailable(userId);
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

  @Override
  public int isMyTurn(int userId) throws RemoteException {
    Match match;
    try {
      match =  this.alocManager.getUserMatch(userId);
      if(match != null) return match.isMyTurn(userId);
    } catch (InterruptedException ex) {
      Logger.getLogger(JaguarServer.class.getName()).log(Level.SEVERE, null, ex);
    }
      return -1;
  }

  @Override
  public String getBoard(int userId) throws RemoteException {
    
    Match match;
    String board = "";
    try {
      if((match = this.alocManager.getUserMatch(userId)) != null)  board = match.getBoard();      
    } catch (InterruptedException ex) {
      Logger.getLogger(JaguarServer.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return board;
  }

  @Override
  public String getOpponent(int userId) throws RemoteException {
    
    String opponent = null;
    
    try {
      opponent = this.alocManager.getOpponentName(userId);
    } catch (InterruptedException ex) {
      Logger.getLogger(JaguarServer.class.getName()).log(Level.SEVERE, null, ex);
    }
    return opponent;
  }
  

  
}
