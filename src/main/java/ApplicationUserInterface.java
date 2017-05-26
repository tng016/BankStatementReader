

import Budget.Budget;
import FileIO.FileManager;
import JDBC.JDBC;
import JDBC.queryBuilder;
import Misc.CalendarFactory;
import Misc.Money;
import PDFBox.ReadPDF;
import Transactions.BankTransaction;
import Transactions.DBSCreditTransactionExtractor;
import Transactions.DBTransaction;
import Transactions.DBSTransactionExtractor;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by tzeyangng on 28/12/16.
 */
public class ApplicationUserInterface {
    private static Scanner scanner = new Scanner(System.in);

    private static void print(Object a) {
        System.out.println(a);
    }

    private static JDBC myJDBC;

    public static void main(String[] args){
        System.out.println("Welcome to TY's Bank Statement Reader");
        System.out.println("-------------------------------------");
        myJDBC = new JDBC("BankStatements");

        int userIntChoice = 0;
        while(userIntChoice != -1){
            printMenu();

            //get user input
            try {
                if(scanner.hasNext("\n")){
                    scanner.next("\n");
                }
                userIntChoice = Integer.parseInt(scanner.nextLine());
            }catch (Exception e){
                print("Invalid input. Try again");
                userIntChoice = 0;
            }

            switch(userIntChoice){
                case 1:
                    importBankStatement();
                    break;

                case 2:
                    addType();
                    break;

                case 3:
                    analyseDateType();
                    break;

                case 4:
                    analyseDate();
                    break;
                case 5:
                    viewEntry();
                    break;
                case 6:
                    editEntry();
                    break;
                case 7:
                    deleteEntry();
                    break;
                case 8:
                    //combineEntry();
                    break;
                default:
                    break;

            }

        }
        scanner.close();
        print("------------------------------------------");
        print("Quitting program. Hope you had a nice day!");
        print("------------------------------------------");

    }

    public static void printMenu(){
        print("Select (1) : Import bank statments");
        print("Select (2) : Add types to transactions");
        print("Select (3) : Analyse IO by date and type");
        print("Select (4) : Analyse total IO by date");
        print("Select (5) : View entries by date");
        print("Select (6) : Edit database entry");
        print("Select (7) : Delete database entry");
        print("Select (8) : Combine database entry");
        print("Select (-1) : Quit Program");
    }

    public static void importBankStatement() {
        print("Importing files into DataBase...\n");
        File[][] importFiles = FileManager.getImportFileList();
        ArrayList<BankTransaction> transactionList = new ArrayList<>();
        for (int i=0;i<importFiles.length;i++){
            File[] folder = importFiles[i];
            for (File f : folder) {
                if (!f.getName().contains(".pdf")) {
                    continue;
                }
                print("Importing file: " + f.getName());
                String pdfString = null;
                try {
                    pdfString = ReadPDF.readPDF(f.getPath());
                    print("Successfully decoded pdf file...");
                } catch (IOException e) {
                    print(e.getMessage());
                    //e.printStackTrace();
                }

                switch(i) {
                    case 0: //DBS
                        DBSTransactionExtractor DBSExtractor = new DBSTransactionExtractor(pdfString); //create extractor object
                        transactionList.addAll(DBSExtractor.extract());
                        print("Successfully extracted transactions...");
                        FileManager.moveFileToArchive(f, DBSExtractor.getMonth(), DBSExtractor.getYear(), DBSExtractor.getBank());
                        break;
                    case 1: //DBS Credit
                        DBSCreditTransactionExtractor DBSCreditExtractor = new DBSCreditTransactionExtractor(pdfString);
                        transactionList.addAll(DBSCreditExtractor.extract());
                        print("Successfully extracted transactions...");
                        FileManager.moveFileToArchive(f, DBSCreditExtractor.getDate(), DBSCreditExtractor.getBank());
                        if(myJDBC.store(queryBuilder.deleteCreditQuery(DBSCreditExtractor.getSum()))){
                            print("Deleted corresponding credit statement");
                        }

                        break;
                    case 2: //OCBC
                        break;
                }
                print("Moved File to Archives \n");
            }
        }

        print("Storing all transactions to Database...");
        for (BankTransaction b : transactionList){
            myJDBC.store(queryBuilder.createStoreQuery(b));
        }
        print("Storing complete! \n");
    }

