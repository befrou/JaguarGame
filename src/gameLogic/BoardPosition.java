package gameLogic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import utilities.Direction;
        
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
  
  public Piece getPiece() {
    return this.piece;
  }
  
  public void setPiece(Piece piece) {
    this.piece = piece;
  }
  
  public Position getPosition() {
    return this.position;
  }
  
  public BoardPosition getAdjacentPosition(Direction direction) {
    return this.adjacentPositions.get(direction);
  }
  
  public ArrayList<BoardPosition> getAdjacentPositions() {
    ArrayList<BoardPosition> array = new ArrayList<BoardPosition>(this.adjacentPositions.values());
    return array;
  }
  public ArrayList<Direction> getAvailableDirections() {
     ArrayList<Direction> array = new ArrayList<Direction>(this.adjacentPositions.keySet());
     return array;
  }
  
  public void addAdjacentPosition(Direction direction, BoardPosition boardPosition) {
    this.adjacentPositions.put(direction, boardPosition);
  }
}
