package com.ameykolhe.bookmymovie.userInterface;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {

	
	
	Socket s;
	
	DataInputStream dis;
	DataOutputStream dos;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	private static final long serialVersionUID = 1L;
	
	
	JButton signIN;
	JButton signUP;
	
	JLabel userIDLabel;
	JLabel passwordLabel;
	JLabel welcomeMSG;
	
	JLabel MSG;
	
	JTextField userID;
	JPasswordField password;
	
	SignUPFrame signUPFrame;
	
	JButton getSignINButton()
	{
		return signIN;
	}
	
	JButton getSignUPButton()
	{
		return signUP;
	}
	
	
	LoginFrame getLoginFrame()
	{
		return this;
	}
	
	
	@SuppressWarnings({ "unused", "resource" })
	public LoginFrame() throws IOException {
		super("Login");
		
		
		Scanner sc = new Scanner(System.in);
		
		s = new Socket("127.0.0.1",1234);
		
		DataInputStream dis = new DataInputStream(s.getInputStream());
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize(400,400);
		setBounds(0,0,400,400);
		setLayout(null);
		
		Font font = new Font("Serif", Font.PLAIN , 15);
		Font welcomeFont = new Font("Serif", Font.PLAIN , 20);
		
		welcomeMSG = new JLabel("WELCOME TO BookMyMovie");
		welcomeMSG.setFont(welcomeFont);
		welcomeMSG.setBounds(60, 50, 380, 50);
		
		userIDLabel = new JLabel("User ID : ");
		userIDLabel.setFont(font);
		userIDLabel.setBounds(50, 120, 100, 30);
		
		passwordLabel = new JLabel("Password : ");
		passwordLabel.setFont(font);
		passwordLabel.setBounds(50,170 , 100 , 30);
		
		userID = new JTextField();
		userID.setFont(font);
		userID.setBounds(150,120, 200, 30);
		
		password = new JPasswordField();
		password.setBounds(150,170, 200, 30);
		
		signIN = new JButton("Sign IN");
		signIN.setBounds(140, 220, 100 , 30);
		
		signIN.addActionListener(new ActionListener() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					ArrayList ar = new ArrayList();
					String username = userID.getText().toString();
					ar.add(username);
					String pass = new String(password.getPassword());
					System.out.println("Username : " + username + "  password : "+ pass);
					ar.add(pass);
					dos.writeUTF("2");
					oos.writeObject(ar);
					String str = dis.readUTF().toString();
					if(str.equals("Login Successful"))
					{
						Integer type1 = (Integer)ois.readObject();
						if(type1.intValue() == 0)					//User
						{
							UserFrame ui = new UserFrame(s,dis,dos,ois,oos,username);
							Thread t = new Thread(ui);
							t.start();
						}
						else if(type1.intValue() == 1)				//Owner
						{
							OwnerFrame ui = new OwnerFrame(s,dis,dos,ois,oos,username);
							Thread t = new Thread(ui);
							t.start();
						}
						dispose();
					}
					JOptionPane.showMessageDialog(new JFrame(),str);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
		}
		);
		
		signUP = new JButton("Sign UP");
		signUP.setBounds(280, 320, 100, 20);
		
		signUP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				signUPFrame = new SignUPFrame(s,dis,dos,ois,oos,getLoginFrame());
				signUPFrame.setVisible(true);
			}
			
		});
		
		add(welcomeMSG);
		add(passwordLabel);
		add(userIDLabel);
		add(userID);
		add(password);
		add(signUP);
		add(signIN);
		
		
	}
	
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		LoginFrame frame = new LoginFrame();
		frame.setVisible(true);
		
	}
	

}
