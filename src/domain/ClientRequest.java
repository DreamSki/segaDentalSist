package domain;


public class ClientRequest {

	private long id;
	private long clientId;	
	private String name;
	private int statusUserProduct;
	private String expirationDate;
	private Product product;
	private String typeName;
	private int type;
	private int justificationId;
	private String justification;
	
	
	
	
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


	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}


	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setProduct(Product product){
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}
	
	
	public void setStatusUserProduct(int status){
		this.statusUserProduct = status;
	}

	public int getStatusUserProduct() {
		return statusUserProduct;
	}

	public void setTypeName(){
		if (statusUserProduct == 5 || statusUserProduct == 1 ){
			this.typeName = "Solicitud";
			this.type = 1;
		}
		else{ 
			this.typeName = "Renovación";
			this.type = 2;
		}
	}

	
	public String getTypeName(){
		return typeName;
	}
	
	public int getType(){
		return type;
	}
	
	public void setJustificationId(int justificationId){
		this.justificationId = justificationId;
	}
	
	public int getJustificationId(){
		return justificationId;
	}
	
	public void setJustification(String justification){
		this.justification = justification;
	}
	
	public String getJustification(){
		return justification;
	}
	
	
}
