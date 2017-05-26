package JDBC;

import Transactions.BankTransaction;
import Transactions.DBTransaction;

/**
 * Created by tzeyangng on 27/2/17.
 */
public class queryBuilder {
    public static String table = "transactions";
    public static String createStoreQuery(BankTransaction t){
        return "insert into "+table+" (date, amount, isDeposit, details) values (" +
                "'" + t.getDate() + "', " +
                "'"+ t.getAmount() + "', " +
                "'"+ (t.isDeposit()?1:0) + "', " +
                "'"+ t.getDetails() + "')";
    }

    public static String updateTypeQuery(int id, int type){
        return "update "+table+" " +
                "set type = '" + type + "' " +
                "where id = " + id;
    }

    public static String updateEntryQuery(DBTransaction t){
        return "update "+table+" " +
                "set date = '"+t.generateDateString()+"', " +
                "amount = '"+t.getAmount()+"', " +
                "isDeposit = '"+t.isDeposit()+"', " +
                "details = '"+t.getDetails()+"', " +
                "type = '"+t.getType()+"' " +
                "where id = "+t.getId()+";";
    }

    public static String getEntryQuery(int id){
        return "select * from "+table+" where id = '" + id + "';";
    }

    public static String deleteEntryQuery(int id){
        return "delete from "+table+" " +
                "where id = '"+id+"';";
    }

    public static String queryDateType(String start, String end){
        return "select sum(amount),isDeposit,type from "+table+" " +
                "where date between '"+ start +"' and '"+ end +"' " +
                "group by isDeposit,type";
    }

    public static String queryDate(String start, String end){
        return "select sum(amount),isDeposit from "+table+" " +
                "where date between '"+ start +"' and '"+ end +"' " +
                "group by isDeposit";
    }

    public static String queryView(String start, String end){
        return "select * from "+table+" " +
                "where date between '"+ start +"' and '"+ end +"' ;";
    }

    public static String getAllTransactionsWithoutType(){
        return "select * from "+table+" where type is null";
    }

    public static String deleteCreditQuery(int amount){
        return "delete from "+table+" where type = 1 and amount = " + Integer.toString(amount);
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
