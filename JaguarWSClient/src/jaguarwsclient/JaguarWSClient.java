/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaguarwsclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author bruno
 */
public class JaguarWSClient {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws jaguarwsclient.InterruptedException_Exception
     */
    public static void main(String[] args) throws IOException, InterruptedException_Exception {
        //executaTeste("Teste");
      
        //executaTeste("JogoDaOnca-005");
        executaTeste("GuilhermeTaschetto");
        
    }
    
    private static void executaTeste(String rad) throws IOException, InterruptedException_Exception {
        String inFile = rad+".in";
        FileInputStream is = new FileInputStream(new File(inFile));
        System.setIn(is);

        String outFile = rad+".out";
        FileWriter outWriter = new FileWriter(outFile);
        try (PrintWriter out = new PrintWriter(outWriter)) {
            Scanner leitura = new Scanner(System.in);
            int numOp = leitura.nextInt();
            for (int i=0;i<numOp;++i) {
                System.out.print("\r"+rad+": "+(i+1)+"/"+numOp);
                int op = leitura.nextInt();
                String parametros = leitura.next();
                String param[] = parametros.split(":",-1);
                switch(op) {
                    case 0:
                        if (param.length!=4)
                            erro(inFile,i+1);
                        else
                            out.println(preRegistro(param[0],Integer.parseInt(param[1]),param[2],Integer.parseInt(param[3])));
                        break;
                    case 1:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(registraJogador(param[0]));
                        break;
                    case 2:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(encerraPartida(Integer.parseInt(param[0])));
                        break;
                    case 3:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(temPartida(Integer.parseInt(param[0])));
                        break;
                    case 4:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(ehMinhaVez(Integer.parseInt(param[0])));
                        break;
                    case 5:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(obtemGrade(Integer.parseInt(param[0])));
                        break;
                    case 6:
                        if (param.length!=2)
                            erro(inFile,i+1);
                        else
                            out.println(enviaJogadaOnca(Integer.parseInt(param[0]),Integer.parseInt(param[1])));
                        break;
                    case 7:
                        if (param.length!=3)
                            erro(inFile,i+1);
                        else
                            out.println(enviaJogadaCao(Integer.parseInt(param[0]),Integer.parseInt(param[1]),Integer.parseInt(param[2])));
                        break;
                    case 8:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(obtemOponente(Integer.parseInt(param[0])));
                        break;
                    default:
                        erro(inFile,i+1);
                }
            }
            System.out.println("... terminado!");
            out.close();
            leitura.close();
        }
    }
    
  private static void erro(String arq,int operacao) {
      System.err.println("Entrada invalida: erro na operacao "+operacao+" do arquivo "+arq);
      System.exit(1);
  }

  private static Integer ehMinhaVez(java.lang.Integer id) throws InterruptedException_Exception {
    jaguarwsclient.JaguarWSServer_Service service = new jaguarwsclient.JaguarWSServer_Service();
    jaguarwsclient.JaguarWSServer port = service.getJaguarWSServerPort();
    return port.ehMinhaVez(id);
  }

  private static Integer encerraPartida(java.lang.Integer id) throws InterruptedException_Exception {
    jaguarwsclient.JaguarWSServer_Service service = new jaguarwsclient.JaguarWSServer_Service();
    jaguarwsclient.JaguarWSServer port = service.getJaguarWSServerPort();
    return port.encerraPartida(id);
  }

  private static Integer enviaJogadaCao(java.lang.Integer id, java.lang.Integer dogId, java.lang.Integer dir) throws InterruptedException_Exception {
    jaguarwsclient.JaguarWSServer_Service service = new jaguarwsclient.JaguarWSServer_Service();
    jaguarwsclient.JaguarWSServer port = service.getJaguarWSServerPort();
    return port.enviaJogadaCao(id, dogId, dir);
  }

  private static Integer enviaJogadaOnca(java.lang.Integer id, java.lang.Integer dir) throws InterruptedException_Exception {
    jaguarwsclient.JaguarWSServer_Service service = new jaguarwsclient.JaguarWSServer_Service();
    jaguarwsclient.JaguarWSServer port = service.getJaguarWSServerPort();
    return port.enviaJogadaOnca(id, dir);
  }

  private static String obtemGrade(java.lang.Integer id) throws InterruptedException_Exception {
    jaguarwsclient.JaguarWSServer_Service service = new jaguarwsclient.JaguarWSServer_Service();
    jaguarwsclient.JaguarWSServer port = service.getJaguarWSServerPort();
    return port.obtemGrade(id);
  }

  private static String obtemOponente(java.lang.Integer id) throws InterruptedException_Exception {
    jaguarwsclient.JaguarWSServer_Service service = new jaguarwsclient.JaguarWSServer_Service();
    jaguarwsclient.JaguarWSServer port = service.getJaguarWSServerPort();
    return port.obtemOponente(id);
  }

  private static Integer preRegistro(java.lang.String nome1, java.lang.Integer id1, java.lang.String nome2, java.lang.Integer id2) throws InterruptedException_Exception {
    jaguarwsclient.JaguarWSServer_Service service = new jaguarwsclient.JaguarWSServer_Service();
    jaguarwsclient.JaguarWSServer port = service.getJaguarWSServerPort();
    return port.preRegistro(nome1, id1, nome2, id2);
  }

  private static Integer registraJogador(java.lang.String nome) throws InterruptedException_Exception {
    jaguarwsclient.JaguarWSServer_Service service = new jaguarwsclient.JaguarWSServer_Service();
    jaguarwsclient.JaguarWSServer port = service.getJaguarWSServerPort();
    return port.registraJogador(nome);
  }

  private static Integer temPartida(java.lang.Integer id) throws InterruptedException_Exception {
    jaguarwsclient.JaguarWSServer_Service service = new jaguarwsclient.JaguarWSServer_Service();
    jaguarwsclient.JaguarWSServer port = service.getJaguarWSServerPort();
    return port.temPartida(id);
  }
  
  
  
}
