package Transactions;

import java.util.Calendar;

public class BankTransaction {
	private String date;
	private int amount;
	private String details;
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

	public BankTransaction(String date, String details, int amount, int bbf){
		details = details.replace("'","");
		this.date = date;
		this.details = details;
		this.amount = amount;
		this.balance = bbf;
	}
	
	public void printTransaction(){
		System.out.println("Date of transaction: " + date);
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

	public String getDate() {

		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
