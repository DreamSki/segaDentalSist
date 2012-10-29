package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class EditClientIdentityCard implements DatabaseCommand {
	
	private int id;
	private String identityCard;
	
	public EditClientIdentityCard(int id, String identityCard){
		this.id = id;
		this.identityCard = identityCard;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		
		PreparedStatement sta = conn.prepareStatement("UPDATE CLIENT SET IDENTITY_CARD = ? WHERE ID = ?");
		sta.setString(1, identityCard);
		sta.setInt(2, id);
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		
		return new Integer(rowsUpdated);
	}

}
