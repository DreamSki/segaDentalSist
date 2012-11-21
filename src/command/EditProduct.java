package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import domain.Product;

public class EditProduct implements DatabaseCommand {
	
	private Product product;
	
	public EditProduct(Product product){
		this.product = product;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		
		PreparedStatement sta = conn.prepareStatement("UPDATE PRODUCT SET NAME = ?, DESCRIPTION = ?, PRICE = ? , IS_ACTIVE = ?, SCRIPT_STEP_2 = ?, SCRIPT_STEP_3 = ? WHERE ID = ?");
		sta.setString(1, product.getName());
		sta.setString(2, product.getDescription());
		sta.setString(3, product.getPrice());
		sta.setInt(4, product.getStatus());
		sta.setString(5, product.getScriptStep2());
		sta.setString(6, product.getScriptStep3());
		sta.setInt(7, product.getId());
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		
		return new Integer(rowsUpdated);
	}

}
