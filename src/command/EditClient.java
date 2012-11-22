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
		System.out.println("Modificando telefonos3");
		if (rowsUpdated > 0){
			System.out.println("Modificando telefonos2");
			
			for (int j = 0 ; j < client.getPhones().size(); j++){
				System.out.println("aqui  " + client.getPhones().get(j).getNumber() + client.getClientId() + client.getPhones().get(j).getId());
				sta = conn.prepareStatement("UPDATE CLIENT_PHONE SET PHONE_NUMBER = ? WHERE CLIENT_ID = ? AND PHONE_TYPE_ID = ?");
				sta.setString(1, client.getPhones().get(j).getNumber());
				sta.setLong(2, client.getClientId());
				sta.setLong(3, client.getPhones().get(j).getId());
				int rowsUpdatedAux = sta.executeUpdate();
				if (rowsUpdatedAux == 0){
					sta = conn.prepareStatement("INSERT INTO CLIENT_PHONE (CLIENT_ID, PHONE_NUMBER, PHONE_TYPE_ID) VALUES (?,?,?)");
					sta.setLong(1, client.getClientId());
					sta.setString(2, client.getPhones().get(j).getNumber());
					sta.setLong(3, client.getPhones().get(j).getId());
					sta.executeUpdate();	
					System.out.println("Agrego nuevo");
				}
				
			}	
		}
		sta.close();
		
		return new Integer(rowsUpdated);
	}

}
