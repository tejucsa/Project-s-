package Process;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.IDSDesign;

public class IDSReceiver extends Thread{
	
	public IDSDesign IDSobject;
	public Vector<String> blocklist = new Vector<String>();
	public HashMap<String, Integer> clientstatus= new HashMap<String, Integer>();
	public Vector<String> attackername = new Vector<String>();
	
	public void Receiverstart(IDSDesign IDSobject){
		this.IDSobject=IDSobject;
		start();
	}
	
	public void run(){
		try{
			FileInputStream fis = new FileInputStream("system.properties");
			Properties pro = new Properties();
			pro.load(fis);			
			String IDSprot=pro.getProperty("IDSPort");
			System.out.println("IDS Receiver Start......."+IDSprot);
			ServerSocket ss =new ServerSocket(Integer.parseInt(IDSprot));
			while(true){
				Socket s = ss.accept();
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				String keydata=(String)ois.readObject();
				System.out.println("IDS Receiver Start......."+IDSprot);
				if(keydata.equals("Login")){
					System.out.println("IDS Receive......");
					String srcname=(String)ois.readObject();
					String srcport=(String)ois.readObject();
					String sysname=(String)ois.readObject();
					String srcpass=(String)ois.readObject();
					String status="Login";
					if(!attackername.contains(srcname)){
						InformationUpdate();
						updateToTable(srcname,sysname,status,"Normal");
						SendToServer(status,srcname,srcport,sysname,srcpass);
					}					
				}
				else if(keydata.equals("Search")){
					String srcname=(String)ois.readObject();
					String srcport=(String)ois.readObject();
					String sysname=(String)ois.readObject();
					String filename=(String)ois.readObject();
					String status="Search";
					if(!attackername.contains(srcname)){
						updateToTable(srcname, sysname, status, "Normal");
						SendToServer(status, srcname, srcport, sysname, filename);
					}					
				}
				else if(keydata.equals("Update")){
					String srcname=(String)ois.readObject();
					String srcport=(String)ois.readObject();
					String sysname=(String)ois.readObject();
					String filename=(String)ois.readObject();
					String filedata=(String)ois.readObject();
					String status="Update";
					if(!attackername.contains(srcname)){
						boolean allow=CheckUpdateStatus(filedata,srcname,srcport,sysname,status);
						if(allow){
							updateToTable(srcname, sysname, status, "Normal");
							UpdateToServer(status, srcname, srcport, sysname, filename,filedata);
						}	
					}								
				}
				else if(keydata.equals("RemoveAttacker")){
					String removename=(String)ois.readObject();
					attackername.remove(removename);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void InformationUpdate(){
		try{
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void SendToServer(String stat,String sname,String sport,String sysname,String pass){
		try{
			FileInputStream fis = new FileInputStream("system.properties");
			Properties pro = new Properties();
			pro.load(fis);			
			String serversysname=pro.getProperty("ServerSystem");
			String serverport=pro.getProperty("ServerPort");
			String ownname=InetAddress.getLocalHost().getHostName();
			Socket s = new Socket(serversysname,Integer.parseInt(serverport));
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(stat);
			oos.writeObject(sname);
			oos.writeObject(sport);
			oos.writeObject(sysname);			
			oos.writeObject(pass);
			oos.close();
			System.out.println("Send to Server......");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void UpdateToServer(String stat,String username,String userport,String sysname,String filename,String filedata){
		try{
			FileInputStream fis = new FileInputStream("system.properties");
			Properties pro = new Properties();
			pro.load(fis);			
			String serversysname=pro.getProperty("ServerSystem");
			String serverport=pro.getProperty("ServerPort");
			String ownname=InetAddress.getLocalHost().getHostName();
			Socket s = new Socket(serversysname,Integer.parseInt(serverport));
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(stat);
			oos.writeObject(username);
			oos.writeObject(userport);
			oos.writeObject(sysname);			
			oos.writeObject(filename);
			oos.writeObject(filedata);
			oos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean CheckUpdateStatus(String fdata,String clientname,String clientport,String clientsysname,String stat){
		boolean status=false;
		try{
			byte[] by = fdata.getBytes();
			int size=by.length;
			System.out.println("----------------------------->"+size);
			if(size<=500){
				status=true;
			}else{
				if(clientstatus.containsKey(clientname)){
					int value=clientstatus.get(clientname);
					if(value>=3){
						status=false;
						JOptionPane.showMessageDialog(null, "IDS say "+clientname+" is Attacker.");
						attackername.add(clientname);
						updateToTable(clientname, clientsysname, stat, "Attacker");
						SendToHoneyPot(clientname, clientport, clientsysname, "Update");
						
					}
					else{
						value++;
						clientstatus.put(clientname,value);
						status=false;
						SendToClient(clientname, clientport, clientsysname);
					}					
				}else{
					int i=1;
					clientstatus.put(clientname, i);
					status=false;
					SendToClient(clientname, clientport, clientsysname);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
	
	public void SendToClient(String cliname,String cliport,String clisysname){
		try{
			Socket s = new Socket(clisysname,Integer.parseInt(cliport));
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject("UpdateAlert");			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void SendToHoneyPot(String endusername,String enduserport,String endusersysname,String status){
		try{
			FileInputStream fis = new FileInputStream("system.properties");
			Properties pro = new Properties();
			pro.load(fis);			
			String Hsysname=pro.getProperty("HoneypotSystem");
			String Hport=pro.getProperty("HoneypotPort");
			System.out.println("---------------*************"+Hsysname+""+Hport);
			Socket s = new Socket(Hsysname,Integer.parseInt(Hport));
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject("AttackerDetail");	
			oos.writeObject(endusername);
			oos.writeObject(enduserport);
			oos.writeObject(endusersysname);
			oos.writeObject(status);
			oos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void updateToTable(String srsnodename,String srcsysname,String process,String type){
		try{
			int count=IDSobject.Dlog.getColumnCount();			
			IDSobject.Dlog.insertRow(0,new Object[]{srsnodename,srcsysname,process,type}); 
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
