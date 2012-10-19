package domain;


public class Client {

	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String identityCard;
	private ClientAddress address;
	private ClientCard card;
	private Product product;
	
	
	/**
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getId() {
		return id;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	
	
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getIdentityCard() {
		return identityCard;
	}
	
	public void setAddress(ClientAddress address) {
		this.address = address;
	}

	public ClientAddress getAddress() {
		return address;
	}
	
	
	public void setCard(ClientCard card) {
		this.card = card;
	}

	public ClientCard getCard() {
		return card;
	}
	
	
	public void setProduct(Product product){
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}
	



}
