package com.ameykolhe.bookmymovie.userInterface;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUPFrame extends JFrame {


	Socket s;
	
	DataInputStream dis;
	DataOutputStream dos;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	JLabel userIDLabel;
	JLabel passwordLabel;
	JLabel welcomeMSG;
	
	JLabel MSG;
	
	JTextField userID;
	JPasswordField password;
	
	private static final long serialVersionUID = 1L;

	public SignUPFrame(Socket s,DataInputStream dis,DataOutputStream dos,ObjectInputStream ois,ObjectOutputStream oos) {
		
		super("Sign UP");
		
		
		this.dis = dis;
		this.dos = dos;
		this.ois = ois;
		this.oos = oos;
		this.s = s;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 400, 400);
	}
}
