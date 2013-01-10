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
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
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
		
		
		Document document=new Document(PageSize.A3.rotate(), 20, 20, 20, 20);
		PdfWriter.getInstance(document, outputStream);
		
		document.open();
		document.addTitle("Reporte");
		document.addSubject("Reporte de Venta Diaria");
		
		
		
	
		Font font = FontFactory.getFont("tahoma", 8, Font.NORMAL, BaseColor.BLACK);
		Font fontLarge = FontFactory.getFont("tahoma", 10, Font.NORMAL, BaseColor.BLACK);
		BaseColor color = new BaseColor(154,154,154);
		
		if(reportItems.size()==0){
			document.add(new Paragraph("No se presentaron ventas ni renovaciones durante el día de hoy."));
		} else {
			PdfPTable table=new PdfPTable(16);
			
			table.setKeepTogether(false);
			
			PdfPCell cell = new PdfPCell(new Phrase("Nombre Cliente", fontLarge));
			cell.setColspan(2);
			cell.setBackgroundColor(color);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("C.I", fontLarge));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Número de Beneficiarios", fontLarge));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			table.addCell(cell);
			//table.addCell("Contrato");
			cell = new PdfPCell(new Phrase("Monto", fontLarge));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Sala", fontLarge));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			table.addCell(cell);
			//table.addCell("Cantidad");
			cell = new PdfPCell(new Phrase("Nombre Ejecutivo", fontLarge));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Turno", fontLarge));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Tipo de TDC", fontLarge));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Número transacción", fontLarge));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Tipo Banco", fontLarge));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Dirección", fontLarge));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			table.addCell(cell);
			
		
			color = new BaseColor(195,195,195);
			
			boolean first = true;
			boolean firstC = true;
			for(ReportItem item : reportItems){
				if (item.getType() == 5){
					if (firstC){
						cell = new PdfPCell(new Phrase("Clientes Nuevos", fontLarge));
						cell.setColspan(16);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBackgroundColor(color);
						table.addCell(cell);
						firstC = false;
					}
					cell = new PdfPCell(new Phrase(item.getFirstName() + " " + item.getLastName(), font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setColspan(2);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(item.getIdentityCard(), font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase("a", font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					//table.addCell("Contrato");
					cell = new PdfPCell(new Phrase(item.getAmount().toString(), font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(item.getRoom().toString(), font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					//table.addCell("Cantidad");
					cell = new PdfPCell(new Phrase(item.getSeller(), font));
					cell.setColspan(2);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(item.getTurn(), font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(item.getCardType(), font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(item.getVoucher(), font));
					cell.setColspan(2);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(item.getBank(), font));
					cell.setColspan(2);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(item.getCity() + "-" + item.getState(), font));
					cell.setColspan(2);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}
				else {
					if (first){
						cell = new PdfPCell(new Phrase("Renovaciones", fontLarge));
						cell.setColspan(16);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBackgroundColor(color);
						
						table.addCell(cell);
						first = false;
					}
					cell = new PdfPCell(new Phrase(item.getFirstName() + " " + item.getLastName(), font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setColspan(2);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(item.getIdentityCard(), font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(String.valueOf(item.getNumBenef()), font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					//table.addCell("Contrato");
					cell = new PdfPCell(new Phrase(item.getAmount().toString(), font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(item.getRoom().toString(), font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					//table.addCell("Cantidad");
					cell = new PdfPCell(new Phrase(item.getSeller(), font));
					cell.setColspan(2);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(item.getTurn(), font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(item.getCardType(), font));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(item.getVoucher(), font));
					cell.setColspan(2);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(item.getBank(), font));
					cell.setColspan(2);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					cell = new PdfPCell(new Phrase(item.getCity() + "-" + item.getState(), font));
					cell.setColspan(2);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					
				}
			
			}
			
			document.add(table);
		}
		
		document.close();
		
	}
	
}
