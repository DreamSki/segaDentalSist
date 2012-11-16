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
import command.MoreClientRequests;
import domain.User;

/**
 * Servlet implementation class MoreRequestServlet
 */
@WebServlet(description = "servlet to ask more requests", urlPatterns = { "/MoreRequestServlet" })
public class MoreRequestServlet extends HttpServlet {
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
    public MoreRequestServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			int howMany = Integer.valueOf(request.getParameter("howMany"));
			System.out.println("+++ limit " + howMany);
			if(user != null){
				int roleId = user.getRoleId();
				int checkerId = user.getId();
				// root y verificadores
				if(roleId == 3 || roleId == 8){
					// perform list user operations
					String info = (String)request.getAttribute("info")!=null?(String)request.getAttribute("info"):"";
					String error = (String)request.getAttribute("error")!=null?(String)request.getAttribute("error"):"";

					int rowsUpdate= (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new MoreClientRequests(checkerId, howMany));
					if (rowsUpdate > 0){
						request.setAttribute("info", info);
						request.setAttribute("error", error);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/ListRequestsServlet");
						rd.forward(request, response);
					}else{
						request.setAttribute("info", "No hay mas solicitudes pendientes para mostrar. Intente mas tarde");
						request.setAttribute("error", error);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/ListRequestsServlet");
						rd.forward(request, response);
					}
				}
			}
		} catch (Exception e) {
			request.setAttribute("info", "");
			request.setAttribute("error", "Se presentó un problema tratando de obtener mas solicitudes. Por favor intente nuevamente y si el problema persiste contacte a su administrador. ");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/ListRequestsServlet");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}