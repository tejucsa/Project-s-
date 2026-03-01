package Process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.ServerDesign;


public class ServerReceiver extends Thread{
	
	public Connection con=null;
	public ServerDesign serverobject;
	
	public void StartReceiver(ServerDesign serverobject){
		this.serverobject=serverobject;
		start();
	}
	
	public void run(){
		try{
			ServerSocket ss = new ServerSocket(6666);
			while(true){
				System.out.println("Server Receive......"+"6666");
				Socket s =ss.accept();
				System.out.println("Server Receive......");
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				String keyData=(String)ois.readObject();
				if(keyData.equals("Login")){
					System.out.println("Server Receive......");
					String srcname=(String)ois.readObject();
					String srcport=(String)ois.readObject();
					String sysname=(String)ois.readObject();
					String srcpass=(String)ois.readObject();
					ValidationClient(srcname,srcport,sysname,srcpass);
				}
				else if(keyData.equals("Search")){
					String srcname=(String)ois.readObject();
					String srcport=(String)ois.readObject();
					String sysname=(String)ois.readObject();
					String filename=(String)ois.readObject();
					RetriveFile(srcname,srcport,sysname,filename);
				}
				else if(keyData.equals("Update")){
					String srcname=(String)ois.readObject();
					String srcport=(String)ois.readObject();
					String sysname=(String)ois.readObject();
					String filename=(String)ois.readObject();
					String filedata=(String)ois.readObject();
					UpdateFile(srcname,srcport,sysname,filename,filedata);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	public void ValidationClient(String name,String port,String sysname,String pass){
		try{
			Connection tempcon1=getConnection();
			Statement st = tempcon1.createStatement();
			ResultSet rs = st.executeQuery("select * from userinform where username='"+name+"' and password='"+pass+"'");
			if(rs.next()){
				Socket s = new Socket(sysname,Integer.parseInt(port));
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				System.out.println("Response s......");
				oos.writeObject("LoginStatus");
				oos.writeObject("Success");				
			}else{
				Socket s = new Socket(sysname,Integer.parseInt(port));
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				oos.writeObject("LoginStatus");
				oos.writeObject("Faliure");		
				System.out.println("Response f......");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void RetriveFile(String username,String userport,String usersysname,String userfname){
		try{
			FileInputStream fis = new FileInputStream("system.properties");
			Properties pro = new Properties();
			pro.load(fis);
			String path=pro.getProperty("Path");
			File filelist = new File(path+"ServerData");
            File[] Fl=filelist.listFiles();
            String filedata="";
            boolean filestatus=false;
            I0:for(int i=0; i<Fl.length; i++)
            {
                String temp=Fl[i].getName();
                if(userfname.equals(temp)){
                	FileInputStream fileread = new FileInputStream(path+"ServerData/"+temp);
                	byte[] filebyte = new byte[fileread.available()];
                	fileread.read(filebyte);
                	filedata = new String(filebyte);
                	filestatus=true;
                	break I0;
                }
            }
            if(filestatus){
            	Socket s = new Socket(usersysname,Integer.parseInt(userport));
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				oos.writeObject("FileRetrive");
				oos.writeObject("Available");
				oos.writeObject(filedata);
            }else{
            	Socket s = new Socket(usersysname,Integer.parseInt(userport));
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				oos.writeObject("FileRetrive");
				oos.writeObject("NotAvailable");
				oos.writeObject("");
            }
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void UpdateFile(String username,String userport,String usersysname,String userfilename,String userfiledata){
		try{
			FileInputStream fis = new FileInputStream("system.properties");
			Properties pro = new Properties();
			pro.load(fis);
			String path=pro.getProperty("Path");
			FileOutputStream fos = new FileOutputStream(path+"ServerData/"+userfilename);
			fos.write(userfiledata.getBytes());
			fos.close();
			Socket s = new Socket(usersysname,Integer.parseInt(userport));
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject("FileUpdate");
			oos.writeObject("Success");
			File filelist = new File(path+"ServerData");
            File[] Fl=filelist.listFiles();
            String fname = new String();
            for(int i=0; i<Fl.length; i++)
            {
                String temp=Fl[i].getName();
                fname+=temp+"\n";
            }                    
            serverobject.serverupdatebox.setText("");
            serverobject.serverupdatebox.setText(fname);
            fname="";
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void StoreEnduserPassword(String enduser,String pass,String cont,String mail,String nat,String dob){
		try{
			Connection tempcon = getConnection();
			Statement st=tempcon.createStatement();
			st.execute("insert into userinform values('"+enduser+"','"+pass+"','"+cont+"','"+mail+"','"+nat+"','"+dob+"')");
                       
                        
			JOptionPane.showMessageDialog(null, "Register Successfully");
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
