package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import command.CommandExecutor;

import domain.ReportItem;

/**
 * Servlet implementation class CreateReportServlet
 */
@WebServlet(description = "servlet to create PDF reports", urlPatterns = { "/CreateReportServlet" })
public class CreateReportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateReportServlet() {
        super();
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);	
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd;
		
		try{
			String dateIni = request.getParameter("txtDateIni");
			String dateEnd = request.getParameter("txtDateEnd");
			String dateIniExp = request.getParameter("txtDateIniExp");
			String dateEndExp = request.getParameter("txtDateEndExp");
			String state = request.getParameter("txtState");
			String product = request.getParameter("txtProduct");
			Integer productId;
			String clientStatus = request.getParameter("txtClientStatus");
			Integer statusId;
			Date startAffiliationDate;
			Date endAffiliationDate;
			Date startExpirationDate;
			Date endExpirationDate;
			
			if (dateIni == "" && dateEnd == ""){
				startAffiliationDate = null;
				endAffiliationDate = null;
			} else {
				java.util.Date utilDate = format.parse(dateIni);
				startAffiliationDate = new Date(utilDate.getTime());
				utilDate = format.parse(dateEnd);
				endAffiliationDate = new Date(utilDate.getTime());
			}
			
			if (dateIniExp == "" && dateEndExp == ""){
				startExpirationDate = null;
				endExpirationDate = null;
			} else {
				
				java.util.Date utilDate = format.parse(dateIniExp);
				startExpirationDate = new Date(utilDate.getTime());
				utilDate = format.parse(dateEndExp);
				endExpirationDate = new Date(utilDate.getTime());
			}

			if (state.equals("-1")){
				state = null;
			}
			
			if (clientStatus.equals("-1")){
				statusId = null;
			}else{
				statusId = Integer.valueOf(clientStatus);
			}
			
			if (product.equals("-1")){
				productId = null;
			}else{
				productId = Integer.valueOf(product);
			}			
			
			ArrayList<ReportItem> reportItems = (ArrayList<ReportItem>)CommandExecutor.getInstance().executeDatabaseCommand(new command.CreateReport(productId, statusId, state, 
					startAffiliationDate, endAffiliationDate, startExpirationDate, endExpirationDate));

			request.setAttribute("reportItems", reportItems);
			
			Document document=new Document(PageSize.A3.rotate(), 1, 1, 20, 20);
			String pdfFileName = "reporte.pdf";
			String contextPath = getServletContext().getRealPath(File.separator);
			FileOutputStream file = new FileOutputStream(contextPath + pdfFileName);
			PdfWriter.getInstance(document,file);
			
			document.open();
			
			Font font = FontFactory.getFont("tahoma", 6, Font.NORMAL, BaseColor.BLACK);
			PdfPTable table=new PdfPTable(23);
			
			table.setKeepTogether(false);
			
			PdfPCell cell = new PdfPCell(new Phrase("Cédula", font));
			
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Apellidos", font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Nombres", font));
			table.addCell(cell);
			//table.addCell("Contrato");
			cell = new PdfPCell(new Phrase("Fecha Venta", font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Nombre Producto", font));
			table.addCell(cell);
			//table.addCell("Cantidad");
			cell = new PdfPCell(new Phrase("Precio", font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Vendedor", font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Turno", font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Sala", font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Dirección", font));
			cell.setColspan(4);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Punto Referencia", font));
			cell.setColspan(2);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Ciudad", font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Estado", font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Tel Res", font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Tel Ofi", font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Tel Cel", font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Tel Fax", font));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase("Email", font));
			cell.setColspan(2);
			table.addCell(cell);
			
			for(ReportItem item : reportItems){
								
				cell = new PdfPCell(new Phrase(item.getIdentityCard(), font));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(item.getLastName(), font));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(item.getFirstName(), font));
				table.addCell(cell);
				//table.addCell("Contrato");
				cell = new PdfPCell(new Phrase(item.getAffiliationDate().toString(), font));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(item.getProduct(), font));
				table.addCell(cell);
				//table.addCell("Cantidad");
				cell = new PdfPCell(new Phrase(item.getPrice().toString(), font));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(item.getSeller(), font));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(item.getTurn(), font));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(item.getRoom(), font));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(item.getAddress(), font));
				cell.setColspan(4);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(item.getReferencePoint(), font));
				cell.setColspan(2);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(item.getCity(), font));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(item.getState(), font));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(item.getHomeNumber(), font));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(item.getOfficeNumber(), font));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(item.getMobileNumber(), font));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(item.getFaxNumber(), font));
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(item.getEmail(), font));
				cell.setColspan(2);
				table.addCell(cell);
			}
			
			document.add(table);
			document.close();

			File pdfFile = new File(contextPath + pdfFileName);

			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		 	response.setHeader("Pragma", "public");

			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
		  	response.setContentLength((int)pdfFile.length());

		  	FileInputStream fileInputStream = new FileInputStream(pdfFile);
			OutputStream responseOutputStream = response.getOutputStream();
			int bytes;
			while ((bytes = fileInputStream.read()) != -1) {
				responseOutputStream.write(bytes);
			}	
		}catch (Exception e) {
 			request.setAttribute("info", "");
			request.setAttribute("error", "Ocurrió un error durante la generación del reporte en formato PDF. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
			rd = getServletContext().getRequestDispatcher("/ListReportsServlet");			

			rd.forward(request, response);
		}
			
	}
}
