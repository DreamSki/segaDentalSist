package command;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.ClientRequest;
import domain.Product;

public class ListClientRequests implements DatabaseCommand {

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database
		
		ArrayList<ClientRequest> list = new ArrayList<ClientRequest>();
		Statement sta = conn.createStatement();
		/* ¿COMO SE CUALES SON LOS CLIENT_PRODUCT QUE SE DEVUELVEN? QUE ESTADO TOMA? */
		/* ¿Necesito hacer que la consulta obtenga el numero de beneficiarios de cada cliente , no se hacerlo */
		/* Faltaria poner el limite de requests que se va a traer y ver bien cuales son los que se va a traer */
		ResultSet rs = sta.executeQuery("SELECT C.ID, C.FIRST_NAME, C.LAST_NAME, CP.ID, DATE_FORMAT(CP.EXPIRATION_DATE, '%d/%m/%Y'), CP.STATUS_ID, P.ID, P.NAME, P.PRICE" +
				" FROM CLIENT C, CLIENT_PRODUCT CP, PRODUCT P" +
				" WHERE C.ID = CP.CLIENT_ID AND CP.PRODUCT_ID = P.ID AND CP.STATUS_ID != 4 AND CP.STATUS_ID != 1 AND CP.STATUS_ID != 6 AND CP.IS_DELETED != 1 AND CP.STATUS_JUSTIFICATION_ID IS NULL" +
				" ORDER BY CP.EXPIRATION_DATE ASC");
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
