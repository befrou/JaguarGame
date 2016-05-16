/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarserver;


import gameLogic.PieceType;

/**
 *
 * @author bruno
 */
public class User {
  
  private int id;
  private String username;
  private PieceType pType;
  
  public User(String username, int id) {
    this.id = id;
    this.username = username;
  }
  
  public PieceType getPieceType() {
    return this.pType;
  }
  
  public void setPieceType(PieceType type) {
    this.pType = type;
  }
  
  public int getId() {
    return this.id;
  }
  
  public String getUsername() {
    return this.username;
  }
  
}
