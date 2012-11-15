package Util;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import domain.ReportItem;

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
	
	
	public static void sendEmailWithPdf(Properties propertiesFile, String pref, ArrayList<ReportItem> reportItems){
		
		  System.out.println("entro a enviar correo");
		  String to = propertiesFile.getProperty(pref + "EmailCountTo");;
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
		
		ByteArrayOutputStream outputStream = null;
		
		try {			
			//construct the text body part
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(propertiesFile.getProperty(pref + "EmailBody"));
			
			//now write the PDF content to the output stream
			outputStream = new ByteArrayOutputStream();
			writePdf(outputStream, reportItems);
			byte[] bytes = outputStream.toByteArray();
			
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			System.out.println(dateFormat.format(date));
			
			
			//construct the pdf body part
			DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
			MimeBodyPart pdfBodyPart = new MimeBodyPart();
			pdfBodyPart.setDataHandler(new DataHandler(dataSource));
			pdfBodyPart.setFileName("reporte_" +  dateFormat.format(date) + ".pdf");
						
			//construct the mime multi part
			MimeMultipart mimeMultipart = new MimeMultipart();
			mimeMultipart.addBodyPart(textBodyPart);
			mimeMultipart.addBodyPart(pdfBodyPart);
			
			
			//construct the mime message
			MimeMessage mimeMessage = new MimeMessage(session);
		    mimeMessage.setFrom(new InternetAddress(user));
			mimeMessage.setSubject(propertiesFile.getProperty(pref + "EmailSubject"));
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mimeMessage.setContent(mimeMultipart);
			
			//send off the email
			Transport.send(mimeMessage);
			
			System.out.println("sent from " + user + 
					", to " + to);			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			//clean off
			if(null != outputStream) {
				try { outputStream.close(); outputStream = null; }
				catch(Exception ex) { }
			}
		}
		
		
		
	}
	
	
	public static void writePdf(ByteArrayOutputStream outputStream, ArrayList<ReportItem> reportItems) throws Exception {
		
		
		Document document=new Document(PageSize.A3.rotate(), 1, 1, 20, 20);
		PdfWriter.getInstance(document, outputStream);
		
		document.open();
		document.addTitle("Reporte");
		document.addSubject("Reporte de Venta Diaria");
		
	
		Font font = FontFactory.getFont("tahoma", 6, Font.NORMAL, BaseColor.BLACK);
		PdfPTable table=new PdfPTable(15);
		
		table.setKeepTogether(false);
		
		PdfPCell cell = new PdfPCell(new Phrase("Nombre Cliente", font));
		
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("C.I", font));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Cant", font));
		table.addCell(cell);
		//table.addCell("Contrato");
		cell = new PdfPCell(new Phrase("Monto", font));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Sala", font));
		table.addCell(cell);
		//table.addCell("Cantidad");
		cell = new PdfPCell(new Phrase("Nombre Ejecutivo", font));
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Turno", font));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Tipo de TDC", font));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Número trans", font));
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Tipo Banco", font));
		cell.setColspan(2);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Dirección", font));
		cell.setColspan(2);
		table.addCell(cell);
			
		
		for(ReportItem item : reportItems){
			
			cell = new PdfPCell(new Phrase(item.getFirstName() + " " + item.getLastName(), font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(item.getIdentityCard(), font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("a", font));
			table.addCell(cell);
			//table.addCell("Contrato");
			cell = new PdfPCell(new Phrase(item.getAmount().toString(), font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(item.getRoom().toString(), font));
			table.addCell(cell);
			//table.addCell("Cantidad");
			cell = new PdfPCell(new Phrase(item.getSeller(), font));
			cell.setColspan(2);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(item.getTurn(), font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(item.getCardType(), font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(item.getVoucher(), font));
			cell.setColspan(2);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(item.getBank(), font));
			cell.setColspan(2);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(item.getCity() + "-" + item.getState(), font));
			cell.setColspan(2);
			table.addCell(cell);
		
		}
		
		document.add(table);
		document.close();
		
	}
	
}
