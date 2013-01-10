package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditClientProduct implements DatabaseCommand {
	
	private String amount;
	private long id;

	public EditClientProduct(long id, String amount) {
		this.id = id;
		this.amount = amount;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {

		PreparedStatement sta = conn.prepareStatement("UPDATE CLIENT_PRODUCT SET AMOUNT = ? WHERE ID = ?");
		sta.setString(1, this.amount);
		sta.setLong(2, this.id);
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		
		return new Integer(rowsUpdated);
	}

}
