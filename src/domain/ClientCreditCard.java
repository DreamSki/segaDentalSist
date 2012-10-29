package domain;

import java.sql.Date;

public class ClientCreditCard {

	private int id;
	private long cardNumber;
	private String cardType;
	private int cvc;
	private String bank;
	private Date expirationDate;
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	
	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public long getCardNumber() {
		return cardNumber;
	}
	
	
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardType() {
		return cardType;
	}
	
	
	public void setCVC(int cvc){
		this.cvc = cvc;
	}

	public int getCVC() {
		return cvc;
	}
	
	
	public void setBank(String bank){
		this.bank = bank;
	}

	public String getBank() {
		return bank;
	}
	
	
	public void setExpirationDate(Date date){
		this.expirationDate = date;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

}
