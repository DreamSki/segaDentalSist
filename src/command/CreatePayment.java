package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import domain.Payment;

public class CreatePayment implements DatabaseCommand {
	
	private Payment payment;
	
	public CreatePayment(Payment payment){
		this.payment = payment;
	}

	@Override
	public Object executeDatabaseOperation(Connection conn) throws SQLException {

		PreparedStatement sta = conn.prepareStatement("INSERT INTO PAYMENT_PRODUCT (CLIENT_PRODUCT_ID, CREDIT_CARD_ID, DATE, AMOUNT, VOUCHER) VALUES (?, ?, DATE(NOW()) , ?,  ?)");
		sta.setInt(1, payment.getClientProductId());
		sta.setLong(2, payment.getCreditCardId());
		sta.setString(3, payment.getAmount());
		sta.setString(4, payment.getVoucher());
		int rowsUpdated = sta.executeUpdate();
		if (rowsUpdated == 1){
			sta = conn.prepareStatement("UPDATE CLIENT_PRODUCT SET STATUS_ID = 1, AFFILIATION_DATE = DATE(NOW()), EXPIRATION_DATE = DATE_ADD(DATE(NOW()),INTERVAL 365 DAY)," +
					" CHECKER_ID = ? WHERE ID = ?");
			sta.setInt(1,payment.getCheckerId());
			sta.setInt(2,payment.getClientProductId());
			rowsUpdated = sta.executeUpdate();
		}
		sta.close();
		return new Integer(rowsUpdated);
	}

}
