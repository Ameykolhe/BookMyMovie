package com.ameykolhe.bookmymovie.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Scanner;

public class Client {
	@SuppressWarnings({ "rawtypes", "unchecked", "resource" })
	public static void main(String []args) throws UnknownHostException, IOException, ClassNotFoundException
	{
		Scanner sc = new Scanner(System.in);
		Socket s = new Socket("127.0.0.1",1234);
		
		DataInputStream dis = new DataInputStream(s.getInputStream());
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
		
		while(true)
		{
			System.out.println("Welcome to BookMyMovie");
			System.out.println("Enter   1: Signup\n\t2: Login");
			int choice = sc.nextInt();
			ArrayList ar = new ArrayList();
			String username,password,type;
			String str;
			boolean fl = false;
			switch(choice)
			{
				case 1:				//Signup

					System.out.print("\nSignUp Page\n\nEnter Username : ");
					username = sc.next();
					ar.add(username);
					System.out.print("Enter Password : ");
					password = sc.next();
					ar.add(password);
					System.out.print("Enter Type of account (user/owner)");
					type = sc.next();
					ar.add(type);
					dos.writeUTF("1");
					oos.writeObject(ar);
					str = dis.readUTF().toString();
					System.out.println(str);
					break;
					
				case 2:

					System.out.print("\nLogin Page\n\nEnter Username : ");
					username = sc.next();
					ar.add(username);
					System.out.print("Enter Password : ");
					password = sc.next();
					ar.add(password);
					dos.writeUTF("2");
					oos.writeObject(ar);
					str = dis.readUTF().toString();
					if(str.equals("Login Successful"))
					{
						fl = true;
						Integer type1 = (Integer)ois.readObject();
						if(type1.intValue() == 0)					//User
						{
							UserInterface ui = new UserInterface(s,dis,dos,ois,oos);
							Thread t = new Thread(ui);
							t.start();
						}
						else if(type1.intValue() == 1)				//Owner
						{
							OwnerInterface ui = new OwnerInterface(s,dis,dos,ois,oos);
							Thread t = new Thread(ui);
							t.start();
						}
					}
					System.out.println(str);
					break;
			}
			if(fl)
			{
				System.out.println("Breaking Loop");
				break;
			}
			
		}
		System.out.println("Stopping Login Page");
	}
	
	
}
