package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.CommandExecutor;
import domain.ClientCreditCard;


/**
 * Servlet implementation class EditClientCardServlet
 */
@WebServlet(description = "servlet to edit email's client", urlPatterns = { "/EditClientCardServlet" })
public class EditClientCardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditClientCardServlet() {
        super();
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;

		try{
			int clientId = Integer.valueOf(request.getParameter("clientId"));
			System.out.println("+++ id:"+ clientId);
			String cardType = request.getParameter("txtCardType");
			System.out.println("+++ cardType:"+ cardType);
			String bank = request.getParameter("txtBank");
			System.out.println("+++ bank:"+ bank);
		
			ClientCreditCard cc = new ClientCreditCard();
			cc.setBank(bank);
			cc.setCardType(cardType);
			
			String type = request.getParameter("type");
			int id = Integer.valueOf(request.getParameter("id"));
			System.out.println("+++ type:"+ type);
			System.out.println("+++ id:"+ id);
						
			Integer rowsUpdated = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.EditClientCard(clientId, cc));
			
			if(rowsUpdated == 1){
				request.setAttribute("clientId", clientId);
				request.setAttribute("type", type);
				request.setAttribute("id", id);
				System.out.println("aqui");
				rd = getServletContext().getRequestDispatcher("/EditRequestServlet");			
				rd.forward(request, response);
			} else {
				request.setAttribute("info", "");
				request.setAttribute("error", "Ocurrió un error durante la edición del producto. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
				rd = getServletContext().getRequestDispatcher("/EditRequestServlet");			
				rd.forward(request, response);
			}
			
		} catch (Exception e) {
			request.setAttribute("info", "");
			request.setAttribute("error", "Ocurrió un error durante la edición del producto. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
			rd = getServletContext().getRequestDispatcher("/EditRequestServlet");			

			rd.forward(request, response);
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}
}
