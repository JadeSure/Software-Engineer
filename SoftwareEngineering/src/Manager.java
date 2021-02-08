
public class Manager extends User{

	private static final long serialVersionUID = 1L;

	public Manager(String userID, String userName) {
		super(userID, userName);
	}
	
	@Override
	public String getMenu() {
		String format = "%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%s%n%s";
		String menu = String.format(format,
				"********** MARKET MENU **********",
				"Create customer:", "1", 
				"Create employee:", "2", 
				"Create vendor:", "3", 
				"Create promotion:", "4",
				"Apply promotion:", "5",
				"Create product:", "6", 
				"Change product price:", "7",
				"Issue order:", "8",
				"# Sales report:", "9", 
				"# Orders report:", "10", 
				"# Supply report:", "11", 
				"# Inventory report:", "12", 
				"# Revenue by postcode:", "13",
				"# Revenue by product:", "14", 
				"# Fast moving products:", "15", 
				"Log out:", "0", 
				"Exit Program:", "E", 
				"*********************************"
				,"What's now ? ");
		return menu;
	}

}
