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
import java.net.UnknownHostException;

/**
 *
 * @author jfcosta
 */
public class Client {
    
     public static void main(String[] args) throws IOException {

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String host = "localhost";
        int    port = 4444;

        try {
            echoSocket = new Socket(host, port);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + host);
            System.exit(1);
        }

	BufferedReader stdIn = new BufferedReader(
                                   new InputStreamReader(System.in));
	String userInput, serverOut;

	while ((userInput = stdIn.readLine()) != null) {
            out.println( userInput );
	    serverOut = in.readLine();
            System.out.println( serverOut );
	    if (serverOut.equals("ByeBye!")) { break; }
	}

	out.close();
	in.close();
	stdIn.close();
	echoSocket.close();
    }
}
