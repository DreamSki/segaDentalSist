package servlet;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import command.CommandExecutor;

import domain.Client;
import domain.PhoneType;
import domain.User;


/**
 * Servlet implementation class PrintClientServlet
 */
@WebServlet(description = "servlet to print client's info", urlPatterns = { "/PrintClientServlet" })
public class PrintClientServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrintClientServlet() {
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
				
				//Verificador y renovante.
				if(roleId == 3 || roleId == 5 || roleId == 8){
					
					String id = request.getParameter("clientId");
					int clientId  = 0;
					System.out.println("id" + (id == null));
					if (id == null){
						request.setAttribute("info", " ");
						request.setAttribute("error", " ");
						rd = getServletContext().getRequestDispatcher("/SearchClientServlet");			
						rd.forward(request, response);
						
					}else{
						clientId = Integer.valueOf(id);
						String  type = request.getParameter("type");
						
						Client clientInfo = (Client)CommandExecutor.getInstance().executeDatabaseCommand(new command.SelectClient(clientId, type));
					
						@SuppressWarnings("unchecked")
						ArrayList<PhoneType> phoneType = (ArrayList<PhoneType>) CommandExecutor.getInstance().executeDatabaseCommand(new command.ListPhoneType());
						
						
						request.setAttribute("clientId", clientId);
						request.setAttribute("phoneType", phoneType);
						request.setAttribute("type", type);
						request.setAttribute("clientInfo",clientInfo);
						
						rd = getServletContext().getRequestDispatcher("/printClient.jsp");			
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
			throw new ServletException(e);
		}		
	}
	
	
	
	
}
