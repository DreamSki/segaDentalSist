package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteUser implements DatabaseCommand {
	
	private long id;
	
	public DeleteUser(long id){
		this.id = id;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {

		PreparedStatement sta = conn.prepareStatement("UPDATE USER_PRODUCT SET IS_DELETED=1, DELETED_DATE = NOW() WHERE USER_ID = ?");
		sta.setLong(1, this.id);
		sta.executeUpdate();
		
		sta = conn.prepareStatement("UPDATE USER SET IS_DELETED = 1, DELETED_DATE = NOW() WHERE ID = ?");
		sta.setLong(1, this.id);
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		
		return new Integer(rowsUpdated);
	}
}
