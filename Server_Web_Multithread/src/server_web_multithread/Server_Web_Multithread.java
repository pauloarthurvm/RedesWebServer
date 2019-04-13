/*
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
public class Server_Web_Multithread {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws IOException {
		
		//	Set the port number
		int port = 6789;
		
		//	Establish the listen socket
		ServerSocket server = new ServerSocket(12345);
		Socket client = server.accept();
		
		//	Process HTTP service requestes in an infinite loop.
		while(true){
			//	Listen for a TCP connection request
		}
		
		
		
		
	}
	
}

