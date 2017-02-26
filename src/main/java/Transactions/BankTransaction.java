package Transactions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BankTransaction {
	private String dateTime;
	private String details;
	private int amount;
	private int balanceBroughtForward;
	
	public BankTransaction(String dateTime, String details, int amount, int bbf){
		this.dateTime = dateTime;
		this.details = details;
		this.amount = amount;
		this.balanceBroughtForward = bbf;
	}
	
	public void printTransaction(){
		System.out.println("Date of transaction: " + dateTime.toString());
		System.out.println("Details of transaction: " + details);
		System.out.println("Amount Transferred: " + amount);
		System.out.println("Balance Brought Forward: " + balanceBroughtForward);
	}
}
