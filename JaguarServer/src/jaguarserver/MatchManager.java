/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarserver;

import gameLogic.Board;
import gameLogic.BoardPosition;
import gameLogic.Direction;
import gameLogic.Dog;
import gameLogic.Jaguar;
import gameLogic.PieceType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author bruno
 */
public class MatchManager {
  private final Map<String, BoardPosition> pieces; /* Associates piece's id whit its current location */
  private final Board board;
  private PieceType pType;
  
  private int eatenDogs;
  private static final String JAGUARID = " J";
  
  public MatchManager() {
    board = new Board();
    pieces = new HashMap<>();
  }
  
  public void startMatch() {
    
  }
  
  public int isMyTurn(PieceType type) {
     
    int situation = 0;  /* 0 NOT MYTURN - 1 MYTURN - 2 WIN - 3 LOOSE - 5 WO WINNER - 6 WO LOOSER*/
    
    if(jaguarWinningCondition()) {
      situation = (type == this.pType) ?  2 : 3;
      
    } else if(dogsWinningCondition()) {    
      situation = (type == this.pType) ?  2 : 3;
      
    }
     
    if(this.pType == type) situation = 1;
    
    return situation;
  }
  
  private boolean dogsWinningCondition() {
    BoardPosition jaguarPos = pieces.get(JAGUARID);
    ArrayList<Direction> directions = jaguarPos.getAvailableDirections();
   
    BoardPosition adjacentPosition;
    
    for(Direction dir : directions) {
      if((adjacentPosition = jaguarPos.getAdjacentPosition(dir)) == null) 
        return false;
      else if(canEat(adjacentPosition, dir)) 
        return false;
    }
    
    return true;   
  }
  
  private boolean jaguarWinningCondition() {
     return (this.eatenDogs == 5);
  }
  
  private boolean canEat(BoardPosition dogPos, Direction dir) {
    BoardPosition targetPosition = dogPos.getAdjacentPosition(dir);
     
    return targetPosition.getPiece() == null;
  }
  
  public String getBoard() {
    return this.board.toString();
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
    pieces.put(JAGUARID, array.get(jaguarInitPos));
    pieces.get(JAGUARID).setPiece(new Jaguar(JAGUARID));
  }  
}
