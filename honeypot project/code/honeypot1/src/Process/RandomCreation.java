package Process;

import java.util.Random;

public class RandomCreation {
	
	public Random ran = new Random();
	public String Nodename;
	public String Nodeport;
	public String password;
	
	public String Nodename(){
		Nodename="Node"+String.valueOf(ran.nextInt(10))+String.valueOf(ran.nextInt(10))+String.valueOf(ran.nextInt(10));
		return Nodename;
	}
	
	public String Nodeport(){
		Nodeport=String.valueOf(ran.nextInt(10))+String.valueOf(ran.nextInt(10))+String.valueOf(ran.nextInt(10))+String.valueOf(ran.nextInt(10));
		return Nodeport;
	}
	
	public String GeneratePassword(){
		password=String.valueOf(ran.nextInt(10))+String.valueOf(ran.nextInt(10))+String.valueOf(ran.nextInt(10))+String.valueOf(ran.nextInt(10))+String.valueOf(ran.nextInt(10));
		return password;
	}

}
