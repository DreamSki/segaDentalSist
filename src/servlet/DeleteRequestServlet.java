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
import domain.User;

/**
 * Servlet implementation class DeleteRequestServlet
 */
@WebServlet(description = "servlet to delete requests", urlPatterns = { "/DeleteRequestServlet" })
public class DeleteRequestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException {
		super.init();
		try {
			CommandExecutor.getInstance();
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteRequestServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd;
		String type = request.getParameter("type");
		
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			
			if(user != null){
				int roleId = user.getRoleId();
				
				if(roleId == 1 || roleId == 8){
					// perform list user operations
					Long requestId = Long.valueOf(request.getParameter("id"));
					Integer rowsUpdated = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.DeleteRequest(requestId));
					
					if(rowsUpdated == 1){
						System.out.println("");
						request.setAttribute("info", "La " + type + " fue eliminada sastifactoriamente." );
						request.setAttribute("error", "");
						rd = getServletContext().getRequestDispatcher("/ListRequestsServlet");			

						rd.forward(request, response);
					} else {
						request.setAttribute("info", "");
						System.out.println("Error");
						request.setAttribute("error", "Ocurrió un error durante la eliminación de la " + type + ". Por favor intente de nuevo y si el error persiste contacte a su administrador.");
						rd = getServletContext().getRequestDispatcher("/ListRequestsServlet");			

						rd.forward(request, response);
					}
				} else {
					request.setAttribute("error", "Usted no posee permisos para realizar esta operación");
					rd = getServletContext().getRequestDispatcher("/mainMenu.jsp");
					rd.forward(request, response);
				}
			} else {
				rd = getServletContext().getRequestDispatcher("index.jsp");
				rd.forward(request, response);
			}
			
		} catch (Exception e) {
			request.setAttribute("info", "");
			request.setAttribute("error", "Ocurrió un error durante la eliminación de la " + type + ". Por favor intente de nuevo y si el error persiste contacte a su administrador.");
			rd = getServletContext().getRequestDispatcher("/ListRequestsServlet");			
			rd.forward(request, response);
		}
	}
}
