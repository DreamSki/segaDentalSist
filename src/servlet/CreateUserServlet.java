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

import domain.User;
import domain.Product;
import domain.UserProduct;
import domain.UserRole;
import domain.UserRoom;

/**
 * Servlet implementation class CreateUserServlet
 */
@WebServlet(description = "servlet to create users", urlPatterns = { "/CreateUserServlet" })
public class CreateUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserServlet() {
        super();
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try{
			HttpSession session = request.getSession(true);
			User user = (User) session.getAttribute("user");
			RequestDispatcher rd;

			if(user != null){
				int roleId = user.getRoleId();

				if(roleId == 1 || roleId == 8){

					ArrayList<UserRole> roleList = (ArrayList<UserRole>)CommandExecutor.getInstance().executeDatabaseCommand(new command.ListUserRoles());
					request.setAttribute("userRoles", roleList);

					ArrayList<UserRoom> roomList = (ArrayList<UserRoom>)CommandExecutor.getInstance().executeDatabaseCommand(new command.ListUserRooms());
					request.setAttribute("userRooms", roomList);

					ArrayList<Product> productList = (ArrayList<Product>)CommandExecutor.getInstance().executeDatabaseCommand(new command.ListActiveProducts());
					request.setAttribute("products",  productList);

					rd = getServletContext().getRequestDispatcher("/createUser.jsp");			

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
			String firstName = request.getParameter("txtName");
			String lastName = request.getParameter("txtLastName");
			String identityCardId = request.getParameter("txtCedId");
			String identityCardNum = request.getParameter("txtCedIdNum");
			String password = request.getParameter("txtPassword");
			String encryptPassword = UserLoginServlet.getEncryptPassword(password);
		
			String userName = request.getParameter("txtUserName");
			Integer roleId = Integer.valueOf(request.getParameter("txtRoleId"));
			Integer roomId;

			if((roleId==2) || (roleId==4) || (roleId==5) || (roleId==6)){
				roomId = Integer.valueOf(request.getParameter("txtNumSal"));
			} else {
				roomId = null;
			}
			
			String[] productIds = request.getParameterValues("txtProductoId[]");
			User user = new User();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setIdentityCard(identityCardId+identityCardNum);
			user.setUserName(userName);
			user.setPassword(encryptPassword);
			user.setRoleId(roleId);
			user.setRoomId(roomId);

			Integer userId = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.CreateUser(user));

			if(userId > 0){
				Integer productId;
				UserProduct userProduct;
				@SuppressWarnings("unused")
				Integer rowsUpdated;

				for(String product : productIds){
					//TODO: Manejar errores de mejor manera
					productId = Integer.valueOf(product);
					userProduct = new UserProduct();
					userProduct.setUserId(userId);
					userProduct.setProductId(productId);
					rowsUpdated = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.CreateUserProduct(userProduct));
				}

				request.setAttribute("info", "El usuario fue creado exitosamente.");
				request.setAttribute("error", "");
				rd = getServletContext().getRequestDispatcher("/ListUsersServlet");			
				rd.forward(request, response);
			} else {
				request.setAttribute("info", "");
				request.setAttribute("error", "Ocurrió un error durante la creación del usuario. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
				rd = getServletContext().getRequestDispatcher("/ListUsersServlet");			

				rd.forward(request, response);
			}

		} catch (Exception e) {
			request.setAttribute("info", "");
			request.setAttribute("error", "Ocurrió un error durante la creación del usuario. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
			rd = getServletContext().getRequestDispatcher("/ListUsersServlet");			

			rd.forward(request, response);
		}
	}
}