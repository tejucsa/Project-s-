package com;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import Process.HoneyReceiver;
import java.awt.Image;
import javax.swing.ImageIcon;

public class HoneypotDesign implements ActionListener{
	
	public JFrame frame;
	public JLabel title,attlab;
	public JPanel panel;
	public JTextArea attackfield;
	public DefaultTableModel Dlog;
	public JTable logtable;
	public JButton Valid;
	public Font font = new Font("Rockwell",Font.BOLD,20);
	public HoneyReceiver Hrec = new HoneyReceiver();
	
        
	public void Design(){
           
		Hrec.startpotReceiver(this);
		frame = new JFrame("HoneyPot Design");		
		frame.setLayout(null);
		frame.setBounds(100, 100, 1090, 450);
                
                ImageIcon imageIcon=new ImageIcon("images/honeypot.jpg");
                Image image = imageIcon.getImage(); // transform it 
                Image newimg = image.getScaledInstance(300, 200,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
                imageIcon = new ImageIcon(newimg); 
                JLabel jlimg=new JLabel(imageIcon);   
                jlimg.setBounds(740, 20, 300, 300);
                frame.add(jlimg);
		
		title = new JLabel("HoneyPot");
		title.setFont(font);
		title.setBounds(220, 20, 220, 25);
		frame.add(title);
		
		panel = new JPanel();
		panel.setLayout(null);
        panel.setBorder(BorderFactory.createTitledBorder("Attacker Log"));
        panel.setBounds(50,50, 685, 330);   
        
        Dlog = new DefaultTableModel();
        logtable = new JTable(Dlog);
        JScrollPane CHscrollpane = new JScrollPane(logtable);
        Dlog.addColumn("Node");
        Dlog.addColumn("Systemname");
        Dlog.addColumn("Process");
        Dlog.addColumn("Status");
        CHscrollpane.setBounds(25, 25, 400, 160);
        panel.add(CHscrollpane);
        
        attlab = new JLabel("Attacker Name");
        attlab.setBounds(480, 15, 120, 25);
        panel.add(attlab);
        
        attackfield = new JTextArea();
        attackfield.setBounds(485, 40, 180, 100);
        panel.add(attackfield);
        
        Valid = new JButton("Attacker Validation");
        Valid.setBounds(320, 280, 160, 25);
        Valid.addActionListener(this);
        panel.add(Valid);
        
        frame.add(panel);
		
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Attacker Validation")){
			String attname=JOptionPane.showInputDialog("Enter the Attacker Name.");
			Hrec.SendToClient(attname);
		}
	}
}
