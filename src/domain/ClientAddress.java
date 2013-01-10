package domain;


public class ClientAddress {

	private int addressTypeId ;
	private String addressTypeName;
	private String state;
	private String city;
	private String municipality;
	private String urbanization;
	private String street;
	private int property_type_id;
	private String property_type_name;
	private String propertyName;
	private String tower;
	private String floor;
	private String apartment;
	private String direction;
	private String referencePoint;
	private String postalCode;
	private int clientId;
	
	/**
	 * 
	 * @param id
	 */
	public void setAddressTypeId(int id) {
		this.addressTypeId = id;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getAddressTypeId() {
		return addressTypeId;
	}


	public void setAddressTypeName(String addressTypeName) {
		this.addressTypeName = addressTypeName;
	}

	public String getAddressTypeName() {
		return addressTypeName;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	
	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	
	
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public String getMunicipality() {
		return municipality;
	}
	
	public void setUrbanization(String urbanization) {
		this.urbanization = urbanization;
	}

	public String getUrbanization() {
		return urbanization;
	}
	
	
	public void setPropertyTypeId(int id) {
		this.property_type_id = id;
	}

	public int getPropertyTypeId() {
		return property_type_id;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet() {
		return street;
	}

	
	public void setPropertyTypeName(String name) {
		this.property_type_name = name;
	}

	public String getPropertyTypeName() {
		return property_type_name;
	}
	
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyName() {
		return propertyName;
	}
	
	
	public void setTower(String name) {
		this.tower = name;
	}

	public String getTower() {
		return tower;
	}
	
	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getFloor() {
		return floor;
	}
	
	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public String getApartment() {
		return apartment;
	}

	public String getDirection(){
		
		if (!property_type_name.equals("Edificio")){
			
			this.direction = "Urbanización " + urbanization + ", calle " + street + ", " + property_type_name + 
				" " + propertyName + ". ";
			
			if(referencePoint!=null && !referencePoint.equalsIgnoreCase("")){
				
				this.direction+="Punto Referencia: "+referencePoint+". ";
			}
			
			this.direction+="Municipio " + municipality + ", ciudad " + city + ", estado " + state + ". ";
			
			if(postalCode!=null && !postalCode.equalsIgnoreCase("")){
				
				this.direction+="Código Postal "+postalCode+". ";
			}
		}else{
			this.direction = "Urbanización " + urbanization + ", Calle " + street + ", " + property_type_name + 
				" " + propertyName  + ".Piso " + floor + ", apartamento " + apartment +". ";
			
			if(referencePoint!=null && !referencePoint.equalsIgnoreCase("")){
				
				this.direction+="Punto Referencia: "+referencePoint+". ";
			}

			this.direction+="Municipio " + municipality + ", ciudad " + city + ", estado " + state + ". ";
			
			if(postalCode!=null && !postalCode.equalsIgnoreCase("")){
				
				this.direction+="Código Postal "+postalCode+". ";
			}
		}
		return direction;
	}

	public void setReferencePoint(String referencePoint) {
		this.referencePoint = referencePoint;
	}

	public String getReferencePoint() {
		return referencePoint;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getClientId() {
		return clientId;
	}
	


}
