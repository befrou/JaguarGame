/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarshared;

/**
 *
 * @author bruno
 */
public enum Direction {
  Right(0),
  DownRight(1),
  Down(2),
  DownLeft(3),
  Left(4),
  UpLeft(5),
  Up(6),
  UpRight(7);
 
 
  
  
  private int val;
  
  Direction(int val) {
    this.val = val;
  }
  
  
}
