package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Client;


public class SearchClient implements DatabaseCommand {

	private String identityCard;
	
	public SearchClient(String identityCard){
		this.identityCard = identityCard;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		
		ArrayList<Client> list = new ArrayList<Client>();
		
		PreparedStatement sta = conn.prepareStatement("SELECT C.ID, C.FIRST_NAME, C.LAST_NAME, C.IDENTITY_CARD," +
						" C.EMAIL, C.SEX " +
						" FROM CLIENT C" +
						" WHERE C.IDENTITY_CARD = ?");
		
		sta.setString(1, this.identityCard);
		ResultSet rs = sta.executeQuery();
		Client  client = new Client();
		while(rs.next()) {
			client.setClientId(rs.getInt(1));
			client.setFirstName(rs.getString(2));
			client.setLastName(rs.getString(3));
			client.setIdentityCard(rs.getString(4));
			client.setEmail(rs.getString(5));
			client.setType("Titular");
			list.add(client);
			sta = conn.prepareStatement("SELECT CB.ID, CB.IDENTITY_CARD, CB.FIRST_NAME, CB.LAST_NAME, CB.EMAIL FROM CLIENT_BENEFICIARY CB" +
					" WHERE CLIENT_PRODUCT_ID = (SELECT CP.ID FROM CLIENT_PRODUCT CP" +
					" WHERE CP.CLIENT_ID = (SELECT C.ID FROM CLIENT C WHERE C.IDENTITY_CARD = ?))");
			sta.setString(1, this.identityCard);
			rs = sta.executeQuery();
			while(rs.next()) {
				Client benef = new Client();
				benef.setClientId(rs.getInt(1));
				benef.setIdentityCard(rs.getString(2));
				benef.setFirstName(rs.getString(3));
				benef.setLastName(rs.getString(4));
				benef.setEmail(rs.getString(5));
				benef.setType("Beneficiario");
				list.add(benef);
			}
		} 
		
		
		rs.close();
		sta.close();
		return list;
	}

}
