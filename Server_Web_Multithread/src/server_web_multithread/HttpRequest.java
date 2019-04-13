/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_web_multithread;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author paulo
 */
final class HttpRequest implements Runnable {
	
	final static String CRLF = "\r\n";
	Socket socket;

	HttpRequest(Socket socketClient) {
		System.out.println("Connection requested.");
		socket = socketClient;
	}

	@Override
	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			System.out.println("Error on request processing: " + e);
		}
	}
	
	private void processRequest() throws Exception{
		
		//	Get a reference to the socket's input and ouput stream
		InputStream is = socket.getInputStream();
		DataOutputStream os = new DataOutputStream(socket.getOutputStream());
		
		//	Set up input stream filters
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		
		//	Get the request line of the HTTP request message
		String requestLine = br.readLine();
		
		//	Print the request line
		System.out.println("\n" + requestLine);
		
		//	Get and display the header lines
		String headerLine = null;
		while((headerLine = br.readLine()).length() != 0){
			System.out.println(headerLine);
		}
		
		//Close stream and socket
		os.close();
		br.close();
		socket.close();
	}
	
}
