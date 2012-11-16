package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.ClientRequest;
import domain.Product;

public class ListClientRequests implements DatabaseCommand {

	private int limit = 8;
	private int checkerId;
	
	public ListClientRequests(int checkerId){
		this.checkerId = checkerId;
	}
	
	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		
		ArrayList<ClientRequest> list = new ArrayList<ClientRequest>();
		
		PreparedStatement sta = conn.prepareStatement("SELECT C.ID, C.FIRST_NAME, C.LAST_NAME, CP.ID, DATE_FORMAT(CP.EXPIRATION_DATE, '%d/%m/%Y'), CP.STATUS_ID, P.ID, P.NAME, P.PRICE" +
				" FROM CLIENT C, CLIENT_PRODUCT CP, PRODUCT P" +
				" WHERE C.ID = CP.CLIENT_ID AND CP.PRODUCT_ID = P.ID AND CP.STATUS_ID != 4 AND CP.STATUS_ID != 1 AND CP.STATUS_ID != 6 AND CP.IS_DELETED != 1 " +
				" AND CP.STATUS_JUSTIFICATION_ID IS NULL AND CP.CHECKER_ID = ?" +
				" ORDER BY CP.EXPIRATION_DATE ASC LIMIT ?");
		sta.setInt(1, checkerId);
		sta.setInt(2, this.limit);
		
		ResultSet rs = sta.executeQuery();
		while(rs.next()) {
			ClientRequest clientRequest = new ClientRequest();
			clientRequest.setClientId(rs.getInt(1));
			clientRequest.setName(rs.getString(2) + " " + rs.getString(3));
			clientRequest.setId(rs.getInt(4));
			clientRequest.setExpirationDate(rs.getString(5));
			clientRequest.setStatusUserProduct(rs.getInt(6));
			clientRequest.setTypeName();
			
			Product p = new Product ();
			p.setId(rs.getInt(7));
			p.setName(rs.getString(8));
			p.setPrice(rs.getString(9));
			
			clientRequest.setProduct(p);
			
			
			list.add(clientRequest);
		}
		
		rs.close();
		sta.close();
		
		return list;
	}

}
