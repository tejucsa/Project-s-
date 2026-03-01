package Process;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.NodeDesign;
import com.ValidationForm;

public class NodeReceiver extends Thread{
	
	public String Nname;
	public String Nport;
	public NodeDesign nodeobject;
	ValidationForm v = new ValidationForm();
	
	public void StartReceiver(String name,String port,NodeDesign nodeobject){
		Nname=name;
		Nport=port;
		this.nodeobject=nodeobject;
		start();
	}
	
	public void run(){
		try{
			ServerSocket ss =new ServerSocket(Integer.parseInt(Nport));
			while(true){
				System.out.println("Node Receive......"+Nport);
				Socket s = ss.accept();
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				String keydata=(String)ois.readObject();
				if(keydata.equals("LoginStatus")){
					System.out.println("Response receive.....");
					String status=(String)ois.readObject();	
					if(status.equals("Success")){
						nodeobject.loginscreen=false;
						nodeobject.nodescreen=true;
						nodeobject.frame.setVisible(false);
						nodeobject.Design();
					}else if(status.equals("Faliure")){
						JOptionPane.showMessageDialog(null, "Incorrect Username & Password");
					}
				}
				else if(keydata.equals("FileRetrive")){
					String filestatus=(String)ois.readObject();
					if(filestatus.equals("Available")){
						String filedata=(String)ois.readObject();
						nodeobject.retrivebox.setText(filedata);
					}else{
						JOptionPane.showMessageDialog(null, "File Not Available...");
					}
				}
				else if(keydata.equals("FileUpdate")){
					String updatestatus=(String)ois.readObject();
					if(updatestatus.equals("Success")){
						JOptionPane.showMessageDialog(null, "File Updated Successfully...");
					}else{
						JOptionPane.showMessageDialog(null, "File Not Updated...");
					}
				}
				else if(keydata.equals("UpdateAlert")){
					JOptionPane.showMessageDialog(null, "File Size exceed the Limit......");
				}
				else if(keydata.equals("Verification")){
					int n = JOptionPane.showConfirmDialog(null,"U are not Attacker means?",  "Give Yes or No",JOptionPane.YES_NO_OPTION);
					if(true){						
						v.Design();
					}					
				}
				else if(keydata.equals("ValidationStatus")){
					String ValidStatus=(String)ois.readObject();
					if(ValidStatus.equals("Success")){
						JOptionPane.showMessageDialog(null, "U are Valid User.....");
						v.frame.setVisible(false);
					}else{
						JOptionPane.showMessageDialog(null, "U are Not Valid User.......");
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
