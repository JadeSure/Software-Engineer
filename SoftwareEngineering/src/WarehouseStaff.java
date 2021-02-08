

public class WarehouseStaff extends User{

	private static final long serialVersionUID = 1L;

	public WarehouseStaff(String userID, String userName) {
		super(userID, userName);
	}
	
	@Override
	public String getMenu() {
		String format = "%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%s%n%s";
		String menu = String.format(format,
				"********** MARKET MENU **********",
				"Create new order:", "1",
				"Remove item from order:", "2", 
				"Cancel order:", "3", 
				"Receive order:", "4", 
				"Log out:", "0",
				"Exit Program:", "E", 
				"*********************************"
				,"What's now ? ");
		return menu;
	}

}
