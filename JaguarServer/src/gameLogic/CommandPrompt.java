/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameLogic;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import utilities.*;

/**
 *
 * @author bruno
 */
public class CommandPrompt {
  

  public static String chooseDog(Map<String,BoardPosition> pieces) {
   
    String strId = "";
    int dogId;
    
    boolean contains;
    
    do {
      System.out.println("Choose the id of the dog you want to move.");
      dogId = Integer.parseInt(new Scanner(System.in).next());
            
      if(dogId < 10) {
        strId = "0" + dogId;
      } else {
        strId += dogId;
      }
      
      contains = pieces.containsKey(strId);
      
      if(!contains) System.out.println("There is no dog whith this ID. Choose again.");
      
    }while(!contains);

    
    
    return strId;
  }

  public static Direction chooseDirection(BoardPosition currentPos) {
    
    boolean validDirection = false;
    boolean isJaguar = "JA".equals(currentPos.getPiece().getId());
    
    ArrayList<Direction> directions = currentPos.getAvailableDirections();
    ArrayList<Direction> validDirections = new ArrayList<>();
    
    if(!isJaguar) {
      for(Direction dir : directions) {
        if(currentPos.getAdjacentPosition(dir).getPiece() == null) {
          validDirections.add(dir);
        } 
      }  
    } else {
       for(Direction dir : directions) {
        BoardPosition targetPos = currentPos.getAdjacentPosition(dir);
        
        if(targetPos.getPiece() == null) {         
          validDirections.add(dir);        
        } else {
          BoardPosition newPos = targetPos.getAdjacentPosition(dir);
          
          if(newPos != null && newPos.getPiece() == null) {
            validDirections.add(dir);
          }
        }
      }  
    }
    
    int index = 0;
    int direction;
    
    Direction choosenDir;
            
    do {
      System.out.println("Choose one of the directions  bellow to move");
           
      for(Direction dir : validDirections) {
        System.out.print(index + "- " + dir + "  ");
        index++;
      }
            
      direction = Integer.parseInt(new Scanner(System.in).next());
      choosenDir = validDirections.get(direction);
      
      if(direction >= 0 && direction <= validDirections.size()) {
        validDirection = true;
      }
      System.out.println();
      index = 0;
      
    } while(!validDirection);
   
    return choosenDir;
  }
}
