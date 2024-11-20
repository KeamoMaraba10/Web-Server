/**
 * 
 */
package acsse.csc2b.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * 
 */
public class ClientHandler implements Runnable{
	
	
	 private Socket cs;
	 private BufferedReader in;
	 private DataOutputStream out;//this handles binary data
	
	
	/*
	 * ClientHandler constructor
	 */
	public ClientHandler(Socket cs) {
		this.cs = cs;
		
	}

	
	


@Override
public void run() {
	// TODO Auto-generated method stub
	
	
	
	
	
	try {
		this.in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
		this.out = new DataOutputStream(new BufferedOutputStream(cs.getOutputStream()));
		
		
		//read the get request recieved from client
		
		String request = in.readLine();//GET / index.html
		
		//System.out.println(request);
		StringTokenizer tokenizer = new StringTokenizer(request);
		
		
		
		
		System.out.println("Tokens: " + tokenizer.countTokens());
		
		String requestType = tokenizer.nextToken();
		String filename = tokenizer.nextToken().substring(1);
		String filepath = "data/"+filename;
		
		//handle any file that gets requested in this object
		File file = new File(filepath);
		
		
		
		if(file.exists()) {
			
			if(requestType.equals("GET")) {
				
				
				
				
				 String mt = Files.probeContentType(Paths.get(filepath));
			        if (mt == null) {
			        	mt = "application/octet-stream"; // default if MIME type is not detected
			        }
				
				out.writeBytes("HTTP/1.1 200 OK \r\n");
				out.writeBytes("Connection: close \r\n");
				out.writeBytes("Content-Type: "+mt+"\r\n");
				out.writeBytes("Content-Length:"+ file.length()+"\r\n");
				out.writeBytes("\r\n");
				
				
				
				//read file into memory and write it out to client
				BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
				byte[] buffer = new byte[1024];
				
				int n = 0;
				
				while((n = bin.read(buffer))>0) {
					
					System.out.println(n);
					this.out.write(buffer, 0, n);
				}
				
				bin.close();
				out.writeBytes("\r\n");
				out.flush();
				
			}
			
			
		} else if(!file.exists()) {
			
			
            // File not found, send 404 response
            String errMsg = "<html><head><title>404 Not Found</title></head>" +
                    "<body><h1>404 Not Found</h1>" +
                    "<p>The requested URL /" + filename + " was not found on this server.</p>" +
                    "</body></html>";

            out.writeBytes("HTTP/1.1 404 Not Found\r\n");
            out.writeBytes("Connection: close\r\n");
            out.writeBytes("Content-Type: text/html\r\n");
            out.writeBytes("Content-Length: " + errMsg.length() + "\r\n");
            out.writeBytes("\r\n");
            out.writeBytes(errMsg);
            out.flush();
        }	else {
        	
        	 System.out.println("Error");
        	 out.writeBytes("HTTP/1.1 500 Error\r\n");
             out.writeBytes("\r\n");
             out.flush();
             
        }
		
		
		
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	

	
	

	
}




	
	
	
}


