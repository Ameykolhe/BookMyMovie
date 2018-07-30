package com.ameykolhe.bookmymovie.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

import java.util.Scanner;

public class UserInterface implements Runnable{
	Scanner sc = new Scanner(System.in);
	
	Socket s;
	
	DataInputStream dis;
	DataOutputStream dos;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	public UserInterface(Socket s,DataInputStream dis,DataOutputStream dos,ObjectInputStream ois,ObjectOutputStream oos)
	{
		this.dis = dis;
		this.dos = dos;
		this.ois = ois;
		this.oos = oos;
		this.s = s;
	}
	
	void bookTicket()
	{
		
	}
	
	
	void showBookings()
	{
		
	}
	
	
	@Override
	public void run() {
		System.out.println("Starting Thread : "+Thread.currentThread().getName());
		
		//System.out.println("In User Interface");
		int choice;
		boolean flag = false;
		while(true)
		{
			System.out.println("Enter  : 1: Book Ticket\n\t2: Show Bookings\n\t3: Exit");
			choice = sc.nextInt();
			
			switch(choice)
			{
				case 1:
					bookTicket();
					break;
					
				case 2:
					showBookings();
					break;
					
				case 3:
					flag = true;
					break;
			}
			
			if(flag)
			{
				break;
			}
			clearConsole();
		}
		System.out.println("Stopping Thread  : " + Thread.currentThread().getName());
	}
	
	
	
	
	final static void clearConsole()
	{
	    try
	    {
	        final String os = System.getProperty("os.name");

	        if (os.contains("Windows"))
	        {
	            Runtime.getRuntime().exec("cls");
	        }
	        else
	        {
	            Runtime.getRuntime().exec("clear");
	        }
	    }
	    catch (final Exception e)
	    {
	        //  Handle any exceptions.
	    }
	
	}
}
