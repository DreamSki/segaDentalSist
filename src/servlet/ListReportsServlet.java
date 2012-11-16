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
import domain.ClientStatus;
import domain.Product;
import domain.StatusJustification;
import domain.User;

/**
 * Servlet implementation class ListReportsServlet
 */
@WebServlet(description = "servlet to generate reports", urlPatterns = { "/ListReportsServlet" })
public class ListReportsServlet extends HttpServlet {
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
    public ListReportsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			
			if(user != null){
				int roleId = user.getRoleId();
				// Administradores
				if(roleId == 1 || roleId == 8){
					// perform list user operations
					String info = (String)request.getAttribute("info")!=null?(String)request.getAttribute("info"):"";
					String error = (String)request.getAttribute("error")!=null?(String)request.getAttribute("error"):"";
					ArrayList<Product> listProducts = (ArrayList<Product>)CommandExecutor.getInstance().executeDatabaseCommand(new command.ListProducts());
					request.setAttribute("products", listProducts);
					ArrayList<ClientStatus> listStatus = (ArrayList<ClientStatus>)CommandExecutor.getInstance().executeDatabaseCommand(new command.ListClientStatus());
					request.setAttribute("clientStatus", listStatus);
					ArrayList<StatusJustification> listCallStatus = (ArrayList<StatusJustification>)CommandExecutor.getInstance().executeDatabaseCommand(new command.ListStatusJustification());
					request.setAttribute("callStatus", listCallStatus);
					request.setAttribute("info", info);
					request.setAttribute("error", error);
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/reports.jsp");
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
