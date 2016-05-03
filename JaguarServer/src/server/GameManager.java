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

import utilities.Direction;

/**
 *
 * @author bruno
 */
public class GameManager {
 
  private Map<String, BoardPosition> pieces; /* Associates piece's id whit its current location */
  private Board board;
  
  private int eatenDogs;
  private static final String jaguarId = " J";
  
  public GameManager() {
    board = new Board();
    pieces = new HashMap<>();
  }
  
  
  public void startMatch() {
     while(true) {
       System.out.println(board.toString());
       player2();
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
  public void player2() {
    String id = CommandPrompt.chooseDog(pieces);
    BoardPosition currentPos = pieces.get(id);
    System.out.println("currentPos : " + currentPos.getPosition().getX() + ": " + currentPos.getPosition().getY());
    
    Direction dir = CommandPrompt.chooseDirection(currentPos);
    
    move(currentPos, dir);
  }  
  
  public void move(BoardPosition currentPos, Direction dir) {
    BoardPosition targetPos = currentPos.getAdjacentPosition(dir);
    
    Piece currentPiece = currentPos.getPiece();
    
    targetPos.setPiece(currentPiece);
    currentPos.setPiece(null);
    pieces.replace(currentPiece.getId(), targetPos);
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
    
    String strId;
    int jaguarInitPos = 12;
    
    for(int row = 0; row < Board.ROWS; row++) {
      for(int offset = 0; offset < 3; offset++) {  
        
        strId = "";
        
        if(dogId < 10) strId = "0" + dogId;
               else strId += dogId;
        
        if((arrayIndex + offset) == 12) continue;  /* this position belongs to Jaguar, so don't */ 
                        
        pieces.put(strId, array.get(arrayIndex + offset));
        pieces.get(strId).setPiece(new Dog(strId));
      
        dogId++;
      }
      arrayIndex += 5;  /* jumps to the next row */
    }
    pieces.put(jaguarId, array.get(jaguarInitPos));
    pieces.get(jaguarId).setPiece(new Jaguar(jaguarId));
  }
  
 
  
  public static void main(String args[]) {
    GameManager game = new GameManager();
    game.initializePieces();
    game.startMatch();
  }
}

