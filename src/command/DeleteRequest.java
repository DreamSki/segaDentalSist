package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRequest implements DatabaseCommand {
	
	private long id;
	
	public DeleteRequest(long id){
		this.id = id;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {

		PreparedStatement sta = conn.prepareStatement("UPDATE CLIENT_PRODUCT SET IS_DELETED=1, DELETED_DATE = NOW() WHERE ID = ?");
		sta.setLong(1, this.id);
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		return new Integer(rowsUpdated);
	}
}
