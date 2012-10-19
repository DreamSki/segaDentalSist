package command;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CommandExecutor {

	private static CommandExecutor myOnlyInstance = null;
	private DataSource ds = null;
	
	// accessor method for the singleton
	public static CommandExecutor getInstance() throws NamingException {
		if (myOnlyInstance == null) {
			myOnlyInstance = new CommandExecutor();
		}
		return myOnlyInstance;
	}
	
	// Constructor for the singleton
	private CommandExecutor() throws NamingException {
		getDataSource();
	}
	
	// get the data source from initial context
	public DataSource getDataSource() throws NamingException {
		
		if (ds == null) {			
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/segadental");			
		}
		
		return ds;
	}
	
	// get the SQL connection from the database
	public Connection getConnection() throws NamingException, SQLException {
		return getDataSource().getConnection();
		
	}
		
	// execute particular database command
	public Object executeDatabaseCommand(DatabaseCommand c) throws Exception {
		
		Connection conn = null;
		try {
			conn = getConnection();
			Object o = c.executeDatabaseOperation(conn);
			return o;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (conn != null) conn.close();
		}
	}
}
