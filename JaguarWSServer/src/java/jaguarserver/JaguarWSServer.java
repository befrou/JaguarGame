/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarserver;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import jaguarshared.Direction;
import java.rmi.RemoteException;

/**
 *
 * @author bruno
 */
@WebService(serviceName = "JaguarWSServer")
public class JaguarWSServer {

  private final AlocationManager alocManager;
  
  public JaguarWSServer() throws RemoteException {  
    this.alocManager = AlocationManager.getInstance();
    this.alocManager.init();
  }
  
  /**
   * Operação de Web service
   */
  @WebMethod(operationName = "preRegistro")
  public Integer preRegistro(@WebParam(name = "nome1") String nome1, @WebParam(name = "id1") Integer id1, @WebParam(name = "nome2") String nome2, @WebParam(name = "id2") Integer id2) throws InterruptedException {  
    //TODO write your implementation code here:
    return this.alocManager.preRegister(nome1, id1, nome2, id2);
  }

  /**
   * Operação de Web service
   */
  @WebMethod(operationName = "encerraPartida")
  public Integer encerraPartida(@WebParam(name = "id") Integer id) throws InterruptedException {
    //TODO write your implementation code here:
     return this.alocManager.endMatch(id);
  }

  /**
   * Operação de Web service
   */
  @WebMethod(operationName = "temPartida")
  public Integer temPartida(@WebParam(name = "id") Integer id) throws InterruptedException {
    int state = 0;  
    state = this.alocManager.userMatchAvailable(id); 
    
    return state;
  }

  /**
   * Operação de Web service
   */
  @WebMethod(operationName = "ehMinhaVez")
  public Integer ehMinhaVez(@WebParam(name = "id") Integer id) throws InterruptedException {
    return this.alocManager.getMatchState(id);
    

  }

  /**
   * Operação de Web service
   */
  @WebMethod(operationName = "obtemGrade")
  public String obtemGrade(@WebParam(name = "id") Integer id) throws InterruptedException {
    Match match;
    String board = "";
    
    if((match = this.alocManager.getUserMatch(id)) != null)  board = match.getBoard();  
    
    return board;
  }

  /**
   * Operação de Web service
   */
  @WebMethod(operationName = "enviaJogadaOnca")
  public Integer enviaJogadaOnca(@WebParam(name = "id") Integer id, @WebParam(name = "dir") Integer dir) throws InterruptedException {

    Direction[] dirs = Direction.values();
    if(dir < 0 || dir > 7)  return -5;
    return this.alocManager.sendJaguarMove(id, dirs[dir]);
  }


  /**
   * Operação de Web service
   */
  @WebMethod(operationName = "obtemOponente")
  public String obtemOponente(@WebParam(name = "id") Integer id) throws InterruptedException {
    String opponent = null;
    opponent = this.alocManager.getOpponentName(id);
      
    return opponent;
  }

  /**
   * Operação de Web service
   */
  @WebMethod(operationName = "enviaJogadaCao")
  public Integer enviaJogadaCao(@WebParam(name = "id") Integer id, @WebParam(name = "dogId") Integer dogId, @WebParam(name = "dir") Integer dir) throws InterruptedException {
    int situation = 0;
     
    Direction[] dirs = Direction.values();
    if(dir < 0 || dir > 7)  return -5;
    
    String strId = (dogId < 10)? "0"+dogId : ""+dogId; 
   
    return this.alocManager.sendDogMove(id, strId, dirs[dir]);
  }

  /**
   * Operação de Web service
   */
  @WebMethod(operationName = "registraJogador")
  public Integer registraJogador(@WebParam(name = "nome") String nome) throws InterruptedException {
    //TODO write your implementation code here:
    return this.alocManager.registerPlayer(nome);
  }
}
