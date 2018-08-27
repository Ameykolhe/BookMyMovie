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

public class OwnerHandler implements Runnable {

	Socket s;
	DataInputStream dis;
	DataOutputStream dos;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	String userName;
	String password;
	Integer type;
	Integer ownerID;
	
	public OwnerHandler(Socket s,DataInputStream dis,DataOutputStream dos,ObjectInputStream ois,ObjectOutputStream oos,String userName , String password , Integer type , Integer ownerID)
	{
		this.dis = dis;
		this.dos = dos;
		this.ois = ois;
		this.oos = oos;
		this.s = s;
		this.userName = userName;
		this.password = password;
		this.type = type;
		this.ownerID = ownerID;
	}
	
	
	void scheduleMovie() throws SQLException, IOException
	{
		System.out.println("In Schedule Movie");
		String query = "Select * from movie";
		ResultSet rs = DatabaseMGR.executeQuery(query);
		
		ArrayList <String> movieNames = new ArrayList<String>();
		ArrayList <Integer> movieID = new ArrayList<Integer>();
		ArrayList <String> theatreNames = new ArrayList<String>();
		ArrayList <Integer> theatreID = new ArrayList<Integer>();
		ArrayList <Integer> screenNum = new ArrayList<Integer>();
		ArrayList <Integer> screenID = new ArrayList<Integer>();
		
		while(rs.next())
		{
			movieNames.add(rs.getString("movieName"));
			movieID.add(rs.getInt("movieID"));
		}
		oos.writeObject(movieNames);
		
		Integer movieIndex = Integer.parseInt(dis.readUTF().toString());
		movieIndex = movieIndex - 1; 
		System.out.println("movieName : " + movieNames.get(movieIndex));
		
		query = "Select * from theatre Where ownerID = " + this.ownerID + ";";
		rs = DatabaseMGR.executeQuery(query);
		
		while(rs.next())
		{
			theatreNames.add(rs.getString("theatreName"));
			theatreID.add(rs.getInt("theatreID"));
		}
		oos.writeObject(theatreNames);
		
		Integer theatreIndex = Integer.parseInt(dis.readUTF().toString());
		theatreIndex = theatreIndex - 1;
		
		System.out.println("theatreName : " + theatreNames.get(theatreIndex));
		
		query = "Select * from screen Where theatreID = " + theatreID.get(theatreIndex) + ";" ;
		rs = DatabaseMGR.executeQuery(query);
		
		while(rs.next())
		{
			screenNum.add(rs.getInt("screenNum"));
			screenID.add(rs.getInt("screenID"));
		}
		oos.writeObject(screenNum);
		
		Integer screenIndex = Integer.parseInt(dis.readUTF().toString());
		screenIndex = screenIndex - 1;
		
		System.out.println("screenNum : " + screenNum.get(screenIndex));
		
		String showDate = dis.readUTF().toString();
		
		String time = dis.readUTF().toString();
		
		String showTime = showDate + " " + time;
		
		System.out.println("ShowTIme : " + showTime);
		
		Integer price = Integer.parseInt(dis.readUTF().toString());
		Integer stat = new Integer(0);
		
		query = String.format( "Select scheduleID from schedule where screenId = %d and (showTime between '%s' and ADDTIME('%s',(select runTime from movie where movieID = %d)) );" ,screenID.get(screenIndex) , showTime , showTime , movieID.get(movieIndex) );
		rs = DatabaseMGR.executeQuery(query);
		if(rs.next())
		{
			stat = Integer.parseInt("-1");
		}
		if(stat.intValue() != -1)
		{
			query = String.format("Insert into schedule(showDate,showTime,screenID,movieID,price) values ('%s','%s',%d,%d,%d);",showDate,showTime,screenID.get(screenIndex),movieID.get(movieIndex),price);
			//query = "Insert into schedule(showDate,showTime,screenID,movieID,price) values ("+" ' " + showDate + " ',' "+showTime+" ', " + screenID.get(screenIndex).toString()+" , "+movieID.get(movieIndex).toString()+" , "+price.toString()+" ); ";
			stat = DatabaseMGR.executeUpdate(query);
			if(stat.intValue() == 1 )
			{
				dos.writeUTF("Movie Successfully Scheduled");
			}
			else
			{
				dos.writeUTF("Schedule Unsuccessful");
			}
		}
		else
		{
			dos.writeUTF("Schedule Unsuccessful : SCREEN IS BUSY");
		}
		
	}
	
	
	
	
	@Override
	public void run() {
		System.out.println("Starting Thread : "+Thread.currentThread().getName());
		
		System.out.println("IN OwnerHandler Thread");
		
		
		boolean flag = false;
		
		while(true)
		{
			Integer choice = null;
			try {
				choice = Integer.parseInt( dis.readUTF().toString() );
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
			switch(choice)
			{
				case 1:
					try {
						scheduleMovie();
					} catch (SQLException | IOException e) {
						e.printStackTrace();
					}	
					break;
					
				case 2:
					flag = true;
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
