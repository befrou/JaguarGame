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
 
  private Map<Integer, BoardPosition> pieces; /* Associates piece's id whit its current location */
  private Board board;
  
  private int eatenDogs;
  private static final int jaguarId = 12;
  
  public GameManager() {
    board = new Board();
    pieces = new HashMap<>();
    initializePieces();
    
  }
  
  
  public void startMatch() {
    /*
    do {
      
      
    } while(!(dogsWinningCondition()) && !(jaguarWinningCondition()));
    if(eatenDogs == 5) System.out.println("Player 1 - Jaguar Wins!");
      else
        System.out.println("Player 2 - Dog Wins!");
    */
  }
  
  /* Player 1 turn */
  public void player1() {
    System.out.print("Player 1 - Jaguar ");

    Direction direction = null;
    
    BoardPosition currentPosition = pieces.get(jaguarId);
    BoardPosition nextPosition;
    
    Jaguar jaguar = (Jaguar)currentPosition.getPiece();
 
    do {
      
      System.out.println("Jaguar can't perform this move.");
      
    } while((nextPosition = moveTo(jaguarId, direction)) == null);
    
    nextPosition.setPiece(jaguar);
    currentPosition.setPiece(null);
    
    pieces.replace(jaguarId, currentPosition, nextPosition);
  }
  
   /* Player 2 turn */
  public void player2(int id) {
    System.out.print("Player 2 - Dogs ");

    Direction direction = null;
    
    BoardPosition currentPosition = pieces.get(id);
    BoardPosition nextPosition;
    
    Dog dog = (Dog)currentPosition.getPiece();
 
    do {
      
      System.out.println("Dog can't perform this move.");
      
    } while((nextPosition = moveTo(id, direction)) == null);
    
    nextPosition.setPiece(dog);
    currentPosition.setPiece(null);
    
    pieces.replace(id, currentPosition, nextPosition);
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
  public BoardPosition moveTo(int id, Direction dir) {
    BoardPosition targetPosition = pieces.get(id).getAdjacentPosition(dir);
    return targetPosition;
  }
  
  /* set pieces position on its respective places */
  public void initializePieces() {
    
    ArrayList<BoardPosition> boardPositions = board.getBoardPositions();
    
    /* Maps the pieces ids to its respective positions */
    int id = 1;
    int aux = 0;
    for(int i = 0; i < 5; i++) {
      if(i == 12) {
        pieces.put(id++, boardPositions.get(i));
        continue;
      }
      pieces.put(id++, boardPositions.get(aux));
      pieces.put(id++, boardPositions.get(aux+1));
      pieces.put(id++, boardPositions.get(aux+2));
      aux+=5;
    }
    
    /* Create dogs and jaguars and put then on its respective positions */
    for(id =  1; id <= 15; id++) {
      if(id == 12) continue;
       
      pieces.get(id).setPiece(new Dog(id));
    }
    pieces.get(12).setPiece(new Jaguar(id));
  }
  
  public Direction promptPlayer2() {
       System.out.println("Which dog do you want to move:");
       int id = Integer.parseInt(new Scanner(System.in).next());
       
       if(id == 12 || id > 15) return null;
       
       Dog dog = (Dog) pieces.get(id).getPiece();
       ArrayList<Direction> availableDirections = pieces.get(id).getAvailableDirections();
       
       System.out.println("Available Directions:");
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

