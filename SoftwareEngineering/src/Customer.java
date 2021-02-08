import java.io.Serializable;

public class Customer extends User implements Serializable{

	// default serialVersion id
	private static final long serialVersionUID = 1L;
	
	// attributes
	private String postCode;
	private int rewardPoint;

	// constructor
	public Customer(String userID, String userName, String postCode) {
		super(userID, userName);
		this.postCode = postCode;
		
		// default state
		this.rewardPoint = 0;
	}

	public String getPostcode() {
		return postCode;
	}
	
	public int getRewardPoint() {
		return rewardPoint;
	}

	public void collectPoints(int points) {
		this.rewardPoint += points;
	}

	public void redeemPoints(int points) {
		this.rewardPoint -= points;
	}

	public String getDetails() {
		String format = "%-15s%s%n%-15s%s%n%-15s%s%n%-15s%s%n%S";
		String temp = String.format(format, 
				"Customer ID:", super.getUserID(),
				"Customer Name:", super.getUserName(),
				"Post Code", postCode,
				"Reward Point:", rewardPoint,
				"---------------------------------------");
		return temp;
	}

	@Override
	public String getMenu() {
		String format = "%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%s%n%s";
		String menu = String.format(format,
				"********** MARKET MENU **********",
				"Browse product list:", "1",
				"Search by product ID:", "2", 
				"Search bulk sales:", "3",
				"View my cart:", "4", 
				"Remove product:", "5", 
				"Check out:", "6", 
				"Log out:", "0",
				"*********************************"
				,"What's now ? ");
		return menu;
	}

}
