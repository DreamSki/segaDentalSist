package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.ReportItem;

public class CreateReportOfRequests implements DatabaseCommand {
	
	String baseQuery = "SELECT C.IDENTITY_CARD, C.LAST_NAME, C.FIRST_NAME, "+
						" U.FIRST_NAME AS SELLER_FIRST_NAME, U.LAST_NAME AS SELLER_LAST_NAME, UR.NAME AS ROOM, U.TURN, " +
						" CA.CITY, CA.STATE, CCT.NAME, PP.VOUCHER, PP.AMOUNT, CCC.BANK, CP.STATUS_ID" +
						" FROM CLIENT C, USER U, PRODUCT P, CLIENT_PRODUCT CP, USER_ROOM UR, CLIENT_ADDRESS CA, PROPERTY_TYPE PT, " +
						" CREDIT_CARD_TYPE CCT, PAYMENT_PRODUCT PP, CLIENT_CREDIT_CARD CCC " +
						" WHERE C.ID = CP.CLIENT_ID AND CP.PRODUCT_ID = P.ID AND CP.SELLER_ID = U.ID AND U.ROOM_ID = UR.ID AND C.ID = CA.CLIENT_ID AND" +
						" CA.IS_SHIPPING = 1 AND CA.PROPERTY_TYPE_ID = PT.ID AND PP.DATE = CURDATE() " +
						" AND PP.CLIENT_PRODUCT_ID = CP.ID AND PP.CREDIT_CARD_ID = CCC.ID  AND CCC.TYPE_ID = CCT.ID" +
						" ORDER BY CP.STATUS_ID";
	
	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {
		
		ArrayList<ReportItem> list = new ArrayList<ReportItem>();
		PreparedStatement sta = conn.prepareStatement(baseQuery);
		ResultSet rs = sta.executeQuery();
		
		while(rs.next()) {
			ReportItem reportItem = new ReportItem();
			reportItem.setIdentityCard(rs.getString(1));
			reportItem.setLastName(rs.getString(2));
			reportItem.setFirstName(rs.getString(3));
			reportItem.setSeller(rs.getString(4), rs.getString(5));
			reportItem.setRoom(rs.getString(6));
			reportItem.setTurn(rs.getString(7));
			reportItem.setCity(rs.getString(8));
			reportItem.setState(rs.getString(9));
			reportItem.setCardType(rs.getString(10));
			reportItem.setVoucher(rs.getString(11));
			reportItem.setAmount(rs.getString(12));
			reportItem.setBank(rs.getString(13));
			reportItem.setType(rs.getInt(14));
			
			list.add(reportItem);
		}
		
		rs.close();
		sta.close();
		
		return list;
	}

}
