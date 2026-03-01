package com;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Process.IDSReceiver;
import java.awt.Image;
import javax.swing.ImageIcon;

public class IDSDesign implements ActionListener{
	
	public JFrame frame;
	public JPanel panel;
	public JLabel title;
	public DefaultTableModel Dlog;
	public JTable logtable;
	public JButton HoneyPot;
	public Font font = new Font("Rockwell",Font.BOLD,20);
	public IDSReceiver IDSRec = new IDSReceiver();
	
        
	public void Init(){
          
		IDSRec.Receiverstart(this);
		Design();
	}
	
	public void Design(){
		frame = new JFrame("IDS Design");		
		frame.setLayout(null);
		frame.setBounds(100, 100, 900, 350);
                
                ImageIcon imageIcon=new ImageIcon("images/ids.jpg");
                Image image = imageIcon.getImage(); // transform it 
                Image newimg = image.getScaledInstance(300, 300,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
                imageIcon = new ImageIcon(newimg); 
                JLabel jlimg=new JLabel(imageIcon);   
                jlimg.setBounds(520, 20, 300, 300);
                frame.add(jlimg);
		
		title = new JLabel("Intrusion Detection");
		title.setFont(font);
		title.setBounds(220, 20, 220, 25);
		frame.add(title);
		
		panel = new JPanel();
		panel.setLayout(null);
        panel.setBorder(BorderFactory.createTitledBorder("Log Data"));
        panel.setBounds(50,50, 480, 250);    
        
        Dlog = new DefaultTableModel();
        logtable = new JTable(Dlog);
        JScrollPane CHscrollpane = new JScrollPane(logtable);
        Dlog.addColumn("Node");
        Dlog.addColumn("Systemname");
        Dlog.addColumn("Process");
        Dlog.addColumn("Status");
        CHscrollpane.setBounds(25, 25, 400, 160);
        panel.add(CHscrollpane);
        
        HoneyPot = new JButton("Honeypot");
        HoneyPot.setBounds(200, 205, 100, 25);
        HoneyPot.addActionListener(this);
        panel.add(HoneyPot);
        
        frame.add(panel);
		
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Honeypot")){
			HoneypotDesign Hdesign = new HoneypotDesign();
			Hdesign.Design();
		}
	}

}
