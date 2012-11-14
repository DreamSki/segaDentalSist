package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import domain.Client;

public class EditClient implements DatabaseCommand {
	
	private Client client;
	private String type;
	
	
	public EditClient(Client client, String type){
		this.client = client;
		this.type = type;
		
	}
	
	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		PreparedStatement sta = null;
		if (type.equalsIgnoreCase("titular")){
			System.out.println("aqui modificando titular"  + client.getFirstName() + client.getLastName() + client.getSex());
			sta = conn.prepareStatement("UPDATE CLIENT SET FIRST_NAME = ?, LAST_NAME = ?, IDENTITY_CARD = ?, EMAIL = ?, SEX = ?," +
					" BIRTHDATE = STR_TO_DATE(?,'%d/%m/%Y') WHERE ID = ?");
			sta.setString(1, client.getFirstName());
			sta.setString(2, client.getLastName());
			sta.setString(3, client.getIdentityCard());
			sta.setString(4, client.getEmail());
			sta.setString(5, client.getSex());
			sta.setString(6, client.getBirthdate());
			sta.setLong(7, client.getClientId());
		}else{
			System.out.println("aqui modificando beneficiario" + client.getFirstName() + client.getLastName() + client.getSex());
			
			sta = conn.prepareStatement("UPDATE CLIENT_BENEFICIARY SET FIRST_NAME = ?, LAST_NAME = ?, IDENTITY_CARD = ?, EMAIL = ?, SEX = ?, " +
					" BIRTHDATE = STR_TO_DATE(?,'%d/%m/%Y') WHERE ID = ?");
			sta.setString(1, client.getFirstName());
			sta.setString(2, client.getLastName());
			sta.setString(3, client.getIdentityCard());
			sta.setString(4, client.getEmail());
			sta.setString(5, client.getSex());
			sta.setString(6, client.getBirthdate());
			sta.setLong(7, client.getClientId());
		}
		int rowsUpdated = sta.executeUpdate();
		sta.close();
		
		return new Integer(rowsUpdated);
	}

}
