

import FileIO.FileReaderWriter;
import PDFBox.ReadPDF;
import Transactions.BankTransaction;
import Transactions.TransactionExtractor;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Created by tzeyangng on 28/12/16.
 */
public class ApplicationUserInterface {
    private static Scanner scanner = new Scanner(System.in);

    private static void print(Object a) {
        System.out.println(a);
    }

    public static void main(String[] args){
        System.out.println("Welcome to TY's Bank Statement Reader");
        System.out.println("-------------------------------------");

        int userIntChoice = 0;
        while(userIntChoice != -1){
            printMenu();

            //get user input
            try {
                userIntChoice = Integer.parseInt(scanner.nextLine());
            }catch (Exception e){
                print("Invalid input. Try again");
                userIntChoice = 0;
            }

            switch(userIntChoice){
                case 1:
                    print("Enter the bank statement file name");
                    String bankStatementFileName = scanner.nextLine();
                    try{
                        String pdfString = ReadPDF.readPDF(bankStatementFileName);

                        TransactionExtractor DBSExtractor = new TransactionExtractor(pdfString); //create extractor object
                        ArrayList<BankTransaction> transactions = DBSExtractor.extract();
                        for (BankTransaction b : transactions){
                            b.printTransaction();
                        }
                    }catch(FileNotFoundException e){
                        print("File not found. Try again");
                    }

                    break;
                case 2:
                    try{
                        String a = ReadPDF.readPDF("Testing Folders/DBS.pdf");
                        FileReaderWriter.writeFile("DBS.txt",a);
                    }catch(Exception e){
                        print("File not found. Try again");
                    }

                    break;

                default:
                    break;

            }

        }
        System.out.println("------------------------------------------");
        System.out.println("Quitting program. Hope you had a nice day!");
        System.out.println("------------------------------------------");

    }

    public static void printMenu(){
        System.out.println("Select (1) : Import bank statment");
        System.out.println("Select (-1) : Quit Program");
    }
}
