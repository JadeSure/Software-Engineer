import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.TimeZone;

public class SupermarketSystem implements Serializable {

	// default serialVersion id
	private static final long serialVersionUID = 1L;

	static Scanner scan = new Scanner(System.in);
	static Calendar calendar = new GregorianCalendar();
	static TimeZone timeZone = TimeZone.getTimeZone("Australia/Sydney");
	static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private String output;

	// attributes
	private HashMap<String, Vendor> vendors = new HashMap<String, Vendor>();
	private HashMap<String, User> users = new HashMap<String, User>();
	private HashMap<String, Product> products = new HashMap<String, Product>();
	private HashMap<String, Promotion> promotions = new HashMap<String, Promotion>();

	// transactions
	private HashMap<String, Sale> sales = new HashMap<String, Sale>();
	private HashMap<String, Order> orders = new HashMap<String, Order>();

	// current pointer
	private User currentLogin;
	private Sale currentSale;
	private Order currentOrder;
	private int saleIDCount, orderIDCount, customerIDCount, vendorIDCount, promotionIDCount;

	// constructor
	public SupermarketSystem() {
		this.currentLogin = null;
		this.currentSale = null;
		this.currentOrder = null;
		calendar.setTimeZone(timeZone);
	}

	private boolean login() {
		String helloWord = "Please touch your ID card to log in: ";

		System.out.print(helloWord);
		String loginID = scan.next();

		if (users.containsKey(loginID)) {
			System.out.println("Welcome back, " + users.get(loginID).getUserName() + "!\n");
			currentLogin = users.get(loginID);
			return true;
		} else {
			System.out.println("ID: " + loginID + " is invalid, please seek staff for help.\n");
			return false;
		}
	}

	// display the corresponding menu according to user role and return the selected
	// option
	private String menu() {
		String option = null;

		if (currentLogin instanceof Customer) {
			System.out.print(currentLogin.getMenu());
			option = "C" + scan.next();

		} else if (currentLogin instanceof SaleStaff) {
			System.out.print(currentLogin.getMenu());
			option = "S" + scan.next();

		} else if (currentLogin instanceof WarehouseStaff) {
			System.out.print(currentLogin.getMenu());
			option = "W" + scan.next();

		} else if (currentLogin instanceof Manager) {
			System.out.print(currentLogin.getMenu());
			option = "M" + scan.next();
		}

		return option.toUpperCase();
	}

	public void run() {
		boolean run = true;
		boolean login = false;
		String option = null;
		System.out.println("Welcome to unicorn market!");

		do {
			login = login();

			// if log in success
			while (login) {
				try {
					option = menu();
					switch (option) {
					case "C0":
					case "S0":
					case "W0":
					case "M0":
						System.out.println("See you next time!\n");
						login = false;
						break;
					case "SE":
					case "WE":
					case "ME":
						System.out.println("\n----------- SYSTEM OFF ----------\n");
						login = false;
						run = false;
						break;
					case "C1":
						browseProduct();
						break;
					case "C2":
						searchProduct();
						break;
					case "C3":
						searchPromotion();
						break;
					case "C4":
						// if currentSale is null or current sale is not "Created"
						if (currentSale == null || !currentSale.getStatus().equals("created")) {
							currentSale = new Sale(generateNewSaleID(), new Date(), (Customer) currentLogin);
							System.out.println("Your cart is empty!\n");
						} else {
							System.out.println(currentSale.getDetails());
						}
						break;
					case "C5":
						System.out.println("Please ask staff to help you with this operation.\n");
						break;
					case "C6":
						endSale();
						// auto log out
						login = false;
						break;
					case "S1":
						removeItem();
						break;
					case "S2":
						cancelSale();
						break;
					case "W1":
						createOrder();
						break;
					case "W2":
						removeOrderItem();
						break;
					case "W3":
						cancelOrder();
						break;
					case "W4":
						receiveOrder();
						break;
					case "M1":
						createCustomer();
						break;
					case "M2":
						createEmployee();
						break;
					case "M3":
						createVendor();
						break;
					case "M4":
						createPromotion();
						break;
					case "M5":
						applyPromotion();
						break;
					case "M6":
						createProduct();
						break;
					case "M7":
						overrideUnitPrice();
						break;
					case "M8":
						issueOrder();
						break;
					case "M9":
						printSalesReport();
						break;
					case "M10":
						printOrdersReport();
						break;
					case "M11":
						printCostByVendor();
						break;
					case "M12":
						printInventoryReport();
						break;
					case "M13":
						printRevenueByPostCode();
						break;
					case "M14":
						printRevenueByProduct();
						break;
					case "M15":
						printProductsByQuantity();
						break;
					default:
						System.out.println(option.substring(1) + " is invalid, please try again!\n");
					}
				} catch (InputMismatchException ime) {
					System.err.println("Invalid input format.\n");
					scan.next();
				}
			}

		} while (run);
	}

