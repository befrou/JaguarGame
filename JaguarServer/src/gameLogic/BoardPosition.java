package gameLogic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
        
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bruno
 */
public class BoardPosition {
  
  protected Piece piece;
  protected Map<Direction, BoardPosition> adjacentPositions;
  protected final Position position;
  
  
  public BoardPosition(Piece piece, Position position) {
    adjacentPositions = new HashMap<>();
    
    this.piece = piece;
    this.position = position;
  }
  
  /* returns piece that is occupying this board position */
  public Piece getPiece() {
    return this.piece;
  }
  
  /* set the piece that is occupying this board position */
  public void setPiece(Piece piece) {
    this.piece = piece;
  }
  
  /* return an object Position whith coordinates x and y */
  public Position getPosition() {
    return this.position;
  }
  
  /* returns an adjacent board position pointed by the direction */
  public BoardPosition getAdjacentPosition(Direction direction) {
    if(this.adjacentPositions.containsKey(direction))
      return this.adjacentPositions.get(direction);
    
    return null;  
  }
  
  /* returns an array with all adjacent board positions */
  public ArrayList<BoardPosition> getAdjacentPositions() {
    ArrayList<BoardPosition> array = new ArrayList();
    
    for(BoardPosition pos : adjacentPositions.values()) {
      array.add(pos);
    }
    return array;
  }
  
  /* returns an array whith all possible directions from this board position */
  public ArrayList<Direction> getAvailableDirections() {
    ArrayList<Direction> array = new ArrayList<>();
     
    for(Direction dir : adjacentPositions.keySet()) {
      array.add(dir);
    }
     return array;
  }
  
  /* verify if the direction passed by parameter is available */
  public boolean availableDirection(Direction direction) {
    return adjacentPositions.containsKey(direction);
  }
  
  /* add a set key, value to the hash map */
  public void addAdjacentPosition(Direction direction, BoardPosition boardPosition) {
    this.adjacentPositions.put(direction, boardPosition);
  }
}
