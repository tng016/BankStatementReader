package JDBC;

import Misc.CalendarFactory;
import Transactions.BankTransaction;

import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;

/**
 * Created by tzeyangng on 27/2/17.
 */
public class JDBC {
    private String dbUrl = "jdbc:mysql://localhost:3306/";
    private String suffix = "?verifyServerCertificate=false&useSSL=true";
    private String username;
    private String password;

    public JDBC(String dbName){
        dbUrl = dbUrl + dbName + suffix;
        this.authenticate();
    }

    public ResultSet query(String query) {
        ResultSet results;
        try {
            Connection myConnection = DriverManager.getConnection(dbUrl, username, password);

            Statement myStatement = myConnection.createStatement();

            results = myStatement.executeQuery(query);

        } catch (Exception e) {
            System.out.print(e.getMessage());
            //e.printStackTrace();
            return null;
        }
        return results;
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

    private void authenticate(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your username :");//root
        username = sc.next();
        System.out.println("Please enter your password :");
        password = sc.next();
    }

}
