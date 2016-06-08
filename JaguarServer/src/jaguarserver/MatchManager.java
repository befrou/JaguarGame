/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarserver;

import gameLogic.Board;
import gameLogic.BoardPosition;
import jaguarshared.Direction;
import gameLogic.Dog;
import gameLogic.Jaguar;
import gameLogic.Piece;
import jaguarshared.PieceType;
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
  private PieceType turn;   /* 0 to jaguar - 1 to dogs */
  private TimeManager timer;
  
  private int eatenDogs;
  private static final String JAGUARID = " J";
  private boolean alreadyAte;
  
  public MatchManager() {
    this.board = new Board();
    this.pieces = new HashMap<>();
    this.turn = PieceType.JAGUAR;
    this.timer = new TimeManager();
    this.alreadyAte = false;
  }
  
  public void startMatch() {
    initializePieces();
  }

  public int move(String pieceId, Direction direction) {
    int flag = 0;
    
    /* 
      User didn't make a move, so return any value.
      In the next call to the method isMyTurn()
      there will be a message informing that the user lost
      by timeout
    */
    if(timer.reachedLimitToMove())
      return 1;
    
    BoardPosition currentPosition = pieces.get(pieceId);
    
    if(currentPosition == null) return -1;  //  there is no piece with the especified ID(might have been eaten or doesn't exist)
    if(pieceId.equals(JAGUARID) && this.turn == PieceType.DOGS) return -1;  //  in case the dog user tries to select the jaguar
    
    BoardPosition targetPosition = currentPosition.getAdjacentPosition(direction);
   
    Piece piece = currentPosition.getPiece();
   
    boolean validMove = canMove(currentPosition, direction, pieceId);
    
    if(validMove) {
      targetPosition.setPiece(currentPosition.getPiece());  
      currentPosition.setPiece(null);
      
      pieces.replace(pieceId, currentPosition, targetPosition);
      
      flag = 1;
    }else {
      if(piece.getId().equals(JAGUARID) && canEat(targetPosition, direction)) {
        eat(currentPosition, targetPosition, direction);
        this.eatenDogs++;
        flag = 1;
      } 
    }
    
    
    /* Move executed correctly so change turn */
    if(flag == 1) {
      this.turn = (this.turn == PieceType.JAGUAR) ? PieceType.DOGS : PieceType.JAGUAR;
    }
    
    timer.setUpTimers();  // restart timers
    
    return flag;
  }
  
  
  
  private void eat(BoardPosition currentPosition, BoardPosition victimPosition, Direction dir) {
    BoardPosition targetPosition = victimPosition.getAdjacentPosition(dir);
    
    Piece jaguar = currentPosition.getPiece();
    Piece dog = victimPosition.getPiece();
            
    targetPosition.setPiece(jaguar);
    victimPosition.setPiece(null);
    currentPosition.setPiece(null);
    
    pieces.replace(JAGUARID, currentPosition, targetPosition);
    pieces.replace(dog.getId(), victimPosition, null);
  }
  
  private boolean canMove(BoardPosition currentPosition, Direction dir, String pieceId) {
    BoardPosition targetPosition = currentPosition.getAdjacentPosition(dir);
    
    if(targetPosition.getPiece() != null)
      return false;
    
    return true;
  }
  
  private boolean canEat(BoardPosition dogPos, Direction dir) {
    BoardPosition targetPosition = dogPos.getAdjacentPosition(dir);
    
    if(targetPosition != null )
      return targetPosition.getPiece() == null;
    
    return false;
  }
  
  /* Whoose turn it is/ who won/ who lost */
  public int getMatchState(PieceType pType) {
    boolean timeout = timer.reachedLimitToMove();
    
    if(pType == this.turn) {
      /* Your timer expired => you loose by WO */
      if(timeout)
        return 6;
      
      /*  The player in the previous turn won so the current player lost*/
      if(dogsWinningCondition() || jaguarWinningCondition()) {
        return 3; 
      } else {
        return 1;
      }
    } else {
      /* Opponent timer expired => you won by WO */
      if(timeout)
        return 5;
      
      if(dogsWinningCondition() || jaguarWinningCondition()) {
        return 2;
      } else {
        return 0;
      }
    }
  }
  
  private boolean dogsWinningCondition() {
    BoardPosition jaguarPos = pieces.get(JAGUARID);
    ArrayList<Direction> directions = jaguarPos.getAvailableDirections();
   
    BoardPosition adjacentPosition;
    
    for(Direction dir : directions) {
      adjacentPosition = jaguarPos.getAdjacentPosition(dir);
      if(adjacentPosition.getPiece() == null) 
        return false;
      else if(canEat(adjacentPosition, dir)) 
        return false;
    }
   
    return true;   
  }
  
  private boolean jaguarWinningCondition() {
     return (this.eatenDogs == 5);
  }
  
  public int matchmakingTimeout() {
    boolean reachedLimit = timer.reachedLimitToFindMatch();
    
    if(reachedLimit)
      return -2;
    
    return 0;
  }
  
  public String getBoard() {
    return this.board.toString();
  }
  
   /* set pieces position on its respective places */
  private void initializePieces() {
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
