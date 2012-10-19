package domain;

public class User {

	private int id;
	private String name;
	private String firstName;
	private String lastName;
	private String identityCard;
	private String username;
	private String password;
	private int roleId;
	private String roleName;
	private Integer roomId;
	private String roomName; 
	private String productName;
	
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
	
	/**
	 * 
	 * @param username
	 */
	public void setUserName(String username) {
		this.username = username;
	}

	/**
	 * 
	 * @return
	 */
	public String getUserName() {
		return username;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param roleId
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	/**
	 * 
	 * @return
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * 
	 * @param roleName
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 
	 * @return
	 */
	public String getRoleName() {
		return roleName;
	}
	
	
	/**
	 * 
	 * @param roomId
	 */
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getRoomId() {
		return roomId;
	}
	
	/**
	 * 
	 * @param roomId
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/**
	 * 
	 * @return
	 */
	public String getRoomName() {
		return roomName;
	}
	
	/**
	 * 
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * 
	 * @return
	 */
	public String getProductName() {
		return productName;
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

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getIdentityCard() {
		return identityCard;
	}
	
}
