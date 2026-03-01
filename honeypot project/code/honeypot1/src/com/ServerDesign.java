package com;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import Process.RandomCreation;
import Process.ServerReceiver;
import Process.ServerUpdate;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class ServerDesign implements ActionListener{
	
	public JFrame frame;
	public JPanel panel,panel1,panel2;
	public JLabel title,username,password,contact,email,natplace,dob;
	public JTextField userfield,contfield,mailfield,nativefield,dobfield;
        public JPasswordField  passfield;
	public JButton upload,signin;
	public JTextArea serverupdatebox;
	public Font font = new Font("Rockwell",Font.BOLD,20);
	public Font font1 = new Font("Monotype Corsiva",Font.BOLD,17);
	
	public ServerReceiver SRec = new ServerReceiver();
	
	public RandomCreation ranc = new RandomCreation();
        
	
	public void Init(){
            
		FolderCreation();
		SRec.StartReceiver(this);
		Design();
                serverupdatebox.setEditable(false);
                updatelist();
	}
	
        void updatelist()
        {
            try
            {
                FileInputStream fis = new FileInputStream("system.properties");
	            Properties p = new Properties();
	            p.load(fis);
	            String path=p.getProperty("Path");
	            File filelist = new File(path+"ServerData");
	            File[] Fl=filelist.listFiles();
	            String fname = new String();
	            for(int i=0; i<Fl.length; i++)
	            {
	                String temp=Fl[i].getName();
	                fname+=temp+"\n";
	            }          
	            serverupdatebox.setText("");
	            serverupdatebox.setText(fname);
	             fname="";
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        
	public void Design(){
		frame = new JFrame("Server Design");		
		frame.setLayout(null);
		frame.setBounds(100, 100, 700, 500);
                
                ImageIcon imageIcon=new ImageIcon("images/server.jpg");
                Image image = imageIcon.getImage(); // transform it 
                Image newimg = image.getScaledInstance(300, 200,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
                imageIcon = new ImageIcon(newimg); 
                JLabel jlimg=new JLabel(imageIcon);   
                jlimg.setBounds(10, 230, 300, 200);
                frame.add(jlimg);
		
		title = new JLabel("Server Node");
		title.setFont(font);
		title.setBounds(250, 20, 150, 25);
		frame.add(title);
		
		panel = new JPanel();
		panel.setLayout(null);
        panel.setBorder(BorderFactory.createTitledBorder("Upload Form"));
        panel.setBounds(50,50, 270, 170);
        
        serverupdatebox = new JTextArea();
        serverupdatebox.setBounds(25, 20, 180, 100);
        panel.add(serverupdatebox);
        
        upload = new JButton("Upload");
        upload.setBounds(45, 130, 100, 25);
        upload.addActionListener(this);
        panel.add(upload);
        
        frame.add(panel);
        
        panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBorder(BorderFactory.createTitledBorder("Register From"));
        panel1.setBounds(350,50, 300, 370);
        
        username = new JLabel("Username");
        username.setBounds(20, 30, 100, 20);
        username.setFont(font1);
        panel1.add(username);
        
        userfield = new JTextField();        
        userfield.setBounds(150, 30, 125, 25);
        panel1.add(userfield);
        
        password = new JLabel("Password");
        password.setBounds(20, 80, 100, 20);
        password.setFont(font1);
        panel1.add(password);
        
        passfield = new JPasswordField();  
        passfield.setBounds(150, 80, 125, 25);
        panel1.add(passfield);
        
        contact = new JLabel("Contact");
        contact.setBounds(20, 130, 100, 20);
        contact.setFont(font1);
        panel1.add(contact);
        
        contfield = new JTextField();        
        contfield.setBounds(150, 130, 125, 25);
        panel1.add(contfield);
        
        email = new JLabel("E Mail");
        email.setBounds(20, 180, 100, 20);
        email.setFont(font1);
        panel1.add(email);
        
        mailfield = new JTextField();        
        mailfield.setBounds(150, 180, 125, 25);
        panel1.add(mailfield);
        
        natplace = new JLabel("Native Place");
        natplace.setBounds(20, 230, 100, 20);
        natplace.setFont(font1);
        panel1.add(natplace);
        
        nativefield = new JTextField();        
        nativefield.setBounds(150, 230, 125, 25);
        panel1.add(nativefield);
        
        dob = new JLabel("DOB");
        dob.setBounds(20, 280, 100, 20);
        dob.setFont(font1);
        panel1.add(dob);
        
        dobfield = new JTextField();        
        dobfield.setBounds(150, 280, 125, 25);
        panel1.add(dobfield);
        
        signin = new JButton("Register");
        signin.setBounds(160, 330, 100, 25);
        signin.addActionListener(this);
        panel1.add(signin);
        
        frame.add(panel1);
		
		frame.setVisible(true);
	}
	
	public void FolderCreation(){
        try
        {
            Properties prop=new Properties();
            FileInputStream fis=new FileInputStream("system.properties");
            prop.load(fis);
            String path=prop.getProperty("Path");            
            File f=new File(path+"ServerData");
            f.mkdir();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Register")){
			System.out.println("HI----------");
			String password=passfield.getText().trim();	
			String enduser=userfield.getText().trim();
                        if (enduser.equals(""))
                            JOptionPane.showMessageDialog(frame,"Enter Username!");
                        else
                        if (password.equals(""))
                            JOptionPane.showMessageDialog(frame,"Enter Password!");
                        else
                        if (check(password))    
                            SRec.StoreEnduserPassword(enduser, password, contfield.getText(),mailfield.getText(),nativefield.getText(),dobfield.getText());
                        else
                            JOptionPane.showMessageDialog(frame,"Password should be AlphaNumeric!");
		}
		else if(e.getActionCommand().equals("Upload")){
			try{
				ServerUpdate file = new ServerUpdate();
	            file.UpdateData();
	            FileInputStream fis = new FileInputStream("system.properties");
	            Properties p = new Properties();
	            p.load(fis);
	            String path=p.getProperty("Path");
	            File filelist = new File(path+"ServerData");
	            File[] Fl=filelist.listFiles();
	            String fname = new String();
	            for(int i=0; i<Fl.length; i++)
	            {
	                String temp=Fl[i].getName();
	                fname+=temp+"\n";
	            }          
	            serverupdatebox.setText("");
	            serverupdatebox.setText(fname);
	             fname="";
			}
			catch(Exception Ex){
				Ex.printStackTrace();
			}			
		}		
	}
        
        
    boolean check(String s)
	{
		boolean digit=false,alphabet=false;
		
		for (int i=0;i<s.length();i++)
		{
			char c=s.charAt(i);
			int ascii=(int)c;
			System.out.println(ascii);
			if (ascii>=48 && ascii<=57)
			{
				digit=true;
			}
			else
			if ((ascii>=65 && ascii<=90) || (ascii>=97 && ascii<=122))
			{
				alphabet=true;	
			}
			
			if (digit && alphabet)
			break;
			
			
		}
		
		if (digit && alphabet)
			return true;
		else
		 return false;	
	}    
}
