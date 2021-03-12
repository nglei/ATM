import java.util.Date;

public class Transaction {
	
	private double amount;
	
	private Date timestamp;
	
	private String memo;
	
	private Account inAccount;
	
	public Transaction(double amount , Account inAccount) {
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.memo ="";
	}
	
	public Transaction(double amount, String memo, Account inAccount) {
		
		this(amount, inAccount);
		
		this.memo = memo;
	}
	
	public String getSummaryLine() {
		 if(this.amount >= 0) {
			 return String.format("%s : $%.02f : %s\n", this.timestamp.toString(),
					 this.amount, this.memo);
		 }else {
			 return String.format("%s : $(%.02f) : %s\n", this.timestamp.toString(),
					 this.amount, this.memo);
		 }
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