	// generate a sequential string with a format of S_0000X
	private String generateNewSaleID() {
		saleIDCount = sales.size();
		saleIDCount++;
		return String.format("S_%0" + (6 - String.valueOf(saleIDCount).length()) + "d", saleIDCount);
	}

	// generate a sequential string with a format of O_0000X
	private String generateNewOrderID() {
		orderIDCount = orders.size();
		orderIDCount++;
		return String.format("O_%0" + (6 - String.valueOf(orderIDCount).length()) + "d", orderIDCount);
	}

	// generate a sequential string with a format of C_0000X
	private String generateNewCustomerID() {
		customerIDCount = 0;
		for (User i : users.values()) {
			if (i instanceof Customer) {
				customerIDCount++;
			}
		}
		customerIDCount++;
		return String.format("C_%0" + (6 - String.valueOf(customerIDCount).length()) + "d", customerIDCount);
	}

	// generate a sequential string with a format of V_0000X
	private String generateNewVendorID() {
		vendorIDCount = vendors.size();
		vendorIDCount++;
		return String.format("V_%0" + (6 - String.valueOf(vendorIDCount).length()) + "d", vendorIDCount);
	}

	// generate a sequential string with a format of PR_0000X
	private String generateNewPromotionID() {
		promotionIDCount = promotions.size();
		promotionIDCount++;
		return String.format("PR_%0" + (6 - String.valueOf(promotionIDCount).length()) + "d", promotionIDCount);
	}

	// management------------------
	private void createCustomer() {
		System.out.print("Enter customer name : ");
		String customerName = scan.next();
		System.out.print("Enter post code : ");
		String customerPostCode = scan.next();

		String customerID = generateNewCustomerID();

		users.put(customerID, new Customer(customerID, customerName, customerPostCode));
		System.out.println("Customer: " + customerID + " has been created.\n");
	}

	private void createEmployee() {
		System.out.print("Enter employee ID : ");
		String employeeID = scan.next();
		if (users.containsKey(employeeID)) {
			System.out.println("Employee " + employeeID + " already exist.\n");
			return;
		}
		System.out.print("Enter employee name : ");
		String employeeName = scan.next();

		System.out.print("Enter employee role\n (1)Manager (2)Sale staff (3)Warehouse staff : ");
		int employeeRole = scan.nextInt();

		switch (employeeRole) {
		case 1:
			users.put(employeeID, new Manager(employeeID, employeeName));
			break;
		case 2:
			users.put(employeeID, new SaleStaff(employeeID, employeeName));
			break;
		case 3:
			users.put(employeeID, new WarehouseStaff(employeeID, employeeName));
			break;
		default:
			System.out.println("Employee role is invalid.\n");
		}

		System.out.println("Employee: " + employeeID + " has been created.\n");
	}

	private void createVendor() {
		System.out.print("Enter vendor name : ");
		String vendorName = scan.next();
		String vendorID = generateNewVendorID();

		vendors.put(vendorID, new Vendor(vendorID, vendorName));
		System.out.println("Vendor: " + vendorID + " has been created.\n");
	}

