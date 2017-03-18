package FileIO;

import java.io.File;

/**
 * Created by tzeyangng on 27/2/17.
 */
public class FileManager {
    public static File[] getImportFileList(){
        File f = new File("Import Folder");
        return f.listFiles();
    }

    public static void moveFileToArchive(File f,String month,String year,String Bank) {
        f.renameTo(new File("Statement Archives/"+month+" "+year+" "+Bank+".pdf"));
    }
}
