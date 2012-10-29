package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import domain.Client;

public class EditClient implements DatabaseCommand {
	
	private Client client;
	
	public EditClient(Client client){
		this.client = client;
	}
	
	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		
		PreparedStatement sta = conn.prepareStatement("UPDATE CLIENT SET FIRST_NAME = ?, LAST_NAME = ?, IDENTITY_CARD = ?, EMAIL = ? WHERE ID = ?");
		sta.setString(1, client.getFirstName());
		sta.setString(2, client.getLastName());
		sta.setString(3, client.getIdentityCard());
		sta.setString(4, client.getEmail());
		sta.setLong(5, client.getClientId());
		
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		
		return new Integer(rowsUpdated);
	}

}
