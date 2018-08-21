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

public class UserHandler implements Runnable {

	Socket s;
	DataInputStream dis;
	DataOutputStream dos;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	String userName;
	String password;
	Integer type;
	Integer userID;
	
	public UserHandler(Socket s,DataInputStream dis,DataOutputStream dos,ObjectInputStream ois,ObjectOutputStream oos,String userName , String password , Integer type , Integer userID)
	{
		this.dis = dis;
		this.dos = dos;
		this.ois = ois;
		this.oos = oos;
		this.s = s;
		this.userName = userName;
		this.password = password;
		this.type = type;
		this.userID = userID;
	}
	
	
	@SuppressWarnings({ "unchecked" })
	void bookTicket() throws IOException, SQLException, ClassNotFoundException
	{
		System.out.println("In bookTicket");
		
		
		String query = "SELECT * from city;";
		ArrayList<String> cityNames = new ArrayList<String>();
		ArrayList<Integer> cityID = new ArrayList<Integer>();
		ResultSet rs = DatabaseMGR.executeQuery(query);
		while(rs.next())
		{
			cityNames.add(rs.getString("cityName"));
			cityID.add(rs.getInt("cityID"));
		}
		oos.writeObject(cityNames);
		Integer cityIndex = Integer.parseInt(dis.readUTF().toString());
		cityIndex = cityIndex - 1;
		
		
		query = String.format("select distinct movie.movieID , movie.movieName from schedule inner join movie on(schedule.movieID = movie.MovieID) inner join screen on (schedule.screenID = screen.screenID) inner join theatre on(theatre.theatreID = screen.screenID) inner join city on (theatre.cityID = city.cityID)  where schedule.showTime > current_timestamp and city.cityID = %d;",cityID.get(cityIndex.intValue()));
		rs = DatabaseMGR.executeQuery(query);
		ArrayList<String> movieNames = new ArrayList <String >();
		ArrayList <Integer> movieID = new ArrayList <Integer>();
		while(rs.next())
		{
			movieNames.add(rs.getString("movie.movieName"));
			//System.out.println(rs.getString("movie.movieName"));
			movieID.add(rs.getInt("movie.movieID"));
			//System.out.println(rs.getString("movie.movieID"));
		}
		oos.writeObject(movieNames);
		Integer movieIndex = Integer.parseInt(dis.readUTF().toString());
		movieIndex = movieIndex - 1;
		
		
		query = String.format("select distinct schedule.showDate from schedule inner join movie on(schedule.movieID = movie.MovieID) inner join screen on (schedule.screenID = screen.screenID) inner join theatre on(theatre.theatreID = screen.theatreID) inner join city on (theatre.cityID = city.cityID)  where schedule.showTime > current_timestamp and city.cityID = %d and movie.movieID = %d;",cityID.get(cityIndex.intValue()),movieID.get(movieIndex.intValue()));
		rs = DatabaseMGR.executeQuery(query);
		
		ArrayList <String> scheduleDates = new ArrayList <String>();
		while(rs.next())
		{
			//System.out.println(rs.getString("schedule.showDate"));
			scheduleDates.add(rs.getString("schedule.showDate"));
		}
		oos.writeObject(scheduleDates);
		Integer scheduleDateIndex = Integer.parseInt(dis.readUTF().toString());
		scheduleDateIndex = scheduleDateIndex - 1; 
		
		
		query = String.format( "select distinct theatre.theatreID , theatre.theatreName from schedule inner join movie on(schedule.movieID = movie.MovieID) inner join screen on (schedule.screenID = screen.screenID) inner join theatre on(theatre.theatreID = screen.theatreID) inner join city on (theatre.cityID = city.cityID)  where schedule.showTime > current_timestamp and city.cityID = %d and movie.movieID = %d and schedule.showDate = '%s';",cityID.get(cityIndex.intValue()),movieID.get(movieIndex.intValue()) , scheduleDates.get(scheduleDateIndex.intValue()) );
		rs = DatabaseMGR.executeQuery(query);
		ArrayList <String> theatreNames = new ArrayList <String>();
		ArrayList <Integer> theatreID = new ArrayList <Integer>();
		while(rs.next())
		{
			theatreNames.add(rs.getString("theatre.theatreName"));
			//System.out.println(rs.getString("theatre.theatreName"));
			theatreID.add(rs.getInt("theatre.theatreID"));
			//System.out.println(rs.getString("theatre.theatreID"));
		}
		oos.writeObject(theatreNames);
		Integer theatreIndex = Integer.parseInt(dis.readUTF().toString());
		theatreIndex  = theatreIndex - 1;
		
		
		query = String.format( "select schedule.showTime , schedule.scheduleID , schedule.screenID from schedule inner join movie on(schedule.movieID = movie.MovieID) inner join screen on (schedule.screenID = screen.screenID) inner join theatre on(theatre.theatreID = screen.theatreID) inner join city on (theatre.cityID = city.cityID)  where schedule.showTime > current_timestamp and city.cityID = %d and movie.movieID = %d and schedule.showDate = '%s' and theatre.theatreID = %d;",cityID.get(cityIndex.intValue()),movieID.get(movieIndex.intValue()) , scheduleDates.get(scheduleDateIndex.intValue()),theatreID.get(theatreIndex.intValue()) );
		rs = DatabaseMGR.executeQuery(query);
		ArrayList <String> showTime = new ArrayList <String>();
		ArrayList <Integer> scheduleID = new ArrayList <Integer>();
		ArrayList <Integer> screenID = new ArrayList <Integer>();
		while(rs.next())
		{
			showTime.add(rs.getString("schedule.showTime"));
			scheduleID.add(rs.getInt("schedule.scheduleID"));
			screenID.add(rs.getInt("schedule.screenID"));
		}
		oos.writeObject(showTime);
		Integer scheduleIndex = Integer.parseInt(dis.readUTF().toString());
		scheduleIndex = scheduleIndex - 1;
		
		//System.out.println(scheduleID.get(scheduleIndex.intValue()));
		
		query = String.format("Select * from seatmap where screenID = %d and seatmapID not in (select bookedseats.seatmapID from bookedseats NATURAL JOIN bookinginfo where scheduleID = %d )", screenID.get(scheduleIndex.intValue()),scheduleID.get(scheduleIndex.intValue()));
		rs = DatabaseMGR.executeQuery(query);
		ArrayList<Integer> availableSeatNO = new ArrayList <Integer>();
		ArrayList<Integer> availableSeatMapID = new ArrayList<Integer>();
		while(rs.next())
		{
			availableSeatNO.add(rs.getInt("seatNO"));
			availableSeatMapID.add(rs.getInt("seatMapID"));
		}
		query = String.format("Select noOfRows , noOfCol from schedule NATURAL JOIN screen where scheduleID = %d and screen.screenID = %d", scheduleID.get(scheduleIndex.intValue()),screenID.get(scheduleIndex.intValue()));
		rs = DatabaseMGR.executeQuery(query);
		Integer rows = null;
		Integer col = null;
		while(rs.next())
		{
			rows = new Integer(rs.getInt("noOfRows"));
			col = new Integer(rs.getInt("noOfCol"));
		}
		oos.writeObject(rows);
		oos.writeObject(col);
		oos.writeObject(availableSeatNO);
		
		
		ArrayList <String> seatNO = (ArrayList <String>) ois.readObject();
		for(int i = 0 ; i<seatNO.size() ;i++)
		{
			System.out.println(seatNO.get(i));
		}
		
		query = String.format("Insert into bookinginfo( userID,scheduleID,totalSeats,bookingTime ) values(%d,%d,%d,current_timestamp)", this.userID,scheduleID.get(scheduleIndex.intValue()) , seatNO.size() );
		DatabaseMGR.executeUpdate(query);
		query = String.format("Select bookingID from bookinginfo where scheduleID = %d and userID = %d", scheduleID.get(scheduleIndex.intValue()),this.userID);
		rs = DatabaseMGR.executeQuery(query);
		rs.next();
		Integer bookingID = rs.getInt("bookingID");
		for(int i = 0 ; i<seatNO.size() ; i++)
		{
			query = String.format("Insert into bookedseats(bookingID,seatmapID) values(%d,%d)", bookingID.intValue() , availableSeatMapID.get( Integer.parseInt(seatNO.get(i)) - 1 ) );
			DatabaseMGR.executeUpdate(query);
		}
		
		dos.writeUTF("Booking Successful");
		dos.writeInt(bookingID);
	}
	
	
	void showBookings() 
	{
		System.out.println("In Show Bookings");
		
	}
	
	
	
	@Override
	public void run() {
		System.out.println("Starting Thread : "+Thread.currentThread().getName());
		
		System.out.println("IN UserHandler Thread");
		
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
						bookTicket();
					} catch (SQLException | IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}	
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
		}
		
		
		System.out.println("Stopping Thread  : " + Thread.currentThread().getName());
		
	}

}
