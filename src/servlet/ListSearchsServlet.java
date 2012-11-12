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
 * Servlet implementation class ListSearchsServlet
 */
@WebServlet(description = "servlet to list searchs", urlPatterns = { "/ListSearchsServlet" })
public class ListSearchsServlet extends HttpServlet {
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
    public ListSearchsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			
			if(user != null){
				int roleId = user.getRoleId();
				// Administradores
				if(roleId == 3 || roleId == 7 || roleId == 8){
					// perform list user operations
					String info = (String)request.getAttribute("info")!=null?(String)request.getAttribute("info"):"";
					String error = (String)request.getAttribute("error")!=null?(String)request.getAttribute("error"):"";
					request.setAttribute("info", info);
					request.setAttribute("error", error);
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/search.jsp");
					rd.forward(request, response);
				} else {
					request.setAttribute("info", "");
					request.setAttribute("error", "Usted no posee permisos para realizar esta operación");
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/mainMenu.jsp");
					rd.forward(request, response);
				}
			} else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
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
