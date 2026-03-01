package com;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;


public class Main implements ActionListener{

	public JFrame frame;
	public JLabel title;
	public JButton node,IDS,server,exit;
	public Font font = new Font("Monotype Corsiva",Font.BOLD,20);
	public Font font1 = new Font("Monotype Corsiva",Font.BOLD,18);
        

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        try
        {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception e)
        {
                e.printStackTrace();
        }
         new Main();
	}

	public Main(){

		frame = new JFrame("IDS HoneyPot");
                
		frame.setLayout(null);
		frame.setBounds(100, 100, 700, 300);
                //    frame.getContentPane().setBackground(Color.red);
                
                ImageIcon imageIcon=new ImageIcon("images/main.jpg");
                Image image = imageIcon.getImage(); // transform it 
                Image newimg = image.getScaledInstance(700, 300,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
                imageIcon = new ImageIcon(newimg); 
                JLabel jlimg=new JLabel(imageIcon);   
                Container cp=frame.getContentPane();
                cp.add(jlimg);
                jlimg.setBounds(0,0,700,300);
                
		title = new JLabel("Honeypot");
		title.setFont(font);
		title.setForeground(Color.white);
		title.setBounds(80,10,800,30);
        jlimg.add(title);
        

        node = new JButton("Node");
        node.setFont(font1);
        node.setBounds(110,150,80,25);
        node.addActionListener(this);
        jlimg.add(node);

        IDS = new JButton("IDS");
        IDS.setFont(font1);
        IDS.setBounds(230,150,80,25);
        IDS.addActionListener(this);
        jlimg.add(IDS);

        server = new JButton("Server");
        server.setFont(font1);
        server.setBounds(350,150,80,25);
        server.addActionListener(this);
        jlimg.add(server);

        exit = new JButton("Exit");
        exit.setFont(font1);
        exit.setBounds(470,150,80,25);
        exit.addActionListener(this);
        jlimg.add(exit);

		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if(e.getActionCommand().equals("Node")){
			NodeDesign node = new NodeDesign();
			node.Init();
		}
		else if(e.getActionCommand().equals("IDS")){
			IDSDesign ids = new IDSDesign();
			ids.Init();
		}
		else if(e.getActionCommand().equals("Server")){
			ServerDesign server = new ServerDesign();
			server.Init();
		}
		else if(e.getActionCommand().equals("Exit")){
                            System.exit(0);
		}

	}

}
