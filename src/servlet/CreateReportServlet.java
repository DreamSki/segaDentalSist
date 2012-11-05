package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import command.CommandExecutor;

//import command.CommandExecutor;

//import domain.Product;
import domain.ReportItem;
import domain.User;
import domain.UserProduct;

/**
 * Servlet implementation class CreateReportServlet
 */
@WebServlet(description = "servlet to create reports", urlPatterns = { "/CreateReportServlet" })
public class CreateReportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
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

		try{
			HttpSession session = request.getSession(true);
			User user = (User) session.getAttribute("user");
			RequestDispatcher rd;
			   
			if(user != null){
				int roleId = user.getRoleId();
				
				if(roleId == 1 || roleId == 8){
					rd = getServletContext().getRequestDispatcher("/createProduct.jsp");			
					rd.forward(request, response);
				} else {
					request.setAttribute("error", "Usted no posee permisos para realizar esta operación");
					rd = getServletContext().getRequestDispatcher("/mainMenu.jsp");
					rd.forward(request, response);
				}			
			} else {
				rd = getServletContext().getRequestDispatcher("/index.jsp");			
				rd.forward(request, response);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd;
		
		try{
			String dateIni = request.getParameter("txtDateIni");
			System.out.println("+++ dateIni:"+ dateIni);
			String dateEnd = request.getParameter("txtDateEnd");
			System.out.println("+++ dateEnd:"+ dateEnd);
			String state = request.getParameter("txtState");
			System.out.println("+++ state:"+ state);
			String product = request.getParameter("txtProduct");
			System.out.println("+++ product:"+ product);
			String  clientStatus = request.getParameter("txtClientStatus");
			System.out.println("+++ clientStatus:"+ clientStatus);
			
			int porFechas = 0, porEstado = 0, porProducto = 0, porStatusClient = 0;
			if (dateIni != "" && dateEnd != ""){
				porFechas = 1;
			}
			if (!state.equals("-1")){
				porEstado = 1;
			}
			if (!clientStatus.equals("-1")){
				porStatusClient = 1;
			}
			if (!product.equals("-1")){
				porProducto = 1;
			}
			
			System.out.println("El reporte a generar "+ 
					" fechas " + porFechas +  " estado " + porEstado
					+ " clienteEstado " + porStatusClient + " producto " + porProducto);
		
//			Integer rowsUpdated  = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.CreateProduct(product));
			ArrayList<ReportItem> reportItems = (ArrayList<ReportItem>)CommandExecutor.getInstance().executeDatabaseCommand(new command.CreateReport());
			request.setAttribute("reportItems", reportItems);
			
			Document document=new Document(PageSize.A4.rotate());
			String pdfFileName = "report.pdf";
			String contextPath = getServletContext().getRealPath(File.separator);
			FileOutputStream file = new FileOutputStream(contextPath + pdfFileName);
			PdfWriter.getInstance(document,file);
			document.open();
			PdfPTable table=new PdfPTable(10);
			table.setKeepTogether(false);
			table.addCell("Cédula");
			table.addCell("Apellidos");
			table.addCell("Nombres");
			table.addCell("Fecha Venta");
			table.addCell("Nombre Producto");
			table.addCell("Precio");
			table.addCell("Vendedor");
			table.addCell("Sala");
			//table.addCell("Dirección");
			table.addCell("Ciudad");
			table.addCell("Estado");
			
			for(ReportItem item : reportItems){
				table.addCell(item.getIdentityCard());
				table.addCell(item.getLastName());
				table.addCell(item.getFirstName());
				table.addCell(item.getAffiliationDate().toString());
				table.addCell(item.getProduct());
				table.addCell(item.getPrice().toString());
				table.addCell(item.getSeller());
				table.addCell(item.getRoom());
				//table.addCell(item.getAddress());
				table.addCell(item.getCity());
				table.addCell(item.getState());
				
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
			
//			
//			if(rowsUpdated == 1){
//					request.setAttribute("info", "El producto fue creado exitosamente.");
//					request.setAttribute("error", "");
//					System.out.println("bien");
//					rd = getServletContext().getRequestDispatcher("/ListProductsServlet");			
//					rd.forward(request, response);
//			}
//			else{
//				request.setAttribute("info", "");
//				request.setAttribute("error", "Ocurrió un error durante la creación del producto. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
//				System.out.println("error");
//				rd = getServletContext().getRequestDispatcher("/ListProductsServlet");			
//
//				rd.forward(request, response);
//			}
//			
		}catch (Exception e) {
//			request.setAttribute("info", "");
//			request.setAttribute("error", "Ocurrió un error durante la creación del producto. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
//			rd = getServletContext().getRequestDispatcher("/ListProductsServlet");			
//
//			rd.forward(request, response);
		}
			
	}
}
