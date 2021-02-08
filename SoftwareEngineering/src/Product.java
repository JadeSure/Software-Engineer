import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Product  implements Serializable {
	// default serialVersion id
	private static final long serialVersionUID = 1L;
	
	// attributes
	private String productID;
	private String productName;
	private double unitCost;
	private double unitPrice;
	private double reorderPoint;
	private double reorderQuantity;
	private Vendor mainSupplier;
	private double stockLevel;
	private double outstandingStock;
	private ArrayList<LineItem> stockRecords = new ArrayList<LineItem>();
	private ArrayList<Promotion> promotions = new ArrayList<Promotion>();

	// constructor
	public Product(String productID, String productName, double unitCost, double unitPrice, double reorderPoint,
			double reorderQuantity, Vendor mainSupplier) {
		this.productID = productID;
		this.productName = productName;
		this.unitCost = unitCost;
		this.unitPrice = unitPrice;
		this.reorderPoint = reorderPoint;
		this.reorderQuantity = reorderQuantity;
		this.mainSupplier = mainSupplier;

		// default state
		this.stockLevel = 0;
		this.outstandingStock = 0;
		this.stockRecords.clear();
		this.promotions.clear();
	}
	
	public void addPromotion(Promotion promotion) {
		promotions.add(promotion);
	}
	
	public double calBestPromotion(double quantity) {
		Date currentDate = new Date();
		double bestDiscount = 0;

		for (Promotion i : promotions) {
			if (currentDate.compareTo(i.getStartDate()) >= 0 
					&& currentDate.compareTo(i.getEndDate()) < 0
					&& quantity >= i.getBulkQuantity()) {
				if (i.getBulkDiscount() > bestDiscount) {
					bestDiscount = i.getBulkDiscount();
				}
			}
		}
		return bestDiscount;
	}
	

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setReorderPoint(double reorderPoint) {
		this.reorderPoint = reorderPoint;
	}

	public void setReorderQuantity(double reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}

	public void updateOutstandingStock(double quantity) {
		this.outstandingStock += quantity;
	}
	
	// will be called when a sale/order change status to "finished"
	// loop the line items[] in the sale/order
	// add the record to records[]
	// after that, update the product stock level
	public void addRecord(LineItem newLineItem) {
		stockRecords.add(newLineItem);
		calStockLevel();
	}

	private void calStockLevel() {
		stockLevel = 0;

		for (LineItem i : stockRecords) {
			if (i instanceof SalesLineItem) {
				stockLevel -= i.getQuantity();
			} else {
				stockLevel += i.getQuantity();
			}
		}
	}
	
	public double calRevenue() {
		double revenue = 0;

		for (LineItem i : stockRecords) {
			if (i instanceof SalesLineItem) {
				revenue += i.getAmount();
			}
		}
		
		return revenue;
	}
	
	public double calSaleQuantity() {
		double quantity = 0;

		for (LineItem i : stockRecords) {
			if (i instanceof SalesLineItem) {
				quantity += i.getQuantity();
			}
		}
		
		return quantity;
	}

	public boolean isSufficient() {
		// current stock + records where status is create ==> estimated current stock
		// level
		if ((stockLevel + outstandingStock) <= reorderPoint) {
			return false;
		} else {
			return true;
		}
	}

	public String getProductID() {
		return productID;
	}

	public double getUnitCost() {
		return unitCost;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public double getReorderQuantity() {
		return reorderQuantity;
	}

	public double getStockLevel() {
		return stockLevel;
	}

	public double getOutstandingStock() {
		return outstandingStock;
	}

	public String getProductName() {
		return productName;
	}
	
	public Vendor getMainSupplier() {
		return mainSupplier;
	}
	

	public String getDetails() {
		String format = "%-20s%s%n%-20s%s%n%-20s%s%n%-20s%s%n%-20s%s%n%-20s%s%n%-20s%s%n%-20s%s%n%-20s%s%n%s";
		String temp = String.format(format, 
				"Product ID:", productID, 
				"Product Name:", productName,
				"Main supplier:", mainSupplier.getVendorID(),
				"Unit Cost:", unitCost,
				"Unit Price:", unitPrice, 
				"Reorder Point:", reorderPoint, 
				"Reorder Quantity:", reorderQuantity,
				"Stock Level:", stockLevel, 
				"Outstanding Stock:", outstandingStock,
				"---------------------------------------");
		
//		for (LineItem i : stockRecords) {
//			temp += i.toString();
//			temp += "---------------------------------------\n";
//		}

		return temp;
	}
	
	public String toString() {
		String format = "%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%n";
		String temp = String.format(format, productID, productName, mainSupplier.getVendorID(), unitCost, unitPrice, reorderPoint, reorderQuantity, stockLevel, outstandingStock);
		return temp;
	}
	
	public String getView() {
		String temp = String.format("%-20s%-20s%s", productID, productName, unitPrice);
		return temp;
	}
	
	public String getPromotions() {
		Date currentDate = new Date();
		
		String temp = String.format("%-20s%-20s%-20s%-20s%s%n%-20s%-20s%-20s%-20s%s%n",
				"Promotion ID" , "Start date", "End date", "Bulk quantity", "Bulk discount%",
				"-------------", "-------------", "-------------", "-------------", "-------------");

		if (promotions.size() == 0) {
			temp += "(none)";
		} else {
			for (Promotion i : promotions) {
				if (currentDate.compareTo(i.getStartDate()) >= 0 
						&& currentDate.compareTo(i.getEndDate()) < 0) {
					temp += i.getDetails();
					}
				}
			}
		
		return temp;
	}

}
