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
public class Board {
 
  private ArrayList<BoardPosition> boardPositions;  /* Stores all board positions */
  private Map<Position, BoardPosition> positions; /* Using this Map just to print */
  
  public static final int ROWS = 5;
  public static final int COLUMNS = 7;
  
  
  public Board() {
    boardPositions = new ArrayList<BoardPosition>();
    positions = new HashMap<>();
  
    
    /* initialize board positions without the pieces */
    for(int x = 0; x < ROWS; x++) {
      for(int y = 0; y < COLUMNS - 2; y++) {
        Position pos = new Position(x, y);
        BoardPosition bPos = new BoardPosition(null, pos);
        
        boardPositions.add(bPos);             
        positions.put(pos, bPos);        
      }
    }
 
    /* missing positions */
    boardPositions.add(new BoardPosition(null, new Position(1, 5))); //25
    boardPositions.add(new BoardPosition(null, new Position(2, 5))); //26
    boardPositions.add(new BoardPosition(null, new Position(3, 5))); //27
    
    boardPositions.add(new BoardPosition(null, new Position(0, 6))); //28
    boardPositions.add(new BoardPosition(null, new Position(2, 6))); //29
    boardPositions.add(new BoardPosition(null, new Position(4, 6))); //30
    
    positions.put(new Position(1,5), boardPositions.get(25));
    positions.put(new Position(2,5), boardPositions.get(26));
    positions.put(new Position(3,5), boardPositions.get(27));
    
    positions.put(new Position(0,6), boardPositions.get(28));
    positions.put(new Position(2,6), boardPositions.get(29));
    positions.put(new Position(4,6), boardPositions.get(30));
    
    /* set adcjacent positions for each BoardPosition */
    boardPositions.get(0).addAdjacentPosition(Direction.Right, boardPositions.get(1));
    boardPositions.get(0).addAdjacentPosition(Direction.Down, boardPositions.get(5));
    boardPositions.get(0).addAdjacentPosition(Direction.DownRight, boardPositions.get(6));
 
    boardPositions.get(1).addAdjacentPosition(Direction.Left, boardPositions.get(0));
    boardPositions.get(1).addAdjacentPosition(Direction.Right, boardPositions.get(2));
    boardPositions.get(1).addAdjacentPosition(Direction.Down, boardPositions.get(6));
    
    boardPositions.get(2).addAdjacentPosition(Direction.Left, boardPositions.get(1));
    boardPositions.get(2).addAdjacentPosition(Direction.Right, boardPositions.get(3));
    boardPositions.get(2).addAdjacentPosition(Direction.DownLeft, boardPositions.get(6));
    boardPositions.get(2).addAdjacentPosition(Direction.Down, boardPositions.get(7));
    boardPositions.get(2).addAdjacentPosition(Direction.DownRight, boardPositions.get(8));
   
    boardPositions.get(3).addAdjacentPosition(Direction.Left, boardPositions.get(2));
    boardPositions.get(3).addAdjacentPosition(Direction.Right, boardPositions.get(4));
    boardPositions.get(3).addAdjacentPosition(Direction.Down, boardPositions.get(8));
    
    boardPositions.get(4).addAdjacentPosition(Direction.Left, boardPositions.get(3));
    boardPositions.get(4).addAdjacentPosition(Direction.DownLeft, boardPositions.get(8));
    boardPositions.get(4).addAdjacentPosition(Direction.Down, boardPositions.get(9));
    
    boardPositions.get(5).addAdjacentPosition(Direction.Up, boardPositions.get(0));
    boardPositions.get(5).addAdjacentPosition(Direction.Right, boardPositions.get(6));
    boardPositions.get(5).addAdjacentPosition(Direction.Down, boardPositions.get(10));
    
    boardPositions.get(6).addAdjacentPosition(Direction.Up, boardPositions.get(1));
    boardPositions.get(6).addAdjacentPosition(Direction.UpRight, boardPositions.get(2));
    boardPositions.get(6).addAdjacentPosition(Direction.Right, boardPositions.get(7));
    boardPositions.get(6).addAdjacentPosition(Direction.DownRight, boardPositions.get(12));
    boardPositions.get(6).addAdjacentPosition(Direction.Down, boardPositions.get(11));
    boardPositions.get(6).addAdjacentPosition(Direction.DownLeft, boardPositions.get(10));
    boardPositions.get(6).addAdjacentPosition(Direction.Left, boardPositions.get(5));
    boardPositions.get(6).addAdjacentPosition(Direction.UpLeft, boardPositions.get(0));
    
    boardPositions.get(7).addAdjacentPosition(Direction.Up, boardPositions.get(2));
    boardPositions.get(7).addAdjacentPosition(Direction.Right, boardPositions.get(8));
    boardPositions.get(7).addAdjacentPosition(Direction.Down, boardPositions.get(12));
    boardPositions.get(7).addAdjacentPosition(Direction.Left, boardPositions.get(6));
    
    boardPositions.get(8).addAdjacentPosition(Direction.Up, boardPositions.get(3));
    boardPositions.get(8).addAdjacentPosition(Direction.UpRight, boardPositions.get(4));
    boardPositions.get(8).addAdjacentPosition(Direction.Right, boardPositions.get(9));
    boardPositions.get(8).addAdjacentPosition(Direction.DownRight, boardPositions.get(14));
    boardPositions.get(8).addAdjacentPosition(Direction.Down, boardPositions.get(13));
    boardPositions.get(8).addAdjacentPosition(Direction.DownLeft, boardPositions.get(12));
    boardPositions.get(8).addAdjacentPosition(Direction.Left, boardPositions.get(7));
    boardPositions.get(8).addAdjacentPosition(Direction.UpLeft, boardPositions.get(2));
    
   
    boardPositions.get(9).addAdjacentPosition(Direction.Up, boardPositions.get(4));
    boardPositions.get(9).addAdjacentPosition(Direction.Left, boardPositions.get(8));
    boardPositions.get(9).addAdjacentPosition(Direction.Down, boardPositions.get(14));
    
    boardPositions.get(10).addAdjacentPosition(Direction.Up, boardPositions.get(5));
    boardPositions.get(10).addAdjacentPosition(Direction.UpRight, boardPositions.get(6));
    boardPositions.get(10).addAdjacentPosition(Direction.Right, boardPositions.get(11));
    boardPositions.get(10).addAdjacentPosition(Direction.DownRight, boardPositions.get(16));
    boardPositions.get(10).addAdjacentPosition(Direction.Down, boardPositions.get(15));
  
    boardPositions.get(11).addAdjacentPosition(Direction.Up, boardPositions.get(6));
    boardPositions.get(11).addAdjacentPosition(Direction.Right, boardPositions.get(12));
    boardPositions.get(11).addAdjacentPosition(Direction.Down, boardPositions.get(16));
    boardPositions.get(11).addAdjacentPosition(Direction.Left, boardPositions.get(10));
    
    
    boardPositions.get(12).addAdjacentPosition(Direction.Up, boardPositions.get(7));
    boardPositions.get(12).addAdjacentPosition(Direction.UpRight, boardPositions.get(8));
    boardPositions.get(12).addAdjacentPosition(Direction.Right, boardPositions.get(13));
    boardPositions.get(12).addAdjacentPosition(Direction.DownRight, boardPositions.get(18));
    boardPositions.get(12).addAdjacentPosition(Direction.Down, boardPositions.get(17));
    boardPositions.get(12).addAdjacentPosition(Direction.DownLeft, boardPositions.get(16));
    boardPositions.get(12).addAdjacentPosition(Direction.Left, boardPositions.get(11));
    boardPositions.get(12).addAdjacentPosition(Direction.UpLeft, boardPositions.get(6));
    
    boardPositions.get(13).addAdjacentPosition(Direction.Up, boardPositions.get(8));
    boardPositions.get(13).addAdjacentPosition(Direction.Right, boardPositions.get(14));
    boardPositions.get(13).addAdjacentPosition(Direction.Down, boardPositions.get(18));
    boardPositions.get(13).addAdjacentPosition(Direction.Left, boardPositions.get(12));
    
    boardPositions.get(14).addAdjacentPosition(Direction.Up, boardPositions.get(9));
    boardPositions.get(14).addAdjacentPosition(Direction.UpRight, boardPositions.get(25));
    boardPositions.get(14).addAdjacentPosition(Direction.Right, boardPositions.get(26));
    boardPositions.get(14).addAdjacentPosition(Direction.DownRight, boardPositions.get(27));
    boardPositions.get(14).addAdjacentPosition(Direction.Down, boardPositions.get(19));
    boardPositions.get(14).addAdjacentPosition(Direction.DownLeft, boardPositions.get(18));
    boardPositions.get(14).addAdjacentPosition(Direction.Left, boardPositions.get(13));
    boardPositions.get(14).addAdjacentPosition(Direction.UpLeft, boardPositions.get(8));
    
    boardPositions.get(15).addAdjacentPosition(Direction.Up, boardPositions.get(10));
    boardPositions.get(15).addAdjacentPosition(Direction.Right, boardPositions.get(16));
    boardPositions.get(15).addAdjacentPosition(Direction.Down, boardPositions.get((20)));
    
    boardPositions.get(16).addAdjacentPosition(Direction.Up, boardPositions.get(11));
    boardPositions.get(16).addAdjacentPosition(Direction.UpRight, boardPositions.get(12));
    boardPositions.get(16).addAdjacentPosition(Direction.Right, boardPositions.get(17));
    boardPositions.get(16).addAdjacentPosition(Direction.DownRight, boardPositions.get(22));
    boardPositions.get(16).addAdjacentPosition(Direction.Down, boardPositions.get(21));
    boardPositions.get(16).addAdjacentPosition(Direction.DownLeft, boardPositions.get(20));
    boardPositions.get(16).addAdjacentPosition(Direction.Left, boardPositions.get(15));
    boardPositions.get(16).addAdjacentPosition(Direction.UpLeft, boardPositions.get(10));
   
    boardPositions.get(17).addAdjacentPosition(Direction.Up, boardPositions.get(12));
    boardPositions.get(17).addAdjacentPosition(Direction.Right, boardPositions.get(18));
    boardPositions.get(17).addAdjacentPosition(Direction.Down, boardPositions.get(22));
    boardPositions.get(17).addAdjacentPosition(Direction.Left, boardPositions.get(16));
    
    boardPositions.get(18).addAdjacentPosition(Direction.Up, boardPositions.get(13));
    boardPositions.get(18).addAdjacentPosition(Direction.UpRight, boardPositions.get(14));
    boardPositions.get(18).addAdjacentPosition(Direction.Right, boardPositions.get(19));
    boardPositions.get(18).addAdjacentPosition(Direction.DownRight, boardPositions.get(24));
    boardPositions.get(18).addAdjacentPosition(Direction.Down, boardPositions.get(23));
    boardPositions.get(18).addAdjacentPosition(Direction.DownLeft, boardPositions.get(22));
    boardPositions.get(18).addAdjacentPosition(Direction.Left, boardPositions.get(17));
    boardPositions.get(18).addAdjacentPosition(Direction.UpLeft, boardPositions.get(12));
    
    boardPositions.get(19).addAdjacentPosition(Direction.Up, boardPositions.get(14));
    boardPositions.get(19).addAdjacentPosition(Direction.Left, boardPositions.get(18));
    boardPositions.get(19).addAdjacentPosition(Direction.Down, boardPositions.get((24)));
    
    boardPositions.get(20).addAdjacentPosition(Direction.Up, boardPositions.get(15));
    boardPositions.get(20).addAdjacentPosition(Direction.UpRight, boardPositions.get(16));
    boardPositions.get(20).addAdjacentPosition(Direction.Right, boardPositions.get((21)));
    
    boardPositions.get(21).addAdjacentPosition(Direction.Up, boardPositions.get(16));
    boardPositions.get(21).addAdjacentPosition(Direction.Right, boardPositions.get(22));
    boardPositions.get(21).addAdjacentPosition(Direction.Down, boardPositions.get((20)));
    
    boardPositions.get(22).addAdjacentPosition(Direction.Up, boardPositions.get(17));
    boardPositions.get(22).addAdjacentPosition(Direction.UpRight, boardPositions.get(18));
    boardPositions.get(22).addAdjacentPosition(Direction.Right, boardPositions.get((23)));
    boardPositions.get(22).addAdjacentPosition(Direction.Left, boardPositions.get(21));
    boardPositions.get(22).addAdjacentPosition(Direction.UpLeft, boardPositions.get(16));
    
    
    boardPositions.get(23).addAdjacentPosition(Direction.Up, boardPositions.get(18));
    boardPositions.get(23).addAdjacentPosition(Direction.Right, boardPositions.get(24));
    boardPositions.get(23).addAdjacentPosition(Direction.Left, boardPositions.get((22)));
    
    boardPositions.get(24).addAdjacentPosition(Direction.Up, boardPositions.get(19));
    boardPositions.get(24).addAdjacentPosition(Direction.Left, boardPositions.get(23));
    boardPositions.get(24).addAdjacentPosition(Direction.UpLeft, boardPositions.get((18)));
    
    boardPositions.get(25).addAdjacentPosition(Direction.UpRight, boardPositions.get(28));
    boardPositions.get(25).addAdjacentPosition(Direction.Down, boardPositions.get(26));
    boardPositions.get(25).addAdjacentPosition(Direction.DownLeft, boardPositions.get((14)));
    
    boardPositions.get(26).addAdjacentPosition(Direction.Up, boardPositions.get(25));
    boardPositions.get(26).addAdjacentPosition(Direction.Right, boardPositions.get(29));
    boardPositions.get(26).addAdjacentPosition(Direction.Down, boardPositions.get((27)));
    boardPositions.get(26).addAdjacentPosition(Direction.Left, boardPositions.get(14));
    
    
    boardPositions.get(27).addAdjacentPosition(Direction.Up, boardPositions.get(25));
    boardPositions.get(27).addAdjacentPosition(Direction.DownRight, boardPositions.get(30));
    boardPositions.get(27).addAdjacentPosition(Direction.UpLeft, boardPositions.get((14)));
    
    boardPositions.get(28).addAdjacentPosition(Direction.Down, boardPositions.get(29));
    boardPositions.get(28).addAdjacentPosition(Direction.DownLeft, boardPositions.get(25));
    
    boardPositions.get(29).addAdjacentPosition(Direction.Up, boardPositions.get(28));
    boardPositions.get(29).addAdjacentPosition(Direction.Down, boardPositions.get(30));
    boardPositions.get(29).addAdjacentPosition(Direction.Left, boardPositions.get((26)));
    
    boardPositions.get(30).addAdjacentPosition(Direction.Up, boardPositions.get(29));
    boardPositions.get(30).addAdjacentPosition(Direction.UpLeft, boardPositions.get(27));
  }
  
