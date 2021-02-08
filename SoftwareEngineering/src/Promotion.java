import java.io.Serializable;
import java.util.Date;

public class Promotion implements Serializable{

	// default serialVersion id
	private static final long serialVersionUID = 1L;
	
	private String promotionID;
	private Date startDate, endDate;
	private double bulkQuantity;
	private double bulkDiscount;
	
	public Promotion (String promotionID,Date startDate, Date endDate, double bulkQuantity, double bulkDiscount) {
		this.promotionID = promotionID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.bulkQuantity = bulkQuantity;
		this.bulkDiscount = bulkDiscount;
	}

	public String getPromotionID() {
		return promotionID;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public double getBulkQuantity() {
		return bulkQuantity;
	}
	
	public double getBulkDiscount() {
		return bulkDiscount;
	}
	
	public String getDetails() {
		String format = "%-20s%-20s%-20s%-20s%s%n";
		String temp = String.format(format, promotionID, SupermarketSystem.df.format(startDate), SupermarketSystem.df.format(endDate), bulkQuantity, bulkDiscount);

		return temp;
	}
	

}
