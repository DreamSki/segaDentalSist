package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.User;

public class SelectUser implements DatabaseCommand {

	private int userId;
	
	public SelectUser(int userId){
		this.userId = userId;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database

		User user = new User();
		PreparedStatement sta = conn.prepareStatement("SELECT A.ID, A.FIRST_NAME, A.LAST_NAME, A.IDENTITY_CARD, A.USER_NAME, A.PASSWORD, " +
				"A.ROLE_ID, A.ROLE_NAME, URM.ID AS ROOM_ID, URM.NAME AS ROOM_NAME, A.TURN " +
				"FROM (SELECT U.ID, U.FIRST_NAME, U.LAST_NAME, U.IDENTITY_CARD, U.USER_NAME, U.PASSWORD, U.ROLE_ID, U.ROOM_ID, UR.NAME AS ROLE_NAME, U.TURN " +
				"FROM USER U, USER_ROLE UR WHERE U.ROLE_ID = UR.ID AND U.ID = ?) AS A " +
				"LEFT JOIN USER_ROOM URM ON A.ROOM_ID = URM.ID " +
				"ORDER BY A.ID ASC");
		
		sta.setLong(1, this.userId);
		ResultSet rs = sta.executeQuery();
		
		if (rs.next()) {
			user.setId(rs.getInt(1));
			user.setFirstName(rs.getString(2));
			user.setLastName(rs.getString(3));
			user.setIdentityCard(rs.getString(4));
			user.setUserName(rs.getString(5));
			user.setPassword(rs.getString(6));
			user.setRoleId(rs.getInt(7));
			user.setRoleName(rs.getString(8));
			user.setRoomId(rs.getInt(9));
			user.setRoomName(rs.getString(10));
			user.setTurn(rs.getString(11));
		}
		
		rs.close();
		sta.close();
		
		return user;
	}
}
