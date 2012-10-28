package command;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Product;

public class ListActiveProducts implements DatabaseCommand {

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List active products in the database
		
		ArrayList<Product> list = new ArrayList<Product>();
		Statement sta = conn.createStatement();
		ResultSet rs = sta.executeQuery("SELECT P.ID, P.NAME, P.DESCRIPTION, P.PRICE, P.IS_ACTIVE FROM PRODUCT P WHERE IS_DELETED=0 AND IS_ACTIVE=1");
		while(rs.next()) {
			Product product = new Product();
			product.setId(rs.getInt(1));
			product.setName(rs.getString(2));
			product.setDescription(rs.getString(3));
			product.setPrice(rs.getString(4));
			product.setStatus(rs.getInt(5));
			list.add(product);
		}
		rs.close();
		sta.close();
		
		return list;
	}

}
