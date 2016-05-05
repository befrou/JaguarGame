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
public class UserAlocation {
  
  private static UserAlocation instance = null;
  
  private int capacity;
  private int lastId;
  private ArrayList<User> users;
  
  private Semaphore mutex = new Semaphore(1);
  
  /* singleton can be called only be called by other classes in the same package */
  protected UserAlocation() {
    
  }
  
  /* initializer UserAlocation attributes */
  public void init(int capacity) {
    users = new ArrayList<>();
    lastId = 0;
    this.capacity = capacity;
  }
  
  /* Return an unique instance of object UserAlocation */
  public static UserAlocation getInstance() {
    if(instance == null) {
      instance = new UserAlocation();
    }
    return instance;
  }
  
  
  /* Register User */
  public User alocateUser(String username) throws InterruptedException {
    mutex.acquire();
    User newUser = new User(username, ++lastId);
    users.add(newUser);
    mutex.release();
    
    return newUser;
  }
  
  /* Verify a user with the parameter username has already been alocated */
  public boolean containUser(String username) throws InterruptedException {  
    mutex.acquire();
    boolean b = users.stream().anyMatch((user) -> (user.getUsername().equals(username)));
    mutex.release();
    return b;
  }
  
  /* return user with the informed id */
  public User getUserById(int id) throws InterruptedException {
    mutex.acquire();
    for(User user : users) {
      if(user.getId() == id) {
        mutex.release();
        return user;
      }
    }
    mutex.release();
    
    return null;   
  }
  
  public int getTotalUsersAlocated() throws InterruptedException {
    mutex.acquire();
    int i = users.size();
    mutex.release();
    return i;
  }
  
  public void startMatch() {
    
  }
}
