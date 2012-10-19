package domain;

public class UserProduct {

	private int userId;
	private int productId;
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public int getProductId() {
		return productId;
	}
	
	@Override
	 public boolean equals(Object o) {		
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof UserProduct))
			return false;
		UserProduct up = (UserProduct) o;
		if (userId != up.userId)
		    return false;
		if (productId != up.productId)
		    return false;
		return true;
	}
	
	
}
