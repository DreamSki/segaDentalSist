package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class EditClientEmail implements DatabaseCommand {
	
	private int id;
	private String email;
	
	public EditClientEmail(int id, String email){
		this.id = id;
		this.email = email;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		
		PreparedStatement sta = conn.prepareStatement("UPDATE CLIENT SET EMAIL = ?  WHERE ID = ?");
		sta.setString(1, email);
		sta.setInt(2, id);
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		
		return new Integer(rowsUpdated);
	}

}
