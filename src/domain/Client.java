package domain;


import java.util.ArrayList;

public class Client {

	
	private long clientId;
	private long clientProductId;
	private String firstName;
	private String lastName;
	private String email;
	private String birthdate;
	private String sex;
	private String identityCard;
	private ClientAddress address;
	private ClientCreditCard card;
	private Product product;
	private String type;
	private String txtMovPhone;
	private String txtHabPhone;
	private String txtOficPhone;
	private String txtOtherPhone;
	private ArrayList<PhoneType> phones;
	private int isHolder;
	
	/**
	 * 
	 * @param id
	 */
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getClientId() {
		return clientId;
	}

	
	/**
	 * 
	 * @param id
	 */
	public void setClientProductId(long clientProductId) {
		this.clientProductId = clientProductId;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getClientProductId() {
		return clientProductId;
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
	

	
	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		return sex;
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
	
	
	public void setCard(ClientCreditCard card) {
		this.card = card;
	}

	public ClientCreditCard getCard() {
		return card;
	}
	
	
	public void setProduct(Product product){
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}
	
	public void setBirthdate(String birthdate){
		this.birthdate = birthdate;
	}

	public String getBirthdate(){
		return birthdate;
		
	}
	
	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
		
	}
	
	public void setPhones(ArrayList<String> phones, ArrayList<PhoneType> phoneType){
		for( int i = 0; i<phones.size(); i++){
			String [] phone = phones.get(i).split("-");
			String type = phone[0];
			String number = phone[1];
			for (int j = 0; j < phoneType.size(); j++){
				if (type.equals("1")){
					txtHabPhone = number;
				}else if (type.equals("2")){
					txtOficPhone = number;
				}else if (type.equals("3")){
					txtMovPhone = number;
				}else{
					txtOtherPhone = number;
				}
			}
		}
	}

	public void setPhones(ArrayList<PhoneType> phones){
		this.phones = phones;
	}

	
	public ArrayList<PhoneType> getPhones(){
		return phones;
	}

	public String getTxtHabPhone(){
		return txtHabPhone;
	}
	
	public String getTxtOficPhone(){
		return txtOficPhone;
	}

	public String getTxtMovPhone(){
		return txtMovPhone;
	}
	
	public String getTxtOtherPhone(){
		return txtOtherPhone;
	}

	public void setIsHolder(int isHolder) {
		this.isHolder = isHolder;
	}

	public int getIsHolder() {
		return isHolder;
	}
	


}