	private void createPromotion() {
		try {
			int day, month, year;
			System.out.print("Enter the satrt date : ");
			String inputStartDate = scan.next();
			day = Integer.parseInt(inputStartDate.substring(0, 2));
			month = Integer.parseInt(inputStartDate.substring(3, 5));
			year = Integer.parseInt(inputStartDate.substring(6));
			calendar.set(year, month - 1, day);
			Date startDate = calendar.getTime();

			System.out.print("Enter the end date : ");
			String inputEndDate = scan.next();
			day = Integer.parseInt(inputEndDate.substring(0, 2));
			month = Integer.parseInt(inputEndDate.substring(3, 5));
			year = Integer.parseInt(inputEndDate.substring(6));
			calendar.set(year, month - 1, day);
			Date endDate = calendar.getTime();

			System.out.print("Enter bulk sales quantity : ");
			double bulkQuantity = scan.nextDouble();
			// if quantity < 1
			if (bulkQuantity <= 0) {
				System.out.println("Bulk sale quantity should be positive value.\n");
				return;
			}

			System.out.print("Enter bulk sales discount% : ");
			double bulkDiscount = scan.nextDouble();
			// if discount percentage > 100
			if (bulkDiscount > 100) {
				System.out.println("Bulk sale discount % should be under 100.\n");
				return;
			}

			String promotionID = generateNewPromotionID();

			promotions.put(promotionID, new Promotion(promotionID, startDate, endDate, bulkQuantity, bulkDiscount));
			System.out.println("Promotion: " + promotionID + " has been created.\n");

			// Catch error when substring the date string
		} catch (StringIndexOutOfBoundsException siobe) {
			System.err.println("Invalid date input format.\n");
			scan.nextLine();

			// Catch error when parse string to number
		} catch (NumberFormatException nfe) {
			System.err.println("Invalid date input format.\n");
			scan.nextLine();
		}
	}

	private void createProduct() {
		System.out.print("Enter product ID : ");
		String productID = scan.next();
		// if the product ID is not unique
		if (products.containsKey(productID)) {
			System.out.println("Product: " + productID + " already exist.\n");
			return;
		}

		System.out.print("Enter product name : ");
		String productName = scan.next();

		System.out.print("Enter unit cost : ");
		double unitCost = scan.nextDouble();
		// if the unit cost < 0
		if (unitCost < 0) {
			System.out.println("Unit cost should be positive value.\n");
			return;
		}

		System.out.print("Enter unit price : ");
		double unitPrice = scan.nextDouble();
		// if the unit price < 0
		if (unitPrice < 0) {
			System.out.println("Unit price should be positive value.\n");
			return;
		}

		System.out.print("Enter reorder point : ");
		double reorderPoint = scan.nextDouble();
		// if the reorder point < 0
		if (reorderPoint < 0) {
			System.out.println("Reorder point should be positive value.\n");
			return;
		}

		System.out.print("Enter reorder quantity : ");
		double reorderQuantity = scan.nextDouble();
		// if the reorder quantity < 0
		if (reorderQuantity < 0) {
			System.out.println("Reorder quantity should be positive value.\n");
			return;
		}

		System.out.print("Enter main supplier : ");
		String mainSupplierID = scan.next();
		// if the vendor ID does not exist
		if (!vendors.containsKey(mainSupplierID)) {
			System.out.println("Vendor: " + mainSupplierID + " does not exist.\n");
			return;
		}

		products.put(productID, new Product(productID, productName, unitCost, unitPrice, reorderPoint, reorderQuantity,
				vendors.get(mainSupplierID)));
		System.out.println("Product: " + productID + " has been created.\n");
		System.out.println(products.get(productID).getDetails());
	}

	// other functions------------------
	private void autoReplenishStock() {
		for (Product i : products.values()) {
			// if the stock level + outstanding stock < reorder point
			if (!i.isSufficient()) {
				// create the order
				Order AutoOrder = new Order((generateNewOrderID() + "_auto"), new Date(), i.getMainSupplier());
				orders.put(AutoOrder.getOrderID(), AutoOrder);
				AutoOrder.makeLineItem(i, i.getReorderQuantity());

				// automatically issue the order
				AutoOrder.issueOrder();
			}
		}
	}

	private void applyPromotion() {
		System.out.print("Enter product ID : ");
		String productID = scan.next();
		// if the product ID does not exist
		if (!products.containsKey(productID)) {
			System.out.println("Product: " + productID + " does not exist.\n");
			return;
		}

		System.out.print("Enter promotion ID : ");
		String promotionID = scan.next();
		// if the promotion ID does not exist
		if (!promotions.containsKey(promotionID)) {
			System.out.println("Promotion: " + promotionID + " does not exist.\n");
			return;
		}

		products.get(productID).addPromotion(promotions.get(promotionID));
		System.out.println("Promotion: " + promotionID + " is now applied to product: " + productID + ".");
		System.out.println(products.get(productID).getPromotions());
	}

