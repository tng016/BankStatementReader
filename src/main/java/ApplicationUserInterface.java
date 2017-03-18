

import FileIO.FileManager;
import FileIO.FileReaderWriter;
import JDBC.JDBC;
import JDBC.queryBuilder;
import PDFBox.ReadPDF;
import Transactions.BankTransaction;
import Transactions.DBTransaction;
import Transactions.TransactionExtractor;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        ResultSet results;

        int userIntChoice = 0;
        while(userIntChoice != -1){
            printMenu();

            //get user input
            try {
                userIntChoice = Integer.parseInt(scanner.nextLine());
                print(userIntChoice);
            }catch (Exception e){
                print("Invalid input. Try again");
                userIntChoice = 0;
            }

            switch(userIntChoice){
                case 1:
                    print("Importing files into DataBase...\n");
                    File[] importFiles = FileManager.getImportFileList();
                    ArrayList<BankTransaction> transactionList = new ArrayList<>();
                    for (File f : importFiles){
                        if (!f.getName().contains(".pdf")){
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
                        TransactionExtractor DBSExtractor = new TransactionExtractor(pdfString); //create extractor object
                        transactionList.addAll(DBSExtractor.extract());
                        print("Successfully extracted transactions...");
                        FileManager.moveFileToArchive(f,DBSExtractor.getMonth(),DBSExtractor.getYear(),DBSExtractor.getBank());
                        print("Moved File to Archives \n");
                    }

                    print("Storing all transactions to Database...");
                    for (BankTransaction b : transactionList){
                        myJDBC.store(queryBuilder.createStoreQuery(b));
                    }
                    print("Storing complete! \n");
                    break;
                case 2:
                    results = myJDBC.query(queryBuilder.getAllTransactionsWithoutType());
                    HashMap<Integer,Integer> typeMap = new HashMap<Integer,Integer>();
                    try {
                        while (results.next()){
                            int id = results.getInt("id");
                            boolean isDeposit = results.getBoolean("isDeposit");
                            int type = 0;
                            print("ID: "+ id);
                            print("Date: "+ results.getDate("date"));
                            print("Amount: "+ results.getInt("amount"));
                            print(isDeposit?"Desposit":"Withdrawal");
                            print("Details: "+ results.getString("details"));
                            print("Enter the type: ");
                            type = scanner.nextInt();
                            typeMap.put(id,type);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    for (int i :typeMap.keySet()){
                        myJDBC.store(queryBuilder.updateTypeQuery(i,typeMap.get(i)));
                    }
                    break;

                case 3:
                    System.out.println("Enter start date (format YYYY-MM-DD): ");
                    String start = scanner.nextLine();
                    System.out.println("Enter end date (format YYYY-MM-DD): ");
                    String end = scanner.nextLine();
                    results = myJDBC.query(queryBuilder.queryDateType(start,end));
                    try {
                        while (results.next()) {
                            print("Type:" +results.getInt("type"));
                            print((results.getBoolean("isDeposit")?"Desposit":"Withdrawal"));
                            print("Amount: "+ results.getInt("sum(amount)"));
                        }
                    }catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;

            }

        }
        print("------------------------------------------");
        print("Quitting program. Hope you had a nice day!");
        print("------------------------------------------");

    }

    public static void printMenu(){
        System.out.println("Select (1) : Import bank statments");
        System.out.println("Select (2) : Add types to transactions");
        System.out.println("Select (3) : Analyse spending by date and type");
        System.out.println("Select (-1) : Quit Program");
    }
}
