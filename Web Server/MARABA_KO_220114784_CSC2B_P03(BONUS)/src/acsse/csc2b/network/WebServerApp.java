/**
 * 
 */
package acsse.csc2b.network;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 */
public class WebServerApp {

	 ServerSocket ss = null;
	 int portNum = 4004;
	 boolean isRunning = true;
	
	
	 public WebServerApp(int portNum) {//constructor
		 
		 try {
			ss = new ServerSocket(portNum);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
	public void initializeServer() {
		
	
		while(isRunning) {
			
			try {
				Thread t = new Thread(new ClientHandler(ss.accept()));
				t.start();
				System.out.println("Server waiting on port:" + ss.getLocalPort());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	



	
	
	
	
	
}
