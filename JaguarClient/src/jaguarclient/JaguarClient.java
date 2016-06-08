/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarclient;

import jaguarshared.Direction;
import jaguarshared.JaguarServerInterface;
import jaguarshared.PieceType;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;


        
/**
 *
 * @author bruno
 */
public class JaguarClient {

  private JaguarServerInterface clientGame;
  private int clientId;
  private PieceType pType;
  
  public JaguarClient(String server) {
    try {
      this.clientGame = (JaguarServerInterface) Naming.lookup("//" + server + "/JaguarServerBruno");
    } catch (NotBoundException | MalformedURLException | RemoteException ex) {
      System.out.println("JaguarClient failed:");
    }
  }
  
  public void initClientMatch(String username) throws RemoteException, InterruptedException, Exception {
    int id = this.clientGame.registerPlayer(username);
    int matchState;        
    
    if(id == -2) {
      System.out.println("Server has reached maximum user capacity!");
      System.exit(0);
    } else if(id == -1) {
      System.out.println("Username is already being used. Pleas try again.");
      System.exit(0);
    }
    
    this.clientId = id;
    this.pType = this.clientGame.getUserPieceType(this.clientId);
    System.out.println("My Piece:" + this.pType);
    
    System.out.print("Waiting for match up...");
    waitForMatchUp();
    
    this.clientGame.startMatch(id);
    play();
    
    // match = clientGame.startMatch(id);    
  }  
  
  private void play() throws RemoteException, Exception {
    int matchState = 0;
    
    while(matchState == 0 || matchState == 1) {
      matchState = isMyTurn();
      switch(matchState) {
        case -1: throw new Exception("Error");
          
        case 0: sleep(700);   // not my turn
          break;
        case 1: myMove();      // my turn
          break;
        case 2: System.out.println("YOU WON!!!!!!!!!!!");
          break;
        case 3: System.out.println("YOU LOST!");
          break;
        case 4:
          break;  
        case 5: System.out.println("YOU WON BY WO!");
          break;
        case 6: System.out.println("YOU LOST BY WO!");
          break;
      }
    }
  }
  
  
  public void myMove() throws RemoteException {
    String id;
    Direction dir;
    
    int validMove = 0;
    
    while(validMove == 0) {
     
      System.out.println(this.clientGame.getBoard(this.clientId));
      if(pType == PieceType.DOGS) {
        id = chooseDog();
      } else {
        id = " J";
      }
    
      dir = chooseDirection();
      
      validMove = this.clientGame.sendMove(this.clientId, id, dir);
      if(validMove == 0) {
        System.out.println("Invalid move!!! Try again.\n");
      }
    }
    if(validMove == -1) throw new RemoteException("Error");
  }
  
  private String chooseDog() { 
    
    System.out.println("Choose the ID of the DOG you wish to move.");
    int dogId = Integer.parseInt(new Scanner(System.in).next()); 
   
    while(dogId < 1 || dogId > 14) {
      System.out.println("You have choosen an invalid ID!\n Choose again. ");
      dogId = Integer.parseInt(new Scanner(System.in).next());
    }
    String strId = (dogId < 10) ? "0"+ dogId : ""+ dogId;
    return strId;
  }
  
  private Direction chooseDirection() {
    System.out.println("0 -> RIGHT");
    System.out.println("1 -> DOWN RIGHT");
    System.out.println("2 -> DOWN");
    System.out.println("3 -> DOWN LEFT");
    System.out.println("4 -> LEFT");
    System.out.println("5 -> UP LEFT");
    System.out.println("6 -> UP");
    System.out.println("7 -> UP RIGHT");
    
    int dir = Integer.parseInt(new Scanner(System.in).next());   
    Direction direction;
    
    switch(dir) {
      case 0: direction = Direction.Right;       break;
      case 1: direction = Direction.DownRight;   break;
      case 2: direction = Direction.Down;        break;  
      case 3: direction = Direction.DownLeft;    break; 
      case 4: direction = Direction.Left;        break;       
      case 5: direction = Direction.UpLeft;      break;        
      case 6: direction = Direction.Up;          break;      
      case 7: direction = Direction.UpRight;     break;
      
      default: direction = null;  break;
    }
    
    return direction;
  }
  
  private int isMyTurn() throws RemoteException {
    return this.clientGame.isMyTurn(this.clientId);
  }
  
  private void waitForMatchUp() throws RemoteException, InterruptedException {  
    int matchState;
    
    while((matchState = this.clientGame.isVacant(this.clientId)) == 0) {
      sleep(700);
      System.out.print(".");
    }
    
    if(matchState == -2) {
      throw new RemoteException("Couldn't find a match up!");
    }
    
    System.out.println("\n");
  }
}
