/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jfcosta
 */
public class ClientSocket implements Runnable {
    
    Socket client;
    PrintWriter out;
    BufferedReader in;
    String inputLine, outputLine;
    
    ClientSocket(Socket skt) {
    
        this.client = skt;
        try {
            out = new PrintWriter(client.getOutputStream(), true);
            in  = new BufferedReader( new InputStreamReader(client.getInputStream()) );
        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public boolean isConnected() {
        return client.isConnected();
    }
    
    @Override
    public void run() {

        try {

            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                if (inputLine.equals("bye.")) {
                    out.println("ByeBye!");
                    break;
                }
                outputLine = "Mensagem {" + inputLine + "} recebida em: " + System.currentTimeMillis();
                out.println(outputLine);
            }
            out.close();
            in.close();
            client.close();

        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
