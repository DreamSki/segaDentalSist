package command;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.ClientStatus;
import domain.PhoneType;

public class ListPhoneType implements DatabaseCommand {

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		
		ArrayList<PhoneType> list = new ArrayList<PhoneType>();
		Statement sta = conn.createStatement();
		ResultSet rs = sta.executeQuery("SELECT PT.ID, PT.NAME FROM PHONE_TYPE PT");
		while(rs.next()) {
			PhoneType phoneType = new PhoneType();
			phoneType.setId(rs.getInt(1));
			phoneType.setName(rs.getString(2));
			list.add(phoneType);
		}
		
		rs.close();
		sta.close();
		
		return list;
	}

}
