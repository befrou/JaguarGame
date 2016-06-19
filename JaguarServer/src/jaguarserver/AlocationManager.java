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
public class AlocationManager {
  
  private static AlocationManager instance = null;
  
  private static final int USER_CAPACITY = 100;
  private static final int MATCH_CAPACITY = USER_CAPACITY/2;
  
  private UserAlocation userRegistry;
  private MatchAlocation matchRegistry;
  private PreRegistryAlocation preRegistry;
  
  
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
    this.preRegistry = PreRegistryAlocation.getInstance();
    
    this.userRegistry.init(USER_CAPACITY);
    this.matchRegistry.init(MATCH_CAPACITY);
    this.preRegistry.init(MATCH_CAPACITY);
    /*
    try {
      this.preRegistry.AlocatePreRegistry("J1", 10, "J2", 20);
    } catch (InterruptedException ex) {
      Logger.getLogger(AlocationManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  */
  }
  
  public int preRegister(String username1, int userId1, String username2, int userId2) throws InterruptedException {
    this.preRegistry.AlocatePreRegistry(username1, userId1, username2, userId2);
    return 0;
  }
 
  public int registerPlayer(String username) throws InterruptedException {
    System.out.println("\nRegistering player: " + username); 
    
    if(this.userRegistry.containUser(username)) {
      return -1;
    }
    
    if(this.userRegistry.getTotalUsersAlocated() == USER_CAPACITY) {
      return -2;
    }
    
    User user;
    Match match;
    
    
    PreRegistry preReg = this.preRegistry.getPreRegistryByUsername(username);
    if(preReg != null) {
      
      if(preReg.getUsernameOne().equals(username)) {
        user = this.userRegistry.alocatePreRegisteredUser(username, preReg.getUserOneId());
      }else {
        user = this.userRegistry.alocatePreRegisteredUser(username, preReg.getUserTwoId());
      }
      
      match = this.matchRegistry.filterMatchByPreReg(preReg.getUserOneId(), preReg.getUserTwoId());
    }else {
      user = this.userRegistry.alocateUser(username);
      match = this.matchRegistry.lookForAvailableMatch();
    }
   
    System.out.println("Username: " + username + "  - ID: " + user.getId());
    
    if(match == null && this.matchRegistry.getTotalMatchesAlocated() < MATCH_CAPACITY) {    
      match = this.matchRegistry.createMatch();
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
    match.startMatch();

    return user.getId();
  }
  
  public Match getUserMatch(int userId) throws InterruptedException {
    
     User user = this.userRegistry.getUserById(userId);
     Match match = user.getMatch();

     return match;
  //return this.matchRegistry.getMatchByUserId(userId);
  }
  
  public int sendJaguarMove(int userId, Direction dir) throws InterruptedException {
    Match match = this.getUserMatch(userId);
    
    if(match != null) {
      if(match.getUserOne() == null || match.getUserTwo() == null) return -2; //  Match not started
      
      User user = match.getUserById(userId);
      if(user == null)  return -1;
      
      if(user.getPieceType() != match.getTurn())  return -3;  //  Noot user turn
      if(user.getPieceType() != PieceType.JAGUAR) return -4;  //  Not playing with the correct piece
      
      MatchManager manager = match.getMatchManager();
      return manager.move(" J", dir);
    }
    
    return -1;
  }
  
  public int sendDogMove(int userId, String dogId, Direction dir) throws InterruptedException {
    Match match = this.getUserMatch(userId);
    
    if(match != null) {
      if(match.getUserOne() == null || match.getUserTwo() == null) return -2; //  Match not started
      
      User user = match.getUserById(userId);
      if(user == null)  return -1;
      
      if(user.getPieceType() != match.getTurn())  return -3;  //  not user turn
      if(user.getPieceType() != PieceType.DOGS) return -4;  //  not playing with the correct piece
      
      MatchManager manager = match.getMatchManager();
      return manager.move(dogId, dir);
    }
    
    return -1;
  }
  
  public PieceType getUserPieceType(int userId) throws InterruptedException {
    return this.userRegistry.getUserById(userId).getPieceType();
  }
  
  public int userMatchAvailable(int userId) throws InterruptedException {
    Match match = this.matchRegistry.getMatchByUserId(userId);    
    if(match == null) return -1;
    
    MatchManager manager = match.getMatchManager();
   
    User user = match.getUserById(userId);
    if(user == null)  return -1;
    /*
      0- não há partida
      1- há partida e o jogador é a onça
      2- há partida e o jogador é os cães
    */
     
    //  timeout
    if(manager.matchmakingTimeout() == -2)
       return -2;
    
    int matchState = match.isAvailable() ? 0 : 1; // 0 => there is only one user - 1 => there are two users
    
    if(matchState == 1) {
      if(user.getPieceType() == PieceType.DOGS) return 2;
      else 
        return 1;
    }
    return matchState;   
  }
  
  public int getMatchState(int userId) throws InterruptedException {
    /*
      -2 não há dois jogadores registrados na partida
      -1 erro
    */
    Match match = this.matchRegistry.getMatchByUserId(userId);
    if(match == null) return -1;
    if(match.isAvailable()) return -2;
    
    int matchState = match.getMatchState(userId);
    
    return matchState;
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
    
    User userOne = match.getUserOne();
    User userTwo = match.getUserTwo();
    
    
    if(userOne != null) {
      if(userOne.getId() == userId) {
        this.userRegistry.removeUserById(match.getUserOne().getId());
        match.setUserOne(null);
      }
    }else if(userTwo != null) {
        if(userTwo.getId() == userId) {
          this.userRegistry.removeUserById(match.getUserTwo().getId());
          match.setUserTwo(null);
        }
     }
    
    
    if(match.getUserOne() == null && match.getUserTwo() == null) {
      match.resetMatch();
      match.getMatchManager().setWaitingForMatchUp(false);
      return 0;     
    }
    return 0;
  }
}
