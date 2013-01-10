package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectClientBeneficiaryId implements DatabaseCommand {
	
	private String contractHolder;
	private String firstNames;
	private String lastNames;

	public SelectClientBeneficiaryId(String contractHolder, String firstNames,
			String lastNames) {
		this.contractHolder = contractHolder;
		this.firstNames = firstNames;
		this.lastNames = lastNames;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		PreparedStatement sta = conn.prepareStatement("SELECT CB.ID FROM CLIENT_BENEFICIARY CB, CLIENT_PRODUCT CP" +
				" WHERE CP.CONTRACT = ? AND CP.ID = CB.CLIENT_PRODUCT_ID AND CB.FIRST_NAME = ? AND CB.LAST_NAME = ? AND CB.IS_DELETED = 0");
		ResultSet rs = null;
		Integer clientBeneficiaryId = null;
		
		sta.setString(1, this.contractHolder);
		sta.setString(2, this.firstNames);
		sta.setString(3, this.lastNames);
		rs = sta.executeQuery();
		
		while(rs.next()) {
			clientBeneficiaryId = rs.getInt(1);
		}
		
		rs.close();
		sta.close();
		return clientBeneficiaryId;
	}

}
