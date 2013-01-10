package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.ClientAddress;

public class CreateClientAddress implements DatabaseCommand {
	
	private ClientAddress address;

	public CreateClientAddress(ClientAddress address) {
		this.address = address;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		int lastIdInserted = -1;
		PreparedStatement sta = conn.prepareStatement("INSERT INTO CLIENT_ADDRESS (CLIENT_ID, ADDRESS_TYPE_ID, STATE, CITY, IS_SHIPPING, PROPERTY_TYPE_ID) VALUES (?, ?, ?, ?, ?, ?)");
		
		sta.setInt(1, address.getClientId());
		sta.setLong(2, address.getAddressTypeId());
		sta.setString(3, address.getState());
		sta.setString(4, address.getCity());
		sta.setInt(5, 1);
		sta.setInt(6, address.getPropertyTypeId());
				
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
