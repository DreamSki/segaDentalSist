package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.UserProduct;

public class SelectUserProducts implements DatabaseCommand {
	
	private int userId;
	
	public SelectUserProducts(int userId){
		this.userId = userId;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database

		ArrayList<UserProduct> list = new ArrayList<UserProduct>();
		PreparedStatement sta = conn.prepareStatement("SELECT UP.USER_ID, UP.PRODUCT_ID" +
				" FROM USER_PRODUCT UP WHERE UP.USER_ID = ?");
		sta.setInt(1, this.userId);
		ResultSet rs = sta.executeQuery();
		
		while(rs.next()) {
			UserProduct userProduct = new UserProduct();
			userProduct.setUserId(rs.getInt(1));
			userProduct.setProductId(rs.getInt(2));
			list.add(userProduct);
		}
		
		rs.close();
		sta.close();
		
		return list;
	}

}
