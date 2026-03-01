package com;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Process.Callserver;
import Process.FileChooser;
import Process.NodeReceiver;
import Process.RandomCreation;

public class NodeDesign extends Thread implements ActionListener{

	public JFrame frame;
	public JLabel logtitle,logname,logpass,labnode,nodename,labport,nodeport;
	public JLabel sysnamelab,sysname,retrivelab,updatelab;
	public JTextField namefield,searchfield;
	public JTextArea retrivebox,updatebox;
	public JPasswordField passfield;
	public JButton login,search,browse,update;
	public JPanel panel,panel1,panel2;
	public ButtonGroup bgroup = new ButtonGroup();
	public JRadioButton normal,attack;
	public Font font = new Font("Rockwell",Font.BOLD,20);
	public Font font1 = new Font("Monotype Corsiva",Font.BOLD,17);
	public RandomCreation ren = new RandomCreation();
	public Callserver sendinform= new Callserver();
	public boolean loginscreen=true;
	public boolean nodescreen=false;
	public String Nodename;//=ren.Nodename();
	public String Nodeport=ren.Nodeport();
	public NodeReceiver Nrec = new NodeReceiver();
	
	public void Init(){
		Nrec.StartReceiver(Nodename, Nodeport, this);
		Design();
	}
	
	public void Design(){
		frame = new JFrame("Node Design");		
		frame.setLayout(null);
		frame.setBounds(100, 100, 800, 510);
		
		if(loginscreen==true){
			
			logtitle = new JLabel("Login Form");
			logtitle.setFont(font);
			logtitle.setForeground(Color.BLACK);
			logtitle.setBounds(350,20,800,30);
	        frame.add(logtitle);
	        
	        panel = new JPanel();
	        panel.setLayout(null);
	        panel.setBorder(BorderFactory.createTitledBorder(""));
	        panel.setBounds(175,100, 400, 280);
	        
	        logname = new JLabel("Username");
	        logname.setBounds(50, 80, 100, 20);
	        logname.setFont(font1);
	        panel.add(logname);
	        
	        namefield = new JTextField();
	       // namefield.setText(Nodename);
	        namefield.setBounds(185, 80, 125, 25);
	        panel.add(namefield);
	        
	        logpass = new JLabel("Password");
	        logpass.setBounds(50, 130, 100, 20);
	        logpass.setFont(font1);
	        panel.add(logpass);
	        
	        passfield = new JPasswordField();
	        passfield.setEchoChar('@');
	        passfield.setBounds(185, 130, 125, 25);
	        panel.add(passfield);
	        
	        login = new JButton("Login");
	        login.setFont(font1);
	        login.setBounds(195, 180, 90, 25);
	        login.addActionListener(this);
	        panel.add(login);
	        
	        normal = new JRadioButton("Normal");
	        normal.setFont(font1);
	        normal.setBounds(80, 230, 120, 25);
	        bgroup.add(normal);
	       // panel.add(normal);
	        
	        attack = new JRadioButton("Attack");
	        attack.setFont(font1);
	        attack.setBounds(250, 230, 120, 25);
	        bgroup.add(attack);	        
	        normal.setSelected(true);
	        //panel.add(attack);
	        	       	        	        
	        frame.add(panel);	        	       
			
		}else if(nodescreen==true){
			
			panel1 = new JPanel();
			panel1.setLayout(null);
	        panel1.setBorder(BorderFactory.createTitledBorder("Node Details"));
	        panel1.setBounds(50, 35, 700, 80);
		        
			labnode = new JLabel("Nodename");
			labnode.setFont(font1);
			labnode.setForeground(Color.BLACK);
			labnode.setBounds(50, 30, 120, 30);
			panel1.add(labnode);
	        
	        nodename = new JLabel("");
	        nodename.setText(Nodename);
	        nodename.setFont(font1);
	        nodename.setForeground(Color.BLACK);
	        nodename.setBounds(150,30,120,30);
	        panel1.add(nodename);
	        
	        labport = new JLabel("Nodeport");
	        labport.setFont(font1);
	        labport.setForeground(Color.BLACK);
	        labport.setBounds(290,30,120,30);
	        panel1.add(labport);
	        
	        nodeport = new JLabel("");
	        nodeport.setText(ren.Nodeport());
	        nodeport.setFont(font1);
	        nodeport.setForeground(Color.BLACK);
	        nodeport.setBounds(380,30,120,30);
	        panel1.add(nodeport);
	        
	        sysnamelab = new JLabel("SystemName");
	        sysnamelab.setFont(font1);
	        sysnamelab.setForeground(Color.BLACK);
	        sysnamelab.setBounds(500,30,120,30);
	        panel1.add(sysnamelab);
	        
	        sysname = new JLabel("");
	        try {
				sysname.setText(InetAddress.getLocalHost().getHostName());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        sysname.setFont(font1);
	        sysname.setForeground(Color.BLACK);
	        sysname.setBounds(610,30,120,30);
	        panel1.add(sysname);
	        
	        frame.add(panel1);
	        
	    	panel2 = new JPanel();
	    	panel2.setLayout(null);
	    	panel2.setBorder(BorderFactory.createTitledBorder("Data Update and Retrive"));
	    	panel2.setBounds(50, 135, 700, 300);
	    	
	    	searchfield = new JTextField();
	    	searchfield.setBounds(50, 50, 480, 25);
	    	panel2.add(searchfield);
	    	
	    	search = new JButton("Search");
	    	search.setFont(font1);
	    	search.setBounds(550, 50, 120, 25);
	    	search.addActionListener(this);
	    	panel2.add(search);
	    	
	    	retrivelab = new JLabel("Retrive Data");
	    	retrivelab.setBounds(50, 100, 195, 20);
	    	panel2.add(retrivelab);
	    	
	    	retrivebox = new JTextArea();
	    	JScrollPane retrivepane = new JScrollPane(retrivebox);
	    	retrivepane.setBounds(55, 125, 195, 100);
	    	panel2.add(retrivepane);
	    	
	    	updatelab = new JLabel("Update Data");
	    	updatelab.setBounds(400, 100, 195, 20);
	    	panel2.add(updatelab);
	    	
	    	updatebox = new JTextArea();
	    	JScrollPane updatepane = new JScrollPane(updatebox);
	    	updatepane.setBounds(405, 125, 195, 100);
	    	panel2.add(updatepane);
	    	
	    	browse = new JButton("Browse");
	    	browse.setFont(font1);
	    	browse.setBounds(200, 250, 110, 25);
	    	browse.addActionListener(this);
	    	panel2.add(browse);
	    	
	    	update = new JButton("Update");
	    	update.setFont(font1);
	    	update.setBounds(400, 250, 110, 25);
	    	update.addActionListener(this);
	    	panel2.add(update);
	    	
	    	frame.add(panel2);
	        	        
		}				
				
		frame.setVisible(true);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Login")){
                        Nodename=namefield.getText().trim();
			String password=passfield.getText();
                        if (Nodename.equals("") || password.equals(""))
                        {
                            JOptionPane.showMessageDialog(frame,"Enter UserName/Password!");
                        }
                        else
                        {
			if(normal.isSelected()){
					sendinform.DataCall(Nodename, Nodeport, password, "Login");
			}
                        else if(attack.isSelected())
                        {
				for(int i=0;i<3;i++)
                                {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					sendinform.DataCall(Nodename, Nodeport, password, "Login");
				}
			}
                        }
			
		}
		else if(e.getActionCommand().equals("Browse")){
			FileChooser filech = new FileChooser();
			try {
				String data=filech.selectmsg();
				updatebox.setText(data);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getActionCommand().equals("Search")){
			String searchdata=searchfield.getText();
			if(searchdata.equals("")){
				JOptionPane.showMessageDialog(null, "Enter the FileName");
			}else{
				sendinform.SearchRequestToServer(Nodename, Nodeport, searchdata, "Search");
			}
		}
		else if(e.getActionCommand().equals("Update")){
			String filename=JOptionPane.showInputDialog("Enter The Filename");
			if(!filename.equals("")){
				sendinform.UpdateRequestToServer(Nodename, Nodeport, filename, updatebox.getText());
			}
		}
		
	}
}
