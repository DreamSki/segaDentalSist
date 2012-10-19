package command;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.User;


public class ListUsers implements DatabaseCommand {

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		
		ArrayList<User> list = new ArrayList<User>();
		Statement sta = conn.createStatement();
		ResultSet rs = sta.executeQuery("SELECT A.ID, A.FIRST_NAME, A.LAST_NAME, A.USER_NAME, A.ROLE_ID, A.ROLE_NAME, URM.NAME AS ROOM_NAME, A.PRODUCT_NAME " +
				"FROM (SELECT U.ID, U.FIRST_NAME, U.LAST_NAME, U.USER_NAME, U.ROLE_ID, U.ROOM_ID, UR.NAME AS ROLE_NAME, P.NAME AS PRODUCT_NAME " +
				"FROM USER U, USER_ROLE UR, USER_PRODUCT UP, PRODUCT P WHERE U.ID <> 1 AND U.IS_DELETED = 0 AND U.ROLE_ID = UR.ID AND U.ID = UP.USER_ID AND UP.PRODUCT_ID = P.ID) AS A " +
				"LEFT JOIN USER_ROOM URM ON A.ROOM_ID = URM.ID " +
				"ORDER BY A.ID ASC"
				);
		int lastId = 0;
		
		while(rs.next()) {
			
			if (rs.isFirst()){
				lastId = rs.getInt(1);
				User user = new User();
				user.setId(lastId);
				user.setName(rs.getString(2) + " " + rs.getString(3));
				user.setUserName(rs.getString(4));
				user.setRoleId(rs.getInt(5));
				user.setRoleName(rs.getString(6));
				user.setRoomName(rs.getString(7));
				user.setProductName(rs.getString(8));
				list.add(user);
			}
			else{
				int currentId = rs.getInt(1);
				if (lastId == currentId){
					User lastUser = list.get(list.size() - 1);
					String productName = lastUser.getProductName() + ", " + rs.getString(8);
					lastUser.setProductName(productName);
				}
				else {
					lastId = rs.getInt(1);
					User user = new User();
					user.setId(lastId);
					user.setName(rs.getString(2) + " " + rs.getString(3));
					user.setUserName(rs.getString(4));
					user.setRoleId(rs.getInt(5));
					user.setRoleName(rs.getString(6));
					user.setRoomName(rs.getString(7));
					user.setProductName(rs.getString(8));
					list.add(user);
				}
			}
		}
		
		rs.close();
		sta.close();
		
		return list;
	}

}
