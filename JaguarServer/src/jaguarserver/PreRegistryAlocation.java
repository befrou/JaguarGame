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
public class PreRegistryAlocation {
  private static PreRegistryAlocation instance = null;
  private ArrayList<PreRegistry> preRegistries;
  
  private Semaphore mutex = new Semaphore(1);
  
  protected PreRegistryAlocation() {
    
  }
  
  public static PreRegistryAlocation getInstance() {
    if(instance == null) {
      instance = new PreRegistryAlocation();
    }
    return instance;
  }
  
  public void init(int capacity) {
    this.preRegistries = new ArrayList<>();
  }
  
  public void AlocatePreRegistry(String username1, int userId1, String username2, int userId2) throws InterruptedException {
    mutex.acquire();
    this.preRegistries.add(new PreRegistry(username1, userId1, username2, userId2));
    mutex.release();
  }
  
  public int getTotalPreRegistriesAlocated() throws InterruptedException {
    mutex.acquire();
    int preRegistriesAlocated = this.preRegistries.size();
    mutex.release();
    return preRegistriesAlocated;
  }
  
  public PreRegistry getPreRegistryByUsername(String username) throws InterruptedException {
    mutex.acquire();
    
    for(PreRegistry pre : preRegistries) {
      if(pre.getUsernameOne().equals(username)) {
        mutex.release();
        return pre;
      }else if(pre.getUsernameTwo().equals(username)) {
        mutex.release();
        return pre;
      }
    }
    mutex.release();
    return null;
  }
  
  public PreRegistry getPreRegistryByUserId(int userId) throws InterruptedException {
    mutex.acquire();
    
    for(PreRegistry pre : preRegistries) {
      if(pre.getUserOneId() == userId) {
        mutex.release();
        return pre;
      }else if(pre.getUserTwoId() == userId) {
        mutex.release();
        return pre;
      }
    }
    mutex.release();
    return null;
  }
        
  
}
