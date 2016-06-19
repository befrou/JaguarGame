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
  private boolean waitingForMatchUp;
  
  private int eatenDogs;
  private static final String JAGUARID = " J";
  private boolean moveAgain;
  
  public MatchManager() {
    this.board = new Board();
    this.pieces = new HashMap<>();
    this.turn = PieceType.JAGUAR;
    this.timer = new TimeManager();
    this.moveAgain = this.waitingForMatchUp = false;
   
  }
  
  public PieceType geTurn() {
    return this.turn;
  }
  
  public void startMatch() {
    initializePieces();
  }
  
  public void setWaitingForMatchUp(boolean waiting) {
    this.waitingForMatchUp = waiting;
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
      return 2;
    
    BoardPosition currentPosition = pieces.get(pieceId);
     
    if(currentPosition == null) return -1;  //  There is no piece with the especified ID(might have been eaten or doesn't exist)
    
    
    BoardPosition targetPosition = currentPosition.getAdjacentPosition(direction);
    
    Piece piece = currentPosition.getPiece();
   
    boolean validMove = canMove(currentPosition, direction, pieceId);

    String id = piece.getId();
    boolean isJaguar = id.equals(JAGUARID);
    
  
    if(validMove) {
      targetPosition.setPiece(currentPosition.getPiece());  
      currentPosition.setPiece(null);

      pieces.replace(pieceId, currentPosition, targetPosition);
      this.moveAgain = false;
      flag = 1;
    }else {
      
      if(isJaguar && canEat(targetPosition, direction)) {         
        eat(currentPosition, targetPosition, direction);

        this.eatenDogs++;
        flag = 1;
        this.moveAgain = !jaguarWinningCondition();
      } else {
        flag = 0;
      }
    }
    
    /* Move executed correctly so change turn */
    if(flag == 1) {
      if (!this.moveAgain) {
        this.turn = (this.turn == PieceType.JAGUAR) ? PieceType.DOGS : PieceType.JAGUAR;
      }
    }
    
    timer.setUpTimers();  // restart timers
    
    return flag;
  }
  
  private boolean canEat(BoardPosition victimPosition, Direction dir) {
    if(victimPosition == null || victimPosition.getPiece() == null) return false;
    
    BoardPosition targetPosition; // Jaguar position after eating DOG
    if((targetPosition = victimPosition.getAdjacentPosition(dir)) != null ) {
      return targetPosition.getPiece() == null;
    }
    return false;
  }
 
  private void eat(BoardPosition currentPosition, BoardPosition victimPosition, Direction dir) {
    BoardPosition targetPosition = victimPosition.getAdjacentPosition(dir); // Position of the Jaguar after eating a dog
    
    Piece jaguar = currentPosition.getPiece(); 
    Piece dog = victimPosition.getPiece();      
            
    targetPosition.setPiece(jaguar);  // Setting Jaguar piece on new Position
    victimPosition.setPiece(null);    // Setting the dog piece as null because he was eaten
    currentPosition.setPiece(null);   // Setting preview Jaguar position to null
    
    pieces.replace(JAGUARID, currentPosition, targetPosition);  //  Mapping Jaguar ID to new Position
    pieces.replace(dog.getId(), victimPosition, null);          //  Removing map to the dog ID since he has been eaten

  }
  
  private boolean canMove(BoardPosition currentPosition, Direction dir, String pieceId) {
    BoardPosition targetPosition = currentPosition.getAdjacentPosition(dir);
    
    if(targetPosition != null)
      return targetPosition.getPiece() == null;
    
    return false;
  }
  
  /* Whoose turn it is/ who won/ who lost */
  public int getMatchState(PieceType pType) {
    boolean timeout = timer.reachedLimitToMove();
    
    boolean myTurn = pType == this.turn;
    
    if(myTurn) {
      // Your timer expired => you loose by WO 
      if(timeout)
        return 6;
      
      //  The player in the previous turn won so the current player lost
      if((dogsWinningCondition() || jaguarWinningCondition())) {
        return 3; 
      } else {
        return 1;
      }
    } else {
      // Opponent timer expired => you won by WO 
      if(timeout)
        return 5;
      
      if((dogsWinningCondition() || jaguarWinningCondition())) {
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
    if(!this.waitingForMatchUp) {
      this.waitingForMatchUp = true;
      timer.setUpTimers();
    }
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
    int dogId = 0;
    
    String strId;
    int jaguarInitPos = 12;
    int arrayIndex;
    
    for(int col = Board.COLUMNS - 5; col >=0; col--) {
     
      for(int row = 0; row < Board.ROWS; row++) {
        arrayIndex = (row * 5) + col;
        strId = "";
       
        if(dogId < 10) strId = "0" + dogId;
               else strId += dogId;
        
        if(arrayIndex == jaguarInitPos)  continue;
        
        pieces.put(strId, array.get(arrayIndex));
        pieces.get(strId).setPiece(new Dog(strId));
        
        dogId++;
      }
    }
    
    pieces.put(JAGUARID, array.get(jaguarInitPos));
    pieces.get(JAGUARID).setPiece(new Jaguar(JAGUARID));
  
  }
  
}