    public static void addType(){
        ResultSet results = myJDBC.query(queryBuilder.getAllTransactionsWithoutType());
        HashMap<Integer,Integer> typeMap = new HashMap<Integer,Integer>();
        try {
            while (results.next()){
                int id = results.getInt("id");
                boolean isDeposit = results.getBoolean("isDeposit");
                int type = 0;
                print("ID: "+ id);
                print("Date: "+ results.getDate("date"));
                print("Amount: "+ Money.printMoney(results.getInt("amount")));
                print(isDeposit?"Desposit":"Withdrawal");
                print("Details: "+ results.getString("details"));
                print("Enter the type: ");
                Budget.printCategories();
                type = scanner.nextInt();
                typeMap.put(id,type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i :typeMap.keySet()){
            myJDBC.store(queryBuilder.updateTypeQuery(i,typeMap.get(i)));
        }
    }

    public static void analyseDateType(){
        System.out.println("Enter start date (format YYYY-MM-DD): ");
        String start = scanner.nextLine();
        System.out.println("Enter end date (format YYYY-MM-DD): ");
        String end = scanner.nextLine();
        ResultSet results = myJDBC.query(queryBuilder.queryDateType(start,end));
        try {
            while (results.next()) {
                print("Type:" +Budget.parseType(results.getInt("type")));
                print((results.getBoolean("isDeposit")?"Desposit":"Withdrawal"));
                print("Amount: "+ Money.printMoney(results.getInt("sum(amount)")));
                print("");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void analyseDate(){
        System.out.println("Enter start date (format YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.println("Enter end date (format YYYY-MM-DD): ");
        String endDate = scanner.nextLine();
        ResultSet results = myJDBC.query(queryBuilder.queryDate(startDate,endDate));
        try {
            boolean b;
            int sum = 0;
            while (results.next()) {
                b = results.getBoolean("isDeposit");
                int amt = results.getInt("sum(amount)");
                print("Total "+(b?"Desposit":"Withdrawal"));
                print("Amount: "+ Money.printMoney(amt));
                print("");
                if (!b){
                    sum = sum - amt;
                }
                else{
                    sum = sum + amt;
                }
            }
            print("Net Change: "+Money.printMoney(sum));
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewEntry(){
        System.out.println("Enter start date (format YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.println("Enter end date (format YYYY-MM-DD): ");
        String endDate = scanner.nextLine();
        ResultSet results = myJDBC.query(queryBuilder.queryView(startDate,endDate));
        try {
            while (results.next()) {
                print("ID: "+(results.getInt("id")));
                print("Date: "+(results.getString("date")));
                print("Amount: "+(results.getBoolean("isDeposit")?"":"-")+Money.printMoney(results.getInt("amount")));
                print("Details: "+(results.getString("details")));
                print("Type: "+Budget.type[(results.getInt("type"))]);
                print("--------------------------------");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editEntry(){
        System.out.println("Enter transaction ID: ");
        int tID,userInput = 0;
        tID = scanner.nextInt();
        ResultSet results = myJDBC.query(queryBuilder.getEntryQuery(tID));

        DBTransaction t = null;
        try {
            results.next();
            t = new DBTransaction(tID,results.getDate("date"),results.getInt("amount"),
                    results.getString("details"),results.getBoolean("isDeposit"), results.getInt("type"));

            while (userInput != -1){
                t.printTransaction();
                print("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
                print("Select (1): Edit Date");
                print("Select (2): Edit Amount");
                print("Select (3): Edit Details");
                print("Select (4): Edit isDeposit");
                print("Select (5): Edit Type");
                print("Select (-1): Exit editor");
                userInput = scanner.nextInt();

                switch (userInput){
                    case 1:
                        print("Enter the new Date (Format YYYY-MM-DD): ");
                        scanner.nextLine();
                        Date d =CalendarFactory.generateDate(scanner.nextLine());
                        t.setDate(d);
                        break;
                    case 2:
                        print("Enter the new Amount (without '.'): ");
                        t.setAmount(scanner.nextInt());
                        break;
                    case 3:
                        print("Enter the new Details: ");
                        scanner.nextLine();
                        t.setDetails(scanner.nextLine());
                        break;
                    case 4:
                        print("Enter the new isDeposit (true/false): ");
                        t.setDeposit(scanner.nextBoolean());
                        break;
                    case 5:
                        print("Enter the new Type: ");
                        t.setType(scanner.nextInt());
                        break;
                    case -1:
                        print("Storing data back into database...");
                        break;
                    default:
                        print("Error input: " + userInput);
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        myJDBC.store(queryBuilder.updateEntryQuery(t));
        print("Database Updated!");

    }

    public static void deleteEntry(){
        print("Enter transaction ID: ");
        int tID= 0;
        tID = scanner.nextInt();
        myJDBC.store(queryBuilder.deleteEntryQuery(tID));
        print("Entry Deleted!");
    }

    /*public static void combineEntry(){
        int userInput= 0;
        ArrayList<DBTransaction> transactionsList = new ArrayList<DBTransaction>();
        while (true){
            System.out.println("Enter transaction ID (Enter -1 to proceed): ");
            userInput = scanner.nextInt();
            if (userInput == -1){
                break;
            }
            DBTransaction t = null;
            ResultSet results = myJDBC.query(queryBuilder.getEntryQuery(userInput));
            try {
                results.next();
                 t = new DBTransaction(userInput,results.getDate("date"),results.getInt("amount"),
                        results.getString("details"),results.getBoolean("isDeposit"), results.getInt("type"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            transactionsList.add(t);
        }

        int sum = 0;
        for (DBTransaction t : transactionsList){
            if(t.isDeposit()==1){
                sum += t.getAmount();
            }
            else{
                sum -= t.getAmount();
            }
        }
        print("Combined sum :" + sum);
        print("Enter the new Date (Format YYYY-MM-DD): ");
        scanner.nextLine();
        Date d = null;
        try {
             d =CalendarFactory.generateDate(scanner.nextLine());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        print("Enter the new Details: ");
        scanner.nextLine();
        String s =scanner.nextLine();
        print("Enter the new Type: ");
        int type = scanner.nextInt();
        DBTransaction t = new DBTransaction(0,d,Math.abs(sum),s,(sum>0),type);
        myJDBC.store(queryBuilder.createStoreQuery(t));
    }*/
}
