/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import gameLogic.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import utilities.Direction;

/**
 *
 * @author bruno
 */
public class GameManager {
 
  private Map<String, BoardPosition> pieces; /* Associates piece's id whit its current location */
  private Board board;
  
  private int eatenDogs;
  private static final String jaguarId = "JA";
  
  public GameManager() {
    board = new Board();
    pieces = new HashMap<>();
    initializePieces();    
  }
  
  
  public void startMatch() {
     while(true) {
     
     }
  }
  
  /* Player 1 turn */
  public void player1() {
    System.out.print("Player 1 - Jaguar ");

    Direction direction = null;
    
    BoardPosition currentPosition = pieces.get(jaguarId);
    BoardPosition nextPosition;
    
    Jaguar jaguar = (Jaguar)currentPosition.getPiece();
 
    do {
      System.out.println("Choose an action: (1 - Move) (2 - Eat)");
    
      
      nextPosition = moveTo(jaguarId, direction);
      System.out.println("Jaguar can't perform this move.");
      
    } while((nextPosition = moveTo(jaguarId, direction)) == null);
    
    nextPosition.setPiece(jaguar);
    currentPosition.setPiece(null);
   
    pieces.replace(jaguarId, currentPosition, nextPosition);
  }
  
   /* Player 2 turn */
  public void player2(int id) {
    System.out.print("Player 2 - Dogs ");

    Direction direction;
    String strId;
    
    if(id < 10) {
      strId = "0" + id;
    } else {
      strId = "" + id;
    }
    
    BoardPosition currentPosition = pieces.get(strId);
    BoardPosition nextPosition;
    
    Dog dog = (Dog)currentPosition.getPiece();
 
    do {
      direction = promptPlayer2();
      
      if(direction == null) System.out.println("Dog can't perform this move.");
      
    } while((nextPosition = moveTo(strId, direction)) == null);
    
    nextPosition.setPiece(dog);
    currentPosition.setPiece(null);
    
    pieces.replace(strId, currentPosition, nextPosition);
  }  
  
  
  
  /* Verify if Dogs - Player 2 Won */
  public boolean dogsWinningCondition() {
    BoardPosition jaguarPos = pieces.get(jaguarId);
    ArrayList<BoardPosition> adjacentPositions = jaguarPos.getAdjacentPositions();
   
    for(BoardPosition pos : adjacentPositions) {
      if(pos.getPiece() == null) return false;
    }  
    return true;
  }
  
  /* Verify if Jaguar - Player1 Won */
  public boolean jaguarWinningCondition() {
    return eatenDogs == 5;
  }
  
 
  /* Moves a piece */
  public BoardPosition moveTo(String id, Direction dir) {
    BoardPosition targetPosition = pieces.get(id).getAdjacentPosition(dir);
    return targetPosition;
  }
  
  /* set pieces position on its respective places */
  public void initializePieces() {
    ArrayList<BoardPosition> array = board.getBoardPositions();
    int dogId = 1;
    int arrayIndex = 0;
    
    String strId = "";
    int jaguarInitPos = 12;
    
    for(int row = 0; row < Board.ROWS; row++) {
      for(int offset = 0; offset < 3; offset++) {  
        
        strId = "";
        
        if(dogId < 10) strId = "0" + dogId;
               else strId += dogId;
        
        if((arrayIndex + offset) == 12) continue;  /* this position belongs to Jaguar, so don' */ 
                        
        pieces.put(strId, array.get(arrayIndex + offset));
        pieces.get(strId).setPiece(new Dog(strId));
      
        dogId++;
      }
      arrayIndex += 5;  /* jumps to the next row */
    }
    pieces.put(jaguarId, array.get(jaguarInitPos));
    pieces.get(jaguarId).setPiece(new Jaguar(jaguarId));
  }
  
  public Direction promptPlayer1() {
    System.out.println("Type 1 to move or 2 to eat");
    int action = Integer.parseInt(new Scanner(System.in).next());
    
    if(action == 1) {
      
    }
    
    return null;
  }
  
  public Direction promptPlayer2() {
       System.out.println("Which dog do you want to move:");
       int id = Integer.parseInt(new Scanner(System.in).next());
       
       String strId;
       // if(id == 12 || id > 15) return null;
       if(id < 10) {
        strId = "0" + id;
       } else {
        strId = "" + id;
       }
       
       
      // Dog dog = (Dog) pieces.get(strId).getPiece();
       ArrayList<Direction> availableDirections = pieces.get(strId).getAvailableDirections();
       
       System.out.print("Available Directions: ");
       for(Direction dir : availableDirections) {
         System.out.print(dir + " ");
       }
       
       System.out.println("Choose direction:");
       int aux = Integer.parseInt(new Scanner(System.in).next());
       Direction dir = null; 
       
       switch(aux) {
         case 1: dir = Direction.Up;
          break;
         case 2: dir =  Direction.UpLeft;
          break;
         case 3: dir = Direction.UpRight;
          break;
         case 4: dir =  Direction.Down;
          break;
         case 5: dir = Direction.DownLeft;
          break;
         case 6: dir = Direction.DownRight;
          break;
         case 7: dir = Direction.Left;
          break;
         case 8: dir = Direction.Right;
          break;
       }
       return dir;
  }
  
  public static void main(String args[]) {
    GameManager game = new GameManager();
    System.out.println(game.board.toString());
  }
}

