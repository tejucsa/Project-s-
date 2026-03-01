package Process;

import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;

public class Callserver {
	
	public String serversysname;
	public String serverport;
	
	public void getServerDetails(){
		try {
			FileInputStream fis = new FileInputStream("system.properties");
			Properties pro = new Properties();
			pro.load(fis);
			serversysname=pro.getProperty("IDSSystem");
			serverport=pro.getProperty("IDSPort");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void DataCall(String srcname,String srcport,String pass,String status){
		try{
			getServerDetails();
			String sysname=InetAddress.getLocalHost().getHostName();
			System.out.println("------------------"+serverport+"------------"+serversysname);
			Socket s = new Socket(serversysname,Integer.parseInt(serverport));
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject("Login");
			oos.writeObject(srcname);
			oos.writeObject(srcport);
			oos.writeObject(sysname);			
			oos.writeObject(pass);
			oos.close();
			System.out.println("Send to IDS......");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void SearchRequestToServer(String srcname,String srcport,String fname,String status){
		try{
			getServerDetails();
			String sysname=InetAddress.getLocalHost().getHostName();
			Socket s = new Socket(serversysname,Integer.parseInt(serverport));
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject("Search");
			oos.writeObject(srcname);
			oos.writeObject(srcport);
			oos.writeObject(sysname);			
			oos.writeObject(fname);
			oos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void UpdateRequestToServer(String srcname,String srcport,String fname,String fdata){
		try{
			getServerDetails();
			String sysname=InetAddress.getLocalHost().getHostName();
			Socket s = new Socket(serversysname,Integer.parseInt(serverport));
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject("Update");
			oos.writeObject(srcname);
			oos.writeObject(srcport);
			oos.writeObject(sysname);			
			oos.writeObject(fname);
			oos.writeObject(fdata);
			oos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void SendToHoneypot(String name,String place,String dob,String code){
		try{
			FileInputStream fis = new FileInputStream("system.properties");
			Properties pro = new Properties();
			pro.load(fis);			
			String Hsysname=pro.getProperty("HoneypotSystem");
			String Hport=pro.getProperty("HoneypotPort");
			Socket s = new Socket(Hsysname,Integer.parseInt(Hport));
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject("UserDetails");
			oos.writeObject(name);
			oos.writeObject(place);
			oos.writeObject(dob);
			oos.writeObject(code);
			oos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
