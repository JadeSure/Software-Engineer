
public class OrderLineItem extends LineItem{
	
	private static final long serialVersionUID = 1L;
	private double unitCost;

	public OrderLineItem(Product product, double quantity) {
		super(product, quantity);
		this.unitCost = product.getUnitCost() ;
		super.calAmount();
	}
	
	public double getUnitCost() {
		return unitCost;
	}

}
