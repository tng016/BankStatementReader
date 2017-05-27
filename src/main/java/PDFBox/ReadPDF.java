package PDFBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class ReadPDF {

	public static String readPDF(String filePath) throws IOException {
		String st = null;
		PDDocument document = null;
		try{
			document = PDDocument.load(new File(filePath));
			document.getClass();
			//if( !document.isEncrypted() ){
			if( true ){
			    PDFTextStripperByArea stripper = new PDFTextStripperByArea();
			    stripper.setSortByPosition( true );
			    PDFTextStripper Tstripper = new PDFTextStripper();
			    st = Tstripper.getText(document);
			}
		}
		catch(IOException e){
			throw e;
		}
		finally
		{
			if( document != null )
			{
				document.close();
			}
		}
		return st;
	}

}
