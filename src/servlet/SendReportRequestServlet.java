package servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Util.SendEmail;


import command.CommandExecutor;

import domain.ReportItem;
import domain.User;


/**
 * Servlet implementation class SendBackRequestServlet
 */
@WebServlet(description = "servlet to send report of today's requests", urlPatterns = { "/SendReportRequestServlet" })
public class SendReportRequestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendReportRequestServlet() {
        super();
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("aqui");
		
		RequestDispatcher rd;
		
		try{
			HttpSession session = request.getSession(true);
			User user = (User) session.getAttribute("user");
			   
			if(user != null){
				int roleId = user.getRoleId();
				
				if(roleId == 3 || roleId == 8){
				
					@SuppressWarnings("unchecked")
					ArrayList<ReportItem> reportItems = (ArrayList<ReportItem>)CommandExecutor.getInstance().executeDatabaseCommand(new command.CreateReportOfRequests());
					if (reportItems.size() > 0){
						Properties propertiesFile = new Properties();
						String context = getServletContext().getInitParameter("properties");
						propertiesFile.load(new FileInputStream(context));
						SendEmail.sendEmailWithPdf(propertiesFile, "report", reportItems );
						
						request.setAttribute("info", "El correo fue envíado sastifactoriamente");
						rd = getServletContext().getRequestDispatcher("/ListRequestsServlet");			
						rd.forward(request, response);
					}
					else{	
						request.setAttribute("info", "Actualmente no hay ventas para mostrar. Intente más tarde");
						rd = getServletContext().getRequestDispatcher("/ListRequestsServlet");			
						rd.forward(request, response);
					}
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
			e.printStackTrace();
		
		}
	}
	
	
}
