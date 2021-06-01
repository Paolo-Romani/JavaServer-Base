/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author paoloromani
 */
public class Main extends Thread{

    /**
     * @param args the command line arguments
     */
    
    private final static int defalutPort = 18000;
    private static ServerSocket ss;
    private static ArrayList<Socket> listaClient;
    private static boolean stato = true;

    public Main(int port) throws IOException {
        listaClient = new ArrayList<Socket>();
        ss = new ServerSocket(port);
        System.out.println("Il server è attivo all'indiizzo: "+InetAddress.getLocalHost().getHostAddress()+":"+ss.getLocalPort());
        this.start();
    }
    
    public void run() {
        Socket s;
        while(stato) {
            System.out.println("In attesa di una connessione");
            try {
                s = ss.accept();
                System.out.println("Connessione accettata");
                System.out.println("con: "+s.getInetAddress().getHostAddress()+":"+s.getPort());
                listaClient.add(s);
                stampa();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            stato = false; 
        }
        close();
    }
    private void close(){
        for(Socket s:listaClient){
            try {
                s.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            ss.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void stampa() {
        System.out.println("Connessione instaurata");
        for(Socket s:listaClient){
            System.out.println("ip: "+s.getInetAddress().getHostAddress()+":"+s.getPort());
        }

    }
    
    public static void main(String[] args) throws IOException {
        if(args.length>0)  
            new Main(Integer.parseInt(args[0]));
        else
            new Main(defalutPort);
        
        
    }
    
}
