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

    public static String updateTypeQuery(int id, int type){
        return "update Transactions " +
                "set type = '" + type + "' " +
                "where id = " + id;
    }

    public static String createDeleteQuery(BankTransaction t){
        return "delete from Transactions " +
                "where id = 1";
    }

    public static String queryDateType(String start, String end){
        return "select sum(amount),isDeposit,type from transactions " +
                "where date between '"+ start +"' and '"+ end +"' " +
                "group by isDeposit,type";
    }

    public static String getAllTransactionsWithoutType(){
        return "select * from transactions where type is null";
    }

    public static String createTransactionsTable(BankTransaction t){
        return "CREATE TABLE Transactions " +
                "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                "date VARCHAR(20), " +
                "amount INTEGER, " +
                "isDeposit BINARY," +
                "details VARCHAR(200))";

    }
}
