package PDFBox;

import FileIO.FileReaderWriter;
import junit.framework.TestCase;

/**
 * Created by tzeyangng on 26/2/17.
 */
public class ReadPDFTest extends TestCase {
    public void testReadPDFDBS() throws Exception {
        String pdf = ReadPDF.readPDF("Testing Folders/DBS.pdf");
        String ans = FileReaderWriter.readFileString("Testing Folders/DBS.txt");
        assertEquals(pdf,ans);
    }

    public void testReadPDFOCBC() throws Exception {
        String pdf = ReadPDF.readPDF("Testing Folders/OCBC.pdf");
        String ans = FileReaderWriter.readFileString("Testing Folders/OCBC.txt");
        assertEquals(pdf,ans);
    }
}