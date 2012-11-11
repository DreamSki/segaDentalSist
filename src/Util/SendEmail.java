package Util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {

	public static void sendEmail(Properties propertiesFile, String email, String name, boolean attach, String pref){
		
	  String to = email;
	  final String user= propertiesFile.getProperty(pref + "EmailCountFrom");
	  final String password =  propertiesFile.getProperty(pref + "EmailPasswordFrom");
			
	  Properties properties = System.getProperties();
	  properties.setProperty("mail.smtp.host", propertiesFile.getProperty(pref + "EmailServer") );
	  properties.setProperty("mail.smtp.port", "587");
	  properties.setProperty("mail.smtp.starttls.enable", "true");
	  properties.put("mail.smtp.auth", "true");
	  properties.put("mail.debug", "true");
	  
	  Session session = Session.getDefaultInstance(properties,
	   new javax.mail.Authenticator() {
		  protected PasswordAuthentication getPasswordAuthentication() {
		   return new PasswordAuthentication(user,password);
		  }
	  });
	   
	  try{
	    MimeMessage message = new MimeMessage(session);
	    message.setFrom(new InternetAddress(user));
	    message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
	    message.setSubject(propertiesFile.getProperty(pref + "EmailSubject"));
	    
	    BodyPart messageBodyPart1 = new MimeBodyPart();
	    
	    if (pref.equalsIgnoreCase("contrato")){
	    	messageBodyPart1.setText( name + " reciba un cordial saludo de parte de Segadental.\n \n En el link que encontrará" +
	    		"  a continuación podrá visualizar el contrato por el servicio adquirido. \n\n " +
	    	    propertiesFile.getProperty("contratoLink") +	
	    		" \n\n Cualquier información adicional que desee, no dude en contactarnos. \n" +
	    		" Departamento de Ventas, \n Segadental. ");
	    }
	    else{
	    	messageBodyPart1.setText("A continuación encontrará el reporte generado.");
	    	
	    }
	    
	    
	    Multipart multipart = new MimeMultipart();
	    multipart.addBodyPart(messageBodyPart1);
	    
	    if (attach){
	    	MimeBodyPart messageBodyPart2 = new MimeBodyPart();
	    	String filename = "D:\\workspace\\segaDentalSist\\WebContent\\archivos\\Contrato.pdf";//change accordingly
	    	DataSource source = new FileDataSource(filename);
	    	messageBodyPart2.setDataHandler(new DataHandler(source));
	    	messageBodyPart2.setFileName("Contrato.pdf");

		    multipart.addBodyPart(messageBodyPart2);

	    }

	    message.setContent(multipart);
	   
	    Transport.send(message);
	 
	   }catch (MessagingException ex) {
		   ex.printStackTrace();
	   }
	 }
	
}
