package command;

import domain.UserProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateUserProduct implements DatabaseCommand {

	private UserProduct userProduct;
	
	public CreateUserProduct(UserProduct userProduct){
		this.userProduct = userProduct;
	}
	
	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {

		PreparedStatement sta = conn.prepareStatement("INSERT INTO USER_PRODUCT (USER_ID, PRODUCT_ID) VALUES (?, ?)");
		sta.setInt(1, userProduct.getUserId());
		sta.setInt(2, userProduct.getProductId());
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		
		return new Integer(rowsUpdated);
	}
}