	private void overrideUnitPrice() {
		System.out.print("Enter product ID : ");
		String productID = scan.next();
		// if the product ID does not exist
		if (!products.containsKey(productID)) {
			System.out.println("Product " + productID + " does not exist.\n");
			return;
		}

		System.out.println("The current unit price for " + productID + " is " + products.get(productID).getUnitPrice());
		System.out.print("Enter new price ID : ");
		double newUnitPrice = scan.nextDouble();
		// if the price is invalid
		if (newUnitPrice < 0) {
			System.out.println("Unit price should be positive value.\n");
			return;
		}

		products.get(productID).setUnitPrice(newUnitPrice);
		System.out.println("Product: " + productID + " has changed unit price to " + newUnitPrice + ".\n");
	}

	// sales management------------------
	private void addItem() {
		System.out.print("Enter product ID : ");
		String productID = scan.next();
		// if the product ID does not exist
		if (!products.containsKey(productID)) {
			System.out.println("Product: " + productID + " does not exist.\n");
			return;
		}

		System.out.print("Enter product Quantity : ");
		double quantity = scan.nextDouble();
		// if the product ID does not exist
		if (quantity <= 0) {
			System.out.println("Product quantity should be positive value.\n");
			return;
		}

		// if currentSale is null or current sale is not "Created"
		if (currentSale == null || !currentSale.getStatus().equals("created")) {
			currentSale = new Sale(generateNewSaleID(), new Date(), (Customer) currentLogin);
			sales.put(currentSale.getSaleID(), currentSale);
		}

		currentSale.makeLineItem(products.get(productID), quantity);
		System.out.println("Product: " + productID + " has been added to sale: " + currentSale.getSaleID() + ".\n");
	}

	private void removeItem() {
		if (currentSale == null || !currentSale.getStatus().equals("created")) {
			System.out.println("There is no current sale.\n");
			return;
		}

		// print current sale state
		System.out.println(currentSale.getDetails());
		System.out.print("Enter item index to remove: ");
		int lineItemNumber = scan.nextInt();

		// if the lineItem index does not exist
		if (lineItemNumber < 1 || lineItemNumber > currentSale.getLineItemsSize()) {
			System.out.println("Index: " + lineItemNumber + " invalid.\n");
			return;
		}

		currentSale.removeLineItem(lineItemNumber);
		System.out.println("Product has been removed from sale: " + currentSale.getSaleID() + ".\n");
	}

	private void endSale() {
		// if currentSale is null or current sale is not "Created"
		if (currentSale == null || !currentSale.getStatus().equals("created") || currentSale.getLineItemsSize() == 0) {
			System.out.println("Please scan items before ending.");
			return;
		}

		// make payment
		System.out.println(currentSale.getDetails());
		System.out.print("Ready to check out(Y/N)? ");
		char resp = scan.next().toUpperCase().charAt(0);
		if (resp != 'Y') {
			return;
		}

		currentSale.makePayment();
		System.out.println("Thank you for shopping with unicorns, see you next time :)\n");

		// check stock level
		autoReplenishStock();
	}

	private void cancelSale() {
		// if currentSale is null or current sale is not "Created"
		if (currentSale == null || !currentSale.getStatus().equals("created")) {
			System.out.println("Please scan items before cancelling.\n");
			return;
		}

		// cancel the sale
		currentSale.cancelSale();
		System.out.println("Sale: " + currentSale.getSaleID() + " has been cancelled.\n");
	}

	private void browseProduct() {
		System.out.println(String.format("%-20s%-20s%s%n%-20s%-20s%s", "Product ID", "Product name", "Product price",
				"-------------", "-------------", "-------------"));

		for (Product i : products.values()) {
			System.out.println(i.getView());
		}

		addItem();
	}

	private void searchProduct() {
		System.out.print("Enter product ID : ");
		String productID = scan.next();
		// if the product ID does not exist
		if (!products.containsKey(productID)) {
			System.out.println("Product " + productID + " does not exist.");
			return;
		}

		System.out.println(String.format("%-20s%-20s%s%n%-20s%-20s%s", "Product ID", "Product name", "Product price",
				"-------------", "-------------", "-------------"));
		System.out.println(products.get(productID).getView());

		addItem();
	}

	private void searchPromotion() {

		System.out.print("Enter product ID : ");
		String productID = scan.next();
		// if the product ID does not exist
		if (!products.containsKey(productID)) {
			System.out.println("Product " + productID + " does not exist.");
			return;
		}

		System.out.println(products.get(productID).getPromotions() + "\n");
	}

