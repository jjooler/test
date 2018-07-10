package test;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client 
{
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		Socket socket = new Socket("127.0.0.1",1234);
		PrintStream stream = new PrintStream(socket.getOutputStream());
		
		Scanner scan = new Scanner(socket.getInputStream());
		Scanner input = new Scanner(System.in);
		
		stream.println(input.nextLine());
	}
}