  /* return all board positions */
  public ArrayList<BoardPosition> getBoardPositions() {
    return boardPositions;
  }
 
  public String toString() {
    
    String output = "";
    int row = 0, column = 0;
    
    for (int x = 0; x <= Board.ROWS - 1; x++) {
      for (int y = 0; y < Board.COLUMNS; y++) {
        BoardPosition aux = positions.get(new Position(x, y));
        Piece p;
        //System.out.println("x:"+x + " y:"+y + "aux" + aux);

        if(aux != null) {
         p = aux.getPiece();
         if(p == null) {     
             output += "..";
         }
         else{
            output += "" + p.getId() + "";
         }
         if (y < 4 || (x == 2 && y < 6)) {
          output += "--";
         }       
         else {
              output += "  ";
          } 
      }
      else if (y == 5 && (x == 0 || x == 4)) {
             output += "    ";
      }
        
        if (y == 6 && (x == 1 || x == 3)) output += " |";
    }
      if (x == 0) 
       output += "\n | \\ | / | \\ | / |     / |\n";
      else if (x == 1)
        output += "\n | / | \\ | / | \\ | / |   |\n";
      else if (x == 2)
        output += "\n | \\ | / | \\ | / | \\ |   |\n";
      else if (x == 3)
        output += "\n | / | \\ | / | \\ |     \\ |\n";
    }
      
      return output;       
  
  }
  

}