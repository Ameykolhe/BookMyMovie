package com.ameykolhe.bookmymovie.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.sql.SQLException;

public class Server {

	@SuppressWarnings("resource")
	public static void main(String []args) throws IOException, SQLException, ClassNotFoundException
	{
		ServerSocket ss = new ServerSocket(1234);
		
		
		System.out.println("Server Started . Port : 1234");
		
		/*
		ResultSet rs = DatabaseMGR.executeQuery("select * from City");
		while(rs.next()) {
			System.out.println("CityID : " + rs.getInt("cityID") + "\tCityName : " + rs.getString("cityName"));
		}
		*/
		
		Socket s;
		while(true)
		{
			s = ss.accept();
			System.out.println("New Connection Received : " + s.getLocalAddress().toString());
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			
			System.out.println("Creating new Login Handler For : " + s.getInetAddress().toString());
			LoginHandler l = new LoginHandler(s,dis,dos,ois,oos);		
			
			Thread t = new Thread(l);
			t.start();
		}
	}
}