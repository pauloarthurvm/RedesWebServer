/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_web_multithread;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author paulo
 */
final class HttpRequest implements Runnable {
	
	final static String CRLF = "\r\n";
	Socket socket;

	HttpRequest(Socket socketClient) {
		System.out.println("Connection requested.\n");
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
		System.out.println("Request line:\n\t" + requestLine);
		
		//	Get and display the header lines
		String headerLine = null;
		while((headerLine = br.readLine()).length() != 0){
			System.out.println(headerLine);
		}
		System.out.println("");
		
		//	Extract the filename from the request line
		StringTokenizer tokens = new StringTokenizer(requestLine);
		tokens.nextToken();	//	Skip over the method, which should be "GET"
		String fileName = tokens.nextToken();
		
		//	Prepend a "." so that file request is within the current directory.
		fileName = "." + fileName;
		
		//Open the requested file
		FileInputStream fis = null;
		boolean fileExists = true;
		
		try {
			fis = new FileInputStream(fileName);
		} catch (Exception e) {
			fileExists = false;
		}
		
		//	Construct the response message
		String statusLine = null;
		String contentTypeLine = null;
		String entityBody = null;
		
		if(fileExists){
			statusLine = "The file exists!";
			contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
		}
		else{
			statusLine = "404 Not Found.";
			contentTypeLine = "404 Not Found!";
			entityBody = "<HTML><HEAD><TITLE>Not found.</TITLE></HEAD><BODY>Not found.</BODY></HTML>";
		}
		
		//	Send the status line
		os.writeBytes(statusLine);
		
		//	Send the content type line
		os.writeBytes(contentTypeLine);
		
		//	Send a blank line to indicate the end of the header lines
		os.writeBytes(CRLF);
		
		//	Send the entity body
		if(fileExists){
			sendBytes(fis, os);
			fis.close();
		}
		else{
			os.writeBytes(entityBody);
		}
		
		//Close stream and socket
		os.close();
		br.close();
		socket.close();
	}

	private String contentType(String fileName) {
		if (fileName.endsWith(".html") || fileName.endsWith(".htm")) {
			return "text/html";
		} else {
			return "application/octet-stream";
		}
	}

	private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception {
		//	Construct a 1K buffer to hold bytes on their way to the socket
		byte[] buffer = new byte[1024];
		int bytes = 0;
		
		//	Copy requested file into the socket's output stream
		while((bytes = fis.read(buffer))!= -1){
			os.write(buffer, 0, bytes);
		}
	}
	
	
	
	
	
}
