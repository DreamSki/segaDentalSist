package domain;

import java.sql.Date;

public class Payment {
	
	private int id;
	private int clientProductId;
	private int creditCardId;
	private Date date;
	private String amount;
	private String voucher;
	private int checkerId;
	private String type;
	
	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param name
	 */
	public void setClientProductId(int clientProductId) {
		this.clientProductId = clientProductId;
	}

	/**
	 * 
	 * @return
	 */
	public int getClientProductId() {
		return clientProductId;
	}
	
	public void setCreditCardId(int creditCardId){
		this.creditCardId = creditCardId;
	}

	public int getCreditCardId(){
		return creditCardId;
	}

	
	public void setDate(Date date){
		this.date = date;
	}

	public Date getDate(){
		return date;
	}
	
	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}
	
	public void setVoucher(String voucher){
		this.voucher = voucher;
	}

	public String getVoucher(){
		return voucher;
	}
	
	
	public void setCheckerId(int checkerId){
		this.checkerId = checkerId;
	}

	public int getCheckerId(){
		return checkerId;
	}
	
	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}
	
	
}
