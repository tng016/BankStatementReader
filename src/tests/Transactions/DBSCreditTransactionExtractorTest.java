package Transactions;

import PDFBox.ReadPDF;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by tzeyangng on 24/5/17.
 */
public class DBSCreditTransactionExtractorTest {
    @Test
    public void extract() throws Exception {
        File f = new File("Testing Folder/dbsctest.pdf");
        String pdfString = "";
        try {
            pdfString = ReadPDF.readPDF(f.getPath());
            System.out.println("Successfully decoded pdf file...");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
        String[] a = pdfString.split("\n");
        for (String b:a){
            System.out.println(b);
        }

        //DBSCreditTransactionExtractor DBSC = new DBSCreditTransactionExtractor(pdfString);
    }

    @Test
    public void getMonth() throws Exception {

    }

    @Test
    public void getYear() throws Exception {

    }

    @Test
    public void getBank() throws Exception {

    }

}