import java.io.Serializable;

public class Vendor implements Serializable{
	
	// default serialVersion id
	private static final long serialVersionUID = 1L;
	
	// attributes
	private String vendorID;
	private String vendorName;
	
	// constructor
	public Vendor(String vendorID, String vendorName) {
		this.vendorID = vendorID;
		this.vendorName = vendorName;
	}

	public String getVendorID() {
		return vendorID;
	}
	
	public String getVendorName() {
		return vendorName;
	}
	
	public String getDetails() {
		String format = "%-15s%s%n%-15s%s%n%s";
		String temp = String.format(format, 
				"Vendor ID:", vendorID,
				"Vendor Name:", vendorName,
				"---------------------------------------");
		return temp;
	}
}
