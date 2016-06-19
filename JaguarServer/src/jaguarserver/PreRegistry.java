/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarserver;

/**
 *
 * @author bruno
 */
public class PreRegistry {
  private String username1;
  private int userId1;
  private String username2;
  private int userId2;
  
  public PreRegistry(String username1, int userId1, String username2, int userId2) {
    this.username1 = username1;
    this.userId1 = userId1;
    this.username2 = username2;
    this.userId2 = userId2;
  }
   
  public String getUsernameOne() {
    return this.username1;
  }
  
  public String getUsernameTwo() {
    return this.username2;
  }
  
  public int getUserOneId() {
    return this.userId1;
  }
  
  public int getUserTwoId() {
    return this.userId2;
  }
}
