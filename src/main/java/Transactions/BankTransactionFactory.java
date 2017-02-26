package Transactions;

import Regex.RegexChecker;

import java.util.Calendar;

/**
 * Created by tzeyangng on 26/2/17.
 */
public class BankTransactionFactory {
    //Array of strings seen on bank statement to object
    public static BankTransaction createTransaction(String[] transactionArray){
        String date = RegexChecker.regexExtractor("[0-9]{2} [A-Za-z]{3}",transactionArray[0]);
        String details = "";
        int amount = 0;
        int bbf = 0;
        for (int i =0; i<transactionArray.length; i++){
            details = details + transactionArray[i] + " ";
            if (RegexChecker.regexContains("^\\d?\\d?\\d?,?\\d{1,3}?\\.\\d{2}|Interest Earned d?\\d?\\d?,?\\d{1,3}?\\.\\d{2}",transactionArray[i]))
            {
                String[] amountStr = RegexChecker.regexExtractor("\\d?\\d?\\d?,?\\d{1,3}?\\.\\d{2}( \\d{1,3},\\d{1,3}.\\d{2})?",transactionArray[i]).split(" ");
                amount = Integer.parseInt(amountStr[0].replace(".","").replace(",",""));
                if (amountStr.length == 2){
                    bbf = Integer.parseInt(amountStr[1].replace(".","").replace(",",""));
                }
            }
        }
        BankTransaction transactionObj = new BankTransaction(date,details,amount,bbf);
        return transactionObj;
    }
}