	// order management------------------
	private void createOrder() {
		System.out.print("Enter vendor ID : ");
		String vendorID = scan.next();
		// if the vendor ID does not exist
		if (!vendors.containsKey(vendorID)) {
			System.out.println("Vendor: " + vendorID + " does not exist.\n");
			return;
		}

		boolean run = true;
		while (run) {
			System.out.print("Enter product ID : ");
			String productID = scan.next();
			// if the product ID does not exist
			if (!products.containsKey(productID)) {
				System.out.println("Product: " + productID + " does not exist.\n");
				break;
			}

			System.out.print("Enter product Quantity : ");
			double quantity = scan.nextDouble();
			// if the quantity < 0
			if (quantity <= 0) {
				System.out.println("Product quantity should be positive value.\n");
				break;
			}

			// if currentOrder is null or current order is not "Created"
			if (currentOrder == null || !currentOrder.getStatus().equals("created")) {
				currentOrder = new Order(generateNewOrderID(), new Date(), vendors.get(vendorID));
				orders.put(currentOrder.getOrderID(), currentOrder);
			}

			currentOrder.makeLineItem(products.get(productID), quantity);
			System.out
					.println("Product: " + productID + " has been added to order: " + currentOrder.getOrderID() + ".");
			System.out.print("Any more items(Y/N) ? ");
			char answer = scan.next().toUpperCase().charAt(0);
			run = (answer == 'Y') ? true : false;
		}

		System.out.println("Order: " + currentOrder.getOrderID() + " has been created.\n");
		currentOrder = null;
	}

	private void removeOrderItem() {
		System.out.print("Enter Order ID : ");
		String orderID = scan.next();
		// if the order ID does not exist
		if (!orders.containsKey(orderID)) {
			System.out.println("Order: " + orderID + " does not exist.\n");
			return;
		}

		// print current order state
		System.out.println(orders.get(orderID).getDetails());
		System.out.print("Enter item index to remove : ");
		int lineItemIndex = scan.nextInt();

		// if the lineItem index does not exist
		if (lineItemIndex < 1 || lineItemIndex > orders.get(orderID).getLineItemsSize()) {
			System.out.println("Index " + lineItemIndex + " invalid.\n");
			return;
		}

		orders.get(orderID).removeLineItem(lineItemIndex);
		System.out.println("Product has been removed from order: " + orderID + ".\n");
	}

	private void issueOrder() {
		System.out.print("Enter Order ID : ");
		String orderID = scan.next();
		// if the order ID does not exist
		if (!orders.containsKey(orderID)) {
			System.out.println("Order: " + orderID + " does not exist.\n");
			return;
			// if the order status is not "created"
		} else if (!orders.get(orderID).getStatus().equals("created")) {
			System.out.println("Order: " + orderID + " can not be issued.\n");
			return;
		}

		orders.get(orderID).issueOrder();
		System.out.println("Order: " + orderID + " has been issued.\n");
	}

	private void cancelOrder() {
		System.out.print("Enter Order ID : ");
		String orderID = scan.next();
		// if the order ID does not exist
		if (!orders.containsKey(orderID)) {
			System.out.println("Order: " + orderID + " does not exist.\n");
			return;
			// if the order status is "received"
		} else if (orders.get(orderID).getStatus().equals("received")) {
			System.out.println("Order: " + orderID + " is already received that can not be cancelled.\n");
			return;
		}

		orders.get(orderID).cancelOrder();
		System.out.println("Order: " + orderID + " has been cancelled.\n");
	}

	private void receiveOrder() {
		System.out.print("Enter Order ID : ");
		String orderID = scan.next();
		// if the order ID does not exist
		if (!orders.containsKey(orderID)) {
			System.out.println("Order: " + orderID + " does not exist.\n");
			return;
			// if the order status is not "issued"
		} else if (!orders.get(orderID).getStatus().equals("issued")) {
			System.out.println("Order: " + orderID + " hasn't been issued.\n");
			return;
		}

		orders.get(orderID).receiveOrder(new Date());
		System.out.println("Order: " + orderID + " has been received.\n");

	}

