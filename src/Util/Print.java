package Util;

import java.io.*;  

import javax.print.*;  
import javax.print.attribute.*;   

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
   
public class Print{  
   
   public static void print() throws DocumentException{  
	   
//	   
//	   System.out.println("intento imrpimir");
//	   	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		
//	   	Document document=new Document(PageSize.A4.rotate());
//		
//		try {
//			PdfWriter.getInstance(document, outputStream);
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		document.open();
//		
//		document.addTitle("Reporte");
//		document.addSubject("Reporte de Venta Diaria");
//		
//		PdfPTable table=new PdfPTable(10);
//		table.setKeepTogether(false);
//		table.addCell("Cédula");
//		table.addCell("Apellidos");
//		table.addCell("Nombres");
//		table.addCell("Fecha Venta");
//		table.addCell("Nombre Producto");
//		table.addCell("Precio");
//		table.addCell("Vendedor");
//		table.addCell("Sala");
//		//table.addCell("Dirección");
//		table.addCell("Ciudad");
//		table.addCell("Estado");
//		
//		document.add(table);
//		document.close();
//	   	
//		byte[] bytes = outputStream.toByteArray();
//		
		FileInputStream psStream = null;  
        try {  
            psStream = new FileInputStream("D:\\workspace\\segaDentalSist\\WebContent\\archivos\\Contrato.pdf");  
            } catch (FileNotFoundException ffne) {  
              ffne.printStackTrace();  
            }  
            if (psStream == null) {  
                return;  
            }  
        DocFlavor psInFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;  
        Doc myDoc = new SimpleDoc(psStream, psInFormat, null);    
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();  
        PrintService[] services = PrintServiceLookup.lookupPrintServices(psInFormat, aset);  
          
        // this step is necessary because I have several printers configured  
        PrintService myPrinter = null;  
        for (int i = 0; i < services.length; i++){  
             String svcName = services[i].toString();             
             System.out.println("service found: "+svcName);  
              if (svcName.contains("HP Deskjet 3050")){  
                myPrinter = services[i];  
                System.out.println("my printer found: "+svcName);  
                break;  
            }  
        }  
          
        if (myPrinter != null) {              
            DocPrintJob job = myPrinter.createPrintJob();  
            try {  
            job.print(myDoc, aset);  
            } catch (Exception pe) {pe.printStackTrace();}  
        } else {  
            System.out.println("no printer services found");  
        }  
   }  
}  