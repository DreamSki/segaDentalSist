package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.CommandExecutor;

import domain.Client;

/**
 * Servlet implementation class SearchClientServlet
 */
@WebServlet(description = "servlet to create users", urlPatterns = { "/SearchClientServlet" })
public class SearchClientServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchClientServlet() {
        super();
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String identityCardNum = request.getParameter("txtCedClient");
		String identityCardId = request.getParameter("txtCedId");
		request.setAttribute("txtCedClient", identityCardNum);
		request.setAttribute("txtCedId", identityCardId);
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;

		try{
			String identityCardNum = request.getParameter("txtCedClient");
			String identityCardId = request.getParameter("txtCedId");
			String identityCard = identityCardId + identityCardNum;
			
			String info = (String)request.getAttribute("info")!=null?(String)request.getAttribute("info"):"";
			String error = (String)request.getAttribute("error")!=null?(String)request.getAttribute("error"):"";
		
			@SuppressWarnings("unchecked")
			ArrayList<Client> list = (ArrayList<Client>) CommandExecutor.getInstance().executeDatabaseCommand(new command.SearchClient(identityCard));
			
			request.setAttribute("clients", list);
			request.setAttribute("info", info);
			request.setAttribute("error", error);
			
			rd = getServletContext().getRequestDispatcher("/searchResult.jsp");			
			rd.forward(request, response);
		} catch (Exception e) {
			request.setAttribute("clients", null);
			request.setAttribute("info", "");
			request.setAttribute("error", "Ocurrió un error durante la búsqueda de clientes. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
			rd = getServletContext().getRequestDispatcher("/searchResult.jsp");			

			rd.forward(request, response);
		}
	}
}