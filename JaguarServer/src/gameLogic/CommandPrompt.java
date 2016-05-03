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
    
    ArrayList<Direction> directions = currentPos.getAvailableDirections();
    ArrayList<Direction> validDirections = new ArrayList<Direction>();
    
    for(Direction dir : directions) {
            //System.out.println("possibleDir " + dir);
        if(currentPos.getAdjacentPosition(dir).getPiece() == null) {
          validDirections.add(dir);
        }
        else{
          //System.out.println(dir+ " Not null" + currentPos.getAdjacentPosition(dir).getPiece().getId());
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
    System.out.println("choosenDir" + choosenDir);
    return choosenDir;
  }
  
}
