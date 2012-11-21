package domain;

public class Product {
	
	private int id;
	private String name;
	private int status;
	private String price;
	private String description;
	private String scriptStep2;
	private String scriptStep3;
	
	
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
	
	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	
	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}
	
	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setScriptStep2(String scriptStep2) {
		this.scriptStep2 = scriptStep2;
	}

	public String getScriptStep2() {
		return scriptStep2;
	}

	public void setScriptStep3(String scriptStep3) {
		this.scriptStep3 = scriptStep3;
	}

	public String getScriptStep3() {
		return scriptStep3;
	}
	
	
}
