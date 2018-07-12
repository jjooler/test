package test;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Server {
	
	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(1234);
		System.out.println("Waiting for client...");
		Socket accept = socket.accept();
		String server = accept.getLocalAddress().getHostAddress();
		String user = accept.getInetAddress().getHostAddress();
		
		Scanner scan = new Scanner(System.in);
		
		Thread t1 = new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	PrintStream output = null;
		    	
				try {
					output = new PrintStream(accept.getOutputStream());
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				
				System.out.println("Client connected from: " + user);
				output.println("Client connected from: " + user);
				
		    	String message = "";
		    	
			    	while(true)
			    	{	
			    		message = server + ": " + scan.nextLine();
			    		
			    		if(message.equalsIgnoreCase(server + ": quit") || accept.isClosed()) {
			    			System.out.println("Server closed.");
			    			break;
			    		}
			    		else
			    			output.println(message);
			    	}
			    	
			    	output.close();
			    	scan.close();
			    	
			    	try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    }
		});
		
		Thread t2 = new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	Scanner input = null;
				
					while(t1.isAlive()) {
						try {
							input = new Scanner(accept.getInputStream());
						} catch (IOException e) {
							break;
						}
						
						try {
							System.out.println(input.nextLine());
						} catch(NoSuchElementException ee) {
							if(t1.isAlive())
								System.out.println("Client has disconnected.");
							break;
						}
					}
					
					input.close();
		    }
		});
		
		t1.start();
		t2.start();
	}
}
