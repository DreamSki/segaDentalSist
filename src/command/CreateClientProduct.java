package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.ClientProduct;

public class CreateClientProduct implements DatabaseCommand {
	
	private ClientProduct product;

	public CreateClientProduct(ClientProduct product) {
		this.product = product;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		int lastIdInserted = -1;
		PreparedStatement sta = conn.prepareStatement("INSERT INTO CLIENT_PRODUCT (CLIENT_ID, PRODUCT_ID, AFFILIATION_DATE, SELLER_ID, EXPIRATION_DATE, STATUS_ID, AMOUNT, CONTRACT) VALUES (?, ?, STR_TO_DATE(?,'%d/%m/%Y'), ?, DATE_ADD(affiliation_date, INTERVAL 1 YEAR), ?, ?,?)");
		
		sta.setLong(1, product.getClientId());
		sta.setLong(2, product.getProductId());
		sta.setString(3, product.getAffiliationDate());
		sta.setLong(4, product.getSellerId());
		sta.setInt(5, product.getStatusId());
		sta.setString(6, product.getAmount());
		sta.setString(7, product.getContract());
				
		int rowsUpdated = sta.executeUpdate();
		
		if (rowsUpdated == 1){
			
			PreparedStatement getLastInsertId = conn.prepareStatement("SELECT LAST_INSERT_ID()");  
			ResultSet rs = getLastInsertId.executeQuery();
			
			if (rs.next()){
				lastIdInserted = rs.getInt("last_insert_id()"); 
			}
			
			getLastInsertId.close();
		}
		
		sta.close();
		
		return new Integer(lastIdInserted);
	}

}
