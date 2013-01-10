package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectClientPhoneId implements DatabaseCommand {
	
	private int clientId;
	private String phoneNumber;
	private int phoneTypeId;

	public SelectClientPhoneId(Integer clientId, String phoneNumber,
			int phoneTypeId) {
		this.clientId = clientId;
		this.phoneNumber = phoneNumber;
		this.phoneTypeId = phoneTypeId;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		PreparedStatement sta = conn.prepareStatement("SELECT CP.CLIENT_ID FROM CLIENT_PHONE CP" +
				" WHERE CP.CLIENT_ID = ? AND CP.PHONE_NUMBER = ? AND CP.PHONE_TYPE_ID = ? AND IS_DELETED = 0");
		ResultSet rs = null;
		Integer clientPhoneId = null;
		
		sta.setInt(1, this.clientId);
		sta.setString(2, this.phoneNumber);
		sta.setInt(3, this.phoneTypeId);
		rs = sta.executeQuery();
		
		while(rs.next()) {
			clientPhoneId = rs.getInt(1);
		}
		
		rs.close();
		sta.close();
		return clientPhoneId;
	}

}
