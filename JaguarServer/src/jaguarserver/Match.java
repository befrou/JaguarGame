/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarserver;


import jaguarshared.Direction;
import jaguarshared.PieceType;

/**
 *
 * @author bruno
 */
public class Match {  
  private int id;
  private User user1; /* Jaguar */
  private User user2; /* Dogs */
  private MatchManager manager;
  
  private boolean available;
  
  public Match(int id) {
    this.user1 = null;
    this.user2 = null;
    this.id = id;
    this.manager = new MatchManager();
    
    this.available = true;
  }
  
  public void startMatch() {
    this.manager.startMatch();
  }
  
  public void resetMatch() {
    this.manager = new MatchManager();
  }
  
  public boolean isAvailable() {
    return (user1 == null || user2 == null);
  }
  
  public void setAvailability(boolean available) {
    this.available = available;
  }
  
  public int getId() {
    return this.id;
  }
  
  public User getUserOne() {
    return this.user1;
  }
  
  public User getUserTwo() {
    return this.user2;
  }
  
  public User getUserById(int userId) {
    return (userId == user1.getId()) ? user1 : user2;
  }
  
  public void setUserOne(User user1) {
    this.user1 = user1; 
  }
  
  public void setUserTwo(User user2) {
    this.user2 = user2;
  }
  
  public MatchManager getMatchManager() {
    return this.manager;
  }
  
  public int getMatchState(int userId) {
    PieceType pType = (user1.getId() == userId) ? user1.getPieceType() : user2.getPieceType();
    return this.manager.getMatchState(pType);
  }
  
  public PieceType getTurn() {
    return this.manager.geTurn();
  }
  
  public String getBoard() {
    return this.manager.getBoard();
  }
}
