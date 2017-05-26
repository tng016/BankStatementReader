package Transactions;

import Misc.CalendarFactory;
import Regex.RegexChecker;

import java.text.ParseException;
import java.util.ArrayList;


public class DBSCreditTransactionExtractor {
	private String[] lines;
	private String date;
	private int sum;
	private ArrayList<BankTransaction> transactions;

	public DBSCreditTransactionExtractor(String pdfString){
		lines = pdfString.split("\n");
		findDate();
		transactions = new ArrayList<BankTransaction>();
	}

	private void findDate(){
		for (int i = 0; i< lines.length; i++){
			if (lines[i].contains("STATEMENT DATE CREDIT LIMIT MINIMUM PAYMENT PAYMENT DUE DATE")){
				date = RegexChecker.regexExtractor("^\\d\\d \\w\\w\\w 20\\d\\d",lines[i+1]);
			}
		}
	}
	
	public ArrayList<BankTransaction> extract() {
		int startIndex = 0;
		int endIndex = 1000;
		for (int i=0;i<lines.length;i++){
			if (lines[i].contains("NEW TRANSACTIONS NG TZE YANG")){
				startIndex = i;
			}
			if (RegexChecker.regexContains("^GRAND TOTAL FOR ALL CARD ACCOUNTS:",lines[i])){
				endIndex = i;
				sum =Integer.parseInt(RegexChecker.regexExtractor("\\d?\\d?\\d?,?\\d{1,3}?\\.\\d{2}",lines[i]).
						replace(".","").replace(".",""));
			}
		}

		String s =lines[startIndex+1];

		for (int i=startIndex+2;i<endIndex-2;i++){
			if(!RegexChecker.regexContains("^\\d\\d \\w\\w\\w",lines[i])){
				s += lines[i];
			}
			else{
				transactions.add(BankTransactionFactory.createTransactionDBSC(s));
				s  =lines[i];
			}
		}
		transactions.add(BankTransactionFactory.createTransactionDBSC(s));
		String year = RegexChecker.regexExtractor("\\d\\d\\d\\d",date);
		for(BankTransaction t :transactions){
			try {
				t.setDate( CalendarFactory.reformatDateString(t.getDate().trim()+" "+year));

			} catch (ParseException e) {
				e.printStackTrace();
			}

		}


		System.out.println("Completed extractions!");

		return transactions;
	}

	public String getBank(){
		return "DBS Credit";
	}


	public String getDate(){
		return date;
	}

	public int getSum() {
		return sum;
	}
}


