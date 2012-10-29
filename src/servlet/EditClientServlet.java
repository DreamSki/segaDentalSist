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

import domain.Client;
import domain.ClientAddress;
import domain.User;

/**
 * Servlet implementation class EditClientServlet
 */
@WebServlet(description = "servlet to edit clients", urlPatterns = { "/EditClientServlet" })
public class EditClientServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditClientServlet() {
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
					
					int clientId = Integer.valueOf(request.getParameter("clientId"));
					request.setAttribute("clientId", clientId);
					
					Client clientInfo = (Client)CommandExecutor.getInstance().executeDatabaseCommand(new command.SelectClient(clientId));
					request.setAttribute("clientInfo",clientInfo);
					rd = getServletContext().getRequestDispatcher("/editClient.jsp");			
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
		String identityCardId = request.getParameter("txtCedId");
		String identityCardNum = request.getParameter("txtCedIdNum");
	
		try{
		
			int clientId = Integer.valueOf(request.getParameter("txtClientId"));
			String firstName = request.getParameter("txtName");
			String lastName = request.getParameter("txtLastName");
			
			//String birthdate = request.getParameter("txtDateIni");
				
//			DateFormat formatter ; 
//			Date date = null ; 
//			formatter = new SimpleDateFormat("dd/mm/yyyy");
//			try {
//				date = (Date) formatter.parse(birthdate);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}  
//			System.out.println("+++ bd:"+ date);
//			
			String state = request.getParameter("txtState");
			String city = request.getParameter("txtCity");
			String municipality = request.getParameter("txtMunicipality"); 	
			String urbanization = request.getParameter("txtUrbanization"); 	
			String street = request.getParameter("txtStreet"); 	
			String propertyName = request.getParameter("txtPropetyName"); 
			
			String email = request.getParameter("txtEmail"); 
			
			Client c = new Client();
			c.setClientId(clientId);
			c.setFirstName(firstName);
			c.setLastName(lastName);
			c.setIdentityCard(identityCardId + identityCardNum);
			c.setEmail(email);
			
		
			ClientAddress address = new ClientAddress();
			address.setCity(city);
			address.setState(state);
			address.setMunicipality(municipality);
			address.setUrbanization(urbanization);
			address.setStreet(street);
			address.setPropertyName(propertyName);
			
			int propertyTypeId  = Integer.valueOf(request.getParameter("txtPropertyTypeId"));
			address.setPropertyTypeId(propertyTypeId);
			
			if (propertyTypeId == 1 || propertyTypeId == 3 || propertyTypeId == 5){
				String tower = request.getParameter("txtTower"); 	
				int floor = Integer.valueOf(request.getParameter("txtFloor")); 	
				String apartment = request.getParameter("txtApartment"); 	
			
				address.setTower(tower);
				address.setFloor(floor);
				address.setApartment(apartment);
			}
			
		
			Integer rowsUpdated = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.EditClient(c));
			if(rowsUpdated == 1){
				rowsUpdated = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.EditClientAddress(clientId, address));
				if (rowsUpdated == 1){
					request.setAttribute("info", "El cliente fue editado exitosamente.");
					request.setAttribute("error", "");
					rd = getServletContext().getRequestDispatcher("/search.jsp");			
					rd.forward(request, response);
				}
				 else {
					request.setAttribute("info", "");
					request.setAttribute("error", "Ocurrió un error durante la edición del cliente. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
					rd = getServletContext().getRequestDispatcher("/search.jsp");			
					rd.forward(request, response);
				}
				
			} else {
				request.setAttribute("info", "");
				request.setAttribute("error", "Ocurrió un error durante la edición del cliente. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
				rd = getServletContext().getRequestDispatcher("/search.jsp");			
				rd.forward(request, response);
			}
			
		} catch (Exception e) {
			request.setAttribute("info", "");
			request.setAttribute("error", "Ocurrió un error durante la edición del usuario. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
			rd = getServletContext().getRequestDispatcher("/search.jsp");			
			rd.forward(request, response);
		}
	}
}
