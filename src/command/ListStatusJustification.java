package command;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.StatusJustification;

public class ListStatusJustification implements DatabaseCommand {

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		
		ArrayList<StatusJustification> list = new ArrayList<StatusJustification>();
		Statement sta = conn.createStatement();
		ResultSet rs = sta.executeQuery("SELECT SJ.ID, SJ.NAME FROM STATUS_JUSTIFICATION SJ");
		while(rs.next()) {
			StatusJustification status = new StatusJustification();
			status.setId(rs.getInt(1));
			status.setName(rs.getString(2));
			list.add(status);
		}
		
		rs.close();
		sta.close();
		
		return list;
	}

}
