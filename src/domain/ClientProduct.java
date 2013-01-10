package domain;

public class ClientProduct {
	
	private long clientId;
	private long productId;
	private String affiliationDate;
	private long sellerId;
	private int statusId;
	private String amount;
	private String contract;
	private long id;
	
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	
	public long getClientId() {
		return clientId;
	}
	
	public void setAffiliationDate(String affiliationDate) {
		this.affiliationDate = affiliationDate;
	}
	
	public String getAffiliationDate() {
		return affiliationDate;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getProductId() {
		return productId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmount() {
		return amount;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getContract() {
		return contract;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	

}
