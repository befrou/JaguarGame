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
  }
 
  public int registerPlayer(String username) throws InterruptedException {
      
    if(userRegistry.getTotalUsersAlocated() == USER_CAPACITY) {
      return -2;
    } else if(userRegistry.containUser(username)) {
      return -1;
    } 
    User user = userRegistry.alocateUser(username);

    Match match = matchRegistry.lookForAvailableMatch();

    if(match == null) {
      match = matchRegistry.createMatch(user);
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
  
  public boolean verifyUserMatchUp(int userId) throws InterruptedException {
    Match match = matchRegistry.getMatchByUserId(userId);
    
    return match.isAvailable();
    
  }
}
