package servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import command.CommandExecutor;

import domain.Payment;
import domain.User;
import Util.SendEmail;

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
		
		try{
			HttpSession session = request.getSession(true);
			User user = (User) session.getAttribute("user");
			
			String email = request.getParameter("clientEmail");
			String voucher = request.getParameter("txtVoucher");
			String clientProductId = request.getParameter("clientProductId"); 
			String amount = request.getParameter("txtAmount"); 
			String cardId = request.getParameter("txtCardId"); 
			String name = request.getParameter("txtName");
			String type = request.getParameter("type");
			
			Payment payment = new Payment();
			payment.setClientProductId(Integer.valueOf(clientProductId));
			payment.setCreditCardId(Integer.valueOf(cardId));
			payment.setAmount(amount);
			payment.setVoucher(voucher);
			payment.setCheckerId(user.getId());
			payment.setType(type);
		
			Integer rowsUpdated  = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.CreatePayment(payment));
			
			if(rowsUpdated == 1){
				Properties propertiesFile = new Properties();
				propertiesFile.load( new FileInputStream( getServletContext().getInitParameter("properties") ) );
				SendEmail.sendEmail(propertiesFile, email, name, false, "contrato");

				request.setAttribute("info", "El pago fue registrado existosamente.");
				request.setAttribute("error", "");
				rd = getServletContext().getRequestDispatcher("/ListRequestsServlet");			
				rd.forward(request, response);
			}
			else{
				request.setAttribute("info", "");
				request.setAttribute("error", "Ocurrió un error durante el registro del pago. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
				System.out.println("error");
				rd = getServletContext().getRequestDispatcher("/ListRequestsServlet");			
				rd.forward(request, response);
			}
			
		}catch (Exception e) {
			request.setAttribute("info", "");
			request.setAttribute("error", "Ocurrió un error durante el registro del pago. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
			rd = getServletContext().getRequestDispatcher("/ListRequestsServlet");			

			rd.forward(request, response);
		}
	}
	
	
}
