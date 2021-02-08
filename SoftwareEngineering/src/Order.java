import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable{

	// default serialVersion id
	private static final long serialVersionUID = 1L;

	private String orderID;
	private Date orderDate;
	private Vendor vendor;
	private String status; // created , issued, received, cancelled
	public ArrayList<OrderLineItem> lineItems = new ArrayList<OrderLineItem>();
	private double total;
	private Date receivedDate;

	public Order(String orderID, Date orderDate, Vendor vendor) {
		this.orderID = orderID;
		this.orderDate = orderDate;
		this.vendor = vendor;

		// default state
		this.status = "created";
		this.lineItems.clear();
		this.total = 0;
		this.receivedDate = null;
	}

	public String getOrderID() {
		return orderID;
	}

	public double getTotal() {
		return total;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}

	public void makeLineItem(Product product, double quantity) {
		lineItems.add(new OrderLineItem(product, quantity));
		calTotal();
	}

	public void removeLineItem(int lineItemNumber) {
		lineItems.remove(lineItemNumber - 1);
		calTotal();
	}

	public void calTotal() {
		total = 0;
		for (OrderLineItem i : lineItems) {
			total += i.getAmount();
		}
	}

	public void issueOrder() {

		status = "issued";

		for (OrderLineItem i : lineItems) {
			i.getProduct().updateOutstandingStock(i.getQuantity());
		}

	}

	// receive by order
	public void receiveOrder(Date receivedDate) {

		this.receivedDate = receivedDate;

		this.status = "received";

		// for each line item, update the stock(add record to the product object)
		for (OrderLineItem i : lineItems) {
			i.updateStock();
			i.getProduct().updateOutstandingStock(i.getQuantity() * -1);
		}

	}

	public void cancelOrder() {
		status = "cancelled";
	}

	public String getStatus() {
		return status;
	}
	
	public Vendor getVendor() {
		return vendor;
	}

	public double getLineItemsSize() {
		return lineItems.size();
	}

	public String getDetails() {
		String format = "%-15s%s%n%-15s%s%n%-15s%s%n%-15s%s%n%-15s%s%n%-15s%s%n%s%n";
		String temp = String.format(format, 
				"Order ID:", orderID, 
				"Order Date:", orderDate, 
				"Vendoe:", vendor.getVendorID(),
				"Status:", status, 
				"Total:", total, 
				"Received Date:", receivedDate,
				"----------------LineItems---------------");

		for (OrderLineItem i : lineItems) {
			temp += i.getDetails();;
			temp += "---------------------------------------\n";
		}
		
		return temp;
	}
}
