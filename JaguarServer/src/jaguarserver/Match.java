/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarserver;

import gameLogic.GameManager;

/**
 *
 * @author bruno
 */
public class Match {
  
  private int id;
  private User user1; /* Jaguar */
  private User user2; /* Dogs */
  private GameManager game;
  private int turn;   /* 0 to jaguar - 1 to dogs */
  
  private boolean available;
  
  public Match(User user, int id) {
    this.user1 = user;
    this.user2 = null;
    this.id = id;
    this.game = new GameManager();
    
    this.available = true;
  }
  
  public void startMatch() {
    this.game.startMatch();
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
  
  public void setUserOne(User user1) {
    this.user1 = user1; 
  }
  
  public void setUserTwo(User user2) {
    this.user2 = user2;
  }
  
  public GameManager getMatch() {
    return this.game;
  }
}
