package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteUserProduct implements DatabaseCommand {

	private int id;
	
	public DeleteUserProduct(int id){
		this.id = id;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {

		PreparedStatement sta = conn.prepareStatement("DELETE FROM USER_PRODUCT WHERE USER_ID = ?");
		sta.setLong(1, this.id);
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		
		return new Integer(rowsUpdated);
	}
}
