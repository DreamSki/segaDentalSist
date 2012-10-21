package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Client;
import domain.ClientAddress;
import domain.ClientCard;
import domain.Product;


public class SelectClientRequest implements DatabaseCommand {

	private Long clientId;
	private Long id;
	
	public SelectClientRequest(Long clientId, long id){
		this.clientId = clientId;
		this.id = id;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		
		Client clientRequest = new Client();
		PreparedStatement sta = conn.prepareStatement("SELECT C.FIRST_NAME, C.LAST_NAME, C.IDENTITY_CARD," +
						" C.EMAIL, C.SEX, AT.NAME, CA.STATE, CA.CITY,  CA.MUNICIPALITY, CA.URBANIZATION, CA.STREET, CA.PROPERTY_TYPE_ID, PR.NAME," +
						" CA.PROPERTY_NAME, CA.TOWER, CA.FLOOR, CA.APARTMENT, CC.ID, CC.NUMBER, CC.BANK, CCT.NAME, CC.EXPIRATION_DATE," +
						" CC.CVC, P.NAME, P.PRICE" +
						" FROM CLIENT C, CLIENT_ADDRESS CA, CLIENT_CREDIT_CARD CC, ADDRESS_TYPE AT," +
						" CREDIT_CARD_TYPE CCT, PRODUCT P, CLIENT_PRODUCT CP, PROPERTY_TYPE PR" +
						" WHERE C.ID = CA.CLIENT_ID AND C.ID = CC.CLIENT_ID AND CA.ADDRESS_TYPE_ID = AT.ID" +
						" AND CC.TYPE_ID = CCT.ID AND CP.CLIENT_ID = C.ID AND CP.PRODUCT_ID = P.ID AND CA.PROPERTY_TYPE_ID = PR.ID AND CP.ID = ? AND CP.CLIENT_ID = ? ;");
		
		sta.setLong(1, this.id);
		sta.setLong(2, this.clientId);
		ResultSet rs = sta.executeQuery();
		
		while(rs.next()) {
			
			clientRequest.setFirstName(rs.getString(1));
			clientRequest.setLastName(rs.getString(2));
			clientRequest.setIdentityCard(rs.getString(3));
			clientRequest.setEmail(rs.getString(4));
			clientRequest.setSex(rs.getString(5));
			
			ClientAddress address = new ClientAddress();
			address.setAddressTypeName(rs.getString(6));
			address.setState(rs.getString(7));
			address.setCity(rs.getString(8));
			address.setMunicipality(rs.getString(9));
			address.setUrbanization(rs.getString(10));
			address.setStreet(rs.getString(11));
			address.setPropertyTypeId(rs.getInt(12));
			address.setPropertyTypeName(rs.getString(13));
			address.setPropertyName(rs.getString(14));
			address.setTower(rs.getString(15));
			address.setFloor(rs.getInt(16));
			address.setApartment(rs.getString(17));
			
			ClientCard card = new ClientCard();
			card.setId(rs.getInt(18));
			card.setCardNumber(rs.getLong(19));
			card.setBank(rs.getString(20));
			card.setCardType(rs.getString(21));
			card.setExpirationDate(rs.getDate(22));
			card.setCVC(rs.getInt(23));
			
			Product p = new Product();
			p.setName(rs.getString(24));
			p.setPrice(rs.getString(25));
			
			clientRequest.setAddress(address);
			clientRequest.setCard(card);
			clientRequest.setProduct(p);
			
			
		}
		
		rs.close();
		sta.close();
		
		return clientRequest;
	}

}
