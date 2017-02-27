package Transactions;

public class BankTransaction {
	private String dateTime;
	private String details;
	private int amount;
	private int balance;
	private boolean isDeposit;

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isDeposit() {
		return isDeposit;
	}

	public void setDeposit(boolean deposit) {
		isDeposit = deposit;
	}

	public BankTransaction(String dateTime, String details, int amount, int bbf){
		this.dateTime = dateTime;
		this.details = details;
		this.amount = amount;
		this.balance = bbf;
	}
	
	public void printTransaction(){
		System.out.println("Date of transaction: " + dateTime.toString());
		System.out.println("Details of transaction: " + details);
		System.out.println("Amount Transferred: " + amount);
		System.out.println("Balance Brought Forward: " + balance);
		System.out.println("Is Deposit: " + isDeposit);
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getDateTime() {

		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
}
