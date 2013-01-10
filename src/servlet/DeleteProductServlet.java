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
 * Servlet implementation class DeleteProductServlet
 */
@WebServlet(description = "servlet to delete products", urlPatterns = { "/DeleteProductServlet" })
public class DeleteProductServlet extends HttpServlet {

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
    public DeleteProductServlet() {
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
		
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			
			if(user != null){
				int roleId = user.getRoleId();
				
				if(roleId == 1 || roleId == 8){
					// perform list user operations
					Long productId = Long.valueOf(request.getParameter("productId"));
					Integer rowsUpdated = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.DeleteProduct(productId));
					
					if(rowsUpdated == 1){
						request.setAttribute("info", "El producto fue eliminado exitosamente.");
						request.setAttribute("error", "");
						rd = getServletContext().getRequestDispatcher("/ListProductsServlet");			

						rd.forward(request, response);
					} else {
						request.setAttribute("info", "");
						request.setAttribute("error", "Ocurrió un error durante la eliminación del producto. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
						rd = getServletContext().getRequestDispatcher("/ListProductsServlet");			

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
			request.setAttribute("error", "Ocurrió un error durante la eliminación del producto. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
			rd = getServletContext().getRequestDispatcher("/ListProductsServlet");			

			rd.forward(request, response);
		}
	}
}
