package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Product;

public class SelectProduct implements DatabaseCommand {

	private Long productId;
	
	public SelectProduct(Long productId){
		this.productId = productId;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database

		Product product = new Product();
		PreparedStatement sta = conn.prepareStatement("SELECT P.ID, P.NAME, P.DESCRIPTION, P.PRICE, P.IS_ACTIVE, P.SCRIPT_STEP_2, P.SCRIPT_STEP_3 FROM PRODUCT P WHERE P.ID = ? ");
		sta.setLong(1, this.productId);
		ResultSet rs = sta.executeQuery();
		
		if (rs.next()) {
			product.setId(rs.getInt(1));
			product.setName(rs.getString(2));
			product.setDescription(rs.getString(3));
			product.setPrice(rs.getString(4));
			product.setStatus(rs.getInt(5));
			product.setScriptStep2(rs.getString(6));
			product.setScriptStep3(rs.getString(7));			
		}
		
		rs.close();
		sta.close();
		
		return product;
	}
}
