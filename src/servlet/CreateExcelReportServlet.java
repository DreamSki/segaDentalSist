package servlet;

import java.io.File;
import java.io.FileInputStream;
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

import jxl.Workbook;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;

import command.CommandExecutor;

import domain.ReportItem;

/**
 * Servlet implementation class CreateExcelReportServlet
 */
@WebServlet(description = "servlet to create Excel reports", urlPatterns = { "/CreateExcelReportServlet" })
public class CreateExcelReportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateExcelReportServlet() {
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
			String callStatus = request.getParameter("txtCallStatus");
			Integer callStatusId;
			
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
			
			if (callStatus.equals("-1")){
				callStatusId = null;
			}else{
				callStatusId = Integer.valueOf(callStatus);
			}
			
			ArrayList<ReportItem> reportItems = (ArrayList<ReportItem>)CommandExecutor.getInstance().executeDatabaseCommand(new command.CreateReport(productId, statusId, state, 
					startAffiliationDate, endAffiliationDate, startExpirationDate, endExpirationDate, callStatusId));

			if (reportItems.size() > 0){
				
				String excelFileName = "reporte.xls";
				String contextPath = getServletContext().getRealPath(File.separator);
				File file = new File(contextPath + excelFileName);
				WritableWorkbook workbook = Workbook.createWorkbook(file);
				WritableSheet sheet = workbook.createSheet("REPORTE", 0);
				WritableCellFormat headerFormat = new WritableCellFormat();	
				WritableFont TableFormat = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
				headerFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
				headerFormat.setFont(TableFormat);
				headerFormat.setBackground(Colour.GRAY_25) ;
				
				sheet.addCell(new Label(0,0,"Cédula", headerFormat));
				sheet.addCell(new Label(1,0,"Apellidos", headerFormat));
				sheet.addCell(new Label(2,0,"Nombres", headerFormat));
				//table.addCell("Contrato");
				sheet.addCell(new Label(3,0,"Fecha Venta", headerFormat));
				sheet.addCell(new Label(4,0,"Nombre Producto", headerFormat));
				sheet.addCell(new Label(5,0,"Precio", headerFormat));
				sheet.addCell(new Label(6,0,"Vendedor", headerFormat));
				sheet.addCell(new Label(7,0,"Turno", headerFormat));
				sheet.addCell(new Label(8,0,"Sala", headerFormat));
				sheet.addCell(new Label(9,0,"Dirección", headerFormat));
				sheet.addCell(new Label(10,0,"Punto Referencia", headerFormat));
				sheet.addCell(new Label(11,0,"Ciudad", headerFormat));
				sheet.addCell(new Label(12,0,"Estado", headerFormat));
				sheet.addCell(new Label(13,0,"Tel Res", headerFormat));
				sheet.addCell(new Label(14,0,"Tel Ofi", headerFormat));
				sheet.addCell(new Label(15,0,"Tel Cel", headerFormat));
				sheet.addCell(new Label(16,0,"Tel Fax", headerFormat));
				sheet.addCell(new Label(17,0,"Email", headerFormat));
				
				DateFormat customDateFormat = new DateFormat("dd/mm/yyyy"); 
				WritableCellFormat dateFormat = new WritableCellFormat(customDateFormat); 
				dateFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
				WritableCellFormat borderFormat = new WritableCellFormat();	
				WritableFont cellFormat = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
				borderFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
				borderFormat.setFont(cellFormat);
				int i = 1;
				
				for(ReportItem item : reportItems){
					
					sheet.addCell(new Label(0,i,item.getIdentityCard(), borderFormat));
					sheet.addCell(new Label(1,i,item.getLastName(), borderFormat));
					sheet.addCell(new Label(2,i,item.getFirstName(), borderFormat));
					//table.addCell("Contrato");
					sheet.addCell(new DateTime(3,i,item.getAffiliationDate(), dateFormat));
					sheet.addCell(new Label(4,i,item.getProduct(), borderFormat));
					sheet.addCell(new Number(5,i,item.getPrice(), borderFormat));
					sheet.addCell(new Label(6,i,item.getSeller(), borderFormat));
					sheet.addCell(new Label(7,i,item.getTurn(), borderFormat));
					sheet.addCell(new Label(8,i,item.getRoom(), borderFormat));
					sheet.addCell(new Label(9,i,item.getAddress(), borderFormat));
					sheet.addCell(new Label(10,i,item.getReferencePoint(), borderFormat));
					sheet.addCell(new Label(11,i,item.getCity(), borderFormat));
					sheet.addCell(new Label(12,i,item.getState(), borderFormat));
					sheet.addCell(new Label(13,i,item.getHomeNumber(), borderFormat));
					sheet.addCell(new Label(14,i,item.getOfficeNumber(), borderFormat));
					sheet.addCell(new Label(15,i,item.getMobileNumber(), borderFormat));
					sheet.addCell(new Label(16,i,item.getFaxNumber(), borderFormat));
					sheet.addCell(new Label(17,i,item.getEmail(), borderFormat));
					
					i++;
				}
				
				workbook.write();
	            workbook.close(); 
	
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
			 	response.setHeader("Pragma", "public");
	
				response.setContentType("application/vnd.ms-excel");
				response.addHeader("Content-Disposition", "attachment; filename=" + excelFileName);
			  	response.setContentLength((int)file.length());
	
			  	FileInputStream fileInputStream = new FileInputStream(file);
				OutputStream responseOutputStream = response.getOutputStream();
				int bytes;
				while ((bytes = fileInputStream.read()) != -1) {
					responseOutputStream.write(bytes);
				}	
			}else{
				request.setAttribute("error", "No hay resultados que coincidan con su búsqueda.");
				rd = getServletContext().getRequestDispatcher("/ListReportsServlet");			
				rd.forward(request, response);
			}	
		}catch (Exception e) {
 			request.setAttribute("info", "");
			request.setAttribute("error", "Ocurrió un error durante la generación del reporte en formato Excel. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
			rd = getServletContext().getRequestDispatcher("/ListReportsServlet");			

			rd.forward(request, response);
		}
			
	}
}
