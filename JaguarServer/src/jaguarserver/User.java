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
public class User {
  
  private int id;
  private String username;
  
  public User(String username, int id) {
    this.id = id;
    this.username = username;
  }
  
  public int getId() {
    return this.id;
  }
  
  public String getUsername() {
    return this.username;
  }
  
}
