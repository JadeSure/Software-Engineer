import java.io.Serializable;

public abstract class LineItem implements Serializable{

	// default serialVersion id
	private static final long serialVersionUID = 1L;
	
	private Product product;
	private double quantity;
	private double amount;
	
	public LineItem(Product product, double quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void calAmount() {
		if (this instanceof SalesLineItem) {
			amount = ((SalesLineItem)this).getUnitPrice() * quantity;
		} else {
			amount = ((OrderLineItem)this).getUnitCost() * quantity;
		}
	}

	public void updateStock() {
		product.addRecord(this);
	}
	
	public String getDetails() {
		String format = "%-15s%s%n%-15s%s%n";
		String temp = String.format(format, 
				"Product:", (product.getProductID() + " " + product.getProductName()), 
				"Qty:", quantity);
	
		if (this instanceof SalesLineItem) {
			temp +=  String.format(format,
					"Price:", ((SalesLineItem)this).getUnitPrice(),
					"Amount:", amount);
		} else {
			temp +=  String.format(format,
					"Cost:", ((OrderLineItem)this).getUnitCost(),
					"Amount:", amount);
		}
		
		return temp;
	}
	
	public String toString() {
		String temp = product.getProductID() + ":" + quantity;
		
		if (this instanceof SalesLineItem) {
			temp +=  ":" + ((SalesLineItem)this).getUnitPrice() + ":" + amount;
		} else {
			temp +=   ":" + ((OrderLineItem)this).getUnitCost() + ":" + amount;
		}
		
		return temp;
	}


}
