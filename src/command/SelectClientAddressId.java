package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectClientAddressId implements DatabaseCommand {
	
	private String state; 
	private String city; 
	private int clientId; 
	private int addressType;
	
	public SelectClientAddressId(int clientId, String state, String city, int addressType){
		this.clientId = clientId;
		this.state = state;
		this.city = city;
		this.addressType = addressType;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		PreparedStatement sta = conn.prepareStatement("SELECT CA.ID FROM CLIENT_ADDRESS CA" +
				" WHERE CA.CLIENT_ID = ? AND CA.STATE = ? AND CA.CITY = ? AND ADDRESS_TYPE_ID = ? AND IS_DELETED = 0");
		ResultSet rs = null;
		Integer clientAddressId = null;
		
		sta.setInt(1, this.clientId);
		sta.setString(2, this.state);
		sta.setString(3, this.city);
		sta.setInt(4, this.addressType);
		rs = sta.executeQuery();
		
		while(rs.next()) {
			clientAddressId = rs.getInt(1);
		}
		
		rs.close();
		sta.close();
		return clientAddressId;
	}

}
