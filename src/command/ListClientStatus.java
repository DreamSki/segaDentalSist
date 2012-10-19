package command;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.ClientStatus;

public class ListClientStatus implements DatabaseCommand {

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		
		ArrayList<ClientStatus> list = new ArrayList<ClientStatus>();
		Statement sta = conn.createStatement();
		ResultSet rs = sta.executeQuery("SELECT CS.ID, CS.NAME FROM CLIENT_STATUS CS");
		while(rs.next()) {
			ClientStatus clientStatus = new ClientStatus();
			clientStatus.setId(rs.getInt(1));
			clientStatus.setName(rs.getString(2));
			list.add(clientStatus);
		}
		
		rs.close();
		sta.close();
		
		return list;
	}

}
