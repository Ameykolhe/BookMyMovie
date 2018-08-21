package com.ameykolhe.bookmymovie.server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ameykolhe.bookmymovie.utilities.DatabaseMGR;

public class LoginHandler implements Runnable {

	Socket s;
	DataInputStream dis;
	DataOutputStream dos;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	boolean login = false;
	boolean signup = false;

	public LoginHandler(Socket s,DataInputStream dis,DataOutputStream dos,ObjectInputStream ois,ObjectOutputStream oos) {
		this.dis = dis;
		this.dos = dos;
		this.ois = ois;
		this.oos = oos;
		this.s = s;
	}
	
	@SuppressWarnings({ "unchecked" })
	Integer loginOrSignup() throws IOException, ClassNotFoundException, SQLException
	{
		String msg = dis.readUTF().toString();
		boolean f = true;
		if(msg.equalsIgnoreCase("1"))							//signup
		{
			ArrayList <String> ar = (ArrayList <String>)ois.readObject();
			System.out.print("Received Data : ");
			System.out.println(ar.get(0) + " " + ar.get(1) + " " + ar.get(2) );
			String query = "Select * from User where userName = '" + ar.get(0) + "';";
			ResultSet rs = DatabaseMGR.executeQuery(query);
			if(rs.next()) {
				//System.out.println(rs.getString("userName"));
				dos.writeUTF("User already exists");
				f = false;
			}
			else
			{
				String type = new String();
				boolean fl = false;
				if(ar.get(2).equalsIgnoreCase("user")) {
					type = new String("0");
					fl = true;
				}
				else if(ar.get(2).equalsIgnoreCase("owner")) {
					type = new String("1");
					fl = true;
				}
				System.out.println("User Existance : " + f);
				if(fl && f){
					query = "Insert into User(userName,password,type) values('" + ar.get(0) + "','" + ar.get(1) + "'," + type + ");" ;
					Integer i = DatabaseMGR.executeUpdate(query);
					if(i.intValue() == 1)
					{
						
						dos.writeUTF("Signup Successful");
						return new Integer(1);
					}
					else
					{
						dos.writeUTF("Signup Unsuccessful");
					}
				}
		
			}
		}
		else if(msg.equalsIgnoreCase("2"))						//login
		{
			ArrayList <String> ar = (ArrayList <String>)ois.readObject();
			System.out.print("Received Data : ");
			System.out.println(ar.get(0) + " " + ar.get(1) );
			String query;
			query = "select * from User where userName = '" + ar.get(0) + "';" ;
			ResultSet rs = DatabaseMGR.executeQuery(query);
			System.out.print("Data Retrived From Database : ");
			if(rs.next())
			{
				System.out.println("retreived");
				System.out.print(rs.getString("userName") + " " + rs.getString("password") + " ");
				System.out.println(rs.getInt("type"));
				if(rs.getRow() == 1)
				{
					if(rs.getString("userName").equals(ar.get(0)) )
					{
						if(rs.getString("password").equals(ar.get(1)))
						{
							if(rs.getInt("type") == 0)
							{
								System.out.println("IN USer login");
								UserHandler obj = new UserHandler(s,dis,dos,ois,oos,rs.getString("userName"),rs.getString("password"),rs.getInt("type"), rs.getInt("userID"));
								Thread t = new Thread(obj);
								t.setName(rs.getString("userName"));
								t.start();
								dos.writeUTF("Login Successful");
								oos.writeObject(rs.getInt("type"));
								return new Integer(2);
							}
							else if(rs.getInt("type") == 1)
							{
								OwnerHandler obj = new OwnerHandler(s,dis,dos,ois,oos,rs.getString("userName"),rs.getString("password"),rs.getInt("type"),rs.getInt("userID"));
								Thread t = new Thread(obj);
								t.setName(rs.getString("userName"));
								t.start();
								dos.writeUTF("Login Successful");
								oos.writeObject(rs.getInt("type"));
								return new Integer(2);
							}
						}
						else
						{
							dos.writeUTF("Invalid Password");
						}
					}
					else
					{
						dos.writeUTF("Invalid UserName");
					}
				}
				else
				{
					dos.writeUTF("Invalid UserName");
				}
			}
			else
			{
				
				dos.writeUTF("Invalid UserName");
			}
		}
		return new Integer(0);
	}
	
	@Override
	public void run() {
		System.out.println("Starting Thread : "+Thread.currentThread().getName());
		Integer stat = null;
		
		while(true)
		{
			stat = 0;
			try {
				stat = loginOrSignup();
				
			} catch (ClassNotFoundException | IOException | SQLException e) {
				e.printStackTrace();
			}
			if(stat.intValue() == 2)
			{
				System.out.println("Breaking Loop");
				break;
			}
		}
		System.out.println("Stopping Thread  : " + Thread.currentThread().getName());
	}

}
