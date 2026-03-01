package Process;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import com.HoneypotDesign;


public class HoneyReceiver extends Thread{
	
	public HoneypotDesign Hobject;
	public HashMap<String, String> AttackerDetail = new HashMap<String, String>();
	public Connection con;
	
	public void startpotReceiver(HoneypotDesign Hobject){
		this.Hobject=Hobject;
		start();
	}
	
	public void run(){
		try{
			ServerSocket ss = new ServerSocket(7777);
			while(true){
				Socket s = ss.accept();
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				String inform=(String)ois.readObject();
				if(inform.equals("AttackerDetail")){
					String Aname=(String)ois.readObject();
					String Aport=(String)ois.readObject();
					String Asysname=(String)ois.readObject();
					String Astatus=(String)ois.readObject();
					updateToTable(Aname, Asysname, Astatus, "Attacker");
					AddAttackerData(Aname,Aport,Asysname);
				}
				else if(inform.equals("UserDetails")){
					String name=(String)ois.readObject();
					String place=(String)ois.readObject();
					String dob=(String)ois.readObject();
					String code=(String)ois.readObject();
					boolean status=CheckDeatils(name,place,dob);
					if(status){
						boolean status1=CheckCode(name, code);
						if(status1){
							String tempstatus="Success";
							SendToVerificationData(name,tempstatus);
							ClearAttackerName(name);
						}else{
							String tempstatus="Failure";
							SendToVerificationData(name,tempstatus);
						}
					}
					else{
						String tempstatus="Failure";
						SendToVerificationData(name,tempstatus);
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void updateToTable(String srsnodename,String srcsysname,String process,String type){
		try{
			//int count=IDSobject.Dlog.getColumnCount();			
			//IDSobject.Dlog.insertRow(0,new Object[]{srsnodename,srcsysname,process,type}); 
			Hobject.Dlog.insertRow(0,new Object[]{srsnodename,srcsysname,process,type});
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void AddAttackerData(String Attname,String Attport,String Attsysname){
		try{
			String temp=Attport+"$"+Attsysname;
			AttackerDetail.put(Attname, temp);
			Set<String> s = AttackerDetail.keySet();
			Iterator<String> it = s.iterator();
			String ATname = new String();
			while(it.hasNext()){
				String tempname=it.next();
				ATname=ATname+"\n"+tempname;
			}
			Hobject.attackfield.setText(ATname);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void SendToClient(String enduser){
		try{
			GenerateVerificationCode(enduser);
			String userdetails=AttackerDetail.get(enduser);
			StringTokenizer st = new StringTokenizer(userdetails, "$");
			String port=st.nextToken();
			String endattsysname=st.nextToken();
			Socket s = new Socket(endattsysname,Integer.parseInt(port));
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject("Verification");			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean CheckDeatils(String ename,String eplace,String edob){
		boolean status1=false;
		try{
			Connection tempcon = getConnection();
			Statement st=tempcon.createStatement();
			ResultSet rs=st.executeQuery("select * from userinform where username='"+ename+"'");
			if(rs.next()){
				String nplace=rs.getString("native");
				String Udob=rs.getString("dob");
				if((eplace.equals(nplace)) && (edob.equals(Udob))){
					status1 = true;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return status1;
	}
	
	public boolean CheckCode(String name,String code){
		boolean status2=false;
		try{
			Connection tempcon = getConnection();
			Statement st=tempcon.createStatement();
			ResultSet rs=st.executeQuery("select * from verificationcode where username='"+name+"' and code='"+code+"'");
			if(rs.next()){
				status2=true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return status2;
	}
	
	public void GenerateVerificationCode(String uname){
		try{
			Random ran = new Random();
			String Vcode=String.valueOf(ran.nextInt(10))+String.valueOf(ran.nextInt(10))+String.valueOf(ran.nextInt(10))+String.valueOf(ran.nextInt(10))+String.valueOf(ran.nextInt(10))+String.valueOf(ran.nextInt(10))+String.valueOf(ran.nextInt(10));
			Connection tempcon = getConnection();
			Statement st=tempcon.createStatement();
			st.execute("insert into verificationcode values('"+uname+"','"+Vcode+"')");
			SendToMail(uname,Vcode);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void SendToMail(String usename,String vcode){
		try{
			String MailID="";
			Connection tempcon = getConnection();
			Statement st=tempcon.createStatement();
			String sql="select * from userinform where username='"+usename+"'";
			System.out.println(sql);
			ResultSet rs=st.executeQuery(sql);
			if(rs.next())
                        {
                                String contact=rs.getString("contact");
				MailID=rs.getString("mail");
				SendMailExample mail = new SendMailExample();
				mail.main(MailID, vcode);
                                
                           
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void SendToVerificationData(String name,String valstatus){
		try{
			String sysnameandport=AttackerDetail.get(name);
			StringTokenizer st = new StringTokenizer(sysnameandport, "$");
			String sysport=st.nextToken();
			String systemname=st.nextToken();
			Socket s = new Socket(systemname,Integer.parseInt(sysport));
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject("ValidationStatus");
			oos.writeObject(valstatus);
			oos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void ClearAttackerName(String name){
		try{
			AttackerDetail.remove(name);
			if(AttackerDetail.isEmpty()){
				Hobject.attackfield.setText("");
			}else{
				Set<String> s = AttackerDetail.keySet();
				Iterator<String> it = s.iterator();
				String ATname = new String();
				while(it.hasNext()){
					String tempname=it.next();
					ATname=ATname+"\n"+tempname;
				}
				Hobject.attackfield.setText(ATname);
			}
			SendToIDS(name);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void SendToIDS(String rename){
		try{
			FileInputStream fis = new FileInputStream("system.properties");
			Properties pro = new Properties();
			pro.load(fis);			
			String Hsysname=pro.getProperty("IDSSystem");
			String Hport=pro.getProperty("IDSPort");
			Socket s = new Socket(Hsysname,Integer.parseInt(Hport));
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject("RemoveAttacker");
			oos.writeObject(rename);
			oos.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		Connection con=null;
		try{						
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://"+"localhost"+"/ids","root","root");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
}
