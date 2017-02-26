package Transactions;

import PDFBox.ReadPDF;
import Regex.RegexChecker;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static Regex.RegexChecker.regexContains;

public class TransactionExtractor {
	public static String[] pages;
	public static String month;
	public static String year;

	public TransactionExtractor(String pdfString){
		pages = pdfString.split("Page \\d+ of \\d+");
		String temp = RegexChecker.regexExtractor("As at [0-9]{2} [A-Za-z]{3} [0-9]{4}",pdfString);
		month = temp.split(" ")[3];
		year = temp.split(" ")[4];
	}
	
	public ArrayList<BankTransaction> extract() throws FileNotFoundException {
		ArrayList<BankTransaction> transactions = new ArrayList<BankTransaction>();
		//for every page..
		for(String pageStr:pages){
			String[] pageStringArray = pageStr.split("\n");
			ArrayList<Integer> startIndexTransactions = findStartIndicesOfTransactions(pageStringArray);
			while (startIndexTransactions.size()>=2){
				String[] transactionStringArray = Arrays.copyOfRange(pageStringArray, startIndexTransactions.remove(0), startIndexTransactions.get(0));
				transactions.add(BankTransactionFactory.createTransaction(transactionStringArray));
			}
		}

		return transactions;
	}

	private static ArrayList<Integer> findStartIndicesOfTransactions (String[] stringArray){
		ArrayList<Integer> startIndexTransactions = new ArrayList<>();
		for (int i=0;i<stringArray.length;i++){
			if (regexContains("Total|^[0-9]{2} "+month,stringArray[i])){
				startIndexTransactions.add(i);
			}
		}
		return startIndexTransactions;
	}
}
