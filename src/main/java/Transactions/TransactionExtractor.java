package Transactions;

import Algorithms.isDeposit;
import Regex.RegexChecker;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Misc.CalendarFactory.reformatDateString;


public class TransactionExtractor {
	private String[] pages;
	private String month;
	private String year;
	private int balanceBroughtForward;
	private ArrayList<BankTransaction> transactions;

	public TransactionExtractor(String pdfString){
		pages = pdfString.split("Page \\d+ of \\d+");
		String tempString = RegexChecker.regexExtractor("As at [0-9]{2} [A-Za-z]{3} [0-9]{4}",pdfString);
		month = tempString.split(" ")[3];
		year = tempString.split(" ")[4];
		tempString = RegexChecker.regexExtractor("Balance Brought Forward (\\d{1,3},\\d{1,3}.\\d{2})?",pdfString);
		balanceBroughtForward = Integer.parseInt(tempString.split(" ")[3].replace(".","").replace(",",""));
		transactions = new ArrayList<BankTransaction>();
	}
	
	public ArrayList<BankTransaction> extract() {
		//for every page..
		int count = 1;
		for(String pageStr:pages){
			System.out.println("Extracting transactions from Page "+count++);
			String[] pageStringArray = pageStr.split("\n");
			ArrayList<Integer> startIndexTransactions = findStartIndicesOfTransactions(pageStringArray);
			while (startIndexTransactions.size()>=2){
				String[] transactionStringArray = Arrays.copyOfRange(pageStringArray, startIndexTransactions.remove(0),
						startIndexTransactions.get(0));
				transactions.add(BankTransactionFactory.createTransaction(transactionStringArray));
			}
		}

		System.out.println("Completed extractions!");
		if(!implementYear()){
			System.out.println("FAILED TO IMPLEMENT YEAR...PROCESS CANCELLED");
			return null;
		}
		System.out.println("Year implementation successful");
		implementDepositWithdrawal();
		System.out.println("D/W implementation successful");

		return transactions;
	}

	private ArrayList<Integer> findStartIndicesOfTransactions (String[] stringArray){
		ArrayList<Integer> startIndexTransactions = new ArrayList<>();
		for (int i=0;i<stringArray.length;i++){
			if (RegexChecker.regexContains("Balance Carried Forward|^[0-9]{2} "+month+ " ",stringArray[i])){
				startIndexTransactions.add(i);
			}
		}
		return startIndexTransactions;
	}

	private boolean implementYear(){
		for (BankTransaction t : transactions){
			try {
				t.setDate(reformatDateString(t.getDate() + " " + year));
			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
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
			}
			amounts.add(transactions.get(i).getAmount());
			isDeposit Algorithm = new isDeposit(amounts,previousBalance,transactions.get(i).getBalance());
			Algorithm.solve(Algorithm.getAmountList(),Algorithm.getTarget());
			ArrayList<Integer> withdrawalList = Algorithm.getNegativeList();
			List<BankTransaction> subList = transactions.subList(i-amounts.size()+1,i+1);
			for(BankTransaction b :subList){
				if (withdrawalList.contains(b.getAmount())){
					//b.setAmount(b.getAmount()*-1);
					withdrawalList.remove((Integer)b.getAmount());
				}
				else{
					b.setDeposit(true);
				}
			}
		}
	}

	/*private void findIsDeposit(ArrayList<Integer> amountList, int previousBalance, int currentBalance, int pointer){
		int numberOfBits = amountList.size();
		int binaryNumber = (int) (Math.pow(2,numberOfBits+1)-1);
		int expectedAnswer = currentBalance - previousBalance;
		String binaryString;

		while(true){
			binaryString = Integer.toBinaryString(binaryNumber);
			System.out.println(binaryString);
			int answer = 0;
			for (int i=1; i<binaryString.length();i++){
				if (binaryString.charAt(i)== '0'){
					answer += amountList.get(i-1);
				}
				else{
					answer -= amountList.get(i-1);
				}
			}
			if (answer == expectedAnswer){
				break;
			}
			binaryNumber--;
		}

		for (int i=binaryString.length()-1; i>0; i--) {
			boolean isDeposit = (binaryString.charAt(i)=='0');
			transactions.get(pointer - binaryString.length()+i+1).setDeposit(isDeposit);
		}

	}*/

	public String getMonth() {
		return month;
	}

	public String getYear() {
		return year;
	}

	public String getBank(){
		for (String p: pages){
			if (p.contains("DBS Bank Ltd ")){
				return "DBS";
			}
		}
		return null;
	}
}
