import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Sale implements Serializable{

	// default serialVersion id
	private static final long serialVersionUID = 1L;
	
	private String saleID;
	private Date saleDate;
	private Customer customer;
	private String status; // created, paid, cancelled
	private ArrayList<SalesLineItem> lineItems = new ArrayList<SalesLineItem>();
	private Payment payment;
	private double subtotal;
	private double total;
	private int discount;
	private int rewardPoint;

	// with customer card
	public Sale(String saleID, Date saleDate, Customer customer) {
		this.saleID = saleID;
		this.saleDate = saleDate;
		this.customer = customer;
		
		// default state
		this.status = "created";
		this.lineItems.clear();
		this.subtotal = 0;
		this.total = 0;
		this.discount = 0;
		this.rewardPoint = 0;
		
	}

	public void makeLineItem(Product product, double quantity) {
		lineItems.add(new SalesLineItem(product, quantity));
		calTotal();
	}
	
	
	public void removeLineItem(int lineItemNumber) {
		this.lineItems.remove(lineItemNumber - 1);
		calTotal();
	}

	
	public void calTotal() {
		// calculate sub total
		subtotal = 0;
		for (SalesLineItem i : lineItems) {
			subtotal += i.getAmount();
		}
		
		// calculate discount
		// every 20 points get $5 discount
		
		discount = (customer.getRewardPoint() / 20) * 5;
		
		if (subtotal < discount) {
			discount = ((int)subtotal / 5) * 5;
		}
		
		// calculate total
		total = subtotal - discount;
		
		// calculate points
		// every $10 spent get 1 point
		rewardPoint = ((int)total) / 10; 
	}
	
	public void makePayment() {
		setPayment(new Payment(new Date(), total));

		// if payment amount == total
		// update status
		status = "paid";
		
		// deduct points
		customer.redeemPoints(discount / 5 * 20);
			
		// add points
		customer.collectPoints(rewardPoint);
		
		// for each line item, update the stock(add record to the product object)
		for (SalesLineItem i : lineItems) {
			i.updateStock();
		}
		
	}
	
	public void cancelSale() {
		status = "cancelled";
	}
	
	public String getSaleID() {
		return saleID;
	}
	
	public double getLineItemsSize() {
		return lineItems.size();
	}
	
	public double getTotal() {
		return total;
	}
	
	public double getSubtotal() {
		return subtotal;
	}
	
	public double getDiscount() {
		return discount;
	}
	
	public int getRewardPoint() {
		return rewardPoint;
	}
	
	public String getStatus() {
		return status;
	}
	
	public Date getSaleDate() {
		return saleDate;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public String getDetails() {
		String format = "%-15s%s%n%-15s%s%n%-15s%s%n%-15s%s%n%-15s%s%n%-15s%s%n%-15s%s%n%-15s%s%n";
		String temp = String.format(format, 
				"Sale ID:", saleID, 
				"Sale Date:", saleDate, 
				"Customer:", customer.getUserID(), 
				"Status:", status,
				"Subtotal:", subtotal, 
				"Discount:", discount,
				"Total:", total,
				"Reward Point:", rewardPoint);

		temp += getLineItemsDetails();
		
		return temp;
	}
	
	public String getLineItemsDetails() {
		String temp = "";
		for (SalesLineItem i : lineItems) {
			temp += "---------- Line Items " + (lineItems.indexOf(i)+1) + " ---------\n";
			temp += i.getDetails();
		}
		
		return temp;
	}
	
//	public String toString() {
//		String temp = "";
//		for (SalesLineItem i : lineItems) {
//			temp += "---------- Line Items " + (lineItems.indexOf(i)+1) + " ---------\n";
//			temp += i.getDetails();
//		}
//		
//		return temp;
//	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	
	
	
}
