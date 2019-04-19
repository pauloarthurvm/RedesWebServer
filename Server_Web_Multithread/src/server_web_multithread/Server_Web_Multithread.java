/*,,,,,,,,,,,,,,,,,
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_web_multithread;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author paulo
 */
public final class Server_Web_Multithread {

	//	Set the port number
	static final int port = 6789;
	static ServerSocket server;
	static Socket socketClient;
	
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws IOException {
		
		//	Establish the listen socket
		server = new ServerSocket(port);
				
		//	Process HTTP service requestes in an infinite loop.
		while(true){
			System.out.println("Server openned.");
			System.out.println(new Date() + "\n");
			
			//	Listen for a TCP connection request
			socketClient = server.accept();
			
			//	HTTP request
			HttpRequest request = new HttpRequest(socketClient);
			
			//	Create new Thread to process the request
			Thread thread = new Thread(request);
			thread.start();
			
		}
	}
}

