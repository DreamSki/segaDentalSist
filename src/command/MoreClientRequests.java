package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MoreClientRequests implements DatabaseCommand {

	private int limit = 8;
	private int checkerId;
	private int howMany;
	
	public MoreClientRequests(int checkerId, int howMany){
		this.checkerId = checkerId;
		this.howMany = howMany;
	}
	
	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		int show = limit - howMany;
		PreparedStatement sta = conn.prepareStatement("UPDATE CLIENT_PRODUCT CP SET CP.CHECKER_ID = ?" +
				" WHERE CP.CHECKER_ID IS NULL AND CP.STATUS_ID != 4 AND CP.STATUS_ID != 1 AND CP.STATUS_ID != 6" +
				" AND CP.IS_DELETED != 1 AND CP.STATUS_JUSTIFICATION_ID IS NULL " +
				" ORDER BY EXPIRATION_DATE ASC LIMIT  ?");
		sta.setInt(1, checkerId);
		sta.setInt(2, show);
		
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		
		return new Integer(rowsUpdated);
	}

}
