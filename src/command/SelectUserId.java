package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectUserId implements DatabaseCommand {
	
	private String userName; 
	private int roleId;
	
	public SelectUserId(String userName, int roleId){
		this.userName = userName;
		this.roleId = roleId;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		PreparedStatement sta = conn.prepareStatement("SELECT U.ID FROM USER U" +
				" WHERE U.USER_NAME = ? AND U.ROLE_ID = ? AND IS_DELETED = 0");
		ResultSet rs = null;
		Integer userId = null;
		
		sta.setString(1, this.userName);
		sta.setInt(2, this.roleId);
		rs = sta.executeQuery();
		
		while(rs.next()) {
			userId = rs.getInt(1);
		}
		
		rs.close();
		sta.close();
		return userId;
	}

}
