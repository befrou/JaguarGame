/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author bruno
 */
public class GameManager {
 
  private final Map<String, BoardPosition> pieces; /* Associates piece's id whit its current location */
  private final Board board;
  
  private int eatenDogs;
  private static final String jaguarId = " J";
  
  public GameManager() {
    board = new Board();
    pieces = new HashMap<>();
  }
  
  
  public void startMatch() {
    String winner = "";
    eatenDogs = 4;
    
    do {
      System.out.println(board.toString());
      player1();
      System.out.println("\n\n");
      
      if(jaguarWinningCondition()) {
         winner = "Jaguar";
         break;
      }
      
      System.out.println(board.toString());
      player2();
      System.out.println("\n\n");
      
      if(dogsWinningCondition()) {
        winner = "Dogs";
        break;
      }
          
    } while(true);
    
    System.out.println(winner);
  }
  
  /* Player 1 turn */
  public void player1() {
    System.out.print("Player 1 - Jaguar ");
    
    BoardPosition currentPos = pieces.get(" J");
            
    Direction dir = CommandPrompt.chooseDirection(currentPos);
    
    BoardPosition targetPos = currentPos.getAdjacentPosition(dir);
    
    if(targetPos.getPiece() != null)
      eatDog(currentPos, targetPos, dir);
    else
      move(currentPos, dir);
  }
  
   /* Player 2 turn */
  public void player2() {
    Direction dir;
    BoardPosition currentPos;
    
    System.out.print("Player 2 - Dogs ");
    
    do {
      String id = CommandPrompt.chooseDog(pieces);
      currentPos = pieces.get(id);

      dir = CommandPrompt.chooseDirection(currentPos);
      System.out.println("Dog is Stuck!!! Choose another one! \n\n");
    } while(dir == null);
    move(currentPos, dir);
  } 
  
  public void eatDog(BoardPosition currentPos, BoardPosition victimPos, Direction dir) {
    Piece currentPiece = currentPos.getPiece();
    Piece victimPiece = victimPos.getPiece();
    
    BoardPosition newPos = victimPos.getAdjacentPosition(dir);
    
    /* New Jaguar position */
    newPos.setPiece(currentPiece);
    
    /* Previous Jaguar Position. Now there is no piece on it */
    pieces.replace(currentPiece.getId(), currentPos, newPos);
    currentPos.setPiece(null);
    
    /* Dog was eaten by the Jaguar, so take him out of the board */
    pieces.replace(victimPiece.getId(), victimPos, null);
    victimPos.setPiece(null);
    eatenDogs++;
    
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

