/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarserver;

import java.rmi.RemoteException;

/**
 *
 * @author bruno
 */
public class AlocationManager {
  
  private static AlocationManager instance = null;
  
  private static final int USER_CAPACITY = 100;
  private static final int MATCH_CAPACITY = USER_CAPACITY/2;
  
  private UserAlocation userRegistry;
  private MatchAlocation matchRegistry;
  
  protected AlocationManager() {}
  
  public static AlocationManager getInstance() {
    if(instance == null) {
      instance =  new AlocationManager();
    }
    return instance;
  }
  
  public void init() {
    this.userRegistry = UserAlocation.getInstance();
    this.matchRegistry = MatchAlocation.getInstance();
    
    this.userRegistry.init(USER_CAPACITY);
    this.matchRegistry.init(MATCH_CAPACITY);
  }
 
  public int registerPlayer(String username) throws InterruptedException {
    System.out.println("\nRegistering player: " + username); 
    
    if(this.userRegistry.getTotalUsersAlocated() == USER_CAPACITY) {
      return -2;
    } else if(this.userRegistry.containUser(username)) {
      return -1;
    } 
    
    User user = this.userRegistry.alocateUser(username);
    System.out.println("User Registry - OK");
    
    Match match = this.matchRegistry.lookForAvailableMatch();   
    
    if(match == null && this.matchRegistry.getTotalMatchesAlocated() < MATCH_CAPACITY) {
      match = this.matchRegistry.createMatch(user);
      System.out.println("Match Created \n");
    } 
    
    if(match.getUserOne() == null) {
       match.setUserOne(user);
    } else {
      match.setUserTwo(user);
    }

    return user.getId();
  }
  
  public Match getUserMatch(int id) throws InterruptedException {
    return matchRegistry.getMatchByUserId(id);
  }
  
  public boolean userMatchAvailable(int userId) throws InterruptedException {
    System.out.println("\nVerifying match up");
    Match match = this.matchRegistry.getMatchByUserId(userId);
    System.out.println("Found available match");
    
    return match.isAvailable();
    
  }
}
