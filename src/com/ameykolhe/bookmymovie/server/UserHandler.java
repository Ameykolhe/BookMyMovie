package com.ameykolhe.bookmymovie.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

public class UserHandler implements Runnable {

	Socket s;
	DataInputStream dis;
	DataOutputStream dos;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	String userName;
	String password;
	Integer type;
	
	public UserHandler(Socket s,DataInputStream dis,DataOutputStream dos,ObjectInputStream ois,ObjectOutputStream oos,String userName , String password , Integer type)
	{
		this.dis = dis;
		this.dos = dos;
		this.ois = ois;
		this.oos = oos;
		this.s = s;
		this.userName = userName;
		this.password = password;
		this.type = type;
	}
	
	@Override
	public void run() {
		System.out.println("Starting Thread : "+Thread.currentThread().getName());
		
		System.out.println("IN UserHandeler Thread");
		
		System.out.println("Stopping Thread  : " + Thread.currentThread().getName());
		
	}

}
