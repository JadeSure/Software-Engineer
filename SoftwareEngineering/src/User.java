import java.io.Serializable;

public abstract class User implements Serializable {

	// default serialVersion id
	private static final long serialVersionUID = 1L;

	private String userID;
	private String userName;

	public User(String userID, String userName) {
			this.userID = userID;
			this.userName = userName;
		}
	
	public String getUserID() {
		return userID;
	}


	public String getUserName() {
		return userName;
	}
	
	public abstract String getMenu();
	
	public String toString() {
		String temp = userID + ":" + userName;
		
		if (this instanceof Customer) {
			temp += ":" + ((Customer)this).getPostcode() + ":" + ((Customer)this).getRewardPoint(); 
		}
		
		return temp;
	}

}
