import java.io.Serializable;
import java.util.Date;

public class Payment implements Serializable{

	// default serialVersion id
	private static final long serialVersionUID = 1L;
	
	private Date paymentDate;
	private double amount;
	
	public Payment(Date paymentDate, double amount) {
		this.setPaymentDate(paymentDate);
		this.setAmount(amount);
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
