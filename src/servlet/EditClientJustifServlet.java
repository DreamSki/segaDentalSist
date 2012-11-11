package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.CommandExecutor;


/**
 * Servlet implementation class EditClientJustifServlet
 */
@WebServlet(description = "servlet to add a justification for edit clients", urlPatterns = { "/EditClientJustifServlet" })
public class EditClientJustifServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditClientJustifServlet() {
        super();
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;
		try{
			int clientId = Integer.valueOf(request.getParameter("txtClientId"));
			String type = request.getParameter("type");
			String justif = request.getParameter("txtJustifEdit");
			
			
			Integer rowsUpdated = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.EditClientJustif(clientId, type, justif));
			
			if(rowsUpdated == 1){
				request.setAttribute("info", "El usuario fue modificado exitosamente.");
				request.setAttribute("error", "");
				rd = getServletContext().getRequestDispatcher("/search.jsp");			
				rd.forward(request, response);
			} else {
				request.setAttribute("info", "");
				request.setAttribute("error", "Ocurrió un error durante la edición del cliente. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
				rd = getServletContext().getRequestDispatcher("/search.jsp");			
				rd.forward(request, response);
			}
			
		} catch (Exception e) {
			request.setAttribute("info", "");
			request.setAttribute("error", "Ocurrió un error durante la edición del cliente. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
			rd = getServletContext().getRequestDispatcher("/search.jsp");			
			rd.forward(request, response);
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd;
		try{
			int clientId = Integer.valueOf(request.getParameter("txtClientId"));
			String type = request.getParameter("type");
			String justif = request.getParameter("txtJustifEdit");
			
			System.out.println("titular" + clientId + " " + justif) ;
			Integer rowsUpdated = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.EditClientJustif(clientId, type, justif));
			
			if(rowsUpdated == 1){
				request.setAttribute("info", "El usuario fue modificado exitosamente.");
				request.setAttribute("error", "");
				rd = getServletContext().getRequestDispatcher("/search.jsp");			
				rd.forward(request, response);
			} else {
				request.setAttribute("info", "");
				request.setAttribute("error", "Ocurrió un error durante la edición del cliente. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
				rd = getServletContext().getRequestDispatcher("/search.jsp");			
				rd.forward(request, response);
			}
			
		} catch (Exception e) {
			request.setAttribute("info", "");
			request.setAttribute("error", "Ocurrió un error durante la edición del cliente. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
			rd = getServletContext().getRequestDispatcher("/search.jsp");			
			rd.forward(request, response);
		}
			
	}
}
