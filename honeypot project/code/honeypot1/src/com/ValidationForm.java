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
import javax.swing.JTextField;

import Process.Callserver;

public class ValidationForm implements ActionListener{
	
	public JFrame frame;
	public JLabel title,namelab,place,userdob,codelab,lab;
	public JTextField namefield,placefield,dobfield,codefield;
	public JButton submit;
	public JPanel panel;
	public Font font = new Font("Rockwell",Font.BOLD,20);
	public Font font1 = new Font("Monotype Corsiva",Font.BOLD,17);
	public Callserver call = new Callserver();
	
	public void Design(){
		frame = new JFrame("Validation Form");		
		frame.setLayout(null);
		frame.setBounds(100, 100, 600, 500);
		
		title = new JLabel("Validation Form");
		title.setBounds(220, 20, 190, 25);
		title.setFont(font);
		frame.add(title);
		
		panel = new JPanel();
	    panel.setLayout(null);
	    panel.setBorder(BorderFactory.createTitledBorder("User Validation"));
	    panel.setBounds(50,80, 500, 350);
	    
	    lab = new JLabel("*Verification Code sent to ur Mail address");
	    lab.setBounds(50, 30, 300, 20);
	    lab.setFont(font1);
        panel.add(lab);
	    
	    namelab = new JLabel("Nodename");
	    namelab.setBounds(80, 70, 120, 20);
	    namelab.setFont(font1);
        panel.add(namelab);
        
        namefield = new JTextField();
        namefield.setText("");
        namefield.setBounds(215, 70, 125, 25);
        panel.add(namefield);
        
        place = new JLabel("Native Place");
        place.setBounds(80, 120, 125, 20);
        place.setFont(font1);
        panel.add(place);
        
        placefield = new JTextField();
        placefield.setText("");
        placefield.setBounds(215, 120, 125, 25);
        panel.add(placefield);
        
        userdob = new JLabel("DOB");
        userdob.setBounds(80, 170, 120, 20);
        userdob.setFont(font1);
        panel.add(userdob);
        
        dobfield = new JTextField();
        dobfield.setText("");
        dobfield.setBounds(215, 170, 125, 25);
        panel.add(dobfield);
        
        codelab = new JLabel("Verification code");
        codelab.setBounds(80, 220, 120, 20);
        codelab.setFont(font1);
        panel.add(codelab);
        
        codefield = new JTextField();
        codefield.setText("");
        codefield.setBounds(215, 220, 125, 25);
        panel.add(codefield);
	    
        submit = new JButton("Submit");
        submit.setFont(font1);
        submit.setBounds(195, 270, 90, 25);
        submit.addActionListener(this);
        panel.add(submit);
	     
	    frame.add(panel);
		
		frame.setVisible(true);
	}
	
	public static void main(String ar[]){
		ValidationForm v = new ValidationForm();
		v.Design();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Submit")){
			if(namefield.getText()==null || placefield.getText()==null || dobfield.getText()==null || codefield.getText()==null){
				JOptionPane.showMessageDialog(null, "Fill all the Field.");
			}else{
				call.SendToHoneypot(namefield.getText(), placefield.getText(), dobfield.getText(), codefield.getText());
			}
		}
	}
}
