package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import command.CommandExecutor;

import domain.ClientRequest;
import domain.User;


/**
 * Servlet implementation class SendBackRequestServlet
 */
@WebServlet(description = "servlet to send back requets", urlPatterns = { "/SendBackRequestServlet" })
public class SendBackRequestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendBackRequestServlet() {
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
					/*Se debe modificar el estado del user_product, cambiar la justificacion etc. PENDIENTE POR HACER */
					String clientProductId = request.getParameter("clientProductId");
					String justificationId = request.getParameter("justif");
					ClientRequest cr = new ClientRequest();
					cr.setId(Integer.valueOf(clientProductId));
					cr.setJustificationId(Integer.valueOf(justificationId));
					String otherJustif = null;
					if (justificationId.equals("5")){
						otherJustif = request.getParameter("otherJustif");
						cr.setJustification(otherJustif);
					}
					
					System.out.println("ClientR " + cr.getId() + " justId " + cr.getJustificationId() + " otherJ " + cr.getJustification());
					Integer rowsUpdated = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.EditClientRequest(cr));
					
					if(rowsUpdated == 1){
						request.setAttribute("info", "La devolución fue realizada sastifactoriamente.");
						rd = getServletContext().getRequestDispatcher("/ListRequestsServlet");			
						rd.forward(request, response);
					} else {
						request.setAttribute("info", "");
						request.setAttribute("error", "Ocurrió un error durante la devolucion. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
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
