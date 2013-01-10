package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.ClientProduct;

public class SelectClientProduct implements DatabaseCommand {
	
	private String contract;
	
	public SelectClientProduct(String contract) {
		this.contract = contract;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		PreparedStatement sta = conn.prepareStatement("SELECT CP.ID, CP.AMOUNT FROM CLIENT_PRODUCT CP" +
			" WHERE CP.CONTRACT = ? AND IS_DELETED = 0");
		ResultSet rs = null;
		ClientProduct clientProduct = null;
		
		sta.setString(1, this.contract);
		rs = sta.executeQuery();
		
		while(rs.next()) {
			clientProduct = new ClientProduct();
			clientProduct.setId(rs.getLong(1));
			clientProduct.setAmount(rs.getString(2));
		}
		
		rs.close();
		sta.close();
		return clientProduct;	
	}

}
