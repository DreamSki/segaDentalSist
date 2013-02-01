package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Client;
import domain.Product;


public class SearchClient implements DatabaseCommand {

	private String identityCard;

	public SearchClient(String identityCard){
		this.identityCard = identityCard;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		// List users in the database

		ArrayList<Client> list = new ArrayList<Client>();
		ArrayList<Client> listAux = new ArrayList<Client>();

		PreparedStatement sta = conn.prepareStatement("SELECT C.ID, C.FIRST_NAME, C.LAST_NAME, C.IDENTITY_CARD, " +
				"C.EMAIL, P.NAME FROM CLIENT C, PRODUCT P, CLIENT_PRODUCT CP " +
				"WHERE C.IDENTITY_CARD = ? AND P.ID = CP.PRODUCT_ID AND CP.CLIENT_ID = C.ID ORDER BY P.NAME ASC ");
		sta.setString(1, this.identityCard);
		ResultSet rs = sta.executeQuery();
		int lastId = 0;
		while(rs.next()) {

			if (rs.isFirst()){
				lastId = rs.getInt(1);
				Client  client = new Client();
				client.setClientId(rs.getInt(1));
				client.setFirstName(rs.getString(2));
				client.setLastName(rs.getString(3));
				client.setIdentityCard(rs.getString(4));
				client.setEmail(rs.getString(5));
				client.setType("Titular");
				Product p = new Product();
				p.setName(rs.getString(6));
				client.setProduct(p);
				list.add(client);
			}
			else{
				int currentId = rs.getInt(1);
				if (lastId == currentId){
					Client lastClient = list.get(list.size() - 1);
					Product product = lastClient.getProduct();
					String productName = product.getName() + ", " + rs.getString(6);
					product.setName(productName);
					lastClient.setProduct(product);
				}else {
					lastId = rs.getInt(1);
					Client  client = new Client();
					client.setClientId(rs.getInt(1));
					client.setFirstName(rs.getString(2));
					client.setLastName(rs.getString(3));
					client.setIdentityCard(rs.getString(4));
					client.setEmail(rs.getString(5));
					client.setType("Titular");
					Product p = new Product();
					p.setName(rs.getString(6));
					client.setProduct(p);
					list.add(client);
				}
			}
		}
		
		//Busco los beneficiarios de ese cliente
		PreparedStatement staInt = conn.prepareStatement("SELECT CB.ID, CB.IDENTITY_CARD, CB.FIRST_NAME, CB.LAST_NAME, CB.EMAIL, P.NAME FROM CLIENT_BENEFICIARY CB, " +
				"PRODUCT P, CLIENT_PRODUCT CP WHERE P.ID = CP.PRODUCT_ID AND CP.ID = CB.CLIENT_PRODUCT_ID AND CLIENT_PRODUCT_ID IN (SELECT CP.ID FROM CLIENT_PRODUCT CP " +
				"WHERE CP.CLIENT_ID = (SELECT C.ID FROM CLIENT C WHERE C.IDENTITY_CARD = ? )) ORDER BY CB.FIRST_NAME ASC, P.NAME ASC");
		staInt.setString(1, this.identityCard);
		ResultSet rsInt = staInt.executeQuery();
		
		String lastIdB = "";
		while(rsInt.next()) {
			if (rsInt.isFirst()){
				lastIdB = rsInt.getString(3) + rsInt.getString(4);

				Client benef = new Client();
				benef.setClientId(rsInt.getInt(1));
				benef.setIdentityCard(rsInt.getString(2));
				benef.setFirstName(rsInt.getString(3));
				benef.setLastName(rsInt.getString(4));
				benef.setEmail(rsInt.getString(5));
				benef.setType("Beneficiario");
				Product pAux = new Product();
				pAux.setName(rsInt.getString(6));
				benef.setProduct(pAux);
				list.add(benef);
				listAux.add(benef);
			}else{
				String currentIdB = rsInt.getString(3) + rsInt.getString(4);
				
				if (lastIdB.equals(currentIdB)){
					Client lastClient = listAux.get(listAux.size() - 1);
					Product product = lastClient.getProduct();
					String productName = product.getName() + ", " + rsInt.getString(6);
					product.setName(productName);
					lastClient.setProduct(product);
				}else {
					lastIdB = rsInt.getString(3) + rsInt.getString(4);
					Client benef = new Client();
					benef.setClientId(rsInt.getInt(1));
					benef.setIdentityCard(rsInt.getString(2));
					benef.setFirstName(rsInt.getString(3));
					benef.setLastName(rsInt.getString(4));
					benef.setEmail(rsInt.getString(5));
					benef.setType("Beneficiario");
					Product pAux = new Product();
					pAux.setName(rsInt.getString(6));
					benef.setProduct(pAux);
					list.add(benef);
					listAux.add(benef);
				}
			}
			
		}


		rs.close();
		sta.close();
		return list;
	}

}
