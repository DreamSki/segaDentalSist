package command;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.UserRoom;

public class ListUserRooms implements DatabaseCommand {

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		
		ArrayList<UserRoom> list = new ArrayList<UserRoom>();
		Statement sta = conn.createStatement();
		ResultSet rs = sta.executeQuery("SELECT U.ID, U.NAME FROM USER_ROOM U");
		while(rs.next()) {
			UserRoom userRoom = new UserRoom();
			userRoom.setId(rs.getInt(1));
			userRoom.setName(rs.getString(2));
			list.add(userRoom);
		}
		
		rs.close();
		sta.close();
		
		return list;
	}
}
