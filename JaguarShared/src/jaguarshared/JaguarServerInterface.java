/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarshared;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *
 * @author bruno
 */
public interface JaguarServerInterface extends Remote {
  
  /* returns 1 if the user is already registered, 2 if the maximun number of players has been reached */
  int registerPlayer(String username) throws  RemoteException;
  int isVacant(int userId) throws RemoteException;
  void startMatch(int userId) throws RemoteException;
  int isMyTurn(int userId) throws RemoteException;
  String getBoard(int userId) throws RemoteException;
  String getOpponent(int userId) throws RemoteException;
  int sendMove(int userId, String pieceId, Direction direction) throws RemoteException;
  PieceType getUserPieceType(int userId) throws RemoteException;
  int endMatch(int userId) throws RemoteException;
}