	// reports generate------------------
	private void printSalesReport() {
		try {
			int day, month, year;
			System.out.print("Enter the satrt date : ");
			String inputStartDate = scan.next();
			day = Integer.parseInt(inputStartDate.substring(0, 2));
			month = Integer.parseInt(inputStartDate.substring(3, 5));
			year = Integer.parseInt(inputStartDate.substring(6));
			calendar.set(year, month - 1, day);
			Date startDate = calendar.getTime();

			System.out.print("Enter the end date : ");
			String inputEndDate = scan.next();
			day = Integer.parseInt(inputEndDate.substring(0, 2));
			month = Integer.parseInt(inputEndDate.substring(3, 5));
			year = Integer.parseInt(inputEndDate.substring(6));
			calendar.set(year, month - 1, day);
			Date endDate = calendar.getTime();

			double total = 0;
			output = String.format("%-20s%-20s%-20s%-20s%-20s%n%-20s%-20s%-20s%-20s%-20s%n", "Sale ID",
					"Customer ID", "Date", "Status", "Amount", "-------------", "-------------", "-------------",
					"-------------", "-------------");

			for (Sale i : sales.values()) {
				if (i.getSaleDate().compareTo(startDate) >= 0 && i.getSaleDate().compareTo(endDate) <= 0 && i.getStatus().equals("paid")) {
					output += String.format("%-20s%-20s%-20s%-20s%-20s%n", i.getSaleID(),
							i.getCustomer().getUserID(), df.format(i.getSaleDate()), i.getStatus(), i.getTotal());
						total += i.getTotal();
				}

			}

			output += String.format("%-20s%-20s%-20s%-20s%-20s%n%-20s%-20s%-20s%-20s%-20s%n", "-------------",
					"-------------", "-------------", "-------------", "-------------", "", "", "", "Total", total);
			ObjectIO.writeReportToFile("files/Sales report.txt","sales report", output);

			// Catch error when substring the date string
		} catch (StringIndexOutOfBoundsException siobe) {
			System.err.println("Invalid date input format.\n");
			scan.nextLine();

			// Catch error when parse string to number
		} catch (NumberFormatException nfe) {
			System.err.println("Invalid date input format.\n");
			scan.nextLine();
		}

	}

	private void printProductsByQuantity() {

		HashMap<String, Double> productReport = new HashMap<String, Double>();
		double total = 0;

		for (Product i : products.values()) {
			productReport.put(i.getProductID(), i.calSaleQuantity());
			total += i.calSaleQuantity();
		}

		output = String.format("%-20s%-20s%n%-20s%-20s%n", "Product", "Sales quantity", "-------------", "-------------");

		productReport.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.forEach(i -> output += String.format("%-20s%-20s%n", i.getKey(), i.getValue()));

		output +=String.format("%-20s%-20s%n%-20s%-20s%n", "-------------", "-------------", "Total", total);

		ObjectIO.writeReportToFile("files/Fast moving products.txt","fast moving products", output);
	}

	private void printRevenueByPostCode() {

		HashMap<String, Double> postcodeReport = new HashMap<String, Double>();
		double total = 0;

		for (Sale i : sales.values()) {
			if (i.getStatus().equals("paid")) {
				if (postcodeReport.containsKey(i.getCustomer().getPostcode())) {
					double oldValue = postcodeReport.get(i.getCustomer().getPostcode());
					postcodeReport.replace(i.getCustomer().getPostcode(), (oldValue + i.getTotal()));
				} else {
					postcodeReport.put(i.getCustomer().getPostcode(), i.getTotal());
				}

				total += i.getTotal();
			}
		}

		output = String.format("%-20s%-20s%n%-20s%-20s%n", "Postcode", "Amount", "-------------", "-------------");
		for (String i : postcodeReport.keySet()) {
			output += String.format("%-20s%-20s%n", i, postcodeReport.get(i));
		}

		output +=String.format("%-20s%-20s%n%-20s%-20s%n", "-------------", "-------------", "Total", total);
		ObjectIO.writeReportToFile("files/Revenue by postcode.txt","revenue by postcode", output);

	}

	private void printRevenueByProduct() {
		HashMap<String, Double> productReport = new HashMap<String, Double>();
		double total = 0;

		for (Product i : products.values()) {
			productReport.put(i.getProductID(), i.calRevenue());
			total += i.calRevenue();
		}

		output = String.format("%-20s%-20s%n%-20s%-20s%n", "Product", "Amount", "-------------", "-------------");

		productReport.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).forEach(i -> {
			output += String.format("%-20s%-20s%n", i.getKey(), i.getValue());
		});

