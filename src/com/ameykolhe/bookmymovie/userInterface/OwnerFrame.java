package com.ameykolhe.bookmymovie.userInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;
public class OwnerFrame extends JFrame implements  Runnable {

	private static final long serialVersionUID = 6982690865977969166L;
	JButton button1,button2;
	JLabel labelback;
	
	JPanel pan1,pan2,pan3,pan4,pan5,pan6;
	JButton pan_button1,pan_button2,pan_button3,pan_button4,pan_button5,pan_button6;
	JLabel pan_label1,pan_label2,pan_label3,pan_label4,pan_label5,pan_label6;
	@SuppressWarnings("rawtypes")
	JComboBox movie_list,theatre_list,screen_list;
	
	JTextField price;
	
	JSpinner hours,minutes;

	SqlDateModel model;
	JDatePanelImpl datePanel;
	JDatePickerImpl datePicker;
	
	SpinnerModel hvalue,mvalue;
	
	
	Socket s;
	
	DataInputStream dis;
	DataOutputStream dos;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	
	@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
	public OwnerFrame(Socket s,DataInputStream dis,DataOutputStream dos,ObjectInputStream ois,ObjectOutputStream oos , String userName)
	{
		super("BookMyMovie : " + userName);
		
		this.dis = dis;
		this.dos = dos;
		this.ois = ois;
		this.oos = oos;
		this.s = s;
		
		labelback=new JLabel("OWNER PAGE");
		labelback.setForeground(Color.BLUE);
		labelback.setFont(new Font("Serif",Font.BOLD,20));
		
		
		
		
		button1=new JButton(new AbstractAction("SCHEDULE") {
	        @Override
	        public void actionPerformed( ActionEvent e ) {
	            
	        	try{
		        	
	        		dos.writeUTF("1");
	        		button2.setEnabled(false);
		        	pan1.setVisible(true);
		        	pan2.setVisible(false);
		            pan3.setVisible(false);
		            pan4.setVisible(false);
		            pan5.setVisible(false);
		            pan6.setVisible(false);
		            
		            ArrayList<String> movieNames = (ArrayList<String>)ois.readObject();
					movie_list=new JComboBox(movieNames.toArray());
					movie_list.setBounds(250, 30, 300, 50);
					pan1.add(movie_list);
		            
	        	}
	        	catch(Exception e1)
	        	{
	        		e1.printStackTrace();
	        	}
	        }
	    });

		button2=new JButton(new AbstractAction("LOGOUT") {
	        @Override
	        public void actionPerformed( ActionEvent e ) {
	        	try
	        	{
		        	dos.writeUTF("2");
		        	
		            pan1.setVisible(false);
		            pan2.setVisible(false);
		            pan3.setVisible(false);
		            pan4.setVisible(false);
		            pan5.setVisible(false);
		            pan6.setVisible(false);
		            dispose();
	        	}
	        	catch(Exception e1)
	        	{
	        		e1.printStackTrace();
	        	}
	        }
	    });
		
		pan1=new JPanel();
		pan2=new JPanel();
		pan3=new JPanel();
		pan4=new JPanel();
		pan5=new JPanel();
		pan6=new JPanel();
		
		pan1.setVisible(false);
		pan2.setVisible(false);
		pan3.setVisible(false);
		pan4.setVisible(false);
		pan5.setVisible(false);
		pan6.setVisible(false);
			
		pan_button1=new JButton(new AbstractAction("NEXT") {
			
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					Integer index = new Integer(movie_list.getSelectedIndex() + 1);
					dos.writeUTF(index.toString());
					
					ArrayList<String> theatreNames = (ArrayList<String>)ois.readObject();
					theatre_list=new JComboBox(theatreNames.toArray());
					theatre_list.setBounds(250, 30, 300, 50);
					pan2.add(theatre_list);
					
					pan_button1.setEnabled(false);
					pan2.setVisible(true);
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
			}
		});

