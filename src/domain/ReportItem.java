package domain;

import java.sql.Date;

public class ReportItem {
	
	private String identityCard;
	private String lastName;
	private String firstName;
	private String contract;
	private Date affiliationDate;
	private String product;
	private Double price;
	private String seller;
	private String room;
	private String turn;
	private String address;
	private String referencePoint;
	private String city;
	private String state;
	private String homeNumber;
	private String officeNumber;
	private String mobileNumber;
	private String faxNumber;
	private String email;
	private String cardType;
	private String voucher;
	private String bank;
	private String amount;
	
	
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	
	public String getIdentityCard() {
		return identityCard;
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
	
	public void setContract(String contract) {
		this.contract = contract;
	}
	
	public String getContract() {
		return contract;
	}
	
	public void setAffiliationDate(Date affiliationDate) {
		this.affiliationDate = affiliationDate;
	}
	
	public Date getAffiliationDate() {
		return affiliationDate;
	}
	
	public void setProduct(String product) {
		this.product = product;
	}
	
	public String getProduct() {
		return product;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setSeller(String firstNameSeller, String lastNameSeller) {
		this.seller = firstNameSeller + " " + lastNameSeller;
	}
	
	public String getSeller() {
		return seller;
	}
	
	public void setRoom(String room) {
		this.room = room;
	}
	
	public String getRoom() {
		return room;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCity() {
		return city;
	}
	
		public void setAddress(String street, String propertyType, String propertyName, String tower, String floor, String apartment, 
			String urbanization, String municipality, String postalCode) {
		
		if(street != null){
			this.address = "Calle " + street;
		}
		
		if(propertyType != null && propertyName != null){
			this.address += ", " + propertyType + " " + propertyName;
		}
		
		if(tower != null){
			this.address += ", torre " + tower;
		}
		
		if(floor != null){
			this.address += ", piso " + floor;
		}
		
		if(apartment != null){
			this.address += ", apto " + apartment;
		}
		
		if(urbanization != null){
			this.address += ", Urbanización " + urbanization;
		}
		
		if(municipality != null){
			this.address += ". Municipio " + municipality;
		}
		
		if(postalCode != null){
			this.address += ". Código Postal " + postalCode;
		}
		
		this.address += ".";
	}
	
	
	
	public String getAddress() {
		return address;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return state;
	}
	
	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}
	
	public String getHomeNumber() {
		return homeNumber;
	}
	
	public void setOfficeNumber(String officeNumber) {
		this.officeNumber = officeNumber;
	}
	
	public String getOfficeNumber() {
		return officeNumber;
	}
	
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	
	public String getFaxNumber() {
		return faxNumber;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setTurn(String turn) {
		this.turn = turn;
	}
	
	public String getTurn() {
		return turn;
	}
	
	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}
	
	public String getVoucher() {
		return voucher;
	}
	
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	public String getBank() {
		return bank;
	}
	
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	public String getCardType() {
		return cardType;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setReferencePoint(String referencePoint) {
		this.referencePoint = referencePoint;
	}

	public String getReferencePoint() {
		return referencePoint;
	}
}


