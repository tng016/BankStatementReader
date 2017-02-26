package Transactions;

import Transactions.BankTransaction;

import java.util.ArrayList;

public class TransactionArchive {
	private ArrayList<BankTransaction> transactionList;
	
	public TransactionArchive(){
		transactionList = new ArrayList<>();
	}
	
	public void addTransaction(BankTransaction e){
		transactionList.add(e);
	}
	
	public void printTransactionList(){
		for (BankTransaction i : transactionList)
			i.printTransaction();
	}

}
