package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Client;
import domain.ClientAddress;


public class SelectClient implements DatabaseCommand {

	private int id;
	private String type; 
	
	public SelectClient(int id, String type){
		this.id = id;
		this.type = type;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		PreparedStatement sta = null;
		ResultSet rs = null;
		Client client = null;
		if (type.equalsIgnoreCase("titular")){
			sta = conn.prepareStatement("SELECT C.ID, C.FIRST_NAME, C.LAST_NAME, C.IDENTITY_CARD," +
						" DATE_FORMAT(C.BIRTHDATE, '%d/%m/%Y'), C.EMAIL, C.SEX, AT.NAME, CA.STATE, CA.CITY,  CA.MUNICIPALITY, CA.URBANIZATION, CA.STREET, CA.PROPERTY_TYPE_ID, PR.NAME," +
						" CA.PROPERTY_NAME, CA.TOWER, CA.FLOOR, CA.APARTMENT " +
						" FROM CLIENT C, CLIENT_ADDRESS CA, ADDRESS_TYPE AT, PROPERTY_TYPE PR" +
						" WHERE C.ID= CA.CLIENT_ID AND CA.ADDRESS_TYPE_ID = AT.ID AND CA.PROPERTY_TYPE_ID = PR.ID" +
						" AND C.ID = ?");
		
			sta.setInt(1, this.id);
			rs = sta.executeQuery();
			
			client = new Client();
			while(rs.next()) {
				client.setClientId(rs.getInt(1));
				client.setFirstName(rs.getString(2));
				client.setLastName(rs.getString(3));
				client.setIdentityCard(rs.getString(4));
				client.setBirthdate(rs.getString(5));
				client.setEmail(rs.getString(6));
				client.setSex(rs.getString(7));
				
				
				ClientAddress address = new ClientAddress();
				address.setAddressTypeName(rs.getString(8));
				address.setState(rs.getString(9));
				address.setCity(rs.getString(10));
				address.setMunicipality(rs.getString(11));
				address.setUrbanization(rs.getString(12));
				address.setStreet(rs.getString(13));
				address.setPropertyTypeId(rs.getInt(14));
				address.setPropertyTypeName(rs.getString(15));
				address.setPropertyName(rs.getString(16));
				address.setTower(rs.getString(17));
				address.setFloor(rs.getInt(18));
				address.setApartment(rs.getString(19));
				
				client.setAddress(address);
				client.setType("Titular");
				
				sta = conn.prepareStatement("SELECT CP.PHONE_NUMBER , CP.PHONE_TYPE_ID" +
						" FROM CLIENT_PHONE CP" +
						" WHERE CP.CLIENT_ID = ?");
				sta.setInt(1, this.id);
				rs = sta.executeQuery();
				
				ArrayList<String> phones = new ArrayList<String>(); ;
				while(rs.next()) {
					String number = rs.getString(1);
					String type = rs.getString(2);
					String phone = type + "-" + number;
					phones.add(phone);
				}
				client.setPhones(phones);
				
			}
			
		}else{
			sta = conn.prepareStatement("SELECT B.ID, B.FIRST_NAME, B.LAST_NAME, B.IDENTITY_CARD," +
					" DATE_FORMAT(B.BIRTHDATE, '%d/%m/%Y'), B.EMAIL, B.SEX FROM CLIENT_BENEFICIARY B" +
					" WHERE B.ID = ?");
			sta.setInt(1, this.id);
			
			rs = sta.executeQuery();
			
			client = new Client();
			while(rs.next()) {
				client.setClientId(rs.getInt(1));
				client.setFirstName(rs.getString(2));
				client.setLastName(rs.getString(3));
				client.setIdentityCard(rs.getString(4));
				client.setBirthdate(rs.getString(5));
				client.setEmail(rs.getString(6));
				client.setSex(rs.getString(7));
				client.setType("Beneficiario");
			}
			
		}
			
	
		rs.close();
		sta.close();
		return client;
	}

}
