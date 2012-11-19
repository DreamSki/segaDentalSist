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

import domain.Product;
import domain.User;
import domain.UserProduct;
import domain.UserRole;
import domain.UserRoom;

/**
 * Servlet implementation class EditUserServlet
 */
@WebServlet(description = "servlet to edit users", urlPatterns = { "/EditUserServlet" })
public class EditUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserServlet() {
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
					
					int userId = Integer.valueOf(request.getParameter("userId"));
					request.setAttribute("userId", userId);
					
					User userInfo = (User)CommandExecutor.getInstance().executeDatabaseCommand(new command.SelectUser(userId));
					request.setAttribute("userInfo", userInfo);
					
					ArrayList<UserProduct> userProducts = (ArrayList<UserProduct>)CommandExecutor.getInstance().executeDatabaseCommand(new command.SelectUserProducts(userId));
					request.setAttribute("userProducts", userProducts);
					
					ArrayList<UserRole> roleList = (ArrayList<UserRole>)CommandExecutor.getInstance().executeDatabaseCommand(new command.ListUserRoles());
					request.setAttribute("userRoles", roleList);
					
					ArrayList<UserRoom> roomList = (ArrayList<UserRoom>)CommandExecutor.getInstance().executeDatabaseCommand(new command.ListUserRooms());
					request.setAttribute("userRooms", roomList);
					
					ArrayList<Product> productList = (ArrayList<Product>)CommandExecutor.getInstance().executeDatabaseCommand(new command.ListActiveProducts());
					request.setAttribute("products",  productList);
					rd = getServletContext().getRequestDispatcher("/editUser.jsp");			

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
			Integer userId = Integer.valueOf(request.getParameter("txtUserId"));
			String firstName = request.getParameter("txtName");
			String lastName = request.getParameter("txtLastName");
			String identityCardId = request.getParameter("txtCedId");
			String identityCardNum = request.getParameter("txtCedIdNum");
			
			
			String userName = request.getParameter("txtUserName");
			Integer roleId = Integer.valueOf(request.getParameter("roleId"));
			Integer roomId;
			String turn;
			
			if((roleId==2) || (roleId==4) || (roleId==5) || (roleId==6)){
				roomId = Integer.valueOf(request.getParameter("txtNumSal"));
				turn = request.getParameter("txtTurn");
			} else {
				roomId = null;
				turn = null;
			}
			
			String[] productIds = request.getParameterValues("txtProductoId[]");
			
			User user = new User();
			user.setId(userId);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setIdentityCard(identityCardId+identityCardNum);
			user.setUserName(userName);
			//user.setRoleId(roleId);
			user.setRoomId(roomId);
			user.setTurn(turn);
			
			Integer rowsUpdated = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.EditUser(user));
			
			if(rowsUpdated == 1){
				rowsUpdated = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.DeleteUserProduct(userId));
				Integer productId;
				UserProduct userProduct;
				
				for(String product : productIds){
					//TODO: Manejar errores de mejor manera
					productId = Integer.valueOf(product);
					userProduct = new UserProduct();
					userProduct.setUserId(userId);
					userProduct.setProductId(productId);
					rowsUpdated = (Integer) CommandExecutor.getInstance().executeDatabaseCommand(new command.CreateUserProduct(userProduct));
				}
				
				request.setAttribute("info", "El usuario fue editado exitosamente.");
				request.setAttribute("error", "");
				rd = getServletContext().getRequestDispatcher("/ListUsersServlet");			

				rd.forward(request, response);
			} else {
				request.setAttribute("info", "");
				request.setAttribute("error", "Ocurrió un error durante la edición del usuario. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
				rd = getServletContext().getRequestDispatcher("/ListUsersServlet");			

				rd.forward(request, response);
			}
			
		} catch (Exception e) {
			request.setAttribute("info", "");
			request.setAttribute("error", "Ocurrió un error durante la edición del usuario. Por favor intente de nuevo y si el error persiste contacte a su administrador.");
			rd = getServletContext().getRequestDispatcher("/ListUsersServlet");			

			rd.forward(request, response);
		}
	}
}
