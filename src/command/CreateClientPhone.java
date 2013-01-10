package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.PhoneType;

public class CreateClientPhone implements DatabaseCommand {
	
	private PhoneType phone;

	public CreateClientPhone(PhoneType phone) {
		this.phone = phone;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		int lastIdInserted = -1;
		PreparedStatement sta = conn.prepareStatement("INSERT INTO CLIENT_PHONE (CLIENT_ID, PHONE_NUMBER, PHONE_TYPE_ID) VALUES (?, ?, ?)");
		
		sta.setInt(1, phone.getClientId());
		sta.setString(2, phone.getNumber());
		sta.setInt(3, phone.getId());
				
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