		output += String.format("%-20s%-20s%n%-20s%-20s%n", "-------------", "-------------", "Total", total);

		ObjectIO.writeReportToFile("files/Revenue by product.txt","revenue by product", output);

	}

	private void printOrdersReport() {
		try {
			int day, month, year;
			System.out.print("Enter the satrt date : ");
			String inputStartDate = scan.next();
			day = Integer.parseInt(inputStartDate.substring(0, 2));
			month = Integer.parseInt(inputStartDate.substring(3, 5));
			year = Integer.parseInt(inputStartDate.substring(6));
			calendar.set(year, month - 1, day);
			Date startDate = calendar.getTime();

			System.out.print("Enter the end date : ");
			String inputEndDate = scan.next();
			day = Integer.parseInt(inputEndDate.substring(0, 2));
			month = Integer.parseInt(inputEndDate.substring(3, 5));
			year = Integer.parseInt(inputEndDate.substring(6));
			calendar.set(year, month - 1, day);
			Date endDate = calendar.getTime();

			double total = 0;
			output = String.format("%-20s%-20s%-20s%-20s%-20s%n%-20s%-20s%-20s%-20s%-20s%n", "Order ID",
					"Vendor ID", "Date", "Status", "Amount", "-------------", "-------------", "-------------",
					"-------------", "-------------");

			for (Order i : orders.values()) {
				if (i.getOrderDate().compareTo(startDate) >= 0 && i.getOrderDate().compareTo(endDate) <= 0) {
					output += String.format("%-20s%-20s%-20s%-20s%-20s%n", i.getOrderID(),
							i.getVendor().getVendorID(), df.format(i.getOrderDate()), i.getStatus(), i.getTotal());
					total += i.getTotal();
				}

			}

			output += String.format("%-20s%-20s%-20s%-20s%-20s%n%-20s%-20s%-20s%-20s%-20s%n", "-------------",
					"-------------", "-------------", "-------------", "-------------", "", "", "", "Total", total);
			
			ObjectIO.writeReportToFile("files/Order report.txt","order report", output);

			// Catch error when substring the date string
		} catch (StringIndexOutOfBoundsException siobe) {
			System.err.println("Invalid date input format.\n");
			scan.nextLine();

			// Catch error when parse string to number
		} catch (NumberFormatException nfe) {
			System.err.println("Invalid date input format.\n");
			scan.nextLine();
		}

	}

	private void printCostByVendor() {
		HashMap<String, Double> vendorReport = new HashMap<String, Double>();
		double total = 0;

		for (Order i : orders.values()) {
			if (i.getStatus().equals("received")) {
				if (vendorReport.containsKey(i.getVendor().getVendorID())) {
					double oldValue = vendorReport.get(i.getVendor().getVendorID());
					vendorReport.replace(i.getVendor().getVendorID(), (oldValue + i.getTotal()));
				} else {
					vendorReport.put(i.getVendor().getVendorID(), i.getTotal());
				}

				total += i.getTotal();
			}
		}

		output = String.format("%-20s%-20s%n%-20s%-20s%n", "Vendor ID", "Order Amount", "-------------", "-------------");
		for (String i : vendorReport.keySet()) {
			output += String.format("%-20s%-20s%n", i, vendorReport.get(i));
		}

		output += String.format("%-20s%-20s%n%-20s%-20s%n", "-------------", "-------------", "Total", total);
		ObjectIO.writeReportToFile("files/Supply report.txt","supply report", output);
	}

	private void printInventoryReport() {
		double stockTotal = 0;
		double outstandingStockTotal = 0;
		String format = "%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%n%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%n";
		String output = String.format(format, "Product ID:", "Product Name:", "Main supplier:", "Unit Cost:",
				"Unit Price:", "Reorder Point:", "Reorder Quantity:", "Stock Level:", "Outstanding Stock:",
				"-------------","-------------","-------------","-------------","-------------","-------------","-------------","-------------","-------------");
		
		for (Product i : products.values()) {
			output += i.toString();
			stockTotal += i.getStockLevel();
			outstandingStockTotal +=i.getOutstandingStock();
		}
		
		output += String.format(format, "-------------","-------------","-------------","-------------","-------------","-------------","-------------","-------------","-------------",
				"","","","","","","",stockTotal,outstandingStockTotal);
		
		ObjectIO.writeReportToFile("files/Inventory report.txt", "inventory report", output);
	}

}
