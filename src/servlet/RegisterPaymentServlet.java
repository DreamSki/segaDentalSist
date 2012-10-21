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

import domain.Payment;
import domain.Product;
import domain.User;

/**
 * Servlet implementation class RegisterPaymentServlet
 */
@WebServlet(description = "servlet to register a payment", urlPatterns = { "/RegisterPaymentServlet" })
public class RegisterPaymentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterPaymentServlet() {
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
					rd = getServletContext().getRequestDispatcher("/ListRequestsServlet");			
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
		
		//try{
			String cardType = request.getParameter("txtCardType");
			String numCard = request.getParameter("txtNumCard");
			String bank = request.getParameter("txtBank");
			String cedNumber = request.getParameter("txtCedNumClient");
			String voucher = request.getParameter("txtVoucher");
			String clientProductId = request.getParameter("clientProductId"); 
			String amount = request.getParameter("txtAmount"); 
			
			Payment payment = new Payment();
			payment.setClientProductId(Integer.valueOf(clientProductId));
			
			System.out.println("aqui toy " + cardType + "  " + numCard + " " + bank + " " + cedNumber + " " + voucher + " " + clientProductId + " " + amount);

			request.setAttribute("info", "Solicitud todavia sin guardar en BD :).");
			request.setAttribute("error", "");
			rd = getServletContext().getRequestDispatcher("/ListRequestsServlet");			
			rd.forward(request, response);
			
//			Product product = new Product();
//			product.setName(name);
//			product.setDescription(description);
//			product.setStatus(isActive);
//			product.setPrice(price);
//						
//			Integer rowsUpdated  = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.CreateProduct(product));
//			
//			if(rowsUpdated == 1){
//					request.setAttribute("info", "El producto fue creado exitosamente.");
//					request.setAttribute("error", "");
//					System.out.println("bien");
//					rd = getServletContext().getRequestDispatcher("/ListProductsServlet");			
//					rd.forward(request, response);
//			}
//			else{
//				request.setAttribute("info", "");
//				request.setAttribute("error", "Ocurrió un error durante la creación del producto. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
//				System.out.println("error");
//				rd = getServletContext().getRequestDispatcher("/ListProductsServlet");			
//
//				rd.forward(request, response);
//			}
//			
//		}catch (Exception e) {
//			request.setAttribute("info", "");
//			request.setAttribute("error", "Ocurrió un error durante la creación del producto. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
//			rd = getServletContext().getRequestDispatcher("/ListProductsServlet");			
//
//			rd.forward(request, response);
//		}
	}
}
