package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Client;
import domain.ClientAddress;


public class SelectClient implements DatabaseCommand {

	private int id;
	
	public SelectClient(int id){
		this.id = id;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		PreparedStatement sta = conn.prepareStatement("SELECT C.ID, C.FIRST_NAME, C.LAST_NAME, C.IDENTITY_CARD," +
						" C.BIRTHDATE, C.EMAIL, AT.NAME, CA.STATE, CA.CITY,  CA.MUNICIPALITY, CA.URBANIZATION, CA.STREET, CA.PROPERTY_TYPE_ID, PR.NAME," +
						" CA.PROPERTY_NAME, CA.TOWER, CA.FLOOR, CA.APARTMENT " +
						" FROM CLIENT C, CLIENT_ADDRESS CA, ADDRESS_TYPE AT, PROPERTY_TYPE PR" +
						" WHERE C.ID= CA.CLIENT_ID AND CA.ADDRESS_TYPE_ID = AT.ID AND CA.PROPERTY_TYPE_ID = PR.ID" +
						" AND C.ID = ?");
		
		sta.setInt(1, this.id);
		ResultSet rs = sta.executeQuery();
		
		Client  client = new Client();
		while(rs.next()) {
			System.out.println("La busqueda tuvo resultados");
			client.setClientId(rs.getInt(1));
			client.setFirstName(rs.getString(2));
			client.setLastName(rs.getString(3));
			client.setIdentityCard(rs.getString(4));
			client.setBirthday(rs.getDate(5));
			client.setEmail(rs.getString(6));
			
			ClientAddress address = new ClientAddress();
			address.setAddressTypeName(rs.getString(7));
			address.setState(rs.getString(8));
			address.setCity(rs.getString(9));
			address.setMunicipality(rs.getString(10));
			address.setUrbanization(rs.getString(11));
			address.setStreet(rs.getString(12));
			address.setPropertyTypeId(rs.getInt(13));
			address.setPropertyTypeName(rs.getString(14));
			address.setPropertyName(rs.getString(15));
			address.setTower(rs.getString(16));
			address.setFloor(rs.getInt(17));
			address.setApartment(rs.getString(18));
			
			client.setAddress(address);
		}
		rs.close();
		sta.close();
		return client;
	}

}
