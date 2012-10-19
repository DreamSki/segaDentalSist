package command;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.UserRole;

public class ListUserRoles implements DatabaseCommand {

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		
		ArrayList<UserRole> list = new ArrayList<UserRole>();
		Statement sta = conn.createStatement();
		ResultSet rs = sta.executeQuery("SELECT U.ID, U.NAME FROM USER_ROLE U WHERE U.ID != 8");
		while(rs.next()) {
			UserRole userRole = new UserRole();
			userRole.setId(rs.getInt(1));
			userRole.setName(rs.getString(2));
			list.add(userRole);
		}
		
		rs.close();
		sta.close();
		
		return list;
	}

}
