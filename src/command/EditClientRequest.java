package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import domain.ClientRequest;


public class EditClientRequest implements DatabaseCommand {
	
	private ClientRequest cr;
	
	public EditClientRequest(ClientRequest cr){
		this.cr = cr;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		PreparedStatement sta;
		if (cr.getJustificationId() == 5){
			System.out.println("id ");
			sta = conn.prepareStatement("UPDATE CLIENT_PRODUCT SET STATUS_JUSTIFICATION_ID = ?, OTHER_JUSTIFICATION = ? WHERE ID = ?");
			sta.setInt(1, cr.getJustificationId());
			sta.setString(2, cr.getJustification());
			sta.setLong(3, cr.getId());

		}
		else{
			sta = conn.prepareStatement("UPDATE CLIENT_PRODUCT SET STATUS_JUSTIFICATION_ID = ? WHERE ID = ?");
			sta.setInt(1, cr.getJustificationId());
			sta.setLong(2, cr.getId());
			
		}
	
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		
		return new Integer(rowsUpdated);
	}

}