		pan_button2=new JButton(new AbstractAction("NEXT") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					Integer index = new Integer(theatre_list.getSelectedIndex() + 1);
					dos.writeUTF(index.toString());
					
					ArrayList screenNum = (ArrayList)ois.readObject();
					for(int i = 0 ; i< screenNum.size() ;i++ )
					{
						screenNum.set(i, "Screen " + ((Integer)screenNum.get(i)).toString() );
					}
					screen_list=new JComboBox(screenNum.toArray());
					screen_list.setBounds(250, 30, 300, 50);
					pan3.add(screen_list);
					
					pan_button2.setEnabled(false);
					pan3.setVisible(true);
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
			}
		});

		pan_button3=new JButton(new AbstractAction("NEXT") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					Integer index = new Integer(screen_list.getSelectedIndex() + 1);
					dos.writeUTF(index.toString());
					
					pan_button3.setEnabled(false);
					pan4.setVisible(true);
				
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
			}
		});
		
		pan_button4=new JButton(new AbstractAction("NEXT") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					System.out.println("IN date Picker");
					String date = datePicker.getModel().getValue().toString();
					
					dos.writeUTF(date);
					
					pan_button4.setEnabled(false);
					pan5.setVisible(true);
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
			}
		});
		
		pan_button5=new JButton(new AbstractAction("NEXT") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					String hrs = hvalue.getValue().toString();
					String min = mvalue.getValue().toString();
					dos.writeUTF(hrs + ":" + min + ":00");
					
					pan_button5.setEnabled(false);
					pan6.setVisible(true);
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
			}
		});

		pan_button6=new JButton(new AbstractAction("FINISH") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					dos.writeUTF(price.getText());
					
					String msg = dis.readUTF().toString();
					
					JOptionPane.showMessageDialog(new JFrame(),msg);
					
					
					pan_button1.setEnabled(true);
					pan_button2.setEnabled(true);
					pan_button3.setEnabled(true);
					pan_button4.setEnabled(true);
					pan_button5.setEnabled(true);
					
					button2.setEnabled(true);
					
					pan1.setVisible(false);
					pan2.setVisible(false);
					pan3.setVisible(false);
					pan4.setVisible(false);
					pan5.setVisible(false);
					pan6.setVisible(false);
				
					
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
			}
		});



		pan_label1=new JLabel("MOVIES");
		pan_label2=new JLabel("THEATRE");
		pan_label3=new JLabel("SCREENS");
		pan_label4=new JLabel("DATE");
		pan_label5=new JLabel("TIME");
		pan_label6=new JLabel("PRICE");
		
		
		price=new JTextField();
		
		labelback.setBounds(425, 30, 1000, 30);
		button1.setBounds(30,80,200,50);
		button2.setBounds(30,200,200,50);
		
		pan1.setBounds(250,100,900,100);
		pan2.setBounds(250,200,900,100); 
		pan3.setBounds(250,300,900,100); 
		pan4.setBounds(250,400,900,100); 
		pan5.setBounds(250,500,900,100);
		pan6.setBounds(250,600,900,100); 

		pan1.setLayout(null);
		pan2.setLayout(null);
		pan3.setLayout(null);
		pan4.setLayout(null);
		pan5.setLayout(null);
		pan6.setLayout(null);
		

		pan_label1.setBounds(50,30,150,50);
		pan_button1.setBounds(600, 30,200, 50);
		
		pan_label2.setBounds(50,30,150,50);
		pan_button2.setBounds(600,30,200, 50);
		
		pan_label3.setBounds(50,30,150,50);
		pan_button3.setBounds(600,30,200, 50);
		
		pan_label4.setBounds(50,30,150,50);
		pan_button4.setBounds(600,30,200, 50);
		
		model = new SqlDateModel();
		
		datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		datePicker.setBounds(250, 50, 200, 100);
		pan4.add(datePicker);
		
		hvalue =  
	             new SpinnerNumberModel(12, //initial value  
	                0, //minimum value  
	                23, //maximum value  
	                1); //step  
	    hours = new JSpinner(hvalue);   
	    hours.setBounds(100,100,50,30);    

	  mvalue =  
	   	             new SpinnerNumberModel(0, //initial value  
	   	                0, //minimum value  
	   	                59, //maximum value  
	   	                15); //step  
	   	  minutes = new JSpinner(mvalue);   
	   	            minutes.setBounds(100,100,50,30);    
	
	   	pan_label5.setBounds(50,30,150,50);
	   	hours.setBounds(250, 30, 100, 30);
	   	minutes.setBounds(375, 30, 100, 30);
	   	pan_button5.setBounds(600,30,200, 50);
	   	
	   	pan_label6.setBounds(50,30,150,50);
		price.setBounds(300, 30, 200, 50);
		pan_button6.setBounds(600,30,200, 50);
		
	
		
		add(labelback);
		add(button1);
		add(button2);		
		add(pan1);
		add(pan2);
		add(pan3);
		add(pan4);
		add(pan5);
		add(pan6);
		
		pan1.add(pan_label1);
		pan1.add(pan_button1);

		pan2.add(pan_label2);
		pan2.add(pan_button2);

		pan3.add(pan_label3);
		pan3.add(pan_button3);

		pan4.add(pan_label4);
		pan4.add(pan_button4);
		
		pan5.add(pan_label5);
		pan5.add(hours);
		pan5.add(minutes);
		pan5.add(pan_button5);
		
		pan6.add(pan_label6);
		pan6.add(price);
		pan6.add(pan_button6);
		
		
		setSize(1000, 1000);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	@Override
	public void run() {
		
		setVisible(true);
	}
};

