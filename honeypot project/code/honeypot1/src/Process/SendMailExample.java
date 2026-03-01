package Process;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;

import java.text.SimpleDateFormat;
import java.util.*;

public class SendMailExample {

	/**
	 * @param args
	 */
	public void main(String sendermail,String code)throws MessagingException {
		// TODO Auto-generated method stub
		// Recipient's email ID needs to be mentioned.
			String sender_email = "ibsheetal@gmail.com";
			String sender_password = "aeyfnnlisqumvnwy";
			String sender_host = "smtp.gmail.com";
			String sender_port = "465";
			String reciever_id = sendermail;
			String subject_to_be_given = "Verification Code";
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	        String val = sdf.format(cal.getTime());		 	       
			String message_mi = "Verification Code is "+code;

			Properties props = new Properties();
			 props.put("mail.smtp.user", sender_email);
			 props.put("mail.smtp.host", sender_host);
			 props.put("mail.smtp.port", sender_port);
			 props.put("mail.smtp.starttls.enable","true");
			 props.put("mail.smtp.auth", "true");
			 //props.put("mail.smtp.debug", "true");
			 props.put("mail.smtp.socketFactory.port",sender_port);
			 props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			 props.put("mail.smtp.socketFactory.fallb","false");

			 SecurityManager security = System.getSecurityManager();

			 try
			 {
			 Authenticator auth = new SMTPAuthenticator();
			 Session session = Session.getInstance(props, auth);
			 //session.setDebug(true);

			 MimeMessage msg = new MimeMessage(session);
			 msg.setText(message_mi);
			 msg.setSubject(subject_to_be_given);
			 msg.setFrom(new InternetAddress(sender_email));
			 //msg.addRecipient(Message.RecipientType.TO,new InternetAddress(reciever_id));
			 msg.addRecipient(RecipientType.TO, new InternetAddress(reciever_id));
			 Transport.send(msg);
			 }
			 catch (Exception mex)
			 {
				 mex.printStackTrace();
			 }

	}

}
class SMTPAuthenticator extends javax.mail.Authenticator
{
	String sender_email = "ibsheetal@gmail.com",
			sender_password = "aeyfnnlisqumvnwy";
	public PasswordAuthentication getPasswordAuthentication()
	{
			return new PasswordAuthentication(sender_email, sender_password);
	}
}