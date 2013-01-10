package domain;

public class PhoneType {
	
	private int id;
	private String name;
	private String number;
	private int clientId;
	
	
	public PhoneType(){
	}
	
	public PhoneType(int id, String number, int clientId){
		this.id = id;
		this.number = number;
		this.clientId = clientId;
	}
	
	
	public PhoneType(int id, String name, String number){
		this.id = id;
		this.name = name;
		this.number = number;
	}
	
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
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	public void setNumber(String number){
		this.number = number;
	}
	
	public String getNumber(){
		return number;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getClientId() {
		return clientId;
	}
}
