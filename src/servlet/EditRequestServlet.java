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
import domain.CreditCard;
import domain.StatusJustification;
import domain.User;


/**
 * Servlet implementation class EditRequestServlet
 */
@WebServlet(description = "servlet to edit requets", urlPatterns = { "/EditRequestServlet" })
public class EditRequestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditRequestServlet() {
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
				
				if(roleId == 3 || roleId == 8){
					//Por ahora supondré que el requestId es el id del cliente
					
					Long clientId = Long.valueOf(request.getParameter("clientId"));
					String typeRequest = String.valueOf(request.getParameter("type"));
					Long id = Long.valueOf(request.getParameter("id"));
					
					Client client = (Client)CommandExecutor.getInstance().executeDatabaseCommand(new command.SelectClientRequest(clientId, id));
					client.setClientId(clientId);
					client.setClientProductId(id);
					
					@SuppressWarnings("unchecked")
					ArrayList<StatusJustification> list = (ArrayList<StatusJustification>)CommandExecutor.getInstance().executeDatabaseCommand(new command.ListStatusJustification());
					request.setAttribute("statusJustification", list);
					
					@SuppressWarnings("unchecked")
					ArrayList<CreditCard> listCardType = (ArrayList<CreditCard>)CommandExecutor.getInstance().executeDatabaseCommand(new command.ListCardType());
					request.setAttribute("cardType", listCardType);
					
					request.setAttribute("client", client);
					request.setAttribute("type", typeRequest);
					request.setAttribute("id", id);
					request.setAttribute("user", user);
					
					rd = getServletContext().getRequestDispatcher("/editRequest.jsp");			
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
		doGet(request, response);
		
	}
}
