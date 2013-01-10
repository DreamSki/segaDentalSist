package domain;

public class ClientBeneficiary {

	private long clientProduct;
	private String identityCard;
	private String firstName;
	private String lastName;
	
	public void setClientProduct(long clientProduct) {
		this.clientProduct = clientProduct;
	}
	
	public long getClientProduct() {
		return clientProduct;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getIdentityCard() {
		return identityCard;
	}	
}
