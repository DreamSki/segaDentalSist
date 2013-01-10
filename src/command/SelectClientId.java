package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectClientId implements DatabaseCommand {
	
	private String identityCard; 
	
	public SelectClientId(String identityCard){
		this.identityCard = identityCard;
	}
	
	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		PreparedStatement sta = conn.prepareStatement("SELECT C.ID FROM CLIENT C" +
				" WHERE C.IDENTITY_CARD = ? AND IS_DELETED = 0");
		ResultSet rs = null;
		Integer clientId = null;
		
		sta.setString(1, this.identityCard);
		rs = sta.executeQuery();
		
		while(rs.next()) {
			clientId = rs.getInt(1);
		}
		
		rs.close();
		sta.close();
		return clientId;
	}

}
