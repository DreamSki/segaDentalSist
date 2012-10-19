package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.User;

public class CreateUser implements DatabaseCommand {
	
	private User user;
	
	public CreateUser(User user){
		this.user = user;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {

		int lastIdInserted = -1;
		PreparedStatement sta = conn.prepareStatement("INSERT INTO USER (FIRST_NAME, LAST_NAME, IDENTITY_CARD, USER_NAME, PASSWORD, ROLE_ID, ROOM_ID) VALUES (?, ?, ?, ?, ?, ?, ?)");
		
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
		
		int rowsUpdated = sta.executeUpdate();
		
		if (rowsUpdated == 1){
			
			PreparedStatement getLastInsertId = conn.prepareStatement("SELECT LAST_INSERT_ID()");  
			ResultSet rs = getLastInsertId.executeQuery();
			
			if (rs.next()){
				lastIdInserted = rs.getInt("last_insert_id()"); 
			}
			
			getLastInsertId.close();
		}
		
		sta.close();
		
		return new Integer(lastIdInserted);
	}

}
