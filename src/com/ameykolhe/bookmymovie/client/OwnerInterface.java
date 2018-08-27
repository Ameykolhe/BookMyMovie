package com.ameykolhe.bookmymovie.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class OwnerInterface implements Runnable{
	Scanner sc = new Scanner(System.in);
	
	Socket s;
	
	DataInputStream dis;
	DataOutputStream dos;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	public OwnerInterface(Socket s,DataInputStream dis,DataOutputStream dos,ObjectInputStream ois,ObjectOutputStream oos)
	{
		this.dis = dis;
		this.dos = dos;
		this.ois = ois;
		this.oos = oos;
		this.s = s;
	}
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	void scheduleMovie() throws ClassNotFoundException, IOException
	{
		String movieName = null;
		ArrayList<String> movieNames = (ArrayList<String>)ois.readObject();
		System.out.println("Enter Movie Name : ");
		
		for(int i = 0 ; i<movieNames.size() ;i++ )
		{
			System.out.println( (i + 1) + "   " + movieNames.get(i) );
		}
		dos.writeUTF(sc.next());
		
		ArrayList<String> theatreNames = (ArrayList<String>)ois.readObject();
		System.out.println("Enter Theatre Name : ");
		
		for(int i = 0 ; i<theatreNames.size() ;i++ )
		{
			System.out.println( (i + 1) + "   " + theatreNames.get(i) );
		}
		dos.writeUTF(sc.next());
		
		ArrayList<Integer> screenNum = (ArrayList<Integer>)ois.readObject();
		System.out.println("Enter Screen Num : ");
		
		for(int i = 0 ; i<screenNum.size() ;i++ )
		{
			System.out.print( "\t" + screenNum.get(i) );
		}
		System.out.println();
		dos.writeUTF(sc.next());
		
		System.out.print("Enter Date (yyyy-mm-dd) :  ");
		dos.writeUTF(sc.next());
		System.out.print("Enter Time (HH:MM) :  ");
		dos.writeUTF(sc.next() + ":00");
		
		System.out.println("Enter Price  : ");
		dos.writeUTF(sc.next());
		
		System.out.println(dis.readUTF());
		
	}
	
	
	void createNewTheatre()
	{
		
	}
	
	@Override
	public void run() {
		System.out.println("Starting Thread : "+Thread.currentThread().getName());
		
		System.out.println("In Owner Interface");
		
		boolean flag = false;
		
		while(true)
		{
			System.out.println("Enter : 1: Schedule Movie\n\t2: Exit");
			int choice = sc.nextInt();
			switch(choice)
			{
				case 1:
					try {
						dos.writeUTF("1");
						scheduleMovie();
					
					} catch (IOException | ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					break;
					
				case 2:
					try {
						dos.writeUTF("2");
						flag = true;
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
			}
			
			if(flag)
			{
				break;
			}
		}
		
	
	
		System.out.println("Stopping Thread  : " + Thread.currentThread().getName());
	}

}