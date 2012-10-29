package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import domain.ClientCreditCard;


public class EditClientCard implements DatabaseCommand {
	
	private int id;
	private ClientCreditCard cc;
	
	public EditClientCard(int id, ClientCreditCard cc){
		this.id = id;
		this.cc = cc;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		
		PreparedStatement sta = conn.prepareStatement("UPDATE CLIENT_CREDIT_CARD SET BANK = ?, TYPE_ID = ?  WHERE ID = ?");
		sta.setString(1, cc.getBank());
		sta.setString(2, cc.getCardType());
		sta.setInt(3, id);
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		
		return new Integer(rowsUpdated);
	}

}
