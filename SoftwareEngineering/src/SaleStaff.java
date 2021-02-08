
public class SaleStaff extends User{

	private static final long serialVersionUID = 1L;

	public SaleStaff(String userID, String userName) {
		super(userID, userName);
	}
	
	public String getMenu() {
		String format = "%s%n%-25s%s%n%-25s%s%n%-25s%s%n%-25s%s%n%s%n%s";
		String menu = String.format(format,
				"********** MARKET MENU **********",
				"Remove product:", "1",
				"Cancel sale:", "2",
				"Log out:", "0",
				"Exit Program:", "E", 
				"*********************************",
				"What's now ? ");
		return menu;
	}

}
