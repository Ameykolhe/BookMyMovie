package com.ameykolhe.bookmymovie.userInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SignUPFrame extends JFrame implements ActionListener{


	Socket s;
	
	DataInputStream dis;
	DataOutputStream dos;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	JLabel label1,label2,label3;
	JTextField text;
	JButton button;
	JPasswordField password;
	JRadioButton owner_button,user_button;
	
	LoginFrame loginRef;
	
	private static final long serialVersionUID = 1L;

	public SignUPFrame(Socket s,DataInputStream dis,DataOutputStream dos,ObjectInputStream ois,ObjectOutputStream oos, LoginFrame loginRef) {
		
		super("Sign UP");
		
		this.dis = dis;
		this.dos = dos;
		this.ois = ois;
		this.oos = oos;
		this.s = s;
		
		this.loginRef = loginRef;
		
		label1 = new JLabel("SIGN UP");
		label1.setForeground(Color.blue);
		label1.setFont(new Font("Serif", Font.BOLD, 20));
		
		label2=new JLabel("Username");
		label3=new JLabel("Password");
		text=new JTextField();
		password=new JPasswordField();
		button=new JButton("Proceed");
		owner_button=new JRadioButton("Owner");
		user_button=new JRadioButton("User");
		
		
		
		label1.setBounds(450, 30, 400, 30);
		label2.setBounds(180, 70, 200, 30);
		label3.setBounds(180, 110, 200, 30);
		text.setBounds(500, 70, 200, 30);
		password.setBounds(500, 110, 200, 30);
		button.setBounds(350, 360, 100, 30);
		
		ButtonGroup grp = new ButtonGroup();
		grp.add(owner_button);
		grp.add(user_button);
		owner_button.setBounds(200, 200, 150, 150);  
		user_button.setBounds(600, 200, 150, 150); 
		user_button.setSelected(true);
		
		add(label1);
		add(label2);
		add(label3);
		add(text);
		add(button);
		add(password);
		add(owner_button);
		add(user_button);
		button.addActionListener(this);
		
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1000, 1000);
	
	}

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try
		{
			ArrayList ar = new ArrayList();
			
			String username = text.getText().toString();
			ar.add(username);
			
			String pass = new String(password.getPassword());
			System.out.println("Username : " + username + "  password : "+ pass);
			ar.add(pass);
			
			String type;
			if(owner_button.isSelected())
			{
				type = "owner";
			}
			else
			{
				type = "user";
			}
			
			ar.add(type);
			dos.writeUTF("1");
			oos.writeObject(ar);
			
			
			String str = dis.readUTF().toString();
			if(str.equals("Signup Successful"))
			{
				dispose();
				loginRef.setVisible(true);
			}
			JOptionPane.showMessageDialog(new JFrame(),str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
