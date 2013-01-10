package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import command.CommandExecutor;

import domain.User;

import sun.misc.BASE64Encoder;

/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet(description = "servlet to log in users", urlPatterns = { "/UserLoginServlet" })
public class UserLoginServlet extends HttpServlet {
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
    public UserLoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		RequestDispatcher rd;
		   
		if(user != null){
			rd = getServletContext().getRequestDispatcher("/mainMenu.jsp");			

			rd.forward(request, response);
		} else {
			rd = getServletContext().getRequestDispatcher("/index.jsp");			

			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			String name = request.getParameter("txtName");
			String password = request.getParameter("txtPassword");
			String encryptPassword = getEncryptPassword(password);
			User user = (User) CommandExecutor.getInstance().executeDatabaseCommand(new command.UserExists(name, encryptPassword));
			RequestDispatcher rd;
			
			if(user != null){
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user);
				//request.setAttribute("user", user);
				rd = getServletContext().getRequestDispatcher("/mainMenu.jsp");			

				rd.forward(request, response);
			} else {
				request.setAttribute("error", "La información de nombre de usuario o contraseña introducida no es correcta.");
				rd = getServletContext().getRequestDispatcher("/index.jsp");			

				rd.forward(request, response);
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	/**
	 * 
	 * @param password
	 * @return
	 */
	public static String getEncryptPassword(String password){
		MessageDigest md;
		
		try {
			
			md = MessageDigest.getInstance("SHA");
			String clearPassword = password;
			md.update(clearPassword.getBytes("UTF-8"));
	        byte[] digestedPassword = md.digest();
		    String hash = (new BASE64Encoder()).encode(digestedPassword);
		    return hash;
	        
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
