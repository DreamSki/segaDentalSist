package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Client;


public class SearchClient implements DatabaseCommand {

	private String identityCard;
	
	public SearchClient(String identityCard){
		this.identityCard = identityCard;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		System.out.println("Entro aqui a la busqueda");
		PreparedStatement sta = conn.prepareStatement("SELECT C.ID, C.FIRST_NAME, C.LAST_NAME, C.IDENTITY_CARD," +
						" C.EMAIL" +
						" FROM CLIENT C" +
						" WHERE C.IDENTITY_CARD = ?");
		
		sta.setString(1, this.identityCard);
		ResultSet rs = sta.executeQuery();
		Client  client = new Client();
		while(rs.next()) {
			System.out.println("La busqueda tuvo resultados");
			client.setClientId(rs.getInt(1));
			client.setFirstName(rs.getString(2));
			client.setLastName(rs.getString(3));
			client.setIdentityCard(rs.getString(4));
			client.setEmail(rs.getString(5));
			System.out.println("clienteId " + client.getClientId());
		} 
		rs.close();
		sta.close();
		return client;
	}

}
