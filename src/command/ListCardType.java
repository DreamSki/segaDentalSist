package command;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.CreditCard;

public class ListCardType implements DatabaseCommand {

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		
		ArrayList<CreditCard> list = new ArrayList<CreditCard>();
		Statement sta = conn.createStatement();
		ResultSet rs = sta.executeQuery("SELECT CCT.ID, CCT.NAME FROM CREDIT_CARD_TYPE CCT WHERE IS_DELETED != 1");
		while(rs.next()) {
			CreditCard cc = new CreditCard();
			cc.setId(rs.getInt(1));
			cc.setName(rs.getString(2));
			list.add(cc);
		}
		
		rs.close();
		sta.close();
		
		return list;
	}

}
