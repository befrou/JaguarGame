/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameLogic;

/**
 *
 * @author bruno
 */
public enum Direction {
  Up(1),
  UpLeft(2),
  UpRight(3),
  Down(4),
  DownLeft(5),
  DownRight(6),
  Left(7),
  Right(8);
  
  
  private int val;
  
  Direction(int val) {
    this.val = val;
  }
  
  
}
