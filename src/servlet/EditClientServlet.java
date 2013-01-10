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

import domain.Client;
import domain.ClientAddress;
import domain.PhoneType;
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
				
				//Verificador y renovante.
				if(roleId == 3 || roleId == 5 || roleId == 8){
					
					int clientId = Integer.valueOf(request.getParameter("clientId"));
					String  type = request.getParameter("type");
					String print = request.getParameter("print");
			
					@SuppressWarnings("unchecked")
					ArrayList<PhoneType> phoneType = (ArrayList<PhoneType>) CommandExecutor.getInstance().executeDatabaseCommand(new command.ListPhoneType());
			
					
					Client clientInfo = (Client)CommandExecutor.getInstance().executeDatabaseCommand(new command.SelectClient(clientId, type, phoneType));
				
					request.setAttribute("clientId", clientId);
					request.setAttribute("type", type);
					request.setAttribute("print", print);
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
		
		try{
		

			int clientId = Integer.valueOf(request.getParameter("txtClientId"));
			String type = request.getParameter("type");
			
			String firstName = request.getParameter("txtName");
			String lastName = request.getParameter("txtLastName");
			String identityCardId = request.getParameter("txtCedId");
			String identityCardNum = request.getParameter("txtCedIdNum");
			String email = request.getParameter("txtEmail"); 
			String sex = request.getParameter("txtGen"); 
			String txtHabPhone = request.getParameter("txtHabPhone");
			String txtOficPhone = request.getParameter("txtOfiPhone");
			String txtMovPhone = request.getParameter("txtMovPhone");
			String txtOtherPhone = request.getParameter("txtOtherPhone");
		
			String birthdate = request.getParameter("txtDateIni");
			
			Client c = new Client();
			c.setClientId(clientId);
			c.setFirstName(firstName);
			c.setLastName(lastName);
			c.setIdentityCard(identityCardId + identityCardNum);
			c.setEmail(email);
			c.setBirthdate(birthdate);
			c.setSex(sex);
			
			
			ArrayList<PhoneType> phones = new ArrayList<PhoneType>(); 
			if (txtHabPhone != ""){
				PhoneType habPhone = new PhoneType(1,"",txtHabPhone);
				phones.add(habPhone);
			}
			
			if (txtOficPhone != ""){
				PhoneType oficPhone = new PhoneType(2,"",txtOficPhone);
				phones.add(oficPhone);
			}
			if (txtMovPhone != ""){
				PhoneType movPhone = new PhoneType(3,"",txtMovPhone);
				phones.add(movPhone);
			}
			if (txtOtherPhone != ""){
				PhoneType otherPhone = new PhoneType(4,"",txtOtherPhone);
				phones.add(otherPhone);
			}
			c.setPhones(phones);
			
			Integer rowsUpdated = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.EditClient(c, type));
			if(rowsUpdated == 1){
				request.setAttribute("info", "El cliente fue editado exitosamente.");
				request.setAttribute("error", "");
			}
			
			
			if (type.equalsIgnoreCase("titular")){
				String state = request.getParameter("txtState");
				String city = request.getParameter("txtCity");
				String municipality = request.getParameter("txtMunicipality"); 	
				String urbanization = request.getParameter("txtUrbanization"); 	
				String street = request.getParameter("txtStreet"); 	
				String propertyName = request.getParameter("txtPropetyName"); 
				String referencePoint = request.getParameter("txtReferencePoint");
				String postalCode = request.getParameter("txtPostalCode");
			
				ClientAddress address = new ClientAddress();
				address.setCity(city);
				address.setState(state);
				address.setMunicipality(municipality);
				address.setUrbanization(urbanization);
				address.setStreet(street);
				address.setPropertyName(propertyName);
				address.setReferencePoint(referencePoint);
				address.setPostalCode(postalCode);
				
				int propertyTypeId  = Integer.valueOf(request.getParameter("txtPropertyTypeId"));
				address.setPropertyTypeId(propertyTypeId);
				
				if (propertyTypeId == 1 || propertyTypeId == 3 || propertyTypeId == 5){
					String tower = request.getParameter("txtTower"); 
					String floor = request.getParameter("txtFloor");
					String apartment = request.getParameter("txtApartment"); 	
					address.setTower(tower);
					address.setFloor(floor);
					address.setApartment(apartment);
				}
				
				if(rowsUpdated == 1){
					rowsUpdated = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.EditClientAddress(clientId, address));
					if (rowsUpdated == 1){
						request.setAttribute("clientId",clientId);
						request.setAttribute("type", type);
						rd = getServletContext().getRequestDispatcher("/editClientJustification.jsp");			
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
				
			}else{
				request.setAttribute("clientId",clientId);
				request.setAttribute("type", type);
				rd = getServletContext().getRequestDispatcher("/editClientJustification.jsp");	
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
