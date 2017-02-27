package JDBC;

import Misc.CalendarFactory;
import Transactions.BankTransaction;

import java.sql.*;
import java.text.ParseException;

/**
 * Created by tzeyangng on 27/2/17.
 */
public class JDBC {
    private String dbUrl = "jdbc:mysql://localhost:3306/";
    private String suffix = "?verifyServerCertificate=false&useSSL=true";
    private String username;
    private String password;

    public JDBC(String dbName, String username, String password){
        dbUrl = dbUrl + dbName + suffix;
        this.username = username;
        this.password = password;
    }

    public boolean query(String query) {
        try {
            Connection myConnection = DriverManager.getConnection(dbUrl, username, password);

            Statement myStatement = myConnection.createStatement();

            ResultSet results = myStatement.executeQuery(query);

            while (results.next()){
                System.out.println(results.getString("title"));
            }

        } catch (Exception e) {
            System.out.print(e.getMessage());
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean store(String query) {
        try {
            Connection myConnection = DriverManager.getConnection(dbUrl, username, password);

            Statement myStatement = myConnection.createStatement();

            myStatement.executeUpdate(query);

        } catch (Exception e) {
            System.out.print(e.getMessage());
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main (String[] args) throws ParseException {
        JDBC myJDBC = new JDBC("BankStatements","root","MyMacb00k");
        BankTransaction t = new BankTransaction("11 Nov 2016", "testing", 1000, 1);
        String s = queryBuilder.createStoreQuery(t);
        String newCal = CalendarFactory.reformatDateString("11 Nov 2016");
        s = s.replace("11 Nov 2016", newCal );
        myJDBC.store(s);
        System.out.print(s);
        System.out.print(newCal);
    }
}
