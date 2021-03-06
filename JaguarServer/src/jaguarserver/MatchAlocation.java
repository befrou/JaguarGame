/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarserver;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 *
 * @author bruno
 */
public class MatchAlocation {
  
  private static MatchAlocation instance = null;
  
  private int lastId;
  private ArrayList<Match> matches;
  private int capacity;
  
  private Semaphore mutex = new Semaphore(1);
  
  protected MatchAlocation() {
    
  }
  
  public static MatchAlocation getInstance() {
    if(instance == null) {
      instance = new MatchAlocation();
    }
    return instance;
  }
  
  public void init(int capacity) {
    this.capacity = capacity;
    matches = new ArrayList<>();
    lastId = 0;
  }
  
  public Match createMatch() throws InterruptedException {
    mutex.acquire();
    
    Match match = new Match(lastId++);
    this.matches.add(match);
    
    mutex.release();
    
    return match;
  }
  
  public int alocateMatch(User user) throws InterruptedException {
    mutex.acquire();
    
    if(matches.size() == capacity) {
      mutex.release();
      return -2;
    }
    
    int id = lastId++;
    
    Match newMatch = new Match(lastId++);
    matches.add(newMatch);
    
    mutex.release();
    
    return id;
  }
  
  public Match getMatchById(int id) throws InterruptedException {
    mutex.acquire();
    
    for(Match match : matches) {    
      if(match.getId() == id) {
        mutex.release();
        return match;
      }      
    }  
    mutex.release();
    
    return null;
  }
  
  public Match filterMatchByPreReg(int userOneId, int userTwoId) throws InterruptedException {
    mutex.acquire();
    for(Match match : matches) {
      if(match.isAvailable()) {
        User userOne = match.getUserOne();
        
        if(userOne!= null) {
          if(userOne.getId() == userOneId) {
            mutex.release();
            return match;
          }
        }
        User userTwo = match.getUserTwo();
        
        if(userTwo != null) {
          if(userTwo.getId() == userTwoId) {
            mutex.release();
            return match;
          }
        }
      }
    }
    mutex.release();
    return null;
  }
  
  public Match lookForAvailableMatch() throws InterruptedException {
    mutex.acquire();
    
    for(Match match : matches) {
      if(match.isAvailable()) {
        mutex.release();
        return match;
      }
    }
    mutex.release();
    
    return null;
  }
  
  public Match getMatchByUserId(int userId) throws InterruptedException {
    mutex.acquire();
   
    for(Match match : matches) {
      User userOne = match.getUserOne();
      User userTwo = match.getUserTwo();
      
      if(userOne != null) {
        if(match.getUserOne().getId() == userId) {
          mutex.release();
          return match;
        }
      }
      
       if(userTwo != null) {
        if(match.getUserTwo().getId() == userId) {
          mutex.release();
          return match;
        }
      }
    }
  
    mutex.release();
    
    return null;
  }
  
   public int getTotalMatchesAlocated() throws InterruptedException {
    
    mutex.acquire();
    int numMatches = matches.size();
    mutex.release();
  
    return numMatches;
  }
   
}
