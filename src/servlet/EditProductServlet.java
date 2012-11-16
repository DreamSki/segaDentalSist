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

import domain.Product;
import domain.User;

/**
 * Servlet implementation class EditProductServlet
 */
@WebServlet(description = "servlet to edit products", urlPatterns = { "/EditProductServlet" })
public class EditProductServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProductServlet() {
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
				
				if(roleId == 1 || roleId == 8){
					Long productId = Long.valueOf(request.getParameter("productId"));
					Product productInfo = (Product)CommandExecutor.getInstance().executeDatabaseCommand(new command.SelectProduct(productId));
					request.setAttribute("productInfo", productInfo);
					request.setAttribute("productId", productId);
					rd = getServletContext().getRequestDispatcher("/editProduct.jsp");			

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
		
		RequestDispatcher rd;
		
		try{
			int productId = Integer.valueOf(request.getParameter("txtProductId"));
			System.out.println("+++ id:"+ productId);
			String name = request.getParameter("txtNameProduct");
			System.out.println("+++ name:"+ name);
			String description = request.getParameter("txtDescription");
			System.out.println("+++ desc:"+ description);
			String price = request.getParameter("txtPrice");
			System.out.println("+++ price:"+ price);
			int isActive = 0;
			if (request.getParameter("txtIsActive") != null)
				isActive = 1;
			System.out.println("+++ status:"+ isActive);
			
			Product product = new Product();
			product.setId(productId);
			product.setName(name);
			product.setDescription(description);
			product.setStatus(isActive);
			product.setPrice(price);
			
			Integer rowsUpdated = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.EditProduct(product));
			
			if(rowsUpdated == 1){
				request.setAttribute("info", "El producto fue editado exitosamente.");
				request.setAttribute("error", "");
				rd = getServletContext().getRequestDispatcher("/ListProductsServlet");			

				rd.forward(request, response);
			} else {
				request.setAttribute("info", "");
				request.setAttribute("error", "Ocurrió un error durante la edición del producto. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
				rd = getServletContext().getRequestDispatcher("/ListProductsServlet");			

				rd.forward(request, response);
			}
			
		} catch (Exception e) {
			request.setAttribute("info", "");
			request.setAttribute("error", "Ocurrió un error durante la edición del producto. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
			rd = getServletContext().getRequestDispatcher("/ListProductsServlet");			

			rd.forward(request, response);
		}
	}
}
