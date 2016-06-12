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
public class TimeManager {
  private final long MOVE_TIMEOUT = 30000;
  private final long MATCHMAKING_TIMEOUT = 120000;
  
  private long started_at = System.currentTimeMillis();
  private long move_time_limit;
  private long matchmaking_time_limit;
  
  private long currentTime;
  
  public TimeManager() {
   this.currentTime = System.currentTimeMillis();
   //setUpTimers();
  }
  
  public void setUpTimers() {
    this.started_at = System.currentTimeMillis();
    this.move_time_limit = this.started_at + MOVE_TIMEOUT;
    this.matchmaking_time_limit = this.started_at + MATCHMAKING_TIMEOUT;
  }
  

  
  public boolean reachedLimitToMove() {
    this.currentTime = System.currentTimeMillis();
    return currentTime >= this.move_time_limit;
  }
  
  public boolean reachedLimitToFindMatch() {
    this.currentTime = System.currentTimeMillis();
    return System.currentTimeMillis() >= this.matchmaking_time_limit;
  } 
}
