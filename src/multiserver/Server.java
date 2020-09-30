/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jfcosta
 */
public class Server {
    
    private static ServerSocket serverSocket;
    private static Set connections = new LinkedHashSet();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // Cria um servidor escutando na porta 4444
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.out.println("Could not listen on port: 4444");
            System.exit(-1);
        }
        
        
        boolean running = true;
        // Loop infinito esperando conexões
        while(running){
            System.out.println("Aguardando conexões...\n");
            try {
                ClientSocket clientSocket = new ClientSocket(serverSocket.accept());
                System.out.println(clientSocket.isConnected());
                boolean ok = connections.add(clientSocket);
                if (ok) { System.out.println("Conexão aceita!\n"); }
                Thread t = new Thread(clientSocket);
                t.start();

            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, "Erro ao aceitar a conexão.", ex);
            }
            System.out.println("Existem " + connections.size() + " conexões ativas.\n");
        }
        
    }
    
    @Override
    @SuppressWarnings("FinalizeDeclaration")
    protected void finalize(){
        //Objects created in run method are finalized when
        //program terminates and thread exits
        try {
            serverSocket.close();
            super.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, "Erro ao finalizar servidor.", ex);
            System.exit(-1);
        } 
    }

}
