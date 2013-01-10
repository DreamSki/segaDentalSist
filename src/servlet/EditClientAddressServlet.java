package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.CommandExecutor;
import domain.ClientAddress;


/**
 * Servlet implementation class EditClientAddressServlet
 */
@WebServlet(description = "servlet to edit address's client", urlPatterns = { "/EditClientAddressServlet" })
public class EditClientAddressServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditClientAddressServlet() {
        super();
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;

		try{
			int clientId = Integer.valueOf(request.getParameter("clientId"));
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
			address.setPostalCode(postalCode);
			address.setReferencePoint(referencePoint);
			
			int propertyTypeId  = Integer.valueOf(request.getParameter("propertyTypeId"));
			address.setPropertyTypeId(propertyTypeId);
			if (propertyTypeId == 1 || propertyTypeId == 3 || propertyTypeId == 5){
				String tower = request.getParameter("txtTower"); 	
				String floor = request.getParameter("txtFloor"); 	
				String apartment = request.getParameter("txtApartment"); 	
			
				address.setTower(tower);
				address.setFloor(floor);
				address.setApartment(apartment);
			}
			
			
			
			String type = request.getParameter("type");
			int id = Integer.valueOf(request.getParameter("id"));
			Integer rowsUpdated = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.EditClientAddress(clientId, address));
			
			if(rowsUpdated == 1){
				request.setAttribute("clientId", clientId);
				request.setAttribute("type", type);
				request.setAttribute("id", id);
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
}
