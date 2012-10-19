package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import domain.User;

public class EditUser implements DatabaseCommand {
	
	private User user;
	
	public EditUser(User user){
		this.user = user;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		
		PreparedStatement sta = conn.prepareStatement("UPDATE USER SET FIRST_NAME = ?, LAST_NAME = ?, IDENTITY_CARD = ?, USER_NAME = ?, PASSWORD = ?, ROLE_ID = ?, ROOM_ID = ? WHERE ID = ?");
		sta.setString(1, user.getFirstName());
		sta.setString(2, user.getLastName());
		sta.setString(3, user.getIdentityCard());
		sta.setString(4, user.getUserName());
		sta.setString(5, user.getPassword());
		sta.setInt(6, user.getRoleId());
		
		if(user.getRoomId()==null){
			sta.setNull(7, java.sql.Types.INTEGER);
		} else {
			sta.setInt(7, user.getRoomId());			
		}
		
		sta.setInt(8, user.getId());
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		
		return new Integer(rowsUpdated);
	}

}
