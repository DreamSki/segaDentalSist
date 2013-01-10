package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.ClientBeneficiary;

public class CreateClientBeneficiary implements DatabaseCommand {
	
	private ClientBeneficiary beneficiary;

	public CreateClientBeneficiary(ClientBeneficiary beneficiary) {
		this.beneficiary = beneficiary;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		int lastIdInserted = -1;
		PreparedStatement sta = conn.prepareStatement("INSERT INTO CLIENT_BENEFICIARY (CLIENT_PRODUCT_ID, IDENTITY_CARD, FIRST_NAME, LAST_NAME, IS_HOLDER) VALUES (?, ?, ?, ?, 0)");
		
		sta.setLong(1, beneficiary.getClientProduct());
		sta.setString(2, beneficiary.getIdentityCard());
		sta.setString(3, beneficiary.getFirstName());
		sta.setString(4, beneficiary.getLastName());
				
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
