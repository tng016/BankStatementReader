package Transactions;

import Algorithms.isDeposit;
import Misc.Money;
import Regex.RegexChecker;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Misc.CalendarFactory.reformatDateString;


public class OCBCTransactionExtractor {
	private String[] pages;
	private String month;
	private String year;
	private int balanceBroughtForward;
	private ArrayList<BankTransaction> transactions;
	private static String Monthregex ="\\d{1,2} JAN|\\d{1,2} FEB|\\d{1,2} MAR|\\d{1,2} APR|\\d{1,2} MAY|\\d{1,2} JUN|" +
			"\\d{1,2} JUL|\\d{1,2} AUG|\\d{1,2} SEP|\\d{1,2} OCT|\\d{1,2} NOV|\\d{1,2} DEC";


	public OCBCTransactionExtractor(String pdfString){
		pages = pdfString.split("Page \\d+ of \\d+");
		String tempString = RegexChecker.regexExtractor("\\d{1,2}  ?\\D\\D\\D \\d\\d\\d\\d TO \\d{1,2}  ?\\D\\D\\D \\d\\d\\d\\d",pdfString);
		month = RegexChecker.regexExtractor("JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC",tempString);
		year = RegexChecker.regexExtractor("20\\d\\d",tempString);
		transactions = new ArrayList<BankTransaction>();
	}
	
	public ArrayList<BankTransaction> extract() {
		//for every page..
		int count = 1;
		for(String pageStr:pages){
			System.out.println("Extracting transactions from Page "+count++);
			String[] pageStringArray = pageStr.split("\n");
			int startIndex = 0;
			int endIndex = 1000;
			for (int i=0;i<pageStringArray.length;i++){
				if (pageStringArray[i].contains("BALANCE B/F")){
					balanceBroughtForward = Money.strToInt(RegexChecker.regexExtractor("\\d?\\d?\\d?,?\\d?\\d?\\d?\\.\\d\\d",pageStringArray[i]));
					startIndex = i;
				}
				if (pageStringArray[i].contains("BALANCE C/F")){
					endIndex = i;
				}
			}
			if(startIndex == 0 && endIndex==1000){
				continue;
			}

			String s =pageStringArray[startIndex+1];

			for (int i=startIndex+2;i<endIndex;i++){
				if(!RegexChecker.regexContains(Monthregex,pageStringArray[i])){
					s += pageStringArray[i];
				}
				else{
					transactions.add(BankTransactionFactory.createTransactionOCBC(s,month));
					s  =pageStringArray[i];
				}
			}
			transactions.add(BankTransactionFactory.createTransactionOCBC(s,month));
		}

		System.out.println("Completed extractions!");
		if(!implementYear()){
			System.out.println("FAILED TO IMPLEMENT YEAR...PROCESS CANCELLED");
			return null;
		}
		System.out.println("Year implementation successful");
		if(implementDepositWithdrawal()) {
			System.out.println("D/W implementation successful");
		}
		else {
			System.out.println("ERROR IN D/W");
		}

		return transactions;
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

	private boolean implementDepositWithdrawal(){
		boolean complete = true;
		for (int i=0;i<transactions.size();i++){
			BankTransaction t = transactions.get(i);
			if(t.getBalance()-balanceBroughtForward==t.getAmount()){
				t.setDeposit(true);
				balanceBroughtForward = t.getBalance();
			}
			else if(balanceBroughtForward-t.getBalance()==t.getAmount()){
				t.setDeposit(false);
				balanceBroughtForward = t.getBalance();
			}
			else{
				complete = false;
			}
		}
		return complete;
	}

	public String getMonth() {
		return month;
	}

	public String getYear() {
		return year;
	}

	public String getBank(){
		return "OCBC";
	}

}
