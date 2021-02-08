
public class SalesLineItem extends LineItem {

	private static final long serialVersionUID = 1L;
	
	private double unitPrice;
	
	public SalesLineItem(Product product, double quantity) {
		super(product, quantity);
		
		this.unitPrice = product.getUnitPrice() 
				* (100 - product.calBestPromotion(quantity)) / 100;
		
		super.calAmount();
	}
	
	public double getUnitPrice() {
		return unitPrice;
	}

}
