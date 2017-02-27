package Transactions;

import Regex.RegexChecker;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;


public class TransactionExtractor {
	private String[] pages;
	private String month;
	private String year;
	private int balanceBroughtForward;
	private ArrayList<BankTransaction> transactions;

	public TransactionExtractor(String pdfString){
		pages = pdfString.split("Page \\d+ of \\d+");
		String temp = RegexChecker.regexExtractor("As at [0-9]{2} [A-Za-z]{3} [0-9]{4}",pdfString);
		month = temp.split(" ")[3];
		year = temp.split(" ")[4];
		temp = RegexChecker.regexExtractor("Balance Brought Forward (\\d{1,3},\\d{1,3}.\\d{2})?",pdfString);
		balanceBroughtForward = Integer.parseInt(temp.split(" ")[3].replace(".","").replace(",",""));
		transactions = new ArrayList<BankTransaction>();
	}
	
	public ArrayList<BankTransaction> extract() throws FileNotFoundException {
		//for every page..
		for(String pageStr:pages){
			String[] pageStringArray = pageStr.split("\n");
			ArrayList<Integer> startIndexTransactions = findStartIndicesOfTransactions(pageStringArray);
			while (startIndexTransactions.size()>=2){
				String[] transactionStringArray = Arrays.copyOfRange(pageStringArray, startIndexTransactions.remove(0), startIndexTransactions.get(0));
				transactions.add(BankTransactionFactory.createTransaction(transactionStringArray));
			}
		}
		implementYear();
		implementDepositWithdrawal();

		return transactions;
	}

	private ArrayList<Integer> findStartIndicesOfTransactions (String[] stringArray){
		ArrayList<Integer> startIndexTransactions = new ArrayList<>();
		for (int i=0;i<stringArray.length;i++){
			if (RegexChecker.regexContains("Balance Carried Forward|^[0-9]{2} "+month,stringArray[i])){
				startIndexTransactions.add(i);
			}
		}
		return startIndexTransactions;
	}

	private void implementYear(){
		for (BankTransaction t : transactions){
			t.setDateTime(t.getDateTime() + " " + year);
		}
	}

	private void implementDepositWithdrawal(){
		for (int i=0;i<transactions.size();i++){
			int previousBalance;
			if (i ==0){
				previousBalance = balanceBroughtForward;
			}else{
				previousBalance = transactions.get(i-1).getBalance();
			}

			if (transactions.get(i).getBalance() != 0){
				boolean isDeposit = (transactions.get(i).getBalance()-previousBalance == transactions.get(i).getAmount());
				transactions.get(i).setDeposit(isDeposit);
				continue;
			}

			ArrayList<Integer> amounts = new ArrayList<>();

			while (transactions.get(i).getBalance() == 0){
				amounts.add(transactions.get(i).getAmount());
				i++;
				System.out.println("looping" + transactions.get(i).getBalance());
			}
			amounts.add(transactions.get(i).getAmount());
			for (Integer j : amounts){
				System.out.println(j);
			}
			findIsDeposit(amounts,previousBalance,transactions.get(i).getBalance(),i);
		}
	}

	private void findIsDeposit(ArrayList<Integer> amountList, int previousBalance, int currentBalance, int pointer){
		System.out.println(previousBalance);
		System.out.println(currentBalance);
		System.out.println(amountList.get(0));
		System.out.println(amountList.get(1));
		int numberOfBits = amountList.size();
		int binaryNumber = (int) (Math.pow(2,numberOfBits+1)-1);
		int expectedAnswer = currentBalance - previousBalance;
		String binaryString;
		System.out.println(expectedAnswer);

		while(true){
			binaryString = Integer.toBinaryString(binaryNumber);
			int answer = 0;
			for (int i=1; i<binaryString.length();i++){
				if (binaryString.charAt(i)== '0'){
					answer += amountList.get(i-1);
				}
				else{
					answer -= amountList.get(i-1);
				}
			}
			System.out.println(answer);
			if (answer == expectedAnswer){
				break;
			}
			binaryNumber--;
		}
		System.out.println("binary String : "+binaryString);

		for (int i=binaryString.length()-1; i>0; i--) {
			boolean isDeposit = (binaryString.charAt(i)=='0');
			transactions.get(pointer - binaryString.length()+i+1).setDeposit(isDeposit);
		}

	}
}
