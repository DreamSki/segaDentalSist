package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectClientProductId implements DatabaseCommand {
	
	private int clientId;
	private int productId;

	public SelectClientProductId(Integer clientId, Integer productId) {
		this.clientId = clientId;
		this.productId = productId;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		PreparedStatement sta = conn.prepareStatement("SELECT CP.ID FROM CLIENT_PRODUCT CP" +
			" WHERE CP.CLIENT_ID = ? AND CP.PRODUCT_ID = ? AND IS_DELETED = 0");
		ResultSet rs = null;
		Integer clientProductId = null;
		
		sta.setInt(1, this.clientId);
		sta.setInt(2, this.productId);
		rs = sta.executeQuery();
		
		while(rs.next()) {
			clientProductId = rs.getInt(1);
		}
		
		rs.close();
		sta.close();
		return clientProductId;		
	}

}
