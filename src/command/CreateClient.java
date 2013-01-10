package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Client;

public class CreateClient implements DatabaseCommand {
	
	private Client client;
	
	public CreateClient(Client client){
		this.client = client;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {

		int lastIdInserted = -1;
		PreparedStatement sta = conn.prepareStatement("INSERT INTO CLIENT (FIRST_NAME, LAST_NAME, IDENTITY_CARD, EMAIL, SEX, IS_HOLDER) VALUES (?, ?, ?, ?, ?, ?)");
		
		sta.setString(1, client.getFirstName());
		sta.setString(2, client.getLastName());
		sta.setString(3, client.getIdentityCard());
		sta.setString(4, client.getEmail());
		sta.setString(5, client.getSex());
		sta.setInt(6, client.getIsHolder());
								
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
