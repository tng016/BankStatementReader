package JDBC;

import Transactions.BankTransaction;

/**
 * Created by tzeyangng on 27/2/17.
 */
public class queryBuilder {
    public static String createStoreQuery(BankTransaction t){
        return "insert into Transactions (date, amount, isDeposit, details) values (" +
                "'" + t.getDate() + "', " +
                "'"+ t.getAmount() + "', " +
                "'"+ (t.isDeposit()?1:0) + "', " +
                "'"+ t.getDetails() + "')";
    }

    public static String createTransactionsTable(){
        return "CREATE TABLE Transactions " +
                "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                "date VARCHAR(20), " +
                "amount INTEGER, " +
                "isDeposit BINARY," +
                "details VARCHAR(200))";

    }
}
