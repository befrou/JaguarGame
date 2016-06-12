/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarserver;

import jaguarshared.PieceType;
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
    System.out.println("USER REGISTRY - OK");
    
    Match match = this.matchRegistry.lookForAvailableMatch();
   
    System.out.println("Username: " + username + "\nID: " + user.getId());
    
    if(match == null && this.matchRegistry.getTotalMatchesAlocated() < MATCH_CAPACITY) {    
      match = this.matchRegistry.createMatch();
      //System.out.println("Match Created with id " + match.getId() + "\n");
    } 
    user.setMatch(match);
    System.out.println(username + " is on Match " + match.getId());
    
    if(match.getUserOne() == null) {
       user.setPieceType(PieceType.JAGUAR);
       match.setUserOne(user);
    } else {
      user.setPieceType(PieceType.DOGS);
      match.setUserTwo(user);
    }

    return user.getId();
  }
  
  public Match getUserMatch(int userId) throws InterruptedException {
    
     User user = this.userRegistry.getUserById(userId);
     Match match = user.getMatch();
     return match;
  //return this.matchRegistry.getMatchByUserId(userId);
  }
  
  public PieceType getUserPieceType(int userId) throws InterruptedException {
    return this.userRegistry.getUserById(userId).getPieceType();
  }
  
  public int userMatchAvailable(int userId) throws InterruptedException {
    Match match = this.matchRegistry.getMatchByUserId(userId);    
    
    //  timeout
    if(match.getMatchManager().matchmakingTimeout() == -2)
       return -2;
    
    int matchState = match.isAvailable() ? 0 : 1; // 0 => there is only one user - 1 => there are two users
    
    return matchState;   
  }
  
  public int getMatchState(int userId) throws InterruptedException {
    
    Match match = this.matchRegistry.getMatchByUserId(userId);
    
    int myTurn = match.getMatchState(userId);
    
    return myTurn; //0
  }
  
  public String getOpponentName(int userId) throws InterruptedException {
    Match match = this.getUserMatch(userId);
   
    User user1 = match.getUserOne();
    User user2 = match.getUserTwo();
       
    String opponent = "";
    if(user1 != null && user2 != null) {
      if(user1.getId() != userId) {
         opponent = user1.getUsername();
      } else {
        opponent = user2.getUsername();
      }
     
    }
  
    return opponent;
  }
  
  public int endMatch(int userId) throws InterruptedException {
    User user = this.userRegistry.getUserById(userId);
    //Match match = this.matchRegistry.getMatchByUserId(userId);
    Match match = user.getMatch();
    
    System.out.println("ENDING MATCH");
    System.out.println("Match ID -> " + match.getId());
    System.out.println("User1 ID -> " + match.getUserOne().getId());
    System.out.println("User2 ID -> " + match.getUserTwo().getId() + "\n");
    
    boolean userOneRemoved = this.userRegistry.removeUserById(match.getUserOne().getId());
    boolean userTwoRemoved = this.userRegistry.removeUserById(match.getUserTwo().getId());
 
    match.setUserOne(null);
    match.setUserTwo(null);
    
    if(match != null) {
      match.resetMatch();
      match.getMatchManager().setWaitingForMatchUp(false);
      if(userOneRemoved && userTwoRemoved) { 
        return 0;
      }
    }
    return -1;
  }
}
