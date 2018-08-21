package com.ameykolhe.bookmymovie.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
	
	@SuppressWarnings({ "unchecked" })
	void bookTicket() throws IOException, ClassNotFoundException, ParseException, InterruptedException
	{	
		ArrayList <String> cityNames = (ArrayList<String>)ois.readObject();
		System.out.println("Select City : ");
		for(int i = 0 ; i<cityNames.size() ;i++ )
		{
			System.out.println( (i + 1) + "   " + cityNames.get(i) );
		}
		String cityIndex = sc.next();
		dos.writeUTF(cityIndex);
		
		ArrayList <String> movieNames = (ArrayList<String>)ois.readObject();	
		System.out.println("Select Movie : ");
		for(int i = 0 ; i<movieNames.size() ;i++ )
		{
			System.out.println( (i + 1) + "   " + movieNames.get(i) );
		}
		String movieIndex = sc.next();
		dos.writeUTF(movieIndex);
		
		ArrayList<String> scheduleDates = (ArrayList <String>) ois.readObject();
		System.out.println("Select Date : ");
		for(int i = 0 ; i<scheduleDates.size() ;i++ )
		{
			System.out.println( (i + 1) + "   " + scheduleDates.get(i) );
		}
		String scheduleDateIndex = sc.next();
		dos.writeUTF(scheduleDateIndex);
		
		ArrayList<String> theatreNames = (ArrayList <String>) ois.readObject();
		System.out.println("Select theatreName : ");
		for(int i = 0 ; i < theatreNames.size() ;i++ )
		{
			System.out.println( (i + 1) + "   " + theatreNames.get(i) );
		}
		String theatreIndex = sc.next();
		dos.writeUTF(theatreIndex);
		
		ArrayList<String> showTimes = (ArrayList <String>) ois.readObject();
		System.out.println("Select showTime : ");
		for(int i = 0 ; i < showTimes.size() ;i++ )
		{
			System.out.println( (i + 1) + "   " + showTimes.get(i) );
		}
		String showTimeIndex = sc.next();
		dos.writeUTF(showTimeIndex);
		
		Integer rows = (Integer)ois.readObject();
		Integer col = (Integer)ois.readObject();
		ArrayList <Integer> availableSeatNO = (ArrayList <Integer>)ois.readObject();
		System.out.println("Avaliable seats : \n");
		for(int i = 1 ; i <= rows.intValue()*col.intValue() ; i++ )
		{
			if(availableSeatNO.contains(i))
			{
				System.out.print(String.format("%3d ", i));
			}
			else
			{
				System.out.print(" X  ");
			}
			if(i%col.intValue() == 0)
			{
				System.out.println();
			}
		}
		
		System.out.println("\nEnter Seat NO : ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String seatNo = br.readLine();
		ArrayList<String> seatNOs = new ArrayList<String>(Arrays.asList(seatNo.split(" ")));
		oos.writeObject(seatNOs);
		
		String msg = dis.readUTF().toString();
		if(msg.equals("Booking Successful"))
		{
			Integer bookingID = dis.readInt();
			System.out.println("--------------------Booking Details----------------------- ");
			System.out.println("BookingID : " + bookingID.toString());
			Thread.sleep(1000);
		}
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
			System.out.println("Enter : 1: Book Ticket\n\t2: Show Bookings\n\t3: Exit");
			choice = sc.nextInt();
			
			switch(choice)
			{
				case 1:
					try {
						dos.writeUTF("1");
						bookTicket();
					} catch (IOException | ClassNotFoundException | ParseException | InterruptedException e) {
						e.printStackTrace();
					}
					break;
					
				case 2:
					try {
						dos.writeUTF("2");
						showBookings();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
					
				case 3:
					try {
						dos.writeUTF("3");
					} catch (IOException e) {
						e.printStackTrace();
					}
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
