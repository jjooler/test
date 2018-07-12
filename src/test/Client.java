package test;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client 
{
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Enter an IP: ");
		String IP = scan.nextLine();
		
		Socket socket = new Socket(IP,1234);
		String user = socket.getLocalAddress().getHostAddress();
		
		Thread t1 = new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	PrintStream output = null;
		    	String message = "";
		    	
			    	while(true)
			    	{			
			    		try {
			    			output = new PrintStream(socket.getOutputStream());
						} catch (IOException e) {
							e.printStackTrace();
						}
						
			    		message = user + ": " + scan.nextLine();
			    		
			    		if(message.equalsIgnoreCase(user + ": quit") || socket.isClosed())
			    		{
			    			System.out.println("Disconnected from server.");
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
							input = new Scanner(socket.getInputStream());
						} catch (IOException e) {
						}
						
						try {
							System.out.println(input.nextLine());
						} catch(NoSuchElementException ee) {
							if(t1.isAlive())
								System.out.println("Server offline!");
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