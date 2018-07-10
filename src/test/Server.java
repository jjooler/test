package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	
	public static void main(String[] args) throws IOException
	{
		ServerSocket socket = new ServerSocket(1234);
		Socket accept = socket.accept();
		Scanner scan = new Scanner(accept.getInputStream());
		
		System.out.println(scan.nextLine());
	}
	
}
